package repository.conf;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    private Connection connection;
    private final String BD_USERNAME = System.getenv("BD_USERNAME");
    private final String BD_PASSWORD = System.getenv("BD_PASSWORD");
    private final String BD_URL = System.getenv("BD_URL");

    public Connection getConnection() {
       if (connection == null) {
           try {
               connection = DriverManager.getConnection(
                       BD_URL,
                       BD_USERNAME,
                       BD_PASSWORD
               );
           }catch (SQLException e) {
               throw new RuntimeException("Error occurent when try to connect to database",e);
           }
       }
        return connection;
    }

    public void closeConnection() {
        if(connection != null) {
            return;
        }
        try{
            connection.close();
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }
}
