package com.example.mad03_fragments_and_navigation

import android.os.Bundle
import android.text.format.DateUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import com.example.mad03_fragments_and_navigation.databinding.FragmentQuizBinding
import com.example.mad03_fragments_and_navigation.viewmodel.QuizViewModel


class QuizFragment : Fragment() {
    private lateinit var binding: FragmentQuizBinding
    private lateinit var viewModel: QuizViewModel

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_quiz, container, false)
        viewModel = ViewModelProvider(this).get(QuizViewModel::class.java)

        viewModel.questionsCount.observe(viewLifecycleOwner, { counter ->
            binding.questionsCount = counter
        })

        viewModel.question.observe(viewLifecycleOwner, Observer { newQuestion ->
            binding.question = newQuestion
        })

        viewModel.index.observe(viewLifecycleOwner, { newIndex ->
            binding.index = newIndex
        })

        viewModel.endGame.observe(viewLifecycleOwner, Observer<Boolean> { hasFinished ->
            if (hasFinished) gameFinished()
        })


        binding.btnNext.setOnClickListener {
            if (getClickedRadioButton() != -1) {
                if (binding.question?.answers?.get(getClickedRadioButton())?.isCorrectAnswer == true) {
                    viewModel.onCorrect()
                }
                viewModel.nextQuestion()
            }
        }


        return binding.root
    }

    private fun gameFinished() {
        val action = QuizFragmentDirections.actionQuizFragmentToQuizEndFragment()
        action.score = viewModel.score.value ?: 0
        NavHostFragment.findNavController(this).navigate(action)
        viewModel.onEndGameFinish()
    }

    private fun getClickedRadioButton(): Int {
        val currentRadioButton = binding.answerBox.checkedRadioButtonId
        return if (currentRadioButton == -1) {
            Toast.makeText(requireContext(), "You have not selected an answer", Toast.LENGTH_SHORT).show()
            currentRadioButton
        } else {
            val radioButton: View = binding.answerBox.findViewById(currentRadioButton)
            binding.answerBox.indexOfChild(radioButton)
        }
    }

}