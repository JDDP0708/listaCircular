package co.edu.udistrital.controller;

import co.edu.udistrital.model.Juego;
import co.edu.udistrital.view.VistaJuego;
import javax.swing.JOptionPane;

public class ControladorJuego {

    private final Juego modelo;
    private final VistaJuego vista;

    public ControladorJuego(Juego modelo, VistaJuego vista) {
        this.modelo = modelo;
        this.vista = vista;
        inicializarEventos();
    }

    private void inicializarEventos() {
        // Evento: Agregar Jugador
        vista.getBtnAgregar().addActionListener(e -> {
            String nombre = vista.getTxtNombre().getText().trim();
            if (nombre.isEmpty()) {
                JOptionPane.showMessageDialog(vista, "Ingrese un nombre válido.", "Error", JOptionPane.WARNING_MESSAGE);
                return;
            }

            if (modelo.agregarJugador(nombre)) {
                // Actualiza el anillo visual
                actualizarEstadoVisual();
                
                vista.getTxtNombre().setText("");
                vista.getTxtNombre().requestFocus();
            } else {
                JOptionPane.showMessageDialog(vista, "El jugador ya existe.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        // Evento: Iniciar Juego
        vista.getBtnIniciar().addActionListener(e -> {
            String[] jugadores = modelo.obtenerNombresJugadores();
            if (jugadores.length < 2) {
                JOptionPane.showMessageDialog(vista, "Necesitas al menos 2 jugadores.", "Aviso", JOptionPane.INFORMATION_MESSAGE);
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

    // Método centralizado para actualizar texto de turno y anillo visual a la vez
    private void actualizarEstadoVisual() {
        String jugadorActual = modelo.obtenerJugadorActual();
        String[] listaJugadores = modelo.obtenerNombresJugadores();

        if (modelo.juegoTerminado() && listaJugadores.length == 1) {
             vista.setMensajeTurno("¡Ganador: " + jugadorActual + "! 🎉");
        } else if (listaJugadores.length == 0) {
             vista.setMensajeTurno("Turno actual: Esperando inicio...");
        } else {
             vista.setMensajeTurno("Turno de: " + jugadorActual);
        }
        
        // ENVÍA LOS DATOS ACTUALIZADOS A LA VISTA PARA QUE REDIBUJE EL ANILLO
        vista.actualizarVisualizacionAnillo(listaJugadores, jugadorActual);
    }

    public void iniciar() {
        vista.setVisible(true);
    }
}