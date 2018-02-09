package cz.martinforejt.swipetohidelayout;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.widget.FrameLayout;

/**
 * {@code SwipeHideLayout} is layout extending base android {@code FrameLayout}
 * Layout can be hide/show by dragging (swiping). Layout (view) can be dragged only in one direction.
 * Swipe direction is sets in xml by attr {@code direction}
 * or programmatically using methods {@link #show()} and {@link #hide()}
 * <p>
 * Created by Martin Forejt on 08.02.2018.
 * me@martinforejt.cz
 *
 * @author Martin Forejt
 */
public class SwipeToHideLayout extends FrameLayout implements SwipeHideable {

    private static final String TAG = SwipeToHideLayout.class.getSimpleName();

    /**
     * default no direction
     */
    public static final int DIRECTION_NOT_SET = 0;
    /**
     * left direction
     */
    public static final int DIRECTION_LEFT = 1;
    /**
     * top direction
     */
    public static final int DIRECTION_TOP = 2;
    /**
     * right direction
     */
    public static final int DIRECTION_RIGHT = 3;
    /**
     * bottom direction
     */
    public static final int DIRECTION_BOTTOM = 4;

    private static final int DEFAULT_SPEED = 300;

    private int _yDelta;
    private int _xDelta;
    private boolean hide;
    private boolean animating = false;
    private Integer _y = null;
    private Integer _x = null;

    private int direction = DIRECTION_NOT_SET;
    private boolean enabled = true;
    private OnSwipeChangeListener listener;

    public SwipeToHideLayout(Context context) {
        this(context, null);
    }

    public SwipeToHideLayout(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SwipeToHideLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.SwipeToHideLayout, 0, 0);

        try {
            direction = a.getInteger(R.styleable.SwipeToHideLayout_direction, DIRECTION_NOT_SET);
            enabled = a.getBoolean(R.styleable.SwipeToHideLayout_enabled, true);
        } finally {
            a.recycle();
        }
    }

    /**
     * Sets direction of sliding
     *
     * @param direction direction constant from {@link SwipeToHideLayout}
     * @see SwipeToHideLayout directions constants
     */
    @Override
    public void setDirection(int direction) {
        this.direction = direction;
    }

    /**
     * Returns current direction or 0 if not set
     *
     * @return direction
     * @see SwipeToHideLayout directions constants
     */
    @Override
    public int getDirection() {
        return direction;
    }

    /**
     * Shows view using default speed
     * Changes {@link android.view.View#getVisibility()} param using animation
     */
    @Override
    public void show() {
        show(DEFAULT_SPEED);
    }

    /**
     * Shows view using defined speed
     * Changes {@link android.view.View#getVisibility()} param using animation
     *
     * @param speed speed of sliding animation (ms)
     */
    @Override
    public void show(int speed) {
        startShowAnimation(speed);
    }

    /**
     * Hide view using default speed
     * Changes {@link android.view.View#getVisibility()} param using animation
     */
    @Override
    public void hide() {
        hide(DEFAULT_SPEED);
    }

    /**
     * Hide view using defined speed
     * Changes {@link android.view.View#getVisibility()} param using animation
     *
     * @param speed speed of sliding animation (speed)
     */
    @Override
    public void hide(int speed) {
        startHideAnimation(speed);
    }

    /**
     * Check if view is visible
     *
     * @return is visible
     */
    @Override
    public boolean isVisible() {
        return getVisibility() == VISIBLE;
    }

    /**
     * Enable/disable view for swiping (touching)
     *
     * @param enable enable/disable
     */
    @Override
    public void enable(boolean enable) {
        this.enabled = enable;
    }

    /**
     * Check if view is enable for swiping (touching)
     *
     * @return is enabled
     */
    @Override
    public boolean isSlideEnabled() {
        return enabled;
    }

    /**
     * Sets on swipe change listener
     *
     * @param listener listener
     */
    @Override
    public void setOnSwipeChangeListener(OnSwipeChangeListener listener) {
        this.listener = listener;
    }

    private void startHideAnimation(int speed) {
        if (direction == DIRECTION_NOT_SET)
            throw new SwipeNoDirectionException("You must set direction!");

        final ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) getLayoutParams();

        int ofValue = 0;
        switch (direction) {
            case DIRECTION_LEFT:
                ofValue = params.leftMargin;
                break;
            case DIRECTION_TOP:
                ofValue = params.topMargin;
                break;
            case DIRECTION_RIGHT:
                ofValue = params.rightMargin;
                break;
            case DIRECTION_BOTTOM:
                ofValue = params.bottomMargin;
                break;
        }

        int valueTo = 0;
        switch (direction) {
            case DIRECTION_LEFT:
                valueTo = -getWidth();
                break;
            case DIRECTION_TOP:
                valueTo = -getHeight();
                break;
            case DIRECTION_RIGHT:
                valueTo = -getWidth();
                break;
            case DIRECTION_BOTTOM:
                valueTo = -getHeight();
                break;
        }

        final ValueAnimator animator = ValueAnimator.ofInt(ofValue, valueTo);
        animator.setInterpolator(new DecelerateInterpolator());
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                if (!animating) {
                    animator.cancel();
                    return;
                }

                switch (direction) {
                    case DIRECTION_LEFT:
                        params.leftMargin = (Integer) animation.getAnimatedValue();
                        break;
                    case DIRECTION_TOP:
                        params.topMargin = (Integer) animation.getAnimatedValue();
                        break;
                    case DIRECTION_RIGHT:
                        params.rightMargin = (Integer) animation.getAnimatedValue();
                        break;
                    case DIRECTION_BOTTOM:
                        params.bottomMargin = (Integer) animation.getAnimatedValue();
                        break;
                }
                requestLayout();
            }
        });
        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                if (animating) {
                    if (isVisible() && listener != null) {
                        listener.onSwipeChange(false, SwipeToHideLayout.this);
                    }
                    setVisibility(GONE);
                }
                animating = false;
            }

            @Override
            public void onAnimationStart(Animator animation) {
                super.onAnimationStart(animation);
                animating = true;
            }
        });
        animator.setDuration(speed);
        animator.start();
    }

    private void startShowAnimation(int speed) {
        if (direction == DIRECTION_NOT_SET)
            throw new SwipeNoDirectionException("You must set direction!");

        final ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) getLayoutParams();
        final boolean wasVisible = isVisible();
        int ofValue = 0;
        switch (direction) {
            case DIRECTION_LEFT:
                ofValue = params.leftMargin;
                break;
            case DIRECTION_TOP:
                ofValue = params.topMargin;
                break;
            case DIRECTION_RIGHT:
                ofValue = params.rightMargin;
                break;
            case DIRECTION_BOTTOM:
                ofValue = params.bottomMargin;
                break;
        }

        final ValueAnimator animator = ValueAnimator.ofInt(ofValue, 0);
        animator.setInterpolator(new DecelerateInterpolator());
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                if (!animating) {
                    animator.cancel();
                    return;
                }

                switch (direction) {
                    case DIRECTION_LEFT:
                        params.leftMargin = (Integer) animation.getAnimatedValue();
                        break;
                    case DIRECTION_TOP:
                        params.topMargin = (Integer) animation.getAnimatedValue();
                        break;
                    case DIRECTION_RIGHT:
                        params.rightMargin = (Integer) animation.getAnimatedValue();
                        break;
                    case DIRECTION_BOTTOM:
                        params.bottomMargin = (Integer) animation.getAnimatedValue();
                        break;
                }
                requestLayout();
            }
        });
        animator.addListener(new AnimatorListenerAdapter() {

            @Override
            public void onAnimationStart(Animator animation) {
                super.onAnimationStart(animation);
                setVisibility(VISIBLE);
                animating = true;
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                if (animating) {
                    if (!wasVisible && listener != null) {
                        listener.onSwipeChange(true, SwipeToHideLayout.this);
                    }
                }
                animating = false;
            }
        });
        animator.setDuration(speed);
        animator.start();
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        if (animating) {
            animating = false;
        }
        if (enabled && isEnabled()) {
            Integer oldY = _y == null ? (int) ev.getRawY() : _y;
            _y = (int) ev.getRawY();

            Integer oldX = _x == null ? (int) ev.getRawX() : _x;
            _x = (int) ev.getRawX();

            switch (direction) {
                case DIRECTION_LEFT:
                    hide = oldX.equals(_x) ? hide : oldX > _x;
                    break;
                case DIRECTION_TOP:
                    hide = oldY.equals(_y) ? hide : oldY > _y;
                    break;
                case DIRECTION_RIGHT:
                    hide = oldX.equals(_x) ? hide : oldX < _x;
                    break;
                case DIRECTION_BOTTOM:
                    hide = oldY.equals(_y) ? hide : oldY < _y;
                    break;
            }

            switch (ev.getAction() & MotionEvent.ACTION_MASK) {
                case MotionEvent.ACTION_DOWN:
                    ViewGroup.MarginLayoutParams lParams = (ViewGroup.MarginLayoutParams) getLayoutParams();

                    switch (direction) {
                        case DIRECTION_LEFT:
                            _xDelta = _x - lParams.leftMargin;
                            break;
                        case DIRECTION_TOP:
                            _yDelta = _y - lParams.topMargin;
                            break;
                        case DIRECTION_RIGHT:
                            _xDelta = _x + lParams.rightMargin;
                            break;
                        case DIRECTION_BOTTOM:
                            _yDelta = _y + lParams.bottomMargin;
                            break;
                    }

                    break;
                case MotionEvent.ACTION_POINTER_DOWN:
                    break;
                case MotionEvent.ACTION_CANCEL:
                case MotionEvent.ACTION_UP:
                case MotionEvent.ACTION_POINTER_UP:
                    if (hide) {
                        startHideAnimation(DEFAULT_SPEED);
                    } else {
                        startShowAnimation(DEFAULT_SPEED);
                    }
                    break;
                case MotionEvent.ACTION_MOVE:
                    ViewGroup.MarginLayoutParams layoutParams = (ViewGroup.MarginLayoutParams) getLayoutParams();

                    switch (direction) {
                        case DIRECTION_LEFT:
                            layoutParams.leftMargin = (_x - _xDelta) > 0 ? 0 : (_x - _xDelta);
                            break;
                        case DIRECTION_TOP:
                            layoutParams.topMargin = (_y - _yDelta) > 0 ? 0 : (_y - _yDelta);
                            break;
                        case DIRECTION_RIGHT:
                            layoutParams.rightMargin = (_x - _xDelta) > 0 ? -(_x - _xDelta) : 0;
                            break;
                        case DIRECTION_BOTTOM:
                            layoutParams.bottomMargin = (_y - _yDelta) > 0 ? -(_y - _yDelta) : 0;
                            break;
                    }
                    setLayoutParams(layoutParams);
                    break;
            }
        }
        return true;
    }
}
