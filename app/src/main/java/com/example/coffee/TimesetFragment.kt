package com.example.coffee

import android.app.DatePickerDialog
import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView.LayoutManager
import com.example.coffee.databinding.FragmentTimesetBinding
import java.util.*

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"


class TimesetFragment : Fragment() {

    lateinit var naviActivity : NaviActivity

    lateinit var listAdapter:ListAdapter
    val datas= mutableListOf<ListItem>()

    override fun onAttach(context: Context) {
        super.onAttach(context)

        naviActivity=context as NaviActivity

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

        val binding=FragmentTimesetBinding.inflate(inflater,container,false)

        listAdapter=ListAdapter(naviActivity)
        binding.rvList.adapter=listAdapter

        datas.apply {
            add(ListItem(name="name", time = 11,date=2023))
            add(ListItem(name="na1me", time = 121,date=2022))
            add(ListItem(name="na2me", time = 131,date=2021))

            listAdapter.datas=datas
            listAdapter.notifyDataSetChanged()

        }
        Log.d("data",datas.toString())
        /*listAdapter.setItemClickListener(object : ListAdapter.OnItemClickListener){

            VIew->
            Toast.makeText(naviActivity,"카드뷰 클릭됨",Toast.LENGTH_SHORT).show()

            val cal = Calendar.getInstance()
            val dateSetListener=DatePickerDialog.OnDateSetListener{view,year,month,dayOfMonth->
                dateString="${year}년 ${month}월 ${dayOfMonth}일"
                timeset.text="날짜/시간 : "+dateString+" / "+timeString
            }
            DatePickerDialog(naviActivity,dateSetListener,cal.get(Calendar.YEAR),cal.get(Calendar.MONTH),cal.get(Calendar.DAY_OF_MONTH)).show()

        }*/

        return binding.root
    }

}