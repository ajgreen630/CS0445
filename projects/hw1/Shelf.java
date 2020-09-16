// ==================================================
// Author Information:
// ==================================================
// Name: Alexi Green
// MyPitt Username: AJG143
// MyPitt E-mail: ajg143@pitt.edu
// PeoplesoftID: 4193629
// Work E-mail: alexi.green@outlook.com
// Course Information: CS 445
// ==================================================
// package edu.pitt.cs.as1;
import java.util.*;

public final class Shelf<T> implements ShelfInterface<T> {
	private T[][][] shelf;
	private final int rows;
	private final int columns;
	private final int quantity;

	public Shelf(int rows, int columns, int quantity)  // DONE
	{
		this.rows = rows;
		this.columns = columns;
		this.quantity = quantity;

		T[][][] tempShelf = (T[][][])new Object[rows][columns][quantity];
		shelf = tempShelf;
	}

	/**
     * Returns the item at row, column, position
     *
     * @param row  The row of the object to be retrieved.
     * @param column  The column of the object to be retrieved
     * @param position  The position of the object to be retrieved
     * @return T, the entry at this row, column, position
     */
    public T get(int row, int column, int position) // DONE
	{
		return shelf[row][column][position];
	}

	/**
     * Determines the name of the entry at row, column, position
     *
     * @param row  The row of the object whose name is retrieved
     * @param column  The column of the object whose name is retrieved
     * @param position  The position of the object whose name is retrieved
     * @return  The String representation of the object at this row, column, position
     */
    public String getName(int row, int column, int position)
	{
		// Note: Overode toString method in VendingItem.java
		String name = shelf[row][column][position].toString();
		return name;
	}

	/**
     * Adds a new entry to this shelf
     *
     * <p> If o is not null then add the o to the corresponding row, column, and position.
     *
     * <p> If o is null, then the entry is ignored.
     * @param row  The row of the object to be added.
     * @param column  The column of the object to be added
     * @param position  The position of the object to be added
     * @param o  The object to be added as a new entry
     */
    public void add(int row, int column, int position, T o) // DONE
	{
		/** Note: Use == or != for non-String data types and null checks. **/
		if (o != null)
		{
			shelf[row][column][position] = o;
		}
	}

	 /**
     * Removes an entry from this shelf
     *<p>
     * You need to remove the item at position 0 every time and shift the values one position left.
     *</p>
     *
     * @param row  The row of the object to be removed.
     * @param column  The column of the object to be removed.
     */
    public void remove(int row, int column) // DONE
	{
		shelf[row][column][0] = null;
		for(int i = 0; i < quantity-1; i++)
		{
			//System.out.println(row+":"+column+":"+i);

			shelf[row][column][i] = shelf[row][column][i + 1];
		}
		shelf[row][column][quantity - 1] = null;	// Prevent second to last index and last index from having duplicate values.
	}

	 /**
     * Tests whether this shelf contains a given entry. Equality is determined
     * using the .equals() method.
     *
     * <p> If this shelf contains entry, then contains returns true. Otherwise
     * (including if this shelf is empty), contains returns false.  If entry is null,
     * return false.
     *
     * @param entry  The entry to locate
     * @return  true if this shelf contains entry; false otherwise
     */
    public boolean contains(T entry)
	{
		for(int i = 0; i < shelf.length; i++)
		{
			for (int j = 0; j < shelf[i].length; j++)
			{
				for (int k = 0; k < shelf[i][j].length; k++)
				{
					if(shelf[i][j][k]!=null)
					if (shelf[i][j][k].equals(entry))
					{
						return true;
					}
					else if (entry == null)
					{
						return false;
					}
				}
			}
		}
		return false;	// Will this cover if the entry is not found and if the shelf is empty?
	}

	 /**
     * Determines whether the shelf at a specific row, column, position is empty.
     *
     * @return true if this is empty; false if not
     */
    public boolean isEmpty(int row, int column, int position) // DONE
	{
		if(shelf[row][column][position] == null)
		{
			return true;
		}
		else
		{
			return false;
		}
	}

    /**
     * Retrieves all entries that are in this Shelf.
     *
     * <p> A 3D array is returned that contains a reference to each of the entries
     * in this shelf.
     *
     * <p> If the implementation of Shelf is array-backed, toArray will not return
     * the private backing array. Instead, a new array will be allocated with
     * the appropriate capacity.
     *
     * @return  A newly-allocated array of all the entries in this shelf
     */
    public T[][][] toArray()
	{
		T[][][] shelfCopy = (T[][][])new Object[rows][columns][quantity];
		shelfCopy = shelf;
		return shelfCopy;
	}
}
