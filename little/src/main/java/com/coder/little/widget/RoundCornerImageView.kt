package com.coder.little.widget

import android.content.Context
import android.graphics.Canvas
import android.graphics.Path
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatImageView
import com.coder.little.R

/**
 * Created by Zero on 2021/11/18.
 */
class RoundCornerImageView(context: Context, attrs: AttributeSet?, defStyleAttr: Int) :
    AppCompatImageView(context, attrs, defStyleAttr) {
    private var width = 0f
    private var height = 0f
    private val defaultRadius = 0
    private var radius = 0
    private var leftTopRadius = 0
    private var rightTopRadius = 0
    private var rightBottomRadius = 0
    private var leftBottomRadius = 0

    constructor(context: Context) : this(context, null) {
        init(context, null)
    }

    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0) {
        init(context, attrs)
    }

    private fun init(context: Context, attrs: AttributeSet?) {
        // 读取配置
        val array = context.obtainStyledAttributes(attrs, R.styleable.RoundCornerImageView)
        radius =
            array.getDimensionPixelOffset(R.styleable.RoundCornerImageView_radius, defaultRadius)
        leftTopRadius = array.getDimensionPixelOffset(
            R.styleable.RoundCornerImageView_left_top_radius,
            defaultRadius
        )
        rightTopRadius = array.getDimensionPixelOffset(
            R.styleable.RoundCornerImageView_right_top_radius,
            defaultRadius
        )
        rightBottomRadius = array.getDimensionPixelOffset(
            R.styleable.RoundCornerImageView_right_bottom_radius,
            defaultRadius
        )
        leftBottomRadius = array.getDimensionPixelOffset(
            R.styleable.RoundCornerImageView_left_bottom_radius,
            defaultRadius
        )

        //如果四个角的值没有设置，那么就使用通用的radius的值。
        if (defaultRadius == leftTopRadius) {
            leftTopRadius = radius
        }
        if (defaultRadius == rightTopRadius) {
            rightTopRadius = radius
        }
        if (defaultRadius == rightBottomRadius) {
            rightBottomRadius = radius
        }
        if (defaultRadius == leftBottomRadius) {
            leftBottomRadius = radius
        }
        array.recycle()
    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)
        width = getWidth().toFloat()
        height = getHeight().toFloat()
    }

    private val path = Path()

    override fun onDraw(canvas: Canvas) {
        //这里做下判断，只有图片的宽高大于设置的圆角距离的时候才进行裁剪
        val maxLeft = leftTopRadius.coerceAtLeast(leftBottomRadius)
        val maxRight = rightTopRadius.coerceAtLeast(rightBottomRadius)
        val minWidth = maxLeft + maxRight
        val maxTop = leftTopRadius.coerceAtLeast(rightTopRadius)
        val maxBottom = leftBottomRadius.coerceAtLeast(rightBottomRadius)
        val minHeight = maxTop + maxBottom
        if (width >= minWidth && height > minHeight) {
            //四个角：右上，右下，左下，左上
            path.moveTo(leftTopRadius.toFloat(), 0f)
            path.lineTo(width - rightTopRadius, 0f)
            path.quadTo(width, 0f, width, rightTopRadius.toFloat())
            path.lineTo(width, height - rightBottomRadius)
            path.quadTo(width, height, width - rightBottomRadius, height)
            path.lineTo(leftBottomRadius.toFloat(), height)
            path.quadTo(0f, height, 0f, height - leftBottomRadius)
            path.lineTo(0f, leftTopRadius.toFloat())
            path.quadTo(0f, 0f, leftTopRadius.toFloat(), 0f)
            canvas.clipPath(path)
        }
        super.onDraw(canvas)
    }

    init {
        init(context, attrs)
    }
}