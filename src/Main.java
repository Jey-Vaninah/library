import repository.AuthorRepository;
import repository.BookRepository;
import repository.Pagination;
import repository.conf.DatabaseConnection;

import java.sql.Connection;

public class Main {
    public static void main(String[] args) {
        DatabaseConnection db = new DatabaseConnection();
        final Connection connection = db.getConnection();
        final AuthorRepository authorRepository = new AuthorRepository(connection);
        final BookRepository bookRepository = new BookRepository(connection, authorRepository);
        System.out.println(authorRepository.findAll(new Pagination(1, 10)));
        System.out.println(bookRepository.findAll(new Pagination(1, 10)));
    }
}