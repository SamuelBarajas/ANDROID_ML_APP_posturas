package dev.sadrpete.detectposesmlandroid.model

import com.google.firebase.auth.FirebaseAuth
import java.math.BigDecimal
import java.math.RoundingMode
import org.json.JSONObject
import com.google.firebase.database.FirebaseDatabase
import java.util.*

data class MLPose(
    val leftShoulder: Landmark? = null,
    val rightShoulder: Landmark? = null,

    val leftElbow: Landmark? = null,
    val rightElbow: Landmark? = null,

    val leftHip: Landmark? = null,
    val rightHip: Landmark? = null,

    val leftKnee: Landmark? = null,
    val rightKnee: Landmark? = null,

    val leftShoulderAngle: Double? = null,
    val rightShoulderAngle: Double? = null,

    val leftElbowAngle: Double? = null,
    val rightElbowAngle: Double? = null,

    val leftHipAngle: Double? = null,
    val rightHipAngle: Double? = null,

    val leftKneeAngle: Double? = null,
    val rightKneeAngle: Double? = null
) {

    fun saveOnDatabasePose(){

        val calendario = Calendar.getInstance()
        val dia = calendario.get(Calendar.DAY_OF_MONTH)
        val mes = calendario.get(Calendar.MONTH) + 1
        val anio = calendario.get(Calendar.YEAR)

        val database= FirebaseDatabase.getInstance()
        val dbReference=database.reference.child("Postura")
        val firebaseAuth = FirebaseAuth.getInstance()
        val auth = firebaseAuth.currentUser
        //val jsonObject = JSONObject()


       /* jsonObject.put("LeftShoulder", BigDecimal(leftShoulderAngle ?: 0.0).setScale(2, RoundingMode.HALF_EVEN))
        jsonObject.put("RightShoulder", BigDecimal(rightShoulderAngle ?: 0.0).setScale(2, RoundingMode.HALF_EVEN))
        jsonObject.put("LeftElbow", BigDecimal(leftElbowAngle ?: 0.0).setScale(2, RoundingMode.HALF_EVEN))
        jsonObject.put("RightElbow", BigDecimal(rightElbowAngle ?: 0.0).setScale(2, RoundingMode.HALF_EVEN))
        jsonObject.put("LeftHip", BigDecimal(leftHipAngle ?: 0.0).setScale(2, RoundingMode.HALF_EVEN))
        jsonObject.put("RightHip", BigDecimal(rightHipAngle ?: 0.0).setScale(2, RoundingMode.HALF_EVEN))
        jsonObject.put("LeftKnee", BigDecimal(leftKneeAngle ?: 0.0).setScale(2, RoundingMode.HALF_EVEN))
        jsonObject.put("RightKnee", BigDecimal(rightKneeAngle ?: 0.0).setScale(2, RoundingMode.HALF_EVEN))*/

        val HombroIzquierdo = setOf<Double>(leftShoulderAngle ?: 0.0).average()
        val HombroDerecho = setOf<Double>(rightShoulderAngle ?: 0.0).average()
        val CodoIzquierdo = setOf<Double>(leftElbowAngle ?: 0.0).average()
        val CodoDerecho = setOf<Double>(rightElbowAngle ?: 0.0).average()
        val CaderaIzquierda = setOf<Double>(leftHipAngle ?: 0.0).average()
        val CaderaDerecha = setOf<Double>(rightHipAngle ?: 0.0).average()
        val RodillaIzquierda = setOf<Double>(leftKneeAngle ?: 0.0).average()
        val RodillaDerecha = setOf<Double>(rightKneeAngle ?: 0.0).average()


        val newReference = dbReference.child(auth?.uid.toString()).child("$anio/$mes/$dia")

            newReference.child("LeftShoulderAngle").setValue(HombroIzquierdo)
            newReference.child("RightShoulderAngle").setValue(HombroDerecho)
            newReference.child("LeftElbowAngle").setValue(CodoIzquierdo)
            newReference.child("RightElbowAngle").setValue(CodoDerecho)
            newReference.child("LeftHipAngle").setValue(CaderaIzquierda)
            newReference.child("RightHipAngle").setValue(CaderaDerecha)
            newReference.child("LeftKneeAngle").setValue(RodillaIzquierda)
            newReference.child("RightKneeAngle").setValue(RodillaDerecha)

        //println(jsonObject)
    }
    override fun toString(): String {

        saveOnDatabasePose()

        return "POSE ANGLES:\n\n" +
                "LeftShoulder: ${BigDecimal(leftShoulderAngle ?: 0.0).setScale(2, RoundingMode.HALF_EVEN)}\n" +
                "RightShoulder: ${BigDecimal(rightShoulderAngle ?: 0.0).setScale(2, RoundingMode.HALF_EVEN)}\n" +
                "LeftElbow: ${BigDecimal(leftElbowAngle ?: 0.0).setScale(2, RoundingMode.HALF_EVEN)}\n" +
                "RightElbow: ${BigDecimal(rightElbowAngle ?: 0.0).setScale(2, RoundingMode.HALF_EVEN)}\n" +
                "LeftHip: ${BigDecimal(leftHipAngle ?: 0.0).setScale(2, RoundingMode.HALF_EVEN)}\n" +
                "RightHip: ${BigDecimal(rightHipAngle ?: 0.0).setScale(2, RoundingMode.HALF_EVEN)}\n" +
                "LeftKnee: ${BigDecimal(leftKneeAngle ?: 0.0).setScale(2, RoundingMode.HALF_EVEN)}\n" +
                "RightKnee: ${BigDecimal(rightKneeAngle ?: 0.0).setScale(2, RoundingMode.HALF_EVEN)}"
    }

}