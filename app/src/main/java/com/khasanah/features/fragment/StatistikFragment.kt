package com.khasanah.features.fragment

import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.components.YAxis.AxisDependency
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.highlight.Highlight
import com.github.mikephil.charting.listener.OnChartValueSelectedListener
import com.github.mikephil.charting.utils.ColorTemplate
import com.khasanah.R
import kotlinx.android.synthetic.main.fragment_statistik.*
import java.util.ArrayList
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import com.khasanah.features.utils.Status
import com.khasanah.features.utils.ViewUtil
import com.khasanah.features.viewModel.KhasanahViewModel
import kotlinx.android.synthetic.main.fragment_statistik.view.*
import org.koin.android.viewmodel.ext.android.viewModel
import java.lang.Exception

class StatistikFragment : Fragment(), OnChartValueSelectedListener {

    private val viewModel : KhasanahViewModel by viewModel()
    lateinit var swipeRefresh: SwipeRefreshLayout
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_statistik, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        chartConfig()
        swipeRefresh = view.swipe_refresh
        viewModel.getPumpToday()
        viewModel.getPumpStatistic()
        observerData()
        observerDataStatistic()

        swipeRefresh.setOnRefreshListener {
            Handler().postDelayed(Runnable {
                swipeRefresh.isRefreshing = false
                viewModel.getPumpToday()
                viewModel.getPumpStatistic()
            }, 2000)
        }

        btn_tambah.setOnClickListener {
            findNavController().popBackStack(R.id.menuStatistik, true)
            findNavController().navigate(R.id.menuPompaAsi)
        }
    }

    private fun observerData() {
        viewModel.getPumpToday.observe(viewLifecycleOwner,
            androidx.lifecycle.Observer {
                when(it.status) {
                    Status.SUCCESS -> {
                        it.data?.data.let {
                            try {
                                if (!it?.total.isNullOrEmpty()) data_today.setText(it!!.total!!) else data_today.setText("-")
                                if (!it?.pleft.isNullOrEmpty()) data_left.setText(it!!.pleft!!) else data_left.setText("-")
                                if (!it?.pright.isNullOrEmpty()) data_right.setText(it!!.pright!!) else data_right.setText("-")
                            } catch (e : Exception){
                                ViewUtil.dialogError(requireContext(), e.toString())
                            }
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

    private fun observerDataStatistic() {
        viewModel.getPumpStatistic.observe(viewLifecycleOwner,
            androidx.lifecycle.Observer {
                when(it.status) {
                    Status.SUCCESS -> {
                        it.data?.data.let {
                            var dataTotal = arrayListOf<Entry>()
                            var dataLeft = arrayListOf<Entry>()
                            var dataRight = arrayListOf<Entry>()
                            var dataDate = arrayListOf<String>()
                            dataTotal.clear()
                            dataLeft.clear()
                            dataRight.clear()
                            var i = 0F
                            try {
                                it!!.listIterator().forEach {
                                    i++
                                    dataTotal.add(Entry(i, it.total!!))
                                    dataLeft.add(Entry(i, it.pleft!!))
                                    dataRight.add(Entry(i, it.pright!!))
                                    dataDate.add(it.datepump!!)
                                }
                            } catch (e : Exception){
                                ViewUtil.dialogError(requireContext(), e.toString())
                            }
                            setDataGraph(dataTotal, dataLeft, dataRight, dataDate)
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
    
    private fun setDataGraph(total : ArrayList<Entry>, left : ArrayList<Entry>, right : ArrayList<Entry>, dataDate : ArrayList<String>) {

        val values1 = total

        val values2 = left

        val values3 = right

        val left: LineDataSet
        val right: LineDataSet
        val total: LineDataSet
        if (chart.data != null &&
            chart.data.dataSetCount > 0
        ) {
            left = chart.data.getDataSetByIndex(0) as LineDataSet
            right = chart.data.getDataSetByIndex(1) as LineDataSet
            total = chart.data.getDataSetByIndex(2) as LineDataSet
            left.values = values1
            right.values = values2
            total.values = values3
            chart.data.notifyDataChanged()
            chart.notifyDataSetChanged()
        } else {
            // create a dataset and give it a type
            left = LineDataSet(values1, "Total")
            left.axisDependency = AxisDependency.LEFT
            left.color = ColorTemplate.getHoloBlue()
            left.setCircleColor(Color.BLACK)
            left.lineWidth = 2f
            left.circleRadius = 3f
            left.fillAlpha = 65
            left.fillColor = ColorTemplate.getHoloBlue()
            left.highLightColor = Color.rgb(244, 117, 117)
            left.setDrawCircleHole(false)
            //left.setFillFormatter(new MyFillFormatter(0f));
            //left.setDrawHorizontalHighlightIndicator(false);
            //left.setVisible(false);
            //left.setCircleHoleColor(Color.WHITE);

            // create a dataset and give it a type
            right = LineDataSet(values2, "Kiri")
            right.axisDependency = AxisDependency.RIGHT
            right.color = Color.RED
            right.setCircleColor(Color.BLACK)
            right.lineWidth = 2f
            right.circleRadius = 3f
            right.fillAlpha = 65
            right.fillColor = Color.RED
            right.setDrawCircleHole(false)
            right.highLightColor = Color.rgb(244, 117, 117)
            //right.setFillFormatter(new MyFillFormatter(900f));
            total = LineDataSet(values3, "Kanan")
            total.axisDependency = AxisDependency.RIGHT
            total.color = Color.YELLOW
            total.setCircleColor(Color.BLACK)
            total.lineWidth = 2f
            total.circleRadius = 3f
            total.fillAlpha = 65
            total.fillColor = ColorTemplate.colorWithAlpha(Color.YELLOW, 200)
            total.setDrawCircleHole(false)
            total.highLightColor = Color.rgb(244, 117, 117)

            // create a data object with the data sets
            val data = LineData(left, right, total)
            data.setValueTextColor(Color.BLACK)
            data.setValueTextSize(9f)
//            chart.axisLeft.setDrawGridLines(false)
//            chart.xAxis.setDrawGridLines(false)
            chart.setTouchEnabled(false)
            chart.setBackgroundColor(Color.WHITE)
            // set data
            chart.data = data
            val xAxis = chart.xAxis

            // the labels that should be drawn on the XAxis
            // Set the value formatter
            xAxis.valueFormatter = IndexAxisValueFormatter(dataDate)

        }
    }

    private fun chartConfig(){
        chart.setOnChartValueSelectedListener(this)

        // no description text
        chart.description.isEnabled = false

        // enable touch gestures

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

        // if disabled, scaling can be done on x- and y-axis separately
        chart.setPinchZoom(true)

        // set an alternative background color

        // set an alternative background color
        chart.setBackgroundColor(Color.LTGRAY)


        chart.animateX(1500)

        // get the legend (only possible after setting data)

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
        xAxis.setDrawGridLines(true)
        xAxis.setDrawAxisLine(true)

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