package partridge.nathan.whichanimalareyou.ui.activities;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.Arrays;
import java.util.Stack;

import partridge.nathan.whichanimalareyou.R;
import partridge.nathan.whichanimalareyou.adapters.SpinAdapter;
import partridge.nathan.whichanimalareyou.model.CharacterArray;
import partridge.nathan.whichanimalareyou.model.CritterBackground;
import partridge.nathan.whichanimalareyou.model.QuestionBank;
import partridge.nathan.whichanimalareyou.model.UnAnsweredQuestionException;
import partridge.nathan.whichanimalareyou.model.UnknownCharacterException;

public class QuestionsActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    // Layout elements
    private ImageView mCritterBackground;
    private TextView mQuestionNumber;
    private TextView mQuestion;
    private Spinner mAnswers;
    private Button mBackButton;
    private Button mNextButton;


    private SpinAdapter mAdapter;

    // Data Models
    private QuestionBank mQuestions;
    private CharacterArray mCharacters;
    private CritterBackground mCritters;

    // Activity data
    private int mCurrentQuestion;
    private String mCaption;
    private Stack<Integer> mBackStack = new Stack<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questions);

        mCritterBackground = findViewById(R.id.characterImage);
        mQuestionNumber = findViewById(R.id.questionNumber);
        mQuestion = findViewById(R.id.questionText);
        mAnswers = findViewById(R.id.answersSpinner);
        mBackButton = findViewById(R.id.prevButton);
        mNextButton = findViewById(R.id.nextButton);

        mAdapter = new SpinAdapter(this, null);
        mAnswers.setAdapter(mAdapter);
        mAnswers.setOnItemSelectedListener(this);

        mBackButton.setOnClickListener(this::PrevButtonClick);
        mNextButton.setOnClickListener(this::NextButtonClick);

        mQuestions = new QuestionBank(this);
        mCharacters = new CharacterArray(this);
        mCritters = new CritterBackground(this);

        mCurrentQuestion = 0;
        Intent intent = getIntent();
        if (intent.hasExtra(getString(R.string.caption_text))) {
            mCaption = intent.getStringExtra(getString(R.string.caption_text));
        } else {
            mCaption = "";
        }

        upDateActivityView();
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

    private void fillAnswerSpinner() {
        if (!mQuestions.isAnserSelected(mCurrentQuestion)) {
            mQuestions.setSelectedAnswer(mCurrentQuestion, 0);
        }
        mAdapter.setItems(Arrays.asList(mQuestions.getAnswers(mCurrentQuestion)));
        mAnswers.setSelection(mQuestions.getSelectedAnswer(mCurrentQuestion));
    }

    private void displayQuestion() {
        mQuestion.setText(mQuestions.getQuestion(mCurrentQuestion));
    }

    private void setBackground() {
        mCritterBackground.setImageDrawable(mCritters.getBackgroundDrawable());
    }


    private void upDateActivityView() {
        mBackStack.push(mCurrentQuestion);
        fillAnswerSpinner();
        displayQuestion();
        setBackground();
        updateQuestionNumber();
    }

    private void updateQuestionNumber() {
        mQuestionNumber.setText(String.format(getString(R.string.question_page),
                mCurrentQuestion + 1,
                mQuestions.getNumOfQuestions()));
    }

    private void PrevButtonClick(View v) {
        mCurrentQuestion--;
        if (mCurrentQuestion < 0) {
            Intent intent = new Intent(this, MainActivity.class);
            intent.putExtra(getString(R.string.caption_text), mCaption);
            startActivity(intent);
        } else {
            upDateActivityView();
        }
    }

    private void NextButtonClick(View v) {
        mCurrentQuestion++;
        if (mCurrentQuestion >= mQuestions.getNumOfQuestions()) {
            mCurrentQuestion = mQuestions.getNumOfQuestions() - 1;
            int character = 0;
            try {
                character = mQuestions.getCharacter(mCharacters.getNumberOfCharacters());
            } catch (UnAnsweredQuestionException e) {
                e.printStackTrace();
            } catch (UnknownCharacterException e) {
                e.printStackTrace();
            }

            Intent intent = new Intent(this, CharacterActivity.class);
            intent.putExtra(getString(R.string.caption_text), mCaption);
            intent.putExtra(getString(R.string.the_character), character);
            startActivity(intent);
        } else {
            upDateActivityView();
        }
    }

    @Override
    public void onBackPressed() {
        mBackStack.pop();

        if (mBackStack.isEmpty()) {
            super.onBackPressed();
        } else {
            mCurrentQuestion = mBackStack.pop();
            upDateActivityView();
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        mQuestions.setSelectedAnswer(mCurrentQuestion, adapterView.getSelectedItemPosition());
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
    }

}
