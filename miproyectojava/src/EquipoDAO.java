import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EquipoDAO {

    public void insertarEquipo(Equipo equipo) throws SQLException {
        String sql = "INSERT INTO Equipos (nombreEquipo, tipo, marca, modelo, numeroSerie, ubicacion, responsable, fechaAdquisicion, estado) "
                   + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, equipo.getNombreEquipo());
            stmt.setString(2, equipo.getTipo());
            stmt.setString(3, equipo.getMarca());
            stmt.setString(4, equipo.getModelo());
            stmt.setString(5, equipo.getNumeroSerie());
            stmt.setString(6, equipo.getUbicacion());
            stmt.setString(7, equipo.getResponsable());
            stmt.setDate(8, equipo.getFechaAdquisicion());
            stmt.setString(9, equipo.getEstado());
            stmt.executeUpdate();
        }
    }

    public List<Equipo> obtenerEquipos() throws SQLException {
        List<Equipo> lista = new ArrayList<>();
        String sql = "SELECT * FROM Equipos";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Equipo equipo = new Equipo(
                    rs.getInt("idEquipo"),
                    rs.getString("nombreEquipo"),
                    rs.getString("tipo"),
                    rs.getString("marca"),
                    rs.getString("modelo"),
                    rs.getString("numeroSerie"),
                    rs.getString("ubicacion"),
                    rs.getString("responsable"),
                    rs.getDate("fechaAdquisicion"),
                    rs.getString("estado")
                );
                lista.add(equipo);
            }
        }
        return lista;
    }

    public List<Equipo> buscarEquiposPorNombre(String nombreParcial) throws SQLException {
        List<Equipo> lista = new ArrayList<>();
        String sql = "SELECT * FROM Equipos WHERE nombreEquipo LIKE ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, "%" + nombreParcial + "%");
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Equipo equipo = new Equipo(
                        rs.getInt("idEquipo"),
                        rs.getString("nombreEquipo"),
                        rs.getString("tipo"),
                        rs.getString("marca"),
                        rs.getString("modelo"),
                        rs.getString("numeroSerie"),
                        rs.getString("ubicacion"),
                        rs.getString("responsable"),
                        rs.getDate("fechaAdquisicion"),
                        rs.getString("estado")
                    );
                    lista.add(equipo);
                }
            }
        }
        return lista;
    }

    public void eliminarEquipo(int id) throws SQLException {
        String sql = "DELETE FROM Equipos WHERE idEquipo = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }
    public boolean existeNombreEquipo(String nombre) throws SQLException {
    String sql = "SELECT COUNT(*) FROM Equipos WHERE nombreEquipo = ?";
    try (Connection conn = DatabaseConnection.getConnection();
         PreparedStatement stmt = conn.prepareStatement(sql)) {
        stmt.setString(1, nombre);
        try (ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        }
    }
    return false;
}
    public Equipo obtenerEquipoPorId(int idEquipo) throws SQLException {
    String sql = "SELECT * FROM Equipos WHERE idEquipo = ?";
    try (Connection conn = DatabaseConnection.getConnection();
         PreparedStatement stmt = conn.prepareStatement(sql)) {
        stmt.setInt(1, idEquipo);
        try (ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) {
                return new Equipo(
                    rs.getInt("idEquipo"),
                    rs.getString("nombreEquipo"),
                    rs.getString("tipo"),
                    rs.getString("marca"),
                    rs.getString("modelo"),
                    rs.getString("numeroSerie"),
                    rs.getString("ubicacion"),
                    rs.getString("responsable"),
                    rs.getDate("fechaAdquisicion"),
                    rs.getString("estado")
                );
            }
        }
    }
    return null;
}
public void actualizarEquipo(Equipo equipo) throws SQLException {
    String sql = "UPDATE Equipos SET nombreEquipo=?, tipo=?, marca=?, modelo=?, numeroSerie=?, ubicacion=?, responsable=?, fechaAdquisicion=?, estado=? WHERE idEquipo=?";
    try (Connection conn = DatabaseConnection.getConnection();
         PreparedStatement stmt = conn.prepareStatement(sql)) {
        stmt.setString(1, equipo.getNombreEquipo());
        stmt.setString(2, equipo.getTipo());
        stmt.setString(3, equipo.getMarca());
        stmt.setString(4, equipo.getModelo());
        stmt.setString(5, equipo.getNumeroSerie());
        stmt.setString(6, equipo.getUbicacion());
        stmt.setString(7, equipo.getResponsable());
        stmt.setDate(8, equipo.getFechaAdquisicion());
        stmt.setString(9, equipo.getEstado());
        stmt.setInt(10, equipo.getIdEquipo());
        stmt.executeUpdate();
    }
}


}
