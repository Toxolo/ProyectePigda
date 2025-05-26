import java.sql.Date;
import java.util.List;
import java.util.Scanner;

public class Main {
    private static Scanner sc = new Scanner(System.in);
    private static EquipoDAO equipoDAO = new EquipoDAO();
    private static TareaMantenimientoDAO tareaDAO = new TareaMantenimientoDAO();

    public static void main(String[] args) {
        if (!probarConexion()) {
            System.out.println("No se pudo establecer conexión con la base de datos. Saliendo...");
            return;
        }

        while (true) {
            mostrarMenu();
            int opcion = leerEntero("Elige una opción: ");
            sc.nextLine();

            switch (opcion) {
                case 1:
                    registrarEquipo();
                    break;
                case 2:
                    mostrarEquipos();
                    break;
                case 3:
                    registrarTareaMantenimiento();
                    break;
                case 4:
                    mostrarTareasDeEquipo();
                    break;
                case 5:
                    eliminarEquipo();
                    break;
                case 6:
                    eliminarTareaMantenimiento();
                    break;
                case 7:
                    mostrarTodasLasTareas();
                    break;
                case 8:
                    modificarEquipo();
                    break;
                case 9:
                    modificarTarea();
                    break;

                case 0:
                    System.out.println("Saliendo del sistema...");
                    sc.close();
                    return;
                default:
                    System.out.println("Opción no válida.");
                    break;
            }
        }
    }

    private static void mostrarMenu() {
        System.out.println("\n--- MENÚ DE INVENTARIO ---");
        System.out.println("1. Registrar nuevo equipo");
        System.out.println("2. Mostrar todos los equipos");
        System.out.println("3. Registrar tarea de mantenimiento");
        System.out.println("4. Ver tareas de mantenimiento de un equipo");
        System.out.println("5. Eliminar equipo");
        System.out.println("6. Eliminar tarea de mantenimiento");
        System.out.println("7. Ver todas las tareas de mantenimiento");
        System.out.println("8. Modificar equipo");
    System.out.println("9. Modificar tarea de mantenimiento");


        System.out.println("0. Salir");
    }

   private static void registrarEquipo() {
    try {
        String nombre;
        while (true) {
            nombre = leerTextoObligatorio("Nombre equipo: ");
            if (equipoDAO.existeNombreEquipo(nombre)) {
                System.out.println("Ya existe un equipo con ese nombre. Introduce uno diferente.");
            } else {
                break;
            }
        }

        String tipo = leerTextoObligatorio("Tipo: ");
        String marca = leerTextoObligatorio("Marca: ");
        String modelo = leerTextoObligatorio("Modelo: ");
        String serie = leerTextoObligatorio("Número de serie: ");
        String ubicacion = leerTextoObligatorio("Ubicación: ");
        String responsable = leerTextoObligatorio("Responsable: ");
        Date fecha = leerFechaValida("Fecha adquisición (YYYY-MM-DD): ");
        String estado = leerTextoObligatorio("Estado: ");

        Equipo nuevoEquipo = new Equipo(0, nombre, tipo, marca, modelo, serie, ubicacion, responsable, fecha, estado);
        equipoDAO.insertarEquipo(nuevoEquipo);
        System.out.println("Equipo registrado correctamente.");
    } catch (Exception e) {
        System.out.println("Error al registrar equipo: " + e.getMessage());
    }
}


    private static void mostrarEquipos() {
        try {
            List<Equipo> equipos = equipoDAO.obtenerEquipos();
            System.out.println("\n--- LISTA DE EQUIPOS ---");
            for (Equipo eq : equipos) {
                System.out.println(eq.getIdEquipo() + ": " + eq.getNombreEquipo() + " - " + eq.getEstado());
            }
        } catch (Exception e) {
            System.out.println("Error al mostrar equipos: " + e.getMessage());
        }
    }

    private static void registrarTareaMantenimiento() {
    try {
        int idEq;
        Equipo equipo;

        while (true) {
            idEq = leerEntero("ID del equipo: ");
            sc.nextLine();
            equipo = equipoDAO.obtenerEquipoPorId(idEq);
            if (equipo == null) {
                System.out.println("El ID ingresado no corresponde a ningún equipo existente.");
            } else {
                System.out.println("Equipo encontrado:");
                System.out.println("Nombre: " + equipo.getNombreEquipo());
                System.out.println("Ubicación: " + equipo.getUbicacion());
                System.out.println("Responsable: " + equipo.getResponsable());
                System.out.println("Estado: " + equipo.getEstado());
                if (!confirmarAccion("¿Deseas continuar registrando la tarea para este equipo? (s/n): ")) {
                    System.out.println("Operación cancelada.");
                    return;
                }
                break;
            }
        }

        String desc = leerTextoObligatorio("Descripción: ");
        Date fechaTarea = leerFechaValida("Fecha (YYYY-MM-DD): ");
        String tecnico = leerTextoObligatorio("Técnico: ");
        String tipoMant = leerTextoObligatorio("Tipo mantenimiento: ");
        String resultado = leerTextoObligatorio("Resultado: ");
        String obs = leerTextoObligatorio("Observaciones: ");
        double coste = leerDecimal("Coste: ");
        sc.nextLine();

        TareaMantenimiento tarea = new TareaMantenimiento(0, idEq, desc, fechaTarea, tecnico, tipoMant, resultado, obs, coste);
        tareaDAO.insertarTarea(tarea);
        System.out.println("Tarea registrada correctamente.");
    } catch (Exception e) {
        System.out.println("Error al registrar tarea: " + e.getMessage());
    }
}


    private static void mostrarTareasDeEquipo() {
        try {
            int idBuscado = leerEntero("ID del equipo: ");
            sc.nextLine();
            List<TareaMantenimiento> tareas = tareaDAO.obtenerTareasPorEquipo(idBuscado);
            System.out.println("\n--- TAREAS DE MANTENIMIENTO ---");
            for (TareaMantenimiento t : tareas) {
                System.out.println(t.getFecha() + ": " + t.getDescripcion() + " - " + t.getTecnico());
            }
        } catch (Exception e) {
            System.out.println("Error al mostrar tareas: " + e.getMessage());
        }
    }

    private static void eliminarEquipo() {
        try {
            System.out.println("¿Deseas buscar por ID o por nombre?");
            System.out.print("Escribe 'id' o 'nombre': ");
            String metodo = sc.nextLine().trim().toLowerCase();

            int id = -1;

            if (metodo.equals("id")) {
                id = leerEntero("Ingrese el ID del equipo a eliminar: ");
                sc.nextLine();
            } else if (metodo.equals("nombre")) {
                String filtro = leerTextoObligatorio("Ingrese parte del nombre del equipo: ");
                List<Equipo> resultados = equipoDAO.buscarEquiposPorNombre(filtro);

                if (resultados.isEmpty()) {
                    System.out.println("No se encontraron equipos con ese nombre.");
                    return;
                }

                System.out.println("\nResultados encontrados:");
                for (Equipo eq : resultados) {
                    System.out.println("ID: " + eq.getIdEquipo() + " - " + eq.getNombreEquipo() + " (" + eq.getEstado() + ")");
                }

                id = leerEntero("Escribe el ID del equipo que deseas eliminar: ");
                sc.nextLine();
            } else {
                System.out.println("Opción inválida.");
                return;
            }

            if (confirmarAccion("¿Está seguro que desea eliminar el equipo con ID " + id + "? (s/n): ")) {
                equipoDAO.eliminarEquipo(id);
                System.out.println("Equipo eliminado correctamente.");
            } else {
                System.out.println("Operación cancelada.");
            }

        } catch (Exception e) {
            System.out.println("Error al eliminar equipo: " + e.getMessage());
        }
    }

    private static void eliminarTareaMantenimiento() {
        try {
            System.out.println("¿Deseas buscar por ID o por descripción?");
            System.out.print("Escribe 'id' o 'descripcion': ");
            String metodo = sc.nextLine().trim().toLowerCase();

            int id = -1;

            if (metodo.equals("id")) {
                id = leerEntero("Ingrese el ID de la tarea a eliminar: ");
                sc.nextLine();
            } else if (metodo.equals("descripcion")) {
                String filtro = leerTextoObligatorio("Ingrese parte de la descripción: ");
                List<TareaMantenimiento> resultados = tareaDAO.buscarTareasPorDescripcion(filtro);

                if (resultados.isEmpty()) {
                    System.out.println("No se encontraron tareas con esa descripción.");
                    return;
                }

                System.out.println("\nResultados encontrados:");
                for (TareaMantenimiento t : resultados) {
                    System.out.println("ID: " + t.getIdTarea() + " - " + t.getDescripcion() + " (" + t.getFecha() + ")");
                }

                id = leerEntero("Escribe el ID de la tarea que deseas eliminar: ");
                sc.nextLine();
            } else {
                System.out.println("Opción inválida.");
                return;
            }

            if (confirmarAccion("¿Está seguro que desea eliminar la tarea con ID " + id + "? (s/n): ")) {
                tareaDAO.eliminarTarea(id);
                System.out.println("Tarea eliminada correctamente.");
            } else {
                System.out.println("Operación cancelada.");
            }

        } catch (Exception e) {
            System.out.println("Error al eliminar tarea: " + e.getMessage());
        }
    }

    private static boolean probarConexion() {
        System.out.print("Verificando conexión con la base de datos... ");
        try {
            DatabaseConnection.getConnection().close();
            System.out.println("Conexión exitosa.");
            return true;
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            return false;
        }
    }

    private static int leerEntero(String mensaje) {
        System.out.print(mensaje);
        while (!sc.hasNextInt()) {
            System.out.print("Por favor ingresa un número entero válido: ");
            sc.next();
        }
        return sc.nextInt();
    }

    private static double leerDecimal(String mensaje) {
        System.out.print(mensaje);
        while (!sc.hasNextDouble()) {
            System.out.print("Por favor ingresa un número decimal válido: ");
            sc.next();
        }
        return sc.nextDouble();
    }

    private static Date leerFechaValida(String mensaje) {
        Date fechaActual = new Date(System.currentTimeMillis());
        while (true) {
            try {
                System.out.print(mensaje);
                String input = sc.nextLine();
                Date fechaIngresada = Date.valueOf(input);
                if (fechaIngresada.after(fechaActual)) {
                    System.out.println("La fecha no puede ser mayor que la actual. Intenta de nuevo.");
                } else {
                    return fechaIngresada;
                }
            } catch (IllegalArgumentException e) {
                System.out.println("Formato de fecha inválido. Usa YYYY-MM-DD.");
            }
        }
    }

    private static String leerTextoObligatorio(String mensaje) {
        String entrada;
        do {
            System.out.print(mensaje);
            entrada = sc.nextLine().trim();
            if (entrada.isEmpty()) {
                System.out.println("Este campo no puede estar en blanco.");
            }
        } while (entrada.isEmpty());
        return entrada;
    }

    private static boolean confirmarAccion(String mensaje) {
        System.out.print(mensaje);
        String respuesta = sc.nextLine().trim().toLowerCase();
        return respuesta.equals("s") || respuesta.equals("si");
    }
    private static void mostrarTodasLasTareas() {
    try {
        List<TareaMantenimiento> tareas = tareaDAO.obtenerTodasLasTareas();
        if (tareas.isEmpty()) {
            System.out.println("No hay tareas registradas.");
            return;
        }

        System.out.println("\n--- TODAS LAS TAREAS DE MANTENIMIENTO ---");
        for (TareaMantenimiento t : tareas) {
            System.out.println("ID: " + t.getIdTarea() + 
                               " | Equipo ID: " + t.getIdEquipo() + 
                               " | Fecha: " + t.getFecha() + 
                               " | Técnico: " + t.getTecnico() + 
                               " | Descripción: " + t.getDescripcion());
        }
    } catch (Exception e) {
        System.out.println("Error al mostrar tareas: " + e.getMessage());
    }
}

public static void modificarEquipo() {
    try {
        System.out.print("Buscar equipo por 'id' o 'nombre': ");
        String metodo = sc.nextLine().trim().toLowerCase();
        Equipo equipo = null;

        if (metodo.equals("id")) {
            int id = leerEntero("ID del equipo: ");
            sc.nextLine();
            equipo = equipoDAO.obtenerEquipoPorId(id);
        } else if (metodo.equals("nombre")) {
            String filtro = leerTextoObligatorio("Parte del nombre del equipo: ");
            List<Equipo> resultados = equipoDAO.buscarEquiposPorNombre(filtro);
            if (resultados.isEmpty()) {
                System.out.println("No se encontraron equipos.");
                return;
            }

            System.out.println("\nResultados encontrados:");
            for (Equipo eq : resultados) {
                System.out.println("ID: " + eq.getIdEquipo() + " | Nombre: " + eq.getNombreEquipo() + " | Ubicación: " + eq.getUbicacion());
            }

            int idSel = leerEntero("ID del equipo que deseas modificar: ");
            sc.nextLine();
            equipo = equipoDAO.obtenerEquipoPorId(idSel);
        } else {
            System.out.println("Método de búsqueda inválido.");
            return;
        }

        if (equipo == null) {
            System.out.println("Equipo no encontrado.");
            return;
        }

        System.out.println("Campos modificables: nombre, tipo, marca, modelo, serie, ubicacion, responsable, fecha, estado");
        String campo = leerTextoObligatorio("¿Qué campo quieres modificar?: ").toLowerCase();

        switch (campo) {
            case "nombre":
                String nuevoNombre;
                while (true) {
                    nuevoNombre = leerTextoObligatorio("Nuevo nombre: ");
                    if (!nuevoNombre.equalsIgnoreCase(equipo.getNombreEquipo())
                        && equipoDAO.existeNombreEquipo(nuevoNombre)) {
                        System.out.println("Ya existe un equipo con ese nombre.");
                    } else {
                        break;
                    }
                }
                equipo.setNombreEquipo(nuevoNombre);
                break;
            case "tipo":
                equipo.setTipo(leerTextoObligatorio("Nuevo tipo: "));
                break;
            case "marca":
                equipo.setMarca(leerTextoObligatorio("Nueva marca: "));
                break;
            case "modelo":
                equipo.setModelo(leerTextoObligatorio("Nuevo modelo: "));
                break;
            case "serie":
                equipo.setNumeroSerie(leerTextoObligatorio("Nuevo número de serie: "));
                break;
            case "ubicacion":
                equipo.setUbicacion(leerTextoObligatorio("Nueva ubicación: "));
                break;
            case "responsable":
                equipo.setResponsable(leerTextoObligatorio("Nuevo responsable: "));
                break;
            case "fecha":
                equipo.setFechaAdquisicion(leerFechaValida("Nueva fecha (YYYY-MM-DD): "));
                break;
            case "estado":
                equipo.setEstado(leerTextoObligatorio("Nuevo estado: "));
                break;
            default:
                System.out.println("Campo no válido.");
                return;
        }

        equipoDAO.actualizarEquipo(equipo);
        System.out.println("Equipo actualizado correctamente.");

    } catch (Exception e) {
        System.out.println("Error al modificar equipo: " + e.getMessage());
    }
}



public static void modificarTarea() {
    try {
        int id = leerEntero("ID de la tarea a modificar: ");
        sc.nextLine();
        TareaMantenimiento tarea = tareaDAO.obtenerTareaPorId(id);
        if (tarea == null) {
            System.out.println("No se encontró la tarea.");
            return;
        }

        System.out.println("Campos modificables: equipo, descripcion, fecha, tecnico, tipo, resultado, observaciones, coste");
        String campo = leerTextoObligatorio("¿Qué campo quieres modificar?: ").toLowerCase();

        switch (campo) {
            case "equipo":
                int nuevoId = leerEntero("Nuevo ID de equipo: ");
                sc.nextLine();
                if (equipoDAO.obtenerEquipoPorId(nuevoId) == null) {
                    System.out.println("ID no válido.");
                    return;
                }
                tarea.setIdEquipo(nuevoId);
                break;
            case "descripcion":
                tarea.setDescripcion(leerTextoObligatorio("Nueva descripción: "));
                break;
            case "fecha":
                tarea.setFecha(leerFechaValida("Nueva fecha (YYYY-MM-DD): "));
                break;
            case "tecnico":
                tarea.setTecnico(leerTextoObligatorio("Nuevo técnico: "));
                break;
            case "tipo":
                tarea.setTipoMantenimiento(leerTextoObligatorio("Nuevo tipo de mantenimiento: "));
                break;
            case "resultado":
                tarea.setResultado(leerTextoObligatorio("Nuevo resultado: "));
                break;
            case "observaciones":
                tarea.setObservaciones(leerTextoObligatorio("Nuevas observaciones: "));
                break;
            case "coste":
                tarea.setCoste(leerDecimal("Nuevo coste: "));
                sc.nextLine();
                break;
            default:
                System.out.println("Campo no válido.");
                return;
        }

        tareaDAO.actualizarTarea(tarea);
        System.out.println("Tarea actualizada correctamente.");

    } catch (Exception e) {
        System.out.println("Error al modificar tarea: " + e.getMessage());
    }
}



}
