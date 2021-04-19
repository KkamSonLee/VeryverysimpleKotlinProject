package com.example.englishwordproject

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
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
        val str = intent.getStringExtra("time")
        val massage = intent.getStringExtra("search")
        if(massage!= null){
            Toast.makeText(this, massage, Toast.LENGTH_SHORT).show()
        }
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

            var iterator = temp_Data.iterator()
            iterator.forEach {
                if(it.name.contains(str)){
                    total_Data.add(it)
                }
            }
            myadapter.notifyDataSetChanged()
            binding.searchEdit.text.clear()
        }

        myadapter = Myadapter(total_Data)
        myadapter.itemOnClickListener = object : Myadapter.OnItemClickListener{
            override fun OnItemClick(
                holder: Myadapter.ViewHolder,
                view: View,
                data: Mydata,
                position: Int
            ) {
                makenotification(data)
            }

        }
        // 관련 리스너가 끝나고 나서 recyclerview와 adapter를 연결해준다.
        binding.searchrecyclerview.adapter = myadapter
        val simpleCallback = object : ItemTouchHelper.SimpleCallback(ItemTouchHelper.DOWN or ItemTouchHelper.UP, ItemTouchHelper.RIGHT) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                myadapter.move(viewHolder.adapterPosition, target.adapterPosition)
                return true
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                myadapter.remove(viewHolder.adapterPosition)

            }

        }
        val itemTouchHelper = ItemTouchHelper(simpleCallback)
        itemTouchHelper.attachToRecyclerView(binding.searchrecyclerview)
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

    fun makenotification(data: Mydata) {
        val id = "MyChannel"
        val name = "TimeCheckChannel"
        val notificationChannel = NotificationChannel(id, name, NotificationManager.IMPORTANCE_DEFAULT)
        notificationChannel.enableVibration(true)
        notificationChannel.enableLights(true)
        notificationChannel.lightColor = Color.BLUE
        notificationChannel.lockscreenVisibility = Notification.VISIBILITY_PRIVATE

        val builder = NotificationCompat.Builder(this, id)
            .setSmallIcon(R.drawable.hat)
            .setContentTitle("일정 알림")
            .setContentText(data.name)
            .setAutoCancel(true)
        val intent = Intent(this, SearchActivity::class.java)
        intent.putExtra("time", data.name)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP

        val pendingIntent = PendingIntent.getActivity(this, 1, intent, PendingIntent.FLAG_UPDATE_CURRENT)
        builder.setContentIntent(pendingIntent)

        val manager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager //as로 매니저 객체 생성
        val notification = builder.build()
        manager.createNotificationChannel(notificationChannel)
        manager.notify(10, notification)
    }

}