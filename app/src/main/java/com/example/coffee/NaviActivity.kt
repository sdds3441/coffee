package com.example.coffee

import android.graphics.Insets.add
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.navigation.NavController
import androidx.recyclerview.widget.RecyclerView
import com.example.coffee.databinding.ActivityNaviBinding
import com.example.coffee.databinding.FragmentTimesetBinding

private const val TAG_ETC = "Etcfragment"
private const val TAG_HOME = "Homefragment"
private const val TAG_SETTING = "Settingfragment"
private const val TAG_Timeset = "Timesetfragment"
private const val TAG_Timesetting = "TimeSettingfragment"

class NaviActivity : AppCompatActivity() {

    lateinit var listAdapter:ListAdapter
    private lateinit var binding : ActivityNaviBinding
    private lateinit var secbinding : FragmentTimesetBinding
    val datas = mutableListOf<ListItem>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNaviBinding.inflate(layoutInflater)
        secbinding= FragmentTimesetBinding.inflate(layoutInflater)

        var select=0

       setContentView(binding.root)
        setFragment(TAG_HOME, HomeFragment())

        binding.navigationView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.etcFragment -> setFragment(TAG_ETC, EtcFragment())
                R.id.homeFragment -> setFragment(TAG_HOME, HomeFragment())
                R.id.time_setFragment -> setFragment(TAG_Timeset, TimesetFragment())
                R.id.settingFragment -> setFragment(TAG_SETTING, SettingFragment())

            }

            true


        }
    }

    fun setFragment(tag: String, fragment: Fragment) {
        val manager: FragmentManager = supportFragmentManager
        val fragTransaction = manager.beginTransaction()

        if (manager.findFragmentByTag(tag) == null){
            fragTransaction.add(R.id.mainFrameLayout, fragment, tag)
        }

        val etc = manager.findFragmentByTag(TAG_ETC)
        val home = manager.findFragmentByTag(TAG_HOME)
        val setting = manager.findFragmentByTag(TAG_SETTING)
        val timeset = manager.findFragmentByTag(TAG_Timeset)
        val timeSetting=manager.findFragmentByTag(TAG_Timesetting)

        if (etc != null){
            fragTransaction.hide(etc)
        }

        if (home != null){
            fragTransaction.hide(home)
        }

        if (setting != null) {
            fragTransaction.hide(setting)
        }
        if (timeset != null) {
            fragTransaction.hide(timeset)
        }

        if (timeSetting != null) {
            fragTransaction.hide(timeSetting)
        }

        if (tag == TAG_HOME) {
            if (home!=null){
                fragTransaction.show(home)
            }
        }
        else if (tag == TAG_Timeset) {
            if (timeset != null) {
                fragTransaction.show(timeset)
            }
        }

        else if (tag == TAG_ETC){
            if (etc != null){
                fragTransaction.show(etc)
            }
        }

        else if (tag == TAG_SETTING){
            if (setting != null){
                fragTransaction.show(setting)
            }
        }
        else if (tag == TAG_Timesetting){
            if (timeSetting != null){
                fragTransaction.show(timeSetting)
            }
        }

        fragTransaction.commitAllowingStateLoss()
    }



}