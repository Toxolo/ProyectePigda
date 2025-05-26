public class TestConexion {
    public static void main(String[] args) {
        try {
            java.sql.Connection con = DatabaseConnection.getConnection();
            System.out.println("V Conexión exitosa a la base de datos.");
            con.close();
        } catch (Exception e) {
            System.out.println("X Error de conexión: " + e.getMessage());
        }
    }
}
