package com.gregspitz.whoslaughingnow.game

import android.view.View
import android.widget.Button
import android.widget.TextView
import com.gregspitz.whoslaughingnow.BuildConfig
import com.gregspitz.whoslaughingnow.R
import com.gregspitz.whoslaughingnow.SingleFragmentActivity
import com.gregspitz.whoslaughingnow.TestData.LAUGHERS
import com.gregspitz.whoslaughingnow.WhosLaughingApplication
import com.gregspitz.whoslaughingnow.data.model.Game
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.Robolectric
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(constants = BuildConfig::class)
class GameFragmentRobolectricTest {

    private val localDataSource =
            WhosLaughingApplication.repoComponent.exposeLocalDataSource()

    private lateinit var activity: SingleFragmentActivity
    private lateinit var fragment: GameFragment

    @Before
    fun setup() {
        localDataSource.clear()
        localDataSource.addLaughers(LAUGHERS)
    }

    @Test
    fun start_showsPlayButtonAndGuessOptions() {
        setupActivityAndAddFragment()

        assertMessagesGone()

        val playButton = activity.findViewById<Button>(R.id.playButton)
        assertEquals(View.VISIBLE, playButton.visibility)

        val buttonTexts = getGuessOptionButtonTexts()
        val laugherDisplayNames = LAUGHERS.map { it.displayName }
        assertTrue(laugherDisplayNames.containsAll(buttonTexts))
    }

    @Test
    fun startWithNoDataToShow_showsFailedToLoadGame() {
        localDataSource.clear()
        setupActivityAndAddFragment()

        assertFailedToLoadGameTextVisible()

        val buttonTexts = getGuessOptionButtonTexts()
        for (buttonText in buttonTexts) {
            assertEquals("", buttonText)
        }
    }

    @Test
    fun clickCorrectOption_showsRightAnswer() {
        setupActivityAndAddFragment()
        val game = getGameFromPresenter()
        val correctText = game.correctAnswer.displayName
        clickButtonByText(correctText)
        assertRightAnswerTextVisible()
    }

    @Test
    fun clickWrongOption_showsWrongAnswer() {
        setupActivityAndAddFragment()
        val game = getGameFromPresenter()
        val wrongText = game.wrongAnswers[0].displayName
        clickButtonByText(wrongText)
        assertWrongAnswerTextVisible()
    }

    @Test
    fun nextGameButtonClick_loadsNewGameAndClearsMessages() {
        setupActivityAndAddFragment()

        // Create message for correct answer
        val correctText = getGameFromPresenter().correctAnswer.displayName
        clickButtonByText(correctText)
        assertRightAnswerTextVisible()

        val nextGameButton = activity.findViewById<Button>(R.id.nextGameButton)
        nextGameButton.performClick()

        // Only asserting messages gone to prove new game happened
        // Spying on a Fragment with Robolectric is maybe impossible
        // in the way that it needs to be done
        // so can't yet prove loadNewGame() is called
        assertMessagesGone()
    }

    private fun setupActivityAndAddFragment() {
        activity = Robolectric.setupActivity(SingleFragmentActivity::class.java)
        fragment = GameFragment.newInstance()
        activity.setFragment(fragment)
    }

    private fun getGuessOptionButtonTexts(): List<String> {
        return getGuessOptionButtons().map { it.text.toString() }
    }

    private fun getGuessOptionButtons(): List<Button> {
        return listOf(activity.findViewById<Button>(R.id.guessOption1),
                activity.findViewById<Button>(R.id.guessOption2),
                activity.findViewById<Button>(R.id.guessOption3),
                activity.findViewById<Button>(R.id.guessOption4))
    }

    private fun clickButtonByText(text: String) {
        for (button in getGuessOptionButtons()) {
            if (button.text.toString() == text) {
                button.performClick()
            }
        }
    }

    private fun assertFailedToLoadGameTextVisible() {
        val failedToLoadText = getMessagesView()
        assertEquals(View.VISIBLE, failedToLoadText.visibility)
        assertEquals(getResourceString(R.string.failed_to_load_game_text),
                failedToLoadText.text.toString())
    }

    private fun assertMessagesGone() {
        val messages = getMessagesView()
        assertEquals(View.GONE, messages.visibility)
    }

    private fun assertRightAnswerTextVisible() {
        val messages = getMessagesView()
        assertEquals(getResourceString(R.string.right_answer_text),
                messages.text.toString())
        assertEquals(View.VISIBLE, messages.visibility)
    }

    private fun assertWrongAnswerTextVisible() {
        val messages = getMessagesView()
        assertEquals(getResourceString(R.string.wrong_answer_text),
                messages.text.toString())
        assertEquals(View.VISIBLE, messages.visibility)
    }

    private fun getGameFromPresenter(): Game {
        return (fragment.getPresenter() as GamePresenter).getGame()!!
    }

    private fun getMessagesView(): TextView {
        return activity.findViewById(R.id.messages)
    }

    private fun getResourceString(id: Int): String {
        return activity.resources.getString(id)
    }
}
