/*
 * Copyright (C) 2019 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.android.guesstheword.screens.score

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.android.guesstheword.R
import com.example.android.guesstheword.databinding.ScoreFragmentBinding
import com.example.android.guesstheword.screens.game.SharedViewModel
import java.util.Observer

/**
 * Fragment where the final score is shown, after the game is over
 */
class ScoreFragment : Fragment() {


    private val viewModel: SharedViewModel by activityViewModels()
    //private val viewModel: GameViewModel by activityViewModels()

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {

        // принимает количество очков (args.score) с помощью Bundle
        //val args =ScoreFragmentArgs.fromBundle(requireArguments())
        //viewModel= ViewModelProvider(this).get(ScoreViewModel::class.java)
        //viewModel.score.value = args.score

        // Inflate view and obtain an instance of the binding class.
        val binding: ScoreFragmentBinding = DataBindingUtil.inflate(
                inflater,
                R.layout.score_fragment,
                container,
                false
        )
        viewModel.score.observe(viewLifecycleOwner) {
            binding.scoreText.text = it.toString()
        }

        binding.playAgainButton.setOnClickListener { viewModel.onPlayAgain() }

        // Navigates back to game when button is pressed
        viewModel.eventPlayAgain.observe(viewLifecycleOwner) { playAgain ->
            if (playAgain) {
                findNavController().navigate(ScoreFragmentDirections.actionRestart())
                viewModel.onPlayAgainComplete()
                viewModel.onRestartViewModel()
            }
        }

        //binding.scoreText.text = viewModel.score.toString()

        return binding.root
    }
}
