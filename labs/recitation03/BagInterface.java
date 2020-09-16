package cs445.lab3;

/**
 * An interface that describes the operations of a bag of objects.
 * @author Frank M. Carrano
 * @author William C. Garrison III
 * @version 4.1
 */
public interface BagInterface<E> {

    /**
     * Gets the current number of entries in this bag.
     * @return  The integer number of entries currently in this bag.
     */
    public int getCurrentSize();


    /**
     * Sees whether this bag is empty.
     * @return  True if this bag is empty, or false if not.
     */
    public boolean isEmpty();

    /**
     * Adds a new entry to this bag.
     * @param newEntry  The object to be added as a new entry.
     * @return  True if the addition is successful, false if not.
     */
    public boolean add(E newEntry);

    /**
     * Removes one unspecified entry from this bag, if possible.
     * @return  Either the removed entry, if the removal was successful, or
     * null.
     */
    public E remove();

    /**
     * Removes one occurrence of a given entry from this bag, if possible.
     * @param anEntry  The entry to be removed.
     * @return  True if the removal was successful, or false if not.
     */
    public boolean remove(E anEntry);

    /**
     * Removes all entries from this bag.
     */
    public void clear();

    /**
     * Counts the number of times a given entry appears in this bag.
     * @param anEntry  The entry to be counted.
     * @return  The number of times anEntry appears in this bag.
     */
    public int getFrequencyOf(E anEntry);

    /**
     * Tests whether this bag contains a given entry.
     * @param anEntry  The entry to locate.
     * @return  True if this bag contains anEntry, or false otherwise.
     */
    public boolean contains(E anEntry);

    /**
     * Retrieves all entries that are in this bag.
     * @return  A newly allocated array of all the entries in this bag.
     */
    public E[] toArray();
}

