package ru.skillbranch.devintensive.ui.custom

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.util.TypedValue
import android.widget.ImageView
import androidx.annotation.ColorRes
import androidx.annotation.Dimension
import androidx.annotation.Dimension.DP
import androidx.core.content.ContextCompat
import androidx.core.graphics.createBitmap
import ru.skillbranch.devintensive.App
import ru.skillbranch.devintensive.R

open class CircleImageView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ImageView(context, attrs, defStyleAttr) {

    companion object {
        private const val DEFAULT_CV_BORDERCOLOR = Color.WHITE
        private const val DEFAULT_CV_BORDERWIDTH = 2f
    }

    private var cv_borderColor = DEFAULT_CV_BORDERCOLOR
    private var cv_borderWidth = DEFAULT_CV_BORDERWIDTH

    init {
        if (attrs != null) {
            val a = context.obtainStyledAttributes(attrs, R.styleable.CircleImageView)
            cv_borderColor = a.getColor(R.styleable.CircleImageView_cv_borderColor, DEFAULT_CV_BORDERCOLOR)
            cv_borderWidth = a.getDimension (R.styleable.CircleImageView_cv_borderWidth, DEFAULT_CV_BORDERWIDTH*resources.displayMetrics.density)

            a.recycle()
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
            strokeWidth = cv_borderWidth
        }

        canvas.drawCircle(centre, centre, centre-(cv_borderWidth/2), paint)
    }

    @Dimension(unit = DP)
    fun getBorderWidth():Int = (cv_borderWidth/resources.displayMetrics.density).toInt()

    fun setBorderWidth(@Dimension(unit = DP) dp:Int) {
        if (cv_borderWidth == dp*resources.displayMetrics.density) return
        cv_borderWidth = dp*resources.displayMetrics.density
        invalidate()
    }

    fun getBorderColor():Int = cv_borderColor

    fun setBorderColor(hex:String){
        cv_borderColor = if (hex[0].toString() != "#") {
            Color.parseColor("#$hex")
        } else {
            Color.parseColor(hex)
        }
        invalidate()
    }

    fun setBorderColor(@ColorRes colorId: Int){
        cv_borderColor = ContextCompat.getColor(App.applicationContext(), colorId)


//        if (cv_borderColor != resources.getColor(colorId, context.theme)) return
//        else cv_borderColor = resources.getColor(colorId, context.theme)
        invalidate()
    }


    fun setInitialsImage(firstName: String, lastName: String) {
        if (firstName.isEmpty() && lastName.isEmpty()) {
//            setImageDrawable(resources.getDrawable(R.drawable.avatar_default, context.theme))
//        } else {
//            val initialsSB = StringBuilder()
//            if (firstName.isNotEmpty()) {
//                initialsSB.append(firstName[0].toUpperCase())
//            }
//            if (lastName.isNotEmpty()) {
//                initialsSB.append(lastName[0].toUpperCase())
//            }

            val initials = "${firstName[0]?:""} ${lastName[0]?:""}"

            val bitmap = createBitmap (1000, 1000, Bitmap.Config.ARGB_8888)
            val canvas = Canvas(bitmap)

            val value = TypedValue()
            context.theme.resolveAttribute(R.attr.colorAccent, value, true)

            val textScaleValue = bitmap.width/200f

            val paintText = Paint().apply {
                color = Color.WHITE
                textAlign = Paint.Align.CENTER
                textSize = 80*textScaleValue
            }

            canvas.drawColor(value.data)
            canvas.drawText(initials, bitmap.width/2f, (bitmap.height + 68*textScaleValue)/2f, paintText)

            setImageBitmap(bitmap)
        }
        invalidate()
    }

    fun setInitialsImage(initials: String) {
//        if (firstName.isEmpty() && lastName.isEmpty()) {
//            setImageDrawable(resources.getDrawable(R.drawable.avatar_default, context.theme))
//        } else {
//            val initialsSB = StringBuilder()
//            if (firstName.isNotEmpty()) {
//                initialsSB.append(firstName[0].toUpperCase())
//            }
//            if (lastName.isNotEmpty()) {
//                initialsSB.append(lastName[0].toUpperCase())
//            }

        val bitmap = createBitmap (1000, 1000, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)

        val value = TypedValue()
        context.theme.resolveAttribute(R.attr.colorAccent, value, true)

        val textScaleValue = bitmap.width/200f

        val paintText = Paint().apply {
            color = Color.WHITE
            textAlign = Paint.Align.CENTER
            textSize = 80*textScaleValue
        }

        canvas.drawColor(value.data)
        canvas.drawText(initials, bitmap.width/2f, (bitmap.height + 68*textScaleValue)/2f, paintText)

        setImageBitmap(bitmap)
//        }
        invalidate()
    }
}