package com.example.coffee

import android.app.DatePickerDialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.*
import androidx.core.widget.addTextChangedListener
import androidx.core.widget.doOnTextChanged
import com.example.coffee.databinding.ActivityTimesettingBinding
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import com.google.android.material.datepicker.CalendarConstraints
import com.google.android.material.datepicker.DateValidatorPointForward
import com.google.android.material.datepicker.MaterialDatePicker
import java.io.BufferedReader
import java.io.InputStream
import java.io.InputStreamReader
import java.io.OutputStream
import java.net.HttpURLConnection
import java.net.URL
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

class TimeSettingActivity : AppCompatActivity() {

    private val IP_ADDRESS = "172.30.40.23"
    //private val IP_ADDRESS = "10.0.2.2"
    private val TAG = "phptest"
    private lateinit var binding : ActivityTimesettingBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_timesetting)

        val alName: EditText = findViewById(R.id.al_name)
        val timePicker: TimePicker = findViewById(R.id.timePicker)
        val dateButton: Button = findViewById(R.id.date_sel)
        val endButton: Button = findViewById(R.id.sel_complete)
        val test:TextView=findViewById(R.id.date_test)
        val DOW:ChipGroup=findViewById(R.id.DOW)
        val coffee_sw:Switch=findViewById(R.id.Coffee_sw)
        val alarm_sw:Switch=findViewById(R.id.Sound_sw)

        val Now_daytime: LocalDateTime = LocalDateTime.now()
        val yearformatter = DateTimeFormatter.ofPattern("YYYY")
        val monthformatter = DateTimeFormatter.ofPattern("MM")
        val dateformatter = DateTimeFormatter.ofPattern("dd")
        val hourformatter = DateTimeFormatter.ofPattern("HH")
        val minuteformatter = DateTimeFormatter.ofPattern("mm")

        val Now_year = Now_daytime.format(yearformatter)
        val Now_month = Now_daytime.format(monthformatter)
        val Now_date = Now_daytime.format(dateformatter)
        val Now_hour = Now_daytime.format(hourformatter)
        val Now_minute = Now_daytime.format(minuteformatter)

        var sel_hour=intent.getIntExtra("ori_hour",Now_hour.toInt())
        var sel_minute=intent.getIntExtra("ori_minute",Now_minute.toInt())
        var selname=intent.getStringExtra("ori_name")
        var sel_year=intent.getIntExtra("ori_year",Now_year.toInt())
        var sel_month=intent.getIntExtra("ori_month",Now_month.toInt())
        var sel_date=intent.getIntExtra("ori_date",Now_date.toInt())
        var pos=intent.getIntExtra("pos",0)
        var sound:Boolean=true
        var coffee:Boolean=true
        var sum:Boolean=true
        var sel_dow= mutableListOf(0,0,0,0,0,0,0)
        var sound_int:Int=1
        var coffee_int:Int=1

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


        DOW.setOnCheckedStateChangeListener { group, checkedIds ->
            Log.d("test", checkedIds.toString())
            if (checkedIds.contains(2131230741))
                sel_dow[0]=1
            else
                sel_dow[0]=0
            if (checkedIds.contains(2131230730))
                sel_dow[1]=1
            else
                sel_dow[1]=0
            if (checkedIds.contains(2131230745))
                sel_dow[2]=1
            else
                sel_dow[2]=0
            if (checkedIds.contains(2131230746))
                sel_dow[3]=1
            else
                sel_dow[3]=0
            if (checkedIds.contains(2131230744))
                sel_dow[4]=1
            else
                sel_dow[4]=0
            if (checkedIds.contains(2131230728))
                sel_dow[5]=1
            else
                sel_dow[5]=0
            if (checkedIds.contains(2131230738))
                sel_dow[6]=1
            else
                sel_dow[6]=0

            Log.d("요일",sel_dow.toString())




        }

        coffee_sw.setOnCheckedChangeListener{p0, isChecked->
            coffee = isChecked
        }

        alarm_sw.setOnCheckedChangeListener{p0, isChecked->
            sound = isChecked
        }


        endButton.setOnClickListener {
            val bundle=Bundle()
            if(alName.text.toString()!="")
                selname=alName.text.toString()
            intent=Intent()
            intent.putExtra("sel_hour",sel_hour)
            intent.putExtra("sel_minute",sel_minute)
            intent.putExtra("sel_name",selname)
            intent.putExtra("sel_year",sel_year)
            intent.putExtra("sel_month",sel_month)
            intent.putExtra("sel_date",sel_date)

            intent.putExtra("coffee_sw",coffee)
            intent.putExtra("alarm_sw",sound)

            intent.putExtra("dow",sel_dow.toString())


            val timesetFragment=TimesetFragment()
            timesetFragment.arguments=bundle
            setResult(2,intent)

            finish()
            val n = 2
            val Pos : String=(pos.toString())
            val Day : String = (sel_year.toString()+"."+sel_month.toString().padStart(n, '0')+"."+sel_date.toString().padStart(n, '0'))
            val Time: String = (sel_hour.toString().padStart(n, '0')+"."+sel_minute.toString().padStart(n, '0'))
            val Dow:String = (sel_dow.toString())
            if (sound==true)
                sound_int=1
            else
                sound_int=0
            val Alarm:String = (sound_int.toString())
            if (coffee==true)
                coffee_int=1
            else
                coffee_int=0
            val Coffee:String = (coffee_int.toString())
            val task = InsertData()
            //task.execute("http://$IP_ADDRESS/input.php", Day, Time)
            task.execute("http://$IP_ADDRESS/insertTest.php",Pos, Day, Time,Dow,Alarm,Coffee)

        }
    }
    private class InsertData : AsyncTask<String, Void, String>() {


        override fun doInBackground(vararg params: String?): String {


            val serverURL: String? = params[0]
            val Pos : String? = params[1]
            val Day: String? = params[2]
            val Time: String? = params[3]
            val Dow:String?=params[4]
            val Alarm:String?=params[5]
            val Coffee:String?=params[6]
            Log.d("coffeesw",Coffee.toString())
            Log.d("dow",Dow.toString())

            val postParameters: String = "Pos=$Pos&Day=$Day&Time=$Time&Dow=$Dow" +
                    "&Alarm=$Alarm&Coffee=$Coffee"
            Log.d("pos",postParameters.toString())
            try {
                val url = URL(serverURL)
                val httpURLConnection: HttpURLConnection = url.openConnection() as HttpURLConnection


                httpURLConnection.readTimeout = 5000
                httpURLConnection.connectTimeout = 5000
                httpURLConnection.requestMethod = "POST"
                httpURLConnection.connect()


                val outputStream: OutputStream = httpURLConnection.outputStream
                outputStream.write(postParameters.toByteArray(charset("UTF-8")))
                outputStream.flush()
                outputStream.close()

                val responseStatusCode: Int = httpURLConnection.responseCode


                val inputStream: InputStream
                inputStream = if (responseStatusCode == HttpURLConnection.HTTP_OK) {
                    httpURLConnection.inputStream
                } else {
                    httpURLConnection.errorStream
                }

                val inputStreamReader = InputStreamReader(inputStream, "UTF-8")
                val bufferedReader = BufferedReader(inputStreamReader)

                val sb = StringBuilder()
                var line: String? = null

                while (bufferedReader.readLine().also({ line = it }) != null) {
                    sb.append(line)
                }

                bufferedReader.close();
                Log.d("이거뭐임3",sb.toString())
                return sb.toString()

            } catch (e: Exception) {
                Log.d("안듀ㅙㅁ",e.message.toString())
                return "Error" + e.message
            }

        }

    }
}