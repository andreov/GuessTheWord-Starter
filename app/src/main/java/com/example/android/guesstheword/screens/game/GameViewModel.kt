package com.example.android.guesstheword.screens.game

import android.os.CountDownTimer
import android.text.format.DateUtils
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel

class GameViewModel : ViewModel() {

    //константы таймера
    companion object {
        // Time when the game is over
        private const val DONE = 0L

        // Countdown time interval
        private const val ONE_SECOND = 1000L

        // Total time for the game
        private const val COUNTDOWN_TIME = 60000L
    }

    val timer: CountDownTimer

    // Countdown time
    private val _currentTime = MutableLiveData<Long>()
    //val currentTime: LiveData<Long>
        //get() = _currentTime

    // The String version of the current time
    val currentTimeString = Transformations.map(_currentTime) { time ->
        DateUtils.formatElapsedTime(time)
    }

    // The current word private
    private val _word = MutableLiveData<String>()
    val word: LiveData<String>
        get() = _word

    // The current score private
    private val _score = MutableLiveData<Int>()
    val score: LiveData<Int>
        get() = _score

    // Event which triggers the end of the game
    private val _eventGameFinish = MutableLiveData<Boolean>()
    val eventGameFinish: LiveData<Boolean>
        get() = _eventGameFinish

    // The list of words - the front of the list is the next word to guess
    private lateinit var wordList: MutableList<String>

    init {
        //initTimer()
        timer = object : CountDownTimer(COUNTDOWN_TIME, ONE_SECOND) {

            override fun onTick(millisUntilFinished: Long) {
                _currentTime.value = millisUntilFinished/ONE_SECOND
            }
            override fun onFinish() {
                _currentTime.value = DONE
                onGameFinish()
            }
        }
        timer.start()

        initModel()
        Log.i("GameViewModel", "GameViewModel Created!")
    }



    private fun initModel(){
        _word.value = ""
        _score.value = 0
        resetList()
        nextWord()
    }

    override fun onCleared() {
        super.onCleared()
        // Cancel the timer
        timer.cancel()
        Log.i("GameViewModel", "GameViewModel destroyed!")
    }


    /**
     * Resets the list of words and randomizes the order
     */
    private fun resetList() {
        wordList = mutableListOf(
            "queen",
            "hospital",
            "basketball",
            "cat",
            "change",
            "snail",
            "soup",
            "calendar",
            "sad",
            "desk",
            "guitar",
            "home",
            "railway",
            "zebra",
            "jelly",
            "car",
            "crow",
            "trade",
            "bag",
            "roll",
            "bubble"
        )
        wordList.shuffle()
    }

    /** Methods for buttons presses **/

    fun onSkip() {
        when (_score.value) {
            0 -> return
            else -> {_score.value = (_score.value)?.minus(1)
                nextWord()
            }
        }
        //_score.value = (_score.value)?.minus(1)
        //nextWord()
    }

    fun onCorrect() {
        _score.value = (_score.value)?.plus(1)
        nextWord()
    }

    /** Method for the game completed event **/
    fun onGameFinish() {
        _eventGameFinish.value = true
    }

    /** Method for the game completed event **/
    fun onGameFinishComplete() {
        _eventGameFinish.value = false
        timer.cancel()
        _currentTime.value = DONE
        wordList.clear()
        initModel()

    }

    /**
     * Moves to the next word in the list
     */
    private fun nextWord() {
        if (wordList.isEmpty()) {
            onGameFinish()
            //resetList()
        } else {
            //Select and remove a word from the list
            _word.value = wordList.removeAt(0)
        }
    }



//    fun onRestartViewModel(){
//        _word.value = ""
//        _score.value = 0
//        resetList()
//        nextWord()
//    }


}