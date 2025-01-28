package repository;

import entity.Author;
import entity.Gender;
import repository.conf.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AuthorRepository {
    private final Connection connection;


    public AuthorRepository(Connection connection) {
        this.connection = connection;
    }

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


    public List<Author> findAll() {
        String query = "select * from \"author\";";
        List<Author> authors = new ArrayList<>();
        try {

            ResultSet rs = connection.createStatement().executeQuery(query);
            while(rs.next()){
                authors.add(resultSetToAuthor(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return authors;
    }

    public Author udpate(Author toUpdate){
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

    public Author create(Author toCreate){
        String query = """
            insert into "student"("id", "name", "ref", "birthdate", "gender", "group_id") 
            values (?, ?, ?, ?, ?, ?);
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

    public Author crupdate(Author crupdateAuthor){
        final boolean isCreate = this.findById(crupdateAuthor.getId()) == null;
        if(isCreate) {
            return this.create(crupdateAuthor);
        }
        return this.udpate(crupdateAuthor);
    }

    public Object Author(String id){
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
