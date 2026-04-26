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
                JOptionPane.showMessageDialog(vista, "Ingrese un nombre valido.", "Error", JOptionPane.WARNING_MESSAGE);
                return;
            }

            if (modelo.agregarJugador(nombre)) {
                vista.actualizarLista(modelo.obtenerNombresJugadores());
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
                JOptionPane.showMessageDialog(vista, "Necesitas al menos 2 jugadores para iniciar.", "Aviso", JOptionPane.INFORMATION_MESSAGE);
                return;
            }

            modelo.iniciarJuego();
            vista.agregarLog("El juego ha iniciado");
            vista.setMensajeTurno("Turno de: " + modelo.obtenerJugadorActual()); // Actualiza el turno
            
            vista.getBtnAgregar().setEnabled(false);
            vista.getBtnIniciar().setEnabled(false);
            vista.getBtnLanzar().setEnabled(true);
        });

        // Evento: Lanzar Dado
        vista.getBtnLanzar().addActionListener(e -> {
            int dado = modelo.lanzarDado();
            String mensaje = modelo.ejecutarRonda(dado);
            
            vista.agregarLog(mensaje);
            vista.actualizarLista(modelo.obtenerNombresJugadores());

            if (modelo.juegoTerminado()) {
                vista.getBtnLanzar().setEnabled(false);
                vista.setMensajeTurno("Juego Terminado");
            } else {
                // Si el juego sigue, actualiza el letrero con el siguiente turno
                vista.setMensajeTurno("Turno de: " + modelo.obtenerJugadorActual());
            }
        });

        // Evento: Reiniciar
        vista.getBtnReiniciar().addActionListener(e -> {
            String mensaje = modelo.reiniciarJuego();
            vista.actualizarLista(modelo.obtenerNombresJugadores());
            vista.limpiarLogs();
            vista.agregarLog(mensaje);
            vista.setMensajeTurno("Turno actual: Esperando inicio..."); // Reinicia el letrero
            
            vista.getBtnAgregar().setEnabled(true);
            vista.getBtnIniciar().setEnabled(true);
            vista.getBtnLanzar().setEnabled(false);
        });
    }

    public void iniciar() {
        vista.setVisible(true);
    }
}