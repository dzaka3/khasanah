package com.khasanah.features.utils

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Context
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.Window
import android.view.inputmethod.InputMethodManager
import android.widget.*
import androidx.core.content.ContextCompat
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.snackbar.Snackbar.SnackbarLayout
import com.khasanah.R
import kotlinx.android.synthetic.main.dialog_common.*
import java.io.File

object ViewUtil {
    lateinit var imagePath: File
    var snackbar: Snackbar? = null

    fun hideSoftKeyBoard(context: Context, view: View) {
        try {
            val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view.windowToken, InputMethodManager.HIDE_NOT_ALWAYS)
        } catch (e: Exception) {
            // TODO: handle exception
            e.printStackTrace()
        }

    }
    fun dialogInfo(context: Context, message: String) {
        val customDialog = Dialog(context)
        val inflater = LayoutInflater.from(context)
        val dialogLayout = inflater.inflate(R.layout.dialog_common, null)
        customDialog.setContentView(dialogLayout)
//        customDialog.imageView.setImageResource(R.drawable.ic_checklist_white_blue)
        customDialog.lbl_title.text = message

        customDialog.lbl_desc.visibility = View.GONE
        customDialog.lbl_desc2.visibility = View.GONE
        customDialog.btn_okay.text = "OK"
        customDialog.btn_okay.setOnClickListener {
            if (customDialog.isShowing)
                customDialog.dismiss()
        }
        customDialog.window?.setBackgroundDrawableResource(R.color.transparent)
        customDialog.show()
    }

    fun customSnackBar(view: View, img: Drawable?, title: String, btnLabel: String, lenght: Int){
        snackbar = Snackbar.make(view, "", lenght)
        val layout = snackbar!!.view as SnackbarLayout
        val textView = layout.findViewById<View>(R.id.snackbar_text) as TextView
        textView.visibility = View.INVISIBLE
        val mInflater = LayoutInflater.from(view.context)
        val snackView: View = mInflater.inflate(R.layout.custom_snackbar, null)
        val imgDialog = snackView.findViewById<View>(R.id.imageView) as ImageView
        val textViewTop = snackView.findViewById<View>(R.id.lbl_title) as TextView
        val button = snackView.findViewById<Button>(R.id.btn_ok)
        button.text = btnLabel
        button.setOnClickListener{
            if(snackbar!!.isShown){
                snackbar!!.dismiss()
            }
        }
        textViewTop.text = title
        imgDialog.setImageDrawable(img)
        layout.setPadding(0, 0, 0, 0)
        layout.addView(snackView, 0)
        layout.setBackgroundColor(ContextCompat.getColor(view.context, R.color.transparent))
        snackbar!!.show()
        hideSoftKeyBoard(view.context, view)
    }

    fun dialogComing(context: Context, message: String? = null){
        val customDialog = Dialog(context)
        val inflater = LayoutInflater.from(context)
        val dialogLayout = inflater.inflate(R.layout.dialog_common, null)
        customDialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        customDialog.setContentView(dialogLayout)
        customDialog.lbl_title.text = context.getString(R.string.common_info)
        if(message!=null){
            customDialog.lbl_desc.text = message
        }else {
            customDialog.lbl_desc.text = context.getString(R.string.common_coming_soon)
        }
        customDialog.btn_okay.setOnClickListener(View.OnClickListener {
            customDialog.dismiss() // Dialog will be dismissed
        })
        customDialog.window?.setBackgroundDrawableResource(R.color.transparent)
        //Start the dialog and display it on screen.
        customDialog.show()
    }

    @SuppressLint("ResourceAsColor")
    fun dialogError(context: Context, message: String? = null){
        val customDialog = Dialog(context)
        val inflater = LayoutInflater.from(context)
        val dialogLayout = inflater.inflate(R.layout.dialog_error, null)
        customDialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        customDialog.setContentView(dialogLayout)
        customDialog.lbl_title.text = context.getString(R.string.common_waning_title)
        if(message!=null){
            customDialog.lbl_desc.text = message
        }else {
            customDialog.lbl_desc.text = context.getString(R.string.common_coming_soon)
        }
        customDialog.btn_okay.setOnClickListener(View.OnClickListener {
            customDialog.dismiss() // Dialog will be dismissed
        })
        customDialog.window?.setBackgroundDrawableResource(R.color.transparent)
        //Start the dialog and display it on screen.
        customDialog.show()
    }

}