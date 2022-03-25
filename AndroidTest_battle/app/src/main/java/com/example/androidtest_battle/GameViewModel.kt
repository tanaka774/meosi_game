package com.example.androidtest_battle

import android.content.Context
import android.graphics.Color
import android.view.View
import androidx.annotation.ColorRes
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch



class GameViewModel : ViewModel() {
    var stopFlagL = true
    var stopFlagM = true
    var stopFlagR = true
    var startFlag = false
    var randomMax = 5
    var fallSpeed : Long = 500

//    private var _numsL = MutableLiveData<Array<Int>>()
//    var numsL: LiveData<Array<Int>>
//        get() = _numsL
    var numsL = arrayOf(1,1,1)
    var numsM = arrayOf(2,2,2)
    var numsR = arrayOf(3,3,3)
//    var numsL = MutableLiveData<Array<Int>>()
//    var numsM = MutableLiveData<Array<Int>>()
//    var numsR = MutableLiveData<Array<Int>>()

    var bingoCount = 0
    var sumCount = MutableLiveData<Int>()
    //↓配列にしたい
    var colorL1 = MutableLiveData<Int>()
    var colorL2 = MutableLiveData<Int>()
    var colorL3 = MutableLiveData<Int>()
    var colorM1 = MutableLiveData<Int>()
    var colorM2 = MutableLiveData<Int>()
    var colorM3 = MutableLiveData<Int>()
    var colorR1 = MutableLiveData<Int>()
    var colorR2 = MutableLiveData<Int>()
    var colorR3 = MutableLiveData<Int>()

    var conglatStr = MutableLiveData<String>()
    var conglatVis = MutableLiveData<Int>()

    var changeFlagL = MutableLiveData<Boolean>()
    var changeFlagM = MutableLiveData<Boolean>()
    var changeFlagR = MutableLiveData<Boolean>()

    var buttonColorL = MutableLiveData<Int>()

    var stopLVisible = MutableLiveData<Boolean>()
    var stopMVisible = MutableLiveData<Boolean>()
    var stopRVisible = MutableLiveData<Boolean>()

    init{
//        numsL.value = arrayOf(0, 0, 0)
//        numsM.value = arrayOf(1, 1, 1)
//        numsR.value = arrayOf(2, 2, 2)
        bingoCount = 0
        sumCount.value = 0

        colorL1.value = Color.BLACK
        colorL2.value = Color.BLACK
        colorL3.value = Color.BLACK
        colorM1.value = Color.BLACK
        colorM2.value = Color.BLACK
        colorM3.value = Color.BLACK
        colorR1.value = Color.BLACK
        colorR2.value = Color.BLACK
        colorR3.value = Color.BLACK

        conglatStr.value = ""
        conglatVis.value = View.INVISIBLE

        changeFlagL.value = false
        changeFlagM.value = false
        changeFlagR.value = false

        buttonColorL.value = Color.BLUE

        stopLVisible.value = true
        stopMVisible.value = true
        stopRVisible.value = true
    }

    fun randomNumberGenerateL() {
//        numsL.value?.set(2, numsL.value!![1])
//        numsL.value?.set(1, numsL.value!![0])
//        numsL.value?.set(0, (0..randomMax).random())
        numsL[2] = numsL[1]
        numsL[1] = numsL[0]
        numsL[0] = (0..randomMax).random()
        changeFlagL.value = changeFlagL.value != true
    }

    fun randomNumberGenerateM() {
//        numsM.value?.set(2, numsM.value!![1])
//        numsM.value?.set(1, numsM.value!![0])
//        numsM.value?.set(0, (0..randomMax).random())
        numsM[2] = numsM[1]
        numsM[1] = numsM[0]
        numsM[0] = (0..randomMax).random()
        changeFlagM.value = changeFlagM.value != true
    }

    fun randomNumberGenerateR() {
//        numsR.value?.set(2, numsR.value!![1])
//        numsR.value?.set(1, numsR.value!![0])
//        numsR.value?.set(0, (0..randomMax).random())
        numsR[2] = numsR[1]
        numsR[1] = numsR[0]
        numsR[0] = (0..randomMax).random()
        changeFlagR.value = changeFlagR.value != true
    }

    fun bingoCheck() :Boolean {
        if(stopFlagL && stopFlagM && stopFlagR && startFlag){
            startFlag = false

//            if((numsL.value!![0] == numsM.value!![0]) &&
                if((numsL[0] == numsM[0]) &&
                (numsM[0] == numsR[0])){
                bingoCount++
                colorL1.value = Color.CYAN
                colorM1.value = Color.CYAN
                colorR1.value = Color.CYAN
            }
//            if((numsL.value!![1] == numsM.value!![1]) &&
            if((numsL[1] == numsM[1]) &&
                (numsM[1] == numsR[1])){
                bingoCount++
                colorL2.value = Color.CYAN
                colorM2.value = Color.CYAN
                colorR2.value = Color.CYAN
            }
            if((numsL[2] == numsM[2]) &&
                (numsM[2] == numsR[2])){
                bingoCount++
                colorL3.value = Color.CYAN
                colorM3.value = Color.CYAN
                colorR3.value = Color.CYAN
            }
            if((numsL[0] == numsM[1]) &&
                (numsM[1] == numsR[2])){
                bingoCount++
                colorL1.value = Color.CYAN
                colorM2.value = Color.CYAN
                colorR3.value = Color.CYAN
            }
            if((numsL[2] == numsM[1]) &&
                (numsM[1] == numsR[0])){
                bingoCount++
                colorL3.value = Color.CYAN
                colorM2.value = Color.CYAN
                colorR1.value = Color.CYAN
            }

            if(bingoCount > 0) {
//                sumCount.value!!.plus(bingoCount)
                sumCount.value = sumCount.value!!.plus(bingoCount)
                return true
            }
        }
        return false
    }

    fun conglaturation(){
        conglatVis.value = View.VISIBLE

        when(bingoCount){
            1 -> conglatStr.value = "おめでとう！"
            2 -> conglatStr.value = "おめでとう！\nけっこうすごい！！"
            3 -> conglatStr.value = "おめでとう！\n信じられない！！"
            4 -> conglatStr.value = "おめでとう！\n噓でしょ！？"
            5 -> conglatStr.value = "おめでとう！\nもう何も教えることはない"
        }
    }
    

    
    fun onStopL(){
        if(!stopFlagL) {
            stopFlagL = true
            stopLVisible.value = false
            buttonColorL.value = Color.GRAY
            if (bingoCheck()) { conglaturation() }
        }
    }
    
    fun onStopM(){
        if(!stopFlagM) {
            stopFlagM = true
            stopMVisible.value = false
            if (bingoCheck()) { conglaturation() }
        }
    }

    fun onStopR(){
        if(!stopFlagR) {
            stopFlagR = true
            stopRVisible.value = false
            if (bingoCheck()) { conglaturation() }
        }
    }

    fun resetAll(){

        bingoCount = 0

//        numsL.value!![0] = (0..randomMax).random()
//        numsL.value!![1] = (0..randomMax).random()
//        numsL.value!![2] = (0..randomMax).random()
        numsL[0] = (0..randomMax).random()
        numsL[1] = (0..randomMax).random()
        numsL[2] = (0..randomMax).random()
        numsM[0] = (0..randomMax).random()
        numsM[1] = (0..randomMax).random()
        numsM[2] = (0..randomMax).random()
        numsR[0] = (0..randomMax).random()
        numsR[1] = (0..randomMax).random()
        numsR[2] = (0..randomMax).random()
        changeFlagL.value = changeFlagL.value != true
        changeFlagM.value = changeFlagM.value != true
        changeFlagR.value = changeFlagR.value != true

        colorL1.value = Color.BLACK
        colorL2.value = Color.BLACK
        colorL3.value = Color.BLACK
        colorM1.value = Color.BLACK
        colorM2.value = Color.BLACK
        colorM3.value = Color.BLACK
        colorR1.value = Color.BLACK
        colorR2.value = Color.BLACK
        colorR3.value = Color.BLACK

        conglatStr.value = ""
        conglatVis.value = View.INVISIBLE
    }

    fun onStartGame(){
        startFlag = true
        resetAll()

        if(stopFlagL) {
            stopFlagL = false
            stopLVisible.value = true
            // TODO: viewmodelscopeに変更？　stop()内でjob.cancel()を行うようにする？ranGen()にsuspend必要？
            GlobalScope.launch {
                while (!stopFlagL) {
                    launch(Dispatchers.Main) {
                        randomNumberGenerateL()
                    }
                    Thread.sleep(fallSpeed)
                }
            }
        }

        if(stopFlagM) {
            stopFlagM = false
            stopMVisible.value = true
            GlobalScope.launch{
                while (!stopFlagM) {
                    launch(Dispatchers.Main) {
                        randomNumberGenerateM()
                    }
                    Thread.sleep(fallSpeed)
                }
            }
        }

        if(stopFlagR) {
            stopFlagR = false
            stopRVisible.value = true
            GlobalScope.launch{
                while (!stopFlagR) {
                    launch(Dispatchers.Main) {
                            randomNumberGenerateR()
                    }
                    Thread.sleep(fallSpeed)
                }
            }
        }
    }
}