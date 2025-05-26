import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TareaMantenimientoDAO {

    public void insertarTarea(TareaMantenimiento tarea) throws SQLException {
        String sql = "INSERT INTO TareasMantenimiento (idEquipo, descripcion, fecha, tecnico, tipoMantenimiento, resultado, observaciones, coste) "
                   + "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, tarea.getIdEquipo());
            stmt.setString(2, tarea.getDescripcion());
            stmt.setDate(3, tarea.getFecha());
            stmt.setString(4, tarea.getTecnico());
            stmt.setString(5, tarea.getTipoMantenimiento());
            stmt.setString(6, tarea.getResultado());
            stmt.setString(7, tarea.getObservaciones());
            stmt.setDouble(8, tarea.getCoste());
            stmt.executeUpdate();
        }
    }

    public List<TareaMantenimiento> obtenerTareasPorEquipo(int idEquipo) throws SQLException {
        List<TareaMantenimiento> lista = new ArrayList<>();
        String sql = "SELECT * FROM TareasMantenimiento WHERE idEquipo = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idEquipo);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    TareaMantenimiento tarea = new TareaMantenimiento(
                        rs.getInt("idTarea"),
                        rs.getInt("idEquipo"),
                        rs.getString("descripcion"),
                        rs.getDate("fecha"),
                        rs.getString("tecnico"),
                        rs.getString("tipoMantenimiento"),
                        rs.getString("resultado"),
                        rs.getString("observaciones"),
                        rs.getDouble("coste")
                    );
                    lista.add(tarea);
                }
            }
        }
        return lista;
    }

    public List<TareaMantenimiento> buscarTareasPorDescripcion(String descripcionParcial) throws SQLException {
        List<TareaMantenimiento> lista = new ArrayList<>();
        String sql = "SELECT * FROM TareasMantenimiento WHERE descripcion LIKE ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, "%" + descripcionParcial + "%");
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    TareaMantenimiento tarea = new TareaMantenimiento(
                        rs.getInt("idTarea"),
                        rs.getInt("idEquipo"),
                        rs.getString("descripcion"),
                        rs.getDate("fecha"),
                        rs.getString("tecnico"),
                        rs.getString("tipoMantenimiento"),
                        rs.getString("resultado"),
                        rs.getString("observaciones"),
                        rs.getDouble("coste")
                    );
                    lista.add(tarea);
                }
            }
        }
        return lista;
    }

    public void eliminarTarea(int idTarea) throws SQLException {
        String sql = "DELETE FROM TareasMantenimiento WHERE idTarea = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idTarea);
            stmt.executeUpdate();
        }
    }
    public List<TareaMantenimiento> obtenerTodasLasTareas() throws SQLException {
    List<TareaMantenimiento> lista = new ArrayList<>();
    String sql = "SELECT * FROM TareasMantenimiento ORDER BY fecha DESC";
    try (Connection conn = DatabaseConnection.getConnection();
         PreparedStatement stmt = conn.prepareStatement(sql);
         ResultSet rs = stmt.executeQuery()) {
        while (rs.next()) {
            TareaMantenimiento tarea = new TareaMantenimiento(
                rs.getInt("idTarea"),
                rs.getInt("idEquipo"),
                rs.getString("descripcion"),
                rs.getDate("fecha"),
                rs.getString("tecnico"),
                rs.getString("tipoMantenimiento"),
                rs.getString("resultado"),
                rs.getString("observaciones"),
                rs.getDouble("coste")
            );
            lista.add(tarea);
        }
    }
    return lista;
}
public void actualizarTarea(TareaMantenimiento tarea) throws SQLException {
    String sql = "UPDATE TareasMantenimiento SET idEquipo=?, descripcion=?, fecha=?, tecnico=?, tipoMantenimiento=?, resultado=?, observaciones=?, coste=? WHERE idTarea=?";
    try (Connection conn = DatabaseConnection.getConnection();
         PreparedStatement stmt = conn.prepareStatement(sql)) {
        stmt.setInt(1, tarea.getIdEquipo());
        stmt.setString(2, tarea.getDescripcion());
        stmt.setDate(3, tarea.getFecha());
        stmt.setString(4, tarea.getTecnico());
        stmt.setString(5, tarea.getTipoMantenimiento());
        stmt.setString(6, tarea.getResultado());
        stmt.setString(7, tarea.getObservaciones());
        stmt.setDouble(8, tarea.getCoste());
        stmt.setInt(9, tarea.getIdTarea());
        stmt.executeUpdate();
    }
}

public TareaMantenimiento obtenerTareaPorId(int idTarea) throws SQLException {
    String sql = "SELECT * FROM TareasMantenimiento WHERE idTarea = ?";
    try (Connection conn = DatabaseConnection.getConnection();
         PreparedStatement stmt = conn.prepareStatement(sql)) {
        stmt.setInt(1, idTarea);
        try (ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) {
                return new TareaMantenimiento(
                    rs.getInt("idTarea"),
                    rs.getInt("idEquipo"),
                    rs.getString("descripcion"),
                    rs.getDate("fecha"),
                    rs.getString("tecnico"),
                    rs.getString("tipoMantenimiento"),
                    rs.getString("resultado"),
                    rs.getString("observaciones"),
                    rs.getDouble("coste")
                );
            }
        }
    }
    return null;
}

}
