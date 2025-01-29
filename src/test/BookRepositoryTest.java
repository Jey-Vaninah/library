package test;

import entity.Author;
import entity.Book;
import org.junit.jupiter.api.Test;
import repository.AuthorRepository;
import repository.BookRepository;
import repository.Pagination;
import repository.conf.DatabaseConnection;

import java.sql.Connection;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static test.utils.BookTestDataUtils.histoiresRomantiques;

public class BookRepositoryTest {
    final DatabaseConnection db = new DatabaseConnection();
    final Connection connection = db.getConnection();
    final AuthorRepository authorRepository = new AuthorRepository(connection);
    final BookRepository subject = new BookRepository(connection, authorRepository);

    @Test
    public void find_by_id_ok(){
        Book expected = histoiresRomantiques();

        Book actual = subject.findById(expected.getId());

        assertEquals(expected, actual);
    }

    @Test
    public void read_all_book_ok() {
        Book expected = histoiresRomantiques();
        Pagination pagination = new Pagination(1, 10);

        List<Book> actual = subject.findAll(pagination);

        assertTrue(actual.contains(expected));
    }
}
