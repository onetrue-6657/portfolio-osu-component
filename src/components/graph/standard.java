package components.graph;

/**
 * {@code Standard} enhanced with secondary methods.
 */
public interface standard {
    /**
     * Reports the number of elements in {@code this}.
     */
    void clear();

    /**
     * Transfer all elements from {@code source} to {@code this}, leaving
     * {@code source} empty.
     */
    void transferFrom();

    /**
     * Clears {@code this} and then creates a new instance of {@code this}.
     */
    void newInstance();
}
