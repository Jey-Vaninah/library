import repository.conf.DatabaseConnection;

import java.sql.Connection;

public class Main {
    public static void main(String[] args) {
        DatabaseConnection db = new DatabaseConnection();
        final Connection connection = db.getConnection();
    }
}