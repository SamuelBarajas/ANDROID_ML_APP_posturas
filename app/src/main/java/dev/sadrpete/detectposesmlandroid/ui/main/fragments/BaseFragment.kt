package dev.sadrpete.detectposesmlandroid.ui.main.fragments

import android.content.pm.PackageManager
import android.os.Bundle
import android.view.View
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.google.mlkit.vision.pose.defaults.PoseDetectorOptions
import dagger.hilt.android.AndroidEntryPoint
import dev.sadrpete.detectposesmlandroid.model.MLPoseDetector
import dev.sadrpete.detectposesmlandroid.model.MLPoseDetectorListener
import dev.sadrpete.detectposesmlandroid.utils.VisionImageProcessor
import javax.inject.Inject

@AndroidEntryPoint
abstract class BaseFragment : Fragment(), MLPoseDetectorListener {

    @Inject
    lateinit var options: PoseDetectorOptions

    /*@Inject
    lateinit var imageProcessor: VisionImageProcessor*/

    var imageProcessor: VisionImageProcessor? = null

    private var MLPoseDetector: MLPoseDetector? = null

    private val permissionCallbacks = HashMap<Int, PermissionRequest>()

    private data class PermissionRequest(
        val permission: String,
        val onDeniedAction: (() -> Unit)?,
        val onGrantedAction: () -> Unit
    )

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        permissionCallbacks[requestCode]?.let {
            if (ContextCompat.checkSelfPermission(requireContext(), it.permission) == PackageManager.PERMISSION_GRANTED) {
                it.onGrantedAction()
            } else {
                it.onDeniedAction?.invoke()
            }
            permissionCallbacks.remove(requestCode)
        }
    }

    fun onPermission(
        permission: String,
        onDeniedAction: (() -> Unit)? = null,
        onGrantedAction: () -> Unit
    ) {
        val requestCode = View.generateViewId()
        permissionCallbacks[requestCode] = PermissionRequest(permission, onDeniedAction, onGrantedAction)
        ActivityCompat.requestPermissions(requireActivity(), arrayOf(permission), requestCode)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        startCamera()

        MLPoseDetector = MLPoseDetector(requireContext(), options, false)
        imageProcessor = MLPoseDetector
        MLPoseDetector!!.MLPoseDetectorListener = this

    }

    abstract fun startCamera()
}