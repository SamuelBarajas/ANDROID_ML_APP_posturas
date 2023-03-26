package dev.sadrpete.detectposesmlandroid.ui.main

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.core.content.ContextCompat
import com.github.mikephil.charting.charts.RadarChart
import com.github.mikephil.charting.data.RadarData
import com.github.mikephil.charting.data.RadarDataSet
import com.github.mikephil.charting.data.RadarEntry
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*
import com.google.firebase.database.ktx.database
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import dev.sadrpete.detectposesmlandroid.R
import dev.sadrpete.detectposesmlandroid.ui.main.MainActivity.Companion.TAG
import java.util.*
import kotlin.collections.ArrayList
import kotlin.random.Random

class RadarChart : AppCompatActivity() {

    lateinit var radarChart: RadarChart
    private lateinit var mDatabase: DatabaseReference
    val user : FirebaseUser? = FirebaseAuth.getInstance().currentUser
    val userID = user?.uid.toString()

    val calendario = Calendar.getInstance()
    val dia = calendario.get(Calendar.DAY_OF_MONTH)
    val mes = calendario.get(Calendar.MONTH) + 1
    val anio = calendario.get(Calendar.YEAR)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_radar_chart)

        val backButton = findViewById<Button>(R.id.back_button)
        backButton.setOnClickListener { finish() }

        radarChart = findViewById(R.id.radar_chart)

        mDatabase = Firebase.database.reference




        val list:ArrayList<RadarEntry> = ArrayList()
        list.add(RadarEntry(30f))
        list.add(RadarEntry(30f))
        list.add(RadarEntry(145f))
        list.add(RadarEntry(145f))
        list.add(RadarEntry(140f))
        list.add(RadarEntry(140f))
        list.add(RadarEntry(160f))
        list.add(RadarEntry(160f))


        val list2:ArrayList<RadarEntry> = ArrayList()

        var posturaData: Map<Double, Double>? = null
        mDatabase.child("Postura").child("$userID").child("$anio/$mes/$dia").get().addOnSuccessListener { dataSnapshot ->
            if (dataSnapshot.exists()) {

                posturaData = dataSnapshot.value as Map<Double, Double>
                var LeftShoulder = posturaData!!["LeftShoulderAngle"]


                Log.i("firebase", "El nombre es $LeftShoulder")
                posturaData?.get("LeftShoulderAngle")?.let { list2.add(it?.let { RadarEntry(it.toFloat()) }) }
                posturaData?.get("RightShoulderAngle")?.let { list2.add(it?.let { RadarEntry(it.toFloat()) }) }
                posturaData?.get("LeftElbowAngle")?.let { list2.add(it?.let { RadarEntry(it.toFloat()) }) }
                posturaData?.get("RightElbowAngle")?.let { list2.add(it?.let { RadarEntry(it.toFloat()) }) }
                posturaData?.get("LeftHipAngle")?.let { list2.add(it?.let { RadarEntry(it.toFloat()) }) }
                posturaData?.get("RightHipAngle")?.let { list2.add(it?.let { RadarEntry(it.toFloat()) }) }
                posturaData?.get("LeftKneeAngle")?.let { list2.add(it?.let { RadarEntry(it.toFloat()) }) }
                posturaData?.get("RightKneeAngle")?.let { list2.add(it?.let { RadarEntry(it.toFloat()) }) }


            } else {
                Log.i("firebase", "No se encontraron datos para la fecha especificada.")
            }
        }.addOnFailureListener{ exception ->
            Log.e("firebase", "Error al obtener los datos de Firebase", exception)
        }



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