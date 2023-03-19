package dev.sadrpete.detectposesmlandroid.ui.main

import android.Manifest
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import dev.sadrpete.detectposesmlandroid.MainNavGraphDirections
import dev.sadrpete.detectposesmlandroid.R
import dev.sadrpete.detectposesmlandroid.databinding.ActivityMainBinding
import dev.sadrpete.detectposesmlandroid.displayToast

@AndroidEntryPoint
class MainActivity : BaseActivity() {

    private lateinit var binding: ActivityMainBinding

    // val viewModel: MainViewModel by viewModels()

    // private lateinit var cameraExecutor: ExecutorService

    // private var MLPoseDetector: MLPoseDetector? = null

    /*private var MLPoseCaptured: MLPose? = null

    private lateinit var fileName: String

    val dataSet: ArrayList<Array<String>> = ArrayList()
    var numPoseCaptured: Int = 1*/

    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val statsButton = findViewById<Button>(R.id.button)
        statsButton.setOnClickListener{
            val intent = Intent(this, RadarChart::class.java)
            startActivity(intent)
        }

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.navHostFragment) as NavHostFragment
        navController = navHostFragment.findNavController()

        onPermission(
            STORAGE_PERMISSION,
            onDeniedAction = {
                this.displayToast("Permissions not granted by the user.")
                finish()
            }, onGrantedAction = {
                // generateCSVFile()
                // createHeaders()
            }
        )

        // Request camera permissions
        onPermission(
            CAMERA_PERMISSION,
            onDeniedAction = {
                this.displayToast("Permissions not granted by the user.")
                finish()
            }, onGrantedAction = {
                 //startCamera()
            }
        )

    }

    //SELECCIONAR FUNCION
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.capture_angles -> {
                val action = MainNavGraphDirections.actionGlobalCaptureAnglesFragment()
                navController.navigate(action)
            }
            /*R.id.identify_pose -> {
                val action = MainNavGraphDirections.actionGlobalIdentifyPoseFragment()
                navController.navigate(action)
            }*/
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.right_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    companion object {
        const val TAG = "CameraXBasic"
        private const val CAMERA_PERMISSION = Manifest.permission.CAMERA
        private const val STORAGE_PERMISSION = Manifest.permission.WRITE_EXTERNAL_STORAGE
    }
}