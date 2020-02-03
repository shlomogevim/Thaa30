package com.example.thaa30.util

import android.content.Context
import android.graphics.Typeface
import android.widget.Toast

class Helper(val context: Context) {


        fun showToast(st:String){
            Toast.makeText(context,st,Toast.LENGTH_SHORT).show()
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


    fun textSizeUpgrade(
        talkList: ArrayList<Talker>,
        conterStep: Int,
        plusOrMius: Int,
        inteval: Int
    ): ArrayList<Talker> {
        if (plusOrMius == 1) {
            talkList[conterStep].textSize = talkList[conterStep].textSize + inteval
        }
        if (plusOrMius == 0) {
            talkList[conterStep].textSize = talkList[conterStep].textSize - inteval
        }
        upgradeTalker(talkList, conterStep)

        return talkList
    }

    fun durationUpgrade(
        talkList: ArrayList<Talker>,
        conterStep: Int,
        plusOrMius: Int,
        inteval: Int
    ): ArrayList<Talker> {
        if (plusOrMius == 1) {
            talkList[conterStep].dur = talkList[conterStep].dur + inteval
        }
        if (plusOrMius == 0) {
            talkList[conterStep].dur = talkList[conterStep].dur - inteval
        }
        upgradeTalker(talkList, conterStep)

        return talkList
    }

    private fun upgradeTalker(talkList: ArrayList<Talker>, conterStep: Int) {
        var bo = true
        if (talkList[conterStep].textSize < 3) {
            talkList[conterStep].textSize = 3f
            Toast.makeText(context, "Text Size too small", Toast.LENGTH_SHORT).show()
            bo = false
        }
        if (talkList[conterStep].dur < 100) {
            talkList[conterStep].textSize = 100f
            Toast.makeText(context, "Duration too small", Toast.LENGTH_SHORT).show()
            bo = false
        }
        if (bo) {
            trasferStyle(talkList, conterStep)
        }
    }

    private fun trasferStyle(talkList: ArrayList<Talker>, conterStep: Int) {
        var item = talkList[conterStep]
        val style = findStyleObject(item.styleNum)
        item.colorBack = style.colorBack
        item.colorText = style.colorText
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


    fun copyTalker(talkList: ArrayList<Talker>, conterStep: Int, index: Int) {
        var spicalTalkList = arrayListOf(
            Talker(
                numTalker = 1, styleNum = 411, animNum = 61, textSize = 288f, dur = 3000
            ) // god "YES"
        )
        if (index == 1 && talkList[conterStep].whoSpeake == "man") return
        var bo = true
        var i = 0
        while (bo && i < spicalTalkList.size) {
            if (spicalTalkList[i].numTalker == index) {
                val spcialTalk = spicalTalkList[i]
                talkList[conterStep].styleNum = spcialTalk.styleNum
                talkList[conterStep].animNum = spcialTalk.animNum
                talkList[conterStep].textSize = spcialTalk.textSize
                talkList[conterStep].dur = spcialTalk.dur
                val style = findStyleObject(spcialTalk.styleNum)
                talkList[conterStep].colorText = style.colorText
                talkList[conterStep].colorBack = style.colorBack
                bo = false
            }
        }

    }

    object Page {
        val styleArray = ArrayList<StyleObject>()
        fun createBasicStyle() {
            var list = listOf<StyleObject>(
                StyleObject(10, "#ffffff", "#000000", 24f, 1),
                StyleObject(11, "#000000", "#ffffff", 24f, 1),
                StyleObject(20, "#1b5e20", "#81c784", 24f, 1),
                StyleObject(21, "#81c784", "#1b5e20", 24f, 1),
                StyleObject(30, "#1976d2", "#ffab91", 24f, 1),
                StyleObject(31, "#ffab91", "#1976d2", 24f, 1),
                StyleObject(40, "#003c8f", "#c3fdff", 24f, 1),
                StyleObject(41, "#c3fdff", "#003c8f", 24f, 1),
                StyleObject(50, "#574339", "#fdd835", 24f, 1),
                StyleObject(51, "#fdd835", "#574339", 24f, 1),
                StyleObject(60, "#c63f17", "#4dd0e1", 24f, 1),
                StyleObject(61, "#4dd0e1", "#c63f17", 24f, 1),
                StyleObject(70, "#ffab91", "#212121", 24f, 1),
                StyleObject(71, "#212121", "#ffab91", 24f, 1),
                StyleObject(80, "#9c786c", "#40241A", 24f, 1),
                StyleObject(81, "#40241A", "#9c786c", 24f, 1),
                StyleObject(90, "#4caf50", "#ffffff", 24f, 1),
                StyleObject(91, "#ffffff", "#4caf50", 24f, 1),
                StyleObject(100, "#1b1b1b", "#ff8a50", 24f, 1),
                StyleObject(101, "#ff8a50", "#1b1b1b", 24f, 1),
                StyleObject(110, "#fafafa", "#9a0007", 24f, 1),
                StyleObject(111, "#9a0007", "#fafafa", 24f, 1),
                StyleObject(120, "#9e9e9e", "#ba2d65", 24f, 1),
                StyleObject(121, "#ba2d65", "#9e9e9e", 24f, 1),
                StyleObject(130, "#f3e5f5", "#9c27b0", 24f, 1),
                StyleObject(131, "#9c27b0", "#f3e5f5", 24f, 1),
                StyleObject(140, "#ff7043", "#ffeb3b", 24f, 1),
                StyleObject(141, "#ffeb3b", "#ff7043", 24f, 1),
                StyleObject(150, "#ffffff", "#ff9800", 24f, 1),
                StyleObject(151, "#ff9800", "#ffffff", 24f, 1),
                StyleObject(160, "#fdd835", "#005b9f", 24f, 1),
                StyleObject(161, "#005b9f", "#fdd835", 24f, 1),
                StyleObject(170, "#f06292", "#38006b", 24f, 1),
                StyleObject(171, "#38006b", "#f06292", 24f, 1),
                StyleObject(180, "#fff59d", "#33691e", 24f, 1),
                StyleObject(181, "#33691e", "#fff59d", 24f, 1),
                StyleObject(190, "#ffd54f", "#000063", 24f, 1),
                StyleObject(191, "#000063", "#ffd54f", 24f, 1),
                StyleObject(400, "#4fc3f7", "#ffeb3b", 24f, 1),
                StyleObject(401, "#ffeb3b", "#4fc3f7", 24f, 1),
                StyleObject(410, "#aeea00", "#ffa726", 24f, 1),
                StyleObject(411, "#ffa726", "#aeea00", 24f, 1),
                StyleObject(420, "#ffab40", "#6d4c41", 24f, 1),
                StyleObject(421, "#6d4c41", "#ffab40", 24f, 1),
                StyleObject(430, "#f9fbe7", "#cddc39", 24f, 1),
                StyleObject(431, "#cddc39", "#f9fbe7", 24f, 1),
                StyleObject(440, "#283593", "#d32f2f", 24f, 1),
                StyleObject(441, "#d32f2f", "#283593", 24f, 1),
                StyleObject(450, "#757575", "#fdd835", 24f, 1),
                StyleObject(451, "#fdd835", "#757575", 24f, 1),
                StyleObject(460, "#bbdefb", "#004d40", 24f, 1),
                StyleObject(461, "#004d40", "#bbdefb", 24f, 1),
                StyleObject(470, "#4dd0e1", "#f4511e", 24f, 1),
                StyleObject(471, "#f4511e", "#4dd0e1", 24f, 1),
                StyleObject(480, "#18ffff", "#e0e0e0", 24f, 1),
                StyleObject(481, "#e0e0e0", "#18ffff", 24f, 1),

                StyleObject(200, "#ffffff", "#000000", 24f, 1),
                StyleObject(210, "#000000", "#ffffff", 24f, 1),
                StyleObject(211, "#000000", "#bdbdbd", 28f, 1),
                StyleObject(212, "#fbc02d", "#ffffff", 28f, 1),
                StyleObject(213, "#d1c4e9", "#ffffff", 28f, 1),
                StyleObject(220, "#bdbdbd", "#000000", 28f, 1),
                StyleObject(230, "#ffebee", "#e91e63", 35f, 1),
                StyleObject(240, "none", "#1e88e5", 50f, 1),
                StyleObject(250, "none", "#ffffff", 28f, 1),
                StyleObject(260, "none", "#44000D", 28f, 1),
                StyleObject(270, "#e3f2fd", "#44000D", 28f, 1),
                StyleObject(280, "none", "#6ff9ff", 28f, 1),
                StyleObject(290, "none", "#f9a825", 28f, 1),
                StyleObject(300, "#e3f2fd", "#1e88e5", 28f, 1)
            )
            styleArray.addAll(list)
        }
    }

    fun getTypeFace(ind: Int) =
        when {
            ind == 0 -> Typeface.SANS_SERIF
            ind == 1 -> Typeface.createFromAsset(context.assets, "fonts/anka.ttf")
            ind == 2 -> Typeface.createFromAsset(context.assets, "fonts/dana.otf")
            ind == 3 -> Typeface.createFromAsset(context.assets, "fonts/shmulik.ttf")
            ind == 4 -> Typeface.createFromAsset(context.assets, "fonts/stam.ttf")
            ind == 5 -> Typeface.createFromAsset(context.assets, "fonts/drug.ttf")
            ind == 6 -> Typeface.createFromAsset(context.assets, "fonts/drug.ttf")
            else -> Typeface.MONOSPACE
        }
}