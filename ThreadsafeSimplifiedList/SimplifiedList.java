package io.dama.ffi.hoh;

public interface SimplifiedList<T> {

    /**
     * Returns the element at the specified position in this list.
     *
     * @param index index of the element to return
     * @return the element at the specified position in this list
     */
    public T get(int index);

    /**
     * Appends the specified element to the end of this list. There are no
     * limitations on what elements may be added to this list.
     * 
     * @param element element to be appended to this list
     * @return true
     * @see java.util.Collection#add(Object)
     *
     */
    public boolean add(T element);

    /**
     * Replaces the element at the specified position in this list with the
     * specified element.
     * 
     * @param index   index of the element to replace
     * @param element element to be stored at the specified position
     * @return the element previously at the specified position
     */
    public T set(int index, T element);

    /**
     * Returns true if this list contains no elements.
     * 
     * @return true if this list contains no elements
     */
    public boolean isEmpty();

    /**
     * delayed passing through of parameter
     * 
     * @param element element to pass through
     * @return passed though element
     *
     */
    public default T delay(T element) {
        try {
            Thread.sleep(10);
        } catch (InterruptedException ex) {
            // ... nothing ...
        }
        return element;
    }
}
