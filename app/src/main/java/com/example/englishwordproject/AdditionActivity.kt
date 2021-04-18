package com.example.englishwordproject

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.englishwordproject.databinding.ActivityAdditionBinding
import java.io.File
import java.io.PrintStream

class AdditionActivity : AppCompatActivity() {

    lateinit var binding: ActivityAdditionBinding
    lateinit var file: File
    var category: String = ""
    lateinit var input:PrintStream
    var categoryList: ArrayList<String> =
        arrayListOf("data1.txt", "data2.txt", "data3.txt", "data4.txt")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAdditionBinding.inflate(layoutInflater)
        setContentView(binding.root)
        init()


    }

    private fun init() {
        binding.savebtn.setOnClickListener {
            if (binding.nameedit.text.isEmpty() || binding.priceedit.text.isEmpty() || binding.teledit.text.isEmpty()) {
                Toast.makeText(this, "내용이 충분하지 않습니다.", Toast.LENGTH_SHORT).show()
            } else {
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
                val data = Mydata(name.toString(), price.toString(), tel.toString(), false)
                input = PrintStream(openFileOutput(categoryList[index], MODE_APPEND))
                input.println(name)
                input.println(price)
                input.println(tel)
                input.close()
                finishActivity(0)
            }
        }
    }
}