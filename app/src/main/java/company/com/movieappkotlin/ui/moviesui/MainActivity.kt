package company.com.movieappkotlin.ui.moviesui

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView
import company.com.movieappkotlin.R
import company.com.movieappkotlin.utils.toast


class MainActivity : AppCompatActivity() {

    private var dl: DrawerLayout? = null
    private var t: ActionBarDrawerToggle? = null
    private var nv: NavigationView? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        dl=findViewById(R.id.activity_drawer)
        t= ActionBarDrawerToggle(this,dl,R.string.Open,R.string.Close)
        dl!!.addDrawerListener(t!!)
        t!!.syncState()
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        nv=findViewById(R.id.navigation_view)

        nv!!.setNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.myProfile -> {
                    // handle click
                    true
                }
                else -> false
            }
        }



    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (t!!.onOptionsItemSelected(item)){
            return true
        }
        return super.onOptionsItemSelected(item)
    }


}
