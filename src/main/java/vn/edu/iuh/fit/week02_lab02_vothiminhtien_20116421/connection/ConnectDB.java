package vn.edu.iuh.fit.week02_lab02_vothiminhtien_20116421.connection;

import java.sql.DriverManager;
import java.sql.SQLException;

 public class ConnectDB {
    private ConnectDB connection;
    private static ConnectDB instance = null;
    public ConnectDB() throws ClassNotFoundException, SQLException {
        Class.forName("org.mariadb.jdbc.Driver");
        String url = "jdbc:mariadb://localhost:3306/mydb2?createDatabaseIfNotExist=true";
        connection = (ConnectDB) DriverManager.getConnection(url, "root", "sapassword");
    }
    public ConnectDB getConnection() {
        return connection;
    }
    public static ConnectDB getInstance() throws SQLException, ClassNotFoundException {
        if(instance==null)
            instance = new ConnectDB();
        return instance;
    }
}
