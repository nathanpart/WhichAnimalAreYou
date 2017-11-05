package partridge.nathan.whichanimalareyou;

// Support drawing an outline around text to allow contrast with background.
// Based on an example by YGHM on stackoverflow.
// https://stackoverflow.com/questions/3182393/android-textview-outline-text

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;


public class TextViewOutline extends android.support.v7.widget.AppCompatTextView {

    // constants
    private static final int DEFAULT_OUTLINE_SIZE = 0;
    private static final int DFFAULT_OUTLINE_COLOR = Color.TRANSPARENT;


    // data
    private int mOutlineSize;
    private int mOutlineColor;

    // We need to track the textColor and shadow attributes, as we are actually doing a double
    // Test drawing and theses attributes vary depending if we are drawing the text or its
    // outline
    private int mTextColor;
    private float mShadowRadius;
    private float mShadowDx;
    private float mShadowDy;
    private int mShadowColor;


    public TextViewOutline(Context context) {
        this(context, null);
    }

    public TextViewOutline(Context context, AttributeSet attrs) {
        super(context, attrs);
        setAttributes(attrs);
    }

    private void setAttributes(AttributeSet attrs) {
        // set the default outline attributes
        mOutlineColor = DFFAULT_OUTLINE_COLOR;
        mOutlineSize = DEFAULT_OUTLINE_SIZE;
        mTextColor = getCurrentTextColor();

        if (attrs != null) {
            TypedArray ta = getContext().obtainStyledAttributes(attrs, R.styleable.TextViewOutline);

            // outline size
            if (ta.hasValue(R.styleable.TextViewOutline_outlineSize)) {
                mOutlineSize = (int) ta.getDimension(R.styleable.TextViewOutline_outlineSize, DEFAULT_OUTLINE_SIZE);
            }

            // outline color
            if (ta.hasValue(R.styleable.TextViewOutline_outlineColor)) {
                mOutlineColor = ta.getColor(R.styleable.TextViewOutline_outlineColor, DFFAULT_OUTLINE_COLOR);
            }

            // shadow (the reason we take shadow from attributes is because we use min API level 15
            // getShadow... methods are not around until API 16
            if (ta.hasValue(R.styleable.TextViewOutline_android_shadowRadius)
                    || ta.hasValue(R.styleable.TextViewOutline_android_shadowDx)
                    || ta.hasValue(R.styleable.TextViewOutline_android_shadowDy)
                    || ta.hasValue(R.styleable.TextViewOutline_android_shadowColor)) {
                mShadowRadius = ta.getFloat(R.styleable.TextViewOutline_android_shadowRadius, 0);
                mShadowDx = ta.getFloat(R.styleable.TextViewOutline_android_shadowDx, 0);
                mShadowDy = ta.getFloat(R.styleable.TextViewOutline_android_shadowDy, 0);
                mShadowColor = ta.getColor(R.styleable.TextViewOutline_android_shadowColor, Color.TRANSPARENT);
            }

            ta.recycle();
        }
    }

    // Methods to setup painting for the outline or internal part of the text

    // Paint styles when drawing outline - This is also where we apply any shadow effects
    private void setPaintToOutline() {
        Paint paint = getPaint();
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(mOutlineSize);
        super.setTextColor(mOutlineColor);
        super.setShadowLayer(mShadowRadius, mShadowDx, mShadowDy, mShadowColor);
    }

    // Paint syle when painting the normal text - We don't apply shadow here
    private void setPaintToRegualar() {
        Paint paint = getPaint();
        paint.setStyle(Paint.Style.FILL);
        paint.setStrokeWidth(0);
        super.setTextColor(mTextColor);
        super.setShadowLayer(0, 0, 0, Color.TRANSPARENT);
    }

    // Make sure we account for any shadow styling in measurements by making sure the outline paint
    // style is active
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setPaintToOutline();
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    // Track what the current text color is for setPaintToRegular()
    @Override
    public void setTextColor(int color) {
        super.setTextColor(color);
        mTextColor = color;
    }

    // Track the shadow styling for setPaintOutline()
    @Override
    public void setShadowLayer(float radius, float dx, float dy, int color) {
        super.setShadowLayer(radius, dx, dy, color);
        mShadowRadius = radius;
        mShadowDx = dx;
        mShadowDy = dy;
        mShadowColor = color;
    }

    // Setter for outline size
    public void setOutlineSize(int outlineSize) {
        mOutlineSize = outlineSize;
    }

    // Setter for outline color
    public void setOutlineColor(int outlineColor) {
        mOutlineColor = outlineColor;
    }

    // Draw outlined text


    @Override
    protected void onDraw(Canvas canvas) {
        // Draw the outline
        setPaintToOutline();
        super.onDraw(canvas);

        // Draw the text being outlined
        setPaintToRegualar();
        super.onDraw(canvas);
    }
}
