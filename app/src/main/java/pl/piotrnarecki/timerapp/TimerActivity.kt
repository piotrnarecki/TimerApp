package pl.piotrnarecki.timerapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import kotlinx.android.synthetic.main.activity_timer.*
import kotlinx.android.synthetic.main.content_timer.*
import pl.piotrnarecki.timerapp.util.PrefUtil
import kotlin.concurrent.timer

class TimerActivity : AppCompatActivity() {

    enum class TimerState {
        Stopped, Paused, Running
    }


    private lateinit var timer: CountDownTimer
    private var timerLengthSeconds = 0L // long
    private var timerState = TimerState.Stopped

    private var secondRemaining = 0L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_timer)

        supportActionBar?.setIcon(R.drawable.timer)

        supportActionBar?.title = "     Timer"

        fab_start.setOnClickListener {
            startTimer()
            timerState = TimerState.Running
            updateButtons()

        }

        fab_pause.setOnClickListener {
            timer.cancel()
            timerState = TimerState.Paused
            updateButtons()

        }

        fab_stop.setOnClickListener {
            timer.cancel()
            onTimerFinished()


        }


    }

    override fun onResume() {
        super.onResume()

        initTimer()

        //TODO: remove background timer, hide notification
    }

    override fun onPause() {
        super.onPause()


        if (timerState == TimerState.Running) {
            timer.cancel()
            //TODO: start background timer, show notification
        } else if (timerState == TimerState.Paused) {
            //TODO: show notification
        }


        PrefUtil.setPreviousTimerLenghtSeconds(timerLengthSeconds, this)
        PrefUtil.setSecondsRemaining(secondRemaining, this)
        PrefUtil.setTimerState(timerState, this)


    }

    private fun initTimer() {

        timerState = PrefUtil.getTimerState(this)

        if (timerState == TimerState.Stopped) {
            setNewTimerLength()
        } else {
            setPreviousTimerLength()
        }

        secondRemaining = if (timerState == TimerState.Running || timerState == TimerState.Paused) {
            PrefUtil.getSecondsRemaining(this)
        } else {
            timerLengthSeconds
        }

        //TODO: change secondRemaining according to where it stopped

        //

        if (timerState == TimerState.Running) {
            startTimer()
            updateButtons()
            updateCountdownUI()
        }

    }

    private fun onTimerFinished() {
        timerState = TimerState.Stopped

//        timerLength()
        progress_countdown.progress = 0
        PrefUtil.setSecondsRemaining(timerLengthSeconds, this)
        secondRemaining = timerLengthSeconds


        updateButtons()
        updateCountdownUI()
    }


    private fun setNewTimerLength() {
        val lengthInMinutes = PrefUtil.getTimerLength(this)
        timerLengthSeconds = (lengthInMinutes * 60L)
        progress_countdown.max = timerLengthSeconds.toInt()
    }


    private fun setPreviousTimerLength() {
        timerLengthSeconds = PrefUtil.getPreviousTimerLenghtSeconds(this)
        progress_countdown.max = timerLengthSeconds.toInt()
    }

    private fun updateCountdownUI() {
        val minutesUntilFinished = secondRemaining / 60
        val secondsInMinutesUntilFinished = secondRemaining - minutesUntilFinished * 60
        val secondStr = secondsInMinutesUntilFinished.toString()
        textView_countdown.text =
            "$minutesUntilFinished:${if (secondStr.length == 2) secondStr else "0" + secondStr}"

        progress_countdown.progress = (timerLengthSeconds - secondRemaining).toInt()
    }

    private fun updateButtons() {
        when (timerState) {
            TimerState.Running -> {
                fab_start.isEnabled = false
                fab_pause.isEnabled = true
                fab_stop.isEnabled = true

            }

            TimerState.Stopped -> {
                fab_start.isEnabled = true
                fab_pause.isEnabled = true
                fab_stop.isEnabled = false
            }

            TimerState.Paused -> {
                fab_start.isEnabled = true
                fab_pause.isEnabled = false
                fab_stop.isEnabled = true
            }


        }
    }


    private fun startTimer() {
        timerState = TimerState.Running
        timer = object : CountDownTimer(secondRemaining * 1000, 1000) {

            override fun onFinish() = onTimerFinished()

            override fun onTick(millisUntilFinished: Long) {
                secondRemaining = millisUntilFinished / 1000
                updateCountdownUI()
            }

        }.start()

    }
}