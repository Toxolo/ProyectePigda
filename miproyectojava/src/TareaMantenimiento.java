import java.sql.Date;

public class TareaMantenimiento {
    private int idTarea;
    private int idEquipo;
    private String descripcion;
    private Date fecha;
    private String tecnico;
    private String tipoMantenimiento;
    private String resultado;
    private String observaciones;
    private double coste;

    // Constructor con todos los campos (incluye idTarea para lectura desde DB)
    public TareaMantenimiento(int idTarea, int idEquipo, String descripcion, Date fecha, String tecnico,
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

    // Constructor sin idTarea (para inserción)
    public TareaMantenimiento(int idEquipo, String descripcion, Date fecha, String tecnico,
                              String tipoMantenimiento, String resultado, String observaciones, double coste) {
        this.idEquipo = idEquipo;
        this.descripcion = descripcion;
        this.fecha = fecha;
        this.tecnico = tecnico;
        this.tipoMantenimiento = tipoMantenimiento;
        this.resultado = resultado;
        this.observaciones = observaciones;
        this.coste = coste;
    }

    // Getters y setters
    public int getIdTarea() {
        return idTarea;
    }

    public void setIdTarea(int idTarea) {
        this.idTarea = idTarea;
    }

    public int getIdEquipo() {
        return idEquipo;
    }

    public void setIdEquipo(int idEquipo) {
        this.idEquipo = idEquipo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getTecnico() {
        return tecnico;
    }

    public void setTecnico(String tecnico) {
        this.tecnico = tecnico;
    }

    public String getTipoMantenimiento() {
        return tipoMantenimiento;
    }

    public void setTipoMantenimiento(String tipoMantenimiento) {
        this.tipoMantenimiento = tipoMantenimiento;
    }

    public String getResultado() {
        return resultado;
    }

    public void setResultado(String resultado) {
        this.resultado = resultado;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public double getCoste() {
        return coste;
    }

    public void setCoste(double coste) {
        this.coste = coste;
    }
}
