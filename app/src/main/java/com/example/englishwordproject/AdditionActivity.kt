package com.example.englishwordproject

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.englishwordproject.databinding.ActivityAdditionBinding
import java.io.PrintStream

class AdditionActivity : AppCompatActivity() {

    lateinit var binding: ActivityAdditionBinding
    lateinit var input:PrintStream
    var categoryList: ArrayList<String> =
        arrayListOf("data1.txt", "data2.txt", "data3.txt", "data4.txt")   //이건 앱 내부 데이터베이스 파일 이름

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAdditionBinding.inflate(layoutInflater)
        setContentView(binding.root)
        init()


    }

    private fun init() {   
        binding.savebtn.setOnClickListener {   //해당 카테고리에 맞게 추가를 해줘야하는데 내용이 없으면 Toast
            if (binding.nameedit.text.isEmpty() || binding.priceedit.text.isEmpty() || binding.teledit.text.isEmpty()) {
                Toast.makeText(this, "내용이 충분하지 않습니다.", Toast.LENGTH_SHORT).show()
            } else {  //있으면 !!(null이 아님을 보장)한 카테고리 index를 저장
                var name = binding.nameedit.text!!
                var price = binding.priceedit.text!!
                var tel = binding.teledit.text!!
                var index:Int = 0
                when (binding.radioGroup.checkedRadioButtonId) {
                    binding.radioButton.id -> index = 0
                    binding.radioButton2.id -> index = 1
                    binding.radioButton3.id -> index = 2
                    binding.radioButton4.id -> index = 3
                }
                input = PrintStream(openFileOutput(categoryList[index], MODE_APPEND))   //파일이 이미 있으면 그 뒤에 쓴다. MODE_APPEND, MODE_PRIVATE(default)는 자기 앱에서만 사용
                input.println(name)
                input.println(price)
                input.println(tel)
                input.close()
                finishActivity(0)
            }
        }
    }
}