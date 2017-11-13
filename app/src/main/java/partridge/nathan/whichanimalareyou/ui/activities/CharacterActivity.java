package partridge.nathan.whichanimalareyou.ui.activities;

import android.content.Intent;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.Timer;
import java.util.TimerTask;

import partridge.nathan.whichanimalareyou.R;
import partridge.nathan.whichanimalareyou.model.CharacterArray;
import partridge.nathan.whichanimalareyou.ui.views.TextViewOutline;

public class CharacterActivity extends AppCompatActivity {
    private CharacterArray mCastOfCritters;

    private ImageView mCritterPicture;
    private TextViewOutline mCaptionView;
    private MediaPlayer mMediaPlayer;
    private int mCharacter;
    private Timer mTimer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_character);

        mCastOfCritters = new CharacterArray(this);
        Intent intent = getIntent();
        mCharacter = intent.getIntExtra(getString(R.string.the_character), 0);
        String caption = intent.getStringExtra(getString(R.string.caption_text));

        mCritterPicture = findViewById(R.id.characterImage);
        mCaptionView = findViewById(R.id.captionText);
        mCritterPicture.setImageDrawable(mCastOfCritters.getCharacterDrawable(mCharacter));

        Button talkButton = findViewById(R.id.speakAgain);
        talkButton.setOnClickListener(v -> animalTalk());

        Button restart = findViewById(R.id.playAgain);
        restart.setOnClickListener(v -> {
            Intent i = new Intent(this, MainActivity.class);
            startActivity(i);
        });

        Toast.makeText(this, String.format("You are a %s", mCastOfCritters.getCharacterLabel(mCharacter)), Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onStart() {
        super.onStart();
        mMediaPlayer = MediaPlayer.create(this, mCastOfCritters.getCharacterSound(mCharacter));
        mTimer = new Timer();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mMediaPlayer.release();
    }


    @Override
    protected void onResume() {
        super.onResume();
        animalTalk();
    }

    private void animalTalk() {
        mMediaPlayer.start();
        mCaptionView.setVisibility(View.VISIBLE);
        mTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(()->mCaptionView.setVisibility(View.INVISIBLE));
            }
        }, 3000);
    }
}
