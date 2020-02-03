package com.example.thaa30.util

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Base64
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.thaa30.util.Const.Companion.ASSEETS_FILE
import com.example.thaa30.util.Const.Companion.CURRENT_PAGE
import com.example.thaa30.util.Const.Companion.FILE_NUM
import com.example.thaa30.util.Const.Companion.LASTTALKER
import com.example.thaa30.util.Const.Companion.LAST_PAGE
import com.example.thaa30.util.Const.Companion.PREFS_NAME
import com.example.thaa30.util.Const.Companion.SHOWPOSITION
import com.example.thaa30.util.Const.Companion.TALKLIST
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.ByteArrayOutputStream

class GetAndStoreData(view:View) : AppCompatActivity() {

    val contex=view.context

    var myPref = contex.getSharedPreferences(PREFS_NAME, 0)


    fun saveCurrentPage(index: Int) {myPref.edit().putInt(CURRENT_PAGE, index).apply()}
    fun saveLastPage(index: Int) {myPref.edit().putInt(LAST_PAGE, index).apply()}
    fun saveCurrentFile(index: Int) {myPref.edit().putInt(FILE_NUM, index).apply()}
   fun saveShowPosition(index: Int) {myPref.edit().putInt(SHOWPOSITION, index).apply()}




    fun getCurrentPage(): Int = myPref.getInt(CURRENT_PAGE, 1)
    fun getLastPage(): Int = myPref.getInt(LAST_PAGE, 1)
    fun getCurrentFile(): Int = myPref.getInt(FILE_NUM, 1)
    fun getShowPosition(): Int = myPref.getInt(SHOWPOSITION, 1)


    private fun decodebase64(input:String):Bitmap{
        val decodeByte=Base64.decode(input,0)
        val bit=BitmapFactory.decodeByteArray(decodeByte,0,decodeByte.size)
        return bit
    }



    private fun encodeToBase64(image:Bitmap):String{
        val immage=image
        val baos=ByteArrayOutputStream()
        immage.compress(Bitmap.CompressFormat.PNG,100,baos)
        val b=baos.toByteArray()
        val imageEncoded=Base64.encodeToString(b,Base64.DEFAULT)
        Log.d("clima","imageEncode->$imageEncoded")
        return imageEncoded
    }

    fun saveTalkingListInPref(talkingList: ArrayList<Talker>) {
        val gson = Gson()
        val tagNum=getCurrentFile()
        val jsonString = gson.toJson(talkingList)
        myPref.edit().putString(TALKLIST+tagNum.toString(), jsonString).apply()
    }
    fun saveLastTalker(lastTalker: Talker) {
        val gson = Gson()
        val jsonString = gson.toJson(lastTalker)
        myPref.edit().putString(LASTTALKER, jsonString).apply()
    }

    fun getLastTalker():Talker{
        var talker=Talker()
        var jsonS=myPref.getString(LASTTALKER,null)
        if (jsonS!=null){
            val gson=Gson()
            val type = object : TypeToken<Talker>() {}.type
            talker = gson.fromJson(jsonS, type)
        }
        return talker
    }


    fun createTalkListFromPref(): ArrayList<Talker> {
        var talkList1 = ArrayList<Talker>()
        val tagNum=getCurrentFile()

        var jsonS =  myPref.getString(TALKLIST+tagNum.toString(), null)
        if (jsonS != null) {
            val gson = Gson()
            val type = object : TypeToken<ArrayList<Talker>>() {}.type
            talkList1 = gson.fromJson(jsonS, type)
            //saveTalkingListInPref(talkList1)
        }
        return talkList1
    }







    fun saveJsonString(jsonS: String?) {
        myPref.edit().putString(TALKLIST, jsonS).apply()
    }

    fun getJsonArryFromPref( ): ArrayList<Talker> {
        var list= ArrayList<Talker>()
        var jsonS: String?
        jsonS = myPref.getString(TALKLIST, null)
        if (!jsonS.isNullOrEmpty()){
            val gson = Gson()
            val type = object : TypeToken<ArrayList<Talker>>() {}.type
            list = gson.fromJson(jsonS, type)
        }
        return list
    }


    fun createTalkList(): ArrayList<Talker> {
        var talkList: ArrayList<Talker>
        val jsonString = myPref.getString(TALKLIST, null)

        // val jsonString = intent.getStringExtra(JSONSTRING)
        // val jsonString = intent.getStringExtra(JSONSTRING)
        if (jsonString == "none" || jsonString == "") {
            talkList = getTalkingListFromPref(0)
            saveTalkingListInPref(talkList)

        } else {
            val gson = Gson()
            val type = object : TypeToken<ArrayList<Talker>>() {}.type
            talkList = gson.fromJson(jsonString, type)
            saveTalkingListInPref(talkList)
        }
        return talkList
    }





    fun getTalkingListFromPref(ind: Int): ArrayList<Talker> {
        val talkList1: ArrayList<Talker>
        val gson = Gson()
        val jsonString = myPref.getString(TALKLIST, null)

        if (ind == 0 || jsonString == null) {
            talkList1 = createTalkListFromTheStart()
            saveTalkingListInPref(talkList1)

        } else {
            val type = object : TypeToken<ArrayList<Talker>>() {}.type
            talkList1 = gson.fromJson(jsonString, type)
        }
        return talkList1
    }

    fun createTalkListFromTheStart(): ArrayList<Talker> {
        var talkList1 = arrayListOf<Talker>()
        val ADAM = "-אדם-"
        val GOD = "-אלוהים-"
        val currenteFile = "text/text" + ASSEETS_FILE.toString() + ".txt"

        var countItem = 0
        var text = contex.assets.open(currenteFile).bufferedReader().use {
            it.readText()
        }
        text = text.replace("\r", "")
        var list1 = text.split(ADAM)

        var talker = Talker()

        talkList1.add(countItem, talker)
        var i = 0

        for (element in list1) {
            //  if (element != "" && element.length > 25) {
            if (element != "") {
                i++
                var list2 = element.split(GOD)
                var st1 = improveString(list2[0])
                var st2 = improveString(list2[1])
                if (st1.isNullOrEmpty() || st2.isNullOrEmpty()) {
                    return talkList1
                }
                countItem++
                talker = Talker()
                with(talker) {
                    whoSpeake = "man"
                    taking = st1.trim()
                    numTalker = countItem
                    var arr = st1.split("\n")
                    for (item in arr) {
                        if (item != "") {
                            takingArray.add(item)
                        }
                    }
                    colorText = "#000000"
                    colorBack = "#ffffff"
                    animNum = 10
                }

                talkList1.add(talker)

                countItem++
                talker = Talker()
                with(talker) {
                    whoSpeake = "god"
                    talker.taking = st2.trim()
                    talker.numTalker = countItem
                    var arr = st2.split("\n")
                    for (item in arr) {
                        if (item != "") {
                            takingArray.add(item)
                        }
                    }
                    colorText = "#000000"
                    colorBack = "#ffffff"
                    animNum = 10
                }
                talkList1.add(talker)
            }
        }
        return talkList1
    }

    private fun improveString(st: String) = st.substring(1, st.length - 1)

    private fun createTalkArray(jsonString: String?) {
        var talkList: ArrayList<Talker>
        //  Log.d("clima",jsonString)
        val gson = Gson()
        val type = object : TypeToken<ArrayList<Talker>>() {}.type
        talkList = gson.fromJson(jsonString, type)
        Log.d("clima", "${talkList[19].taking}")
    }

}