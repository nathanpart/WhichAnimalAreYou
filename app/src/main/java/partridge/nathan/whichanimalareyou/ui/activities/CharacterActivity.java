package partridge.nathan.whichanimalareyou.ui.activities;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
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
    private Boolean mHasSound;
    private Button mTalkButton;
    private Boolean mShowAlways;

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
        mCaptionView.setText(caption);
        ViewGroup.MarginLayoutParams layoutParams = (ViewGroup.MarginLayoutParams) mCaptionView.getLayoutParams();
        layoutParams.topMargin = mCastOfCritters.getMargin(mCharacter);
        mCaptionView.setLayoutParams(layoutParams);
        mCaptionView.setGravity(mCastOfCritters.getGravity(mCharacter));

        mCritterPicture.setImageDrawable(mCastOfCritters.getCharacterDrawable(mCharacter));

        mTalkButton = findViewById(R.id.speakAgain);
        mTalkButton.setOnClickListener(v -> animalTalk());

        Button restart = findViewById(R.id.playAgain);
        restart.setOnClickListener(v -> {
            Intent i = new Intent(this, MainActivity.class);
            startActivity(i);
        });

        Toast.makeText(this, String.format("You are a %s", mCastOfCritters.getCharacterLabel(mCharacter)), Toast.LENGTH_LONG).show();
        mShowAlways = false;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (mHasSound) {
            getMenuInflater().inflate(R.menu.character_options_menu, menu);
        } else {
            getMenuInflater().inflate(R.menu.options_menu, menu);
        }

        Drawable icon = menu.findItem(R.id.about).getIcon();
        icon.setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_ATOP);
        if (mHasSound) {
            icon = menu.findItem(R.id.caption_mode).getIcon();
            icon.setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_ATOP);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.about:
                Intent intent = new Intent(this, AboutActivity.class);
                startActivity(intent);
                break;


            case R.id.caption_mode:
                mShowAlways = !mShowAlways;

                Resources resources = getResources();

                Drawable icon = (mShowAlways) ?
                        resources.getDrawable(R.drawable.ic_speaker_notes_off_black_24dp) :
                        resources.getDrawable(R.drawable.ic_speaker_notes_black_24dp);
                icon.setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_ATOP);
                item.setIcon(icon);
                if (mShowAlways)
                    mCaptionView.setVisibility(View.VISIBLE);
                else
                    mCaptionView.setVisibility(View.INVISIBLE);

                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (mCastOfCritters.getCharacterSound(mCharacter) != 0) {
            mHasSound = true;
            mMediaPlayer = MediaPlayer.create(this, mCastOfCritters.getCharacterSound(mCharacter));
            mTimer = new Timer();
        } else {
            mHasSound = false;
            mTalkButton.setText(R.string.mute_message);
            mTalkButton.setOnClickListener(v -> {});
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mHasSound) {
            mMediaPlayer.release();
        }
    }


    @Override
    protected void onResume() {
        super.onResume();
        animalTalk();
    }

    private void animalTalk() {
        mCaptionView.setVisibility(View.VISIBLE);
        if (mHasSound) {
            mMediaPlayer.start();
            if (!mShowAlways) {
                mTimer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        runOnUiThread(() -> mCaptionView.setVisibility(View.INVISIBLE));
                    }
                }, 5000);
            }
        }
    }
}
