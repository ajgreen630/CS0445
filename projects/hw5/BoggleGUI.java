package edu.pitt.cs.as5;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.HashSet;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;

/**
 ****************************************************************************** 
 * Assignment 5 CS0445
 ****************************************************************************** 
 * Boggle
 ****************************************************************************** 
 * 
 * This application builds a Boggle game. This class open a graphical user
 * interface.
 * 
 * 
 * 
 * @author  Constantinos Costa (costa.c@cs.pitt.edu)
 * @date  Thursday, November 21, 2019
 *****************************************************************************/
/*****************************************************************************
 * Do not modify this file.
 *****************************************************************************/
public class BoggleGUI extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5881839753817938851L;
	private static Boggle boggle;

	private BoggleBoard theBoard;
	private ScoreArea humanArea, computerArea;
	private WordEntryField wordEntryField;

	public BoggleGUI() {
		super("Welcome to CS 0445!");

		try {
			char[][] grid = {{'S', 'O', 'R', 'N'}, {'H', 'D', 'E', 'A'},
					{'S', 'B', 'O', 'S'}, {'X', 'N', 'I', 'H'}};
			boggle = new Boggle(grid);
		} catch (Exception e) {
			e.printStackTrace();
		}

		// Intialize graphics panels
		initPanels();
		// Establish menu bar options and listeners for them
		setUpMenuBar();
		// WindowClosing listener: for JDK 1.2 compatibility
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
		// Trust contained components to set their sizes; top level frame packs
		pack();
		//Create a new game based on your loaded of the file
		newGame(boggle.grid);
	}

	/**
	 * Get ready for a new game, given a letter list specification of the Boggle
	 * board.
	 * 
	 * @param letterList
	 *            specification of the board
	 * @return void
	 */
	public void newGame(char[][] letterList) {
		// Tell theBoard about the board layout
		theBoard.setBoard(letterList);
		// Prepare score areas
		humanArea.setReady();
		computerArea.setReady();
		// Clear board highlighting
		theBoard.unHighlightAllDice();
		// Allow edits to text area and set focus
		wordEntryField.setReady();
		// redraw the whole thing so it looks as nice as can be
		repaint();
	}

	/**
	 * Consider a word entered by the human player.
	 * 
	 * @param wordToCheck
	 *            word entered by human
	 * @return Nothing, but highlight the letters when on board
	 */
	public void checkAndAddWordHuman(String wordToCheck) {

		// clear any board highlighting
		theBoard.unHighlightAllDice();

		// if empty word, the computer gets to take its turn
		if (wordToCheck.length() == 0) {
			wordEntryField.setUnready();
			computerPlay();
			return;
		}

		// check that word is:
		// (1) at least minimum length, (2) on the board, (3) in the lexicon,
		// (4) not already entered by the player
		if (wordToCheck.length() < Boggle.MINIMUMWORDLENGTH) {
			chideUser(wordToCheck, "Less Than " + Boggle.MINIMUMWORDLENGTH
					+ " Letters");
			return;
		}

		boolean[][] letterLocations = boggle.findWord(wordToCheck);

		// we just trust the computer player to check this
		if (letterLocations == null) {
			chideUser(wordToCheck, "Not On Board or Not In Lexicon");
			return;
		}

		if (humanArea.containsWord(wordToCheck)) {
			chideUser(wordToCheck, "Duplicate Word");
			return;
		}

		// OK, this word passed our rigorous suite of tests. Add it
		humanArea.addWord(wordToCheck);
		// Highlight locations on board
		theBoard.highlightDice(letterLocations);
		wordEntryField.clear(); // clear the wordEntryField text
	}

	/**
	 * Print a message that the human player did something wrong.
	 * 
	 * @param attemptedWord
	 *            The word the human entered complaint Description of what went
	 *            wrong
	 * @return void
	 */

	public void chideUser(String attemptedWord, String complaint) {
		JOptionPane.showMessageDialog(this, attemptedWord + ": " + complaint
				+ "!!!", complaint, JOptionPane.ERROR_MESSAGE);
		wordEntryField.clear(); // clear the wordEntryField text
	}

	/**
	 * Let the computer player take its turn.
	 * 
	 * @param none
	 * @return nothing, but highlights words computer got and computes its score
	 */
	public void computerPlay() {
		computerArea.setName("Thinking!");
		computerArea.paintImmediately(computerArea.getVisibleRect());

		String[] allWords = boggle.findWords();
		computerArea.setName("Computer");
		for (String newWord : allWords) {
			// Add word to appropriate score area & highlight appropriate dice
			computerArea.addWord(newWord);
			theBoard.highlightDice(boggle.findWord(newWord));
		}

		theBoard.unHighlightAllDice(); // leave board unhighlighted when done

		// computerPlayer.debug(); //Show debug info
	}

	/**
	 * Initialize graphic display.
	 * 
	 * @param none
	 * @return void
	 */

	private void initPanels() {
		Container contentPane = getContentPane();
		humanArea = new ScoreArea("Me");
		computerArea = new ScoreArea("Computer");
		theBoard = new BoggleBoard(Boggle.DICEROWS, Boggle.DICECOLS);
		wordEntryField = new WordEntryField();
		contentPane.add(wordEntryField, BorderLayout.SOUTH);
		contentPane.add(humanArea, BorderLayout.WEST);
		contentPane.add(theBoard, BorderLayout.CENTER);
		contentPane.add(computerArea, BorderLayout.EAST);
	}

	/**
	 * Initialize menus for controlling game.
	 * 
	 * @param none
	 * @return void
	 */
	private void setUpMenuBar() {
		// Set Up Menu Bar
		JMenuBar menu = new JMenuBar();

		// Game Menu
		JMenu gameMenu = new JMenu("Game");
		gameMenu.setMnemonic('G');
		menu.add(gameMenu);

		JMenuItem newRandom = new JMenuItem("New Random");
		gameMenu.add(newRandom);
		newRandom.setMnemonic('N');
		newRandom.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				newGame(boggle.getRandomBoard());
			}
		});

		JMenuItem quitGame = new JMenuItem("Quit");
		gameMenu.add(quitGame);
		quitGame.setMnemonic('Q');
		quitGame.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});

		// Help menu
		JMenu helpMenu = new JMenu("Help");
		menu.add(helpMenu);
		helpMenu.setMnemonic('H');

		JMenuItem aboutGame = new JMenuItem("About...");
		helpMenu.add(aboutGame);
		aboutGame.setMnemonic('A');
		aboutGame.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(BoggleGUI.this,
						"It's ABOUT time you got to work", "About Game",
						JOptionPane.PLAIN_MESSAGE);
			}
		});
		setJMenuBar(menu);
	}

	/**
	 * A class defining the visual appearance of a Boggle board, and defining
	 * some related methods.
	 */
	static class BoggleBoard extends JPanel {
		/**
		 *
		 */
		private static final long serialVersionUID = 7301932333937021397L;

		public static final Color BACKGROUNDCOLOR = new Color(255, 219, 13);

		private DiePanel theDice[][];
		private int rows, cols;

		BoggleBoard(int rows, int cols) {
			this.rows = rows;
			this.cols = cols;

			// create a JPanel with rowsXcols GridLayout to hold the DiePanels
			JPanel innerPanel = new JPanel();
			innerPanel.setLayout(new GridLayout(rows, cols, 1, 1));
			innerPanel.setBackground(BACKGROUNDCOLOR);

			// Create Individual DiePanels, and add them
			theDice = new DiePanel[rows][cols];
			for (int row = 0; row < rows; row++) {
				for (int col = 0; col < cols; col++) {
					theDice[row][col] = new DiePanel();

					innerPanel.add(theDice[row][col]);
				}
			}
			innerPanel.setBorder(BorderFactory.createMatteBorder(10, 10, 10,
					10, BACKGROUNDCOLOR));
			this.add(innerPanel);
		}

		/**
		 * Non-randomly reset the board according to an array of Strings showing
		 * the sequence of faces on the new board. Return the array.
		 *
		 * @param letterList
		 *            an array of strings to use for the board
		 * @return void
		 */
		public void setBoard(char[][] letterList) {
			if (letterList == null) {
				throw new IllegalArgumentException(
						"setBoard(): String array length is not " + rows * cols);
			}

			// Set the DiePanels with given letters
			for (int row = 0; row < rows; row++) {
				for (int col = 0; col < cols; col++) {
					theDice[row][col].setFace(letterList[row][col]);
				}
			}
		}

		/**
		 * Find the locations of the letters for a legal word. We cheat and let
		 * the computer player do this for us.
		 *
		 * @param word
		 *            the word to fnd on the board
		 * @return Vector of locations, one for each letter
		 * @see BogglePlayer
		 */
		public boolean[][] getLocationsForWord(String word) {
			return boggle.findWord(word);
		}

		/**
		 * Given a list of locations, recreate the word at the locations.
		 *
		 * @param locations
		 *            Vector containing the letter placements
		 * @return String of letters
		 */
		public String getWordFromLocations(Vector locations) {
			int loc;
			int row, col;
			if (locations == null)
				return null;
			String result = "";
			for (int i = 0; i < locations.size(); i++) {
				loc = ((Integer) locations.get(i)).intValue();
				row = loc / rows;
				col = loc % cols;
				result += theDice[row][col].getFace();
			}
			return result;
		}

		/**
		 * Highlight the dice corresponding to the chosen word.
		 *
		 * @param locations
		 *            for each die
		 * @return void
		 */

		public void highlightDice(boolean[][] locations) {
			if (locations == null)
				return;

			unHighlightAllDice();
			for (int i = 0; i < locations.length; i++) {
				for (int j = 0; j < locations.length; j++) {
					if (locations[i][j])
						highlightDie(i, j);
				}
			}
			this.paintImmediately(this.getVisibleRect());
		}

		/**
		 * Highlight the specified die, given row and column.
		 *
		 * @param row
		 *            col
		 * @return void
		 */
		public void highlightDie(int row, int column) {
			theDice[row][column].highlight();
		}

		/**
		 * Highlight the specified die, given Integer offset from
		 * upper-left-hand corner, using left-to-right, top-to-bottom ordering
		 * of dice.
		 *
		 * @param indx
		 *            the die's location
		 * @return void
		 */
		public void highlightDie(Integer indx) {
			int i = indx.intValue();
			int row = i / theDice[0].length;
			int col = i % theDice[0].length;
			highlightDie(row, col);
		}

		/**
		 * Unhighlight all dice.
		 */
		public void unHighlightAllDice() {
			for (int row = 0; row < theDice.length; row++) {
				for (int col = 0; col < theDice[row].length; col++) {
					theDice[row][col].unHighlight();
				}
			}
			this.paintImmediately(this.getVisibleRect());
		}

		/**
		 * For displaying one Die on the board
		 */
		static class DiePanel extends JPanel {
			private char face;
			private boolean isHighlighted;
			private JLabel faceLabel;

			private final Color DIECOLOR = new Color(230, 230, 230);
			private final Color FACECOLOR = new Color(3, 51, 217);

			private final Font FACEFONT = new Font("SansSerif", Font.PLAIN, 24);
			private final int DIESIZE = 50;

			public DiePanel() {
				face = ' ';
				faceLabel = new JLabel("", SwingConstants.CENTER);
				setLayout(new BorderLayout());
				add(faceLabel, BorderLayout.CENTER);
				setBackground(BoggleBoard.BACKGROUNDCOLOR);
				setSize(DIESIZE, DIESIZE);
			}

			public Dimension getPreferredSize() {
				return (new Dimension(DIESIZE + 1, DIESIZE + 1));
			}

			public Dimension getMinimumSize() {
				return (getPreferredSize());
			}

			public void setFace(char chars) {
				face = chars;
			}

			public char getFace() {
				return face;
			}

			/**
			 * Draw one die including the letter centered in the middle of the
			 * die. If highlight is true, we reverse the background and letter
			 * colors to highlight the die.
			 */
			public void paintComponent(Graphics g) {
				super.paintComponent(g);

				int centeredXOffset, centeredYOffset;
				// Draw the blank die
				g.setColor((isHighlighted) ? FACECOLOR : DIECOLOR);
				g.fillRoundRect(0, 0, DIESIZE, DIESIZE, DIESIZE / 2,
						DIESIZE / 2);

				// Outline the die with black
				g.setColor(Color.black);
				g.drawRoundRect(0, 0, DIESIZE, DIESIZE, DIESIZE / 2,
						DIESIZE / 2);
				Graphics faceGraphics = faceLabel.getGraphics();
				faceGraphics.setColor(isHighlighted ? DIECOLOR : FACECOLOR);
				Color myColor = isHighlighted ? DIECOLOR : FACECOLOR;
				faceLabel.setForeground(myColor);
				faceLabel.setFont(FACEFONT);
				faceLabel.setText(face + "");
			}

			public void unHighlight() {
				isHighlighted = false;
			}

			public void highlight() {
				isHighlighted = true;
			}
		}

	} // class BoggeBoard

	/**
	 * Maintains name, score, and word list information for one player
	 */
	class ScoreArea extends JPanel {
		/**
		 * 
		 */
		private static final long serialVersionUID = -8094677286981303799L;
		private final int WORDAREALINES = 16;
		private String playerName;
		private int playerScore, dimensionX, dimensionY;
		private HashSet<String> wordList;

		private final Font ScoreFont = new Font("SansSerif", Font.PLAIN, 18);
		private final Font WordFont = new Font("Geneva", Font.PLAIN, 9);
		private final Font LabelFont = new Font("Helvitica", Font.PLAIN, 9);

		private final Color WordColor = new Color(3, 128, 77);
		private final Color LabelColor = new Color(3, 115, 64);

		private JPanel topPanel, wordPanel, namePanel, scorePanel;
		private JTextArea wordArea;
		private JLabel nameText, scoreText;

		public ScoreArea(String player) {
			playerName = new String(player);
			playerScore = 0;
			wordList = new HashSet<String>();

			// Set-Up Top of Score Area
			namePanel = new JPanel();
			nameText = new JLabel(player);
			nameText.setFont(ScoreFont);
			namePanel.setLayout(new BorderLayout());
			namePanel.add(nameText, BorderLayout.CENTER);

			scorePanel = new JPanel();
			scoreText = new JLabel("  0");
			scoreText.setFont(ScoreFont);
			scorePanel.setLayout(new BorderLayout());
			scorePanel.add(scoreText, BorderLayout.CENTER);

			topPanel = new JPanel();
			topPanel.setLayout(new BorderLayout());
			topPanel.add(namePanel, BorderLayout.WEST);
			topPanel.add(scorePanel, BorderLayout.EAST);

			// Create bordering for top panel
			Border raisedBevel, loweredBevel, compound;

			raisedBevel = BorderFactory.createRaisedBevelBorder();
			loweredBevel = BorderFactory.createLoweredBevelBorder();
			compound = BorderFactory.createCompoundBorder(raisedBevel,
					loweredBevel);
			topPanel.setBorder(compound);

			// Set-Up area to display word list
			wordPanel = new JPanel();
			Border etched = BorderFactory.createEtchedBorder();
			TitledBorder etchedTitle = BorderFactory.createTitledBorder(etched,
					"Word List");
			etchedTitle.setTitleJustification(TitledBorder.RIGHT);
			wordPanel.setBorder(etchedTitle);
			wordArea = new JTextArea(WORDAREALINES,
			// 2/3 of max word len is a good # of columns
					Boggle.DICEROWS * Boggle.DICECOLS * 2 / 3);
			wordArea.addMouseListener(new MouseAdapter() {
				public void mouseClicked(MouseEvent e) {
					// if double-click, highlight the selection on the board
					if (e.getClickCount() == 2) {
						String word = wordArea.getSelectedText().trim()
								.toUpperCase();
						theBoard.highlightDice(theBoard
								.getLocationsForWord(word));
					}
				}
			});
			wordArea.setEditable(false); // for now
			wordPanel.add(new JScrollPane(wordArea,
					JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
					JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED));

			setLayout(new BorderLayout());
			add(topPanel, BorderLayout.NORTH);
			add(wordPanel, BorderLayout.CENTER);
		}

		public void setReady() {
			resetScore(); // zero out score
			wordList.clear(); // remove words from HashSet
			// remove words from TextArea
			wordArea.setEditable(true);
			wordArea.selectAll();
			wordArea.cut();
			wordArea.setEditable(false);
			paintImmediately(getVisibleRect());
		}

		public void setName(String newName) {
			playerName = newName;
			nameText.setText(playerName);
			repaint();
		}

		public boolean containsWord(String word) {
			return wordList.contains(word);
		}

		public void addWord(String word) {
			if (containsWord(word))
				return;
			wordList.add(word);
			wordArea.append(" " + word + "\n");
			addPoints(pointsForWord(word));
			wordArea.paintImmediately(wordArea.getVisibleRect());
		}

		/**
		 * Define how many points a player gets for a given word.
		 */
		public int pointsForWord(String word) {
			return word.length() - Boggle.MINIMUMWORDLENGTH + 1;
		}

		public void addPoints(int points) {
			playerScore += points;
			scoreText.setText(playerScore + "");
			scoreText.paintImmediately(scoreText.getVisibleRect());
		}

		public void resetScore() {
			playerScore = 0;
			scoreText.setText(playerScore + "");
			scoreText.paintImmediately(scoreText.getVisibleRect());
		}

	} // class ScoreArea

	class WordEntryField extends JPanel {
		private JTextField textField;

		public WordEntryField() {
			// Set up for human player's Text Entry Field
			textField = new JTextField(30);
			// Add listener to text entry field
			textField.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					checkAndAddWordHuman(textField.getText());
				}
			});
			this.add(new JLabel("Enter word: "));
			this.add(textField);
			setUnready();
		}

		public void clear() {
			textField.setText("");
		}

		public void setUnready() {
			clear();
			textField.setEditable(false);
			paintImmediately(getVisibleRect());
			// attempt to give up focus to top-level frame
			BoggleGUI.this.requestFocus();
		}

		public void setReady() {
			textField.setEditable(true);
			textField.requestFocus();
		}
	} // class WordEntryField

	/**
	 * The entry point for the BoggleGUI application. Usage: java BoggleGUI [
	 * wordfile [ rows [ columns [ minwordlength ]]]]
	 */
	public static void main(String args[]) {
		if (args.length > 0)
			Boggle.WORDLISTFILENAME = args[0];

		System.err.println("Starting " + Boggle.DICEROWS + "x"
				+ Boggle.DICECOLS + " game," + " words from "
				+ Boggle.WORDLISTFILENAME + ", min word length "
				+ Boggle.MINIMUMWORDLENGTH + ".");
		(new BoggleGUI()).setVisible(true);
	}

}
