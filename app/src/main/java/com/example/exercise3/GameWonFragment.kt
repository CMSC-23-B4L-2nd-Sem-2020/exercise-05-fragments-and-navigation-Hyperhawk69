//Cabuslay, Ryan Vincent L.
//2018-12076
//03-19-2020

package com.example.exercise3

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.example.exercise3.databinding.FragmentGameWonBinding


class GameWonFragment : Fragment() {
    private val myName: MyName = MyName()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val binding: FragmentGameWonBinding = DataBindingUtil.inflate(
                inflater, R.layout.fragment_game_won, container, false)

        binding.myName = myName //binds myName data class

        binding.apply {
            myName?.score = GameFragment.finalCount.toString() //displays the number of clicks when game was completed
        }

        binding.playAgain.setOnClickListener{view : View -> //if Play Again button is pressed, navigates to start screen
            GameFragment.finalCount = 0
            view.findNavController().navigate(R.id.action_gameWonFragment_to_titleFragment)
        }

        return binding.root
    }
}
