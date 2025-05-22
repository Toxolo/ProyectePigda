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
            public int getIdEquipo() {
            return idEquipo;
            }
            public void setIdEquipo(int idEquipo) {
            this.idEquipo = idEquipo;
            }

            public String getNombreEquipo() {
            return nombreEquipo;
            }
            public void setNombreEquipo(String nombreEquipo) {
            this.nombreEquipo = nombreEquipo;
            }

            public String getTipo() {
            return tipo;
            }
            public void setTipo(String tipo) {
            this.tipo = tipo;
            }

            public String getMarca() {
            return marca;
            }
            public void setMarca(String marca) {
            this.marca = marca;
            }

            public String getModelo() {
            return modelo;
            }
            public void setModelo(String modelo) {
            this.modelo = modelo;
            }

            public String getNumeroSerie() {
            return numeroSerie;
            }
            public void setNumeroSerie(String numeroSerie) {
            this.numeroSerie = numeroSerie;
            }

            public String getUbicacion() {
            return ubicacion;
            }
            public void setUbicacion(String ubicacion) {
            this.ubicacion = ubicacion;
            }

            public String getResponsable() {
            return responsable;
            }
            public void setResponsable(String responsable) {
            this.responsable = responsable;
            }

            public java.sql.Date getFechaAdquisicion() {
            return fechaAdquisicion;
            }
            public void setFechaAdquisicion(java.sql.Date fechaAdquisicion) {
            this.fechaAdquisicion = fechaAdquisicion;
            }

            public String getEstado() {
            return estado;
            }
            public void setEstado(String estado) {
            this.estado = estado;
            }
}

