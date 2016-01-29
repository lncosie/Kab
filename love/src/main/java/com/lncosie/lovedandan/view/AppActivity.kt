package com.lncosie.lovedandan.view

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
import com.lncosie.lovedandan.R
import com.lncosie.lovedandan.model.ChatInterface

@Layout(R.layout.activity_app)
open class AppActivity: PrjActivity(R.string.app_name,null,null){

    @Inject("chat","x")
    lateinit var fragments:Array<PrjFragment>
    @Bind(R.id.fragment_pager)
    lateinit var pager: ViewPager

    @Bind(R.id.home_button)
    lateinit var home_button: RadioButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        pager.setOffscreenPageLimit(4)
        home_button.isChecked=true
        pager.adapter=Adapter(supportFragmentManager)
        ChatInterface.jpinit(this)
        test()
    }
    fun test(){
        ChatInterface.login("lncosie","lncosie")
    }
    override fun onDestroy() {
        super.onDestroy()
    }
    public fun pageActive(v: View){
        val index=v.tag.toString().toInt()
        pager.currentItem=index
        //caption_text=pager.adapter.getPageTitle(index)
    }
    inner  class Adapter (fm: FragmentManager): FragmentPagerAdapter(fm) {

        override fun getItem(position:Int): Fragment?{
            return fragments.get(position)
        }
        override fun getCount():Int=fragments.size
        override fun getPageTitle(position: Int): CharSequence? {
            val title=when(position){
                0->getString(R.string.stub)
                1->getString(R.string.stub)
                else->null
            }
            return title
        }
    }
}
