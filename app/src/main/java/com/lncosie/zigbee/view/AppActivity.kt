package com.lncosie.zigbee.view

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.support.v4.view.ViewPager
import android.view.View
import android.widget.RadioButton
import android.widget.TextView
import com.lncosie.kab.Bind
import com.lncosie.kab.Inject
import com.lncosie.kab.Layout
import com.lncosie.zigbee.R


@Layout(R.layout.activity_app)
open class AppActivity : PrjActivity(R.string.home, null, null) {

    @Inject("home", "user", "history", "setting")
    lateinit var fragments: Array<PrjFragment>
    @Bind(R.id.fragment_pager)
    lateinit var pager: ViewPager

    @Bind(R.id.title_textview)
    lateinit var title: TextView

    @Bind(R.id.home_button)
    lateinit var home_button: RadioButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        pager.setOffscreenPageLimit(4)
        home_button.isChecked = true
        pager.adapter = Adapter(supportFragmentManager)
    }

    override fun onDestroy() {
        super.onDestroy()
    }

    public fun pageActive(v: View) {
        val index = v.tag.toString().toInt()
        pager.currentItem = index
        title.text = pager.adapter.getPageTitle(index)
    }

    inner class Adapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {

        override fun getItem(position: Int): Fragment? {
            return fragments.get(position)
        }

        override fun getCount(): Int = fragments.size
        override fun getPageTitle(position: Int): CharSequence? {
            val title = when (position) {
                0 -> getString(R.string.home)
                1 -> getString(R.string.users)
                2 -> getString(R.string.history)
                3 -> getString(R.string.setting)
                else -> null
            }
            return title
        }
    }
}
