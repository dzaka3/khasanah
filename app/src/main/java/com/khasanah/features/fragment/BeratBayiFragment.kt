package com.khasanah.features.fragment

import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.core.widget.addTextChangedListener
import androidx.navigation.fragment.findNavController
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import com.github.mikephil.charting.highlight.Highlight
import com.github.mikephil.charting.listener.OnChartValueSelectedListener
import com.github.mikephil.charting.utils.ColorTemplate
import com.khasanah.features.dto.ResponseChildDto
import com.khasanah.features.utils.Constant
import com.khasanah.features.utils.Status
import com.khasanah.features.utils.ViewUtil
import com.khasanah.features.viewModel.KhasanahViewModel
import com.khasanah.R
import com.khasanah.features.dto.AddChildWeightDto
import kotlinx.android.synthetic.main.fragment_berat_bayi.*
import kotlinx.android.synthetic.main.fragment_berat_bayi.btn_back
import kotlinx.android.synthetic.main.fragment_berat_bayi.btn_save
import kotlinx.android.synthetic.main.fragment_berat_bayi.chart
import kotlinx.android.synthetic.main.fragment_berat_bayi.view.*
import org.koin.android.viewmodel.ext.android.viewModel
import java.util.ArrayList

class BeratBayiFragment : Fragment(), OnChartValueSelectedListener {

    private val viewModel : KhasanahViewModel by viewModel()
    lateinit var swipeRefresh: SwipeRefreshLayout
    var model = AddChildWeightDto()
    var selectedId = "0"
    var selectedName = ""
    var id : String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
                it -> id = (it.getString(Constant.KEY_DATA_CHILD))!!
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_berat_bayi, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        swipeRefresh = view.swipe_refresh
        viewModel.getChild()
        observerData()
        observerWeightData()
        observerAddData()
        chartConfig()
        btn_back.setOnClickListener {
            findNavController().navigateUp()
        }
        et_berat_anak.addTextChangedListener {
            if (!et_berat_anak.text.toString().isNullOrEmpty()){
                btn_save.isEnabled = true
                btn_save.setTextColor(resources.getColor(R.color.white))
                btn_save.setBackgroundResource(R.drawable.btn_active)
            }else{
                btn_save.isEnabled = false
                btn_save.setTextColor(resources.getColor(R.color.white))
                btn_save.setBackgroundResource(R.drawable.btn_noactive)
            }
        }
        btn_save.setOnClickListener {
            model.child = selectedId
            model.weight = et_berat_anak.text.toString()
            viewModel.postWeight(model)
        }
        swipeRefresh.setOnRefreshListener {
            Handler().postDelayed(Runnable {
                swipeRefresh.isRefreshing = false
                viewModel.getChild()
            }, 2000)
        }
    }

    private fun observerData() {
        viewModel.listAnak.observe(viewLifecycleOwner,
            androidx.lifecycle.Observer {
                when(it.status) {
                    Status.SUCCESS -> {
                        it.data?.data.let {
                            val listTitle = mutableListOf<ResponseChildDto.Data>()
                            listTitle.clear()
                            it!!.listIterator().forEach {
                                listTitle.add(it)
                            }
                            shimmer_bank.visibility = View.GONE
                            spinnerNamaAnak(listTitle)
                        }
                    }
                    Status.LOADING -> {
                        Log.d("TAG", "observerData: loading" )
                    }
                    Status.ERROR -> {
                        ViewUtil.dialogError(requireContext(), it.message)
                    }

                    Status.DONE -> {
                        it.status = Status.DONE
                    }
                }
            })
    }

    private fun observerAddData() {
        viewModel.postAddWeight.observe(viewLifecycleOwner,
            androidx.lifecycle.Observer {
                when(it.status) {
                    Status.SUCCESS -> {
                        it.data?.let {
                            ViewUtil.dialogInfo(requireContext(), it.status.toString())
                            et_berat_anak.setText("")
                        }
                        it.status = Status.DONE
                    }
                    Status.LOADING -> {
                        Log.d("TAG", "observerData: loading" )
                    }
                    Status.ERROR -> {
                        ViewUtil.dialogError(requireContext(), it.message)
                    }

                    Status.DONE -> {
                        it.status = Status.DONE
                    }
                }
            })
    }

    private fun spinnerNamaAnak(namaAnak : List<ResponseChildDto.Data>) {
        val arrStr = mutableListOf<String>()
        val arrStrId = mutableListOf<String>()
        namaAnak.listIterator().forEach {
            arrStr.add(it.name!!.toString())
            arrStrId.add(it.id!!.toString())
        }

        // Create an ArrayAdapter using the string array and a default spinner layout
        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, arrStr)
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        // Apply the adapter to the spinner
        spinner_pilihan_nama!!.setTitle("")
        spinner_pilihan_nama!!.setPositiveButton("")
        spinner_pilihan_nama!!.adapter = adapter

        if (id != null){
            for ((index, value) in arrStrId.withIndex()) {
                if (id == value) {
                    Log.d("bundle", "spinnerNamaAnak: samaaa")
                    spinner_pilihan_nama.setSelection(index)
                }
            }
        }

        //onSelect
        spinner_pilihan_nama!!.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                selectedId = namaAnak[position].id.toString()
                selectedName = namaAnak[position].name.toString()

                viewModel.getChildDetail(selectedId)
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }
    }

    private fun observerWeightData() {
        viewModel.getDetailChild.observe(viewLifecycleOwner,
            androidx.lifecycle.Observer {
                when(it.status) {
                    Status.SUCCESS -> {
                        try {
                            var i = 0F
                            val weightData = ArrayList<Entry>()
                            val date = ArrayList<String>()
                            weightData.clear()
                            date.clear()
                            it.data?.data.let {
                                it?.weight?.listIterator()?.forEach {
                                    i++
                                    weightData.add(Entry(i, it.weight!!))
                                    date.add(i.toString())
                                }
                            }
                            if (!weightData.isEmpty()) {
                                setDataGraph(weightData, date)
                                chart.visibility = View.VISIBLE
                                data_404.visibility = View.INVISIBLE
                            } else {
                                data_404.visibility = View.VISIBLE
                                chart.visibility = View.INVISIBLE
//                                ViewUtil.dialogInfo(
//                                    requireContext(),
//                                    "Silahkan Isi Data Berat Badan Anak"
//                                )
                            }
                        }catch (e : Exception){
                            ViewUtil.dialogError(requireContext(), e.toString())
                        }

                    }
                    Status.LOADING -> {
                        Log.d("TAG", "observerData: loading" )
                    }
                    Status.ERROR -> {
                        ViewUtil.dialogError(requireContext(), it.message)
                    }

                    Status.DONE -> {
                        it.status = Status.DONE
                    }
                }
            })
    }

    private fun setDataGraph(total : ArrayList<Entry>, date : ArrayList<String>) {
        val values = total

        val left: LineDataSet
        val total: LineDataSet
        if (chart.data != null &&
            chart.data.dataSetCount > 0
        ) {
            left = chart.data.getDataSetByIndex(0) as LineDataSet
            left.values = values
            chart.data.notifyDataChanged()
            chart.notifyDataSetChanged()
        } else {
            total = LineDataSet(values, "Berat Badan")
            total.axisDependency = YAxis.AxisDependency.RIGHT
            total.color = Color.YELLOW
            total.setCircleColor(Color.BLACK)
            total.lineWidth = 2f
            total.circleRadius = 3f
            total.fillAlpha = 65
            total.fillColor = ColorTemplate.colorWithAlpha(Color.YELLOW, 200)
            total.setDrawCircleHole(false)
            total.highLightColor = Color.rgb(244, 117, 117)

            // create a data object with the data sets
            val data = LineData(total)
            data.setValueTextColor(Color.BLACK)
            data.setValueTextSize(9f)
            chart.setTouchEnabled(false)
            chart.setBackgroundColor(Color.WHITE)
            // set data
            chart.data = data
            val xAxis = chart.xAxis

            // the labels that should be drawn on the XAxis
            // Set the value formatter
            xAxis.valueFormatter = IndexAxisValueFormatter(date)

        }
    }

    private fun chartConfig(){
        chart.setOnChartValueSelectedListener(this)

        // no description text
        chart.description.isEnabled = false

        // enable touch gestures
        chart.setTouchEnabled(true)

        chart.dragDecelerationFrictionCoef = 0.9f

        // enable scaling and dragging

        // enable scaling and dragging
        chart.isDragEnabled = true
        chart.setScaleEnabled(true)
        chart.setDrawGridBackground(false)
        chart.isHighlightPerDragEnabled = true

        // if disabled, scaling can be done on x- and y-axis separately
        chart.setPinchZoom(true)

        chart.setBackgroundColor(Color.LTGRAY)

        chart.animateX(1500)

        // get the legend (only possible after setting data)
        val l = chart.legend

        // modify the legend ...

        // modify the legend ...
        l.setForm(Legend.LegendForm.LINE)
//        l.setTypeface(tfLight)
        l.setTextSize(11f)
        l.setTextColor(Color.BLACK)
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM)
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.LEFT)
        l.setOrientation(Legend.LegendOrientation.HORIZONTAL)
        l.setDrawInside(false)
//        l.setYOffset(11f);

        //        l.setYOffset(11f);
        val xAxis: XAxis = chart.getXAxis()
//        xAxis.setTypeface(tfLight)
        xAxis.setTextSize(11f)
        xAxis.setTextColor(Color.BLACK)
        xAxis.setDrawGridLines(false)
        xAxis.setDrawAxisLine(false)

        val leftAxis: YAxis = chart.getAxisLeft()
//        leftAxis.setTypeface(tfLight)
        leftAxis.setTextColor(ColorTemplate.getHoloBlue())
        leftAxis.setAxisMaximum(200f)
        leftAxis.setAxisMinimum(0f)
        leftAxis.setDrawGridLines(true)
        leftAxis.setGranularityEnabled(true)

        val rightAxis: YAxis = chart.getAxisRight()
//        rightAxis.setTypeface(tfLight)
        rightAxis.setTextColor(Color.RED)
        rightAxis.setDrawGridLines(false)
        rightAxis.setDrawZeroLine(false)
        rightAxis.setGranularityEnabled(false)
    }

    override fun onValueSelected(e: Entry?, h: Highlight?) {
        TODO("Not yet implemented")
    }

    override fun onNothingSelected() {
        TODO("Not yet implemented")
    }
}