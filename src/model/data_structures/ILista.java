package model.data_structures;

import java.util.Iterator;

public interface ILista<T extends Comparable<T>> extends Iterable<T> {

	public void addFirst(T elem);

	public void addLast(T elem);

	public void addAtK(T elem,int pos);

	public T removeFirst();

	public T removeLast();

	public T remove(T elem);

	public T removeAtK(int pos) ;

	public int size();

	public boolean exists(T elem);

	public T getPos(int pos);

	public boolean isEmpty();

	@Override
	public Iterator<T> iterator();
}
