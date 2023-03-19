package dev.sadrpete.detectposesmlandroid.ui.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.core.content.ContextCompat
import com.github.mikephil.charting.charts.RadarChart
import com.github.mikephil.charting.data.RadarData
import com.github.mikephil.charting.data.RadarDataSet
import com.github.mikephil.charting.data.RadarEntry
import dev.sadrpete.detectposesmlandroid.R

class RadarChart : AppCompatActivity() {

    lateinit var radarChart: RadarChart

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_radar_chart)

        val backButton = findViewById<Button>(R.id.back_button)
        backButton.setOnClickListener { finish() }

        radarChart = findViewById(R.id.radar_chart)

        val list:ArrayList<RadarEntry> = ArrayList()
        list.add(RadarEntry(30f))
        list.add(RadarEntry(30f))
        list.add(RadarEntry(145f))
        list.add(RadarEntry(145f))
        list.add(RadarEntry(140f))
        list.add(RadarEntry(140f))
        list.add(RadarEntry(70f))
        list.add(RadarEntry(70f))


        val list2:ArrayList<RadarEntry> = ArrayList()
        list2.add(RadarEntry(10f))
        list2.add(RadarEntry(180f))
        list2.add(RadarEntry(145f))
        list2.add(RadarEntry(132f))
        list2.add(RadarEntry(168f))
        list2.add(RadarEntry(44f))
        list2.add(RadarEntry(90f))
        list2.add(RadarEntry(90f))


        val radarDataSet = RadarDataSet(list,"Postura ideal")
        radarDataSet.color = ContextCompat.getColor(applicationContext,R.color.ideal_line)
        radarDataSet.fillColor= ContextCompat.getColor(applicationContext,R.color.ideal)
        radarDataSet.fillAlpha=160
        radarDataSet.lineWidth=2f
        radarDataSet.setDrawFilled(true)
        radarDataSet.isDrawHighlightCircleEnabled=true
        radarDataSet.isDrawHighlightCircleEnabled=false
        radarDataSet.valueTextSize=15f

        val radarDataSet2 = RadarDataSet(list2,"Mi postura")
        radarDataSet2.color = ContextCompat.getColor(applicationContext,R.color.postura_line)
        radarDataSet2.fillColor= ContextCompat.getColor(applicationContext,R.color.postura)
        radarDataSet2.fillAlpha=160
        radarDataSet2.lineWidth=2f
        radarDataSet2.setDrawFilled(true)
        radarDataSet2.isDrawHighlightCircleEnabled=true
        radarDataSet2.isDrawHighlightCircleEnabled=false
        radarDataSet2.valueTextSize = 15f


        val radarData = RadarData()
        radarData.addDataSet(radarDataSet)
        radarData.addDataSet(radarDataSet2)

        radarChart.data = radarData
        radarChart.description.text = "Media de postura diaria"
        radarChart.data.setDrawValues(true)
        radarChart.description.textSize = 30f
    }
}