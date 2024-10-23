package com.example.mazaadytask.utilis

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.drawable.Drawable
import android.text.TextUtils
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import com.example.mazaadytask.R


class TextInputLayout : com.google.android.material.textfield.TextInputLayout {

    private var textInputLayoutErrorDrawable: Drawable? = null
    private var textInputLayoutNormalSelectorDrawable: Drawable? = null

    constructor(context: Context) : super(context) {
        initTextInputLayout(context, null, 0)

    }

    constructor(
        context: Context,
        attrs: AttributeSet?,
    ) : super(context, attrs) {
        initTextInputLayout(context, attrs, 0)
    }

    constructor(
        context: Context,
        attrs: AttributeSet?,
        defStyleAttr: Int,
    ) : super(context, attrs, defStyleAttr) {
        initTextInputLayout(context, attrs, defStyleAttr)
    }

    @SuppressLint("Recycle")
    private fun initTextInputLayout(
        context: Context,
        attrs: AttributeSet?,
        defStyleAttr: Int,
    ) {
        val a = context.obtainStyledAttributes(attrs, R.styleable.TextInputLayout, defStyleAttr, 0)
        textInputLayoutErrorDrawable =
            a.getDrawable(R.styleable.TextInputLayout_textInputLayoutErrorDrawable)
        textInputLayoutNormalSelectorDrawable =
            a.getDrawable(R.styleable.TextInputLayout_textInputLayoutNormalSelectorDrawable)
    }

    override fun addView(child: View, index: Int, params: ViewGroup.LayoutParams) {
        if (child is EditText) {
            if (textInputLayoutNormalSelectorDrawable != null) {
                child.background = textInputLayoutNormalSelectorDrawable
            }
        }
        super.addView(child, index, params)
    }

    override fun setError(errorText: CharSequence?) {
        super.setError(errorText)
        if (textInputLayoutErrorDrawable != null) {
            if (TextUtils.isEmpty(errorText)) {
                if (textInputLayoutNormalSelectorDrawable != null) {
                    editText?.background = textInputLayoutNormalSelectorDrawable
                }
            } else {
                if (textInputLayoutErrorDrawable != null) {
                    editText?.background = textInputLayoutErrorDrawable
                }
            }
        }
    }

}