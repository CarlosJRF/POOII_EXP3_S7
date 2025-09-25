/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cinemagenta.view;

import cinemagenta.util.ConexionBD;
import java.util.Scanner;

/**
 *
 * @author Lenovo
 */
public class MenuPrincipal {
    
    private static void mostrarMenu() {
        int opcion = -1;
        while (opcion != 0) {
            System.out.println("\n--- MENÚ ---");
            System.out.println("1. Agregar Película");
            System.out.println("2. Listar Películas");
            System.out.println("3. Buscar Película por ID");
            System.out.println("4. Actualizar Película");
            System.out.println("5. Eliminar Película");
            System.out.println("0. Salir");
            System.out.print("Seleccione una opción: ");
            
            try {
                opcion = Integer.parseInt(scanner.nextLine());
                switch (opcion) {
                    case 1:
                        agregarPelicula();
                        break;
                    case 2:
                        listarPeliculas();
                        break;
                    case 3:
                        buscarPelicula();
                        break;
                    case 4:
                        actualizarPelicula();
                        break;
                    case 5:
                        eliminarPelicula();
                        break;
                    case 0:
                        System.out.println("Saliendo del programa...");
                        ConexionBD.closeConnection();
                        break;
                    default:
                        System.out.println("Opción no válida. Intente de nuevo.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Entrada inválida. Por favor, ingrese un número.");
            }
        }
    }
    
}
