package partridge.nathan.whichanimalareyou.model;


import android.graphics.drawable.Drawable;

public class Character {
    private String mLabel;
    private Drawable mPictureDrawable;
    private int mSoundId;
    private double mCaptionLocX;
    private double mCaptionLocY;

    public Character(String label, Drawable pictureDrawable, int soundId, double captionLocX, double captionLocY) {
        mLabel = label;
        mPictureDrawable = pictureDrawable;
        mSoundId = soundId;
        mCaptionLocX = captionLocX;
        mCaptionLocY = captionLocY;
    }

    public String getLabel() {
        return mLabel;
    }

    public Drawable getPictureDrawable() {
        return mPictureDrawable;
    }

    public int getSoundId() {
        return mSoundId;
    }

    public double getCaptionLocX() {
        return mCaptionLocX;
    }

    public double getCaptionLocY() {
        return mCaptionLocY;
    }
}
