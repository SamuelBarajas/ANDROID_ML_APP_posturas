package dev.sadrpete.detectposesmlandroid.model

import com.google.firebase.auth.FirebaseAuth
import java.math.BigDecimal
import java.math.RoundingMode
import org.json.JSONObject
import com.google.firebase.database.FirebaseDatabase

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

    override fun toString(): String {

        val firebaseAuth = FirebaseAuth.getInstance()
        val user = firebaseAuth.currentUser
        val database = FirebaseDatabase.getInstance().getReference(user?.uid.toString())
        val jsonObject = JSONObject()
        jsonObject.put("LeftShoulder", BigDecimal(leftShoulderAngle ?: 0.0).setScale(2, RoundingMode.HALF_EVEN))
        jsonObject.put("RightShoulder", BigDecimal(rightShoulderAngle ?: 0.0).setScale(2, RoundingMode.HALF_EVEN))
        jsonObject.put("LeftElbow", BigDecimal(leftElbowAngle ?: 0.0).setScale(2, RoundingMode.HALF_EVEN))
        jsonObject.put("RightElbow", BigDecimal(rightElbowAngle ?: 0.0).setScale(2, RoundingMode.HALF_EVEN))
        jsonObject.put("LeftHip", BigDecimal(leftHipAngle ?: 0.0).setScale(2, RoundingMode.HALF_EVEN))
        jsonObject.put("RightHip", BigDecimal(rightHipAngle ?: 0.0).setScale(2, RoundingMode.HALF_EVEN))
        jsonObject.put("LeftKnee", BigDecimal(leftKneeAngle ?: 0.0).setScale(2, RoundingMode.HALF_EVEN))
        jsonObject.put("RightKnee", BigDecimal(rightKneeAngle ?: 0.0).setScale(2, RoundingMode.HALF_EVEN))
        database.setValue(jsonObject.toString())
        println(jsonObject)
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