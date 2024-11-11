package es.ubu.gii.edat.pr02;

import java.util.AbstractList;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;

/**
 * Clase que ordena una lista
 * @author Iván Estépar Rebollo
 * @author Jimena Arnaiz González
 *
 * @param <E>
 */
public class ListaOrdenada<E> extends AbstractList<E> {
	private ArrayList<E> list;
    private Comparator<E> comparador;
    
    /**
     * Constructor 1
     */
    public ListaOrdenada() {
    	this.list = new ArrayList<E>();
    	comparador = null;
    }
    
    /**
     * Constructor 2
     * @param comparator
     */
    public ListaOrdenada(Comparator<E> comparator) {
        this.list = new ArrayList<E>();
        this.comparador = comparator;
    }
    
    
    /**
     * Metodo que busca el elemento
     * @param num
     * @return indice
     */
    private int buscarElemento(E num) {
        if (comparador != null) {
            for (int i = 0; i < list.size(); i++) {
                E actual = list.get(i);
                if (comparador.compare(num, actual) > 0) {
                    return i;
                }
            }
            return list.size();
        } else {
            Comparable<? super E> comparable = (Comparable<? super E>) num;
            for (int i = 0; i < list.size(); i++) {
                E actual = list.get(i);
                if (comparable.compareTo(actual) < 0) {
                    return i;
                }
            }
            return list.size();
        }
    }
    
    @Override
    public boolean add(E elemento) {
        int index = buscarElemento(elemento);
        list.add(index, elemento);
        return true;
    }

    @Override
    public boolean addAll(Collection<? extends E> num) {
        boolean modif = false;
        for (E elem : num) {
            int index = buscarElemento(elem);
            list.add(index, elem);
            modif = true;
        }
        return modif;
    }

    @Override
    public E set(int i, E elem) {
        throw new UnsupportedOperationException("No se puede modificar el orden de la lista");
    }

    @Override
    public void clear() {
        list.clear();
    }

    @Override
    public E get(int i) {
        return list.get(i);
    }

    @Override
    public int size() {
        return list.size();
    }

}
