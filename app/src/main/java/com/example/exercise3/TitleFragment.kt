package com.example.exercise3

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.core.content.ContextCompat.getSystemService
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import com.example.exercise3.databinding.FragmentTitleBinding


class TitleFragment : Fragment() {

    private val myName: MyName = MyName()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val binding: FragmentTitleBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_title, container, false)

        binding.myName = myName

        binding.acceptNickname.setOnClickListener {
            binding.apply {
                myName?.nickname = nickname.text.toString()
                invalidateAll()
                nickname.visibility = View.GONE
                acceptNickname.visibility = View.GONE
                nicknameText.visibility = View.VISIBLE
            }
            val imm = activity!!.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view!!.windowToken, 0)
        }

        binding.playButton.setOnClickListener { view : View ->
            view.findNavController().navigate(R.id.action_titleFragment_to_gameFragment)
        }

        return binding.root
    }


//    private lateinit var nicknameEditText: EditText //displays initial nickname
//    private lateinit var nicknameTextView: TextView //displays the new nickname entered
//    private lateinit var nicknameButton: Button //changes the name when pressed
//
//
//
//    override fun onCreateView(
//        inflater: LayoutInflater, container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View? {
//        val view: View = inflater.inflate(R.layout.fragment_title, container, false)
//
//        nicknameEditText = getView()!!.findViewById(R.id.nickname)
//        nicknameTextView = getView()!!.findViewById(R.id.nickname_text)
//        nicknameButton = getView()!!.findViewById(R.id.accept_nickname)
//
//        getView()!!.findViewById<TextView>(R.id.nickname_text).setOnClickListener {
//            updateNickname(it)
//        }
//
//        getView()!!.findViewById<TextView>(R.id.play_button).setOnClickListener {
//            view.findNavController().navigate(R.id.action_titleFragment_to_gameFragment)
//        }
//
//        return view
//    }
//
//    fun saveNewNickname(view: View) { //when done button is clicked, saves the entered nickname and sets new visibilities
//        nicknameTextView.text = nicknameEditText.text
//        nicknameTextView.visibility = View.VISIBLE
//
//        nicknameEditText.visibility = View.GONE
//        nicknameButton.visibility = View.GONE
//
//        val inputMethodManager = activity!!.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
//        inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
//    }
//
//    fun updateNickname(view: View) { //when nicknameTextView is clicked, allows user to type new nickname
//        nicknameTextView.visibility = View.GONE
//
//        nicknameEditText.visibility = View.VISIBLE
//        nicknameButton.visibility = View.VISIBLE
//
//        nicknameEditText.requestFocus()
//        val imm = activity!!.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
//        imm.showSoftInput(nicknameEditText, 0)
//    }

}
