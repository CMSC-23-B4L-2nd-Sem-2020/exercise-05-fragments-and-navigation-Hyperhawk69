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

}
