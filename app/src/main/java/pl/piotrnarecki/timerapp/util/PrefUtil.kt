package pl.piotrnarecki.timerapp.util

import android.content.Context
import android.preference.PreferenceManager
import pl.piotrnarecki.timerapp.TimerActivity

class PrefUtil {
    companion object {

        fun getTimerLength(context: Context): Int {
            //placeholder
            return 1
        }

        private const val PREVIOUS_TIMER_LENGTH_SECONDS_ID =
            "pl.piotrnarecki.timer.previous_timer_length"

        fun getPreviousTimerLenghtSeconds(context: Context): Long {
            //placeholder
            val preferences = PreferenceManager.getDefaultSharedPreferences(context)
            return preferences.getLong(PREVIOUS_TIMER_LENGTH_SECONDS_ID, 0)

        }

        fun setPreviousTimerLenghtSeconds(seconds: Long, context: Context) {
            val editor = PreferenceManager.getDefaultSharedPreferences(context).edit()
            editor.putLong(PREVIOUS_TIMER_LENGTH_SECONDS_ID, seconds)
            editor.apply()

        }

        private const val TIMER_STATE_ID = "pl.piotrnarecki.timer.timer_state"


        fun getTimerState(context: Context): TimerActivity.TimerState {
            val preferences = PreferenceManager.getDefaultSharedPreferences(context)
            val ordinal = preferences.getInt(TIMER_STATE_ID, 0)
            return TimerActivity.TimerState.values()[ordinal]

        }

        fun setTimerState(state: TimerActivity.TimerState, context: Context) {
            val editor = PreferenceManager.getDefaultSharedPreferences(context).edit()
            val ordinal = state.ordinal
            editor.putInt(TIMER_STATE_ID, ordinal)
            editor.apply()

        }


        private const val SECONDS_REMAINING_ID =
            "pl.piotrnarecki.timer.previous_second_remaining"

        fun getSecondsRemaining(context: Context): Long {
            //placeholder
            val preferences = PreferenceManager.getDefaultSharedPreferences(context)
            return preferences.getLong(SECONDS_REMAINING_ID, 0)

        }

        fun setSecondsRemaining(seconds: Long, context: Context) {
            val editor = PreferenceManager.getDefaultSharedPreferences(context).edit()
            editor.putLong(SECONDS_REMAINING_ID, seconds)
            editor.apply()

        }


    }


}