package edu.pitt.cs.as5;
//	BoggleDictionary.java

import java.io.File;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Scanner;

/**
 * *****************************************************************************
 * Assignment 5 CS0445
 * *****************************************************************************
 * Boggle
 * *****************************************************************************
 * <p>
 * This class stores a dictionary containing words that can be used in a
 * Boggle game.
 *
 * @author Constantinos Costa (costa.c@cs.pitt.edu)
 * @date Thursday, November 21, 2019
 *****************************************************************************/

public class BoggleDictionary implements DictionaryInterface {
    private HashSet<String> dictionary;

    /**
     * Create the BoggleDictionary from the file
     */

    BoggleDictionary() {
        dictionary = new HashSet<String>();
    }

    /**
     * Create the BoggleDictionary from the file
     *
     * @param fname the file name to be loaded
     * @use addWord method
     */
    public BoggleDictionary(String fname) throws Exception {
        dictionary = new HashSet<String>();
        Scanner in = new Scanner(new File(fname));

        while (in.hasNext()) {
            String word = in.next();
            word = word.toUpperCase();
            addWord(word);
        }

    }

    /**
     * Check to see if a string is in the dictionary to determine whether it is
     * a valid word.
     *
     * @param word the string to check for
     * @return true if word is in the dictionary, false otherwise.
     */
    public boolean checkWord(String word) {
        return dictionary.contains(word);
    }

    @Override
    public void addWord(String word) {
        dictionary.add(word);
    }

    /**
     * Check to see if a word in the dictionary starts with
     * the specified word
     *
     * @param word the string to check for
     * @return always true (no efficient way to check otherwise)
     */
    @Override
    public boolean checkWordBeginning(String word) {
        return true;
    }

    /**
     * Get an iterator that returns all the words in the dictionary, one at a
     * time.
     *
     * @return an iterator that can be used to get all the words in the
     * dictionary.
     */
    public Iterator<String> iterator() {
        return dictionary.iterator();
    }

    /**
     * Main entry point
     */
    static public void main(String[] args) {
        System.out.println("BoggleDictionary Program ");

        Scanner kbd = new Scanner(System.in);
        BoggleDictionary theDictionary = null;
        try {
            theDictionary = new BoggleDictionary();
        } catch (Exception ioe) {
            System.err.println("error reading dictionary");
            System.exit(1);
        }
        String word;

        while (kbd.hasNext()) {
            word = kbd.next();
            if (theDictionary.checkWord(word))
                System.out.println(word + " is in the dictionary");
            else
                System.out.println(word + " is not in the dictionary");
        }

        Iterator<String> iter = theDictionary.iterator();
        while (iter.hasNext())
            System.out.println(iter.next());

    }

}
