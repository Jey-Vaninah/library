package test.unit;

import entity.Author;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import repository.AuthorRepository;
import repository.Order;
import repository.Pagination;

import java.sql.*;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static repository.Order.OrderValue.ASC;
import static test.utils.AuthorTestDataUtils.*;

class AuthorRepositoryUnitTest {
  Connection connectionMock = mock();
  ResultSet resultSetMock = mock();
  PreparedStatement preparedStatementMock = mock();

  AuthorRepository subject = new AuthorRepository(connectionMock);

  @BeforeEach
  void setup() throws SQLException {
    when(connectionMock.prepareStatement(any())).thenReturn(preparedStatementMock);
    when(preparedStatementMock.executeQuery()).thenReturn(resultSetMock);
  }

  @Test
  void find_by_id_ok() throws SQLException {
    Author expected = jeanDupont();

    when(resultSetMock.next()).thenReturn(true).thenReturn(false);
    when(resultSetMock.getString("id")).thenReturn(expected.getId());
    when(resultSetMock.getString("name")).thenReturn(expected.getName());
    when(resultSetMock.getString("gender")).thenReturn(expected.getGender().toString());
    when(resultSetMock.getDate("birth_date")).thenReturn(Date.valueOf(expected.getBirthdate()));

    Author actual = this.subject.findById(expected.getId());

    assertEquals(expected, actual);
  }

  @Test
  void find_all_ok() throws SQLException {
    Author jeanDupont = jeanDupont();
    Author marieCurie = marieCurie();
    Author albertCamus = albertCamus();
    List<Author> expecteds = List.of(
        jeanDupont,
        marieCurie,
        albertCamus
    );

    when(resultSetMock.next())
      .thenReturn(true)
      .thenReturn(true)
      .thenReturn(true)
      .thenReturn(false);
    when(resultSetMock.getString("id"))
      .thenReturn(jeanDupont.getId())
      .thenReturn(marieCurie.getId())
      .thenReturn(albertCamus.getId());
    when(resultSetMock.getString("name"))
      .thenReturn(jeanDupont.getName())
      .thenReturn(marieCurie.getName())
      .thenReturn(albertCamus.getName());
    when(resultSetMock.getString("gender"))
      .thenReturn(jeanDupont.getGender().toString())
      .thenReturn(marieCurie.getGender().toString())
      .thenReturn(albertCamus.getGender().toString());
    when(resultSetMock.getDate("birth_date"))
      .thenReturn(Date.valueOf(jeanDupont.getBirthdate()))
      .thenReturn(Date.valueOf(marieCurie.getBirthdate()))
      .thenReturn(Date.valueOf(albertCamus.getBirthdate()));

    List<Author> actuals = this.subject.findAll(
        new Pagination(1, 10),
        new Order("name", ASC)
    );

    assertEquals(expecteds, actuals);
  }

  @Test
  void can_create_author_ok() throws SQLException {
    Author expected = jeanDupont();

    when(preparedStatementMock.executeUpdate()).thenReturn(1);
    when(resultSetMock.next()).thenReturn(true).thenReturn(false);
    when(resultSetMock.getString("id")).thenReturn(expected.getId());
    when(resultSetMock.getString("name")).thenReturn(expected.getName());
    when(resultSetMock.getString("gender")).thenReturn(expected.getGender().toString());
    when(resultSetMock.getDate("birth_date")).thenReturn(Date.valueOf(expected.getBirthdate()));

    var actual = this.subject.create(expected);

    verify(preparedStatementMock, times(2)).setString(1, expected.getId());
    verify(preparedStatementMock, times(1)).setString(2, expected.getGender().toString());
    verify(preparedStatementMock, times(1)).setString(3, expected.getName());
    assertEquals(expected, actual);
  }

  @Test
  void can_update_author_ok() throws SQLException {
    Author expected = jeanDupont();

    when(preparedStatementMock.executeUpdate()).thenReturn(1);
    when(resultSetMock.next()).thenReturn(true).thenReturn(false);
    when(resultSetMock.getString("id")).thenReturn(expected.getId());
    when(resultSetMock.getString("name")).thenReturn(expected.getName());
    when(resultSetMock.getString("gender")).thenReturn(expected.getGender().toString());
    when(resultSetMock.getDate("birth_date")).thenReturn(Date.valueOf(expected.getBirthdate()));

    var actual = this.subject.update(expected);

    verify(preparedStatementMock, times(1)).setString(1, expected.getId());
    verify(preparedStatementMock, times(1)).setString(3, expected.getId());
    verify(preparedStatementMock, times(1)).setString(1, expected.getName());
    verify(preparedStatementMock, times(1)).setString(2, expected.getGender().toString());
    assertEquals(expected, actual);
  }

  @Test
  void can_delete_author() throws SQLException {
    Author expected = jeanDupont();
    when(preparedStatementMock.executeUpdate()).thenReturn(1);
    when(resultSetMock.next()).thenReturn(true).thenReturn(false);
    when(resultSetMock.getString("id")).thenReturn(expected.getId());
    when(resultSetMock.getString("name")).thenReturn(expected.getName());
    when(resultSetMock.getString("gender")).thenReturn(expected.getGender().toString());
    when(resultSetMock.getDate("birth_date")).thenReturn(Date.valueOf(expected.getBirthdate()));

    var actual = this.subject.deleteById(expected.getId());

    verify(preparedStatementMock, times(2)).setString(1, expected.getId());
    assertEquals(expected, actual);
  }
}
