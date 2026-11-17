package org.proven.primera_app

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.activity.ComponentActivity

class MainActivity : ComponentActivity() {

    private lateinit var model: ModelGame

    private lateinit var layoutSelection: LinearLayout
    private lateinit var btn3Cubs: Button
    private lateinit var btn6Cubs: Button
    private lateinit var layoutEndGame: LinearLayout
    private lateinit var btnPlayAgain: Button
    private lateinit var btnChangeDifficulty: Button
    private lateinit var textWin: TextView
    private lateinit var textCurrentWins: TextView
    private lateinit var textCurrentLoses: TextView
    private lateinit var layoutRow2: LinearLayout
    private lateinit var ivCubes: Array<ImageView>

    private var selectedCubs: Int = 3

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        model = ModelGame()

        initViews()
        initController()
        updateView()
    }

    private fun initViews() {
        layoutSelection = findViewById(R.id.layoutSelection)
        btn3Cubs = findViewById(R.id.btn3Cubs)
        btn6Cubs = findViewById(R.id.btn6Cubs)
        layoutEndGame = findViewById(R.id.layoutEndGame)
        btnPlayAgain = findViewById(R.id.btnPlayAgain)
        btnChangeDifficulty = findViewById(R.id.btnChangeDifficulty)
        textWin = findViewById(R.id.textView)
        textCurrentWins = findViewById(R.id.textCurrentWins)
        textCurrentLoses = findViewById(R.id.textCurrentLoses)
        layoutRow2 = findViewById(R.id.layoutRow2)

        ivCubes = arrayOf(
            findViewById(R.id.imageViewCube1),
            findViewById(R.id.imageViewCube2),
            findViewById(R.id.imageViewCube3),
            findViewById(R.id.imageViewCube4),
            findViewById(R.id.imageViewCube5),
            findViewById(R.id.imageViewCube6),
        )
    }

    private fun resetCubsImages() {
        for (iv in ivCubes) {
            iv.setImageResource(R.drawable.cubilete)
            iv.scaleX = 1.0f
            iv.scaleY = 1.0f
            iv.translationY = 0.0f
        }
    }

    private fun updateScoreViews() {
        textCurrentWins.text = getString(R.string.wins_format, model.aciertos)
        textCurrentLoses.text = getString(R.string.loses_format, model.fallos)
    }

    private fun updateView() {
        layoutSelection.visibility = View.VISIBLE
        layoutEndGame.visibility = View.GONE
        textWin.visibility = View.INVISIBLE
        updateScoreViews()
    }

    private fun startGameFlow() {
        layoutSelection.visibility = View.GONE
        layoutEndGame.visibility = View.GONE
        model.startGame(selectedCubs)
        resetCubsImages()

        if (selectedCubs == 6) {
            layoutRow2.visibility = View.VISIBLE
        } else {
            layoutRow2.visibility = View.GONE
        }

        textWin.visibility = View.INVISIBLE
        updateScoreViews()
    }

    private fun resetGameFlow() {
        layoutSelection.visibility = View.VISIBLE
        layoutEndGame.visibility = View.GONE
        layoutRow2.visibility = View.GONE
        textWin.visibility = View.INVISIBLE
        resetCubsImages()
    }

    private fun initController() {
        btn3Cubs.setOnClickListener {
            selectedCubs = 3
            startGameFlow()
        }

        btn6Cubs.setOnClickListener {
            selectedCubs = 6
            startGameFlow()
        }

        btnPlayAgain.setOnClickListener {
            startGameFlow()
        }

        btnChangeDifficulty.setOnClickListener {
            resetGameFlow()
        }

        for (i in ivCubes.indices) {
            ivCubes[i].setOnClickListener { view ->
                if (model.playing) {
                    val chosenCube = i + 1
                    val winningCube = model.posicionBall
                    val won = model.jugar(chosenCube)

                    view.animate()
                        .translationY(-20f)
                        .scaleX(1.1f)
                        .scaleY(1.1f)
                        .setDuration(150)
                        .withEndAction {
                            if (won) {
                                ivCubes[i].setImageResource(R.drawable.levantaok)
                                textWin.text = getString(R.string.you_win)
                                textWin.visibility = View.VISIBLE
                                layoutEndGame.visibility = View.VISIBLE
                            } else {
                                ivCubes[i].setImageResource(R.drawable.levantafail)
                                textWin.text = getString(R.string.has_lost)
                                textWin.visibility = View.VISIBLE

                                Handler(Looper.getMainLooper()).postDelayed(
                                    {
                                        if (winningCube in (1..ivCubes.size)) {
                                            val winIndex = winningCube - 1
                                            ivCubes[winIndex].setImageResource(R.drawable.levantaok)
                                            ivCubes[winIndex].animate()
                                                .translationY(-20f)
                                                .scaleX(1.1f)
                                                .scaleY(1.1f)
                                                .setDuration(200)
                                                .start()
                                        }
                                        layoutEndGame.visibility = View.VISIBLE
                                    },
                                    250,
                                )
                            }
                            updateScoreViews()
                        }
                        .start()
                }
            }
        }
    }
}
