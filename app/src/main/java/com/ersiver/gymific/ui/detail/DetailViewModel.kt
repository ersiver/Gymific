package com.ersiver.gymific.ui.detail

import android.os.CountDownTimer
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.ersiver.gymific.model.Workout
import com.ersiver.gymific.repository.WorkoutRepository
import com.ersiver.gymific.util.MILLIS
import kotlinx.coroutines.launch

enum class TimerStatus {
    ON, OFF, PAUSED
}

class DetailViewModel @ViewModelInject constructor(private val repository: WorkoutRepository) :
    ViewModel() {
    private var workoutSource: LiveData<Workout> = MutableLiveData()
    private val _workout = MediatorLiveData<Workout>()
    val workout: LiveData<Workout> = _workout

    private lateinit var timer: CountDownTimer

    private val _workoutTimeMillis = MutableLiveData<Long>()
    val workoutTimeMillis: LiveData<Long>
        get() = _workoutTimeMillis

    private val _pausedWorkoutTimeMilis = MutableLiveData<Long>()

    //Represent time to be displayed on the progress bar.
    private val _timeRemainingMilis = MutableLiveData<Long>()
    val timeRemainingMilis: LiveData<Long>
        get() = _timeRemainingMilis

    //Responsible for the behaviours of play, pause and stop buttons.
    private val _timerStatus = MutableLiveData<TimerStatus>().apply {
        value = TimerStatus.OFF
    }
    val timerStatus: LiveData<TimerStatus> = _timerStatus

    //Init value of selected workout.
    fun start(id: Int) {
        _workout.removeSource(workoutSource)
        viewModelScope.launch {
            workoutSource = repository.getWorkout(id)
        }

        _workout.addSource(workoutSource) {
            _workout.value = it
        }
    }

    fun setWorkoutTimeMillis(time: Long) {
        _workoutTimeMillis.value = time * MILLIS
        setStartTimeRemaining()
    }

    private fun setStartTimeRemaining() {
        if (_timerStatus.value == TimerStatus.OFF)
            _timeRemainingMilis.value = workoutTimeMillis.value
    }

    /**
     * Executes when the play button is clicked.
     *
     * Pass an appropriate value to the CountDownTimer.
     * If a timer is to be run for the first time, then
     * pass full workout time. If't it's resumed after being
     * paused, then pass the value that was saved on pause.
     *
     * This method cannot be invoked when the timer is running,
     * hence else will be always TimerStatus.OFF
     */
    fun startTimer() {
        val startTimeMillis =
            if (_timerStatus.value == TimerStatus.PAUSED)
                _pausedWorkoutTimeMilis.value!!
            else
                workoutTimeMillis.value!!

        timer = object : CountDownTimer(startTimeMillis, MILLIS) {
            override fun onTick(millisUntilFinished: Long) {
                _timeRemainingMilis.postValue(millisUntilFinished)
            }

            override fun onFinish() {
                resetTimer()
            }
        }.start()
        _timerStatus.value = TimerStatus.ON
    }

    /**
     * Executed when the stop button is clicked.
     */
    fun resetTimer() {
        timer.cancel()
        _timerStatus.value = TimerStatus.OFF
        setStartTimeRemaining()
    }

    /**
     * Executed when the pause button is clicked.
     */
    fun pauseTimer() {
        _pausedWorkoutTimeMilis.value = _timeRemainingMilis.value
        timer.cancel()
        _timeRemainingMilis.value = _pausedWorkoutTimeMilis.value
        _timerStatus.value = TimerStatus.PAUSED
    }

    /**
     * Executed when the MenuItem favourite  is clicked.
     */
    fun setFavourite(workout: Workout) {
        val currentSaveStatus = workout.isSaved
        workout.isSaved = !currentSaveStatus
        workout.timeSaved = System.currentTimeMillis()
        viewModelScope.launch {
            repository.update(workout)
        }
    }
}