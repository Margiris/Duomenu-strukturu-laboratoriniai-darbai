package Main;

public class UnrolledLinkedList {
    class ULLNode {
        ULLNode next;
        int elementsCount;
        WordPair[] wordPairs;

        ULLNode(int n) {
            next = null;
            elementsCount = 0;
            wordPairs = new WordPair[n];
        }
    }

    private ULLNode start;
    private ULLNode end;
    private int nodeLength;
    private int size;

    public UnrolledLinkedList() {
        this(6);
    }

    public UnrolledLinkedList(int capacity) {
        start = null;
        end = null;
        size = 0;
        nodeLength = capacity + 1;
    }

    public void add(WordPair pair) {
        size++;

        if (start == null) {
            start = new ULLNode(nodeLength);
            start.wordPairs[0] = pair;
            start.elementsCount++;
            end = start;
        } else if (end.elementsCount + 1 < nodeLength) {
            end.wordPairs[end.elementsCount++] = pair;
        } else {
            ULLNode node = new ULLNode(nodeLength);
            int j = 0;

            for (int i = end.elementsCount / 2 + 1; i < end.elementsCount; i++) {
                node.wordPairs[j++] = end.wordPairs[i];
            }

            node.wordPairs[j++] = pair;
            node.elementsCount = j;
            end.elementsCount = end.elementsCount / 2 + 1;
            end.next = node;
            end = node;
        }
    }

    public void clear() {
        start = null;
        end = null;
        size = 0;
    }

    public boolean contains(WordPair pair) {
        return (indexOf(pair) != -1);
    }

    public int indexOf(WordPair pair) {
        int index = 0;
        ULLNode current = start;

        while (current != null) {
            for (int i = 0; i < current.elementsCount; i++) {
                if (pair.equals(current.wordPairs[i])) {
                    return index + i;
                }
            }
            index += current.elementsCount;
            current = current.next;
        }
        return -1;
    }

    public boolean isEmpty() {
        return start == null;
    }

    public WordPair get(int index) {
        ULLNode node;
        int p = 0;
        node = start;

        while (p <= index - node.elementsCount) {
            p += node.elementsCount;
            node = node.next;
        }
        return node.wordPairs[index - p];
    }

    public int getNodesCount() {
        return Math.floorDiv(size, start.elementsCount);
    }

    public int getSize() {
        return size;
    }

    public WordPair[] toArray() {
        WordPair[] array = new WordPair[size];
        int p = 0;

        for (ULLNode current = start; current != null; current = current.next) {
            for (int i = 0; i < current.elementsCount; i++) {
                array[p] = current.wordPairs[i];
                p++;
            }
        }
        return array;

    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder("Unrolled Linked List =\n");

        if (size == 0) {
            return stringBuilder.append(" empty").toString();
        }

        ULLNode current = start;

        while (current != null) {
            for (int i = 0; i < current.elementsCount; i++) {
                stringBuilder.append(current.wordPairs[i]).append(" ");
            }
            stringBuilder.append("\n");
            current = current.next;
        }

        return stringBuilder.toString();
    }
}
