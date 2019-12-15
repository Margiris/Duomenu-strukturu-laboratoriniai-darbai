package laborai.studijosktu;

import laborai.gui.fx.Lab3WindowFX_1;

import java.util.Comparator;
import java.util.Iterator;
import java.util.Random;
import java.util.Stack;

/**
 * Rikiuojamos objektų kolekcijos - aibės realizacija dvejetainiu paieškos
 * medžiu.
 *
 * @param <E> Aibės elemento tipas. Turi tenkinti interfeisą Comparable<T>, arba
 *            per klasės konstruktorių turi būti paduodamas Comparator<T> interfeisą
 *            tenkinantis objektas.
 * @author darius.matulis@ktu.lt
 * @užduotis Peržiūrėkite ir išsiaiškinkite pateiktus metodus.
 */
public class BstSetKTU<E extends Comparable<E>> implements SortedSetADT<E>, Cloneable {

    // Medžio šaknies mazgas
    protected BstNode<E> root = null;
    // Medžio dydis
    protected int size = 0;
    // Rodyklė į komparatorių
    protected Comparator<? super E> c = null;

    /**
     * Sukuriamas aibės objektas DP-medžio raktams naudojant Comparable<T>
     */
    public BstSetKTU() {
        this.c = (e1, e2) -> e1.compareTo(e2);
    }

    /**
     * Sukuriamas aibės objektas DP-medžio raktams naudojant Comparator<T>
     *
     * @param c Komparatorius
     */
    public BstSetKTU(Comparator<? super E> c) {
        this.c = c;
    }

    /**
     * Patikrinama ar aibė tuščia.
     *
     * @return Grąžinama true, jei aibė tuščia.
     */
    @Override
    public boolean isEmpty() {
        return root == null;
    }

    /**
     * @return Grąžinamas aibėje esančių elementų kiekis.
     */
    @Override
    public int size() {
        return size;
    }

    /**
     * Išvaloma aibė.
     */
    @Override
    public void clear() {
        root = null;
        size = 0;
    }

    /**
     * Patikrinama ar aibėje egzistuoja elementas.
     *
     * @param element - Aibės elementas.
     * @return Grąžinama true, jei aibėje egzistuoja elementas.
     */
    @Override
    public boolean contains(E element) {
        if (element == null) {
            throw new IllegalArgumentException("Element is null in contains(E element)");
        }

        return get(element) != null;
    }

    /**
     * Aibė papildoma nauju elementu.
     *
     * @param element - Aibės elementas.
     */
    @Override
    public void add(E element) {
        if (element == null) {
            throw new IllegalArgumentException("Element is null in add(E element)");
        }

        root = addRecursive(element, root);
    }

    private BstNode<E> addRecursive(E element, BstNode<E> node) {
        if (node == null) {
            size++;
            return new BstNode<>(element);
        }

        int cmp = c.compare(element, node.element);

        if (cmp == -2) {
            Lab3WindowFX_1.print("Not adding duplicate: " + element.toString());
        } else {
            if (cmp < 0) {
                node.left = addRecursive(element, node.left);
            } else if (cmp > 0) {
                node.right = addRecursive(element, node.right);
            } else {
                boolean left = (new Random().nextBoolean());
                Ks.oun(left);

                if (left)
                    node.left = addRecursive(element, node.left);
                else
                    node.right = addRecursive(element, node.right);
            }
        }

        return node;
    }

    /**
     * Pašalinamas elementas iš aibės.
     *
     * @param element - Aibės elementas.
     */
    @Override
    public void remove(E element) {
        if (element == null) {
            throw new IllegalArgumentException("Element is null in remove(E element)");
        }

        root = removeRecursive(element, root);
    }

    private BstNode<E> removeRecursive(E element, BstNode<E> node) {
        if (node == null) {
            return node;
        }
        // Medyje ieškomas šalinamas elemento mazgas;
        int cmp = c.compare(element, node.element);

        if (cmp < 0) {
            node.left = removeRecursive(element, node.left);
        } else if (cmp > 0) {
            node.right = removeRecursive(element, node.right);
        } else if (node.left != null && node.right != null) {
            /* Atvejis kai šalinamas elemento mazgas turi abu vaikus.
             Ieškomas didžiausio rakto elemento mazgas kairiajame pomedyje.
             Galima kita realizacija kai ieškomas mažiausio rakto
             elemento mazgas dešiniajame pomedyje. Tam yra sukurtas
             metodas getMin(E element);
             */
            BstNode<E> nodeMax = getMax(node.left);
            /* Didžiausio rakto elementas (TIK DUOMENYS!) perkeliamas į šalinamo
             elemento mazgą. Pats mazgas nėra pašalinamas - tik atnaujinamas;
             */
            node.element = nodeMax.element;
            // Surandamas ir pašalinamas maksimalaus rakto elemento mazgas;
            node.left = removeMax(node.left);
            size--;
            // Kiti atvejai
        } else {
            node = (node.left != null) ? node.left : node.right;
            size--;
        }

        return node;
    }

    private E get(E element) {
        if (element == null) {
            throw new IllegalArgumentException("Element is null in get(E element)");
        }

        BstNode<E> node = root;
        while (node != null) {
            int cmp = c.compare(element, node.element);

            if (cmp < 0) {
                node = node.left;
            } else if (cmp > 0) {
                node = node.right;
            } else {
                return node.element;
            }
        }

        return null;
    }

    /**
     * Pašalina maksimalaus rakto elementą paiešką pradedant mazgu node
     *
     * @param node
     * @return
     */
    BstNode<E> removeMax(BstNode<E> node) {
        if (node == null) {
            return null;
        } else if (node.right != null) {
            node.right = removeMax(node.right);
            return node;
        } else {
            return node.left;
        }
    }

    /**
     * Grąžina maksimalaus rakto elementą paiešką pradedant mazgu node
     *
     * @param node
     * @return
     */
    BstNode<E> getMax(BstNode<E> node) {
        return get(node, true);
    }

    /**
     * Grąžina minimalaus rakto elementą paiešką pradedant mazgu node
     *
     * @param node
     * @return
     */
    BstNode<E> getMin(BstNode<E> node) {
        return get(node, false);
    }

    private BstNode<E> get(BstNode<E> node, boolean findMax) {
        BstNode<E> parent = null;
        while (node != null) {
            parent = node;
            node = (findMax) ? node.right : node.left;
        }
        return parent;
    }

    /**
     * Grąžinamas aibės elementų masyvas.
     *
     * @return Grąžinamas aibės elementų masyvas.
     */
    @Override
    public Object[] toArray() {
        int i = 0;
        Object[] array = new Object[size];
        for (Object o : this) {
            array[i++] = o;
        }
        return array;
    }

    /**
     * Aibės elementų išvedimas į String eilutę Inorder (Vidine) tvarka. Aibės
     * elementai išvedami surikiuoti didėjimo tvarka pagal raktą.
     *
     * @return elementų eilutė
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (E element : this) {
            sb.append(element.toString()).append(System.lineSeparator());
        }
        return sb.toString();
    }

    /**
     * Medžio vaizdavimas simboliais, žiūr.: unicode.org/charts/PDF/U2500.pdf
     * Tai 4 galimi terminaliniai simboliai medžio šakos gale
     */
    private static final String[] term = {"\u2500", "\u2534", "\u252C", "\u253C"};
    private static final String rightEdge = "\u250C";
    private static final String leftEdge = "\u2514";
    private static final String endEdge = "\u25CF";
    private static final String vertical = "\u2502  ";
    private String horizontal;

    /* Papildomas metodas, išvedantis aibės elementus į vieną String eilutę.
     * String eilutė formuojama atliekant elementų postūmį nuo krašto,
     * priklausomai nuo elemento lygio medyje. Galima panaudoti spausdinimui į
     * ekraną ar failą tyrinėjant medžio algoritmų veikimą.
     *
     * @author E. Karčiauskas
     */
    public String toVisualizedString(String dataCodeDelimiter) {
        horizontal = term[0] + term[0];
        return root == null ? new StringBuilder().append(">").append(horizontal).toString()
                : toTreeDraw(root, ">", "", dataCodeDelimiter);
    }

    private String toTreeDraw(BstNode<E> node, String edge, String indent, String dataCodeDelimiter) {
        if (node == null) {
            return "";
        }
        String step = (edge.equals(leftEdge)) ? vertical : "   ";
        StringBuilder sb = new StringBuilder();
        sb.append(toTreeDraw(node.right, rightEdge, indent + step, dataCodeDelimiter));
        int t = (node.right != null) ? 1 : 0;
        t = (node.left != null) ? t + 2 : t;
        sb.append(indent).append(edge).append(horizontal).append(term[t]).append(endEdge).append(
                split(node.element.toString(), dataCodeDelimiter)).append(System.lineSeparator());
        step = (edge.equals(rightEdge)) ? vertical : "   ";
        sb.append(toTreeDraw(node.left, leftEdge, indent + step, dataCodeDelimiter));
        return sb.toString();
    }

    private String split(String s, String dataCodeDelimiter) {
        int k = s.indexOf(dataCodeDelimiter);
        if (k <= 0) {
            return s;
        }
        return s.substring(0, k);
    }

    /**
     * Sukuria ir grąžina aibės kopiją.
     *
     * @return Aibės kopija.
     * @throws java.lang.CloneNotSupportedException
     */
    @Override
    public Object clone() throws CloneNotSupportedException {
        BstSetKTU<E> cl = (BstSetKTU<E>) super.clone();
        if (root == null) {
            return cl;
        }
        cl.root = cloneRecursive(root);
        cl.size = this.size;
        return cl;
    }

    private BstNode<E> cloneRecursive(BstNode<E> node) {
        if (node == null) {
            return null;
        }

        BstNode<E> clone = new BstNode<>(node.element);
        clone.left = cloneRecursive(node.left);
        clone.right = cloneRecursive(node.right);
        return clone;
    }

    /**
     * Grąžinamas aibės poaibis iki elemento.
     *
     * @param element - Aibės elementas.
     * @return Grąžinamas aibės poaibis iki elemento.
     */
    @Override
    public SetADT<E> headSet(E element) {
        if (root == null || root == element)
            return null;
        BstSetKTU<E> cl = new BstSetKTU<>();

        headSetR(cl, root, element);
        return cl;
    }

    private void headSetR(BstSetKTU<E> cl, BstNode<E> parent, E e) {
        if (parent.element != e) {
            if (parent.left != null)
                headSetR(cl, parent.left, e);
            else if (parent.right != null)
                headSetR(cl, parent.right, e);
            cl.add(parent.element);
        }
    }

    /**
     * adds all elements from given set to base set
     *
     * @param c set which elements to put
     * @return true if added successfully
     */
    public boolean addAll(BstSetKTU<? extends E> c) {
        addAllR((BstNode<E>) c.root);
        return true;
    }

    private void addAllR(BstNode<E> parent) {
        if (parent.left != null)
            addAllR(parent.left);
        else if (parent.right != null)
            addAllR(parent.right);
        this.add(parent.element);
    }

    /**
     * checks if base set contains all elements in given set
     *
     * @param c set which elements base set should contain
     * @return true if base set contains all elements in c set, false otherwise
     */
    public boolean containsAll(BstSetKTU<? extends E> c) {
        return containsAllR(root);
    }

    private boolean containsAllR(BstNode<E> cur) {
        boolean all = true;
        if (!this.contains(cur.element))
            return false;
        if (cur.left != null)
            all = containsAllR(cur.left);
        else if (cur.right != null)
            if (all)
                all = containsAllR(cur.right);
        return all;
    }

    /**
     * Returns the least element in this set greater than or equal to the given element, or null if there is no such element.
     *
     * @param e given element
     * @return element the least greater than e or null if no such
     */
    public E ceiling(E e) {
        if (e == null)
            return null;
        return ceilingR(e, root);
    }

    private E ceilingR(E e, BstNode<E> cur) {
        E toRet = null;
        if (cur.left != null)
            toRet = ceilingR(e, cur.left);
        if (toRet == null) {
            if (cur.element.compareTo(e) >= 0)
                return cur.element;
            else if (cur.right != null)
                toRet = ceilingR(e, cur.right);
        }
        return toRet;
    }

    /**
     * Returns the greatest element in this set less than or equal to the given element, or null if there is no such element.
     *
     * @param e given element
     * @return element the greatest but less than e or null if no such
     */
    public E floor(E e) {
        if (e == null)
            return null;
        return floorR(e, root);
    }

    private E floorR(E e, BstNode<E> cur) {
        int cmp = cur.element.compareTo(e);
        if (cmp == 0)
            return cur.element;
        else if (cmp < 0) { // look for greater
            if (cur.right != null)
                return floorR(e, cur.right);
            return cur.element;
        } else if (cur.left != null) //look for smaller
            return floorR(e, cur.left);
        return null;
    }

    /**
     * return greater element than given or null if no such element
     *
     * @param e given element
     * @return element greater than given or null if no such
     */
    public E higher(E e) {
        if (e == null)
            return null;
        return higherR(e, root);
    }

    private E higherR(E e, BstNode<E> cur) {
        E toRet = null;
        if (cur.left != null)
            toRet = higherR(e, cur.left);
        if (toRet == null) {
            if (cur.element.compareTo(e) > 0)
                return cur.element;
            else if (cur.right != null)
                toRet = higherR(e, cur.right);
        }
        return toRet;
    }

    public E lower(E e) {
        if (e == null)
            return null;
        return lowerR(e, root);
    }

    private E lowerR(E e, BstNode<E> cur) {
        E toRet = null;
        if (cur.left != null)
            toRet = lowerR(e, cur.left);
        if (toRet == null) {
            if (cur.element.compareTo(e) < 0)
                return cur.element;
            else if (cur.right != null)
                toRet = lowerR(e, cur.right);
        }
        return toRet;
    }


    public int height() {
        return heightR(root, 0);
    }

    public int heightR(BstNode<E> cur, int h) {
        if (cur.left == null && cur.right == null)
            return h;

        int h1 = 0;
        int h2 = 0;

        if (cur.left != null)
            h1 = heightR(cur.left, ++h);
        if (cur.right != null)
            h2 = heightR(cur.right, ++h);
        return h1 > h2 ? h1 : h2;
    }

    /**
     * returns last element and removes it from set
     *
     * @return last highest element
     */

    @Override
    public E pollLast() {
        BstNode<E> last = root;
        BstNode<E> prev = root;
        if (last == null)
            return null;
        while (last.right != null) {
            prev = last;
            last = last.right;
        }
        E toReturn = last.element;
        if (last.left != null) {
            last.element = last.left.element;
            last.right = last.left.right;
            last.left = last.left.left;
        } else if (last == root) {
            root = null;
        } else {
            prev.right = null;
        }
        size--;
        return toReturn;
    }

    public E last() {
        BstNode<E> last = root;
        if (last == null)
            return null;
        while (last.right != null) {
            last = last.right;
        }
        return last.element;
    }

    /**
     * Grąžinamas aibės poaibis nuo elemento element1 iki element2.
     *
     * @param element1 - pradinis aibės poaibio elementas.
     * @param element2 - galinis aibės poaibio elementas.
     * @return Grąžinamas aibės poaibis nuo elemento element1 iki element2.
     */
    @Override
    public SetADT<E> subSet(E element1, E element2) {
        return subSet(element1, true, element2, false);
    }

    public void join(BstSetKTU<E> c) {
        BstSetKTU<E> sajunga = new BstSetKTU<>();

        for (E e1 : this) {
            if (c.contains(e1))
                sajunga.add(e1);
        }
    }

    @SuppressWarnings({"Duplicates", "unused"})
    public SortedSetADT<E> subSet(E fromElement, boolean fromInclusive, E toElement, boolean toInclusive) {
        if (fromElement == null || toElement == null)
            return null;

        BstSetKTU<E> cl = new BstSetKTU<>();
        boolean put = false;

        for (E el : this) {
            if (el == toElement) {
                if (toInclusive)
                    cl.add(el);
                break;
            }

            if (put)
                cl.add(el);

            if (el == fromElement) {
                if (fromInclusive)
                    cl.add(el);
                put = true;
            }
        }

        for (E el : this) {
            if (el == fromElement)
                put = true;
            if (el == toElement)
                break;
            if (put)
                cl.add(el);
        }
        return cl;
    }

    /**
     * Grąžinamas aibės poaibis nuo elemento.
     *
     * @param element - Aibės elementas.
     * @return Grąžinamas aibės poaibis nuo elemento.
     */
    @Override
    public SetADT<E> tailSet(E element) {
        if (element == null)
            return null;
        BstSetKTU<E> cl = new BstSetKTU<E>();
        boolean put = false;
        for (E el : this) {
            if (el == element)
                put = true;
            if (put)
                cl.add(el);
        }
        return cl;
    }

    /**
     * Grąžinamas tiesioginis iteratorius.
     *
     * @return Grąžinamas tiesioginis iteratorius.
     */
    @Override
    public Iterator<E> iterator() {
        return new IteratorKTU(true);
    }

    /**
     * Grąžinamas atvirkštinis iteratorius.
     *
     * @return Grąžinamas atvirkštinis iteratorius.
     */
    @Override
    public Iterator<E> descendingIterator() {
        return new IteratorKTU(false);

    }

    /**
     * Vidinė objektų kolekcijos iteratoriaus klasė. Iteratoriai: didėjantis ir
     * mažėjantis. Kolekcija iteruojama kiekvieną elementą aplankant vieną kartą
     * vidine (angl. inorder) tvarka. Visi aplankyti elementai saugomi steke.
     * Stekas panaudotas iš java.util paketo, bet galima susikurti nuosavą.
     */
    private class IteratorKTU implements Iterator<E> {

        private Stack<BstNode<E>> stack = new Stack<>();
        // Nurodo iteravimo kolekcija kryptį, true - didėjimo tvarka, false - mažėjimo
        private boolean ascending;
        // Nurodo einamojo medžio elemento tėvą. Reikalingas šalinimui.
        private BstNode<E> parent = root;
        private BstNode<E> cur = root;

        IteratorKTU(boolean ascendingOrder) {
            this.ascending = ascendingOrder;
            this.toStack(root);
        }

        @Override
        public boolean hasNext() {
            return !stack.empty();
        }

        @Override
        public E next() {
            if (!stack.empty()) {
                // Grąžinamas paskutinis į steką patalpintas elementas
                cur = stack.pop();
                // Atsimenama tėvo viršunė. Reikia remove() metodui
                parent = (!stack.empty()) ? stack.peek() : root;
                BstNode node = (ascending) ? cur.right : cur.left;
                // Dešiniajame n pomedyje ieškoma minimalaus elemento,
                // o visi paieškos kelyje esantys elementai talpinami į steką
                toStack(node);
                return cur.element;
            } else { // Jei stekas tuščias
                return null;
            }
        }

        @Override
        public void remove() {
            BstNode cn = (ascending) ? parent.left : parent.right;
            if (cur == root) {
                root = remove(root);
            } else if (cn == null) {
                return;
            } else if (cn == cur) {
                if (ascending)
                    parent.left = remove(parent.left);
                else
                    parent.right = remove(parent.right);
            } else {
                if (parent != root)
                    parent = cn;
                while (((ascending) ? parent.right : parent.left) != cur) {
                    parent = (ascending) ? parent.right : parent.left;
                }
                if (ascending)
                    parent.right = remove(parent.right);
                else
                    parent.left = remove(parent.left);
            }
        }

        private BstNode remove(BstNode n) {
            if (((ascending) ? n.left.right : n.right.left) == null) {
                if (ascending) {
                    n.left.right = n.right;
                    return n.left;
                } else {
                    n.right.left = n.left;
                    return n.right;
                }
            } else {
                BstNode rep = getMax(((ascending) ? n.left : n.right));
                rep.left = n.left;
                rep.right = n.right;
                return rep;
            }
        }

        private BstNode getMax(BstNode n) {
            BstNode prev = n;
            while (((ascending) ? n.right : n.left) != null) {
                prev = n;
                n = ((ascending) ? n.right : n.left);
            }
            if (ascending) {
                prev.right = n.left;
            } else {
                prev.left = n.right;
            }
            return n;
        }


        private void toStack(BstNode<E> n) {
            while (n != null) {
                stack.push(n);
                n = (ascending) ? n.left : n.right;
            }
        }
    }

    /**
     * Vidinė kolekcijos mazgo klasė
     *
     * @param <N> mazgo elemento duomenų tipas
     */
    protected class BstNode<N> {

        // Elementas
        protected N element;
        // Rodyklė į kairįjį pomedį
        protected BstNode<N> left;
        // Rodyklė į dešinįjį pomedį
        protected BstNode<N> right;

        protected BstNode() {
        }

        protected BstNode(N element) {
            this.element = element;
            this.left = null;
            this.right = null;
        }
    }
}
