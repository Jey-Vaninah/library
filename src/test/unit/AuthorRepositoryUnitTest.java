package test.unit;

import entity.Author;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import repository.AuthorRepository;
import repository.Pagination;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
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

    List<Author> actuals = this.subject.findAll(new Pagination(1, 10));

    assertEquals(expecteds, actuals);
  }
}
