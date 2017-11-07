package partridge.nathan.whichanimalareyou.model;


import android.util.Log;

import java.util.List;

public class Question {
    private final String TAG = Question.class.getSimpleName();

    // Question data
    private String mQuestion;
    private String[] mAnswers;
    private List<List<Integer>> mCharacterMap;

    // State of current answer selected
    private int selectedAnswer;
    private boolean haveSelectecAnswer;

    public Question(String question, String[] answers, List<List<Integer>> characterMap) {
        mQuestion = question;
        mAnswers = answers;
        mCharacterMap = characterMap;

        selectedAnswer = 0;
        haveSelectecAnswer = false;
    }

    public String getQuestion() {
        return mQuestion;
    }

    public String[] getAnswers() {
        return mAnswers;
    }

    public List<Integer> getCharacters(int answerIndex) {
        return mCharacterMap.get(answerIndex);
    }


    public void setSelectedAnswer(int answerIndex) {
        if (answerIndex < mAnswers.length) {
            selectedAnswer = answerIndex;
            haveSelectecAnswer = true;
        } else {
            Log.d(TAG, String.format("Answer index %d out of range.", answerIndex));
            Log.d(TAG, String.format("Number of answers are %d", mAnswers.length));
        }
    }

    public void clearSelection() {
        haveSelectecAnswer = false;
    }

    public Integer getSelectedAnswer() {
        if (haveSelectecAnswer) {
            return selectedAnswer;
        } else {
            return null;
        }
    }

    public List<Integer> getCharacters() {
        if (haveSelectecAnswer) {
            return mCharacterMap.get(selectedAnswer);
        } else {
            return null;
        }
    }

    public boolean isAnswerSelected() {
        return haveSelectecAnswer;
    }


}
