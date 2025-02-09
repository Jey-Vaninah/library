package repository;

import entity.Author;
import entity.Gender;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AuthorRepository implements Repository<Author> {
    private final Connection connection;

    public AuthorRepository(Connection connection) {
        this.connection = connection;
    }

    private Author resultSetToAuthor(ResultSet rs) throws SQLException {
        return new Author(
            rs.getString("id"),
            rs.getString("name"),
            Gender.valueOf(rs.getString("gender")),
            rs.getDate("birth_date").toLocalDate()
        );
    }

    @Override
    public Author findById(String id) {
        String query = "select * from \"author\" where \"id\" = ?";
        try{
            PreparedStatement st = connection.prepareStatement(query);
            st.setString(1, id);
            ResultSet rs = st.executeQuery();
            if(rs.next()) {
                return resultSetToAuthor(rs);
            }
            return null;
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Author> findAll(Pagination pagination, Order order) {
        StringBuilder query = new StringBuilder("select * from \"author\"");
        query.append(" order by ").append(order.getOrderBy()).append(" ").append(order.getOrderValue());
        query.append(" limit ? offset ?");

        List<Author> authors = new ArrayList<>();

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query.toString());
            preparedStatement.setInt(1, pagination.getPageSize());
            preparedStatement.setInt(2, (pagination.getPage() - 1) * pagination.getPageSize());
            ResultSet rs = preparedStatement.executeQuery();
            while(rs.next()){
                authors.add(resultSetToAuthor(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return authors;
    }


    @Override
    public Author update(Author toUpdate){
        String query = """
            update "author"
                set "name" = ? ,
                    "gender" = ?,
                    "birth_date" = ?
                where "id" = ?
        """;
        try{
            PreparedStatement prs = connection.prepareStatement(query);
            prs.setString (1, toUpdate.getName());
            prs.setString (2, toUpdate.getGender().toString());
            prs.setString (3, toUpdate.getId());
            prs.setDate(4, Date.valueOf(toUpdate.getBirthdate()));
            prs.executeUpdate();
            return this.findById(toUpdate.getId());
        }catch (SQLException error){
            throw new RuntimeException(error);
        }
    }

    @Override
    public Author create(Author toCreate){
        String query = """
            insert into "author"("id", "name", "gender", "birth_date")
            values (?, ?, ?, ?);
         """;
        try{
            PreparedStatement prs = connection.prepareStatement(query);
            prs.setString (1, toCreate.getId());
            prs.setString (2, toCreate.getGender().toString());
            prs.setString (3, toCreate.getName());
            prs.setDate(4, Date.valueOf(toCreate.getBirthdate()));
            prs.executeUpdate();
            return this.findById(toCreate.getId());
        }catch (SQLException error){
            throw new RuntimeException(error);
        }
    }

    @Override
    public Author deleteById(String id){
        String query = """
            delete from "author" where "id" = ?;
        """;

        try{
            final Author toDelete = this.findById((id));
            PreparedStatement prs = connection.prepareStatement(query);
            prs.setString (1, toDelete.getId());
            prs.executeUpdate();
            return toDelete;
        }catch (SQLException error){
            throw new RuntimeException(error);
        }
    }

    @Override
    public Author crupdate(Author crupdateAuthor){
        final boolean isCreate = this.findById(crupdateAuthor.getId()) == null;
        if(isCreate) {
            return this.create(crupdateAuthor);
        }
        return this.update(crupdateAuthor);
    }

    @Override
    public List<Author> findByCriteria(List<Criteria> criteria, Order order) {
        List<Author> authors = new ArrayList<>();
        StringBuilder sql = new StringBuilder("SELECT a.id, a.name, a.birth_date FROM author a WHERE 1=1");
        List<Object> parameters = new ArrayList<>();

        for (Criteria c : criteria) {
            if ("name".equals(c.getColumn())) {
                sql.append(" AND a.").append(c.getColumn()).append(" ilike ?");
                parameters.add("%" + c.getValue().toString() + "%");
            } else if ("birth_date".equals(c.getColumn())) {
                sql.append(" AND a.").append(c.getColumn()).append(" = ?");
                parameters.add(c.getValue().toString());
            }
        }
        sql.append(" ORDER BY a.").append(order.getOrderBy()).append(" ").append(order.getOrderValue());

        try {
            PreparedStatement statement = connection.prepareStatement(sql.toString());
            for (int i = 0; i < parameters.size(); i++) {
                statement.setObject(i + 1, parameters.get(i));
            }
            ResultSet rs = statement.executeQuery();
            while(rs.next()){
                authors.add(resultSetToAuthor(rs));
            }
        } catch (SQLException error) {
            throw new RuntimeException(error.getMessage());
        }
        return authors;
    }
}
