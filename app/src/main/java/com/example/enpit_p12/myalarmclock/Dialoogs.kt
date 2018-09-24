package com.example.enpit_p12.myalarmclock

import android.app.*
import android.app.AlertDialog
import android.content.BroadcastReceiver
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.provider.CalendarContract
import android.support.annotation.RequiresApi
import android.view.View
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast
import org.jetbrains.anko.intentFor
import org.jetbrains.anko.newTask
import android.support.v7.app.*
import android.widget.DatePicker
import android.widget.TimePicker
import java.time.MonthDay
import java.time.OffsetDateTime
import java.util.*


class SimpleAlertDialog : android.support.v4.app.DialogFragment() {
    @RequiresApi(Build.VERSION_CODES.M)
    interface onClickListener {
        fun onPostiveClick()
        fun onNegativeClick()
    }

    private lateinit var listener: onClickListener

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        if (context is SimpleAlertDialog.onClickListener) {
            listener = context
        }
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val context = context
        if (context == null)
            return super.onCreateDialog(savedInstanceState)

        val builder = android.support.v7.app.AlertDialog.Builder(context).apply {
            setMessage("時間になりました！")
            setPositiveButton("起きる") { daialog, which ->
                context.toast("起きるがクリックされました")
                listener.onPostiveClick()
            }

            setNegativeButton("あと5分") { dailog, which ->
                context.toast("あと5分がクリックされました")
                listener.onNegativeClick()
            }
        }
            return builder.create()
        }
    }

class DatePickerFragment : android.support.v4.app.DialogFragment(),
        DatePickerDialog.OnDateSetListener {

    interface OnDateSelectedListener {
        fun onSelected(year: Int, mounth: Int, date: Int)
    }

    private lateinit var listener: OnDateSelectedListener

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        if(context is OnDateSelectedListener) {
            listener = context
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val mounth = c.get(Calendar.MONTH)
        val date = c.get(Calendar.DAY_OF_MONTH)
        return DatePickerDialog(context, this, year, mounth, date)
    }

    override fun onDateSet(view: DatePicker, year: Int, month: Int, date: Int) {
        listener.onSelected(year, month, date)
    }
}

class TimePickerFragment : android.support.v4.app.DialogFragment()
        ,TimePickerDialog.OnTimeSetListener{

    interface OnTimeSelectedListener {
            fun onSelected(hourOfDay: Int, minute: Int)
    }

    private lateinit var listener: OnTimeSelectedListener

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        if(context is TimePickerFragment.OnTimeSelectedListener){
            listener = context
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val c = Calendar.getInstance()
        val hour = c.get(Calendar.HOUR_OF_DAY)
        val minute = c.get(Calendar.MINUTE)
        return TimePickerDialog(context, this, hour, minute, true)
    }

    override fun onTimeSet(view: TimePicker, hourOfDay: Int, minute: Int) {
        listener.onSelected(hourOfDay, minute)
    }
}
