package cinemagenta.main;

import cinemagenta.controller.CineController;
import cinemagenta.persistence.PeliculaDAO;
import cinemagenta.view.MenuPrincipal;

public class Main {
    public static void main(String[] args) {
        // 1. Crear las instancias de cada capa.
        PeliculaDAO dao = new PeliculaDAO();
        MenuPrincipal view = new MenuPrincipal();
        CineController controller = new CineController(view, dao);

        // 2. Iniciar la aplicación a través del controlador.
        controller.iniciar();
    }
}