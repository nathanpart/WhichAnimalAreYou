package partridge.nathan.whichanimalareyou.model;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;

import java.util.ArrayList;
import java.util.List;

import partridge.nathan.whichanimalareyou.R;

public class CharacterArray {
    private List<Character> mCharacters;
    private Context mContext;

    public CharacterArray(Context context) {
        mContext = context;

        Resources resources = context.getResources();
        TypedArray  characterArray = resources.obtainTypedArray(R.array.charaters);
        int itemCount = characterArray.length();
        mCharacters = new ArrayList<>(itemCount);
        for (int i = 0; i < itemCount; i++) {
            TypedArray itemArray = resources.obtainTypedArray(characterArray.getResourceId(i, 0));
            String label = itemArray.getString(0);
            @SuppressLint("ResourceType") Drawable image = itemArray.getDrawable(1);
            @SuppressLint("ResourceType") int sound = itemArray.getResourceId(2, 0);
            @SuppressLint("ResourceType") double xLoc = itemArray.getDimension(3, 0);
            @SuppressLint("ResourceType") double yLoc = itemArray.getDimension(4, 0);
            itemArray.recycle();

            Character item = new Character(label, image, sound, xLoc, yLoc);
            mCharacters.add(item);
        }

        characterArray.recycle();;

    }

    public Drawable getCharacterDrawable(int resultItem) {
        return mCharacters.get(resultItem).getPictureDrawable();
    }

    public int getCharacterSound(int resultItem) {
        return mCharacters.get(resultItem).getSoundId();
    }

    public String getCharacterLabel(int resultItem) {
        return mCharacters.get(resultItem).getLabel();
    }

    public double getCaptionX(int resultItem) {
        return mCharacters.get(resultItem).getCaptionLocX();
    }

    public double getCaptionY(int resultItem) {
        return mCharacters.get(resultItem).getCaptionLocY();
    }

    public int getNumberOfCharacters() {
        return mCharacters.size();
    }


}
