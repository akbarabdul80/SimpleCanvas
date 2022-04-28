package com.zero.canvas

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect
import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.zero.canvas.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private val binding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    private lateinit var mCanvas: Canvas
    private lateinit var mBitmap: Bitmap
    private val mPain = Paint()
    private val mPaintText = Paint(Paint.UNDERLINE_TEXT_FLAG)

    private val mColorBackground by lazy {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            resources.getColor(R.color.colorBackground, null)
        } else {
            resources.getColor(R.color.colorBackground)
        }
    }
    private val mColorRectangle by lazy {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            resources.getColor(R.color.colorRectangle, null)
        } else {
            resources.getColor(R.color.colorRectangle)
        }
    }
    private val mColorAccent by lazy {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            resources.getColor(R.color.purple_200, null)
        } else {
            resources.getColor(R.color.purple_200)
        }
    }

    private var mOffset = 120
    private var OFFSET = mOffset
    private val mRect: Rect = Rect()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        mPaintText.color = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            resources.getColor(R.color.black, null)
        } else {
            resources.getColor(R.color.black)
        }

        mPaintText.textSize = 70F

        binding.imgCanvas.setOnClickListener {
            val vWidth = it.width
            val vHeight = it.height

            val halfWidth = vWidth / 2
            val halfHeight = vHeight / 2

            when (mOffset == OFFSET) {
                true -> {
                    mBitmap = Bitmap.createBitmap(vWidth, vHeight, Bitmap.Config.ARGB_8888)
                    binding.imgCanvas.setImageBitmap(mBitmap)
                    mCanvas = Canvas(mBitmap)
                    mCanvas.drawColor(mColorBackground)
                    mCanvas.drawText("Keep Tapping", 100f, 100f, mPaintText)
                    mOffset += OFFSET
                }
                false -> {
                    if (mOffset < halfWidth && mOffset < halfHeight) {
                        mPain.color = mColorRectangle - 100 * mOffset
                        mRect.set(mOffset, mOffset, vWidth - mOffset, vHeight - mOffset)
                        mCanvas.drawRect(mRect, mPain)
                        mOffset += OFFSET
                    } else {
                        mPain.color = mColorAccent
                        mCanvas.drawCircle(
                            halfWidth.toFloat(), halfHeight.toFloat(),
                            halfWidth.toFloat() / 3, mPain
                        )

                        val mBounds = Rect()
                        val text = "Done!!"
                        mPaintText.getTextBounds(text, 0, text.length, mBounds)

                        val textX = mBounds.centerX()
                        val textY = mBounds.centerY()

                        mCanvas.drawText(
                            text, halfWidth.toFloat() - textX,
                            halfHeight.toFloat() - textY, mPaintText
                        )
                        mOffset += OFFSET
                    }
                }
            }
            it.invalidate()
        }

    }
}