package com.example.agecalculator

import android.app.DatePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnDatePicker.setOnClickListener { view ->
        clickDatePicker(view)
        }
    }

    fun clickDatePicker(view: View) {
        val myCalendar = Calendar.getInstance()
        //Getting year, month & day from Calendar objects
        val year = myCalendar.get(Calendar.YEAR)
        val month = myCalendar.get(Calendar.MONTH)
        val day = myCalendar.get(Calendar.DAY_OF_MONTH)
        val dpd = DatePickerDialog(
            this,
            DatePickerDialog.OnDateSetListener { view, selectedYear, selectedMonth, selectedDayOfMonth ->
                val selectedDate = "$selectedDayOfMonth/${selectedMonth + 1}/$selectedYear"
                tvSelectedDate.setText(selectedDate)
                /**
                 * Here we have taken an instance of Date Formatter as it will format our
                 * selected date in the format which we pass it as an parameter and Locale.
                 * Here I have passed the format as dd/MM/yyyy.
                 */
                val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH)
                /*The formatter will parse the selected date in to Date object
                so we can simply get date in to milliseconds.*/
                val theDate = sdf.parse(selectedDate)
                /** Here we have get the time in milliSeconds from Date object
                 * And as we know the formula as milliseconds can be converted to second by dividing it by 1000.
                 * And the seconds can be converted to minutes by dividing it by 60.
                 * So now in the selected date into minutes.
                 */
                val selectedDateInMinutes = theDate!!.time / 60000
                // Here we have parsed the current date with the Date Formatter which is used above.
                val currentDate = sdf.parse(sdf.format(System.currentTimeMillis()))
                // Current date in to minutes.
                val currentDateInMinutes = currentDate!!.time / 60000
                /**
                 * Now to get the difference into minutes.
                 * We will subtract the selectedDateInMinutes from currentDateInMinutes.
                 * Which will provide the difference in minutes.
                 */
                val differenceInMinutes = currentDateInMinutes - selectedDateInMinutes
                // Set the difference in minutes to TextView to show the user.
                tvSelectedDateInMinutes.setText(differenceInMinutes.toString())
            },
            year,
            month,
            day
        )
        /**
         * Sets the maximal date supported by this in
         * milliseconds since January 1, 1970 00:00:00 in time zone.
         */
        // 86400000 is milliseconds of 24 Hours. Which is used to restrict the user to select today and future day.
        dpd.datePicker.setMaxDate(Date().time - 86400000)
        dpd.show() // It is used to show the datePicker Dialog.
    }

}