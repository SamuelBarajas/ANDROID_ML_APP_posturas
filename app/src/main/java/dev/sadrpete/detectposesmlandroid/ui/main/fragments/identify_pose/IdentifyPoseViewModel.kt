package dev.sadrpete.detectposesmlandroid.ui.main.fragments.identify_pose

import android.content.Context
import android.content.res.AssetFileDescriptor
import android.util.Log
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.sadrpete.detectposesmlandroid.model.MLPose
import dev.sadrpete.detectposesmlandroid.ui.main.MainActivity
import org.tensorflow.lite.Interpreter
import org.tensorflow.lite.support.common.FileUtil
import java.io.FileInputStream
import java.io.IOException
import java.nio.MappedByteBuffer
import java.nio.channels.FileChannel
import javax.inject.Inject

@HiltViewModel
class IdentifyPoseViewModel
@Inject
constructor(
    private val applicationContext: Context
): ViewModel() {

    lateinit var interpreter: Interpreter

    private var labels: List<String>? = null

    private val mean = arrayListOf(90.131819f, 89.043270f, 144.747361f, 147.245133f, 106.229700f, 103.194946f, 116.386812f, 113.702324f)
    private val std = arrayListOf(62.528951f, 62.945958f, 41.655029f, 41.981025f, 43.127208f, 42.296699f, 57.592427f, 56.854638f)

    init {
        labels = FileUtil.loadLabels(applicationContext, "labels.txt")
    }

    fun doInference(input: Array<FloatArray>): String? {
        val output = Array(1){ FloatArray(labels!!.size) }
        interpreter.run(input, output)

        for(x in 0..9){
            Log.d(MainActivity.TAG, "OUTPUT: ${output[0][x]}")
        }

        // return output[0][0]
        return getPosePredicted(output[0])
    }

    @Throws(IOException::class)
    fun loadModelFile(): MappedByteBuffer {
        val assetFileDescriptor: AssetFileDescriptor = applicationContext.assets.openFd("ypc_m4.tflite")
        val fileInputStream = FileInputStream(assetFileDescriptor.fileDescriptor)
        val fileChannel: FileChannel = fileInputStream.channel
        val startOffset = assetFileDescriptor.startOffset
        val length = assetFileDescriptor.length
        return fileChannel.map(FileChannel.MapMode.READ_ONLY, startOffset, length)
    }

    private fun getPosePredicted(array: FloatArray): String? {
        var max = array[0]
        var pos = 0
        for(i in array.indices){
            if(array[i] > max){
                max = array[i]
                pos = i
            }
        }
        Log.d(MainActivity.TAG, "Largest element: $max at position: $pos")

        val poseName = labels?.get(pos)
        Log.d(MainActivity.TAG, "Pose Name: $poseName")
        return poseName
    }

    fun normalizeValues(MLPose: MLPose): Array<FloatArray> {
        val floats = Array(1){ FloatArray(7) }
        floats[0][0] = (MLPose.leftShoulderAngle!!.toFloat() - mean[0]) / std[0]
        floats[0][1] = (MLPose.rightShoulderAngle!!.toFloat() - mean[1]) / std[1]
        floats[0][2] = (MLPose.leftElbowAngle!!.toFloat() - mean[2]) / std[2]
        floats[0][3] = (MLPose.rightElbowAngle!!.toFloat() - mean[3]) / std[3]
        floats[0][4] = (MLPose.leftHipAngle!!.toFloat() - mean[4]) / std[4]
        floats[0][5] = (MLPose.rightHipAngle!!.toFloat() - mean[5]) / std[5]
        floats[0][6] = (MLPose.leftKneeAngle!!.toFloat() - mean[6]) / std[6]
        floats[0][7] = (MLPose.rightKneeAngle!!.toFloat() - mean[7]) / std[7]

        return floats
    }

}