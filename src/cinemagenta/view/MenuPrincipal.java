package cinemagenta.view;

import cinemagenta.model.Pelicula;
import java.util.List;
import java.util.Scanner;

public class MenuPrincipal {
    private final Scanner scanner = new Scanner(System.in);

    public int mostrarMenuYObtenerOpcion() {
        System.out.println("\n--- MENÚ DE CINE MAGENTA ---");
        System.out.println("1. Agregar Película");
        System.out.println("2. Listar Películas");
        // ...
        System.out.println("0. Salir");
        System.out.print("Seleccione una opción: ");
        try {
            return Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            return -1; // Opción inválida
        }
    }

    public Pelicula solicitarDatosPelicula() {
        System.out.println("\n--- AGREGAR PELÍCULA ---");
        System.out.print("Título: ");
        String titulo = scanner.nextLine();
        System.out.print("Director: ");
        String director = scanner.nextLine();
        System.out.print("Año: ");
        int anio = Integer.parseInt(scanner.nextLine());
        System.out.print("Duración: ");
        int duracion = Integer.parseInt(scanner.nextLine());
        System.out.print("Género: ");
        String genero = scanner.nextLine();
        return new Pelicula(titulo, director, anio, duracion, genero);
    }

    public void mostrarListaPeliculas(List<Pelicula> peliculas) {
        System.out.println("\n--- LISTADO DE PELÍCULAS ---");
        if (peliculas.isEmpty()) {
            System.out.println("No hay películas registradas.");
        } else {
            for (Pelicula p : peliculas) {
                System.out.printf("ID: %d | Título: %s | Director: %s%n", p.getId(), p.getTitulo(), p.getDirector());
            }
        }
    }

    public void mostrarMensaje(String mensaje) {
        System.out.println(mensaje);
    }
}
