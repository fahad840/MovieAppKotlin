package company.com.movieappkotlin.ui.session

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import company.com.movieappkotlin.R
import company.com.movieappkotlin.databinding.ActivityLoginBinding
import company.com.movieappkotlin.ui.moviesui.MainActivity
import company.com.movieappkotlin.utils.toast
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein
import org.kodein.di.generic.instance

class LoginActivity : AppCompatActivity(),SessionListener,KodeinAware {
    private lateinit var binding:ActivityLoginBinding
    override val kodein: Kodein by kodein()
    private val sessionViewModelFactory:SessionViewModelFactory by instance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= DataBindingUtil.setContentView(this,R.layout.activity_login)
        val bindingViewModel = ViewModelProvider(this,sessionViewModelFactory).get(SessionViewModel::class.java)
        binding.sessionViewModel=bindingViewModel
        bindingViewModel.sessionListener=this
       if (bindingViewModel.validateUser()) movetoMain()
    }

    override fun onSuccess() {
        movetoMain()

    }
    fun movetoMain(){
        val intent=Intent(this,
            MainActivity::class.java)
        intent.also {
            it.flags=Intent.FLAG_ACTIVITY_CLEAR_TASK
            it.flags=Intent.FLAG_ACTIVITY_NEW_TASK
        }
        startActivity(intent)
    }

    override fun onStarted() {
        print("started")

        toast("Started")
    }

    override fun onFailure(message: String) {
        toast(message)
    }
}
