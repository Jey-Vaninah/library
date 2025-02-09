package test;

import entity.Book;
import org.junit.jupiter.api.Test;
import repository.*;
import repository.conf.DatabaseConnection;

import java.sql.Connection;
import java.util.List;

import static entity.Topic.COMEDY;
import static entity.Topic.ROMANCE;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static repository.Order.OrderValue.ASC;
import static test.utils.AuthorTestDataUtils.marieCurie;
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

    @Test
    void find_books_by_criteria_ok() {
        List<Book> expecteds = List.of(
            histoiresRomantiques()
        );
        Order order = new Order("name", ASC);
        List<Criteria> criteria = List.of(
            new Criteria("name", "histoires")
        );

        List<Book> actuals = subject.findByCriteria(criteria, order);

        assertEquals(expecteds, actuals);
    }

    @Test
    void find_books_by_author_ok() {
        List<Book> expecteds = List.of(
            histoiresRomantiques()
        );
        Order order = new Order("name", ASC);
        List<Criteria> criteria = List.of(
            new Criteria("author_id", marieCurie().getId())
        );

        List<Book> actuals = subject.findByCriteria(criteria, order);

        assertEquals(expecteds, actuals);
    }

    @Test
    void find_books_by_topic_ok() {
        List<Book> expecteds = List.of(
            leGrandRire(),
            rireEtVie()
        );
        Order order = new Order("name", ASC);
        List<Criteria> criteria = List.of(
            new Criteria("topic", COMEDY)
        );

        List<Book> actuals = subject.findByCriteria(criteria, order);

        assertEquals(expecteds, actuals);
    }

    @Test
    void find_books_by_multiple_criteria_ok() {
        List<Book> expecteds = List.of(
            histoiresRomantiques()
        );
        Order order = new Order("name", ASC);
        List<Criteria> criteria = List.of(
            new Criteria("name", "histoi"),
            new Criteria("topic", ROMANCE)
        );

        List<Book> actuals = subject.findByCriteria(criteria, order);

        assertEquals(expecteds, actuals);
    }
}
