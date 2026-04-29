package co.edu.udistrital.controller;

import co.edu.udistrital.model.Juego;
import co.edu.udistrital.view.VistaJuego;
import javax.swing.JOptionPane;

/**
 * Clase que actúa como intermediario entre la Vista y el Modelo (Juego).
 * Maneja los eventos de usuario (clics en botones) y actualiza la vista.
 */
public class ControladorJuego {

    private final Juego modelo;
    private final VistaJuego vista;

    /**
     * Constructor del controlador.
     * 
     * @param modelo Instancia de la clase Juego que contiene la lógica.
     * @param vista  Instancia de la interfaz gráfica principal.
     */
    public ControladorJuego(Juego modelo, VistaJuego vista) {
        this.modelo = modelo;
        this.vista = vista;
        inicializarEventos();
    }

    /**
     * Asocia los Listeners a los botones de la vista.
     * Define qué sucede cuando el usuario interactúa con la interfaz.
     */
    private void inicializarEventos() {
        // Evento: Agregar Jugador
        vista.getBtnAgregar().addActionListener(e -> {
            String nombre = vista.getTxtNombre().getText().trim();
            if (nombre.isEmpty()) {
                JOptionPane.showMessageDialog(vista, 
                        "Ingrese un nombre válido.", "Error", 
                        JOptionPane.WARNING_MESSAGE);
                return;
            }

            if (modelo.agregarJugador(nombre)) {
                // Actualiza el anillo visual
                actualizarEstadoVisual();
                
                vista.getTxtNombre().setText("");
                vista.getTxtNombre().requestFocus();
            } else {
                JOptionPane.showMessageDialog(vista, "El jugador ya existe.", 
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        // Evento: iniciar Juego
        vista.getBtnIniciar().addActionListener(e -> {
            String[] jugadores = modelo.obtenerNombresJugadores();
            if (jugadores.length < 2) {
                JOptionPane.showMessageDialog(vista, 
                        "Necesitas al menos 2 jugadores.", "Aviso", 
                        JOptionPane.INFORMATION_MESSAGE);
                return;
            }

            modelo.iniciarJuego();
            vista.agregarLog("¡El juego ha comenzado!");
            vista.getBtnAgregar().setEnabled(false);
            vista.getBtnIniciar().setEnabled(false);
            vista.getBtnLanzar().setEnabled(true);
            
            // Actualiza el anillo para iluminar al primer jugador
            actualizarEstadoVisual();
        });

        // Evento: Lanzar Dado
        vista.getBtnLanzar().addActionListener(e -> {
            int dado = modelo.lanzarDado();
            String mensaje = modelo.ejecutarRonda(dado);
            
            vista.agregarLog(mensaje);

            if (modelo.juegoTerminado()) {
                vista.getBtnLanzar().setEnabled(false);
            }
            
            // Actualiza el anillo: cambia de turno o quita al eliminado
            actualizarEstadoVisual();
        });

        // Evento: Reiniciar
        vista.getBtnReiniciar().addActionListener(e -> {
            String mensaje = modelo.reiniciarJuego();
            vista.limpiarLogs();
            vista.agregarLog(mensaje);
            
            vista.getBtnAgregar().setEnabled(true);
            vista.getBtnIniciar().setEnabled(true);
            vista.getBtnLanzar().setEnabled(false);
            
            // Limpia el anillo
            actualizarEstadoVisual();
        });
    }

    /**
     * Método centralizado para sincronizar la Vista con el estado actual del Modelo.
     * Extrae al jugador de turno y la lista de jugadores restantes para redibujar el anillo.
     */
    private void actualizarEstadoVisual() {
        String jugadorActual = modelo.obtenerJugadorActual();
        String[] listaJugadores = modelo.obtenerNombresJugadores();

        if (modelo.juegoTerminado() && listaJugadores.length == 1) {
             vista.setMensajeTurno("Ganador: " + jugadorActual + "");
        } else if (listaJugadores.length == 0) {
             vista.setMensajeTurno("Turno actual: Esperando inicio...");
        } else {
             vista.setMensajeTurno("Turno de: " + jugadorActual);
        }
        
        // ENVÍA LOS DATOS ACTUALIZADOS A LA VISTA PARA QUE REDIBUJE EL ANILLO
        vista.actualizarVisualizacionAnillo(listaJugadores, jugadorActual);
    }

    /**
     * Hace visible la interfaz gráfica e inicia la interacción con el usuario.
     */
    public void iniciar() {
        vista.setVisible(true);
    }
}