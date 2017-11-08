package partridge.nathan.whichanimalareyou.ui.activities;

import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import partridge.nathan.whichanimalareyou.R;
import partridge.nathan.whichanimalareyou.model.CharacterArray;

public class MainActivity extends AppCompatActivity {

    private CharacterArray youAreResults;
    private int critterIndex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        critterIndex = 0;
        youAreResults = new CharacterArray(this);
        final ImageView image = (ImageView) findViewById(R.id.characterImage);
        image.setImageDrawable(youAreResults.getCharacterDrawable(critterIndex));
        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                critterIndex++;
                if (critterIndex > 9) {
                    critterIndex = 0;
                }
                image.setImageDrawable(youAreResults.getCharacterDrawable(critterIndex));
                int sound = youAreResults.getCharacterSound(critterIndex);
                if (sound != 0) {
                    MediaPlayer mediaPlayer = MediaPlayer.create(MainActivity.this, sound);
                    mediaPlayer.start();
                }

            }
        });
    }
}
