package cinemagenta.main;

import javax.swing.SwingUtilities;

import cinemagenta.controller.CineController;
import cinemagenta.persistence.PeliculaDAO;
import cinemagenta.view.MenuPrincipal;
import cinemagenta.view.VentanaPrincipal;

public class Main {
    public static void main(String[] args) {
        // 1. Crear las instancias de cada capa.
        PeliculaDAO dao = new PeliculaDAO();
        SwingUtilities.invokeLater(() -> {
            new VentanaPrincipal().setVisible(true);
        });
        MenuPrincipal view = new MenuPrincipal();
        CineController controller = new CineController(view, dao);

        // 2. Iniciar la aplicación a través del controlador.
        controller.iniciar();
    }
}