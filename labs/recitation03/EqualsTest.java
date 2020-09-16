package cs445.lab3;

import java.util.Random;

public class EqualsTest {

    public static void main(String[] args) {
        BagInterface<String> bag1 = new ArrayBag<>();
        BagInterface<String> bag2 = new ArrayBag<>();
        BagInterface<Integer> bag3 = new ArrayBag<>();
        System.out.println("Testing ArrayBag.equals():");
        testEquals(bag1, bag2, bag3);

        bag1 = new LinkedBag<>();
        bag2 = new LinkedBag<>();
        bag3 = new LinkedBag<>();
        System.out.println("Testing LinkedBag.equals():");
        testEquals(bag1, bag2, bag3);
    }

    private static void testEquals(BagInterface<String> bag1, BagInterface<String> bag2, BagInterface<Integer> bag3) {
        System.out.println("empty, empty:\t" + bag1.equals(bag2));
        System.out.println("empty, null:\t" + bag1.equals(null));
        System.out.println("empty, String:\t" + bag1.equals("A string"));
        bag1.add("a"); bag3.add(1);
        System.out.println("String Bag, Int Bag:\t" + bag1.equals(bag3));
        bag1.clear();

        // Test strings, including different numbers of duplicates
        String[] testStrings = new String[] {
            "abc",
            "def",
            "ghi",
            "jkl",
            "mnop",
            "qrs",
            "tuv",
            "wxyz",
            "abc",
            "abc",
            "abc",
            "mnop",
            ".",
            ".",
            "."
        };

        for (String s : testStrings) {
            bag1.add(s);
        }
        // Shuffle the test strings so they are added to bag2 in a different
        // order
        shuffle(testStrings);
        for (String s : testStrings) {
            bag2.add(s);
        }
        System.out.println("diff. order:\t" + bag1.equals(bag2));

        // Remove 1 instance of "." from bag1
        bag1.remove(".");
        System.out.println("after remove:\t" + bag1.equals(bag2));
    }

    /**
     * Implements the Fisher-Yates shuffle on generic array
     * @param array The array to shuffle
     */
    private static <T> void shuffle(T[] array) {
        int index;
        T temp;
        Random random = new Random();
        for (int i = array.length - 1; i > 0; i--) {
            index = random.nextInt(i + 1);
            temp = array[index];
            array[index] = array[i];
            array[i] = temp;
        }
    }

}

