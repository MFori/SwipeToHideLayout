package cz.martinforejt.swipetohidelayout;

/**
 * Created by Martin Forejt on 08.02.2018.
 * me@martinforejt.cz
 *
 * @author Martin Forejt
 */
public class SwipeNoDirectionException extends IllegalArgumentException {
    /**
     * Constructs an <code>IllegalArgumentException</code> with the
     * specified detail message.
     *
     * @param s the detail message.
     */
    public SwipeNoDirectionException(String s) {
        super(s);
    }
}
