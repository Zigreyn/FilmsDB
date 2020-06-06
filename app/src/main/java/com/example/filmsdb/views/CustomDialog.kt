package com.example.filmsdb.views

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import com.example.filmsdb.R

class CustomDialog(
    context: Context,
    private val description: String,
    private val dialogCallback: IDialogCallback
) : Dialog(context) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dialog_custom)

        val dialogDescription = findViewById<TextView>(R.id.dialog_description)
        dialogDescription.text = description

        val negativeButton = findViewById<Button>(R.id.dialog_negative_button)
        negativeButton.setOnClickListener {
            dialogCallback.onClickNegativeButton()
            dismiss()
        }

        val positiveButton = findViewById<Button>(R.id.dialog_positive_button)
        positiveButton.setOnClickListener {
            dialogCallback.onClickPositiveButton()
            dismiss()
        }
    }

    interface IDialogCallback {

        fun onClickPositiveButton()

        fun onClickNegativeButton()
    }
}