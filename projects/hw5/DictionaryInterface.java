package edu.pitt.cs.as5;

/**
 ******************************************************************************
 * Assignment 5 CS0445
 ******************************************************************************
 * Boggle
 ******************************************************************************
 *
 * This is the interface for all of the dictionaries
 *
 *
 *
 * @author Constantinos Costa (costa.c@cs.pitt.edu)
 * @date  Thursday, November 21, 2019
 *****************************************************************************/
/*****************************************************************************
 * Do not modify this file.
 *****************************************************************************/
public interface DictionaryInterface {

    /**
     * This method checks if a word is in the dictionary specified by
     * buildDictionary using binary search.
     *
     * @param word
     *            the word to be checked
     * @return true when the word is in the dictionary, false otherwise
     * @see BoggleGUI
     */
    boolean checkWord(String word);



    /**
     * This method add a word in the dictionary specified by
     * buildDictionary.
     *
     * @param word
     *            the word to be added
     *
     */

    void addWord(String word);

    /**
     * This method checks if a word in the tree starts
     * with the specified word
     *
     * @param word
     *            the word to be checked
     * @return true when a word match the beggining only of a
     * 			word in the dictionary, false otherwise
     * @see BoggleGUI
     */
     boolean checkWordBeginning(String word);
}