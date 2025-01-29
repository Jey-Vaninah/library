package test.unit;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import repository.AuthorRepository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class AuthorRepositoryUnitTest {
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
  void find_by_id_ok(){
    assertTrue(true);
  }
}
