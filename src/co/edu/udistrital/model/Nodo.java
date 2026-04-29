package co.edu.udistrital.model;

/**
 * Clase que representa un nodo genérico para estructuras de datos enlazadas.
 * 
 * @param <T> el tipo de dato que almacenará el nodo
 */
public class Nodo<T> {

    /**
     * Dato almacenado en el nodo.
     */
    private T dato;

    /**
     * Referencia al siguiente nodo en la estructura.
     */
    private Nodo<T> siguiente;

    /**
     * Constructor que crea un nodo con un dato y sin referencia al siguiente nodo.
     * 
     * @param dato el dato que almacenará el nodo
     */
    public Nodo(T dato) {
        this(dato, null);
    }

    /**
     * Constructor que crea un nodo con un dato y una referencia al siguiente nodo.
     * 
     * @param dato el dato que almacenará el nodo
     * @param siguiente referencia al siguiente nodo
     */
    public Nodo(T dato, Nodo<T> siguiente) {
        this.dato = dato;
        this.siguiente = siguiente;
    }

    /**
     * Obtiene el dato almacenado en el nodo.
     * 
     * @return el dato del nodo
     */
    public T getDato() {
        return dato;
    }

    /**
     * Establece el dato del nodo.
     * 
     * @param dato el nuevo dato a asignar
     */
    public void setDato(T dato) {
        this.dato = dato;
    }

    /**
     * Obtiene la referencia al siguiente nodo.
     * 
     * @return el siguiente nodo
     */
    public Nodo<T> getSiguiente() {
        return siguiente;
    }

    /**
     * Establece la referencia al siguiente nodo.
     * 
     * @param siguiente el nodo que será el siguiente
     */
    public void setSiguiente(Nodo<T> siguiente) {
        this.siguiente = siguiente;
    }

    /**
     * Retorna una representación en cadena del dato almacenado.
     * 
     * @return una cadena con el valor del dato
     */
    @Override
    public String toString() {
        return dato.toString();
    }
}