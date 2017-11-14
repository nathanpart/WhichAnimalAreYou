package partridge.nathan.whichanimalareyou.ui.activities;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.options_menu, menu);
        Drawable icon = menu.findItem(R.id.about).getIcon();
        icon.setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_ATOP);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.about) {
            Intent intent = new Intent(this, AboutActivity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mCaption.setText("");
    }
}
