package model.data_structures;

public class NodeDouble<T> extends Node<T>

{

	
	protected NodeDouble<T> anterior;

	
	
	
	public NodeDouble(T elemento) 
	{
		super(elemento);
		anterior = null;
	}
	
	/**
	 * Método que retorna el nodo anterior.
	 * @return Nodo anterior.
	 */
	public NodeDouble<T> darAnterior()
	{
		return anterior;
	}
	
	/**
	 * Método que cambia el nodo anterior por el que llega como parámetro.
	 * @param anterior Nuevo nodo anterior.
	 */
	public void cambiarAnterior(NodeDouble<T> anterior)
	{
		this.anterior = anterior;
	}
}
