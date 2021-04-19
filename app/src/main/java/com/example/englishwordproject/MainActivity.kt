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
        binding = ActivityMainBinding.inflate(layoutInflater)    //binding 쓰려면 꼭 해야한다....
        setContentView(binding.root)               //여튼 권한 승인도 결국엔 Manifests에 uses-permission이 있어야한다.
        if(ActivityCompat.checkSelfPermission(this, android.Manifest.permission.CALL_PHONE)!=PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.CALL_PHONE), REQUEST_CODE)
        }else{
            init()
        }// 권한승인 체크 후 없으면 승인 요청, 결과는 onRequest로 확인
    }

    private fun init() {
        binding.addtionbtn.setOnClickListener {
            var intent = Intent(this, AdditionActivity :: class.java)
            intent.putExtra("addtion", "addtion!")
            startActivity(intent)
        }
        binding.orderactibtn.setOnClickListener {
            var intent = Intent(this, OrderActivity :: class.java)
            intent.putExtra("order", "order!")
            startActivity(intent)
        }
        binding.searchbtn.setOnClickListener {
            var intent = Intent(this, SearchActivity :: class.java)
            intent.putExtra("search", "search!")
            startActivity(intent)
        }
    }

    override fun onRequestPermissionsResult(//승인됐으면 승인 됐다고 처리해주는 코드, 승인 안되면 앱 꺼버림
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