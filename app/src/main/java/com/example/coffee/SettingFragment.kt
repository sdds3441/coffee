package com.example.coffee

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager.VERTICAL
import com.example.coffee.databinding.FragmentSettingBinding
import com.example.coffee.databinding.FragmentTimesetBinding
import com.example.coffee.databinding.ListItemBinding
import com.example.coffee.databinding.SettingItemBinding

class SettingFragment : Fragment() {


    lateinit var naviActivity: NaviActivity

    lateinit var settingAdapter: SettingAdapter

    private var selected_set_val: Int = 0

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
        val binding = FragmentSettingBinding.inflate(inflater, container, false)
        val binding2 = SettingItemBinding.inflate(inflater, container, false)

        val list = ArrayList<SettingItem>()
        list.add(SettingItem("유저 정보"))
        list.add(SettingItem("권한 설정"))
        list.add(SettingItem("어플리케이션 정보"))
        list.add(SettingItem("QnA"))
        list.add(SettingItem("고객센터"))

        settingAdapter=SettingAdapter(list)
        settingAdapter.notifyDataSetChanged()

        binding.setList.adapter=settingAdapter

        binding.setList.layoutManager=LinearLayoutManager(naviActivity,LinearLayoutManager.VERTICAL,false)

        val decoration=DividerItemDecoration(naviActivity,VERTICAL)

        binding.setList.addItemDecoration(decoration)



        return binding.root

    }
}