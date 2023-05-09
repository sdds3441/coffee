package com.example.coffee

import android.app.DatePickerDialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.*
import androidx.core.widget.addTextChangedListener
import androidx.core.widget.doOnTextChanged
import com.google.android.material.chip.ChipGroup
import com.google.android.material.datepicker.CalendarConstraints
import com.google.android.material.datepicker.DateValidatorPointForward
import com.google.android.material.datepicker.MaterialDatePicker
import java.util.*

class TimeSettingActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_timesetting)

        val alName: EditText = findViewById(R.id.al_name)
        val timePicker: TimePicker = findViewById(R.id.timePicker)
        val dateButton: Button = findViewById(R.id.date_sel)
        val endButton: Button = findViewById(R.id.sel_complete)
        val test:TextView=findViewById(R.id.date_test)
        val DOW:ChipGroup=findViewById(R.id.DOW)

        var sel_hour: Int = 0
        var sel_minute: Int = 0
        var sel_name:String=""
        var sel_year:Int = 0
        var sel_month:Int = 0
        var sel_date:Int = 0
        var sound:Boolean
        var coffee:Boolean
        var sel_dow= mutableListOf(0,0,0,0,0,0,0)

        val sharedPreference = getSharedPreferences("CreateProfile", Context.MODE_PRIVATE)
        val editor: SharedPreferences.Editor = sharedPreference.edit()

        timePicker.setOnTimeChangedListener { timePicker, hourOfDay, minute ->


            sel_hour = hourOfDay
            sel_minute = minute
        }


        dateButton.setOnClickListener {
//calendar Constraint Builder 선택할수있는 날짜 구간설정
            val calendarConstraintBuilder = CalendarConstraints.Builder()
            //오늘 이후만 선택가능하게 하는 코드
            calendarConstraintBuilder.setValidator(DateValidatorPointForward.now())
            //오늘 이전만 선택가능하게 하는 코드
            //calendarConstraintBuilder.setValidator(DateValidatorPointBackward.now())


            val builder = MaterialDatePicker.Builder.datePicker()
                .setTitleText("Set Dieday")
                .setSelection(MaterialDatePicker.todayInUtcMilliseconds())
                .setCalendarConstraints(calendarConstraintBuilder.build());

            val datePicker = builder.build()

            datePicker.addOnPositiveButtonClickListener {
                val calendar = Calendar.getInstance()
                calendar.time = Date(it)
                val calendarMilli = calendar.timeInMillis
                test.text = "${calendar.get(Calendar.MONTH) + 1}/${calendar.get(Calendar.DAY_OF_MONTH)}/${calendar.get(Calendar.YEAR)}"
                sel_year=calendar.get(Calendar.YEAR)
                sel_month=calendar.get(Calendar.MONTH)+1
                sel_date=calendar.get(Calendar.DAY_OF_MONTH)
                //sharedPreference
                editor.putLong("Die_Millis", calendarMilli)
                editor.apply()
                Log.d("Die_Millis", "$calendarMilli")

            }

            datePicker.show(supportFragmentManager,datePicker.toString())
        }

        /*DOW.setOnCheckedStateChangeListener { group, checkedId ->
            Log.d("test", "Click: $checkedId")
            when(checkedId){
                ->{d}
            }
        }*/


        endButton.setOnClickListener {
            val bundle=Bundle()
            sel_name=alName.text.toString()
            intent=Intent()
            intent.putExtra("sel_hour",sel_hour)
            intent.putExtra("sel_minute",sel_minute)
            intent.putExtra("sel_name",sel_name)
            intent.putExtra("sel_year",sel_year)
            intent.putExtra("sel_month",sel_month)
            intent.putExtra("sel_date",sel_date)


            Log.d("시간넣은거",sel_name)

            val timesetFragment=TimesetFragment()
            timesetFragment.arguments=bundle
            setResult(2,intent)

            finish()
        }
    }
    /*private fun showDatePicker() {
        val cal = Calendar.getInstance()
        DatePickerDialog(this, DatePickerDialog.OnDateSetListener { datePicker, y, m, d->
            Toast.makeText(this, "$y-$m-$d", Toast.LENGTH_SHORT).show()
        }, cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DATE)).show()
    }
*/
}