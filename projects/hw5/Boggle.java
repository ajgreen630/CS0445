package edu.pitt.cs.as5;

import java.util.*;

/**
 * *****************************************************************************
 * Assignment 5 CS0445
 * *****************************************************************************
 * Boggle
 * *****************************************************************************
 * <p>
 * This class is the main class of the game beside the GUI.
 *
 * @author  Constantinos Costa (costa.c@cs.pitt.edu)
 * @date Thursday, November 21, 2019
 *****************************************************************************/
class Boggle {

    static String WORDLISTFILENAME = "words.txt"; // default
    static int DICEROWS = 4; // default 4
    static int DICECOLS = 4; // default 4
    static int MINIMUMWORDLENGTH = 4; // default
    private Vector<Die> diceBag; // used for drawing a random game

    //This is the grid that is showing on the GUI
    char[][] grid;

    private boolean[][] visited = new boolean[DICEROWS][DICECOLS];

    // Add your dictionary here.
    // Add your list of boggle board words here
    private DictionaryInterface dictionary;
    private HashSet<String> boggleWords;


    public Boggle(char[][] grid) throws Exception {
        this.grid = grid;
        // Initialize the dictionary here
        dictionary = new TreeDictionary(WORDLISTFILENAME);
        // Initialize the of boggle board words here
        boggleWords = new HashSet<>();
        initDiceBag();
    }

    /**
     * Helper inner class for finding the boggle words given a grid
     */
    private class WorldState {
        int x, y;

        String word;
        boolean[][] visited;

        WorldState(int x, int y, String prefix) {
            this.x = x;
            this.y = y;
            this.word = prefix + grid[x][y];
            this.visited = new boolean[DICEROWS][DICECOLS]; //Create a new visiting table for each state
            this.visited[x][y] = true;                        //Set the location of the prefix
        }

        WorldState(int x, int y, String prefix, boolean[][] prev) {
            this.x = x;
            this.y = y;
            this.word = prefix + grid[x][y];
            this.visited = new boolean[DICEROWS][DICECOLS];
            for (int i = 0; i < DICEROWS; i++)
                for (int j = 0; j < DICECOLS; j++)
                    this.visited[i][j] = prev[i][j];
            this.visited[x][y] = true;
        }
    }

    /**
     * Method to clear global table visited
     */
    private void clearVisit() {
        for (int x = 0; x < DICEROWS; x++)
            Arrays.fill(this.visited[x], false);
    }

    /**
     * Method that add words in the hashset based on
     * a given state
     *
     * @param ws The specific state and the hashset
     * @see BoggleGUI
     */
    private void findWords(WorldState ws, HashSet<String> words) {
        // Find the boggle words and return the set
        Stack<WorldState> stack = new Stack<WorldState>();
        WorldState temp, lastState;

        if (!dictionary.checkWordBeginning(ws.word.replace("Q", "QU"))) return;
        stack.push(ws);
        while (!stack.isEmpty()) {
            lastState = stack.pop();
            for (int x = lastState.x - 1; x < lastState.x + 2; x++) {
                if (x < 0 || x >= DICEROWS) continue;
                for (int y = lastState.y - 1; y < lastState.y + 2; y++) {
                    if (y < 0 || y >= DICECOLS) continue;
                    if (lastState.visited[x][y]) continue;
                    temp = new WorldState(x, y, lastState.word, lastState.visited);
                    if (temp.word.length() >= MINIMUMWORDLENGTH && dictionary.checkWord(temp.word.replace("Q", "QU")))
                        words.add(temp.word);
                    if (!dictionary.checkWordBeginning(temp.word.replace("Q", "QU"))) continue;
                    stack.push(temp);
                }
            }
        }
    }


    /**
     * Supplementary method to find all the
     * words in a given boggle.
     *
     * @see BoggleGUI
     */
    public String[] findWords() {

        // Clear the word and iterate through the board to find all the words
        this.boggleWords.clear();
        for (int x = 0; x < DICEROWS; x++) {
            for (int y = 0; y < DICECOLS; y++) {
                clearVisit();
                WorldState ws = new WorldState(x, y, "");
                findWords(ws, boggleWords);
            }
        }
        return boggleWords.toArray(new String[boggleWords.size()]);
    }

    /**
     * Supplementary method that finds and returns the path
     * for a specific word on the boggle.
     *
     * @param word The given word to lookup
     * @see BoggleGUI
     */
    public boolean[][] findWord(String word) {
        // Find the path for a specific word
        boolean found = false;
        word = word.toUpperCase();
        for (int x = 0; x < DICEROWS && !found; x++) {
            for (int y = 0; y < DICECOLS && !found; y++) {
                clearVisit();
                WorldState ws = new WorldState(x, y, "");
                found = DFS(ws, word);
            }
        }
        return found ? this.visited : null;
    }

    /**
     * Method that finds if a given word
     * exists on the boggle
     *
     * @param word The given word to lookup
     * @return true if it was found, false otherwise
     * @see BoggleGUI
     */
    public boolean exist(String word, boolean[][] path) {
        word = word.toUpperCase();
        // Find if a word exist in the board using DFS
        word = word.toUpperCase();
        boolean found = false;
        for (int x = 0; x < DICEROWS && !found; x++) {
            for (int y = 0; y < DICECOLS && !found; y++) {
                clearVisit();
                WorldState ws = new WorldState(x, y, "");
                found = DFS(ws, word.toUpperCase());
            }
        }
        for (int x = 0; x < DICEROWS; x++)
            for (int y = 0; y < DICECOLS; y++)
                path[x][y] = found && this.visited[x][y];
        return found;
    }

    /**
     * Method that finds if a specific word
     * exists on a board using depth first.
     *
     * @param ws   The given world state
     * @param word The given word to lookup
     * @return true if it was found, false otherwise
     * @see BoggleGUI
     */
    private boolean DFS(WorldState ws, String word) {
        // Depth-first search (DFS)
        Stack<WorldState> stack = new Stack<WorldState>();
        WorldState temp, lastState;

        if (word.length() < 4) return false;
        if (!dictionary.checkWord(word.replace("Q", "QU"))) return false;
        if (!word.startsWith(ws.word)) return false;

        stack.push(ws);

        while (!stack.isEmpty()) {
            lastState = stack.pop();
            for (int x = lastState.x - 1; x < lastState.x + 2; x++) {
                if (x < 0 || x >= DICEROWS) continue;
                for (int y = lastState.y - 1; y < lastState.y + 2; y++) {
                    if (y < 0 || y >= DICECOLS) continue;
                    if (lastState.visited[x][y]) continue;
                    if (grid[x][y] != word.charAt(lastState.word.length())) continue;
                    temp = new WorldState(x, y, lastState.word, lastState.visited);
                    if (word.compareTo(temp.word) == 0) {
                        this.visited = temp.visited.clone();
                        return true;
                    } else
                        stack.push(temp);
                }
            }
        }
        return false;
    }

    /**
     * fill the diceBag with the 16 "official" Boggle dice for the 4X4 game
     *
     * @param none
     * @return void
     */
    private void initDiceBag() {
        diceBag = new Vector<Die>(16);
        diceBag.add(new Die('A', 'O', 'B', 'B', 'O', 'J'));
        diceBag.add(new Die('W', 'H', 'G', 'E', 'E', 'N'));
        diceBag.add(new Die('N', 'R', 'N', 'Z', 'H', 'L'));
        diceBag.add(new Die('N', 'A', 'E', 'A', 'G', 'E'));
        diceBag.add(new Die('D', 'I', 'Y', 'S', 'T', 'T'));
        diceBag.add(new Die('I', 'E', 'S', 'T', 'S', 'O'));
        diceBag.add(new Die('A', 'O', 'T', 'T', 'W', 'O'));
        diceBag.add(new Die('H', 'Q', 'U', 'M', 'N', 'I'));// Qu
        diceBag.add(new Die('R', 'Y', 'T', 'L', 'T', 'E'));
        diceBag.add(new Die('P', 'O', 'H', 'C', 'S', 'A'));
        diceBag.add(new Die('L', 'R', 'E', 'V', 'Y', 'D'));
        diceBag.add(new Die('E', 'X', 'L', 'D', 'I', 'R'));
        diceBag.add(new Die('I', 'E', 'N', 'S', 'U', 'E'));
        diceBag.add(new Die('S', 'F', 'F', 'K', 'A', 'P'));
        diceBag.add(new Die('I', 'O', 'T', 'M', 'U', 'C'));
        diceBag.add(new Die('E', 'H', 'W', 'V', 'T', 'R'));
    }

    /**
     * Return an array of Strings showing the sequence of faces on a randomly
     * generated board.
     *
     * @param none
     * @return array of strings representing the letters on the board
     */
    public char[][] getRandomBoard() {
        // Get random faces from randomy selected dice to fill the
        // letterList
        int diceBagSize = diceBag.size();
        char[][] letterList = new char[DICEROWS][DICECOLS];
        // we try to handle any size board with a fixed diceBag
        for (int i = 0; i < DICEROWS; i++) {
            for (int j = 0; j < DICEROWS; j++) {
                if (i % diceBagSize == 0)
                    Collections.shuffle(diceBag);
                Die d = (Die) diceBag.elementAt(((i + 1) * j) % diceBagSize);
                letterList[i][j] = d.getRandomFace();
            }
        }
        // Set the grid
        grid = letterList;
        // set the corresponding DiePanels, and return the result
        return letterList;
    }

    public static void main(String[] args) throws Exception {
        char[][] grid = {{'S', 'O', 'R', 'N'}, {'H', 'D', 'E', 'A'},
                {'S', 'B', 'O', 'S'}, {'X', 'N', 'I', 'H'}};
        Boggle b = new Boggle(grid);
        for (String word : b.findWords()) {
            System.out.println(word);
        }
    }

}
