package company.com.movieappkotlin.ui.settingui

import android.os.Bundle
import androidx.preference.PreferenceFragmentCompat
import company.com.movieappkotlin.R

class SettingFragment : PreferenceFragmentCompat(){

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.setting_screen, rootKey)

    }

}
