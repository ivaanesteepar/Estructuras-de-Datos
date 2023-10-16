package es.ubu.inf.edat.pr03;

import java.util.AbstractList;
import java.util.Iterator;
import java.util.ListIterator;
import java.util.NoSuchElementException;

/**
 * Clase que crea una lista enlazada
 * @author Iván Estépar Rebollo
 * @author Jimena Arnaiz González
 *
 * @param <E>
 */
public class ListaEnlazada<E> extends AbstractList<E> {
    
    private Nodo<E> inicio;
    private int size;
    
    public ListaEnlazada() {
        inicio = null;
        size = 0;
    }

    private static class Nodo<E> {
        E dato;
        Nodo<E> siguiente;
        
        public Nodo(E dato, Nodo<E> siguiente) {
            this.dato = dato;
            this.siguiente = siguiente;
        }
    }
    
    @Override
    public boolean add(E e) {
        if (inicio == null) {
            inicio = new Nodo<E>(e, null);
        } else {
            Nodo<E> actual = inicio;
            while (actual.siguiente != null) {
                actual = actual.siguiente;
            }
            actual.siguiente = new Nodo<E>(e, null);
        }
        size++;
        return true;
    }
    
    @Override
    public E remove(int indice) {
        if (indice < 0 || indice >= size) {
            throw new IndexOutOfBoundsException();
        }
        E resultado;
        if (indice == 0) {
            resultado = inicio.dato;
            inicio = inicio.siguiente;
        } else {
            Nodo<E> actual = inicio;
            for (int i = 0; i < indice - 1; i++) {
                actual = actual.siguiente;
            }
            resultado = actual.siguiente.dato;
            actual.siguiente = actual.siguiente.siguiente;
        }
        size--;
        return resultado;
    }
    
    @Override
    public E get(int indice) {
        if (indice < 0 || indice >= size) {
            throw new IndexOutOfBoundsException();
        }
        Nodo<E> actual = inicio;
        for (int i = 0; i < indice; i++) {
            actual = actual.siguiente;
        }
        return actual.dato;
    }
    
    @Override
    public int size() {
        return size;
    }
    
    @Override
    public Iterator<E> iterator() {
        return new IteradorLista();
    }
    
    @Override
    public void clear() {
        inicio = null;
        size = 0;
    }
    
    private class IteradorLista implements ListIterator<E> {
        private Nodo<E> anterior;
        private Nodo<E> actual;
        private int indice;
        
        public IteradorLista() {
            anterior = null;
            actual = inicio;
            indice = 0;
        }
        
        @Override
        public boolean hasNext() {
            return actual != null;
        }
        
        @Override
        public E next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            E resultado = actual.dato;
            anterior = actual;
            actual = actual.siguiente;
            return resultado;
        }
        
        @Override
        public boolean hasPrevious() {
        	return anterior != null;
        }
        
        @Override
        public E previous() {
        	if (!hasPrevious()) {
                throw new NoSuchElementException();
            }
            E elemento = anterior.dato;
            actual = anterior;
            anterior = null;
            indice--;
            return elemento;
        }
        
        @Override
        public int nextIndex() {
            return indice;
        }
        
        @Override
        public int previousIndex() {
            return indice - 1;
        }
        
        @Override
        public void remove() {
            if (anterior == null) {
                throw new IllegalStateException();
            }
            if (anterior == inicio) {
                inicio = actual;
            } else {
                anterior.siguiente = actual;
            }
            anterior = null;
            size--;
        }

		@Override
		public void set(E e) {
			if (anterior == null) {
	            throw new IllegalStateException();
	        }
	        anterior.dato = e;
			
		}

		@Override
		public void add(E e) {
			throw new UnsupportedOperationException();
		}
        
    }
}
