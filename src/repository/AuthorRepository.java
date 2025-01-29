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

    @Override
    public Author findById(String id) {
        String query = "select * from \"author\" where \"id\"=?;";
        try{
            PreparedStatement st = connection.prepareStatement(query);
            st.setString(1,id);
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
    public List<Author> findAll(Pagination pagination) {
        String query = "select * from \"author\" limit ? offset ?;";
        List<Author> authors = new ArrayList<>();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, (pagination.getPage() - 1) * pagination.getPageSize());
            preparedStatement.setInt(2, pagination.getPageSize());
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
                    "gender" = ? 
                where "id" = ?
        """;
        try{
            PreparedStatement prs = connection.prepareStatement(query);
            prs.setString (1, toUpdate.getName());
            prs.setString (2, toUpdate.getGender().toString());
            prs.setString (3, toUpdate.getId());
            prs.executeUpdate();
            return this.findById(toUpdate.getId());
        }catch (SQLException error){
            throw new RuntimeException(error);
        }
    }

    @Override
    public Author create(Author toCreate){
        String query = """
            insert into "author"("id", "name", "gender") 
            values (?, ?, ?);
         """;
        try{
            PreparedStatement prs = connection.prepareStatement(query);
            prs.setString (1, toCreate.getName());
            prs.setString (2, toCreate.getGender().toString());
            prs.setString (3, toCreate.getId());
            prs.executeUpdate();
            return this.findById(toCreate.getId());
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

    private Author resultSetToAuthor(ResultSet rs) throws SQLException {
        return new Author(
            rs.getString("id"),
            rs.getString("name"),
            Gender.valueOf(rs.getString("gender"))
        );
    }
}
