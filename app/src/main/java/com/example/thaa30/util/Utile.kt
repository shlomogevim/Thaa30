package com.example.thaa30.util

import android.content.res.Resources
import android.graphics.Color
import android.widget.TextView
import com.github.florent37.viewanimator.ViewAnimator

object Utile {
        var wi: Float = Resources.getSystem().displayMetrics.widthPixels.toFloat()
        var hi: Float = Resources.getSystem().displayMetrics.heightPixels.toFloat()
        var start = 0L
        var end = 0L


        var listener1: ((item: Int, myTime: Long) -> Unit)? = null


        fun chageBackgroundColor(ind: Int, textView: TextView, dur: Long) {
            if (ind == 0) {
                ViewAnimator
                    .animate(textView)
                    .backgroundColor(Color.RED, Color.GREEN)
                    .duration(dur)
                    .start()

            } else {
                ViewAnimator
                    .animate(textView)
                    .backgroundColor(Color.GREEN, Color.RED)
                    .duration(dur)
                    .start()

            }
        }

        fun item_scale(ind: Int, textView: TextView, dur: Long) {

            if (ind > -7) {
                ViewAnimator
                    .animate(textView)
                    .scale(0f, 1f)
                    .duration(dur)
                    .start()
                    .onStop {
                        end = System.currentTimeMillis() - start
                        listener1?.invoke(ind, end)
                    }
            }
        }

        fun item_scale_swing(ind: Int, textView: TextView, dur: Long, rep: Int) {

            if (rep == 0) {
                ViewAnimator
                    .animate(textView)
                    .scale(0f, 1f)
                    .duration(dur)
                    .start()
                    .onStop {
                        end = System.currentTimeMillis() - start
                        listener1?.invoke(ind, end)
                    }
            } else {
                ViewAnimator
                    .animate(textView)
                    .scale(0f, 1f)
                    .duration(dur)
                    .thenAnimate(textView)
                    .swing()
                    .repeatCount(rep)
                    .start()
                    .onStop {
                        end = System.currentTimeMillis() - start
                        listener1?.invoke(ind, end)
                    }

            }
        }


        fun item_move_swing(ind: Int, textView: TextView, dur: Long, rep: Int) {
            var arr = getCordinateAndSpine(ind)
            if (rep == 0) {
                ViewAnimator
                    .animate(textView)
                    .scale(1f)
                    .duration(1)
                    .thenAnimate(textView)
                    .translationX(arr[0], 0f)
                    .translationY(arr[1], 0f)
                    .duration(dur)
                    .start()
                    .onStop {
                        end = System.currentTimeMillis() - start
                        listener1?.invoke(ind, end)
                    }

            } else {

                ViewAnimator
                    .animate(textView)
                    .scale(1f)
                    .duration(1)
                    .thenAnimate(textView)
                    .translationX(arr[0], 0f)
                    .translationY(arr[1], 0f)
                    .duration(dur)
                    .thenAnimate(textView)
                    .swing()
                    .repeatCount(rep)
                    .start()
                    .onStop {
                        end = System.currentTimeMillis() - start
                        listener1?.invoke(ind, end)
                    }

            }
        }

        fun item_move_scale(ind: Int, textView: TextView, dur: Long) {
            var arr = getCordinateAndSpine(ind)

            ViewAnimator
                .animate(textView)
                .scale(0f, 1f)
                .translationX(arr[0], 0f)
                .translationY(arr[1], 0f)
                .duration(dur)
                .start()
                .onStop {
                    end = System.currentTimeMillis() - start
                    listener1?.invoke(ind, end)
                }

        }

        fun item_move_scale_rotate(ind: Int, textView: TextView, talker: Talker) {
            var arr = getCordinateAndSpine(ind)
            if (ind == talker.takingArray.size ) {
                ViewAnimator
                    .animate(textView)
                    .scale(0f, 1f)
                    .translationX(arr[0], 0f)
                    .translationY(arr[1], 0f)
                    .rotation(arr[2])
                    .duration(talker.dur)
                    .thenAnimate(textView)
                    .rotation(-arr[2])
                    .duration(1)
                    .start()
                    .onStop {
                        end = System.currentTimeMillis() - start
                        listener1?.invoke(ind, end)
                    }
            } else {
                ViewAnimator
                    .animate(textView)
                    .scale(0f, 1f)
                    .translationX(arr[0], 0f)
                    .translationY(arr[1], 0f)
                    .rotation(arr[2])
                    .duration(talker.dur)
                    .thenAnimate(textView)
                    .rotation(-arr[2])
                    .duration(1)
                    .start()
            }
        }


        fun move_swing(selector: Int, talker: Talker, arr: ArrayList<TextView?>) {
            start = System.currentTimeMillis()
            with(talker) {
                val linesNum = takingArray.size
                if (selector == 10) {
                    for (index in 1..linesNum) {
                        item_move_swing(index, arr[index - 1]!!, dur, swingRepeat)
                    }
                }

                if (selector == 11) {
                    if (linesNum > 1) {
                        for (index in 1..linesNum) {
                            arr[index - 1]?.let {
                                if (index == 1) {
                                    // item_move(index, it, dur)
                                    item_move_swing(index, it, dur, 0)

                                } else {
                                    item_move_swing(index, it, dur, swingRepeat)

                                }
                            }
                        }
                    } else {
                        item_move_swing(1, arr[0]!!, dur, swingRepeat)

                    }
                }
                if (selector == 12) {
                    for (index in 1..linesNum) {
                        arr[index - 1]?.let {
                            if (index <= 2) {
                                //item_move(index, it, dur)
                                item_move_swing(index, it, dur, 0)
                            } else {
                                item_move_swing(index, it, dur, swingRepeat)

                            }
                        }
                    }
                }
                if (selector == 13) {
                    for (index in 1..linesNum) {
                        arr[index - 1]?.let {
                            if (index <= 3) {
                                // item_move(index, it, dur)
                                item_move_swing(index, it, dur, 0)

                            } else {
                                item_move_swing(index, it, dur, swingRepeat)

                            }
                        }
                    }
                }
                if (selector == 14) {
                    for (index in 1..linesNum) {
                        arr[index - 1]?.let {
                            if (index <= 4) {
                                //  item_move(index, it, dur)
                                item_move_swing(index, it, dur, 0)
                            } else {
                                item_move_swing(index, it, dur, swingRepeat)

                            }
                        }
                    }
                }
                if (selector == 15) {
                    for (index in 1..linesNum) {
                        arr[index - 1]?.let {
                            if (index <= 5) {
                                //item_move(index, it, dur)
                                item_move_swing(index, it, dur, 0)
                            } else {
                                item_move_swing(index, it, dur, swingRepeat)

                            }
                        }
                    }
                }
            }

        }

        fun scale_swing(selector: Int, talker: Talker, arr: ArrayList<TextView?>) {
            start = System.currentTimeMillis()
            with(talker) {
                val linesNum = takingArray.size
                if (selector != 20 && swingRepeat < 1) swingRepeat = 1
                if (selector == 20) {
                    for (index in 0 until linesNum) {
                        arr[index]?.let {
                            item_scale_swing(index + 1, it, dur, swingRepeat)
                        }
                    }
                }

                if (selector == 21) {
                    for (index in 1..linesNum) {
                        arr[index - 1]?.let {
                            if (index == 1) {
                                // item_scale(index, it, dur)
                                item_scale_swing(index, it, dur, 0)

                            } else {
                                item_scale_swing(index, it, dur, swingRepeat)

                            }
                        }
                    }
                }
                if (selector == 22) {
                    for (index in 1..linesNum) {
                        arr[index - 1]?.let {
                            if (index <= 2) {
                                //item_scale(index, it, dur)
                                item_scale_swing(index, it, dur, 0)
                            } else {
                                item_scale_swing(index, it, dur, swingRepeat)

                            }
                        }
                    }
                }
                if (selector == 23) {
                    for (index in 1..linesNum) {
                        arr[index - 1]?.let {
                            if (index <= 3) {
                                //  item_scale(index, it, dur)
                                item_scale_swing(index, it, dur, 0)
                            } else {
                                item_scale_swing(index, it, dur, swingRepeat)
                            }
                        }
                    }
                }
                if (selector == 24) {
                    for (index in 1..linesNum) {
                        arr[index - 1]?.let {
                            if (index <= 4) {
                                // item_scale(index, it, dur)
                                item_scale_swing(index, it, dur, 0)
                            } else {
                                item_scale_swing(index, it, dur, swingRepeat)

                            }
                        }
                    }
                }
                if (selector == 25) {
                    for (index in 1..linesNum) {
                        arr[index - 1]?.let {
                            if (index <= 5) {
                                //  item_scale(index, it, dur)
                                item_scale_swing(index, it, dur, 0)
                            } else {
                                item_scale_swing(index, it, dur, swingRepeat)

                            }
                        }
                    }
                }
            }
        }

        fun move_scale(selector: Int, arr: ArrayList<TextView?>, dur: Long) {
            start = System.currentTimeMillis()
            if (selector == 30) {
                for (index in 0 until arr.size) {
                    arr[index]?.let { item_scale(index + 1, it, dur) }
                }
            }

            if (selector == 31) {
                for (index in 0 until arr.size) {
                    arr[index]?.let {
                        if (index > 0) {
                            item_move_scale(index + 1, it, dur)
                        } else {
                            item_scale(index + 1, it, dur)
                        }
                    }
                }
            }
            if (selector == 32) {
                for (index in 0 until arr.size) {
                    arr[index]?.let {
                        if (index > 1) {
                            item_move_scale(index + 1, it, dur)
                        } else {
                            item_scale(index + 1, it, dur)
                        }
                    }
                }
            }
            if (selector == 33) {
                for (index in 0 until arr.size) {
                    arr[index]?.let {
                        if (index > 2) {
                            item_move_scale(index + 1, it, dur)
                        } else {
                            item_scale(index + 1, it, dur)
                        }
                    }
                }
            }
            if (selector == 34) {
                for (index in 0 until arr.size) {
                    arr[index]?.let {
                        if (index > 3) {
                            item_move_scale(index + 1, it, dur)
                        } else {
                            item_scale(index + 1, it, dur)
                        }
                    }
                }
            }
            if (selector == 35) {
                for (index in 0 until arr.size) {
                    arr[index]?.let {
                        if (index > 4) {
                            item_move_scale(index + 1, it, dur)
                        } else {
                            item_scale(index + 1, it, dur)
                        }
                    }
                }
            }
        }

        fun move_scale_rotate(selector: Int, talker: Talker, arr: ArrayList<TextView?>) {
            start = System.currentTimeMillis()
            with(talker) {
                val linesNum = takingArray.size
                if (selector == 40) {
                    for (index in 0 until linesNum) {
                        arr[index]?.let { item_move_scale_rotate(index + 1, it, talker) }
                    }
                }

                if (selector == 41) {
                    for (index in 1..linesNum) {
                        arr[index - 1]?.let {
                            if (index == 1) {
                                item_scale(index, it, dur)
                            } else {
                                item_move_scale_rotate(index, it, talker)

                            }
                        }
                    }
                }

                if (selector == 42) {
                    for (index in 1..linesNum) {
                        arr[index - 1]?.let {
                            if (index <= 2) {
                                item_scale(index, it, dur)
                            } else {
                                item_move_scale_rotate(index, it, talker)

                            }
                        }
                    }
                }
                if (selector == 43) {
                    for (index in 1..linesNum) {
                        arr[index - 1]?.let {
                            if (index <= 3) {
                                item_scale(index, it, dur)
                            } else {
                                item_move_scale_rotate(index, it, talker)

                            }
                        }
                    }
                }
                if (selector == 44) {
                    for (index in 1..linesNum) {
                        arr[index - 1]?.let {
                            if (index <= 4) {
                                item_scale(index, it, dur)
                            } else {
                                item_move_scale_rotate(index, it, talker)

                            }
                        }
                    }
                }
                if (selector == 45) {
                    for (index in 1..linesNum) {
                        arr[index - 1]?.let {
                            if (index <= 5) {
                                item_scale(index, it, dur)
                            } else {
                                item_move_scale_rotate(index, it, talker)

                            }
                        }
                    }
                }


                if (selector == 46) {
                    for (index in 1..linesNum) {
                        arr[index - 1]?.let {
                            if (index <= takingArray.size - 1) {
                                item_move_scale_rotate(index, it, talker)
                            } else {
                                if (swingRepeat < 1) swingRepeat = 1
                                item_scale_swing(index, it, dur, swingRepeat)
                            }
                        }
                    }
                }
            }
        }

        fun apeareOneAfterAnother(arr: ArrayList<TextView?>, talker: Talker) {
            start = System.currentTimeMillis()
            val dur = talker.dur
            if (arr.size == 1) {
                ViewAnimator
                    .animate(arr[0])
                    .scale(0f, 1f)
                    .duration(dur)
                    .start()
                    .onStop {
                        end = System.currentTimeMillis() - start
                        listener1?.invoke(1, end)
                    }
            }

            if (arr.size == 2) {
                ViewAnimator
                    .animate(arr[0])
                    .scale(0f, 1f)
                    .duration(dur)
                    .thenAnimate(arr[1])
                    .scale(0f, 1f)
                    .duration(dur)
                    .start()
                    .onStop {
                        end = System.currentTimeMillis() - start
                        listener1?.invoke(2, end)
                    }
            }
            if (arr.size == 3) {
                ViewAnimator
                    .animate(arr[0])
                    .scale(0f, 1f)
                    //.duration(dur)
                    .thenAnimate(arr[1])
                    .scale(0f, 1f)
                    //.duration(dur)
                    .thenAnimate(arr[2])
                    .scale(0f, 1f)
                    .duration(dur)
                    .start()
                    .onStop {
                        end = System.currentTimeMillis() - start
                        listener1?.invoke(3, end)
                    }
            }
            if (arr.size == 4) {
                ViewAnimator
                    .animate(arr[0])
                    .scale(0f, 1f)
                    .duration(dur)
                    .thenAnimate(arr[1])
                    .scale(0f, 1f)
                    .duration(dur)
                    .thenAnimate(arr[2])
                    .scale(0f, 1f)
                    .duration(dur)
                    .thenAnimate(arr[3])
                    .scale(0f, 1f)
                    .duration(dur)
                    .start()
                    .onStop {
                        end = System.currentTimeMillis() - start
                        listener1?.invoke(4, end)
                    }
            }
            if (arr.size == 5) {
                ViewAnimator
                    .animate(arr[0])
                    .scale(0f, 1f)
                    .duration(dur)
                    .thenAnimate(arr[1])
                    .scale(0f, 1f)
                    .duration(dur)
                    .thenAnimate(arr[2])
                    .scale(0f, 1f)
                    .duration(dur)
                    .thenAnimate(arr[3])
                    .scale(0f, 1f)
                    .duration(dur)
                    .thenAnimate(arr[4])
                    .scale(0f, 1f)
                    .duration(dur)
                    .start()
                    .onStop {
                        end = System.currentTimeMillis() - start
                        listener1?.invoke(5, end)
                    }
            }
            if (arr.size == 6) {
                ViewAnimator
                    .animate(arr[0])
                    .scale(0f, 1f)
                    .duration(dur)
                    .thenAnimate(arr[1])
                    .scale(0f, 1f)
                    .duration(dur)
                    .thenAnimate(arr[2])
                    .scale(0f, 1f)
                    .duration(dur)
                    .thenAnimate(arr[3])
                    .scale(0f, 1f)
                    .duration(dur)
                    .thenAnimate(arr[4])
                    .scale(0f, 1f)
                    .duration(dur)
                    .thenAnimate(arr[5])
                    .scale(0f, 1f)
                    .duration(dur)
                    .start()
                    .onStop {
                        end = System.currentTimeMillis() - start
                        listener1?.invoke(6, end)
                    }
            }

        }

        fun apeareOneAfterAnotherAndSwing(arr: ArrayList<TextView?>, talker: Talker) {
            start = System.currentTimeMillis()
            with(talker) {
                if (arr.size == 1) {
                    ViewAnimator
                        .animate(arr[0])
                        .scale(0f, 1f)
                        .swing()
                        .duration(dur)
                        .thenAnimate(arr[0])
                        .repeatMode(swingRepeat)
                        .swing()
                        .start()
                        .onStop {
                            end = System.currentTimeMillis() - start
                            listener1?.invoke(1, end)
                        }
                }

                if (arr.size == 2) {
                    ViewAnimator
                        .animate(arr[0])
                        .scale(0f, 1f)
                        .duration(dur)
                        .thenAnimate(arr[1])
                        .scale(0f, 1f)
                        .duration(dur)
                        .thenAnimate(arr[1])
                        .repeatMode(swingRepeat)
                        .swing()
                        .start()
                        .onStop {
                            end = System.currentTimeMillis() - start
                            listener1?.invoke(2, end)
                        }
                }
                if (arr.size == 3) {
                    ViewAnimator
                        .animate(arr[0])
                        .scale(0f, 1f)
                        .duration(dur)
                        .thenAnimate(arr[1])
                        .scale(0f, 1f)
                        .duration(dur)
                        .thenAnimate(arr[2])
                        .scale(0f, 1f)
                        .duration(dur)
                        .thenAnimate(arr[2])
                        .repeatMode(swingRepeat)
                        .swing()
                        .start()
                        .onStop {
                            end = System.currentTimeMillis() - start
                            listener1?.invoke(3, end)
                        }
                }
                if (arr.size == 4) {
                    ViewAnimator
                        .animate(arr[0])
                        .scale(0f, 1f)
                        .duration(dur)
                        .thenAnimate(arr[1])
                        .scale(0f, 1f)
                        .duration(dur)
                        .thenAnimate(arr[2])
                        .scale(0f, 1f)
                        .duration(dur)
                        .thenAnimate(arr[3])
                        .scale(0f, 1f)
                        .duration(dur)
                        .thenAnimate(arr[3])
                        .repeatMode(swingRepeat)
                        .swing()
                        .start()
                        .onStop {
                            end = System.currentTimeMillis() - start
                            listener1?.invoke(4, end)
                        }
                }
                if (arr.size == 5) {
                    ViewAnimator
                        .animate(arr[0])
                        .scale(0f, 1f)
                        .duration(dur)
                        .thenAnimate(arr[1])
                        .scale(0f, 1f)
                        .duration(dur)
                        .thenAnimate(arr[2])
                        .scale(0f, 1f)
                        .duration(dur)
                        .thenAnimate(arr[3])
                        .scale(0f, 1f)
                        .duration(dur)
                        .thenAnimate(arr[4])
                        .scale(0f, 1f)
                        .duration(dur)
                        .thenAnimate(arr[4])
                        .repeatMode(swingRepeat)
                        .swing()
                        .start()
                        .onStop {
                            end = System.currentTimeMillis() - start
                            listener1?.invoke(5, end)
                        }
                }
                if (arr.size == 6) {
                    ViewAnimator
                        .animate(arr[0])
                        .scale(0f, 1f)
                        .duration(dur)
                        .thenAnimate(arr[1])
                        .scale(0f, 1f)
                        .duration(dur)
                        .thenAnimate(arr[2])
                        .scale(0f, 1f)
                        .duration(dur)
                        .thenAnimate(arr[3])
                        .scale(0f, 1f)
                        .duration(dur)
                        .thenAnimate(arr[4])
                        .scale(0f, 1f)
                        .duration(dur)
                        .thenAnimate(arr[5])
                        .scale(0f, 1f)
                        .duration(dur)
                        .thenAnimate(arr[5])
                        .swing()
                        .repeatCount(swingRepeat)
                        .start()
                        .onStop {
                            end = System.currentTimeMillis() - start
                            listener1?.invoke(6, end)
                        }
                }
            }
        }


        fun godAppearFromTwoPlaces(
            ind: Int, talker: Talker,
            arr: ArrayList<TextView?>,
            arr1: ArrayList<TextView?>,
            arr2: ArrayList<TextView?>
        ) {
            start = System.currentTimeMillis()
            if (ind == 0) {
                ViewAnimator
                    .animate(arr[0])
                    .translationX(-wi / 2, 0f)
                    .translationY(hi, 0f)
                    .scale(0f, 1f)
                    .andAnimate(arr1[0])
                    .translationX(wi / 2, 0f)
                    .translationY(hi, 0f)
                    .scale(0f, 1f)
                    .duration(talker.dur)
                    .start()
                    .onStop {
                        end = System.currentTimeMillis() - start
                        listener1?.invoke(1, end)
                    }
            }
            if (ind == 1) {
                arr[0]?.setBackgroundColor(Color.TRANSPARENT)         // begining just the text
                arr1[0]?.setBackgroundColor(Color.TRANSPARENT)
                ViewAnimator
                    .animate(arr[0])
                    .translationX(-wi / 2, 0f)
                    .translationY(hi, 0f)
                    .scale(0f, 1f)
                    .andAnimate(arr1[0])
                    .translationX(wi / 2, 0f)
                    .translationY(hi, 0f)
                    .scale(0f, 1f)
                    .duration(talker.dur)
                    .thenAnimate(arr2[0])
                    .scale(0f, 1f)
                    .duration(1)
                    .thenAnimate(arr2[0])
                    .alpha(0f, 1f)
                    .duration(3000)
                    .start()
                    .onStop {
                        end = System.currentTimeMillis() - start
                        listener1?.invoke(2, end)
                    }

            }
            if (ind == 2) {
                arr[0]?.setBackgroundColor(Color.TRANSPARENT)         // only the text appear
                arr1[0]?.setBackgroundColor(Color.TRANSPARENT)
                ViewAnimator
                    .animate(arr[0])
                    .translationX(-wi / 2, 0f)
                    .translationY(hi, 0f)
                    .scale(0f, 1f)
                    .andAnimate(arr1[0])
                    .translationX(wi / 2, 0f)
                    .translationY(hi, 0f)
                    .scale(0f, 1f)
                    .duration(talker.dur)
                    // .thenAnimate(arr[0])
                    // .backgroundColor(Color.parseColor(talker.colorBack))
                    .duration(2000)
                    .start()
                    .onStop {
                        end = System.currentTimeMillis() - start
                        listener1?.invoke(3, end)
                    }
            }


        }

/*  fun move_scale(selector: Int, talker: Talker, arr: ArrayList<TextView?>, dur: Long) {

        val linesNum = talker.lines
        if (selector == 0) {
            for (index in 0 until linesNum) {
                arr[index]?.let { item_move_scale(index + 1, it, dur) }
            }
        }

        if (selector == 1) {
            for (index in 1..linesNum) {
                arr[index - 1]?.let {
                    if (index == 1) {
                        item_scale(index, it, dur)
                    } else {
                        item_move_scale(index, it, dur)

                    }
                }
            }
        }
        if (selector == 2) {
            for (index in 1..linesNum) {
                arr[index - 1]?.let {
                    if (index <= 2) {
                        item_scale(index, it, dur)
                    } else {
                        item_move_scale(index, it, dur)

                    }
                }
            }
        }
        if (selector == 3) {
            for (index in 1..linesNum) {
                arr[index - 1]?.let {
                    if (index <= 3) {
                        item_scale(index, it, dur)
                    } else {
                        item_move_scale(index, it, dur)

                    }
                }
            }
        }
        if (selector == 4) {
            for (index in 1..linesNum) {
                arr[index - 1]?.let {
                    if (index <= 4) {
                        item_scale(index, it, dur)
                    } else {
                        item_move_scale(index, it, dur)

                    }
                }
            }
        }
        if (selector == 5) {
            for (index in 1..linesNum) {
                arr[index - 1]?.let {
                    if (index <= 5) {
                        item_scale(index, it, dur)
                    } else {
                        item_move_scale(index, it, dur)

                    }
                }
            }
        }


        if (selector == 6) {
            for (index in 1..linesNum) {
                arr[index - 1]?.let {
                    if (index <= talker.lines - 1) {
                        item_move_scale(index, it, dur)
                    } else {
                        item_scale_swing(index, it, dur)
                    }
                }
            }
        }
    }*/


        private fun ttMove1(
            textView: TextView,
            x0: Float,
            x1: Float,
            y0: Float,
            y1: Float,
            scale0: Float,
            scale1: Float,
            duration: Long
        ) {
            ViewAnimator
                .animate(textView)
                .translationX(x0, x1)
                .translationY(y0, y1)
                .scale(scale0, scale1)
                .duration(duration)
                .start()
        }


        private fun ttScale1(textView: TextView, dur: Long) {
            ViewAnimator.animate(textView).scale(0f, 1f).duration(dur).start()
        }

        fun getCordinateAndSpine(ind: Int): Array<Float> {
            var x0 = 0f;
            var y0 = 0f;
            var rotate = 360f;
            when (ind) {
                1 -> {
                    x0 = -wi / 2
                    y0 = -hi
                    rotate = 720f * 2
                }
                2 -> {
                    x0 = wi / 2
                    y0 = -hi
                    rotate = -720f * 2
                }
                3 -> {
                    x0 = -wi / 2
                    y0 = 0f
                    rotate = 360f
                }
                4 -> {
                    x0 = wi / 2
                    y0 = 0f
                    rotate = -360f
                }
                5 -> {
                    x0 = -wi / 2
                    y0 = hi
                    rotate = 720f * 2
                }
                6 -> {
                    x0 = wi / 2
                    y0 = hi
                    rotate = -720f * 2
                }
            }
            return arrayOf(x0, y0, rotate)
        }

        fun ttMoveAndRotate(kind: Int, ind: Int, textView: TextView, dur: Long) {
            var arr = getCordinateAndSpine(ind)
            if (kind == 30) {
                ViewAnimator
                    .animate(textView)
                    .scale(0f, 1f)
                    .translationX(arr[0], 0f)
                    .translationY(arr[1], 0f)
                    .rotation(arr[2])
                    .duration(dur)
                    .start()
            }
            if (kind == 31) {
                ViewAnimator
                    .animate(textView)
                    .scale(0f, 1f)
                    .translationX(arr[0], 0f)
                    .translationY(arr[1], 0f)
                    .rotation(arr[2])
                    .duration(dur)
                    .thenAnimate(textView)
                    .repeatCount(1)
                    .swing()
                    .start()
            }
            if (kind == 32) {
                ViewAnimator
                    .animate(textView)
                    .scale(0f, 1f)
                    .translationX(arr[0], 0f)
                    .translationY(arr[1], 0f)
                    .rotation(arr[2])
                    .duration(dur)
                    .thenAnimate(textView)
                    .flipHorizontal()
                    .repeatCount(2)
                    .start()
            }
        }

        fun scale10(arr: ArrayList<TextView?>, dur: Long) {
            for (index in 0..5) {
                arr[index]?.let { ttScale1(it, dur) }
            }
        }


        fun scale11(arr: ArrayList<TextView?>, dur: Long) {
            ViewAnimator
                .animate(arr[0])
                .scale(0f, 1f)
                .duration(dur)
                .thenAnimate(arr[1])
                .scale(0f, 1f)
                .duration(dur)
                .thenAnimate(arr[2])
                .scale(0f, 1f)
                .duration(dur)
                .thenAnimate(arr[3])
                .scale(0f, 1f)
                .duration(dur)
                .thenAnimate(arr[4])
                .scale(0f, 1f)
                .duration(dur)
                .thenAnimate(arr[5])
                .scale(0f, 1f)
                .duration(dur)
                .start()
        }


        fun scale12(arr: ArrayList<TextView?>, dur: Long) {
            ViewAnimator
                .animate(arr[0])
                .scale(0f, 1f)
                .duration(dur)
                .thenAnimate(arr[1])
                .scale(0f, 1f)
                .duration(dur)
                .thenAnimate(arr[2])
                .scale(0f, 1f)
                .duration(dur)
                .thenAnimate(arr[3])
                .scale(0f, 1f)
                .duration(dur)
                .thenAnimate(arr[4])
                .scale(0f, 1f)
                .thenAnimate(arr[5])
                .scale(0f, 1f)
                .onStop {
                    ViewAnimator
                        .animate(arr[5])
                        .scale(1f, 0f, 1f)
                        .repeatCount(1)
                        .duration(1000)
                        .start()
                }
                .duration(dur)
                .start()
        }

        fun scale13(arr: ArrayList<TextView?>, dur: Long) {
            ViewAnimator
                .animate(arr[0])
                .scale(0f, 1f)
                .duration(dur)
                .thenAnimate(arr[1])
                .scale(0f, 1f)
                .duration(dur)
                .thenAnimate(arr[2])
                .scale(0f, 1f)
                .duration(dur)
                .thenAnimate(arr[3])
                .scale(0f, 1f)
                .duration(dur)
                .thenAnimate(arr[4])
                .scale(0f, 1f)
                .thenAnimate(arr[5])
                .scale(0f, 1f)
                .onStop {
                    ViewAnimator
                        .animate(arr[5])
                        .swing()
                        .repeatCount(1)
                        .duration(1000)
                        .start()
                }
                .duration(dur)
                .start()
        }


        fun ttMoveAndRotateAndSwing1(ind: Int, textView: TextView, dur: Long) {
            var arr = getCordinateAndSpine(ind)

            ViewAnimator
                .animate(textView)
                .scale(0f, 1f)
                .translationX(arr[0], 0f)
                .translationY(arr[1], 0f)
                .rotation(arr[2])
                .duration(dur)
                .swing()
                .start()
        }


        /*   fun scale_swing( selector:Int,talker: Talker,arr: ArrayList<TextView?>, dur: Long) {

               if (selector==0) {
                   for (index in 0 until arr.size) {
                       arr[index]?.let { item_scale(index + 1, it, dur) }
                   }
               }

               if (selector==1) {
                   for (index in 0 until arr.size) {
                       arr[index]?.let {
                           if (index > 0) {
                               item_scale(index + 1, it, dur)
                           } else {
                               item_scale_swing(index + 1, it, dur)
                           }
                       }
                   }
               }


               if (selector==2) {
                   for (index in 0 until arr.size) {
                       arr[index]?.let {
                           if (index == talker.lines-1) {
                               item_scale_swing(index + 1, it, dur)
                           } else {
                               item_scale(index + 1, it, dur)

                           }
                       }
                   }
               }
               if (selector==3) {
                   for (index in 0 until arr.size) {
                       arr[index]?.let {
                               item_scale_swing(index + 1, it, dur)
                       }
                   }
               }
               *//*     if (selector==4) {
                     for (index in 0 until arr.size) {
                         arr[index]?.let {
                             if (index > 3) {
                                 item_move_scale_rotate(index + 1, it, dur)
                             } else {
                                 item_scale(index + 1, it, dur)
                             }
                         }
                     }
                 }
                 if (selector==5) {
                     for (index in 0 until arr.size) {
                         arr[index]?.let {
                             if (index > 4) {
                                 item_move_scale_rotate(index + 1, it, dur)
                             } else {
                                 item_scale(index + 1, it, dur)
                             }
                         }
                     }
                 }*//*

    }*/


        fun moveAndRotate3(kind: Int, arr: ArrayList<TextView?>, dur: Long) {
            for (index in 0..5) {
                arr[index]?.let { ttMoveAndRotate(kind, index + 1, it, dur) }
            }
        }


    }


/* fun moveAndRotate31(arr: ArrayList<TextView?>, dur: Long) {
        for (index in 0..5) {

            arr[index]?.let { ttMoveAndRotate(1,index + 1, it, dur) }

        }
    }
    fun moveAndRotate32(arr: ArrayList<TextView?>, dur: Long) {
        for (index in 0..5) {

            arr[index]?.let { ttMoveAndRotate(2,index + 1, it, dur) }

        }*/


/*fun move1(textView1: TextView, textView2: TextView) {
    ttMove1(
        textView1, -wi / 2, 0f, -hi, 0f, 0f, 1f, 2000
    )
    ttMove1(
        textView2, wi / 2, 0f, -hi, 0f, 0f, 1f, 2000
    )
}

fun move1g(textView1: TextView, textView2: TextView) {
    ttMove1(
        textView1,
        wi / 2, 0f, hi, 0f, 0f, 2f, 2000
    )
    ttMove1(
        textView2,
        wi / 2, 0f, hi, 0f, 0f, 2f, 2000
    )
}

fun move1god(textView1: TextView, textView2: TextView, dur: Long) {
    ttMove1(textView1, wi / 2, 0f, hi, 0f, 0f, 1f, dur)
    ttMove1(textView2, -wi / 2, 0f, hi, 0f, 0f, 1f, dur)
}
*/



