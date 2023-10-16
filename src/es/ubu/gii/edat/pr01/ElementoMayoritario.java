package es.ubu.gii.edat.pr01;

import java.util.Arrays;

/**
 * Clase que alberga el código completo para la Práctica 01
 * Búsqueda del elemento mayoritario de forma iterativa y recursiva
 * 
 * @author bbaruque
 * @author Iván Estépar Rebollo y Jimena Arnaiz González
 *
 */
public class ElementoMayoritario {

	/**
	 * Clase estática RespuestaMayoritaria.
	 *
	 * @param <E> objeto tipo E.
	 */
	public static class RespuestaMayoritaria<E> {
		/**
		 * Elemento.
		 */
		private E elemento;
		/**
		 * Frecuencia del elemento.
		 */
		private int frecuencia;

		/**
		 * Constructor de Respuesta mayoritaria.
		 * 
		 * @param elemento   Elemento
		 * @param frecuencia Frecuencia
		 */
		public RespuestaMayoritaria(E elemento, int frecuencia) {
			this.elemento = elemento;
			this.frecuencia = frecuencia;
		}

		/**
		 * Obtiene el elemento.
		 * 
		 * @return elemento.
		 */
		public E getElemento() {
			return elemento;
		}

		/**
		 * Obtiene la frecuencia.
		 * 
		 * @return frecuencia
		 */
		public int getFrecuencia() {
			return frecuencia;
		}
	}

	/**
	 * Calcula el elemento mayoritario de un array de forma iterativa.
	 * 
	 * @param <E>   objeto de tipo E.
	 * @param array El array del que se quiera saber el elemento mayoritario.
	 * @return la respuesta mayoritaria
	 */
	public static <E> RespuestaMayoritaria<E> mayoritarioIterativo(E[] array) {
		int frecMax = 0;
		E elemento = null;
		E mayor = null;
		int frec;

		// recorre los elementos del array y calcula la frecuencia de cada uno de sus
		// elementos
		for (int i = 1; i < array.length; i++) {
			elemento = array[i];
			frec = frecuencia(elemento, array);
			if (frec > frecMax) {
				frecMax = frec; // actualiza el valor de la frecuencia máxima
				mayor = elemento;
			}
		}
		
		if (frecMax > array.length / 2) {
			return new RespuestaMayoritaria<E>(mayor, frecMax);
		} else {
			return new RespuestaMayoritaria<E>(null, 0);
		}
	}

	/**
	 * Calcula la frecuencia.
	 * 
	 * @param <E>      objeto tipo E.
	 * @param elemento Elemento.
	 * @param array    Array.
	 * @return la frecuencia del elemento dado.
	 */
	private static <E> int frecuencia(E elemento, E[] array) {
		int frecuencia = 0;
		for (int i = 0; i < array.length; i++) {
			if (array[i] == elemento) {
				frecuencia++;
			}
		}
		return frecuencia;
	}

	/**
	 * Calcula el elemento mayoritario de un array de forma recursiva.
	 * 
	 * @param <E> objeto tipo E.
	 * @param array Array
	 * @return respuesta mayoritaria.
	 */
	public static <E> RespuestaMayoritaria<E> mayoritarioRecursivo(E[] array) {
		E elemento = null;
		// Caso base: array de tamaño 1
		if (array.length == 1) {
			return new RespuestaMayoritaria<E>(array[0], 1);
		}

		// Dividir el array en dos mitades
		E[] mitadIzquierda = Arrays.copyOfRange(array, 0, array.length / 2);
		E[] mitadDerecha = Arrays.copyOfRange(array, array.length / 2, array.length);

		RespuestaMayoritaria<E> mayoritario1 = mayoritarioRecursivo(mitadIzquierda);
		RespuestaMayoritaria<E> mayoritario2 = mayoritarioRecursivo(mitadDerecha);

		//
		int a = frecuencia(mayoritario1.getElemento(), array);
		int b = frecuencia(mayoritario2.getElemento(), array);

		// Verificar si el candidato de la mitad izquierda es el elemento mayoritario
		if (a > array.length / 2) {
			return new RespuestaMayoritaria<E>(mayoritario1.getElemento(), a);
		}

		// Verificar si el candidato de la mitad derecha es el elemento mayoritario
		if (b > array.length / 2) {
			return new RespuestaMayoritaria<E>(mayoritario2.getElemento(), b);
		}

		// No hay elemento mayoritario
		return new RespuestaMayoritaria<E>(null, 0);
	}
}
