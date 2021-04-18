package com.example.englishwordproject

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.englishwordproject.databinding.ActivitySearchBinding
import java.util.*
import kotlin.collections.ArrayList

class SearchActivity : AppCompatActivity() {



    lateinit var myadapter: Myadapter
    lateinit var binding: ActivitySearchBinding
    var data1: ArrayList<Mydata> = ArrayList()
    var data2: ArrayList<Mydata> = ArrayList()
    var data3: ArrayList<Mydata> = ArrayList()
    var data4: ArrayList<Mydata> = ArrayList()
    var total_Data: ArrayList<Mydata> = ArrayList()
    var temp_Data: ArrayList<Mydata> = ArrayList()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initData()   //내부, 외부 데이터를 받아온다.
        init()       //리싸이클러뷰, 검색 기능 init
    }

    private fun init() {
        total_Data.addAll(data1)
        total_Data.addAll(data2)
        total_Data.addAll(data3)
        total_Data.addAll(data4)
        temp_Data.addAll(total_Data)    //data1~4까지 뭉쳐버린다.
        binding.searchrecyclerview.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

        binding.searchBtn.setOnClickListener {
            var str = binding.searchEdit.text
            total_Data.clear()
            total_Data.addAll(temp_Data)
            val iterator = total_Data.iterator()   //항목 지우거나 할 때는 iterator를 써야한다.
            while(iterator.hasNext()) {
                var it = iterator.next()
                if(!it.name.contains(str)){
                    iterator.remove()      //나는 remove를 선택했다.
                }else{
                    Log.e("find!", "value!")
                }
            }
            binding.searchEdit.text.clear()
            myadapter.notifyDataSetChanged()
        }
        // 관련 리스너가 끝나고 나서 recyclerview와 adapter를 연결해준다.
        myadapter = Myadapter(total_Data)
        binding.searchrecyclerview.adapter = myadapter

    }


    private fun initData() {   //내부는 try로 시도, 외부는 그냥 가져옴(resources.openRawResource(R.raw.data)
        try {
            val scan_1 = Scanner(openFileInput("data1.txt"))
            readfile(scan_1, data1)
        } catch (e: Exception) {
            Log.e("data1", "error")
        }
        val scan = Scanner(resources.openRawResource(R.raw.data1))
        readfile(scan, data1)
        try {
            val scan_2 = Scanner(openFileInput("data2.txt"))
            readfile(scan_2, data2)
        } catch (e: Exception) {
            Log.e("data2", "error")
        }
        val scan2 = Scanner(resources.openRawResource(R.raw.data2))
        readfile(scan2, data2)
        try {
            val scan_3 = Scanner(openFileInput("data3.txt"))
            readfile(scan_3, data3)
        } catch (e: Exception) {
            Log.e("data3", "error")
        }
        val scan3 = Scanner(resources.openRawResource(R.raw.data3))
        readfile(scan3, data3)
        try {
            val scan_4 = Scanner(openFileInput("data4.txt"))
            readfile(scan_4, data4)
        } catch (e: Exception) {
            Log.e("data4", "error")
        }
        val scan4 = Scanner(resources.openRawResource(R.raw.data4))
        readfile(scan4, data4)
    }

    private fun readfile(scan: Scanner, data: ArrayList<Mydata>) {   //데이터 형식이 3개라는 것이다.
        while (scan.hasNextLine()) {
            var name = scan.nextLine()
            var price = scan.nextLine()
            var tel = scan.nextLine()
            data.add(Mydata(name, price, tel, false))
        }


    }
}