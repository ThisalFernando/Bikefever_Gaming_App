package com.example.labexam03

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.view.MotionEvent
import android.view.View

class GameViewModel(var c :Context, var gameWork: GameWork):View(c)
{
    private var myPaint: Paint? = null
    private var speed = 1;
    private var time = 0;
    private var score = 0;
    private var bikePosition = 0;
    private val bikes = ArrayList<HashMap<String,Any>>()

    var viewWidth = 0
    var viewHeight = 0
    init {
        myPaint = Paint()
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        viewWidth = this.measuredWidth
        viewHeight = this.measuredHeight

        if(time % 700 < 10 + speed){
            val map = HashMap<String,Any>()
            map["lane"] = (0..2).random()
            map["startTime"] = time
            bikes.add(map)
        }
        time = time + 10 + speed
        val bikeWidth = viewWidth / 3
        val bikeHeight = bikeWidth + 12
        myPaint!!.style = Paint.Style.FILL
        val b = resources.getDrawable(R.drawable.bike, null)

        b.setBounds(
            bikePosition * viewWidth / 2 + viewHeight / 14 + 25,
            viewHeight - 2 - bikeHeight,
            bikePosition * viewWidth / 2 + viewWidth / 14 + bikeWidth - 25,
            viewHeight - 2
        )
        b.draw(canvas!!)
        myPaint!!.color = Color.GREEN
        var highScore = 0

        for(i in bikes.indices){
            try{
                var bikeX = bikes[i]["lane"] as Int * viewWidth / 2 + viewHeight / 14
                var bikeY = time - bikes[i]["startTime"] as Int
                val car = resources.getDrawable(R.drawable.bike02, null)

                car.setBounds(
                    bikeX + 25, bikeY - bikeHeight, bikeX + bikeWidth - 25, bikeY
                )
                car.draw(canvas)
                if(bikes[i]["lane"] as Int == bikePosition){
                    if(bikeY > viewHeight - 2 - bikeHeight && bikeY < viewHeight - 2){
                        gameWork.gameClose(score)
                        gameWork.gameCloseText("GAME OVER!")
                        gameWork.gameScore("SCORE GAINED")
                        gameWork.gameButton("Play Again")
                    }
                }
                if(bikeY > viewHeight + bikeHeight)
                {
                    bikes.removeAt(i)
                    score++
                    speed = 1 + Math.abs(score / 8)
                    if(score > highScore){
                        highScore = score
                    }
                }
            }
            catch (e:Exception){
                e.printStackTrace()
            }
        }

        myPaint!!.color = Color.WHITE
        myPaint!!.textSize = 60F
        canvas.drawText("Score : $score", 80f, 80f, myPaint!!)
        canvas.drawText("Speed : $speed", 380f, 80f, myPaint!!)
        invalidate()

    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        when(event!!.action){
            MotionEvent.ACTION_DOWN ->{
                val x1 = event.x
                if(x1 < viewWidth/2){
                    if(bikePosition > 0){
                        bikePosition--
                    }
                }
                if(x1 > viewWidth / 2){
                    if(bikePosition < 2){
                        bikePosition++
                    }
                }
                invalidate()
            }
            MotionEvent.ACTION_UP->{}
        }
        return true
    }
}