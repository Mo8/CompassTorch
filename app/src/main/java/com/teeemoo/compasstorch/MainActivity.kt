package com.teeemoo.compasstorch


import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.hardware.camera2.CameraManager
import android.os.Build
import android.os.Bundle
import android.view.animation.Animation
import android.view.animation.RotateAnimation
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import kotlin.math.roundToInt

class MainActivity : AppCompatActivity(), SensorEventListener {

    private lateinit var torchImageView: ImageView
    private lateinit var compassImageView: ImageView
    private lateinit var compassTextView: TextView
    private lateinit var cameraManager: CameraManager
    private lateinit var sensorManager: SensorManager
    private var flash: Boolean = false
    private var currentDegree = 0f

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        torchImageView = findViewById(R.id.torch)
        compassImageView = findViewById(R.id.compass)
        compassTextView = findViewById(R.id.compassDegree)
        cameraManager = getSystemService(Context.CAMERA_SERVICE) as CameraManager
        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager


        torchImageView.setOnClickListener {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                clickTorch()
            }
        }
    }

    override fun onResume() {
        super.onResume()
        @Suppress("DEPRECATION")
        sensorManager.registerListener(
            this,
            sensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION), SensorManager.SENSOR_DELAY_GAME
        )
    }


    @RequiresApi(Build.VERSION_CODES.M)
    private fun clickTorch() {
        flash = !flash
        cameraManager.setTorchMode(cameraManager.cameraIdList[0], flash)
    }

    override fun onSensorChanged(event: SensorEvent?) {
        val degree = (event?.values?.get(0)!!).roundToInt()
        val rotateAnimation = RotateAnimation(
            currentDegree,
            (-degree).toFloat(),
            Animation.RELATIVE_TO_SELF,
            0.5f,
            Animation.RELATIVE_TO_SELF,
            0.5f
        )
        rotateAnimation.duration = 210
        rotateAnimation.fillAfter = true
        compassImageView.startAnimation(rotateAnimation)
        currentDegree = (-degree).toFloat()
        compassTextView.text = currentDegree.toString()
    }

    override fun onAccuracyChanged(p0: Sensor?, p1: Int) {
    }
}


