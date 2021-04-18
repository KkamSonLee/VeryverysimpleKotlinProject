package com.example.englishwordproject

import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.example.englishwordproject.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    var REQUEST_CODE = 100
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        if(ActivityCompat.checkSelfPermission(this, android.Manifest.permission.CALL_PHONE)!=PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.CALL_PHONE), REQUEST_CODE)
        }else{
            init()
        }
    }

    private fun init() {
        binding.addtionbtn.setOnClickListener {
            var intent = Intent(this, AdditionActivity :: class.java)
            startActivity(intent)
        }
        binding.orderactibtn.setOnClickListener {
            var intent = Intent(this, OrderActivity :: class.java)
            startActivity(intent)
        }
        binding.searchbtn.setOnClickListener {
            var intent = Intent(this, SearchActivity :: class.java)
            startActivity(intent)
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when(requestCode){
            REQUEST_CODE ->{
                if(grantResults[0]==PackageManager.PERMISSION_GRANTED){
                    Toast.makeText(this, "permission allow", Toast.LENGTH_SHORT).show()
                }else{
                    Toast.makeText(this, "permission denied", Toast.LENGTH_SHORT).show()
                    finish()
                }
            }
        }
    }
}