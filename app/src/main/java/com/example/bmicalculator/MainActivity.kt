package com.example.bmicalculator

import android.content.SharedPreferences
import android.content.SharedPreferences.Editor
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import java.text.DecimalFormat
import kotlin.math.pow

class MainActivity : AppCompatActivity() {
    private lateinit var etWeightView: EditText
    private lateinit var etHeightView: EditText
    private lateinit var btnCalculateView: Button
    private lateinit var tvResultNumberView: TextView
    private lateinit var tvResultTextView: TextView
    private lateinit var tvResultRangeView: TextView
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var editor: Editor
    private lateinit var decimalFormat: DecimalFormat

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //Pre logger
        Log.i("MyTag", "MainActivity : onCreate")

        //Creating a folder in DATA named "SharedPreferences_Folder_BMI for data storage then setting its rwx to private
        sharedPreferences = getSharedPreferences("SharedPreferences_Folder_BMI", MODE_PRIVATE)
        //Making the "SharedPreferences_Folder_BMI" folder editable
        editor = sharedPreferences.edit()

        //Instantiating the crated objects
        etWeightView = findViewById(R.id.etWeight)
        etHeightView = findViewById(R.id.etHeight)
        btnCalculateView = findViewById(R.id.btnCalculate)
        tvResultNumberView = findViewById(R.id.tvResultNumber)
        tvResultTextView = findViewById(R.id.tvResultText)
        tvResultRangeView = findViewById(R.id.tvResultRange)
        decimalFormat = DecimalFormat("###.##")

        //When the use clicked the button
        btnCalculateView.setOnClickListener {
            //If the hasValue function returns true
            if (hasValue(etWeightView.text.toString(), etHeightView.text.toString())) btnCalculate()
        }

        //Post logger
        Log.i("MyTag", "MainActivity : Post onCreate")
    }
    private fun hasValue(weight: String?, height: String?): Boolean {
        return when {
            //If weight is empty
            weight.isNullOrEmpty() -> {
                Toast.makeText(
                    this@MainActivity,
                    "Please enter your weight",
                    Toast.LENGTH_LONG
                ).show()
                Log.i("MyTag", "MainActivity : First false")
                return false
            }
            //If height is empty
            height.isNullOrEmpty() -> {
                Toast.makeText(
                    this@MainActivity,
                    "Please enter your height",
                    Toast.LENGTH_LONG
                ).show()
                Log.i("MyTag", "MainActivity : Second false")
                return false
            }
            //If weight and height both have values
            else -> {
                Log.i("MyTag", "MainActivity : First True")
                return true
            }
        }
    }

    private fun btnCalculate() {
        val etWeightValue: Double = etWeightView.text.toString().toDouble()
        val etHeightValue: Double = etHeightView.text.toString().toDouble()
        //Getting the height^2
        val convertedHeightValue: Double = (etHeightValue * 0.01).pow(2.0)
        //BMI formula
        val bmiValue: Double = etWeightValue / (convertedHeightValue)
        //Making the answer in two decimal places
        tvResultNumberView.text = decimalFormat.format(bmiValue).toString()

        when {
            bmiValue < 18.5 -> {
                tvResultTextView.text = "Underweight"
                tvResultTextView.setTextColor(ContextCompat.getColor(this@MainActivity, R.color.underweight))
                tvResultRangeView.text = "(underweight range is less than or equal to 18.5)"
            }
            bmiValue in 18.5..24.99 -> {
                tvResultTextView.text = "Healthy"
                tvResultTextView.setTextColor(ContextCompat.getColor(this@MainActivity, R.color.healthy))
                tvResultRangeView.text = "(normal range is between 18.5 to 25)"
            }
            bmiValue in 25.0..29.99 -> {
                tvResultTextView.text = "Overweight"
                tvResultTextView.setTextColor(ContextCompat.getColor(this@MainActivity, R.color.overweight))
                tvResultRangeView.text = "(overweight range is between 25 to 30)"
            }
            bmiValue > 30 -> {
                tvResultTextView.text = "Obese"
                tvResultTextView.setTextColor(ContextCompat.getColor(this@MainActivity, R.color.obese))
                tvResultRangeView.text = "(obese range is greater 30)"
            }
        }
    }

    override fun onResume() {
        super.onResume()
        //Pre logger
        Log.i("MyTag", "MainActivity : onResume")
        //Getting the weight and height value saved from "sharedPreferences_Folder_BMI" folder
        //using shared preferences
        val heightValueFromSharedPreferences: Int = sharedPreferences.getInt("INT_KEY_height", 0)
        val weightValueFromSharedPreferences: Int = sharedPreferences.getInt("INT_KEY_weight", 0)

        //Putting the stored value to the view
        etHeightView.setText(heightValueFromSharedPreferences.toString())
        etWeightView.setText(weightValueFromSharedPreferences.toString())

        //Post logger
        Log.i("MyTag", "MainActivity : Post onResume")
    }

    override fun onPause() {
        super.onPause()
        Log.i("MyTag", "MainActivity : onPause")
        //Getting the Weight and Height values from view
        val etWeightFromView: Int = etWeightView.text.toString().toInt()
        val etHeightFromView: Int = etHeightView.text.toString().toInt()

        //Saving the values from "etWeightFromView" and "etHeightFromView" to
        //"SharedPreferences_Folder_BMI" folder using shared preferences
        editor.apply {
            putInt("INT_KEY_weight", etWeightFromView)
            putInt("INT_KEY_height", etHeightFromView)
            commit()
        }

        //Post logger
        Log.i("MyTag", "MainActivity : Post onPause")
    }

    override fun onStart() {
        super.onStart()
        //Pre logger
        Log.i("MyTag", "MainActivity : onStart")
        //Post logger
        Log.i("MyTag", "MainActivity : Post onStart")
    }

    override fun onStop() {
        super.onStop()
        //Pre logger
        Log.i("MyTag", "MainActivity : onStop")
        //Post logger
        Log.i("MyTag", "MainActivity : Post onStop")
    }

    override fun onRestart() {
        super.onRestart()
        //Pre logger
        Log.i("MyTag", "MainActivity : onRestart")
        //Post logger
        Log.i("MyTag", "MainActivity : Post onRestart")
    }

    override fun onDestroy() {
        super.onDestroy()
        //Pre logger
        Log.i("MyTag", "MainActivity : onDestroy")
        //Post logger
        Log.i("MyTag", "MainActivity : Post onDestroy")
    }
}