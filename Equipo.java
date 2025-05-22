public class Equipo {
    private int idEquipo;
    private String nombreEquipo;
    private String tipo;
    private String marca;
    private String modelo;
    private String numeroSerie;
    private String ubicacion;
    private String responsable;
    private java.sql.Date fechaAdquisicion;
    private String estado;

    // Constructor
    public Equipo(int idEquipo, String nombreEquipo, String tipo, String marca, String modelo,
                  String numeroSerie, String ubicacion, String responsable, java.sql.Date fechaAdquisicion, String estado) {
        this.idEquipo = idEquipo;
        this.nombreEquipo = nombreEquipo;
        this.tipo = tipo;
        this.marca = marca;
        this.modelo = modelo;
        this.numeroSerie = numeroSerie;
        this.ubicacion = ubicacion;
        this.responsable = responsable;
        this.fechaAdquisicion = fechaAdquisicion;
        this.estado = estado;
    }

    // Getters y Setters
    // ... (implementación completa omitida por claridad, incluir todos los campos)
}

