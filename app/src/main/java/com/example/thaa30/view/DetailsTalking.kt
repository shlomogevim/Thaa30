package com.example.thaa30.view

import android.annotation.SuppressLint
import android.graphics.drawable.AnimationDrawable
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.thaa30.R
import com.example.thaa30.util.*
import com.github.florent37.viewanimator.ViewAnimator
import com.google.firebase.firestore.FirebaseFirestore
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.android.synthetic.main.talking_details.*
import kotlinx.android.synthetic.main.talking_details.view.*


class DetailsTalking : Fragment() {

    var conv: Convers? = null
    lateinit var getStoreData: GetAndStoreData
    lateinit var animationInAction: AnimationInAction
    lateinit var getAndStoreData: GetAndStoreData
    lateinit var arrangeLayout: ArrangeLayout
    lateinit var buttonSpace: ButtonSpace
    lateinit var talkList: ArrayList<Talker>
    var showPosition = 1

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.talking_details, container, false)
    }

    fun talkC() = talkList[getAndStoreData.getCurrentPage()]


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.let {
          //  conv = DetailsTalkingArgs.fromBundle(it).currentConvers
            conv = DetailsTalkingArgs.fromBundle(it).currentConvers
            // tv.text=" Hi Man You SeleT Item num "+ conv!!.numC
        }



        getStoreData = GetAndStoreData(view)
        animationInAction = AnimationInAction(view)
        getAndStoreData = GetAndStoreData(view)

        getAndStoreData.saveCurrentFile(20)

        getTalkList()

        arrangeLayout = ArrangeLayout(view)
        buttonSpace = ButtonSpace(view)

        backGroundConfigration()

        arrangeLayout.drawListView()

        arrangeLayout.operateListView()

        buttonSpace.initButton()

        showPosition = 3

        getAndStoreData.saveShowPosition(showPosition)

        //  getAndStoreData.saveCurrentPage(1)

        arrangeLayout.setLayoutShowMode()
        waitToAnnimateEnded()

        animationInAction.excuteTalker(talkC())

    }

    @SuppressLint("RestrictedApi")
    private fun waitToAnnimateEnded() {
        Utile.listener1 = { it1, _ ->

            fab.visibility = View.VISIBLE
            fab1.visibility = View.VISIBLE
            ViewAnimator
                .animate(view?.fab)
                .alpha(0f, 1f)
                .andAnimate(view?.fab1)
                .alpha(0f, 1f)
                .duration(3500)
                .start()
        }

    }

    fun backGroundConfigration() {
        val animationDrawable = imageView.background as? AnimationDrawable
        animationDrawable?.setEnterFadeDuration(2000)
        animationDrawable?.setExitFadeDuration(4000)
        animationDrawable?.start()
    }

    private fun getTalkList() {
        talkList = getStoreData.createTalkListFromPref()

        if (talkList.size == 0) {
            createTalkingListFromFirestore()  //open tool->firebase->firestore see if all depen. ok
            //rebuilt project
            // run and wait for result
        }
        if (talkList.size == 0) {
            // !! must be in remarked becaseus it inteferring to the firebase
            //  talkList=getStoreData.createTalkListFromTheStart()

        }
        getAndStoreData.saveTalkingListInPref(talkList)
    }

    fun createTalkingListFromFirestore(): ArrayList<Talker> {
        var talkList1 = ArrayList<Talker>()
        var jsonS: String
        /*val st="courses"
        val st1="11"
        val st2="name"*/
        val st = "talker1"
        val st1 = "3"
        val st2 = "main"
        var db = FirebaseFirestore.getInstance()
        db.collection(st).document(st1).get().addOnCompleteListener { task ->
            if (task.result!!.exists()) {
                jsonS = task.result?.getString(st2)!!
                val gson = Gson()
                val type = object : TypeToken<ArrayList<Talker>>() {}.type
                talkList1 = gson.fromJson(jsonS, type)
                getStoreData.saveTalkingListInPref(talkList1)
                Log.d("clima", " $jsonS")
            }
        }
        return talkList1
    }


    fun storeTalkingListFromFirestore(talkList: ArrayList<Talker>, index: Int) {

        // must transfer value with  key-value format
        val st = "talker1"
        val st1 = index.toString()
        val gson = Gson()
        val jsonS = gson.toJson(talkList)
        var db = FirebaseFirestore.getInstance()
        var talker = HashMap<String, Any>()
        talker.put("index", st1)
        jsonS?.let { talker.put("main", it) }
        db.collection(st).document(st1).set(talker)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Toast.makeText(context, "Saving is succsses", Toast.LENGTH_LONG).show()
                } else {
                    Toast.makeText(
                        context,
                        "Not Save because \${task.exception?.message",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }


    }
}


