// package edu.pitt.cs.as1;
/**
 * ShelfInterface is an interface that describes the operations of the ADT Shelf. A Shelf is a
 * 3 D collection of objects. The first two dimensions are the rows and column. The last dimension is the quantity (depth)
 */
public interface ShelfInterface<T> {

    /**
     * Returns the item at row, column, position
     *
     * @param row  The row of the object to be retrieved.
     * @param column  The column of the object to be retrieved
     * @param position  The position of the object to be retrieved
     * @return T, the entry at this row, column, position
     */
    public T get(int row, int column, int position);

    /**
     * Determines the name of the entry at row, column, position
     *
     * @param row  The row of the object whose name is retrieved
     * @param column  The column of the object whose name is retrieved
     * @param position  The position of the object whose name is retrieved
     * @return  The String representation of the object at this row, column, position
     */
    public String getName(int row, int column, int position);

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
    public void add(int row, int column, int position, T o);


    /**
     * Removes an entry from this shelf
     *<p>
     * You need to remove the item at position 0 every time and shift the values one position left.
     *</p>
     *
     * @param row  The row of the object to be removed.
     * @param column  The column of the object to be removed.
     */
    public void remove(int row, int column);

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
    public boolean contains(T entry);


    /**
     * Determines whether the shelf at a specific row, column, position is empty.
     *
     * @return true if this is empty; false if not
     */
    public boolean isEmpty(int row, int column, int position);

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
    public T[][][] toArray();
}
