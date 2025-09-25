package cinemagenta.main;

import cinemagenta.persistence.PeliculaDAO;
import cinemagenta.model.Pelicula;
import cinemagenta.view.MenuPrincipal;

import java.util.List;
import java.util.Scanner;

public class Main {

    private static final Scanner scanner = new Scanner(System.in);
    private static final PeliculaDAO peliculaDAO = new PeliculaDAO();
    private static final List<String> GENEROS_VALIDOS = List.of("Comedia", "Drama", "Acción", "Terror", "Ciencia Ficción", "Romance");
    MenuPrincipal MP = new MenuPrincipal();

    public static void main(String[] args) {
        System.out.println("Sistema de Gestión de Cartelera");
        MenuPrincipal;
    }

    

    private static void agregarPelicula() {
        System.out.println("\n--- AGREGAR PELÍCULA ---");
        System.out.print("Título: ");
        String titulo = scanner.nextLine();
        System.out.print("Director: ");
        String director = scanner.nextLine();
        
        int anio, duracion;

        try {
            System.out.print("Año (entero): ");
            anio = Integer.parseInt(scanner.nextLine());
            System.out.print("Duración en minutos (entero): ");
            duracion = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Error: Año y duración deben ser números válidos.");
            return;
        }

        System.out.println("--- GÉNERO ---");
        for (int i = 0; i < GENEROS_VALIDOS.size(); i++) {
            System.out.println((i + 1) + ". " + GENEROS_VALIDOS.get(i));
        }
        System.out.print("Seleccione un número para el género: ");
        
        int generoOpcion;
        try {
            generoOpcion = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Error: La opción debe ser un número.");
            return;
        }
        
        String genero;
        if (generoOpcion > 0 && generoOpcion <= GENEROS_VALIDOS.size()) {
            genero = GENEROS_VALIDOS.get(generoOpcion - 1);
        } else {
            System.out.println("Error: La opción de género no es válida.");
            return;
        }

        if (titulo.isEmpty() || director.isEmpty()) {
            System.out.println("Error: El título y director son obligatorios.");
            return;
        }

        Pelicula nuevaPelicula = new Pelicula(titulo, director, anio, duracion, genero);
        if (peliculaDAO.agregarPelicula(nuevaPelicula)) {
            System.out.println("Película agregada con éxito.");
        } else {
            System.out.println("No se pudo agregar la película. Por favor, revise los datos.");
        }
    }
    
    private static void listarPeliculas() {
        System.out.println("\n--- LISTADO DE PELÍCULAS ---");
        List<Pelicula> peliculas = peliculaDAO.listarPeliculas();
        if (peliculas.isEmpty()) {
            System.out.println("No hay películas registradas.");
        } else {
            for (Pelicula p : peliculas) {
                System.out.println(String.format("ID: %d | Título: %s | Director: %s | Año: %d | Duración: %d min | Género: %s",
                                p.getId(), p.getTitulo(), p.getDirector(), p.getAnio(), p.getDuracion(), p.getGenero()));
            }
        }
    }

    private static void buscarPelicula() {
        System.out.println("\n--- BUSCAR PELÍCULA POR ID ---");
        System.out.print("Ingrese el ID de la película a buscar: ");
        try {
            int id = Integer.parseInt(scanner.nextLine());
            Pelicula p = peliculaDAO.buscarPorId(id);
            if (p != null) {
                System.out.println("Película encontrada:");
                System.out.println(String.format("ID: %d | Título: %s | Director: %s | Año: %d | Duración: %d min | Género: %s",
                                p.getId(), p.getTitulo(), p.getDirector(), p.getAnio(), p.getDuracion(), p.getGenero()));
            } else {
                System.out.println("No se encontró una película con el ID " + id + ".");
            }
        } catch (NumberFormatException e) {
            System.out.println("Error: El ID debe ser un número entero.");
        }
    }

    private static void actualizarPelicula() {
        System.out.println("\n--- ACTUALIZAR PELÍCULA ---");
        System.out.print("Ingrese el ID de la película a actualizar: ");
        try {
            int id = Integer.parseInt(scanner.nextLine());
            Pelicula peliculaExistente = peliculaDAO.buscarPorId(id);
            if (peliculaExistente == null) {
                System.out.println("No se encontró una película con el ID " + id + ".");
                return;
            }

            System.out.println("Película actual: " + peliculaExistente.getTitulo());
            System.out.print("Nuevo Título (dejar vacío para no cambiar): ");
            String nuevoTitulo = scanner.nextLine();
            if (!nuevoTitulo.isEmpty()) {
                peliculaExistente.setTitulo(nuevoTitulo);
            }
            
            System.out.print("Nuevo Director (dejar vacío para no cambiar): ");
            String nuevoDirector = scanner.nextLine();
            if (!nuevoDirector.isEmpty()) {
                peliculaExistente.setDirector(nuevoDirector);
            }

            System.out.print("Nuevo Año (dejar vacío para no cambiar): ");
            String nuevoAnioStr = scanner.nextLine();
            if (!nuevoAnioStr.isEmpty()) {
                try {
                    peliculaExistente.setAnio(Integer.parseInt(nuevoAnioStr));
                } catch (NumberFormatException e) {
                    System.out.println("Error: El año debe ser un número. No se actualizará.");
                }
            }

            System.out.print("Nueva Duración (dejar vacío para no cambiar): ");
            String nuevaDuracionStr = scanner.nextLine();
            if (!nuevaDuracionStr.isEmpty()) {
                try {
                    peliculaExistente.setDuracion(Integer.parseInt(nuevaDuracionStr));
                } catch (NumberFormatException e) {
                    System.out.println("Error: La duración debe ser un número. No se actualizará.");
                }
            }
            
            System.out.println("--- GÉNERO ACTUAL: " + peliculaExistente.getGenero() + " ---");
            System.out.println("Seleccione un nuevo género o deje vacío para no cambiar:");
            for (int i = 0; i < GENEROS_VALIDOS.size(); i++) {
                System.out.println((i + 1) + ". " + GENEROS_VALIDOS.get(i));
            }
            System.out.print("Opción de género: ");
            String nuevoGeneroOpcionStr = scanner.nextLine();
            if (!nuevoGeneroOpcionStr.isEmpty()) {
                try {
                    int nuevoGeneroOpcion = Integer.parseInt(nuevoGeneroOpcionStr);
                    if (nuevoGeneroOpcion > 0 && nuevoGeneroOpcion <= GENEROS_VALIDOS.size()) {
                        peliculaExistente.setGenero(GENEROS_VALIDOS.get(nuevoGeneroOpcion - 1));
                    } else {
                        System.out.println("Error: Opción de género no válida. No se actualizará.");
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Error: La opción de género debe ser un número. No se actualizará.");
                }
            }

            if (peliculaDAO.actualizarPelicula(peliculaExistente)) {
                System.out.println("Película actualizada con éxito.");
            } else {
                System.out.println("No se pudo actualizar la película.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Error: El ID debe ser un número entero.");
        }
    }

    private static void eliminarPelicula() {
        System.out.println("\n--- ELIMINAR PELÍCULA ---");
        System.out.print("Ingrese el ID de la película a eliminar: ");
        try {
            int id = Integer.parseInt(scanner.nextLine());
            if (peliculaDAO.eliminarPelicula(id)) {
                System.out.println("Película eliminada con éxito.");
            } else {
                System.out.println("No se encontró una película con el ID " + id + " o no se pudo eliminar.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Error: El ID debe ser un número entero.");
        }
    }
}