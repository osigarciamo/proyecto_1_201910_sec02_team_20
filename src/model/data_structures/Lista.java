package model.data_structures;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class Lista<T extends Comparable<T>> implements ILista<T>
{
	private NodeDouble<T>primero;

	private NodeDouble<T>ultimo;

	private int tamanio;

	public Lista() 
	{
		tamanio=0;
	}

	@Override
	public void addFirst(T elem) 
	{

		NodeDouble<T> tmp = new NodeDouble<T>(elem);
		tmp.cambiarSiguiente(primero);
		tmp.cambiarAnterior(null);
		if(primero != null)
		{
			primero.anterior=tmp;
		}
		primero=tmp;
		if(ultimo==null)
		{
			ultimo=tmp;
		}
		tamanio++;

	}

	@Override
	public void addLast(T elem) 
	{
		NodeDouble<T>tmp= new NodeDouble<T>(elem);
		tmp.cambiarAnterior(ultimo);
		tmp.cambiarSiguiente(null);
		if(ultimo != null)
		{
			ultimo.siguiente = tmp;
		}
		ultimo=tmp;
		if(primero == null)
		{ 
			primero = tmp;
		}
		tamanio++;

	}

	public void addAtK(T elem,int pos)
	{
		int posicion=pos;
		if(posicion>tamanio)
		{
			throw new ArrayIndexOutOfBoundsException("No es posible añadir en la posicion indicada");
		}
		NodeDouble<T> tmp=primero;
		while(tmp!=null && posicion>0)
		{
			tmp = (NodeDouble<T>) tmp.siguiente;
			posicion--;
		}
		if(pos==0)
		{
			addFirst(elem);
		}
		else if(pos==tamanio)
		{
			addLast(elem);
		}
		else
		{
			NodeDouble<T>nuevo= new NodeDouble<T>(elem);
			nuevo.cambiarAnterior(tmp.darAnterior());
			nuevo.cambiarSiguiente(tmp);
			tmp.cambiarAnterior(nuevo);
			nuevo.anterior.cambiarSiguiente(nuevo);
			tamanio++;

		}
	}

	@Override
	public T remove(T elem) 
	{
		if (tamanio == 0) 
		{
			throw new NoSuchElementException("El tamaño de la lista es cero");
		}
		boolean encontrado=false;
		NodeDouble<T> tmp = primero;
		T buscado=null;
		int contador=0;
		while(tmp!=null && !encontrado)
		{
			if(tmp.darElemento().compareTo(elem)==0)
			{
				encontrado=true;
				buscado=tmp.darElemento();	
				if(contador==0)
				{
					removeFirst();
				}
				else if(contador==tamanio-1)
				{
					removeLast();
				}
				else
				{
					NodeDouble<T>anteriorTmp=tmp.anterior;
					NodeDouble<T>siguienteTmp=(NodeDouble<T>) tmp.siguiente;
					anteriorTmp.cambiarSiguiente(siguienteTmp);
					tmp.cambiarSiguiente(null);
					siguienteTmp.cambiarAnterior(anteriorTmp);
					tmp.cambiarAnterior(null);
					tamanio--;
				}
			}
			tmp = (NodeDouble<T>) tmp.siguiente;
			contador++;
		}
		if (buscado == null) 
		{
			throw new NoSuchElementException("No se encontro el elemento que se desea eliminar");
		}
		return buscado;
	}


	public T set(int index,T elem)
	{
		Node<T> actual= primero;
		int i=0;
		T aux=null;
		while(i<index)
		{
			actual=actual.darSiguiente();
			i++;
		}
		aux= actual.darElemento();
		actual.cambiarElemento(elem);

		return aux;
	}

	public T removeAtK(int pos) 
	{
		if (tamanio == 0) 
		{
			throw new NoSuchElementException("El tamaño de la lista es cero");
		}
		boolean encontrado=false;
		NodeDouble<T> tmp = primero;
		T buscado=null;
		int contador=0;
		while(tmp!=null && !encontrado)
		{
			if(contador==pos)
			{
				encontrado=true;
				buscado=tmp.darElemento();	
				if(contador==0)
				{
					removeFirst();
				}
				else if(contador==tamanio-1)
				{
					removeLast();
				}
				else
				{
					NodeDouble<T>anteriorTmp=tmp.anterior;
					NodeDouble<T>siguienteTmp=(NodeDouble<T>) tmp.siguiente;
					anteriorTmp.cambiarSiguiente(siguienteTmp);
					tmp.cambiarSiguiente(null);
					siguienteTmp.cambiarAnterior(anteriorTmp);
					tmp.cambiarAnterior(null);
					tamanio--;
				}
			}
			tmp = (NodeDouble<T>) tmp.siguiente;
			contador++;
		}
		if (buscado == null) 
		{
			throw new NoSuchElementException("No se encontro el elemento que se desea eliminar");
		}
		return buscado;		
	}

	//Revisar
	@Override
	public T removeFirst() 
	{
		if (tamanio == 0) 
		{
			throw new NoSuchElementException();
		}
		NodeDouble<T> tmp = primero;
		primero =  (NodeDouble<T>) primero.siguiente;
		primero.anterior = null;
		tamanio--;
		return  tmp.elemento;
	}

	@Override
	public T removeLast()
	{
		if (tamanio == 0) 
		{
			throw new NoSuchElementException();

		}
		NodeDouble<T> tmp = ultimo;
		ultimo = ultimo.anterior;
		ultimo.siguiente = null;
		tamanio--;
		return tmp.elemento;
	}

	@Override
	public int size()
	{
		return tamanio;
	}

	@Override
	public boolean exists(T elem)
	{
		boolean encontrado=false;
		NodeDouble<T> tmp = primero;

		while(tmp!=null && !encontrado)
		{
			if(tmp.darElemento().compareTo(elem)==0)
			{
				encontrado=true;
			}
			tmp = (NodeDouble<T>) tmp.siguiente;
		}
		return encontrado;
	}

	public T getFirst()
	{
		return primero.darElemento();
	}

	public T getLast()
	{
		return ultimo.darElemento();
	}

	@Override
	public T getPos(int pos) 
	{	
		if(pos>=tamanio)
		{
			return null;
		}
		NodeDouble<T> tmp=primero;
		while(tmp!=null && pos>0)
		{
			tmp = (NodeDouble<T>) tmp.siguiente;
			pos--;
		}


		return tmp.darElemento();
	}
	public void setList(Lista<T> listaAux)
	{
		for (int i = 0; i < listaAux.size(); i++) 
		{
			addLast(listaAux.getPos(i));
		}
	}
	@Override
	public boolean isEmpty()
	{
		return tamanio ==0 ;
	}

	@Override
	public Iterator<T> iterator() {
		// TODO Auto-generated method stub
		return null;
	}

	public void reverse (Lista<T> listaAux)
	{
		for (int i = 0; i < listaAux.size(); i++) 
		{
			addFirst(listaAux.getPos(i));
		}
	}


}
