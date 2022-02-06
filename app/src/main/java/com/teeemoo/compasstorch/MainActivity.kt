package com.teeemoo.compasstorch


import android.content.Context
import android.hardware.camera2.CameraManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import androidx.annotation.RequiresApi

class MainActivity : AppCompatActivity() {

    private lateinit var torchImageView: ImageView
    private lateinit var compassImageView: ImageView
    private lateinit var cameraManager: CameraManager
    private var flash: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        torchImageView = findViewById(R.id.torch)
        compassImageView = findViewById(R.id.compass)
        cameraManager = getSystemService(Context.CAMERA_SERVICE) as CameraManager

        torchImageView.setOnClickListener {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                clickTorch()
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun clickTorch() {
        flash = !flash
        cameraManager.setTorchMode(cameraManager.cameraIdList[0], flash)
    }
}