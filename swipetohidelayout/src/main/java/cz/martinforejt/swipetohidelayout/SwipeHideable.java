package cz.martinforejt.swipetohidelayout;

import android.view.View;

/**
 * {@code SwipeHideable}
 * <p>
 * Created by Martin Forejt on 08.02.2018.
 * me@martinforejt.cz
 *
 * @author Martin Forejt
 */
public interface SwipeHideable {

    /**
     * Sets direction of sliding
     *
     * @param direction direction constant from {@link SwipeToHideLayout}
     * @see SwipeToHideLayout directions constants
     */
    void setDirection(int direction);

    /**
     * Returns current direction or 0 if not set
     *
     * @return direction
     * @see SwipeToHideLayout directions constants
     */
    int getDirection();

    /**
     * Shows view using default speed
     * Changes {@link View#getVisibility()} param using animation
     */
    void show();

    /**
     * Shows view using defined speed
     * Changes {@link View#getVisibility()} param using animation
     *
     * @param speed speed of sliding animation (ms)
     */
    void show(int speed);

    /**
     * Hide view using default speed
     * Changes {@link View#getVisibility()} param using animation
     */
    void hide();

    /**
     * Hide view using defined speed
     * Changes {@link View#getVisibility()} param using animation
     *
     * @param speed speed of sliding animation (speed)
     */
    void hide(int speed);

    /**
     * Check if view is visible
     *
     * @return is visible
     */
    boolean isVisible();

    /**
     * Enable/disable view for swiping (touching)
     *
     * @param enable enable/disable
     */
    void enable(boolean enable);

    /**
     * Check if view is enable for swiping (touching)
     *
     * @return is enabled
     */
    boolean isSlideEnabled();

    /**
     * Sets on swipe change listener
     *
     * @param listener listener
     */
    void setOnSwipeChangeListener(OnSwipeChangeListener listener);
}
