package com.ersiver.gymific.ui.detail

import android.os.CountDownTimer
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.ersiver.gymific.model.Workout
import com.ersiver.gymific.repository.UserPreferenceRepository
import com.ersiver.gymific.repository.WorkoutRepository
import com.ersiver.gymific.util.MILLIS
import kotlinx.coroutines.launch
import timber.log.Timber

enum class TimerStatus {
    ON, OFF, PAUSED
}

class DetailViewModel @ViewModelInject constructor(
    private val repository: WorkoutRepository,
    private val userPreferenceRepository: UserPreferenceRepository
) :
    ViewModel() {
    init {
        Timber.i("DetailViewModel init")
    }

    private lateinit var timer: CountDownTimer

    /**
     * Unique workout id, will be init on start().
     */
    private val workoutId = MutableLiveData<Int>()

    /**
     * Transform the id to get the matching workout.
     */
    private var _workout = workoutId.switchMap { repository.getWorkout(it) }
    val workout: LiveData<Workout> = _workout

    /**
     * Transform workout to get its time.
     */
    val workoutTimeMillis: LiveData<Long> = workout.map { workout ->
        workout.time * MILLIS
    }

    /**
     * Get the pause time, saved when the user clicked the pause
     * and navigated to another screen. Transform workout to get
     * the time Flow stored in DataStore and convert into LiveData.
     */
    val savedPausedTime: LiveData<Long> = workout.switchMap { workout ->
        userPreferenceRepository.getPausedTime(workout.id).asLiveData()
    }

    /**
     * Running workout time, that will be displayed
     * in the center of the circular ProgressBar.
     */
    private val _runningTimeMillis = MutableLiveData<Long>()
    val runningTime: LiveData<Long> = _runningTimeMillis

    /**
     * Running workout time, caught once the pause button is clicked.
     */
    private val _pausedWorkoutTimeMillis = MutableLiveData<Long>()
    val pausedWorkoutTimeMillis: LiveData<Long> = _pausedWorkoutTimeMillis

    /**
     * Manages behaviours of play, pause and stop buttons via BindingAdapter.
     */
    private val _timerStatus = MutableLiveData<TimerStatus>()
    val timerStatus: LiveData<TimerStatus> = _timerStatus

    /**
     * Invoked on start. Triggers loading of the workout from the db.
     */
    fun start(id: Int) {
        workoutId.value = id
    }

    /**
     * Invoked when a value of savedPausedTime is changed. To prevent
     * calling on orientation change timerStatus conditions must be met.
     */
    fun manageTimer(savedPausedTime: Long) {
        if (timerStatus.value != TimerStatus.ON) {
            if (savedPausedTime == 0L)
                setTimerStartMode()
            else
                setTimerResumeMode(savedPausedTime)
        }
    }

    /**
     * Set the timer ready to countdown the full workout time.
     */
    private fun setTimerStartMode() {
        _pausedWorkoutTimeMillis.value = 0
        _timerStatus.value = TimerStatus.OFF
        _runningTimeMillis.value = workoutTimeMillis.value
        createTimer(workoutTimeMillis.value!!)
    }

    /**
     * Set the timer ready to resume countdown from the paused time.
     */
    private fun setTimerResumeMode(savedPausedTime: Long) {
        _pausedWorkoutTimeMillis.value = savedPausedTime
        _timerStatus.value = TimerStatus.PAUSED
        _runningTimeMillis.value = _pausedWorkoutTimeMillis.value
        createTimer(savedPausedTime)
    }

    /**
     * Init CountDownTimer. Pass a proper value to be countdown.
     */
    private fun createTimer(duration: Long) {
        timer = object : CountDownTimer(duration, MILLIS) {
            override fun onTick(millisUntilFinished: Long) {
                _runningTimeMillis.postValue(millisUntilFinished)
            }
            override fun onFinish() {
                resetTimer()
            }
        }
    }

    /**
     * Executes when the play button is clicked.
     */
    fun startTimer() {
        timer.start()
        _timerStatus.value = TimerStatus.ON
        _pausedWorkoutTimeMillis.value = 0
    }

    /**
     * Executed when the stop button is clicked.
     */
    fun resetTimer() {
        timer.cancel()
        setTimerStartMode()
    }

    /**
     * Executed when the pause button is clicked.
     */
    fun pauseTimer() {
        val savedPausedTime = _runningTimeMillis.value!!
        timer.cancel()
        setTimerResumeMode(savedPausedTime)
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

    /**
     * Save the paused time.
     */
    fun savePausedTime(id: Int, pauseTime: Long) {
        viewModelScope.launch {
            userPreferenceRepository.savePausedTime(
                id, pauseTime
            )
        }
    }
}