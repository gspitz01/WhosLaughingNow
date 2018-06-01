package com.gregspitz.whoslaughingnow

import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.action.ViewActions.click
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.matcher.ViewMatchers.withId
import android.support.test.espresso.matcher.ViewMatchers.withText
import android.support.test.rule.ActivityTestRule
import com.gregspitz.whoslaughingnow.AndroidTestData.LAUGHERS
import com.gregspitz.whoslaughingnow.game.GameFragment
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class GameFragmentTest {

    private val localDataSource =
            WhosLaughingApplication.repoComponent.exposeLocalDataSource()

    @Rule @JvmField
    val activityRule = ActivityTestRule<SingleFragmentActivity>(SingleFragmentActivity::class.java)

    @Before
    fun setup() {
        localDataSource.clear()
        localDataSource.addLaughers(LAUGHERS)
    }

    @Test
    fun clickPlayButton_playsLaugh() {
        val fragment = GameFragment.newInstance()
        activityRule.activity.setFragment(fragment)
        // Force Espresso to wait for Fragment to load
        onView(withId(R.id.playButton)).check(matches(withText(R.string.play_button_text)))
        val mediaPlayer =
                (activityRule.activity.supportFragmentManager.fragments[0] as GameFragment)
                        .getMediaPlayer()
        onView(withId(R.id.playButton)).perform(click())
        assertTrue(mediaPlayer!!.isPlaying)
    }
}
