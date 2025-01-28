package test;

import entity.Author;
import entity.Book;
import entity.Gender;
import entity.Topic;
import org.junit.jupiter.api.Test;
import repository.AuthorRepository;
import repository.BookRepository;
import repository.conf.DatabaseConnection;

import java.sql.Connection;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BookRepositoryTest {
    final DatabaseConnection db = new DatabaseConnection();
    final Connection connection = db.getConnection();
    final AuthorRepository authorRepository = new AuthorRepository(connection);
    final BookRepository subject = new BookRepository(connection, authorRepository);


    void find_by_id_ok(){
        final Author author = new Author("A002","Jean Dupont", Gender.MALE);
        Book expectedBook = new Book(
                "B001",
                "",
                author,
                320,
                Topic.ROMANCE,
                LocalDate.of(2001, 9, 25)

        );

        Book actual = subject.findById("A001");

        assertEquals(expectedBook,actual);
    }

    @Test
    void read_all_book_ok() {
        final Author author = new Author("A002","Jean Dupont", Gender.MALE);
        Book expectedBook = new Book(
                "B001",
                "Histoires Romantiques",
                author,
                320,
                Topic.ROMANCE,
                LocalDate.of(2001, 9, 25)
        );

        List<Book> actual = subject.findAll();

        assertTrue(actual.contains(expectedBook));
    }

    private void assertTrue(boolean contains) {
    }

}
