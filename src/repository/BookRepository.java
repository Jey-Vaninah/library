package repository;

import entity.Author;
import entity.Book;
import entity.Gender;
import entity.Topic;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


public class BookRepository {
    private final Connection connection;
    private final AuthorRepository authorRepository;
    private BookRepository bookRepository;

    public BookRepository(Connection connection, AuthorRepository authorRepository) {
        this.connection = connection;
        this.authorRepository = authorRepository;
    }

    public Book findById(int id) {
        String query = "SELECT * FROM book WHERE id = ?";
        try (
                PreparedStatement st = connection.prepareStatement(query)) {
                st.setString(1, String.valueOf(id));
                ResultSet rs = st.executeQuery();
                if (rs.next()){
                    return resulSetToBook(rs);
                }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }



    private Book resulSetToBook(ResultSet rs) throws SQLException {
        final Author author = this.bookRepository.findById(rs.getInt("author_id")).getAuthor();

        return new Book (
                rs.getString("id"),
                rs.getString("name"),
                author,
                rs.getInt("pageNumber"),
                Topic.valueOf(rs.getString("topic")),
                rs.getDate("releaseDate").toLocalDate()

        );
    }
}


