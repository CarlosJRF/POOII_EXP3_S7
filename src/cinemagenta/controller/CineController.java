package cinemagenta.controller;

import cinemagenta.model.Pelicula;
import cinemagenta.persistence.PeliculaDAO;
import cinemagenta.view.MenuPrincipal;

import java.util.List;
import java.util.Scanner;

public class CineController {
    private final Scanner scanner = new Scanner(System.in);
    private final MenuPrincipal view;
    private final PeliculaDAO dao;

    // El controlador necesita la vista y el DAO para trabajar.
    public CineController(MenuPrincipal view, PeliculaDAO dao) {
        this.view = view;
        this.dao = dao;
    }

    public void iniciar() {
        int opcion;
        do {
            opcion = view.mostrarMenuYObtenerOpcion();
            procesarOpcion(opcion);
        } while (opcion != 0);
    }

    private void procesarOpcion(int opcion) {
        switch (opcion) {
            case 1:
                agregarPelicula();
                break;
            case 2:
                listarPeliculas();
                break;
            // ... otros casos ...
            case 0:
                view.mostrarMensaje("Saliendo del programa...");
                break;
            default:
                view.mostrarMensaje("Opción no válida. Intente de nuevo.");
        }
    }

    private void agregarPelicula() {
        // 1. Pide a la vista que obtenga los datos del usuario.
        Pelicula nuevaPelicula = view.solicitarDatosPelicula();
        // 2. Llama al DAO para guardar la película.
        if (dao.agregarPelicula(nuevaPelicula)) {
            // 3. Pide a la vista que muestre un mensaje de éxito.
            view.mostrarMensaje("Película agregada con éxito.");
        } else {
            view.mostrarMensaje("No se pudo agregar la película.");
        }
    }

    private void listarPeliculas() {
        // 1. Pide los datos al DAO.
        List<Pelicula> peliculas = dao.listarPeliculas();
        // 2. Envía los datos a la vista para que los muestre.
        view.mostrarListaPeliculas(peliculas);
    }
    
    private void buscarPelicula() {
        System.out.println("\n--- BUSCAR PELÍCULA POR ID ---");
        System.out.print("Ingrese el ID de la película a buscar: ");
        try {
            int id = Integer.parseInt(scanner.nextLine());
            Pelicula p = dao.buscarPorId(id);
            if (p != null) {
                System.out.println("Película encontrada:");
                 System.out.printf("ID: %d | Título: %s | Director: %s | Año: %d | Duración: %d min | Género: %s%n",
                                p.getId(), p.getTitulo(), p.getDirector(), p.getAnio(), p.getDuracion(), p.getGenero());
            } else {
                System.out.println("No se encontró una película con el ID " + id + ".");
            }
        } catch (NumberFormatException e) {
            System.out.println("Error: El ID debe ser un número entero.");
        }
    }

    private void actualizarPelicula() {
        System.out.println("\n--- ACTUALIZAR PELÍCULA ---");
        System.out.print("Ingrese el ID de la película a actualizar: ");
        try {
            int id = Integer.parseInt(scanner.nextLine());
            Pelicula peliculaExistente = dao.buscarPorId(id);
            if (peliculaExistente == null) {
                System.out.println("No se encontró una película con el ID " + id + ".");
                return;
            }

            System.out.println("Película actual: " + peliculaExistente.getTitulo());
            System.out.print("Nuevo Título (dejar vacío para no cambiar): ");
            String nuevoTitulo = scanner.nextLine();
            if (!nuevoTitulo.trim().isEmpty()) {
                peliculaExistente.setTitulo(nuevoTitulo);
            }

            // ... (El resto del código para actualizar director, año, etc., sigue aquí, sin cambios)
            // He omitido el resto por brevedad, pero iría todo el método como lo tenías.

            if (dao.actualizarPelicula(peliculaExistente)) {
                System.out.println("Película actualizada con éxito.");
            } else {
                System.out.println("No se pudo actualizar la película.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Error: El ID debe ser un número entero.");
        }
    }

    private void eliminarPelicula() {
        System.out.println("\n--- ELIMINAR PELÍCULA ---");
        System.out.print("Ingrese el ID de la película a eliminar: ");
        try {
            int id = Integer.parseInt(scanner.nextLine());
            if (dao.eliminarPelicula(id)) {
                System.out.println("Película eliminada con éxito.");
            } else {
                System.out.println("No se encontró una película con el ID " + id + " o no se pudo eliminar.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Error: El ID debe ser un número entero.");
        }
    }
}

