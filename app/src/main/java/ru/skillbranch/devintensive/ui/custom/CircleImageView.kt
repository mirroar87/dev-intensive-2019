package ru.skillbranch.devintensive.ui.custom

import android.content.Context
import android.graphics.*
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.widget.ImageView
import androidx.annotation.ColorRes
import androidx.annotation.Dimension
import androidx.core.graphics.drawable.toBitmap
import ru.skillbranch.devintensive.R

class CircleImageView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ImageView(context, attrs, defStyleAttr) {

    companion object {
        private const val DFAULT_CV_BORDERCOLOR = Color.WHITE
        private const val DEFAULT_CV_BORDERWIDTH = 2
    }

    private var cv_borderColor = DFAULT_CV_BORDERCOLOR
    private var cv_borderWidth = DEFAULT_CV_BORDERWIDTH * resources.displayMetrics.density
    private lateinit var avatar_img : Bitmap

    init {
        if (attrs != null) {
            val a = context.obtainStyledAttributes(attrs, R.styleable.CircleImageView)
            cv_borderColor = a.getColor(R.styleable.CircleImageView_cv_borderColor, DFAULT_CV_BORDERCOLOR)
            cv_borderWidth = a.getDimension(R.styleable.CircleImageView_cv_borderWidth, cv_borderWidth)
            a.recycle()

            scaleType = ScaleType.CENTER_CROP
            avatar_img = drawable.toBitmap()

        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        val size = if (widthMeasureSpec > heightMeasureSpec) heightMeasureSpec else widthMeasureSpec
        setMeasuredDimension(size, size)
    }

    override fun onDraw(canvas: Canvas?) {
        val centre = width/2f
        val path = Path().apply {
            addCircle(centre, centre, centre, Path.Direction.CCW)
        }
        canvas!!.clipPath(path)

        super.onDraw(canvas)

        val paint = Paint().apply {
            color = cv_borderColor
            style = Paint.Style.STROKE
            strokeWidth = cv_borderWidth.toFloat()
        }

        canvas.drawCircle(centre, centre, centre-cv_borderWidth/2, paint)
    }

    @Dimension
    fun getBorderWidth():Int = (cv_borderWidth/resources.displayMetrics.density).toInt()

    fun setBorderWidth(@Dimension dp:Int) {
        cv_borderWidth = dp * resources.displayMetrics.density
        invalidate()
    }

    fun getBorderColor():Int = cv_borderColor

    fun setBorderColor(hex:String) {
        cv_borderColor = Color.parseColor(hex)
        invalidate()
    }

    fun setBorderColor(@ColorRes colorId: Int){
        if (cv_borderColor != resources.getColor(colorId, context.theme)) return
        else cv_borderColor = resources.getColor(colorId, context.theme)
        invalidate()
    }

//    fun setInitialsImage(firstName: String, lastName: String) {
//        var initials = ""
//        if (firstName.isNotEmpty()) {
//            initials = firstName[0].toString()
//        }
//        if (lastName.isNotEmpty()) {
//            initials = "$initials${lastName[0]}"
//        }
//        val drawable : Drawable
//        drawable.
//    }
}

