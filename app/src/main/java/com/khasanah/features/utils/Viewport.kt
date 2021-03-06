package com.khasanah.features.utils

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.ViewGroup

class Viewport : ViewGroup {
    constructor(context: Context?) : super(context)

    @JvmOverloads
    constructor(context: Context?, attrs: AttributeSet?, defStyle: Int = 0) : super(context, attrs, defStyle)

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        setMeasuredDimension(widthMeasureSpec, heightMeasureSpec)
    }

    public override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {}
    override fun shouldDelayChildPressedState(): Boolean {
        return false
    }

    override fun dispatchDraw(canvas: Canvas) {
        super.dispatchDraw(canvas)
        val viewportMargin = 2
        val viewportCornerRadius = 0
        val eraser = Paint()
        eraser.isAntiAlias = true
        eraser.xfermode = PorterDuffXfermode(PorterDuff.Mode.CLEAR)
        val width = width.toFloat() - viewportMargin
        val height = height.toFloat() - viewportMargin
//        val height = width * 0.64.toFloat()
        val rect = RectF(viewportMargin.toFloat(), viewportMargin.toFloat(), width, height)
        val frame = RectF(viewportMargin.toFloat() - 2, viewportMargin.toFloat() - 2, width + 2, height + 2)
        val path = Path()
        val stroke = Paint()
        stroke.isAntiAlias = true
        stroke.strokeWidth = 4f
        stroke.color = Color.WHITE
        stroke.style = Paint.Style.STROKE
        path.addRoundRect(frame, viewportCornerRadius.toFloat(), viewportCornerRadius.toFloat(), Path.Direction.CW)
        canvas.drawPath(path, stroke)
        canvas.drawRoundRect(rect, viewportCornerRadius.toFloat(), viewportCornerRadius.toFloat(), eraser)
    }
}