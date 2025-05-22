public class TareaMantenimiento {
    private int idTarea;
    private int idEquipo;
    private String descripcion;
    private java.sql.Date fecha;
    private String tecnico;
    private String tipoMantenimiento;
    private String resultado;
    private String observaciones;
    private double coste;

    // Constructor
    public TareaMantenimiento(int idTarea, int idEquipo, String descripcion, java.sql.Date fecha, String tecnico,
                              String tipoMantenimiento, String resultado, String observaciones, double coste) {
        this.idTarea = idTarea;
        this.idEquipo = idEquipo;
        this.descripcion = descripcion;
        this.fecha = fecha;
        this.tecnico = tecnico;
        this.tipoMantenimiento = tipoMantenimiento;
        this.resultado = resultado;
        this.observaciones = observaciones;
        this.coste = coste;
    }

    // Getters y Setters
    // ... (implementación completa omitida por claridad, incluir todos los campos)
}
