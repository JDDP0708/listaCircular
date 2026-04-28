package co.edu.udistrital.model;

import java.util.Random;

public class Juego {

    private final Random random;
    private final Lista<String> jugadores;
    private final int min;
    private final int max;

    public Juego() {
        jugadores = new Lista<>();
        random = new Random();
        min = 1;
        max = 6;
    }

    public boolean agregarJugador(String nombre) {
        if (jugadores.contiene(nombre)) {
            return false;
        } else {
            jugadores.agregar(nombre);
            return true;
        }
    }

    public String[] obtenerNombresJugadores() {
        Object[] datos = jugadores.jugadoresActuales();
        String[] nombres = new String[datos.length];

        for (int i = 0; i < datos.length; i++) {
            nombres[i] = (String) datos[i];
        }

        return nombres;
    }

    public void iniciarJuego() {
        if (jugadores.getTamanio() > 0) {
            jugadores.turnoIncial();
        }
    }

    public int lanzarDado() {
        return random.nextInt(max - min + 1) + min;
    }

    public boolean juegoTerminado() {
        return jugadores.getTamanio() <= 1;
    }

    public String ejecutarRonda(int dado) {
        if (juegoTerminado()) {
            return "El juego ya ha terminado, el ganador es: " 
                    + jugadores.getJugadorActual();
        }

        String jugadorDeTurno = jugadores.getJugadorActual();
        String mensaje;

        if (dado % 2 == 0) {
            jugadores.eliminarActual();
            mensaje = jugadorDeTurno + " sacó " + dado 
                    + " (Par) y ha sido ELIMINADO.";
        } else {
            jugadores.nuevoTurno();
            mensaje = jugadorDeTurno + " sacó " + dado 
                    + " (Impar) y SALTA dos posiciones.";
        }

        if (juegoTerminado()) {
            mensaje += " \n¡El juego ha terminado! El ganador es: " 
                    + jugadores.getJugadorActual();
        }

        return mensaje;
    }
    
    public String reiniciarJuego(){
        
        jugadores.vaciar();
        
        return "Se ha reiniciado el juego, incluye nuevos jugadores desde 0";
    }
    
    public String obtenerJugadorActual() {
        String actual = jugadores.getJugadorActual();
        return (actual != null) ? actual : "Ninguno";
    }
}
