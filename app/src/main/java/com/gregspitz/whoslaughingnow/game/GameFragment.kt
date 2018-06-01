package com.gregspitz.whoslaughingnow.game

import android.media.MediaPlayer
import android.os.Bundle
import android.support.annotation.VisibleForTesting
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.gregspitz.whoslaughingnow.R
import com.gregspitz.whoslaughingnow.UseCaseHandler
import com.gregspitz.whoslaughingnow.WhosLaughingApplication
import com.gregspitz.whoslaughingnow.data.model.Laugher
import com.gregspitz.whoslaughingnow.game.domain.usecase.GetNewGame
import kotlinx.android.synthetic.main.fragment_game.*
import javax.inject.Inject

class GameFragment : Fragment(), GameContract.View {

    // Dagger Dependency Injection
    @Inject lateinit var useCaseHandler: UseCaseHandler
    @Inject lateinit var getNewGame: GetNewGame

    private lateinit var presenter: GameContract.Presenter

    private var active = false
    private lateinit var buttonList: List<Button>
    private lateinit var laughers: List<Laugher>
    private var mediaPlayer: MediaPlayer? = null

    companion object {
        @JvmStatic
        fun newInstance() = GameFragment()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WhosLaughingApplication.useCaseComponent.inject(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_game, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        buttonList = listOf(guessOption1, guessOption2, guessOption3, guessOption4)

        // Create presenter
        GamePresenter(useCaseHandler, this, getNewGame)

        playButton.setOnClickListener {
            mediaPlayer?.start()
        }
    }

    override fun onResume() {
        super.onResume()
        active = true
        presenter.start()
    }

    override fun onPause() {
        super.onPause()
        active = false
    }

    override fun onDestroyView() {
        super.onDestroyView()
        mediaPlayer?.release()
    }

    override fun setLoadingIndicator(active: Boolean) {
        // TODO: implement
    }

    override fun setLaughFile(laughFileName: String) {
        val laughResourceId =
                resources.getIdentifier(laughFileName, "raw", activity?.packageName)
        mediaPlayer?.release()
        mediaPlayer = MediaPlayer.create(activity, laughResourceId)
    }

    override fun showLaughers(laughers: List<Laugher>) {
        clearMessages()
        this.laughers = laughers
        for (index in 0 until buttonList.size) {
            buttonList[index].text = laughers[index].displayName
        }
    }

    override fun showFailedToLoadGame() {
        showMessage(R.string.failed_to_load_game_text)
    }

    override fun showRightAnswer() {
        showMessage(R.string.right_answer_text)
    }

    override fun showWrongAnswer() {
        showMessage(R.string.wrong_answer_text)
    }

    private fun showMessage(stringId: Int) {
        messages.text = resources.getString(stringId)
        messages.visibility = View.VISIBLE
    }

    private fun clearMessages() {
        messages.visibility = View.GONE
    }

    override fun isActive(): Boolean = active

    override fun setPresenter(presenter: GameContract.Presenter) {
        this.presenter = presenter

        for ((index, button) in buttonList.withIndex()) {
            button.setOnClickListener {
                presenter.makeGuess(laughers[index])
            }
        }

        nextGameButton.setOnClickListener {
            clearMessages()
            presenter.loadNewGame()
        }
    }

    @VisibleForTesting
    fun getPresenter() = presenter

    @VisibleForTesting
    fun getMediaPlayer() = mediaPlayer
}
