package es.ubu.gii.edat.pr04;

import java.util.AbstractMap;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Clase que crea un mapa de valores únicos
 * @author Iván Estépar Rebollo
 * @author Jimena Arnaiz González
 *
 * @param <K,V>
 */
public class MapaValoresUnicos<K, V> extends AbstractMap<K, V> {

    private HashMap<K, V> directo;
    private HashMap<V, K> inverso;

    public MapaValoresUnicos() {
    	directo = new HashMap<>();
        inverso = new HashMap<>();
    }

    @Override
    public V put(K key, V value) {
        if (inverso.containsKey(value)) { //si el valor en el mapa inverso ya existe
            throw new IllegalArgumentException("El valor ya está presente en el mapa");
        }
        V valorAnterior = directo.put(key, value); //introduce la key y el valor en el mapa directo
        inverso.put(value, key); //introduce la key y el valor en el mapa inverso
        if (valorAnterior != null) { //si la clave ya estaba en el mapa directo y, por lo tanto, no es null
            inverso.remove(valorAnterior); //se elimina el valor junto a la clave
        }
        return valorAnterior; //devuelve el valor junto a la clave
    }


    @Override
    public V get(Object key) {
        return directo.get(key); //devuelve el valor de la clave especificada
    }

    @Override
    public Set<Entry<K, V>> entrySet() {
        return directo.entrySet(); //devuelve las claves y valores de los mapas
    }

    public MapaValoresUnicos<V, K> inverso() {
    	MapaValoresUnicos<V, K> resultado = new MapaValoresUnicos<>();
        resultado.directo = inverso; //asigna los valores de inverso al mapa directo
        resultado.inverso = directo; //asigna los valores de directo al mapa inverso
        return resultado; //devuelve los valores de los mapas intercambiados
    }

    public V forcePut(K key, V value) {
        // Comprobar si el valor ya existe en el mapa
        if (directo.containsValue(value)) {
            // Si existe, se elimina la entrada correspondiente
            for (Entry<K, V> entry : entrySet()) {
                if (value.equals(entry.getValue())) {
                    remove(entry.getKey());
                    inverso.remove(entry.getValue());
                    break;
                }
            }
        }
        
        // Comprobar si la clave ya existe en el mapa
        V oldValue = null;
        if (containsKey(key)) {
            // Si existe, se elimina la entrada correspondiente
            oldValue = remove(key);
            inverso.remove(oldValue);
        }
        
        // Insertar la nueva entrada
        directo.put(key, value);
        inverso.put(value, key);
        
        return oldValue;
    }
    
    @Override
    public V remove(Object key) {
        V value = directo.remove(key);
        inverso.remove(value);
        return value;
    }

}
