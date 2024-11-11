package es.ubu.gii.edat.pr05;
import java.util.*;

/**
 * Clase que crea un árbol binario de búsqueda
 * @author Iván Estépar Rebollo
 * @author Jimena Arnaiz González
 *
 * @param <E>
 */
public class ArbolBB<E> extends AbstractSet<E> {
    private Nodo raiz;
    private Comparator<? super E> comparator;
    private int size;

    private class Nodo {
        private E dato;
        private Nodo izquierda;
        private Nodo derecha;

        public Nodo(E dato) {
            this.dato = dato;
            this.izquierda = null;
            this.derecha = null;
        }
    }

    public ArbolBB() {
        this.raiz = null;
        this.comparator = null;
        this.size = 0;
    }

    public ArbolBB(Comparator<? super E> comparator) {
        this.raiz = null;
        this.comparator = comparator;
        this.size = 0;
    }

    public ArbolBB(Collection<? extends E> collection) {
        addAll(collection);
    }

    public ArbolBB(Comparator<? super E> comparator, Collection<? extends E> collection) {
        this.comparator = comparator;
        addAll(collection);
    }

    @Override
    public int size() {
        return size;
    }


    @Override
    public boolean add(E elemento) {
        if (raiz == null) { //si el arbol esta vacio
            raiz = new Nodo(elemento); //se añade un nuevo nodo
            size = 1; //tamaño a 1
            return true;
        } 
        else {
            if (addElement(raiz, elemento)) { //si se ha añadido un elemento
                size++; //aumenta el tamaño
                return true;
            }
            return false;
        }
    }

    @Override
    public boolean remove(Object obj) {
        E elemento = castElement(obj);
        if (containsElement(raiz, elemento)) { //si el elemento está en el árbol
            raiz = removeElement(raiz, elemento); //elimina el elemento
            size--; //disminuye el tamaño
            return true;
        }
        return false;
    }

    @Override
    public Iterator<E> iterator() {
        List<E> elementos = new ArrayList<>(); //se crea una lista de elementos
        inOrder(raiz, elementos); //se recorre inorden
        return elementos.iterator(); //retorna la lista
    }

    public int altura(E elemento) {
        return getAltura(raiz, elemento); //devuelve la altura
    }

    public int profundidad(E elemento) {
        return getProfundidad(raiz, elemento); //devuelve la profundidad
    }

    private E castElement(Object obj) {
        try {
            return (E) obj;
        } 
        catch (ClassCastException e) {
            throw new IllegalArgumentException("Invalid element type");
        }
    }

    private boolean containsElement(Nodo nodo, E elemento) {
        if (nodo == null) { //si el nodo actual es nulo
            return false;
        }

        if (compareElements(elemento, nodo.dato) == 0) { //si el elemento buscado y el nodo actual son iguales
            return true;
        } 
        else if (compareElements(elemento, nodo.dato) < 0) { //si el elemento buscado es menor que el nodo actual
            return containsElement(nodo.izquierda, elemento); //llamada recursiva pero del subarbol izquierdo
        } 
        else {
            return containsElement(nodo.derecha, elemento); //llamada recursiva pero del subarbol derecho
        }
    } //el objetivo de las llamadas recursivas es obtener el mismo elemento que el nodo actual

    
    private boolean addElement(Nodo nodo, E elemento) {
        if (compareElements(elemento, nodo.dato) == 0) { //si el elemento buscado y el nodo actual son iguales
            return false; // No se permite duplicar valores
        } 
        else if (compareElements(elemento, nodo.dato) < 0) { //si el elemento buscado es menor que el nodo actual
            if (nodo.izquierda == null) { //si el nodo izquierdo no tiene un nodo hijo
                nodo.izquierda = new Nodo(elemento); //se crea un nuevo nodo
                return true;
            } else {
                return addElement(nodo.izquierda, elemento); //llamada recursiva del subarbol izquierdo
            }
        } 
        else {
            if (nodo.derecha == null) { //si el nodo derecho no tiene un nodo hijo
                nodo.derecha = new Nodo(elemento); //se crea un nuevo nodo
                return true;
            } 
            else {
                return addElement(nodo.derecha, elemento); //llamada recursiva del subarbol derecho
            }
        }
    }

    private Nodo removeElement(Nodo nodo, E elemento) {
        if (nodo == null) { //si el nodo actual es nulo
            return null;
        }

        if (compareElements(elemento, nodo.dato) < 0) { //si el elemento buscado es menor que el nodo actual
            nodo.izquierda = removeElement(nodo.izquierda, elemento); //llamada recursiva del subarbol izquierdo
        } 
        else if (compareElements(elemento, nodo.dato) > 0) { //si el elemento buscado es mayor que el nodo actual
            nodo.derecha = removeElement(nodo.derecha, elemento); //llamada recursiva del subarbol derecho
        } 
        else {
            if (nodo.izquierda == null) { //si el nodo izquierdo no tiene nodo hijo izquierdo
                return nodo.derecha; //se devuelve el hijo derecho
            } 
            else if (nodo.derecha == null) { //si el nodo derecho no tiene nodo hijo derecho
                return nodo.izquierda; //se devuelve el hijo izquierdo
            } 
            else {
                Nodo sucesor = buscarMinimo(nodo.derecha); //se le asigna al nodo sucesor el valor del subarbol derecho mas pequeño
                nodo.dato = sucesor.dato; //se reemplazan los valores
                nodo.derecha = removeElement(nodo.derecha, sucesor.dato); //se elimina el sucesor
            }
        }

        return nodo;
    }

    private Nodo buscarMinimo(Nodo nodo) { //se deberá hacer en el subarbol izquierdo porque es donde se encuentra el minimo
        while (nodo.izquierda != null) { //mientras exista un nodo hijo izquierdo
            nodo = nodo.izquierda; //se asigna un nodo hijo izquierdo al nodo actual
        }
        return nodo;
    }

    private void inOrder(Nodo nodo, List<E> elementos) {
        if (nodo != null) { //si el nodo actual no es nulo
            inOrder(nodo.izquierda, elementos); //llamada recursiva para recorrer en inorden el subarbol izquierdo
            elementos.add(nodo.dato); //se agrega el dato almacenado en el nodo actual en la lista de elementos
            inOrder(nodo.derecha, elementos); //llamada recursiva para recorrer en inorden el subarbol derecho
        }
    }

    private int getAltura(Nodo nodo, E elemento) {
        if (nodo == null) { //si el nodo actual es nulo
            return -1;
        }

        if (compareElements(elemento, nodo.dato) == 0) { //si el elemento que se busca es igual al dato del nodo actual
            return calcularAltura(nodo); //se calcula la altura
        } 
        else if (compareElements(elemento, nodo.dato) < 0) { //si el elemento que se busca es menor que el dato del nodo actual
            return getAltura(nodo.izquierda, elemento); //llamada recursiva para el subárbol izquierdo que acabará cuando el elemento sea igual al dato del nodo
        } 
        else {
            return getAltura(nodo.derecha, elemento); //llamada recursiva para el subarbol derecho
        }
    }

    private int getProfundidad(Nodo nodo, E elemento) {
        if (nodo == null) { //si el nodo actual es nulo
            return -1;
        }

        if (compareElements(elemento, nodo.dato) == 0) { //si el elemento que se busca es igual al dato del nodo actual
            return calcularProfundidad(nodo); //se calcula la profundidad
        } 
        else if (compareElements(elemento, nodo.dato) < 0) { //si el elemento que se busca es menor que el dato del nodo actual
            return 1 + getProfundidad(nodo.izquierda, elemento); //llamada recursiva pero en este caso para el subárbol izquierdo. Se aumentará en 1 el valor
        } //el método acabará cuando el elemento que se busca sea igual al dato del nodo
        else {
            return 1 + getProfundidad(nodo.derecha, elemento);
        }
    }

    private int calcularAltura(Nodo nodo) {
        if (nodo == null) { //si el nodo actual es nulo
            return -1;
        }

        int alturaIzq = calcularAltura(nodo.izquierda); //se calcula la altura para el subarbol izquierdo
        int alturaDcha = calcularAltura(nodo.derecha); //se calcula la altura para el subarbol derecho

        return 1 + Math.max(alturaIzq, alturaDcha); //retorna el valor maximo entre los 2 subarboles + 1 que es el nivel actual
    }

    private int calcularProfundidad(Nodo nodo) {
        if (nodo == null) {
            return -1;
        }

        return 1 + calcularProfundidad(nodoPadre(nodo));
    }

    private Nodo nodoPadre(Nodo nodo) {
        return buscarPadre(raiz, nodo);
    }

    private Nodo buscarPadre(Nodo padre, Nodo nodo) {
        if (padre == null || nodo == null) {
            return null;
        }

        if (padre.izquierda == nodo || padre.derecha == nodo) {
            return padre;
        }

        Nodo leftParent = buscarPadre(padre.izquierda, nodo);
        if (leftParent != null) {
            return leftParent;
        }

        return buscarPadre(padre.derecha, nodo);
    }

    private int compareElements(E elem1, E elem2) {
        if (comparator != null) {
            return comparator.compare(elem1, elem2);
        } else {
            return ((Comparable<? super E>) elem1).compareTo(elem2);
        }
    }
}
