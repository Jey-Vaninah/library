package repository;

import entity.Author;
import entity.Book;
import entity.Topic;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BookRepository {
    private Connection connection = null;
    private final AuthorRepository authorRepository;

    public BookRepository(Connection connection, AuthorRepository authorRepository) {
        this.connection = connection;
        this.authorRepository = authorRepository;
    }

    public Book findById(String id) {
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

    public List<Book> findAll() {
        String query = "SELECT * FROM book";
        List<Book> books = new ArrayList<>();
        try {
            ResultSet rs = connection.createStatement().executeQuery(query);
            while (rs.next()) {
                books.add(resulSetToBook(rs));
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return books;
    }

    public Book create(Book toCreate) {
        String query = """
        insert into "book"("id", "name", "author_id", "page_numbers", "topic", "release_date") 
        values (?, ?, ?, ?, ?, ?, ?);
    """;
        try {
            PreparedStatement prs = connection.prepareStatement(query);
            prs.setString(1, toCreate.getId());
            prs.setString(2, toCreate.getName());
            prs.setString (3, toCreate.getAuthor().getId());
            prs.setInt(4, toCreate.getPageNumbers());
            prs.setString(5, toCreate.getTopic().toString());
            prs.setDate(6, Date.valueOf(toCreate.getReleaseDate()));
            prs.executeUpdate();
            return this.findById(toCreate.getId());
        } catch (SQLException error) {
            throw new RuntimeException(error);
        }
    }

    public Book udpate (Book toUpdate){
        String query = """
            update "book"
                set "name" = ?,
                    "author_id" = ? ,
                    "page_numbers" = ? ,
                    "topic" = ? ,
                    "release_date" = ?
                where "id" = ?
        """;
        try{
            PreparedStatement prs = connection.prepareStatement(query);
            prs.setString (1, toUpdate.getName());
            prs.setString(2, toUpdate.getAuthor().getId());
            prs.setInt(3, toUpdate.getPageNumbers());
            prs.setString (4, toUpdate.getTopic().toString());
            prs.setDate (5, Date.valueOf(toUpdate.getReleaseDate()));
            prs.setString (6, toUpdate.getId());
            prs.executeUpdate();
            return this.findById(toUpdate.getId());
        }catch (SQLException error){
            throw new RuntimeException(error);
        }
    }

    public Book crupdate(Book crupdateBook){
        final boolean isCreate = this.findById(crupdateBook.getId()) == null;
        if(isCreate) {
            return this.create(crupdateBook);
        }
        return this.udpate(crupdateBook);
    }

    public Book deleteById(String id){
        String query = """
            delete from "book" where "id" = ?;
        """;

        try{
            final Book toDelete = this.findById(id);
            PreparedStatement prs = connection.prepareStatement(query);
            prs.setString (1, toDelete.getId());
            prs.executeUpdate();
            return toDelete;
        }catch (SQLException error){
            throw new RuntimeException(error);
        }
    }

    private Book resulSetToBook(ResultSet rs) throws SQLException {
        final  Author author = this.authorRepository.findById((rs.getString("book_id")));
        return new Book(
            rs.getString("id"),
            rs.getString("name"),
            author,
            rs.getInt("page_numbers"),
            Topic.valueOf(rs.getString("topic")),
            rs.getDate("releaseDate").toLocalDate()
        );
    }
}
