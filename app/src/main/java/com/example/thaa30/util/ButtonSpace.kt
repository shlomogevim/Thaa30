package com.example.thaa30.util

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.DialogInterface
import android.graphics.Color
import android.view.View
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import android.widget.EditText
import android.widget.Toast
import com.example.thaa30.R
import com.github.florent37.viewanimator.ViewAnimator
import kotlinx.android.synthetic.main.helper_view_layout.view.*
import kotlinx.android.synthetic.main.talking_details.view.*

class ButtonSpace(val view: View) : View.OnClickListener {
    val contex = view.context
    val getAndStoreData = GetAndStoreData(view)
    var talkList = getAndStoreData.getTalkingListFromPref(1)
    val animationInAction = AnimationInAction(view)

    var statrTime:Long =0
    var endTime = System.nanoTime()


    private var showPos = 1
    //var showPosition = 3


    fun talkC() = talkList[currentPage()]
    fun drawAnim() {
        if (showPos != 3) {
            updateTitleTalkerSituation()
        }
        // view.tvAnimatinKind.text = text
        val cu = currentPage()
        view.tvPage.text = cu.toString()
        talkC().numTalker = cu
        animationInAction.excuteTalker(talkC())
    }


    fun letsPlay(v: View) {

        time("let play 1")
        showPos = getAndStoreData.getShowPosition()
        if (showPos == 1) {
            when (v.id) {
                R.id.textRevBtn -> readAgainTextFile()
                R.id.newPageBtn -> enterNewPage()
                R.id.toShowModeBtn -> setShowPositionMode()
                R.id.plusAndMinusBtn -> changePlusMinusMode()
                R.id.displayAgainBtn -> drawAnim()
                R.id.saveButton -> saveIt()
                R.id.nextButton -> nextIt()
                R.id.previousButton -> previousIt()
                R.id.lastTalker_button -> retriveLastTalker()
                R.id.reSizeTextBtn -> minTextSize()
//                R.id.tvAnimatinKind -> tvAnimatinKind.visibility = View.VISIBLE
                else -> drawAnim()

            }
            return
        }
        if (showPos == 2) {
            when (v.id) {
//                R.id.plusAndMinusBtn -> initIt()
//                R.id.lastTalker_button -> setPosition(1)
//                R.id.saveButton -> setPosition(3)
//                R.id.nextButton -> nextIt()
//                R.id.tvAnimatinKind -> tvAnimatinKind.visibility = View.INVISIBLE
//                else -> moveTheAnimation()
            }
            return
        }
        if (showPos == 3) {
            when (v.id) {

                R.id.fab -> nextIt()
                R.id.fab1 -> previousIt()
                else -> drawAnim()
            }
        }
        time("let play 2")
    }

    @SuppressLint("RestrictedApi")
    private fun setShowPositionMode() {
        time("setShowPosition")

        showPos = getAndStoreData.getShowPosition()

        with(view) {
            if (showPos == 1) {
                plusAndMinusBtn.text = "+"
                lastTalker_button.text = "Last"
                saveButton.text = "Save"
                upper_layout.visibility = VISIBLE
                style_ListView.visibility = VISIBLE
                para_ListView.visibility = VISIBLE
                ttPara_listView.visibility = VISIBLE
                action_ListView.visibility = VISIBLE
                tvAnimatinKind.visibility = VISIBLE
                fab.visibility = INVISIBLE
                fab1.visibility = INVISIBLE

            }
            if (showPos == 2) {
                plusAndMinusBtn.text = "Start"
                lastTalker_button.text = "Test"
                saveButton.text = "PUB"
                upper_layout.visibility = INVISIBLE
                style_ListView.visibility = INVISIBLE
                para_ListView.visibility = INVISIBLE
                ttPara_listView.visibility = INVISIBLE
                action_ListView.visibility = INVISIBLE
                tvAnimatinKind.visibility = INVISIBLE
            }
            if (showPos == 3) {

                down_layout.visibility = INVISIBLE
                upper_layout.visibility = INVISIBLE
                style_ListView.visibility = INVISIBLE
                para_ListView.visibility = INVISIBLE
                ttPara_listView.visibility = INVISIBLE
                action_ListView.visibility = INVISIBLE
                tvAnimatinKind.visibility = INVISIBLE
                fab.visibility = VISIBLE
                fab1.visibility = VISIBLE

            }
        }

    }

    private fun readAgainTextFile() {
        val textTalkList = getAndStoreData.createTalkListFromTheStart()
        talkList = textReRead(talkList, textTalkList)
        getAndStoreData.saveTalkingListInPref(talkList)
    }

    fun textReRead(
        talkList: ArrayList<Talker>,
        textTalkList: ArrayList<Talker>
    ): ArrayList<Talker> {
        for (index in 0..talkList.size - 1) {
            val st1 = textTalkList[index].taking
            var arr = st1.split("\n")
            val ar = arrayListOf<String>()
            for (item in arr) {
                if (item != "") {
                    ar.add(item)
                }
            }

            if (index > talkList.size) {
                var talk1 = textTalkList[index].copy()
                talkList.add(talk1)

            } else {

                talkList[index].takingArray = ar
                talkList[index].taking = textTalkList[index].taking
            }

            if (index == textTalkList.size - 1) {
                talkList.dropLast(talkList.size - textTalkList.size)
                return talkList
            }
        }
        return talkList
    }


    private fun minTextSize() {
        updateLastTalker(0)
        talkC().textSize = 12f  // for accsident of bigest text
        drawAnim()
    }

    private fun enterNewPage() {

        var myDialog = AlertDialog.Builder(contex)

        val input = EditText(contex)
        myDialog.setView(input)
        myDialog.setTitle("Enter new page")
        myDialog.setPositiveButton("OK", object : DialogInterface.OnClickListener {
            override fun onClick(p0: DialogInterface?, p1: Int) {
                val num = input.text.toString().toInt()
                getAndStoreData.saveCurrentPage(num)
                drawAnim()
                return
            }

        })
        myDialog.setNegativeButton("CANCEL", null)
        myDialog.show()
    }

    private fun retriveLastTalker() {
        updateLastTalker(1)
        drawAnim()
    }

    private fun updateLastTalker(ind: Int) {
        with(getAndStoreData) {
            if (ind == 0) {
                saveLastTalker(talkC())
            } else {
                talkList[currentPage()] = getLastTalker().copy()
            }
        }
    }

    fun saveIt() {
        getAndStoreData.saveTalkingListInPref(talkList)
        Toast.makeText(contex, "It's save Mr", Toast.LENGTH_SHORT).show()
    }

    fun updateTitleTalkerSituation() {
        with(talkC()) {
            val text =
                "l=${takingArray.size}sty=$styleNum anim=$animNum size=${textSize.toInt()}" +
                        " bord=$borderWidth dur=$dur sw=$swingRepeat"

        }

    }

    fun previousIt() {
        var cu = currentPage()
        cu--
        getAndStoreData.saveCurrentPage(cu)
        drawAnim()
    }

    fun nextIt() {
        updateLastTalker(0)
        var cu = currentPage()
        cu++
        getAndStoreData.saveCurrentPage(cu)
        drawAnim()
    }

    private fun changePlusMinusMode() {
        with(view.plusAndMinusBtn) {
            text = (if (text == "+") "-" else "-")
        }
    }

    private fun time(st: String) {
        endTime = System.nanoTime()
//        val interval = TimeUnit.MILLISECONDS.convert(endTime - statrTime, TimeUnit.NANOSECONDS)
//        Log.d("clima", st + " --> $interval ms")

    }

    override fun onClick(view: View) {
        statrTime = System.nanoTime()
        time("onClick 0")
        showPos = getAndStoreData.getShowPosition()
        if (showPos != 3) {
            onClickOther()
            return
        }
        time("onClick 1")
        var def=0

        when (view.id) {
            R.id.fab -> def++
            R.id.fab1 -> def--
        }
        buttonActivation(0)

        var counterStep = currentPage()+def
        time("onClick 2")

        if (counterStep < 1) counterStep = 1
        if (counterStep == talkList.size) counterStep = 1
        getAndStoreData.saveCurrentPage(counterStep)

        time("onClick 3")



        chageBackgroundColor(1, 1000)

        time("onClick 4")

        letsPlay(view)

        time("onClick 5")

        val size = talkC().takingArray.size

        Utile.listener1 = { it1, _ ->
            // Log.d("clima", "Hii num->$it1 and time->$it2 and size=$size")
            if (size == 1 || it1 == size) {
                time("onClick 6")
                buttonActivation(1)
                chageBackgroundColor(0, 1000)
            }
        }
        time("onClick 7")
    }

    private fun onClickOther() {
        var counterStep = currentPage()
        if (showPos == 3) {
            when (view.id) {
                R.id.fab -> counterStep++
                R.id.fab1 -> counterStep--
            }
        }
        if (counterStep < 1) counterStep = 1
        if (counterStep == talkList.size) counterStep = 1
        getAndStoreData.saveCurrentPage(counterStep)

        if (showPos == 3) {
            time("onClickA113")
            buttonActivation(0)

        }

        chageBackgroundColor(1, 1000)

        letsPlay(view)

        val size = talkC().takingArray.size

        Utile.listener1 = { it1, _ ->
            // Log.d("clima", "Hii num->$it1 and time->$it2 and size=$size")
            if (size == 1 || it1 == size) {
                time("onClickB114")
                buttonActivation(1)
                chageBackgroundColor(0, 1000)
            }
        }
    }

    fun currentPage(): Int {
        var cu = getAndStoreData.getCurrentPage()
        if (cu < 1 || cu >= talkList.size) {
            cu = 1
            getAndStoreData.saveCurrentPage(cu)
        }
        return cu
    }

    fun chageBackgroundColor(ind: Int, dur: Long) {
        if (ind == 0) {
            ViewAnimator
                .animate(view.tvPage)
                .backgroundColor(Color.RED, Color.GREEN)
                .duration(dur)
                .start()

        } else {
            ViewAnimator
                .animate(view.tvPage)
                .backgroundColor(Color.GREEN, Color.RED)
                .duration(dur)
                .start()
        }
    }

    fun fabAnimation(ind: Int) {
        if (ind == 0) {
            ViewAnimator
                .animate(view.fab)
                .alpha( 0f)
                .andAnimate(view.fab1)
                .alpha( 0f)
                .duration(2000)
                .start()
        } else {
            ViewAnimator
                .animate(view.fab)
                .alpha(0f, 1f)
                .andAnimate(view.fab1)
                .alpha(0f, 1f)
                .duration(2000)
                .start()
        }
    }

    @SuppressLint("RestrictedApi")
    fun buttonActivation(ind: Int) {
        time("buttonActivation 1 ind=$ind")

        showPos = getAndStoreData.getShowPosition()
        with(view) {
            if (ind == 0) {
                if (showPos == 3) {
                    fab.isClickable = false
                    fab1.isClickable = false
                    fabAnimation(0)
                } else {
                    textRevBtn.visibility = INVISIBLE
                    newPageBtn.visibility = INVISIBLE
                    toShowModeBtn.visibility = INVISIBLE
                    plusAndMinusBtn.visibility = INVISIBLE
                    displayAgainBtn.visibility = INVISIBLE
                    saveButton.visibility = INVISIBLE
                    nextButton.visibility = INVISIBLE
                    previousButton.visibility = INVISIBLE
                    lastTalker_button.visibility = INVISIBLE
                    reSizeTextBtn.visibility = INVISIBLE
                }
            }
            if (ind == 1) {
                if (showPos == 3) {
                    fab.isClickable = true
                    fab1.isClickable = true
                    fabAnimation(1)
                } else {
                    textRevBtn.visibility = VISIBLE
                    newPageBtn.visibility = VISIBLE
                    toShowModeBtn.visibility = VISIBLE
                    plusAndMinusBtn.visibility = VISIBLE
                    displayAgainBtn.visibility = VISIBLE
                    saveButton.visibility = VISIBLE
                    nextButton.visibility = VISIBLE
                    previousButton.visibility = VISIBLE
                    lastTalker_button.visibility = VISIBLE
                    reSizeTextBtn.visibility = VISIBLE
                }
            }
        }
        time("buttonActivation 2 ind=$ind")
    }

    fun initButton() {
        with(view) {
            displayAgainBtn.setOnClickListener { onClick(displayAgainBtn) }
            textRevBtn.setOnClickListener { onClick(textRevBtn) }
            newPageBtn.setOnClickListener { onClick(newPageBtn) }
            plusAndMinusBtn.setOnClickListener { onClick(plusAndMinusBtn) }
            saveButton.setOnClickListener { onClick(saveButton) }
            nextButton.setOnClickListener { onClick(nextButton) }
            previousButton.setOnClickListener { onClick(previousButton) }
            lastTalker_button.setOnClickListener { onClick(lastTalker_button) }
            reSizeTextBtn.setOnClickListener { onClick(reSizeTextBtn) }
            toShowModeBtn.setOnClickListener { onClick(toShowModeBtn) }
            fab.setOnClickListener { onClick(fab) }
            fab1.setOnClickListener { onClick(fab1) }
        }
    }
}