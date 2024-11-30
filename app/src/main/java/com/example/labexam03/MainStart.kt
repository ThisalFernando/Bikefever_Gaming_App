package com.example.labexam03

import android.content.Context
import android.content.Intent
import android.media.Image
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputBinding
import android.widget.Button
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import org.w3c.dom.Text

class MainStart : AppCompatActivity(), GameWork{
    lateinit var share : ImageButton
    lateinit var web : ImageButton
    lateinit var email : ImageButton
    lateinit var layout : ConstraintLayout
    lateinit var  playBtn : Button
    lateinit var highScoreBtn : Button
    lateinit var saveBtn : Button
    lateinit var homeBtn : Button
    lateinit var gameView : GameViewModel
    lateinit var score: TextView
    lateinit var highScore: TextView
    lateinit var msg: TextView
    lateinit var scoreText: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_start)
        share = findViewById(R.id.shareBtn)
        web = findViewById(R.id.webBtn)
        email = findViewById(R.id.emailBtn)
        playBtn = findViewById(R.id.playBtn)
        highScoreBtn = findViewById(R.id.highScoreBtn)
        saveBtn = findViewById(R.id.saveBtn)
        homeBtn = findViewById(R.id.homeBtn)
        layout = findViewById(R.id.layout)
        score = findViewById(R.id.score)
        highScore = findViewById(R.id.highScore)
        msg = findViewById(R.id.msg)
        scoreText = findViewById(R.id.ScoreText)
        gameView = GameViewModel(this, this)

        saveBtn.visibility = View.GONE
        homeBtn.visibility = View.GONE

        homeBtn.setOnClickListener{
            startActivity(Intent(this, MainWelcome::class.java))
        }

        val url = "https://bikefeverSimulator.com"

        share.setOnClickListener{
            val intent = Intent(Intent.ACTION_SEND)
            intent.type = "text/plain"
            intent.putExtra("Share this", url)
            val chooser = Intent.createChooser(intent, "Share using....")
            startActivity(chooser)
        }

        web.setOnClickListener{
            val uri = Uri.parse(url)
            val intent = Intent()
            intent.action = Intent.ACTION_VIEW
            intent.data = uri
            startActivity(intent)
        }

        email.setOnClickListener{
            val mailTo = arrayOf("teamBikeFever@gmail.com")
            val subject = "Feedbacks"
            val mailBody = ""

            val intent = Intent()
            intent.action = Intent.ACTION_SEND
            intent.type = "message/rfc822"
            intent.putExtra(Intent.EXTRA_EMAIL,mailTo)
            intent.putExtra(Intent.EXTRA_SUBJECT,subject)
            intent.putExtra(Intent.EXTRA_TEXT,mailBody)
            startActivity(intent)
        }

        playBtn.setOnClickListener{
            gameView.setBackgroundResource(R.drawable.road)
            layout.addView(gameView)
            playBtn.visibility= View.GONE
            saveBtn.visibility= View.GONE
            highScoreBtn.visibility= View.GONE
            score.visibility= View.GONE
        }

        //Shared Preferences
        val sharedPref = getSharedPreferences("myPref", MODE_PRIVATE)
        val editor = sharedPref.edit()

        saveBtn.setOnClickListener{
            val score = score.text.toString()

            editor.apply{
                putString("Score", score)
                apply()
            }
        }

        highScoreBtn.setOnClickListener {
            val pScore = sharedPref.getString("Score", null)

            highScore.text = pScore

        }

    }

    override fun gameClose(gScore: Int) {
        score.text = "$gScore"
        layout.removeView(gameView)
        playBtn.visibility= View.GONE
        homeBtn.visibility = View.VISIBLE
        saveBtn.visibility=View.VISIBLE
        highScoreBtn.visibility= View.GONE
        highScore.visibility = View.VISIBLE
        score.visibility= View.VISIBLE
    }

    override fun gameCloseText(gEndText: String) {
        msg.text = "$gEndText"
        layout.removeView(gameView)
        playBtn.visibility= View.GONE
        homeBtn.visibility = View.VISIBLE
        saveBtn.visibility=View.VISIBLE
        highScoreBtn.visibility= View.GONE
        highScore.visibility = View.VISIBLE
        score.visibility= View.VISIBLE
    }

    override fun gameScore(gScoreText: String) {
        scoreText.text = "$gScoreText"
        layout.removeView(gameView)
        playBtn.visibility= View.GONE
        homeBtn.visibility = View.VISIBLE
        saveBtn.visibility=View.VISIBLE
        highScoreBtn.visibility= View.GONE
        highScore.visibility = View.VISIBLE
        score.visibility= View.VISIBLE
    }

    override fun gameButton(gPlayBtn: String) {
        playBtn.text = "$gPlayBtn"
        layout.removeView(gameView)
        playBtn.visibility= View.GONE
        homeBtn.visibility = View.VISIBLE
        saveBtn.visibility=View.VISIBLE
        highScoreBtn.visibility= View.GONE
        highScore.visibility = View.VISIBLE
        score.visibility= View.VISIBLE
    }

}