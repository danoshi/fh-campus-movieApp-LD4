package com.example.mad03_fragments_and_navigation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.example.mad03_fragments_and_navigation.databinding.FragmentQuizEndBinding
import com.example.mad03_fragments_and_navigation.viewmodel.QuizEndViewModel
import com.example.mad03_fragments_and_navigation.viewmodel.QuizEndViewModelFactory


class QuizEndFragment : Fragment() {

    private lateinit var binding: FragmentQuizEndBinding
    private lateinit var viewModel: QuizEndViewModel
    private lateinit var viewModelFactory: QuizEndViewModelFactory

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_quiz_end, container, false)
        viewModelFactory = QuizEndViewModelFactory(QuizEndFragmentArgs.fromBundle(requireArguments()).score)
        viewModel = ViewModelProvider(this, viewModelFactory).get(QuizEndViewModel::class.java)
        binding.textView7.text = QuizEndFragmentArgs.fromBundle(requireArguments()).score.toString()
        restartQuiz()
        return binding.root
    }
    private fun restartQuiz() {
        binding.btnRestart.setOnClickListener {
            view?.findNavController()
                    ?.navigate(QuizEndFragmentDirections.actionQuizEndFragmentToQuizFragment())
        }
    }
}