package test;

import entity.Author;
import org.junit.jupiter.api.Test;
import repository.AuthorRepository;
import repository.Criteria;
import repository.Order;
import repository.Pagination;
import repository.conf.DatabaseConnection;

import java.sql.Connection;
import java.util.List;

import static entity.Gender.MALE;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static repository.Order.OrderValue.ASC;
import static test.utils.AuthorTestDataUtils.*;

class AuthorRepositoryTest {
    final DatabaseConnection db = new DatabaseConnection();
    final Connection connection = db.getConnection();
    final AuthorRepository subject = new AuthorRepository(connection);

    @Test
    void find_by_id_ok(){
        Author expected = jeanDupont();

        Author actual = subject.findById(expected.getId());

        assertEquals(expected, actual);
    }

    @Test
    void read_all_authors_ok() {
        List<Author> expecteds = List.of(
            albertCamus(),
            jeanDupont(),
            marieCurie()
        );
        Pagination pagination = new Pagination(1, 10);
        Order order = new Order("name", ASC);

        List<Author> actuals = subject.findAll(pagination, order);

        assertEquals(expecteds, actuals);
    }

    @Test
    void read_authors_by_criteria_ok(){
        List<Author> expecteds = List.of(
            marieCurie()
        );
        Order order = new Order("name", ASC);
        List<Criteria> criteria = List.of(
            new Criteria("name", "marie")
        );

        List<Author> actuals = subject.findByCriteria(criteria, order);

        assertEquals(expecteds, actuals);
    }

    @Test
    void read_authors_by_gender(){
        List<Author> expecteds = List.of(
            albertCamus(),
            jeanDupont()
        );
        Order order = new Order("name", ASC);
        List<Criteria> criteria = List.of(
            new Criteria("gender", MALE)
        );

        List<Author> actuals = subject.findByCriteria(criteria, order);

        assertEquals(expecteds, actuals);
    }
}
