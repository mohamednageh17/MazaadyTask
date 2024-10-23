package com.example.mazaadytask.utilis

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.Gravity.CENTER
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.view.Window
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.Spinner
import com.example.domain.remote.response.property.Options
import com.example.domain.utilis.SpinnerItem
import com.example.mazaadytask.databinding.DialogTableBinding
import com.example.mazaadytask.databinding.SearchableBottomSheetBinding
import com.example.mazaadytask.ui.main_activity.adapters.OptionsAdapter
import com.example.mazaadytask.ui.main_activity.adapters.TableAdapter
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.textfield.TextInputLayout

fun <T : SpinnerItem> TextInputLayout.setAsSpinner(
    spinner: Spinner,
    list: List<T>,
    setOnTextChanged: (EditText?, T?) -> Unit = { editText, item ->
        editText?.setText(item?.getSpinnerText())
    },
    onItemSelectedListener: (T?) -> Unit = {},
) {
    editText?.keyListener = null
    editText?.setOnClickListener {
        spinner.performClick()
    }
    editText?.setOnFocusChangeListener { _, hasFocus ->
        if (hasFocus) {
            spinner.performClick()
        }
    }

    val adapter = ArrayAdapter(
        this.context,
        android.R.layout.simple_spinner_item,
        (listOf(hint) + list.map {
            it.getSpinnerText() ?: ""
        }),
    )
    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
    spinner.adapter = adapter

    spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
        override fun onItemSelected(
            parent: AdapterView<*>?, view: View?, position: Int, id: Long
        ) {
            val isFirstTime = (spinner.tag as? Boolean) == true
            if (isFirstTime) {
                spinner.tag = false
                if (position == 0) {
                    setOnTextChanged(editText, null)
                } else {
                    setOnTextChanged(editText, list[position - 1])
                }
            } else {
                if (position == 0) {
                    onNothingSelected(parent)
                } else {
                    setOnTextChanged(editText, list[position - 1])
                    onItemSelectedListener(list[position - 1])
                }
            }
        }

        override fun onNothingSelected(parent: AdapterView<*>?) {
            setOnTextChanged(editText, null)
            onItemSelectedListener(null)
        }

    }
}

fun <T : SpinnerItem> TextInputLayout.setAsBottomSheet(
    context: Context,
    title: String = "",
    list: List<Options>? = null,
    onItemSelectedListener: (Options?) -> Unit = { editText?.setText(it?.name) },
) {

    editText?.keyListener = null
    editText?.setOnClickListener {
        showBottomSheet(context, title, list, onItemSelectedListener = {
            onItemSelectedListener.invoke(it)
            editText?.setText(it?.name)
        })
    }

    editText?.setOnFocusChangeListener { _, hasFocus ->
        if (hasFocus) {
            showBottomSheet(context, title, list, onItemSelectedListener = {
                onItemSelectedListener.invoke(it)
                editText?.setText(it?.name)
            })
        }
    }
}

private fun TextInputLayout.showBottomSheet(
    context: Context,
    title: String,
    list: List<Options>?,
    onItemSelectedListener: (Options?) -> Unit
) {
    editText?.clearFocus()

    val bottomSheetDialog = BottomSheetDialog(context)
    val bottomSheetContent =
        SearchableBottomSheetBinding.inflate(LayoutInflater.from(context), null, false)

    val adapter = OptionsAdapter(
        data = list ?: listOf(),
        onItemClick = {
            editText?.setText(it?.name)
            bottomSheetDialog.dismiss()
            onItemSelectedListener.invoke(it)
            editText?.clearFocus()
        })

    bottomSheetDialog.setContentView(bottomSheetContent.root)
    bottomSheetDialog.show()

    bottomSheetContent.titleTV.text = title
    bottomSheetContent.recyclerView.adapter = adapter
}

fun View.visible() {
    this.visibility = VISIBLE
}

fun View.invisible() {
    this.visibility = INVISIBLE
}

fun View.gone() {
    this.visibility = GONE
}

fun Double.roundTo(n: Int): Double {
    return "%.${n}f".format(this).toDouble()
}


fun Context.showTableDialog(
    data: List<Pair<String, String?>>
) {
    BottomSheetDialog(this).apply {
        setCancelable(true)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        val binding = DialogTableBinding.inflate(layoutInflater) // view
        setContentView(binding.root)

        window?.setBackgroundDrawable(
            ColorDrawable(Color.TRANSPARENT)
        )
        window?.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT
        )

        window?.attributes?.horizontalMargin = 20f
        window?.setGravity(CENTER)

        val adapter by lazy { TableAdapter(data) }
        with(binding) {
            recyclerView.adapter = adapter
        }
        show()
    }
}
