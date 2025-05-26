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
        System.out.println("0. Salir");
    }

    private static void registrarEquipo() {
        try {
            String nombre = leerTextoObligatorio("Nombre equipo: ");
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
            int idEq = leerEntero("ID del equipo: ");
            sc.nextLine();
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
}
