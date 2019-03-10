package model.data_structures;


public class Node <T>
{
	protected T elemento;
	
    protected Node<T> siguiente;
    
    /**
	 * Constructor del nodo.
	 * @param elemento El elemento que se almacenará en el nodo. elemento != null
	 */
	public Node(T elemento)
	{
		this.elemento = elemento;
	}
	
	/**
	 * Método que cambia el siguiente nodo.
	 * <b>post: </b> Se ha cambiado el siguiente nodo
	 * @param siguiente El nuevo siguiente nodo
	 */
	public void cambiarSiguiente(Node<T> siguiente)
	{
		this.siguiente = siguiente;
	}
	
	/**
	 * Método que retorna el elemento almacenado en el nodo.
	 * @return El elemento almacenado en el nodo.
	 */
	public T darElemento()
	{
		return elemento;
	}
	
	/**
	 * Cambia el elemento almacenado en el nodo.
	 * @param elemento El nuevo elemento que se almacenará en el nodo.
	 */
	public void cambiarElemento(T elemento)
	{
		this.elemento = elemento;
	}
	
	/**
	 * Método que retorna el siguiente nodo.
	 * @return Siguiente nodo
	 */
	public Node<T> darSiguiente()
	{
		return siguiente;
	}

}
