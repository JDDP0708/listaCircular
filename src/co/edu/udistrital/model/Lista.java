package co.edu.udistrital.model;

public class Lista<T> {

    private Nodo<T> cabeza;
    private Nodo<T> cola;
    private Nodo<T> jugadorActual;
    private int tamanio;

    public Lista() {
        cabeza = null;
        cola = null;
        tamanio = 0;
    }

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

    public boolean eliminar(T dato) {

        if (cabeza == null) {
            return false;
        }

        Nodo<T> actual = cabeza;
        Nodo<T> anterior = cola;

        do {
            if (actual.getDato().equals(dato)) {
                if (tamanio == 1) { // Caso: un solo nodo
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

    public int getTamanio() {
        return tamanio;
    }

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

    public void eliminarActual() {
        Nodo<T> temporal = jugadorActual;

        jugadorActual = jugadorActual.getSiguiente();

        eliminar(temporal.getDato());
    }

    public void nuevoTurno() {
        jugadorActual = jugadorActual.getSiguiente().getSiguiente();
    }

    public void turnoIncial() {
        jugadorActual = cabeza;
    }

    public T getJugadorActual() {
        return (jugadorActual != null) ? jugadorActual.getDato() : null;
    }

    public void vaciar() {
        cabeza = null;
        cola = null;
        jugadorActual = null;
        tamanio = 0;
    }
}
