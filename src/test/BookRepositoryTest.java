package test;

import entity.Author;
import entity.Book;
import org.junit.jupiter.api.Test;
import repository.AuthorRepository;
import repository.BookRepository;
import repository.Pagination;
import repository.conf.DatabaseConnection;

import java.sql.Connection;
import java.time.LocalDate;
import java.util.List;

import static entity.Gender.FEMALE;
import static entity.Topic.ROMANCE;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class BookRepositoryTest {
    final DatabaseConnection db = new DatabaseConnection();
    final Connection connection = db.getConnection();
    final AuthorRepository authorRepository = new AuthorRepository(connection);
    final BookRepository subject = new BookRepository(connection, authorRepository);

    @Test
    public void find_by_id_ok(){
        final Author author = new Author("A002","Marie Curie", FEMALE);
        Book expectedBook = new Book(
            "B001",
            "Histoires Romantiques",
            author,
            320,
            ROMANCE,
            LocalDate.of(2001, 9, 25)
        );

        Book actual = subject.findById("B001");

        assertEquals(expectedBook, actual);
    }

    @Test
    public void read_all_book_ok() {
        final Author author = new Author("A002","Marie Curie", FEMALE);
        Book expectedBook = new Book(
            "B001",
            "Histoires Romantiques",
            author,
            320,
            ROMANCE,
            LocalDate.of(2001, 9, 25)
        );
        Pagination pagination = new Pagination(1, 10);

        List<Book> actual = subject.findAll(pagination);

        assertTrue(actual.contains(expectedBook));
    }
}
