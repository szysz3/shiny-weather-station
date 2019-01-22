package com.pinetreeapps.timepicker;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.EditText;

/**
 * Created by michalszyszka on 19.05.2018.
 */

public class NumberPicker extends android.widget.NumberPicker {
    public NumberPicker(Context context) {
        super(context);
    }

    public NumberPicker(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public NumberPicker(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public NumberPicker(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    public void addView(View child) {
        super.addView(child);
        updateView(child);
    }

    @Override
    public void addView(View child, int index, android.view.ViewGroup.LayoutParams params) {
        super.addView(child, index, params);
        updateView(child);
    }

    @Override
    public void addView(View child, android.view.ViewGroup.LayoutParams params) {
        super.addView(child, params);
        updateView(child);
    }

    private void updateView(View view) {
        if (view instanceof EditText) {
            ((EditText) view).setTypeface(TypefaceHelper.get(getContext(), TypefaceHelper.TYPEFACE_NAME));
        }
    }

    @Override
    protected float getTopFadingEdgeStrength() {
        return 1f;
    }

    @Override
    protected float getBottomFadingEdgeStrength() {
        return 1f;
    }
}
