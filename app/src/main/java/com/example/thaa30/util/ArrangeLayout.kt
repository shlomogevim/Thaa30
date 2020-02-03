package com.example.thaa30.util

import android.R
import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.DialogInterface
import android.graphics.Color
import android.view.View
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.Toast
import kotlinx.android.synthetic.main.helper_view_layout.view.*
import kotlinx.android.synthetic.main.talking_details.view.*


class ArrangeLayout(val view: View) {

    val contex = view.context

    var styleList = arrayListOf<String>()
    var paraList = arrayListOf<String>()
    var ttParaList = arrayListOf<String>()
    var actionList = arrayListOf<String>()

    val getAndStoreData = GetAndStoreData(view)
    val talkList = getAndStoreData.getTalkingListFromPref(1)
    val helper = Helper(contex)
    val animationInAction = AnimationInAction(view)

    private var interval = 0
    private var currentColor = "#stam"

    var showPosition = 1

    fun talkC() = talkList[currentPage()]

    fun drawAnim() {
        updateTitleTalkerSituation()
        animationInAction.excuteTalker(talkC())
    }

    fun operateListView() {


        operateStyleLV()

        patamListView()

        ttParaListView()

        animationMovmentListView()

        updatePage(0)
        updateTitleTalkerSituation()
    }

    private fun animationMovmentListView() {  // list view in the right side
        createAnimLV()
        view.action_ListView.setOnItemClickListener { _, _, position, _ ->
            talkC().animNum = actionList[position].toInt()
            moveTheAnimation()
        }
    }

    private fun patamListView() {
        view.para_ListView.setOnItemClickListener { _, _, position, _ ->
            //tranferTalkItem(0)
            translaePara(position)
        }
    }

    private fun translaePara(position: Int) {

        val talker = talkC()
        val s = view.plusAndMinusBtn.text
        var intv = if (s == "+") interval else -interval

        when (position) {

            7 -> initIt()
            10 -> enterNewPage()
            11 -> copyTalker(1)
            15 -> talker.textSize = talker.textSize + intv
            16 -> talker.dur = talker.dur + intv
            19 -> changeTextColor(talker)
            20 -> changeBackColor(talker)
            21 -> changeBorderColor(talker)
            22 -> changeBorderWidth(talker, intv)
            23 -> talker.borderWidth = 0
            24 -> changeSwingRepeat(talker, intv)
            25 -> changeRadius(talker, intv)
        }
        chkNewData(talker)
        if (position != 10) moveTheAnimation()
        updateTitleTalkerSituation()
    }


    fun copyTalker(modelNum: Int) {
        var spicalTalkList = arrayListOf(
            Talker(
                numTalker = 1, styleNum = 411, animNum = 61, textSize = 288f, dur = 3000
            ) // god "YES"
        )
        if (modelNum == 1 && talkC().whoSpeake == "man") return
        var bo = true
        var i = 0
        while (bo && i < spicalTalkList.size) {

            if (spicalTalkList[i].numTalker == modelNum) {
                val spcialTalk = spicalTalkList[i]
                with(talkC()) {
                    styleNum = spcialTalk.styleNum
                    animNum = spcialTalk.animNum
                    textSize = spcialTalk.textSize
                    dur = spcialTalk.dur
                    val style = findStyleObject(spcialTalk.styleNum)
                    colorText = style.colorText
                    colorBack = style.colorBack
                }
                bo = false
            }
        }

    }

    fun initIt() {
        getAndStoreData.saveCurrentPage(1)
        moveTheAnimation()
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


    private fun chkNewData(talker: Talker) {
        with(talker) {
            if (textSize > 300f) textSize = 300f
            if (textSize < 8f) textSize = 8f
            if (dur > 10000f) dur = 10000
            if (dur < 100f) dur = 100
            //  if (radius > 100f) radius = 100f
            if (radius < 2f) radius = 2f
            if (borderWidth > 70) borderWidth = 70
            if (borderWidth < 0) borderWidth = 0
            if (swingRepeat > 10) swingRepeat = 10
            if (swingRepeat < 0) swingRepeat = 0
        }
    }

    private fun changeSwingRepeat(talker: Talker, intv: Int) {
        talker.swingRepeat = talker.swingRepeat + intv
    }

    private fun changeRadius(talker: Talker, intv: Int) {
        talker.radius = talker.radius + intv

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

    private fun changeBorderColor(talker: Talker) {
        try {
            Color.parseColor(currentColor)
        } catch (iae: IllegalArgumentException) {
            Toast.makeText(contex, "IIIigal color entery , try again", Toast.LENGTH_LONG).show()
            return
        }

        talker.borderColor = currentColor

    }

    private fun changeBorderWidth(talker: Talker, intv: Int) {
        talker.borderWidth = talker.borderWidth + intv

    }

    private fun changeBackColor(talker: Talker) {
        try {
            Color.parseColor(currentColor)
        } catch (iae: IllegalArgumentException) {
            Toast.makeText(contex, "IIIigal color entery , try again", Toast.LENGTH_LONG).show()
            return
        }

        talker.colorBack = currentColor

    }

    private fun changeTextColor(talker: Talker) {
        try {
            Color.parseColor(currentColor)
        } catch (iae: IllegalArgumentException) {
            Toast.makeText(contex, "IIIigal color entery , try again", Toast.LENGTH_LONG).show()
            return
        }
        talker.colorText = currentColor

    }

    private fun ttParaListView() {
        view.ttPara_listView.setOnItemClickListener { _, _, position, _ ->
            translaeTtPara(position)
            Toast.makeText(
                contex,
                //  "Don't forget to select Para ListView to excute the operation",
                "Don't forget to select Para ", Toast.LENGTH_SHORT
            ).show()
        }
    }


    private fun translaeTtPara(position: Int) {
        when (position) {
            //14 -> selectColor()
            15 -> view.colorNam_ET.visibility = VISIBLE
            16 -> interval = 0
            17 -> interval = 1
            18 -> interval = 2
            19 -> interval = 3
            20 -> interval = 4
            21 -> interval = 5
            22 -> interval = 6
            23 -> interval = 7
            24 -> interval = 8
            25 -> interval = 9
            26 -> interval = 10
            27 -> interval = 11
            28 -> interval = 12
            29 -> interval = 13
            30 -> interval = 14
            31 -> interval = 15
            32 -> interval = 20
            33 -> interval = 50
            34 -> interval = 100
            35 -> interval = 1000
            36 -> interval = 2000
            37 -> interval = 3000
            38 -> interval = 5000
            39 -> currentColor = "#ffffff"
            40 -> currentColor = "#000000"
            41 -> currentColor = "#8e0000"
            411 -> currentColor = "#ad1457"
            42 -> currentColor = "#9c27b0"
            43 -> currentColor = "#1565c0"
            44 -> currentColor = "#03a9f4"
            45 -> currentColor = "#009688"
            46 -> currentColor = "#00701a"
            47 -> currentColor = "#9ccc65"
            48 -> currentColor = "#a0af22"
            49 -> currentColor = "#fdd835"
            50 -> currentColor = "#ffc107"
            51 -> currentColor = "#ff9800"
            52 -> currentColor = "#ff5722"
            53 -> currentColor = "#4b2c20"
            54 -> currentColor = "#9e9e9e"
            55 -> currentColor = "#90a4ae"

            else -> {
                interval = 0
            }
        }
    }

    private fun operateStyleLV() {
        view.style_ListView.setOnItemClickListener { _, _, position, _ ->
            // tranferTalkItem(0)
            val currentTalker = talkC()
            if (position == 16) {     // ther is NB
                currentTalker.backExist = false
            } else {
                currentTalker.backExist = true
                currentTalker.styleNum = styleList[position].toInt()
            }
            upgradeTalker(currentTalker)
        }

    }

    private fun upgradeTalker(talker: Talker) {

        var bo = true
        if (talker.textSize < 3) {
            talker.textSize = 3f
            Toast.makeText(contex, "Text Size too small", Toast.LENGTH_SHORT).show()
            bo = false
        }
        if (talker.dur < 100) {
            talker.textSize = 100f
            Toast.makeText(contex, "Duration too small", Toast.LENGTH_SHORT).show()
            bo = false
        }
        if (bo) {
            trasferStyle(talker)
            updateTitleTalkerSituation()
            moveTheAnimation()
        }

    }

    private fun moveTheAnimation() {
        /* if (counterStep > 84) counterStep = 84
         if ((counterStep == 84 && SHOW_POSITION) || (counterStep == 84 && PUBLISH_POSITION)) {
             counterStep = 1
             // finish()
         }*/
        //    updateTitleTalkerSituation()
        // if (counterStep < 1) counterStep = 1

        //  counterStep = 1           //*********************

        //  manMode = counterStep % 2 != 0


        val talker = talkC()
        animationInAction.excuteTalker(talker)
        // getAndStoreData.savePage(counterStep)
    }


    fun currentPage(): Int {
        var cu = getAndStoreData.getCurrentPage()
        if (cu < 1 || cu >= talkList.size) {
            cu = 1
            getAndStoreData.saveCurrentPage(cu)
        }
        return cu
    }

    fun updateTitleTalkerSituation() {
        val talker = talkC()
        with(talker) {
            val text =
                "l=${takingArray.size}sty=$styleNum anim=$animNum size=${textSize.toInt()}" +
                        " bord=$borderWidth dur=$dur sw=$swingRepeat"
            val cu = currentPage()
            view.tvPage.text = cu.toString()
            numTalker = cu

            view.tvAnimatinKind.text = text
        }

    }

    private fun trasferStyle(talker: Talker) {

        val style = findStyleObject(talker.styleNum)
        talker.colorBack = style.colorBack
        talker.colorText = style.colorText
    }

    private fun findStyleObject(index: Int): StyleObject {
        var style1 = StyleObject()
        var bo = true
        var i = 0
        while (bo && i < Helper.Page.styleArray.size) {

            if (Helper.Page.styleArray[i].numStyleObject == index) {
                style1 = Helper.Page.styleArray[i]
                bo = false
            }
            i++
        }
        if (bo) style1 = Helper.Page.styleArray[10]
        return style1
    }

    private fun updatePage(ind: Int) {
        val cu = currentPage()
        val la = getAndStoreData.getLastPage()
        if (ind == 0) {
            //lastTalker = talkList[counterStep].copy()
            getAndStoreData.saveLastPage(cu)
        } else {
            //talkList[counterStep] = lastTalker.copy()
            if (la > 1) {
                getAndStoreData.saveLastPage(la - 1)
            }
            if (cu > 1) {
                getAndStoreData.saveCurrentPage(cu - 1)
            }
        }
    }

    fun drawListView() {

        createStyleLV()
        createParaList()
        createTtParaTV()
        createAnimLV()
    }

    private fun createStyleLV() {
        Helper.Page.createBasicStyle()
        for (i in 0..15) {
            styleList.add("-")
        }
        styleList.add("NB")
        for (item in Helper.Page.styleArray) {
            val st = item.numStyleObject.toString()
            styleList.add(st)
        }
        for (i in 0..15) {
            styleList.add("-")
        }
        val adapter0 = ArrayAdapter<String>(contex, R.layout.simple_list_item_1, styleList)
        view.style_ListView.adapter = adapter0
        view.style_ListView.setSelection(15)
    }

    private fun createParaList() {
        for (i in 0..5) {
            paraList.add("-")
        }
        val list = arrayListOf(
            "-",
            "Start",
            "-",
            "-",
            "Page",
            "CopyTalk1",
            "-",
            "-",
            "-",
            "TextSize",
            "Duration",
            "-",
            "-",
            "Text Color",
            "Back Color",
            "Bord Color",
            "Bord Line",
            "No Bord",
            "Swing Re.",
            "Radius"
        )
        paraList.addAll(list)

        for (i in 0..20) {
            paraList.add("-")
        }

        val adapter10 = ArrayAdapter<String>(contex, android.R.layout.simple_list_item_1, paraList)
        view.para_ListView.adapter = adapter10
        view.para_ListView.setSelection(15)
    }

    private fun createTtParaTV() {
        for (i in 0..13) {
            ttParaList.add("-")
        }
        val list = getTtParaList()
        ttParaList.addAll(list)
        for (i in 0..20) {
            ttParaList.add("-")
        }
        val adapter11 =
            ArrayAdapter<String>(contex, android.R.layout.simple_list_item_1, ttParaList)
        view.ttPara_listView.adapter = adapter11
        view.ttPara_listView.setSelection(15)
    }

    private fun getTtParaList(): List<String> = arrayListOf(
        "Piker Color",
        "Color Nun",
        "0",
        "1",
        "2",
        "3",
        "4",
        "5",
        "6",
        "7",
        "8",
        "9",
        "10",
        "11",
        "12",
        "13",
        "14",
        "15",
        "20",
        "50",
        "100",
        "1000",
        "2000",
        "3000",
        "5000",
        "C-White",
        "C-Black",
        "C-Red",
        "C-Pink",
        "C-Purple",
        "C-Blue",
        "C-LBlue",
        "C-Teal",
        "C-Green",
        "C-LGreen",
        "C-Lime",
        "C-Yellow",
        "C-Amber",
        "C-Orange",
        "C-DOrange",
        "C-Brown",
        "C-Gray",
        "C-BGray"
    )

    private fun createAnimLV() {

        for (i in 0..15) {
            actionList.add("-")
        }
        val list = arrayListOf(
            "4",
            "10", "11", "12", "13", "14", "15",
            "20", "21", "22", "23", "24", "25",
            "30", "31", "32", "33", "34", "35",
            "40", "41", "42", "43", "44", "45", "46",
            "50", "51", "52", "53", "54", "55", "506",
            "60", "61", "62", "63", "64", "65"
        )
        actionList.addAll(list)
        for (i in 0..15) {
            actionList.add("-")
        }
        val adapter1 =
            ArrayAdapter<String>(contex, android.R.layout.simple_list_item_1, actionList)
        view.action_ListView.adapter = adapter1
        view.action_ListView.setSelection(15)
    }



    @SuppressLint("RestrictedApi")
     fun setLayoutShowMode() {
        showPosition = getAndStoreData.getShowPosition()
        with(view) {
            if (showPosition == 1) {
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
            if (showPosition == 2) {
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
            if (showPosition == 3) {

                down_layout.visibility = INVISIBLE
                upper_layout.visibility = INVISIBLE
                style_ListView.visibility = INVISIBLE
                para_ListView.visibility = INVISIBLE
                ttPara_listView.visibility = INVISIBLE
                action_ListView.visibility = INVISIBLE
                tvAnimatinKind.visibility = INVISIBLE
                /*fab.visibility = VISIBLE
                fab1.visibility = VISIBLE*/
            }
        }
    }
    /*private fun showEditText(ind: Int) {
        with(view) {
            if (ind == 0) {
                style_ListView.visibility = INVISIBLE
                para_ListView.visibility = INVISIBLE
                ttPara_listView.visibility = INVISIBLE
                action_ListView.visibility = INVISIBLE
                pageNumEditText.visibility = VISIBLE
            } else {
                style_ListView.visibility = VISIBLE
                para_ListView.visibility = VISIBLE
                ttPara_listView.visibility = VISIBLE
                action_ListView.visibility = VISIBLE
                pageNumEditText.visibility = INVISIBLE
                pageNumEditText.hideKeyboard()
            }
        }
    }

    fun View.hideKeyboard() {
        val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(windowToken, 0)
    }*/
}


/* fun operateListView() {
        operateStyleList()


    }*/

/* private fun operateStyleList() {
     view.style_ListView.setOnItemClickListener { _, _, position, _ ->
         tranferTalkItem(0)
        *//* if (position == 16) {     // ther is NB
                talker.backExist = false
            } else {
                talker.backExist = true
                talker.styleNum = styleList[position].toInt()
            }
            upgradeTalker()*//*
        }
    }*/

private fun updateLastTalker(ind: Int) {
    if (ind == 0) {
        // lastTalker = talker.copy()
    } else {
        // talker = lastTalker.copy()
    }
}

fun prepareAllTheListViewParam() {

    // styleListView()   //list view in the left side
    //createStyleLV()

    // patamListView()   //second list view from the left
    // createParaList()


    //     ttParaListView() // third list viee from the left
    //createTtParaTV()


    //    animationMovmentListView()  // list view in the right side
    // createAnimLV()

    /*       initButton()
         lastTalker = Talker()
         tranferTalkItem(0)
         backGroundConfigration()*/
}


/*private fun styleListView() {// list view in the left side
      createStyleLV()
      view.style_ListView.setOnItemClickListener { _, _, position, _ ->
          tranferTalkItem(0)
          if (position == 16) {     // ther is NB
              talker.backExist = false
          } else {
              talker.backExist = true
              talker.styleNum = styleList[position].toInt()
          }
          upgradeTalker()
      }
  }




private fun moveTheAnimation() {
    if (counterStep > 84) counterStep = 84
    if ((counterStep == 84 && SHOW_POSITION) || (counterStep == 84 && PUBLISH_POSITION)) {
        counterStep = 1
        // finish()
    }
    updateTitleTalkerSituation()
    if (counterStep < 1) counterStep = 1

    //  counterStep = 1           //*********************

    manMode = counterStep % 2 != 0



    animationInAction.excuteTalker(talker)
    getAndStoreData.savePage(counterStep)
}*/

//----------------


/*








//------------------------


//------------------------




//------------------


private fun retriveLastTalker() {
    tranferTalkItem(1)
    moveTheAnimation()
}

private fun minTextSize() {
    tranferTalkItem(0)
    talkList[counterStep].textSize = 12f  // for accsident of bigest text
    moveTheAnimation()
}

private fun readAgainTextFile() {
    val textTalkList = sharData.createTalkListFromTheStart()
    talkList = activatApp.textReRead(talkList, textTalkList)
    /*Handler().postDelayed(
        {*/
    moveTheAnimation()
    Toast.makeText(this, "Read all text From the start", Toast.LENGTH_SHORT).show()

    /*          },
              2000 // value in milliseconds
          )*/
}

private fun selectColor() {
    val intent = Intent(this, SelectColor::class.java)
    startActivityForResult(intent, 12)
}

override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
    super.onActivityResult(requestCode, resultCode, data)
    if (resultCode == Activity.RESULT_OK) {
        currentColor = data?.getStringExtra("color")!!

    }
}














private fun changePlusMinusMode() {
    if (plusMode) {
        plusMode = false
        plusAndMinusBtn.setText("-")
    } else {
        plusMode = true
        plusAndMinusBtn.setText("+")
    }
}


private fun findStyleObject(index: Int): StyleObject {
    var style1 = StyleObject()
    var bo = true
    var i = 0
    while (bo && i < Page.styleArray.size) {

        if (Page.styleArray[i].numStyleObject == index) {
            style1 = Page.styleArray[i]
            bo = false
        }
        i++
    }
    if (bo) style1 = Page.styleArray[10]
    return style1
}


private fun updateTitleTalkerSituation() {
    with(talkList[counterStep]) {
        val text =
            "l=${takingArray.size}sty=$styleNum anim=$animNum size=${textSize.toInt()}" +
                    " bord=$borderWidth dur=$dur sw=$swingRepeat"
        tvAnimatinKind.text = text
        tvPage.text = counterStep.toString()
    }

}

private fun trasferStyle() {

    var item = talkList[counterStep]
    val style = findStyleObject(item.styleNum)
    item.colorBack = style.colorBack
    item.colorText = style.colorText
}

private fun upgradeTalker() {

    var bo = true
    if (talkList[counterStep].textSize < 3) {
        talkList[counterStep].textSize = 3f
        Toast.makeText(this, "Text Size too small", Toast.LENGTH_SHORT).show()
        bo = false
    }
    if (talkList[counterStep].dur < 100) {
        talkList[counterStep].textSize = 100f
        Toast.makeText(this, "Duration too small", Toast.LENGTH_SHORT).show()
        bo = false
    }
    if (bo) {
        trasferStyle()
        updateTitleTalkerSituation()
        moveTheAnimation()
    }

}

fun saveIt() {
    sharData.savePage(counterStep)
    sharData.saveTalkingListInPref(talkList)
    Toast.makeText(this, "It's save Mr", Toast.LENGTH_SHORT).show()
    updateTitleTalkerSituation()
}

fun nextIt() {
    counterStep++
    val max = talkList.size - 1
    if (counterStep > max) counterStep = max
    sharData.savePage(counterStep)
    moveTheAnimation()
}

fun previousIt() {
    counterStep--
    if (counterStep < 1) counterStep = 1
    sharData.savePage(counterStep)
    moveTheAnimation()
}






private fun tranferValue(ind: Int) {
    with(talkList[counterStep]) {
        if (ind == 0) {
            current_styleNum = styleNum
            current_animNum = animNum
            current_dur = dur
            current_textSize = textSize
        } else {
            styleNum = current_styleNum
            trasferStyle()
            animNum = current_animNum
            if (current_dur > 100) {
                dur = current_dur
            } else {
                current_dur = 100
            }
            if (current_textSize > 10) {
                textSize = current_textSize
            } else {
                current_textSize = 10f
            }
        }
    }
    updateTitleTalkerSituation()
}

private fun addStyleValueToTalkingList() {
    for (item in talkList) {
        val numStyle = item.styleNum
        val style = findStyleObject(numStyle)
        item.colorBack = style.colorBack
        item.colorText = style.colorText
    }

}
*/




private fun tranferTalkItem(ind: Int) {
    if (ind == 0) {
        lastTalker = talker.copy()
    } else {
        talker = lastTalker.copy()
    }
}




private fun upgradeTalker() {

    var bo = true
    if (talker.textSize < 3) {
        talker.textSize = 3f
        helper.showToast("Text Size too small")
     //   Toast.makeText(this, "Text Size too small", Toast.LENGTH_SHORT).show()
        bo = false
    }
    if (talker.dur < 100) {
        talker.textSize = 100f
       // Toast.makeText(this, "Duration too small", Toast.LENGTH_SHORT).show()
        helper.showToast("Duration too small")
        bo = false
    }
    if (bo) {
        trasferStyle()
        updateTitleTalkerSituation()
        moveTheAnimation()
    }
}
private fun updateTitleTalkerSituation() {
    with(talker) {
        val text =
            "l=${takingArray.size}sty=$styleNum anim=$animNum size=${textSize.toInt()}" +
                    " bord=$borderWidth dur=$dur sw=$swingRepeat"
        view.tvAnimatinKind.text = text
        view.tvPage.text = numTalker.toString()
    }
}
private fun trasferStyle() {

    var item = talker
    val style = findStyleObject(item.styleNum)
    item.colorBack = style.colorBack
    item.colorText = style.colorText
}
private fun findStyleObject(index: Int): StyleObject {
    var style1 = StyleObject()
    var bo = true
    var i = 0
    while (bo && i < Helper.Page.styleArray.size) {

        if (Helper.Page.styleArray[i].numStyleObject == index) {
            style1 = Helper.Page.styleArray[i]
            bo = false
        }
        i++
    }
    if (bo) style1 = Helper.Page.styleArray[10]
    return style1
}
*/



