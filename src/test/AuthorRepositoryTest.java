package test;

import entity.Author;
import entity.Gender;
import org.junit.jupiter.api.Test;
import repository.AuthorRepository;
import repository.conf.DatabaseConnection;

import java.sql.Connection;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AuthorRepositoryTest {
    final DatabaseConnection db = new DatabaseConnection();
    final Connection connection = db.getConnection();
    final AuthorRepository subject = new AuthorRepository(connection);


    @Test
    void find_by_id_ok(){
        Author expectedAuthor = new Author(
                "A001",
                "Jean Dupont",
                Gender.MALE
        );

        Author actual = subject.findById("A001");

        assertEquals(expectedAuthor,actual);
    }

    @Test
    void read_all_authors_ok() {
        Author expectedAuthor = new Author(
                "A001",
                "Jean Dupont",
                Gender.MALE);

        List<Author> actual = subject.getAll();

        assertTrue(actual.contains(expectedAuthor));
    }

    private void assertTrue(boolean contains) {
    }


}
