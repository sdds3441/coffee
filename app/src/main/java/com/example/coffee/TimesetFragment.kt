package com.example.coffee

import android.app.Activity
import android.app.DatePickerDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Switch
import android.widget.Toast
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.LayoutManager
import com.example.coffee.databinding.FragmentTimesetBinding
import java.time.LocalDateTime
import java.util.*

class TimesetFragment : Fragment() {

    lateinit var naviActivity: NaviActivity

    lateinit var listAdapter: ListAdapter

    private var selected_cardview:Int = 0
    private var hour:Int? =1000
    private var minute:Int? = 1000
    private var name:String?=""
    private var year:Int? = 0
    private var month:Int? = 0
    private var date:Int? = 0
    private var ori_time:String?=""
    private var alarm_on:Boolean=true

    val dateAndtime:LocalDateTime=LocalDateTime.now()

    val datas = mutableListOf<ListItem>()
    // val intent=Intent(this.naviActivity,TimeSettingFragment::class.java)

    override fun onAttach(context: Context) {
        super.onAttach(context)

        naviActivity = context as NaviActivity

    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding = FragmentTimesetBinding.inflate(inflater, container, false)


        listAdapter = ListAdapter(naviActivity)
        binding.rvList.adapter = listAdapter

        binding.addAl.setOnClickListener {
            val intent=Intent(context,TimeSettingActivity::class.java)
            startActivityForResult(intent,1)
            selected_cardview=1000
        }

        /*datas.apply {
            add(ListItem(name = "아침", time ="6:23" , date = "5월4일"))

            listAdapter.datas = datas
            listAdapter.notifyDataSetChanged()

        }*/

        val view = inflater.inflate(R.layout.fragment_timeset, container, false)
        //val switch :Switch = view.findViewById(R.id.al_switch)


        listAdapter.setOnItemClickListener(object : ListAdapter.OnItemClickListener {

            override fun onItemClick(v: View, data: ListItem, pos: Int) {
                selected_cardview=pos
                activity?.let {
                    val intent=Intent(context,TimeSettingActivity::class.java)
                    ori_time=datas[selected_cardview].time
                    startActivityForResult(intent,1)
                }

            }

        })
        return binding.root
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == 1) {
            if (resultCode == 2) {



                hour = data?.getIntExtra("sel_hour",1000)
                minute=data?.getIntExtra("sel_minute",1000)
                name=data?.getStringExtra("sel_name")
                year=data?.getIntExtra("sel_year",2023)
                month=data?.getIntExtra("sel_month",1)
                date=data?.getIntExtra("sel_date",1)
                Log.d("시간받아오기", hour.toString())
            }
        }


        if (selected_cardview==1000){
            datas.apply {
                if(year==dateAndtime.year)
                    add(ListItem(name = name.toString(), time = hour.toString()+":"+minute.toString(), date = month.toString()+"월 "+date.toString()+"일"))
               else
                    add(ListItem(name = name.toString(), time = hour.toString()+":"+minute.toString(), date = year.toString()+"년 "+month.toString()+"월 "+date.toString()+"일"))

                listAdapter.datas = datas
                listAdapter.notifyDataSetChanged()

            }
        }
        else{
            if(year==dateAndtime.year)
                datas[selected_cardview] = ListItem(name = name.toString(), time = hour.toString()+":"+minute.toString(), date = month.toString()+"월 "+date.toString()+"일")
            else
                datas[selected_cardview] = ListItem(name = name.toString(), time = hour.toString()+":"+minute.toString(), date =year.toString()+"년 "+month.toString()+"월 "+date.toString()+"일")
        datas.apply {
            listAdapter.datas = datas
            listAdapter.notifyDataSetChanged()
        }

        }
    }
}