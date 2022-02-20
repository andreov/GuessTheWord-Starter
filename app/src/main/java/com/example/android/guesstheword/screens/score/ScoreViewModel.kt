package com.example.android.guesstheword.screens.score

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ScoreViewModel(finalScore: Int):ViewModel() {

    //var score = 0

    // LiveData для кнопки PlayAgain in ScoreFragment
    private val _eventPlayAgain = MutableLiveData<Boolean>()
    val eventPlayAgain: LiveData<Boolean>
        get() = _eventPlayAgain

    private val _score: MutableLiveData<Int> by lazy { MutableLiveData<Int>() }
    val score: LiveData<Int>
        get() = _score

    init {
        _score.value = finalScore
        Log.i("ScoreViewModel", "Final score is $score")
    }

    fun onPlayAgain() {
        _eventPlayAgain.value = true
    }

    fun onPlayAgainComplete() {
        _eventPlayAgain.value = false
    }

}