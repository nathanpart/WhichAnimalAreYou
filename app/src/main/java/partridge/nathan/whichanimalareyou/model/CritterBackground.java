package partridge.nathan.whichanimalareyou.model;


import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;

import java.util.Random;

import partridge.nathan.whichanimalareyou.R;

public class CritterBackground {
    private Drawable[] mCritters;

    public CritterBackground(Context context) {
        Resources resources = context.getResources();
        mCritters = new Drawable[10];

        mCritters[0] = resources.getDrawable(R.drawable.bear);
        mCritters[1] = resources.getDrawable(R.drawable.cat);
        mCritters[2] = resources.getDrawable(R.drawable.dog);
        mCritters[3] = resources.getDrawable(R.drawable.dolphin);
        mCritters[4] = resources.getDrawable(R.drawable.elephant);
        mCritters[5] = resources.getDrawable(R.drawable.giraffe);
        mCritters[6] = resources.getDrawable(R.drawable.horse);
        mCritters[7] = resources.getDrawable(R.drawable.monkey);
        mCritters[8] = resources.getDrawable(R.drawable.redpanda);
        mCritters[9] = resources.getDrawable(R.drawable.tiger);
    }

    public Drawable getBackgroundDrawable() {
        Random random = new Random();

        int critterIndex = random.nextInt(mCritters.length);
        return mCritters[critterIndex];
    }
}
