package com.example.exercise3

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController


class GameFragment : Fragment() {
    private var mCount:Int = 0 //stores number of clicks

    private lateinit var mViewCount: TextView //shows number of clicks

    private var gameBoard = arrayOf<Array<Boolean>>() //5x5 boolean array of the state of the board

    private var index = arrayOf<Array<Int>>() //5x5 int array of the positions of each box's id number

    private lateinit var retryButton: Button //resets the game board and number of clicks

    private var boxesView = arrayOf<TextView>() //array of each TextView box

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_game, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mViewCount = getView()!!.findViewById(R.id.show_count)
        retryButton = getView()!!.findViewById(R.id.retry_button)

        initGameBoard()
        setListeners()
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

    private fun getResource(i: Int): Int{ //accesses each box's id dynamically and returns its id
        val boxID = "box_$i"

        return view!!.resources.getIdentifier(boxID, "id", activity!!.baseContext.packageName)
    }

    private fun setListeners() { //sets listeners for each box
        val clickableViews: MutableList<View> =
            mutableListOf(

            )

        for (i in 0..24){ //adds each box to clickableViews and boxesView
            val resID = getResource(i)
            clickableViews.add(view!!.findViewById<View>(resID))
            boxesView += view!!.findViewById<TextView>(resID)
        }

        for (item in clickableViews) { //when clicked, changes colors of it and its adjacent boxes
            item.setOnClickListener { switchLights(it) }
        }

        view!!.findViewById<TextView>(R.id.retry_button).setOnClickListener {
            retry(it)
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

        if(win){
            view!!.findNavController().navigate(R.id.action_gameFragment_to_gameWonFragment)
        }
    }

    private fun switchLights(view: View) { //if a box is clicked, checks its index and triggers the previous functions
        for (i in 0..4) {
            for (j in 0..4) {
                val x = convertGameBoardtoBoxes(i,j)
                val resID = getResource(x)
                if(view.id==resID){
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
