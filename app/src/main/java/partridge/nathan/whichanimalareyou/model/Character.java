package partridge.nathan.whichanimalareyou.model;


import android.graphics.drawable.Drawable;

public class Character {
    private String mLabel;
    private Drawable mPictureDrawable;
    private int mSoundId;
    private int mCaptionMargin;
    private int mCaptionGravity;

    public Character(String label, Drawable pictureDrawable, int soundId, int captionMargin, int captionGravity) {
        mLabel = label;
        mPictureDrawable = pictureDrawable;
        mSoundId = soundId;
        mCaptionMargin = captionMargin;
        mCaptionGravity = captionGravity;
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

    public int getCaptionMargin() {
        return mCaptionMargin;
    }

    public int getCaptionGravity() {
        return mCaptionGravity;
    }
}
