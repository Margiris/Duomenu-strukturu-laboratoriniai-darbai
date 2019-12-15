package laborai.studijosktu;

import java.util.List;
import java.util.Set;

/**
 *
 * @param <K> raktas
 * @param <V> reikšmė
 */
public interface MapADTp<K, V> extends MapADT<K, V> {

    /**
     * Grąžina maksimalų grandinėlės ilgį.
     *
     * @return Maksimalus grandinėlės ilgis.
     */
    int getMaxChainSize();

    /**
     * Grąžina maišos lentelę formuojant įvykusių permaišymų kiekį.
     *
     * @return Permaišymų kiekis.
     */
    int getRehashesCounter();

    /**
     * Grąžina maišos lentelės talpą.
     *
     * @return Maišos lentelės talpa.
     */
    int getTableCapacity();

    /**
     * Grąžina paskutinės papildytos grandinėlės indeksą.
     *
     * @return Paskutinės papildytos grandinėlės indeksas.
     */
    int getLastUpdatedChain();

    /**
     * Grąžina grandinėlių kiekį.
     *
     * @return Grandinėlių kiekis.
     */
    int getChainsCounter();

    boolean containsValue(Object value);
    Set<K> keySet();
    float averageChainLength();
    V putIfAbsent (K key, V value);
    List<V> values();
    int numberOfEmpties();
}
