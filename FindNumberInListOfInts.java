package tasks;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

public class FindNumberInListOfInts {

    @Test
    public void test() {
        Assertions.assertTrue(findNumber(Arrays.asList(1,1,5,8), Arrays.asList(1,5)));
        Assertions.assertTrue(findNumber(Arrays.asList(1,1,5,8), Arrays.asList(1)));
        Assertions.assertTrue(findNumber(Arrays.asList(1,1,5,8,0,3,5,1), Arrays.asList(5,1)));
        Assertions.assertFalse(findNumber(Arrays.asList(1,1,5,8,0,3,5,1), Arrays.asList(5,8,0,5)));
        Assertions.assertFalse(findNumber(Arrays.asList(1,1,5,8,0,3,5,1), Arrays.asList(9)));
        Assertions.assertTrue(findNumber(Arrays.asList(1,1,5,8,0,3,5,1), Arrays.asList(0)));
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
}
