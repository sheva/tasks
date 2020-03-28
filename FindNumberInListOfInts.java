package tasks;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.*;

public class FindNumberInListOfInts {

    @Test
    public void testFindNumber1() {
        Assertions.assertTrue(findNumber(Arrays.asList(1, 1, 5, 8), Arrays.asList(1, 5)));
        Assertions.assertTrue(findNumber(Arrays.asList(1, 1, 5, 8), Arrays.asList(1)));
        Assertions.assertTrue(findNumber(Arrays.asList(1, 1, 5, 8, 0, 3, 5, 1), Arrays.asList(5, 1)));
        Assertions.assertFalse(findNumber(Arrays.asList(1, 1, 5, 8, 0, 3, 5, 1), Arrays.asList(5, 8, 0, 5)));
        Assertions.assertFalse(findNumber(Arrays.asList(1, 1, 5, 8, 0, 3, 5, 1), Arrays.asList(9)));
        Assertions.assertTrue(findNumber(Arrays.asList(1, 1, 5, 8, 0, 3, 5, 1), Arrays.asList(0)));
    }

    @Test
    public void testFindNumber_Iterators() {
        Assertions.assertTrue(findNumber_Iterators(Arrays.asList(1, 1, 5, 8), Arrays.asList(1, 5)));
        Assertions.assertTrue(findNumber_Iterators(Arrays.asList(1, 1, 5, 8), Arrays.asList(1)));
        Assertions.assertTrue(findNumber_Iterators(Arrays.asList(1, 1, 5, 8, 0, 3, 5, 1), Arrays.asList(5, 1)));
        Assertions.assertFalse(findNumber_Iterators(Arrays.asList(1, 1, 5, 8, 0, 3, 5, 1), Arrays.asList(5, 8, 0, 5)));
        Assertions.assertFalse(findNumber_Iterators(Arrays.asList(1, 1, 5, 8, 0, 3, 5, 1), Arrays.asList(9)));
        Assertions.assertTrue(findNumber_Iterators(Arrays.asList(1, 1, 5, 8, 0, 3, 5, 1), Arrays.asList(0)));
    }

    @Test
    public void testFindNumber_knuthMorrisPratt() {
        Assertions.assertTrue(knuthMorrisPratt(Arrays.asList(1, 1, 5, 8), Arrays.asList(1, 5)));
        Assertions.assertTrue(knuthMorrisPratt(Arrays.asList(1, 1, 5, 8), Arrays.asList(1)));
        Assertions.assertTrue(knuthMorrisPratt(Arrays.asList(1, 1, 5, 8, 0, 3, 5, 1), Arrays.asList(5, 1)));
        Assertions.assertFalse(knuthMorrisPratt(Arrays.asList(1, 1, 5, 8, 0, 3, 5, 1), Arrays.asList(5, 8, 0, 5)));
        Assertions.assertFalse(knuthMorrisPratt(Arrays.asList(1, 1, 5, 8, 0, 3, 5, 1), Arrays.asList(9)));
        Assertions.assertTrue(knuthMorrisPratt(Arrays.asList(1, 1, 5, 8, 0, 3, 5, 1), Arrays.asList(0)));
    }

    @Test
    public void testFindNumber_Nodes() {
        Assertions.assertTrue(findNumber(Node.constructNodes(1,1,5,8), Node.constructNodes(1, 5)));
        Assertions.assertTrue(findNumber(Node.constructNodes(1,1,5,8), Node.constructNodes(1)));
        Assertions.assertTrue(findNumber(Node.constructNodes(1,1,5,8,0,3,5,1), Node.constructNodes(5,1)));
        Assertions.assertFalse(findNumber(Node.constructNodes(1,1,5,8,0,3,5,1), Node.constructNodes(5,8,0,5)));
        Assertions.assertFalse(findNumber(Node.constructNodes(1,1,5,8,0,3,5,1), Node.constructNodes(9)));
        Assertions.assertTrue(findNumber(Node.constructNodes(1,1,5,8,0,3,5,1), Node.constructNodes(0)));
    }

    // Time complexity - O(l.size() * number.size())
    private boolean findNumber(List<Integer> l, List<Integer> number) {
        for (int i = 0; i < l.size(); i++) {
            int j = i, k = i;
            while(j < l.size() && j - i < number.size() && number.get(j - i).equals(l.get(j))) { j++; }
            if (j - i == number.size()) return true;
            else i = k;
        }
        return false;
    }

    private static class Node<T> implements Iterator<Node<T>> {
        private Node<T> next;
        private T value;

        private Node() {}

        private Node(T value, Node<T> next) {
            this.next = next;
            this.value = value;
        }

        private int size() {
            return 1 + (hasNext() ? next.size() : 0);
        }

        @Override
        public boolean hasNext() {
            return next != null;
        }

        @Override
        public Node<T> next() {
            return next;
        }

        private static Node<Integer> constructNodes(int... digits) {
            Node<Integer> dummy = new Node<>(1, new Node<>());
            Node<Integer> c = dummy.next;
            for (int i = 0; i < digits.length; i++) {
                c.value = digits[i];
                if (i < digits.length - 1) c.next = new Node<>();
                c = c.next;
            }
            return dummy.next;
        }
    }

    private boolean findNumber(Node<Integer> l, Node<Integer> number) {
        Node<Integer> c = l;
        while (c.hasNext()) {
            Node<Integer> n = number;
            Node<Integer> d = c;
            while(d.value.equals(n.value)) {
                if (!n.hasNext()) return true;
                d = d.next;
                n = n.next;
            }
            c = c.next;
        }
        return false;
    }

    private boolean findNumber_Iterators(List<Integer> l, List<Integer> number) {
        int checked = 0;
        while (checked < l.size()) {
            Iterator<Integer> listIter = l.iterator();
            int i = 0;
            while(i < checked) {
                listIter.next();
                i++;
            }
            Iterator<Integer> numberIter = number.iterator();
            while(numberIter.hasNext()) {
                if (listIter.next().equals(numberIter.next())) {
                    if (!numberIter.hasNext()) return true;
                } else {
                    break;
                }
            }
            checked++;
        }
        return false;
    }

    private boolean knuthMorrisPratt(List<Integer> l, List<Integer> number) {
        int[] prefixTable = new int[number.size() + 1];
        int i = 1, j = 0;
        while(i < number.size()) {
            if (number.get(i).equals(number.get(j))) prefixTable[i++] = ++j;
            else if (j > 0) j = prefixTable[j-1];
            else prefixTable[i++] = 0;
        }

        i = 0; j = 0;
        while (i < l.size()) {
            if (l.get(i).equals(number.get(j))) {
                i++; j++;

                if (j == number.size()) return true;
            }
            else if (j > 0) j = prefixTable[j - 1];
            else i++;
        }
        return false;
    }
}
