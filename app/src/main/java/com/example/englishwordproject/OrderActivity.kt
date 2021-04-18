package com.example.englishwordproject

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.Button
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.util.*
import kotlin.collections.ArrayList

class OrderActivity : AppCompatActivity() {
    lateinit var myadapter: Myadapter
    lateinit var addmyadapter: Myadapter
    lateinit var recyclerView: RecyclerView
    lateinit var addrecyclerView: RecyclerView
    lateinit var myspinner: Spinner
    lateinit var orderBtn: Button
    var data1: ArrayList<Mydata> = ArrayList()
    var data2: ArrayList<Mydata> = ArrayList()
    var data3: ArrayList<Mydata> = ArrayList()
    var data4: ArrayList<Mydata> = ArrayList()   // 카테고리별 데이터들
    var total_Data: ArrayList<Mydata> = ArrayList()
    var add_Data: ArrayList<Mydata> = ArrayList()
    var totalPrice = 0          // 주문하기 누르면 전체 값 저장 할 친구
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.orderactivity_main)
        initData()
        initRecyclerView()
    }

    private fun initRecyclerView() {
        orderBtn = findViewById(R.id.orderbtn)
        myspinner = findViewById(R.id.spinner)
        recyclerView = findViewById(R.id.recyclerview)
        addrecyclerView = findViewById(R.id.addrecyclerview)

        myspinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {        //스피너 생성은 기억이 안날수도 있으니 한 번 봐라.
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                total_Data.clear()
                when (position) {   //해당 카테고리 포지션에 따라 다른 데이터를 total_Date에 추가했다.(전자레인지 그런거)
                    0 -> total_Data.addAll(data1)
                    1 -> total_Data.addAll(data2)
                    2 -> total_Data.addAll(data3)
                    3 -> total_Data.addAll(data4)
                }
                myadapter.notifyDataSetChanged()  //데이터 바꾸면 알려줘야지?
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                if (total_Data.isEmpty()) {
                    total_Data.addAll(data1)
                }
            }

        }

        if (total_Data.isEmpty()) {
            total_Data.addAll(data1)        //defualt는 전자레인지다.
        }
        myadapter = Myadapter(total_Data)
        addmyadapter = Myadapter(add_Data)  //이거는 처음에 비어있다.
        addrecyclerView.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        myadapter.itemOnClickListener = object : Myadapter.OnItemClickListener {
            override fun OnItemClick(
                holder: Myadapter.ViewHolder,
                view: View,
                data: Mydata,
                position: Int
            ) {
                if (!data.isChecked) {
                    data.isChecked = true
                    add_Data.add(data)
                    addmyadapter.notifyDataSetChanged()
                }
            }

        }
        addmyadapter.itemOnClickListener = object : Myadapter.OnItemClickListener {
            override fun OnItemClick(
                holder: Myadapter.ViewHolder,
                view: View,
                data: Mydata,
                position: Int
            ) {
                val telnumber = Uri.parse("tel:" + data.tel)
                val callintent = Intent(Intent.ACTION_CALL, telnumber)
                startActivity(callintent)
            }

        }
        orderBtn.setOnClickListener {     //볼거면 이쪽 보셔, 나름 유용한 부분
            totalPrice = 0
            add_Data.forEach {
                totalPrice += it.price.toInt()
            }
            Toast.makeText(this, totalPrice.toString(), Toast.LENGTH_SHORT).show()
        }
        recyclerView.adapter = myadapter
        addrecyclerView.adapter = addmyadapter

    }

    private fun initData() {
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

    private fun readfile(scan: Scanner, data: ArrayList<Mydata>) {
        while (scan.hasNextLine()) {
            var name = scan.nextLine()
            var price = scan.nextLine()
            var tel = scan.nextLine()
            data.add(Mydata(name, price, tel, false))
        }


    }
}