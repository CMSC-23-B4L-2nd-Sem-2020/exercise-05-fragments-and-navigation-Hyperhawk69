//Cabuslay, Ryan Vincent L.
//2018-12076
//02-26-2020

package com.example.exercise3

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private var mCount:Int = 0 //stores number of clicks

    private lateinit var mViewCount: TextView //shows number of clicks

    private var gameBoard = arrayOf<Array<Boolean>>() //5x5 boolean array of the state of the board

    private var index = arrayOf<Array<Int>>() //5x5 int array of the positions of each box's id number

    private lateinit var nicknameEditText: EditText //displays initial nickname
    private lateinit var nicknameTextView: TextView //displays the new nickname entered
    private lateinit var nicknameButton: Button //changes the name when pressed
    private lateinit var retryButton: Button //resets the game board and number of clicks

    private lateinit var boxesView: Array<TextView> //array of each TextView box

    private var boxesId = arrayOf( //creates array of each box's R.id
        R.id.box_0,
        R.id.box_1,
        R.id.box_2,
        R.id.box_3,
        R.id.box_4,
        R.id.box_5,
        R.id.box_6,
        R.id.box_7,
        R.id.box_8,
        R.id.box_9,
        R.id.box_10,
        R.id.box_11,
        R.id.box_12,
        R.id.box_13,
        R.id.box_14,
        R.id.box_15,
        R.id.box_16,
        R.id.box_17,
        R.id.box_18,
        R.id.box_19,
        R.id.box_20,
        R.id.box_21,
        R.id.box_22,
        R.id.box_23,
        R.id.box_24)

    override fun onCreate(savedInstanceState: Bundle?) { //creates the app, initializes the game board and listeners
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        nicknameEditText = findViewById<EditText>(R.id.nickname)
        nicknameTextView = findViewById(R.id.nickname_text)
        nicknameButton = findViewById(R.id.accept_nickname)
        retryButton = findViewById(R.id.retry_button)

        findViewById<TextView>(R.id.nickname_text).setOnClickListener {
            updateNickname(it)
        }

        initGameBoard()
        setListeners()

        mViewCount = findViewById(R.id.show_count)

        createBoxesView()
    }

    private fun createBoxesView(){ //creates the array containing each TextView box
        boxesView = arrayOf(box_0,
            box_1,
            box_2,
            box_3,
            box_4,
            box_5,
            box_6,
            box_7,
            box_8,
            box_9,
            box_10,
            box_11,
            box_12,
            box_13,
            box_14,
            box_15,
            box_16,
            box_17,
            box_18,
            box_19,
            box_20,
            box_21,
            box_22,
            box_23,
            box_24)
    }

    fun saveNewNickname(view: View) { //when done button is clicked, saves the entered nickname and sets new visibilities
        nicknameTextView.text = nicknameEditText.text
        nicknameTextView.visibility = View.VISIBLE

        nicknameEditText.visibility = View.GONE
        nicknameButton.visibility = View.GONE

        val inputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
    }

    fun updateNickname(view: View) { //when nicknameTextView is clicked, allows user to type new nickname
        nicknameTextView.visibility = View.GONE

        nicknameEditText.visibility = View.VISIBLE
        nicknameButton.visibility = View.VISIBLE

        nicknameEditText.requestFocus()
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.showSoftInput(nicknameEditText, 0)
    }

    private fun initGameBoard(){ //creates 5x5 game board initialized to true and 5x5 index array of each box's id number
        for (i in 0..4) {
            var array = arrayOf<Boolean>()
            for (j in 0..4) {
                array += true
            }
            gameBoard += array
        }

        var count = 0

        for (i in 0..4) {
            var array = arrayOf<Int>()
            for (j in 0..4) {
                array += count
                count ++
            }
            index += array
        }
    }

    private fun setListeners() { //sets listeners for each box
        val clickableViews: MutableList<View> =
            mutableListOf(

            )

        for (i in 0..24){
            clickableViews.add(findViewById<View>(boxesId[i]))
        }

        for (item in clickableViews) { //when clicked, changes colors of it and its adjacent boxes
            item.setOnClickListener { makeColored(it) }
        }
    }

    private fun countUp() { //appends to the click counter and shows it
        mCount++
        mViewCount.text=mCount.toString()
    }

    private fun countReset() { //resets the click counter to 0 and shows it
        mCount=0
        mViewCount.text=mCount.toString()
    }

    private fun convertGameBoardtoBoxes(i: Int,j: Int): Int{ //converts i and j from the loop to a box's id
        return index[i][j]
    }

    private fun setColor(){ //sets the color of each box based on the boolean value of the game board array
        for (i in 0..4) {
            for (j in 0..4) {
                if(gameBoard[i][j]){
                    boxesView[convertGameBoardtoBoxes(i,j)].setBackgroundResource(R.color.YELLOW)
                }
                else{
                    boxesView[convertGameBoardtoBoxes(i,j)].setBackgroundResource(R.color.BLACK)
                }
            }
        }
    }

    private fun toggleLights(i: Int,j: Int){ //negates the boolean value of a box and its adjacent boxes and sets the color
        gameBoard[i][j]=gameBoard[i][j].not()
        if((j+1)<5) gameBoard[i][j+1]=gameBoard[i][j+1].not()
        if((i+1)<5) gameBoard[i+1][j]=gameBoard[i+1][j].not()
        if((j-1)>-1) gameBoard[i][j-1]=gameBoard[i][j-1].not()
        if((i-1)>-1) gameBoard[i-1][j]=gameBoard[i-1][j].not()

        setColor()
    }

    private fun checkWin(){ //checks if a player turned off all lights, and displays a message if so
        var win = true

        for (i in 0..4) {
            for (j in 0..4) {
                if(gameBoard[i][j]){
                    win = false
                }
            }
        }

        if(win==true){
            val toast: Toast = Toast.makeText(this,R.string.win_message, Toast.LENGTH_SHORT)
            toast.show()
        }
    }

    private fun makeColored(view: View) { //if a box is clicked, checks its index and triggers the previous functions
        for (i in 0..4) {
            for (j in 0..4) {
                if(view.id==boxesId[convertGameBoardtoBoxes(i,j)]){
                    toggleLights(i,j)
                    countUp()
                    checkWin()
                }
            }
        }

        if(retryButton.visibility==View.GONE){ //shows the retry button once a box has been clicked
            retryButton.visibility = View.VISIBLE
        }
    }

    fun retry(view: View){ //sets all game board values to true, sets all colors back to yellow, and resets click counter
        for (i in 0..4) {
            for (j in 0..4) {
                gameBoard[i][j]=true
            }
        }

        if(retryButton.visibility==View.VISIBLE){ //hides the retry button once retry button is pressed
            retryButton.visibility = View.GONE
        }

        setColor()
        countReset()
    }



}
