package tasks;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.*;

public class FindNumberInListOfInts {

    @Test
    public void test() {
        Assertions.assertTrue(findNumber(Arrays.asList(1,1,5,8), Arrays.asList(1,5)));
        Assertions.assertTrue(findNumber(Arrays.asList(1,1,5,8), Arrays.asList(1)));
        Assertions.assertTrue(findNumber(Arrays.asList(1,1,5,8,0,3,5,1), Arrays.asList(5,1)));
        Assertions.assertFalse(findNumber(Arrays.asList(1,1,5,8,0,3,5,1), Arrays.asList(5,8,0,5)));
        Assertions.assertFalse(findNumber(Arrays.asList(1,1,5,8,0,3,5,1), Arrays.asList(9)));
        Assertions.assertTrue(findNumber(Arrays.asList(1,1,5,8,0,3,5,1), Arrays.asList(0)));

        Assertions.assertTrue(knuthMorrisPratt(Arrays.asList(1,1,5,8), Arrays.asList(1,5)));
        Assertions.assertTrue(knuthMorrisPratt(Arrays.asList(1,1,5,8), Arrays.asList(1)));
        Assertions.assertTrue(knuthMorrisPratt(Arrays.asList(1,1,5,8,0,3,5,1), Arrays.asList(5,1)));
        Assertions.assertFalse(knuthMorrisPratt(Arrays.asList(1,1,5,8,0,3,5,1), Arrays.asList(5,8,0,5)));
        Assertions.assertFalse(knuthMorrisPratt(Arrays.asList(1,1,5,8,0,3,5,1), Arrays.asList(9)));
        Assertions.assertTrue(knuthMorrisPratt(Arrays.asList(1,1,5,8,0,3,5,1), Arrays.asList(0)));

        Assertions.assertTrue(findNumber2(Arrays.asList(1,1,5,8), Arrays.asList(1,5)));
        Assertions.assertTrue(findNumber2(Arrays.asList(1,1,5,8), Arrays.asList(1)));
        Assertions.assertTrue(findNumber2(Arrays.asList(1,1,5,8,0,3,5,1), Arrays.asList(5,1)));
        Assertions.assertFalse(findNumber2(Arrays.asList(1,1,5,8,0,3,5,1), Arrays.asList(5,8,0,5)));
        Assertions.assertFalse(findNumber2(Arrays.asList(1,1,5,8,0,3,5,1), Arrays.asList(9)));
        Assertions.assertTrue(findNumber2(Arrays.asList(1,1,5,8,0,3,5,1), Arrays.asList(0)));
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

    private boolean findNumber2(List<Integer> l, List<Integer> number) {
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
