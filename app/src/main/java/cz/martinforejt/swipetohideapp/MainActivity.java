package cz.martinforejt.swipetohideapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import cz.martinforejt.swipetohidelayout.OnSwipeChangeListener;
import cz.martinforejt.swipetohidelayout.SwipeHideable;
import cz.martinforejt.swipetohidelayout.SwipeToHideLayout;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Button bindLeft = findViewById(R.id.btn_hide_left);
        final Button bindTop = findViewById(R.id.btn_hide_top);
        final Button bindRight = findViewById(R.id.btn_hide_right);
        final Button bindBottom = findViewById(R.id.btn_hide_bottom);

        final SwipeToHideLayout left = findViewById(R.id.layout_left);
        final SwipeToHideLayout top = findViewById(R.id.layout_top);
        final SwipeToHideLayout right = findViewById(R.id.layout_right);
        final SwipeToHideLayout bottom = findViewById(R.id.layout_bottom);

        left.setOnSwipeChangeListener(new OnSwipeChangeListener() {
            @Override
            public void onSwipeChange(boolean visible, SwipeHideable swipeHideable) {
                bindLeft.setText(visible ? getString(R.string.btn_hide_left) : getString(R.string.btn_show_left));
            }
        });

        top.setOnSwipeChangeListener(new OnSwipeChangeListener() {
            @Override
            public void onSwipeChange(boolean visible, SwipeHideable swipeHideable) {
                bindTop.setText(visible ? getString(R.string.btn_hide_top) : getString(R.string.btn_show_top));
            }
        });

        right.setOnSwipeChangeListener(new OnSwipeChangeListener() {
            @Override
            public void onSwipeChange(boolean visible, SwipeHideable swipeHideable) {
                bindRight.setText(visible ? getString(R.string.btn_hide_right) : getString(R.string.btn_show_right));
            }
        });

        bottom.setOnSwipeChangeListener(new OnSwipeChangeListener() {
            @Override
            public void onSwipeChange(boolean visible, SwipeHideable swipeHideable) {
                bindBottom.setText(visible ? getString(R.string.btn_hide_bottom) : getString(R.string.btn_show_bottom));
            }
        });

        bindLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (left.isVisible()) {
                    left.hide();
                } else {
                    left.show();
                }
            }
        });

        bindTop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (top.isVisible()) {
                    top.hide();
                } else {
                    top.show();
                }
            }
        });

        bindRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (right.isVisible()) {
                    right.hide();
                } else {
                    right.show();
                }
            }
        });

        bindBottom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (bottom.isVisible()) {
                    bottom.hide();
                } else {
                    bottom.show();
                }
            }
        });
    }
}
