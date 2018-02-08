package cz.martinforejt.swipetohidelayout;

/**
 * Callback for hide/show {@link SwipeHideable}
 * <p>
 * Created by Martin Forejt on 08.02.2018.
 * me@martinforejt.cz
 *
 * @author Martin Forejt
 */
public interface OnSwipeChangeListener {

    /**
     * Called when {@link SwipeHideable} is swiped (show/hide)
     *
     * @param visible       is now visible?
     * @param swipeHideable swipeHideable
     */
    void onSwipeChange(boolean visible, SwipeHideable swipeHideable);
}