package com.lncosie.lovedandan.view

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.lncosie.kab.Kab
import com.lncosie.kab.Layout
import com.lncosie.lovedandan.R

open class PrjActivity(val title_id: Int, val negative: Int?, val context_action: Int?) : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val layout = javaClass.getAnnotation(Layout::class.java)
        if (layout != null) {
            setContentView(layout.value)
            Kab.bind(this, this)
        }
        this.caption = title_id
        val negative_button = findViewById(R.id.negative_button) as ImageView
        if (negative != null) {
            negative_button.setImageResource(negative)
        } else
            negative_button.visibility = View.INVISIBLE
        val tool_button = findViewById(R.id.context_action_button) as ImageView
        if (context_action != null) {
            tool_button.setImageResource(context_action)
        } else {
            tool_button.visibility = View.INVISIBLE
        }
    }

    var caption: Int = R.string.stub
        get() = field
        set(value: Int) {
            val title = findViewById(R.id.title_textview) as TextView
            title.setText(value)
            field = value
        }
    var caption_text: CharSequence = ""
        get() = field
        set(value: CharSequence) {
            val title = findViewById(R.id.title_textview) as TextView
            title.setText(value)
            field = value
        }
    override fun onDestroy() {
        super.onDestroy()
        Kab.unbind(this)
    }

    public open fun negative(v: View) {
        this.finish()
    }

    public open fun context_action(v: View) {

    }
}
