package co.edu.udistrital.model;

import java.util.Random;

/**
 * Clase que representa la lógica de un juego basado en turnos.
 * Los jugadores se almacenan en una lista circular y se eliminan
 * dependiendo del resultado de un dado.
 */
public class Juego {

    /**
     * Generador de números aleatorios para simular el dado.
     */
    private final Random random;

    /**
     * Lista circular de jugadores.
     */
    private final Lista<String> jugadores;

    /**
     * Valor mínimo del dado.
     */
    private final int min;

    /**
     * Valor máximo del dado.
     */
    private final int max;

    /**
     * Constructor que inicializa el juego con valores por defecto.
     */
    public Juego() {
        jugadores = new Lista<>();
        random = new Random();
        min = 1;
        max = 6;
    }

    /**
     * Agrega un jugador al juego si no existe previamente.
     * 
     * @param nombre nombre del jugador
     * @return true si el jugador fue agregado, false si ya existía
     */
    public boolean agregarJugador(String nombre) {
        if (jugadores.contiene(nombre)) {
            return false;
        } else {
            jugadores.agregar(nombre);
            return true;
        }
    }

    /**
     * Obtiene los nombres de los jugadores actuales.
     * 
     * @return arreglo con los nombres de los jugadores
     */
    public String[] obtenerNombresJugadores() {
        Object[] datos = jugadores.jugadoresActuales();
        String[] nombres = new String[datos.length];

        for (int i = 0; i < datos.length; i++) {
            nombres[i] = (String) datos[i];
        }

        return nombres;
    }

    /**
     * Inicializa el juego estableciendo el primer turno.
     */
    public void iniciarJuego() {
        if (jugadores.getTamanio() > 0) {
            jugadores.turnoIncial();
        }
    }

    /**
     * Simula el lanzamiento de un dado.
     * 
     * @return número aleatorio entre el valor mínimo y máximo
     */
    public int lanzarDado() {
        return random.nextInt(max - min + 1) + min;
    }

    /**
     * Verifica si el juego ha terminado.
     * 
     * @return true si queda uno o ningún jugador, false en caso contrario
     */
    public boolean juegoTerminado() {
        return jugadores.getTamanio() <= 1;
    }

    /**
     * Ejecuta una ronda del juego según el valor del dado.
     * Si el número es par, el jugador actual es eliminado.
     * Si es impar, el turno avanza dos posiciones.
     * 
     * @param dado valor obtenido al lanzar el dado
     * @return mensaje describiendo el resultado de la ronda
     */
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

    /**
     * Reinicia el juego eliminando todos los jugadores.
     * 
     * @return mensaje indicando que el juego ha sido reiniciado
     */
    public String reiniciarJuego(){
        jugadores.vaciar();
        return "Se ha reiniciado el juego, incluye nuevos jugadores desde 0";
    }

    /**
     * Obtiene el nombre del jugador actual.
     * 
     * @return nombre del jugador actual o "Ninguno" si no existe
     */
    public String obtenerJugadorActual() {
        String actual = jugadores.getJugadorActual();
        return (actual != null) ? actual : "Ninguno";
    }
}