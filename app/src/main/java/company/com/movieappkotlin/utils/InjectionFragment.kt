package company.com.movieappkotlin.utils

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import company.com.movieappkotlin.BuildConfig
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.KodeinTrigger
import org.kodein.di.generic.kcontext
import org.kodein.di.android.x.kodein
abstract class InjectionFragment : Fragment(), KodeinAware {
    final override val kodein: Kodein by kodein()
    final override val kodeinContext = kcontext<Fragment>(this)
    final override val kodeinTrigger: KodeinTrigger? // See description in InjectionActivity
        get() = if (BuildConfig.DEBUG) KodeinTrigger() else super.kodeinTrigger

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        kodeinTrigger?.trigger()
    }
}