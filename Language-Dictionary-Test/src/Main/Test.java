package Main;

import java.util.LinkedList;
import java.util.Random;

public class Test {
    public static void main(String[] args) {
        UnrolledLinkedList Ulist = IO.ReadXML(20);
        LinkedList<WordPair> Llist = IO.ReadXML("");

        System.out.println(Ulist.toString());
        System.out.println(Llist.toString());

        Random random = new Random();

        long startTime;
        long finishTime;

        for (int i = 0; i < 10; i++) {
            WordPair pair = Ulist.get(random.nextInt(Ulist.getSize()));

            startTime = System.nanoTime();
            for (int j = 0; j < 100; j++) {
                Ulist.indexOf(pair);
            }
            finishTime = System.nanoTime();

            System.out.println("Unrolled Linked List took: " + (finishTime - startTime) + " ns");

            startTime = System.nanoTime();
            for (int j = 0; j < 100; j++) {
                Llist.indexOf(pair);
            }
            finishTime = System.nanoTime();

            System.out.println("Linked List took:          " + (finishTime - startTime) + " ns");
            System.out.println();
        }
    }
}
