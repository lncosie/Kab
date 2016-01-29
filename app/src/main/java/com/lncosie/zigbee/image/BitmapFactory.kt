package com.lncosie.zigbee.image

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import java.io.FileNotFoundException

class BitmapFactory {

    fun cropBitmap(context: Context, uri: Uri, pix: Int): Bitmap? {
        try {
            val crs = context.contentResolver
            val options = BitmapFactory.Options()
            options.inJustDecodeBounds = true
            BitmapFactory.decodeStream(crs.openInputStream(uri), null, options)
            options.inSampleSize = calculateInSampleSize(options, pix, pix)
            options.inJustDecodeBounds = false
            return BitmapFactory.decodeStream(crs.openInputStream(uri), null, options)
        } catch (e: FileNotFoundException) {
            e.printStackTrace()
        }
        return null
    }

    private fun calculateInSampleSize(options: BitmapFactory.Options,
                                      reqWidth: Int, reqHeight: Int): Int {
        val height = options.outHeight
        val width = options.outWidth
        var inSampleSize = 1
        if (height > reqHeight || width > reqWidth) {
            val heightRatio = Math.round(height.toFloat() / reqHeight.toFloat())
            val widthRatio = Math.round(width.toFloat() / reqWidth.toFloat())
            inSampleSize = if (heightRatio < widthRatio) widthRatio else heightRatio
        }
        return inSampleSize
    }
}
