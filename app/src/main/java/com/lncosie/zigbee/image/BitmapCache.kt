package com.lncosie.zigbee.image

import android.content.Context
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import com.lncosie.zigbee.R
import com.lncosie.zigbee.model.User
import java.util.*

class BitmapCache {

    companion object {
        var context: Context? = null
        val resIdx = arrayOf(R.drawable.device, R.drawable.gateway)
        val cache = HashMap<Long, Drawable>()

        fun cacheInit(context: Context) {
            this.context = context
            cache.clear()
        }

        fun getDrawable(user: User): Drawable {
            var drawable = cache.get(user.id)
            if (drawable != null)
                return drawable
            if (user.image != null) {
                drawable = BitmapDrawable(BitmapFactory.decodeByteArray(user.image, 0, user.image?.size!!))
            } else {
                val resource = resIdx.get(user.image_def)
                drawable = context?.resources?.getDrawable(resource)!!
            }
            cache.put(user.id!!, drawable)
            return drawable
        }
    }
}