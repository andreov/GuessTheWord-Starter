package com.example.android.guesstheword.screens.score

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ScoreViewModel(args: ScoreFragmentArgs):ViewModel() {

    //var score = 0

    val score: MutableLiveData<Int> by lazy { MutableLiveData<Int>() }
    //val score: LiveData<Int>
       // get() = _score

    init {
        score.value = args.score
        Log.i("ScoreViewModel", "Final score is $score")
    }


}