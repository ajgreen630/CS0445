package edu.pitt.cs.as5;
import java.util.Random;

/**
 ******************************************************************************
 * Assignment 5 CS0445
 ****************************************************************************** 
 * Boggle
 ****************************************************************************** 
 * 
 * Helper class used to represent dice.
 * 
 * 
 * 
 * @author Constantinos Costa (costa.c@cs.pitt.edu)
 * @date  Thursday, November 21, 2019
 *****************************************************************************/
/*****************************************************************************
 * Do not modify this file.
 *****************************************************************************/
public class Die {
	private char[] sides;
	private int currentSideUp;
	public Random randomizer = new Random();

	public Die(char side1, char side2, char side3, char side4, char side5,
			char side6) {
		sides = new char[] { side1, side2, side3, side4, side5, side6 };
		randomize();
	}

	/**
	 * Given the state of the die, retrieves the letter on top.
	 * 
	 * @return String the letter
	 */
	public char getLetter() {
		return sides[currentSideUp];
	}

	/**
	 * Picks a side of the die at random and returns the letter.
	 * 
	 * @return String the letter chosen.
	 */
	public char getRandomFace() {
		randomize();
		return sides[currentSideUp];
	}

	/**
	 * Picks a number between 0 and 5.
	 */
	private void randomize() {
		currentSideUp = randomizer.nextInt(6);
	}
}