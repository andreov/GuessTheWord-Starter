package com.example.android.guesstheword.screens.score

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.android.guesstheword.screens.game.GameViewModel

class ScoreViewModel:ViewModel() {

    var score = 0
    init {
        Log.i("ScoreViewModel", "Final score is $score")
    }


}