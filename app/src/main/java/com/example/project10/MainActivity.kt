package com.example.project10

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager

import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.location.Geocoder
import android.location.Location
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.app.ActivityCompat
import androidx.dynamicanimation.animation.DynamicAnimation
import androidx.dynamicanimation.animation.FlingAnimation
import com.example.project10.ui.theme.Project10Theme
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices

class MainActivity : ComponentActivity(), SensorEventListener {

    lateinit var fusedLocationProviderClient: FusedLocationProviderClient

    var name = ""
    var city = ""
    var state = ""
    var temperature = ""
    var airPressure = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient((this))

        getCurrentLocation()

        //val fling = FlingAnimation(, DynamicAnimation.SCROLL_X)

        var name = ""
        var city = ""
        var state = ""
        var temperature = ""
        var airPressure = ""


        var tempSensor: Sensor? = null
        var pressureSensor: Sensor? = null

        var sensorManager: SensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        if (sensorManager.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE) != null) {
            tempSensor = sensorManager.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE)
        } else {
            // Failure! No magnetometer.
        }

        if (sensorManager.getDefaultSensor(Sensor.TYPE_PRESSURE) != null) {
            tempSensor = sensorManager.getDefaultSensor(Sensor.TYPE_PRESSURE)
        } else {
            // Failure! No magnetometer.
        }

        //val sensorManager = SensorManager.
        setContent {
            Project10Theme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Greeting(name, city, state, temperature, airPressure)
                }
            }
        }

    }

    private fun getCurrentLocation() {
        TODO("Not yet implemented")
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return
        }
        fusedLocationProviderClient.lastLocation.addOnCompleteListener(this){ task->
            val location: Location?=task.result
            if(location != null){
                val geocoder = Geocoder(this)
                city = geocoder.getFromLocation(location.latitude, location.longitude, 1)?.get(0)!!.locality
                state = geocoder.getFromLocation(location.latitude, location.longitude, 1)?.get(0)!!.adminArea
            }
        }
    }

    override fun onSensorChanged(p0: SensorEvent?) {
        TODO("Not yet implemented")
        if (p0 != null) {
            temperature = p0.values[0].toString()
        }
    }

    override fun onAccuracyChanged(p0: Sensor?, p1: Int) {
        TODO("Not yet implemented")
    }
}

@Composable
fun Greeting(name: String, city: String, state: String, temperature: String, airPressure: String, modifier: Modifier = Modifier) {
    Column {
        Text("Sensors Playground")
        Text("$name")
        Text("Location:")
        Text("City: $city")
        Text("State: $state")
        Text("Temperature: $temperature")
        Text("Air Pressure: $airPressure")
        Button(onClick = { /*TODO*/ }) {
            Text("Gesture Playground")
        }
    }
}

@Composable
fun Gestures(){

}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    Project10Theme {
        Greeting("","","","","")
    }
}