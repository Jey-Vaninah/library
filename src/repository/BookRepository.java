package repository;

import entity.Author;
import entity.Book;
import entity.Topic;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BookRepository implements Repository<Book>{
    private final Connection connection;
    private final AuthorRepository authorRepository;

    public BookRepository(Connection connection, AuthorRepository authorRepository) {
        this.connection = connection;
        this.authorRepository = authorRepository;
    }

    @Override
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

    @Override
    public List<Book> findAll(Pagination pagination, Order order) {
        StringBuilder query = new StringBuilder("select * from \"book\"");
        query.append(" order by ").append(order.getOrderBy()).append(" ").append(order.getOrderValue());
        query.append(" limit ? offset ?");
        List<Book> books = new ArrayList<>();

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query.toString());
            preparedStatement.setInt(1, pagination.getPageSize());
            preparedStatement.setInt(2, (pagination.getPage() - 1) * pagination.getPageSize());
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                books.add(resulSetToBook(rs));
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return books;
    }

    @Override
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

    @Override
    public Book update(Book toUpdate){
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

    @Override
    public Book crupdate(Book crupdateBook){
        final boolean isCreate = this.findById(crupdateBook.getId()) == null;
        if(isCreate) {
            return this.create(crupdateBook);
        }
        return this.update(crupdateBook);
    }

    @Override
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
        final  Author author = this.authorRepository.findById((rs.getString("author_id")));
        return new Book(
            rs.getString("id"),
            rs.getString("name"),
            author,
            rs.getInt("page_numbers"),
            Topic.valueOf(rs.getString("topic")),
            rs.getDate("release_date").toLocalDate()
        );
    }

    @Override
    public List<Book> findByCriteria(List<Criteria> criteria, Order order, Pagination pagination) {
        List<Book> books = new ArrayList<>();
        StringBuilder sql = new StringBuilder("SELECT * FROM book WHERE 1=1");
        List<Object> parameters = new ArrayList<>();

        for (Criteria c : criteria) {
            if ("name".equals(c.getColumn())) {
                sql.append(" AND ").append(c.getColumn()).append(" ILIKE ?");
                parameters.add("%" + c.getValue().toString() + "%");
            } else if ("author_id".equals(c.getColumn())) {
                sql.append(" AND ").append(c.getColumn()).append(" = ?");
                parameters.add(c.getValue().toString());
            } else if ("topic".equals(c.getColumn())) {
                sql.append(" AND ").append(c.getColumn()).append(" = ?");
                parameters.add(c.getValue());
            } else if ("release_date".equals(c.getColumn())) {
                sql.append(" AND ").append(c.getColumn()).append(" = ?");
                parameters.add(Date.valueOf(c.getValue().toString()));
            }
        }

        sql.append(" order by ").append(order.getOrderBy()).append(" ").append(order.getOrderValue());
        sql.append(" limit ").append(pagination.getPageSize()).append(" offset ").append(
            (pagination.getPage()  - 1) * pagination.getPageSize()
        );

        try (PreparedStatement statement = connection.prepareStatement(sql.toString())) {
            for (int i = 0; i < parameters.size(); i++) {
                Object value = parameters.get(i);
                if(value.getClass().isEnum()){
                    statement.setObject(i  + 1, value, Types.OTHER);
                    continue;
                }
                statement.setObject(i + 1, value);
            }
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                books.add(resulSetToBook(rs));
            }
        } catch (SQLException error) {
            throw new RuntimeException(error);
        }
        return books;
    }
}
