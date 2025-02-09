package test;

import entity.Book;
import org.junit.jupiter.api.Test;
import repository.AuthorRepository;
import repository.BookRepository;
import repository.Order;
import repository.Pagination;
import repository.conf.DatabaseConnection;

import java.sql.Connection;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static repository.Order.OrderValue.ASC;
import static test.utils.BookTestDataUtils.*;

class BookRepositoryTest {
    final DatabaseConnection db = new DatabaseConnection();
    final Connection connection = db.getConnection();
    final AuthorRepository authorRepository = new AuthorRepository(connection);
    final BookRepository subject = new BookRepository(connection, authorRepository);

    @Test
    void find_by_id_ok(){
        Book expected = histoiresRomantiques();

        Book actual = subject.findById(expected.getId());

        assertEquals(expected, actual);
    }

    @Test
    void read_all_book_ok() {
        List<Book> expecteds = List.of(
            histoiresRomantiques(),
            leGrandRire(),
            rireEtVie()
        );
        Pagination pagination = new Pagination(1, 10);
        Order order = new Order("name", ASC);

        List<Book> actuals = subject.findAll(pagination, order);

        assertEquals(expecteds, actuals);
    }
}
