package com.khasanah.features.fragment

import android.app.DatePickerDialog
import android.app.Dialog
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.core.os.bundleOf
import androidx.core.widget.addTextChangedListener
import androidx.navigation.fragment.findNavController
import com.khasanah.features.dto.AddChildDto
import com.khasanah.features.dto.ResponseChildDto
import com.khasanah.features.utils.Status
import com.khasanah.features.utils.ViewUtil
import com.khasanah.features.viewModel.KhasanahViewModel
import com.khasanah.R
import com.khasanah.features.utils.Constant
import kotlinx.android.synthetic.main.dialog_common.*
import kotlinx.android.synthetic.main.fragment_add_child_data.*
import kotlinx.android.synthetic.main.fragment_detail_child_data.*
import kotlinx.android.synthetic.main.fragment_detail_child_data.btn_back
import kotlinx.android.synthetic.main.fragment_detail_child_data.btn_save
import kotlinx.android.synthetic.main.fragment_detail_child_data.et_nama_anak
import kotlinx.android.synthetic.main.fragment_detail_child_data.et_tanggal_anak
import kotlinx.android.synthetic.main.fragment_detail_child_data.ly_personal_tanggal
import kotlinx.android.synthetic.main.fragment_detail_child_data.sp_personal_gender
import org.koin.android.viewmodel.ext.android.viewModel
import java.text.SimpleDateFormat
import java.util.*

class AddChildDataFragment : Fragment(), AdapterView.OnItemSelectedListener {

    var selectedGender = ""
    private val viewModel : KhasanahViewModel by viewModel()
    var addChildDto = AddChildDto()
    var model = ResponseChildDto.Data()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_child_data, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (!model.id.isNullOrEmpty()) viewModel.getChildDetail(model.id!!)
        observeAddDataAnak()
        spinner()

        btn_back.setOnClickListener {
            findNavController().navigateUp()
        }

        ly_personal_tanggal.setOnClickListener {
            clickDataPicker()
        }
        btn_save.setOnClickListener {
            sendData()
        }
        et_nama_anak.addTextChangedListener {
            validateBtn()
        }
        et_tanggal_anak.addTextChangedListener {
            validateBtn()
        }
    }


    private fun observeAddDataAnak() {
        viewModel.addChild.observe(viewLifecycleOwner,
            androidx.lifecycle.Observer {
                when(it.status) {
                    Status.SUCCESS -> {
                        dialog(it.data?.status.toString())
                    }
                    Status.LOADING -> {
                        Log.d("TAG", "observerData: loading" )
                    }
                    Status.ERROR -> {
                        ViewUtil.dialogError(requireContext(), it.data?.message.toString())
                    }

                    Status.DONE -> {
                        it.status = Status.DONE
                    }
                }
            })
    }

    private fun validateBtn(){
        if (!et_tanggal_anak.text.toString().isNullOrEmpty() && !et_nama_anak.text.toString().isNullOrEmpty()){
            btn_save.isEnabled = true
            btn_save.setTextColor(resources.getColor(R.color.white))
            btn_save.setBackgroundResource(R.drawable.btn_active)
        } else {
            btn_save.isEnabled = false
            btn_save.setTextColor(resources.getColor(R.color.white))
            btn_save.setBackgroundResource(R.drawable.btn_noactive)
        }
    }

    private fun sendData(){
        addChildDto.name = et_nama_anak.text.toString()
        addChildDto.gender = selectedGender
        addChildDto.birthdate = et_tanggal_anak.text.toString()
        viewModel.postAddChild(addChildDto)
    }

    fun spinner() {
        val adapter = ArrayAdapter.createFromResource(requireContext(), R.array.gender, android.R.layout.simple_spinner_item)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        sp_personal_gender!!.adapter = adapter
        sp_personal_gender!!.onItemSelectedListener = this
    }

    private fun clickDataPicker() {
        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR) // Returns the value of the given calendar field. This indicates YEAR
        val month = c.get(Calendar.MONTH) // This indicates the Month
        var sMonth : String
        val day = c.get(Calendar.DAY_OF_MONTH) // This indicates the Day
        var sDay : String
        val year1= c.get(Calendar.YEAR).toString()
        val month1 = c.get(Calendar.MONTH)
        val month2 = (month1 + 1).toString()
        val day1 = c.get(Calendar.DAY_OF_MONTH).toString()
        val dpd = context?.let {
            DatePickerDialog(it,
                DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
//                        val selectedDate = "$dayOfMonth/${monthOfYear + 1}/$year"
                    sMonth = (monthOfYear + 1).toString()
                    sDay = dayOfMonth.toString()
                    if (monthOfYear < 10) {
                        sMonth = ("0$monthOfYear")
                    }
                    if (dayOfMonth < 10) {
                        sDay = ("0$dayOfMonth")
                    }
                    val selectedDate = "$year-$sMonth-$sDay"
                    et_tanggal_anak.text = selectedDate
                },
                year,
                month,
                day
            )
        }
        val format = SimpleDateFormat("dd/MM/yyyy")
//        val format = SimpleDateFormat("yyyy-MM-dd")
        val maxMonth = "$day/${month}/$year"
        val date = format.parse(maxMonth)
        val dateFormat = date.time

        // Which is used to select today and future day.
//        dpd?.datePicker?.minDate = Date().time
        dpd?.datePicker?.maxDate = dateFormat
        dpd?.show() // It is used to show the datePicker Dialog.
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        when (parent?.id) {
            R.id.sp_personal_gender -> {
                selectedGender = sp_personal_gender?.getItemAtPosition(position).toString()
            }
        }
    }

    override fun onNothingSelected(p0: AdapterView<*>?) {
        TODO("Not yet implemented")
    }

    fun dialog(message: String) {
        val customDialog = Dialog(this.requireActivity())
        val dialogLayout = layoutInflater.inflate(R.layout.dialog_common, null)
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
}