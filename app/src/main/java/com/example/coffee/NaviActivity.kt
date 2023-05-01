package com.example.coffee

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.example.coffee.databinding.ActivityNaviBinding

private const val TAG_ETC = "Etcfragment"
private const val TAG_HOME = "Homefragment"
private const val TAG_SETTING = "Settingfragment"
private const val TAG_Timeset = "Timesetfragment"

class NaviActivity : AppCompatActivity() {

    private lateinit var binding : ActivityNaviBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNaviBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setFragment(TAG_HOME, HomeFragment())

        binding.navigationView.setOnItemSelectedListener { item ->
            when(item.itemId) {
                R.id.etcFragment -> setFragment(TAG_ETC, EtcFragment())
                R.id.homeFragment -> setFragment(TAG_HOME, HomeFragment())
                R.id.time_setFragment -> setFragment(TAG_Timeset, TimesetFragment())
                R.id.settingFragment-> setFragment(TAG_SETTING, SettingFragment())
            }
            true
        }
    }

    private fun setFragment(tag: String, fragment: Fragment) {
        val manager: FragmentManager = supportFragmentManager
        val fragTransaction = manager.beginTransaction()

        if (manager.findFragmentByTag(tag) == null){
            fragTransaction.add(R.id.mainFrameLayout, fragment, tag)
        }

        val etc = manager.findFragmentByTag(TAG_ETC)
        val home = manager.findFragmentByTag(TAG_HOME)
        val setting = manager.findFragmentByTag(TAG_SETTING)
        val timeset = manager.findFragmentByTag(TAG_Timeset)

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

        fragTransaction.commitAllowingStateLoss()
    }
}