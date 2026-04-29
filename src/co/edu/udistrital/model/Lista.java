package co.edu.udistrital.model;

/**
 * Implementa una lista enlazada circular genérica.
 * Permite almacenar elementos y recorrerlos de forma cíclica.
 * Además, maneja un "jugador actual" para lógica de turnos.
 * 
 * @param <T> el tipo de dato que almacenará la lista
 */
public class Lista<T> {

    /**
     * Nodo inicial de la lista.
     */
    private Nodo<T> cabeza;

    /**
     * Nodo final de la lista.
     */
    private Nodo<T> cola;

    /**
     * Nodo que representa el jugador actual.
     */
    private Nodo<T> jugadorActual;

    /**
     * Cantidad de elementos en la lista.
     */
    private int tamanio;

    /**
     * Constructor que inicializa una lista vacía.
     */
    public Lista() {
        cabeza = null;
        cola = null;
        tamanio = 0;
    }

    /**
     * Agrega un nuevo elemento a la lista.
     * Mantiene la estructura circular.
     * 
     * @param dato el dato a agregar
     */
    public void agregar(T dato) {
        Nodo<T> nuevoNodo = new Nodo<>(dato);

        if (cabeza == null) {
            cabeza = nuevoNodo;
            cola = nuevoNodo;
            cola.setSiguiente(cabeza);
        } else {
            cola.setSiguiente(nuevoNodo);
            nuevoNodo.setSiguiente(cabeza);
            cola = nuevoNodo;
        }
        tamanio++;
    }

    /**
     * Elimina un elemento de la lista.
     * 
     * @param dato el dato a eliminar
     * @return true si el elemento fue eliminado, false si no se encontró
     */
    public boolean eliminar(T dato) {

        if (cabeza == null) {
            return false;
        }

        Nodo<T> actual = cabeza;
        Nodo<T> anterior = cola;

        do {
            if (actual.getDato().equals(dato)) {
                if (tamanio == 1) {
                    cabeza = null;
                    cola = null;
                } else {
                    anterior.setSiguiente(actual.getSiguiente());
                    if (actual == cabeza) {
                        cabeza = actual.getSiguiente();
                    }
                    if (actual == cola) {
                        cola = anterior;
                    }
                }
                tamanio--;
                return true;
            }
            anterior = actual;
            actual = actual.getSiguiente();
        } while (actual != cabeza);

        return false;
    }

    /**
     * Verifica si un elemento está presente en la lista.
     * 
     * @param dato el dato a buscar
     * @return true si el elemento está en la lista, false en caso contrario
     */
    public boolean contiene(T dato) {
        if (cabeza == null) {
            return false;
        }

        Nodo<T> actual = cabeza;
        do {
            if (actual.getDato().equals(dato)) {
                return true;
            }
            actual = actual.getSiguiente();
        } while (actual != cabeza);

        return false;
    }

    /**
     * Obtiene el tamaño actual de la lista.
     * 
     * @return número de elementos en la lista
     */
    public int getTamanio() {
        return tamanio;
    }

    /**
     * Retorna los elementos actuales en forma de arreglo.
     * 
     * @return arreglo con los datos de la lista
     */
    public Object[] jugadoresActuales() {
        if (tamanio == 0) {
            return new Object[0];
        }

        Object[] arreglo = new Object[tamanio];
        Nodo<T> actual = cabeza;

        for (int i = 0; i < tamanio; i++) {
            arreglo[i] = actual.getDato();
            actual = actual.getSiguiente();
        }

        return arreglo;
    }

    /**
     * Elimina el nodo correspondiente al jugador actual
     * y avanza al siguiente.
     */
    public void eliminarActual() {
        Nodo<T> temporal = jugadorActual;
        jugadorActual = jugadorActual.getSiguiente();
        eliminar(temporal.getDato());
    }

    /**
     * Avanza el turno dos posiciones en la lista.
     */
    public void nuevoTurno() {
        jugadorActual = jugadorActual.getSiguiente().getSiguiente();
    }

    /**
     * Inicializa el turno estableciendo el jugador actual como la cabeza.
     */
    public void turnoIncial() {
        jugadorActual = cabeza;
    }

    /**
     * Obtiene el dato del jugador actual.
     * 
     * @return el dato del jugador actual o null si no existe
     */
    public T getJugadorActual() {
        return (jugadorActual != null) ? jugadorActual.getDato() : null;
    }

    /**
     * Vacía completamente la lista.
     */
    public void vaciar() {
        cabeza = null;
        cola = null;
        jugadorActual = null;
        tamanio = 0;
    }
}