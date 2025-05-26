import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    //private static final String URL = "jdbc:sqlserver://PCJOSEP\\SQLEXPRESS:1433;databaseName=InventarioEquipos;integratedSecurity=true;encrypt=true;trustServerCertificate=true";

//    private static final String URL = "jdbc:sqlserver://PCJOSEP\\SQLEXPRESS;databaseName=InventarioEquipos;integratedSecurity=true;encrypt=false;trustServerCertificate=true";
     //private static final String URL = "jdbc:sqlserver://SRVRPS\\SQLSERVER;databaseName=RPS2017PICDA;integratedSecurity=true;encrypt=false;trustServerCertificate=true";

     private static final String URL = "jdbc:sqlserver://PCJOSEP\\SQLEXPRESS;databaseName=InventarioEquipos;user=josep;password=josep;encrypt=false;trustServerCertificate=true";


    //private static final String USER = "jsmorant";
    //private static final String PASSWORD = "Rok1@06";

    public static Connection getConnection() throws SQLException {
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            return DriverManager.getConnection(URL); //, USER, PASSWORD
        } catch (ClassNotFoundException e) {
            throw new SQLException("Error al cargar el controlador JDBC", e);
        }
    }
}
