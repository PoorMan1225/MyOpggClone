package com.rjhwork.mycompany.opggcloneapp.extension

import android.graphics.*
import android.os.Build


fun Bitmap.getCroppedBitmap(): Bitmap {
    val squareBitmapWidth = this.width.coerceAtMost(this.height)
    val output = Bitmap.createBitmap(squareBitmapWidth, squareBitmapWidth, Bitmap.Config.ARGB_8888)
    val canvas = Canvas(output) // bitmap 으로 캔버스 구성.

    val paint = Paint().apply {
        isAntiAlias = true
    }
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
        paint.setColor(0xff424242)
    }

    val rect = Rect(0, 0, squareBitmapWidth, squareBitmapWidth)
    canvas.drawARGB(0, 0, 0, 0)
    canvas.drawCircle(
        squareBitmapWidth / 2f,
        squareBitmapWidth / 2f,
        squareBitmapWidth / 2f,
        paint
    )
    paint.xfermode = PorterDuffXfermode(PorterDuff.Mode.SRC_IN)
    canvas.drawBitmap(this, rect, rect, paint) // 원에다 비트맵을 덮어 씌우는 형식
    return output
}