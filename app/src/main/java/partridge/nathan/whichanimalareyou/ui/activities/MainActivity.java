package partridge.nathan.whichanimalareyou.ui.activities;

import android.content.Intent;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import partridge.nathan.whichanimalareyou.R;
import partridge.nathan.whichanimalareyou.model.CharacterArray;
import partridge.nathan.whichanimalareyou.model.CritterBackground;

public class MainActivity extends AppCompatActivity {
    private CritterBackground mCritters;
    private ImageView mBackground;
    private Button mStartButton;
    private EditText mCaption;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mBackground = findViewById(R.id.characterImage);
        mStartButton = findViewById(R.id.startButton);
        mCaption = findViewById(R.id.captionText);

        mCritters = new CritterBackground(this);

        mBackground.setImageDrawable(mCritters.getBackgroundDrawable());

        mStartButton.setOnClickListener(v -> {
            String caption = mCaption.getText().toString();

            Intent intent = new Intent(this, QuestionsActivity.class);
            intent.putExtra(getString(R.string.caption_text), caption);
            startActivity(intent);
        });
    }
}
