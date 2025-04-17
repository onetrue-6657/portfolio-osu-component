package components.graph;

/**
 * {@code Standard} enhanced with secondary methods.
 *
 * @author Zheng Ni
 */
public interface standard {
    /**
     * Reports the number of elements in {@code this}.
     */
    void clear();

    /**
     * Transfer all elements from {@code source} to {@code this}, leaving
     * {@code source} empty.
     *
     * @param source
     *            the source graph to transfer elements from
     */
    void transferFrom(GraphSecondary source);

    /**
     * Clears {@code this} and then creates a new instance of {@code this}.
     *
     * @return a new instance of {@code this}
     */
    Graph1L newInstance();
}
