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

package com.example.android.guesstheword.screens.game

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.NavHostFragment
import com.example.android.guesstheword.R
import com.example.android.guesstheword.databinding.GameFragmentBinding

/**
 * Fragment where the game is played
 */
class GameFragment : Fragment() {

    private lateinit var binding: GameFragmentBinding

    //привязка viewModel к фрагменту
    private val viewModel: GameViewModel by activityViewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {

        //инициализация viewModel с помощью ViewModelProvider.get

        //viewModel = ViewModelProvider(this)[SharedViewModel::class.java]
        Log.i("GameFragment", "Called ViewModelProvider.get")

        //установка наблюдателей
        viewModel.score.observe(viewLifecycleOwner) { newScore ->
            binding.scoreText.text = newScore.toString()
        }

        viewModel.word.observe(viewLifecycleOwner) { newWord ->
            binding.wordText.text = newWord
        }
        viewModel.eventGameFinish.observe(viewLifecycleOwner) { newFinish ->
            if (newFinish) gameFinished()
        }

        // Inflate view and obtain an instance of the binding class
        binding = DataBindingUtil.inflate(
                inflater,
                R.layout.game_fragment,
                container,
                false
        )

        binding.correctButton.setOnClickListener { onCorrect() }
        binding.skipButton.setOnClickListener { onSkip() }
        binding.endGameButton.setOnClickListener { onEndGame() }
        //updateScoreText()
        //updateWordText()
        return binding.root

    }

    /** Methods for button click handlers **/

    private fun onSkip() {
        viewModel.onSkip()
        //updateWordText()
       // updateScoreText()
    }

    private fun onCorrect() {
        viewModel.onCorrect()
        //updateScoreText()
       // updateWordText()
    }

    /**
     * Called when the game is finished
     */
    private fun gameFinished() {
        Toast.makeText(activity, "Game has just finished", Toast.LENGTH_SHORT).show()
        val action = GameFragmentDirections.actionGameToScore()
        action.score = viewModel.score.value?:0
        NavHostFragment.findNavController(this).navigate(action)
        viewModel.onGameFinishComplete()

    }

    private fun onEndGame(){
        gameFinished()
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.i("GameFragment", "destroy")
    }

    /** Methods for updating the UI **/
    //при установке наблюдателя обновление вьюхи происходит автоматически

//    private fun updateWordText() {
//        binding.wordText.text = viewModel.word.value
//    }
//
//    private fun updateScoreText() {
//        binding.scoreText.text = viewModel.score.value.toString()
//    }

}
