package com.example.caffe

import android.content.Intent
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import android.util.Log
import android.widget.Toast
import androidx.core.view.size
import com.example.caffe.activities.MenuActivity
import com.example.caffe.activities.OrdersHistoryActivity
import com.example.caffe.activities.ShoppingCartActivity
import kotlinx.android.synthetic.main.bottom_navigation_view.*



abstract class BottomNavigationMenuLogic(private val navNumber: Int) : AppCompatActivity() {
    private val TAG = "BaseActivity"
    private var exit = false
    fun setUpBottomNavigation(){
       bottom_navigation_view.setTextVisibility(false)
       bottom_navigation_view.enableAnimation(false)
       bottom_navigation_view.enableItemShiftingMode(false)
       bottom_navigation_view.enableShiftingMode(false)
       bottom_navigation_view.setIconSize(30f,30f)
       for(i in 0 until bottom_navigation_view.size){
           bottom_navigation_view.setIconTintList(i,null)
       }
       bottom_navigation_view.setOnNavigationItemSelectedListener {
           val nextActivity =
               when (it.itemId) {
                   R.id.nav_item_menu -> MenuActivity::class.java
                   R.id.nav_item_shopping_cart -> ShoppingCartActivity::class.java
                   R.id.nav_item_profile -> OrdersHistoryActivity::class.java
                   else -> {
                       Log.e(TAG, "onCreate: unknown  nav item clicked $it")
                       null
                   }
               }
           if (nextActivity != null) {
               val intent = Intent(this, nextActivity)
               intent.flags = Intent.FLAG_ACTIVITY_NO_ANIMATION
               startActivity(intent)
               overridePendingTransition(0,0)
               true
           } else {
               false
           }
       }
           bottom_navigation_view.menu.getItem(navNumber).isChecked = true
   }
    override fun onBackPressed() {
        if (exit) {
            finish() // finish activity
        } else {
            Toast.makeText(
                this, "Press Back again to Exit.",
                Toast.LENGTH_SHORT
            ).show()
            exit = true
            Handler().postDelayed(Runnable { exit = false }, 3 * 1000)
        }
    }
}
