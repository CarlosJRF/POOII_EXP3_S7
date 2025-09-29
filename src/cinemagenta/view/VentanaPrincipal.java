/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cinemagenta.view;



import java.awt.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import org.w3c.dom.events.MouseEvent;

import java.util.List;
import cinemagenta.model.Pelicula;
import cinemagenta.persistence.PeliculaDAO;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;

/**
 * Esta clase es nuestro view, usamos JFrame para crear un panel para que el usuario
 * pueda interactuar con la aplicacion
 * La primera seccion contiene la ventana principal con las dimensiones
 * En la segunda seccion estan incluidos los botones, campos de texto y panel del formulario
 * En la tercera seccion podemos encontrar los listener para las acciones del usuario
 * 
 */

public class VentanaPrincipal extends JFrame {

    private JTextField txtId, txtTitulo, txtDirector, txtAnio, txtDuracion, txtGenero;
    private JButton btnAgregar, btnModificar, btnEliminar, btnListar, btnLimpiar;
    private JTable tablaPeliculas;
    private DefaultTableModel modeloTabla;
    private PeliculaDAO peliculaDAO;
    
    /**
     * Primera seccion:
     * Configuracion de la ventana
     */

    public VentanaPrincipal() {
        // Configuramos la ventana
        setTitle("Administrador de Películas - CineMagenta");
        setSize(800, 600); // Tamaño inicial
        setLocationRelativeTo(null); // Centrar en la pantalla
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Terminar el programa al cerrar
        
        peliculaDAO = new PeliculaDAO();
        //Inicializa los componentes que debe contener la ventana.
        inicializarComponentes();
        agregarListeners();
    }
    
    
    /**
     * Segunda seccion:
     * En esta seccion agregamos el metodo inicializarComponentes()
     * Se crean los campos de textos y los botones, junto con el panel de formulario y se les da un orden
     * dentro del panel
     */

    private void inicializarComponentes() {
        // Usamos un BorderLayout para el layout principal
        setLayout(new BorderLayout());

        // --- PANEL DEL FORMULARIO (Norte) ---
        JPanel panelFormulario = new JPanel(new GridLayout(7, 2, 5, 5)); // 7 filas, 2 columnas, con espaciado
        panelFormulario.setBorder(BorderFactory.createTitledBorder("Datos de la Película"));

        // Campos de texto y etiquetas
        panelFormulario.add(new JLabel("ID:"));
        txtId = new JTextField();
        txtId.setEditable(false); // El ID NO se edita manualmente
        panelFormulario.add(txtId);

        panelFormulario.add(new JLabel("Título:"));
        txtTitulo = new JTextField();
        panelFormulario.add(txtTitulo);

        panelFormulario.add(new JLabel("Director:"));
        txtDirector = new JTextField();
        panelFormulario.add(txtDirector);

        panelFormulario.add(new JLabel("Año:"));
        txtAnio = new JTextField();
        panelFormulario.add(txtAnio);

        panelFormulario.add(new JLabel("Duración (min):"));
        txtDuracion = new JTextField();
        panelFormulario.add(txtDuracion);

        panelFormulario.add(new JLabel("Género:"));
        txtGenero = new JTextField();
        panelFormulario.add(txtGenero);

        // --- PANEL DE BOTONES (Parte del formulario) ---
        btnAgregar = new JButton("Agregar");
        btnModificar = new JButton("Modificar");
        btnEliminar = new JButton("Eliminar");
        btnListar = new JButton("Listar");
        btnLimpiar = new JButton("Limpiar");

        panelFormulario.add(btnAgregar);
        panelFormulario.add(btnModificar);
        panelFormulario.add(btnEliminar);
        panelFormulario.add(btnListar);
        panelFormulario.add(btnLimpiar);

        add(panelFormulario, BorderLayout.NORTH);

        // --- PANEL DE LA TABLA (Centro) ---
        String[] columnas = {"ID", "Título", "Director", "Año", "Duración", "Género"};
        modeloTabla = new DefaultTableModel(columnas, 0);
        tablaPeliculas = new JTable(modeloTabla);
        
        // Agregamos la tabla a un JScrollPane para que tenga barras de desplazamiento
        add(new JScrollPane(tablaPeliculas), BorderLayout.CENTER);
    }
    

    private void agregarListeners() {

        btnListar.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                listarPeliculas();
            }
        });

        btnModificar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                actualizarPelicula();
            }
        });
        
        btnLimpiar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                limpiarCampos();
            }
        });
        
        btnAgregar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                agregarPelicula();
            }
        });
        
        btnEliminar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                eliminarPelicula();
            }
        });

        tablaPeliculas.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                int filaSeleccionada = tablaPeliculas.getSelectedRow();
                if (filaSeleccionada >= 0) {
                    // Obtenemos los datos de la tabla y los ponemos en los campos de texto
                    txtId.setText(modeloTabla.getValueAt(filaSeleccionada, 0).toString());
                    txtTitulo.setText(modeloTabla.getValueAt(filaSeleccionada, 1).toString());
                    txtDirector.setText(modeloTabla.getValueAt(filaSeleccionada, 2).toString());
                    txtAnio.setText(modeloTabla.getValueAt(filaSeleccionada, 3).toString());
                    txtDuracion.setText(modeloTabla.getValueAt(filaSeleccionada, 4).toString());
                    txtGenero.setText(modeloTabla.getValueAt(filaSeleccionada, 5).toString());
                }
            }
        });
    }
    
    
    
    /**
     * Funcionalidades del panel
     */
    
    private void listarPeliculas() {
        // Limpiamos la tabla antes de cargar nuevos datos
        modeloTabla.setRowCount(0);

        List<Pelicula> peliculas = peliculaDAO.listarPeliculas();
        for (Pelicula p : peliculas) {
            Object[] fila = {
                p.getId(),
                p.getTitulo(),
                p.getDirector(),
                p.getAnio(),
                p.getDuracion(),
                p.getGenero()
            };
            modeloTabla.addRow(fila);
        }
    }

    private void agregarPelicula() {
        try {
            // Recolectamos los datos de la GUI
            String titulo = txtTitulo.getText();
            String director = txtDirector.getText();
            int anio = Integer.parseInt(txtAnio.getText());
            int duracion = Integer.parseInt(txtDuracion.getText());
            String genero = txtGenero.getText();

            // Validación simple (la mejoraremos en la Fase 3)
            if (titulo.isEmpty() || director.isEmpty() || genero.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Todos los campos son obligatorios.", "Error de Validación", JOptionPane.ERROR_MESSAGE);
                return;
            }

            Pelicula nuevaPelicula = new Pelicula(titulo, director, anio, duracion, genero);
            
            if (peliculaDAO.agregarPelicula(nuevaPelicula)) {
                JOptionPane.showMessageDialog(this, "Película agregada con éxito.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                limpiarCampos();
                listarPeliculas(); // Refrescamos la tabla
            } else {
                JOptionPane.showMessageDialog(this, "No se pudo agregar la película.", "Error de Base de Datos", JOptionPane.ERROR_MESSAGE);
            }

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "El año y la duración deben ser números válidos.", "Error de Formato", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void limpiarCampos() {
        txtId.setText("");
        txtTitulo.setText("");
        txtDirector.setText("");
        txtAnio.setText("");
        txtDuracion.setText("");
        txtGenero.setText("");
    }

    private void actualizarPelicula() {
        try {
            // Validamos que se haya seleccionado un ID
            if (txtId.getText().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Por favor, seleccione una película de la tabla para modificar.", "Error de Selección", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Recolectamos los datos
            int id = Integer.parseInt(txtId.getText());
            String titulo = txtTitulo.getText();
            String director = txtDirector.getText();
            int anio = Integer.parseInt(txtAnio.getText());
            int duracion = Integer.parseInt(txtDuracion.getText());
            String genero = txtGenero.getText();

            // Validamos que los campos no estén vacíos [Requisito S7]
            if (titulo.isEmpty() || director.isEmpty() || genero.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Todos los campos deben estar llenos para modificar.", "Error de Validación", JOptionPane.ERROR_MESSAGE);
                return;
            }

            Pelicula peliculaModificada = new Pelicula(id, titulo, director, anio, duracion, genero);

            if (peliculaDAO.actualizarPelicula(peliculaModificada)) {
                JOptionPane.showMessageDialog(this, "Película actualizada con éxito.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                limpiarCampos();
                listarPeliculas(); // Refrescamos la tabla
            } else {
                JOptionPane.showMessageDialog(this, "No se pudo actualizar la película.", "Error de Base de Datos", JOptionPane.ERROR_MESSAGE);
            }

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "El ID, año y duración deben ser números válidos.", "Error de Formato", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void eliminarPelicula() {
        // Validamos que se haya seleccionado un ID [Requisito S7]
        if (txtId.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Por favor, seleccione una película de la tabla para eliminar.", "Error de Selección", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Pedimos confirmación al usuario [Requisito S7]
        int confirmacion = JOptionPane.showConfirmDialog(this, "¿Está seguro de que desea eliminar esta película?", "Confirmar Eliminación", JOptionPane.YES_NO_OPTION);

        if (confirmacion == JOptionPane.YES_OPTION) {
            try {
                int id = Integer.parseInt(txtId.getText());

                if (peliculaDAO.eliminarPelicula(id)) {
                    JOptionPane.showMessageDialog(this, "Película eliminada con éxito.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                    limpiarCampos();
                    listarPeliculas(); // Refrescamos la tabla
                } else {
                    JOptionPane.showMessageDialog(this, "No se pudo eliminar la película. Verifique que exista.", "Error de Base de Datos", JOptionPane.ERROR_MESSAGE);
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "El ID de la película no es válido.", "Error de Formato", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    

}


