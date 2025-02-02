package test.unit;

import entity.Author;
import entity.Book;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import repository.AuthorRepository;
import repository.BookRepository;
import repository.Pagination;

import java.sql.*;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;
import static test.utils.BookTestDataUtils.histoiresRomantiques;
import static test.utils.BookTestDataUtils.leGrandRire;
import static test.utils.BookTestDataUtils.rireEtVie;
import static test.utils.AuthorTestDataUtils.jeanDupont;
import static test.utils.AuthorTestDataUtils.marieCurie;
import static test.utils.AuthorTestDataUtils.albertCamus;

class BookRepositoryUnitTest {
  Connection connectionMock = mock();
  ResultSet resultSetMock = mock();
  PreparedStatement preparedStatementMock = mock();
  AuthorRepository authorRepositoryMock = mock() ;
  Author jeanDupont = jeanDupont();
  Author marieCurie = marieCurie();
  Author  albertCamus = albertCamus();

  BookRepository subject = new BookRepository(connectionMock, authorRepositoryMock);

  @BeforeEach
  void setup() throws SQLException {
    when(connectionMock.prepareStatement(any())).thenReturn(preparedStatementMock);
    when(preparedStatementMock.executeQuery()).thenReturn(resultSetMock);

    when(authorRepositoryMock.findById(jeanDupont.getId())).thenReturn(jeanDupont);
    when(authorRepositoryMock.findById(marieCurie.getId())).thenReturn(marieCurie);
    when(authorRepositoryMock.findById(albertCamus.getId())).thenReturn(albertCamus);
  }

  @Test
  void find_by_id_ok() throws SQLException {
    Book expected = histoiresRomantiques();

    when(resultSetMock.next()).thenReturn(true).thenReturn(false);
    when(resultSetMock.getString("id")).thenReturn(expected.getId());
    when(resultSetMock.getString("name")).thenReturn(expected.getName());
    when(resultSetMock.getInt("page_numbers")).thenReturn(expected.getPageNumbers());
    when(resultSetMock.getString("author_id")).thenReturn(expected.getAuthor().getId());
    when(resultSetMock.getDate("release_date")).thenReturn(Date.valueOf(expected.getReleaseDate()));
    when(resultSetMock.getString("topic")).thenReturn(expected.getTopic().toString());

    Book actual = this.subject.findById(expected.getId());

    assertEquals(expected, actual);
  }

  @Test
  void find_all_ok() throws SQLException {
    Book histoiresRomantiques = histoiresRomantiques();
    Book leGrandRire= leGrandRire();
    Book rireEtVie = rireEtVie();
    List<Book> expecteds = List.of(
        histoiresRomantiques,
        leGrandRire,
        rireEtVie
    );

    when(resultSetMock.next())
        .thenReturn(true)
        .thenReturn(true)
        .thenReturn(true)
        .thenReturn(false);
    when(resultSetMock.getString("id"))
        .thenReturn(histoiresRomantiques.getId())
        .thenReturn(leGrandRire.getId())
        .thenReturn(rireEtVie.getId());
    when(resultSetMock.getString("name"))
        .thenReturn(histoiresRomantiques.getName())
        .thenReturn(leGrandRire.getName())
        .thenReturn(rireEtVie.getName());
    when(resultSetMock.getString("topic"))
        .thenReturn(histoiresRomantiques.getTopic().toString())
        .thenReturn(leGrandRire.getTopic().toString())
        .thenReturn(rireEtVie.getTopic().toString());
    when(resultSetMock.getString("author_id"))
        .thenReturn(marieCurie.getId())
        .thenReturn(jeanDupont.getId())
        .thenReturn(albertCamus.getId());
    when(resultSetMock.getInt("page_numbers"))
        .thenReturn(histoiresRomantiques.getPageNumbers())
        .thenReturn(leGrandRire.getPageNumbers())
        .thenReturn(rireEtVie.getPageNumbers());
    when(resultSetMock.getDate("release_date"))
        .thenReturn(Date.valueOf(histoiresRomantiques.getReleaseDate()))
        .thenReturn(Date.valueOf(leGrandRire.getReleaseDate()))
        .thenReturn(Date.valueOf(rireEtVie.getReleaseDate()));
    List<Book> actuals = this.subject.findAll(new Pagination(1, 10));

    assertEquals(expecteds, actuals);
  }

  @Test
  void can_delete_book() throws SQLException {
    Book expected = histoiresRomantiques();

    when(preparedStatementMock.executeUpdate()).thenReturn(1);
    when(resultSetMock.next()).thenReturn(true).thenReturn(false);
    when(resultSetMock.getString("id")).thenReturn(expected.getId());
    when(resultSetMock.getString("name")).thenReturn(expected.getName());
    when(resultSetMock.getString("topic")).thenReturn(expected.getTopic().toString());
    when(resultSetMock.getInt("page_numbers")).thenReturn(expected.getPageNumbers());
    when(resultSetMock.getDate("release_date")).thenReturn(Date.valueOf(expected.getReleaseDate()));
    when(resultSetMock.getString("author_id")).thenReturn(expected.getAuthor().getId());

    var actual = this.subject.deleteById(expected.getId());

    verify(preparedStatementMock, times(2)).setString(1, expected.getId());
    assertEquals(expected, actual);
  }

  @Test
  void can_create_a_book() throws SQLException {
    Book expected = histoiresRomantiques();

    when(preparedStatementMock.executeUpdate()).thenReturn(1);
    when(resultSetMock.next()).thenReturn(true).thenReturn(false);
    when(resultSetMock.getString("id")).thenReturn(expected.getId());
    when(resultSetMock.getString("name")).thenReturn(expected.getName());
    when(resultSetMock.getString("topic")).thenReturn(expected.getTopic().toString());
    when(resultSetMock.getInt("page_numbers")).thenReturn(expected.getPageNumbers());
    when(resultSetMock.getDate("release_date")).thenReturn(Date.valueOf(expected.getReleaseDate()));
    when(resultSetMock.getString("author_id")).thenReturn(expected.getAuthor().getId());

    var actual = this.subject.create(expected);

    verify(preparedStatementMock, times(2)).setString(1, expected.getId());
    verify(preparedStatementMock, times(1)).setString(2, expected.getName());
    verify(preparedStatementMock, times(1)).setString(3, expected.getAuthor().getId());
    verify(preparedStatementMock, times(1)).setInt(4, expected.getPageNumbers());
    verify(preparedStatementMock, times(1)).setString(5, expected.getTopic().toString());
    verify(preparedStatementMock, times(1)).setDate(6, Date.valueOf(expected.getReleaseDate()));
    assertEquals(expected, actual);
  }

  @Test
  void can_update_a_book() throws SQLException {
    Book expected = histoiresRomantiques();

    when(preparedStatementMock.executeUpdate()).thenReturn(1);
    when(resultSetMock.next()).thenReturn(true).thenReturn(false);
    when(resultSetMock.getString("id")).thenReturn(expected.getId());
    when(resultSetMock.getString("name")).thenReturn(expected.getName());
    when(resultSetMock.getString("topic")).thenReturn(expected.getTopic().toString());
    when(resultSetMock.getInt("page_numbers")).thenReturn(expected.getPageNumbers());
    when(resultSetMock.getDate("release_date")).thenReturn(Date.valueOf(expected.getReleaseDate()));
    when(resultSetMock.getString("author_id")).thenReturn(expected.getAuthor().getId());

    var actual = this.subject.update(expected);

    verify(preparedStatementMock, times(1)).setString(1, expected.getId());
    verify(preparedStatementMock, times(1)).setString(6, expected.getId());
    verify(preparedStatementMock, times(1)).setString(1, expected.getName());
    verify(preparedStatementMock, times(1)).setString(2, expected.getAuthor().getId());
    verify(preparedStatementMock, times(1)).setInt(3, expected.getPageNumbers());
    verify(preparedStatementMock, times(1)).setString(4, expected.getTopic().toString());
    verify(preparedStatementMock, times(1)).setDate(5, Date.valueOf(expected.getReleaseDate()));
    assertEquals(expected, actual);
  }
}
