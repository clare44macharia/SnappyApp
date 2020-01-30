package com.myapps.claremacharia44.snappyapp

import android.content.pm.PackageManager
import android.os.Bundle
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import kotlinx.android.synthetic.main.activity_main.*




class MainActivity : AppCompatActivity(), ActivityCompat.OnRequestPermissionsResultCallback {

    /**
     * The constant permission request code
     */
    private val PERMISION_REQUEST_CODE = 3

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Make this activity full screen
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)

        setContentView(R.layout.activity_main)
        checkAndRequestCameraPermission()
    }

    /**
     * Request for camera permission if it is needed
     */
    private fun checkAndRequestCameraPermission() {
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.CAMERA),
                    PERMISION_REQUEST_CODE)
        } else {
            startFaceProcessor()
        }
    }

    /**
     * Start the face processor
     */
    private fun startFaceProcessor() {
        // Observe activity lifecycle to start, stop and destroy camera view based on lifecycle events
        lifecycle.addObserver(MainActivityLifecycleObserver(camera_view))

        // Start the face processing
        val faceProcessor = FaceProcessor(camera_view, overlay_view)
        faceProcessor.startProcessing()
    }

    /**
     * handling the camera request permission
     */
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        if (requestCode == PERMISION_REQUEST_CODE) {
            if (android.Manifest.permission.CAMERA == permissions[0] &&
                    grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                startFaceProcessor()
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }
}

