package dev.sadrpete.detectposesmlandroid.ui.main.fragments.capture_angles

import android.annotation.SuppressLint
import android.graphics.Matrix
import android.hardware.camera2.CaptureRequest
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageProxy
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import dagger.hilt.android.AndroidEntryPoint
import dev.sadrpete.detectposesmlandroid.MainNavGraphDirections
import dev.sadrpete.detectposesmlandroid.R
import dev.sadrpete.detectposesmlandroid.databinding.FragmentCaptureAnglesBinding
import dev.sadrpete.detectposesmlandroid.displayToast
import dev.sadrpete.detectposesmlandroid.model.MLPose
import dev.sadrpete.detectposesmlandroid.ui.main.MainActivity
import dev.sadrpete.detectposesmlandroid.ui.main.fragments.BaseFragment
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.lang.reflect.Array.set
import java.util.*
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

@AndroidEntryPoint
class CaptureAnglesFragment : BaseFragment() {

    private val TAG: String = "CaptureAnglesFragment"

    private var _binding: FragmentCaptureAnglesBinding? = null
    private val binding get() = _binding!!

    private val viewModel: CaptureAnglesViewModel by viewModels()

    private lateinit var cameraExecutor: ExecutorService

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCaptureAnglesBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        cameraExecutor = Executors.newSingleThreadExecutor()

        /*binding.btnCaptureAngles.setOnClickListener {
            // Log.d(TAG, "MLPoseCaptured: $MLPoseCaptured")
            viewModel.savePose()
        }*/
    }

    @RequiresApi(Build.VERSION_CODES.S)
    override fun startCamera() {
        val cameraProviderFuture = ProcessCameraProvider.getInstance(requireContext())

        cameraProviderFuture.addListener({
            // Used to bind the lifecycle of cameras to the lifecycle owner
            val cameraProvider: ProcessCameraProvider = cameraProviderFuture.get()

            // Preview
            val preview = Preview.Builder()
                .build()
                .also {
                    it.setSurfaceProvider(binding.viewFinder.surfaceProvider)

                }

            val imageAnalyzer = ImageAnalysis.Builder()
                .build()
                .also {
                    it.setAnalyzer(cameraExecutor, object : ImageAnalysis.Analyzer {

                        @SuppressLint("UnsafeExperimentalUsageError")
                        override fun analyze(imageProxy: ImageProxy) {

                            binding.graphicOverlay.setImageSourceInfo(
                                imageProxy.width, imageProxy.height, true
                            )

                            imageProcessor!!.processImageProxy(imageProxy, binding.graphicOverlay)
                        }
                    })
                }

            // Select front camera as a default
            val cameraSelector = CameraSelector.Builder()
                .requireLensFacing(CameraSelector.LENS_FACING_FRONT)
                .build()

            try {
                // Unbind use cases before rebinding
                cameraProvider.unbindAll()

                // Bind use cases to camera
                cameraProvider.bindToLifecycle(
                    this, cameraSelector, preview, imageAnalyzer
                )

            } catch (exc: Exception) {
                Log.e(MainActivity.TAG, "Use case binding failed", exc)
            }
        }, ContextCompat.getMainExecutor(requireContext()))
    }

    override fun poseIdentified(MLPose: MLPose) {
        viewModel.MLPoseCaptured = MLPose
        lifecycleScope.launch(Dispatchers.Main) {
            delay(300)
            displayToast(requireContext(), MLPose.toString())
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        cameraExecutor.shutdown()
        _binding = null
    }

}