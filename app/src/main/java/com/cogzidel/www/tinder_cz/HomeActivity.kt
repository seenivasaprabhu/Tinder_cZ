package com.cogzidel.www.tinder_cz

import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_home.*

class HomeActivity : AppCompatActivity() {

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_matches -> {

                startFragmentTransaction(MatchesFragment())
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_seen -> {

                startFragmentTransaction(SeenFragment())
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_me-> {

                startFragmentTransaction(MeFragment())
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        startFragmentTransaction(MeFragment())

        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)

        // This method will take a fragment as argument and add/replace
        // that fragment to the activity
//        logout.setOnClickListener(){
//            click()
//        }
//
//
//
    }
//
//    private fun click() {
//        val i = Intent(this@HomeActivity,LoginActivity::class.java)
//        startActivity(i)
//    }

    private fun startFragmentTransaction(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
                .replace(R.id.container, fragment)
                .commit()
    }
}
