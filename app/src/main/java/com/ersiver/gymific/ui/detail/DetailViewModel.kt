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
    private lateinit var timer: CountDownTimer

    private val workoutId = MutableLiveData<Int>()

    private var _workout = workoutId.switchMap { repository.getWorkout(it) }
    val workout: LiveData<Workout> = _workout

    val _workoutTimeMillis = MutableLiveData<Long>()
    val workoutTimeMillis: LiveData<Long> = _workoutTimeMillis

    //Data displayed in the center of the circular ProgressBar.
    private val _timeRemainingMillis = MutableLiveData<Long>()
    val timeRemainingMillis: LiveData<Long> = _timeRemainingMillis

    //Value saved once the pause is clicked.
    private val _pausedWorkoutTimeMillis = MutableLiveData<Long>()

    //Responsible for the behaviours of play, pause and stop buttons.
    private val _timerStatus = MutableLiveData<TimerStatus>().apply {
        value = TimerStatus.OFF
    }
    val timerStatus: LiveData<TimerStatus> = _timerStatus

    fun start(id: Int) {
        workoutId.value = id
    }

    fun setWorkoutTimeMillis(time: Long){
        _workoutTimeMillis.value = time * MILLIS
        setRemainingTimeAtStart()
    }

    private fun setRemainingTimeAtStart() {
        if (_timerStatus.value == TimerStatus.OFF)
            _timeRemainingMillis.value = workoutTimeMillis.value
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
                _pausedWorkoutTimeMillis.value!!
            else
                workoutTimeMillis.value!!

        timer = object : CountDownTimer(startTimeMillis, MILLIS) {
            override fun onTick(millisUntilFinished: Long) {
                _timeRemainingMillis.postValue(millisUntilFinished)
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

        //Reset remaining time so it matches the workout time.
        setRemainingTimeAtStart()
    }

    /**
     * Executed when the pause button is clicked. Once the pause
     * time matching current onTick() is saved, the timer can be reset.
     */
    fun pauseTimer() {
        _pausedWorkoutTimeMillis.value = _timeRemainingMillis.value
        timer.cancel()
        _timeRemainingMillis.value = _pausedWorkoutTimeMillis.value
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