package partridge.nathan.whichanimalareyou.model;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.util.Log;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import partridge.nathan.whichanimalareyou.R;

public class QuestionBank {
    private final String TAG = QuestionBank.class.getSimpleName();

    private List<Question> mQuestions;

    public QuestionBank(Context context) {
        Resources resources = context.getResources();

        // Get array of questions from resources
        TypedArray questionArray = resources.obtainTypedArray(R.array.questions);

        int numQuestions = questionArray.length();
        mQuestions = new ArrayList<>(numQuestions);

        for (int i = 0; i < numQuestions; i++) {
            // Each entry in the questions array is an id to a array of question data
            TypedArray currentQuestion = resources.obtainTypedArray(questionArray.getResourceId(i, 0));
            readQuestion(resources, currentQuestion);
            currentQuestion.recycle();
        }
        questionArray.recycle();
    }

    private void readQuestion(Resources resources, TypedArray currentQuestion) {
        // The currentQuestion array has three entries
        // 1. Reference to the string resource of the question
        // 2. REference to the string-array resource of the options
        // 3. Reference to an array of references to an array of characters.
        String questionString = currentQuestion.getString(0);
        // Why Lint freaks out on the type array index?  I know the types of my array entries!!
        @SuppressLint("ResourceType") String[] answers = resources.getStringArray(currentQuestion.getResourceId(1, 0));
        @SuppressLint("ResourceType") TypedArray mapArray = resources.obtainTypedArray(currentQuestion.getResourceId(2, 0));

        if (answers.length != mapArray.length()) {
            Log.e(TAG, "Size of answer string array must match size of the map array for the question.");
        }

        List<List<Integer>> characterMap = readCharacterMap(resources, mapArray);
        mapArray.recycle();

        Question question = new Question(questionString, answers, characterMap);
        mQuestions.add(question);
    }

    private List<List<Integer>> readCharacterMap(Resources resources, TypedArray mapArray) {
        List<List<Integer>> mapList = new ArrayList<>(mapArray.length());
        for (int i = 0; i < mapArray.length(); i++) {
            List<Integer> characterList = new ArrayList<>();
            for (int j: resources.getIntArray(mapArray.getResourceId(i, 0))) characterList.add(j);
            mapList.add(characterList);
        }
        return mapList;
    }

    public int getNumOfQuestions() {
        return mQuestions.size();
    }

    public String getQuestion(int questionIndex) {
        return mQuestions.get(questionIndex).getQuestion();
    }

    public String[] getAnswers(int questionIndex) {
        return mQuestions.get(questionIndex).getAnswers();
    }

    public boolean isAnserSelected(int questionIndex) {
        return mQuestions.get(questionIndex).isAnswerSelected();
    }

    public Integer getSelectedAnswer(int questionIndex) {
        return mQuestions.get(questionIndex).getSelectedAnswer();
    }

    public void setSelectedAnswer(int questionIndex, int answerIndex) {
        mQuestions.get(questionIndex).setSelectedAnswer(answerIndex);
    }

    public void clearSelectedAnnswer(int questionIndex) {
        mQuestions.get(questionIndex).clearSelection();
    }

    // Check that all questions have been answered
    public boolean isCharacterAvailable() {
        for (Question q: mQuestions) {
            if (!q.isAnswerSelected()) {
                return false;
            }
        }
        return true;
    }

    public int getCharacter(int numberOfCharacters) throws UnAnsweredQuestionException, UnknownCharacterException {

        // Create area to sum counts of characters
        int[] characterCount = new int[numberOfCharacters];
        for (int i = 0; i < characterCount.length; i++) characterCount[i] = 0;

        for (int i = 0; i < mQuestions.size(); i++) {
            Question q = mQuestions.get(i);
            List<Integer> characters = q.getCharacters();
            if (characters == null) {
                throw new UnAnsweredQuestionException(String.format("Question %d does not have a selected answer.", i));
            }
            for (int c : characters) {
                if (c >= characterCount.length) {
                    throw new UnknownCharacterException("Character index %d is beyound size of character array.");
                }
                characterCount[c]++;
            }
        }
        int high = 0;
        int selectedCritter = 0;
        for (int i = 0; i < characterCount.length; i++) {
            if (characterCount[i] > high) {
                high = characterCount[i];
                selectedCritter = i;
            }
        }
        return selectedCritter;
    }
}
