package com.example.androidtest_battle

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import com.example.androidtest_battle.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)
    }
}

// TODO: 2022/01/20 バックやスリープ、フォア復帰の場合などのデータ保存をどうするか
// TODO: 2022/01/20 次画面にranNum渡す?　←上手くいっていない、アプリ落ちる
// TODO: 2022/01/21 ボタン押した後色変える
// TODO: 2022/01/21 stop→resetでカンニングできる問題
// TODO: 2022/01/21 難易度設定（randomMaxの大きさ変更、速さ調整）できるようにする？どの画面で？
// TODO: 2022/01/21 ビンゴ時に揃い線を引きたい
// TODO: 2022/01/21 activity遷移どれでやる？←bingo５のときだけ特別ED？
// TODO: 2022/01/21 起動時にまず説明画面を出したい
// TODO: 2022/01/21 画面回転に対応？
// TODO: 2022/01/21 列間に縦線引く
// TODO: 2022/01/21 フラグ、numなど配列にしたほうがよいのでは？
// TODO: 2022/01/24 layoutmargin入れる、chain入れる
// TODO: 2022/01/24 styleで色を決めるようにしてまとめてテキストの色変えられる？
// TODO: 2022/01/24 whenでsetonclicklistnerまとめる←setListeners()つくる
// TODO: 2022/01/25 各画面をfragment？
// TODO: 2022/01/25 最終画面から”戻る”とスタート画面に行くように？
// TODO: 2022/01/26 binding
// TODO: dataclass
// TODO: 名前を入力して、かかった時間とスコアを記録する？
// TODO: start押した後表示がrestartに？
// TODO: ロジックをonViewCreatedに 
// TODO: conglatテキスト、bingocountと合わせて配列にできるのでは？
// TODO: onclickの関数化、observablearraylistの実装？ 
// TODO: その他もviewmodelに送りたい 
// TODO: ranGen呼ばれるごとにTF変わるフラグのようなものを作ってそれをobserveさせて数字表示を変える？ 

/*
private var stopFlagL = true
    private var stopFlagM = true
    private var stopFlagR = true
    private var randomMax = 3
    private var fallSpeed : Long = 500
    private var preNumL1 = (0..randomMax).random()
    private var preNumL2 = (0..randomMax).random()
    private var preNumM1 = (0..randomMax).random()
    private var preNumM2 = (0..randomMax).random()
    private var preNumR1 = (0..randomMax).random()
    private var preNumR2 = (0..randomMax).random()
    private var bingoCount = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_main)
        val binding = DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)

        findViewById<Button>(R.id.start_button).setOnClickListener {
            resetAll()

            if(stopFlagL) {
                stopFlagL = false
                Thread(Runnable {
                    while (!stopFlagL) {
                        runOnUiThread { randomNumberGenerateL() }
                        Thread.sleep(fallSpeed)
                    }
                }).start()
            }

            if(stopFlagM) {
                stopFlagM = false
                Thread(Runnable {
                    while (!stopFlagM) {
                        runOnUiThread { randomNumberGenerateM() }
                        Thread.sleep(fallSpeed)
                    }
                }).start()
            }

            if(stopFlagR) {
                stopFlagR = false
                Thread(Runnable {
                    while (!stopFlagR) {
                        runOnUiThread { randomNumberGenerateR() }
                        Thread.sleep(fallSpeed)
                    }
                }).start()
            }

        }

        findViewById<Button>(R.id.stop_buttonL).setOnClickListener {
            if(!stopFlagL) {
                stopFlagL = true
                bingoCheck()
                if (bingoCount > 0) {
                    conglaturation()
                }
            }
        }

        findViewById<Button>(R.id.stop_buttonM).setOnClickListener {
            if(!stopFlagM) {
                stopFlagM = true
                bingoCheck()
                if (bingoCount > 0) {
                    conglaturation()
                }
            }
        }

        findViewById<Button>(R.id.stop_buttonR).setOnClickListener {
            if(!stopFlagR) {
                stopFlagR = true
                bingoCheck()
                if (bingoCount > 0) {
                    conglaturation()
                }
            }
        }



        findViewById<Button>(R.id.move_act1).setOnClickListener {
            val intent = Intent(this , SecondActivity::class.java)
//            intent.putExtra("key", powerNum)
            startActivity(intent)
        }


    }

    private fun randomNumberGenerateL() {
        val ranNumL1 = (0..randomMax).random()

        findViewById<TextView>(R.id.numberL1).text = ranNumL1.toString()
        findViewById<TextView>(R.id.numberL2).text = preNumL1.toString()
        findViewById<TextView>(R.id.numberL3).text = preNumL2.toString()

        this.preNumL2 = preNumL1
        this.preNumL1 = ranNumL1
    }

    private fun randomNumberGenerateM() {
        val ranNumM1 = (0..randomMax).random()

        findViewById<TextView>(R.id.numberM1).text = ranNumM1.toString()
        findViewById<TextView>(R.id.numberM2).text = preNumM1.toString()
        findViewById<TextView>(R.id.numberM3).text = preNumM2.toString()

        this.preNumM2 = preNumM1
        this.preNumM1 = ranNumM1
    }

    private fun randomNumberGenerateR() {
        val ranNumR1 = (0..randomMax).random()

        findViewById<TextView>(R.id.numberR1).text = ranNumR1.toString()
        findViewById<TextView>(R.id.numberR2).text = preNumR1.toString()
        findViewById<TextView>(R.id.numberR3).text = preNumR2.toString()

        this.preNumR2 = preNumR1
        this.preNumR1 = ranNumR1
    }


    private fun bingoCheck() {
        if(stopFlagL && stopFlagM && stopFlagR && (findViewById<TextView>(R.id.numberL1).text != "?")){

            val L1 = findViewById<TextView>(R.id.numberL1)
            val L2 = findViewById<TextView>(R.id.numberL2)
            val L3 = findViewById<TextView>(R.id.numberL3)
            val M1 = findViewById<TextView>(R.id.numberM1)
            val M2 = findViewById<TextView>(R.id.numberM2)
            val M3 = findViewById<TextView>(R.id.numberM3)
            val R1 = findViewById<TextView>(R.id.numberR1)
            val R2 = findViewById<TextView>(R.id.numberR2)
            val R3 = findViewById<TextView>(R.id.numberR3)

            if((L1.text == M1.text) && (M1.text == R1.text)){
                bingoCount++
                L1.setTextColor(Color.CYAN)
                M1.setTextColor(Color.CYAN)
                R1.setTextColor(Color.CYAN)
            }
            if((L2.text == M2.text) && (M2.text == R2.text)){
                bingoCount++
                L2.setTextColor(Color.CYAN)
                M2.setTextColor(Color.CYAN)
                R2.setTextColor(Color.CYAN)
            }
            if((L3.text == M3.text) && (M3.text == R3.text)){
                bingoCount++
                L3.setTextColor(Color.CYAN)
                M3.setTextColor(Color.CYAN)
                R3.setTextColor(Color.CYAN)
            }
            if((L1.text == M2.text) && (M2.text == R3.text)){
                bingoCount++
                L1.setTextColor(Color.CYAN)
                M2.setTextColor(Color.CYAN)
                R3.setTextColor(Color.CYAN)
            }
            if((L3.text == M2.text) && (M2.text == R1.text)){
                bingoCount++
                L3.setTextColor(Color.CYAN)
                M2.setTextColor(Color.CYAN)
                R1.setTextColor(Color.CYAN)
            }
        }
    }

    private fun conglaturation(){
        val conglat = findViewById<TextView>(R.id.conglat_text)
        conglat.visibility = View.VISIBLE
        if(bingoCount == 1){
            conglat.text = "おめでとう！"
        }
        else if(bingoCount == 2){
            conglat.text = "おめでとう！\nけっこうすごい！！"
        }
        else if(bingoCount == 3){
            conglat.text = "おめでとう！\n信じられない！！"
        }
        else if(bingoCount == 4){
            conglat.text = "おめでとう！\n噓でしょ！？"
        }
        else if(bingoCount == 5){
            conglat.text = "おめでとう！\nもう何も教えることはない"
        }
    }

    private fun resetAll(){

        bingoCount = 0

        val L1 = findViewById<TextView>(R.id.numberL1)
        val L2 = findViewById<TextView>(R.id.numberL2)
        val L3 = findViewById<TextView>(R.id.numberL3)
        val M1 = findViewById<TextView>(R.id.numberM1)
        val M2 = findViewById<TextView>(R.id.numberM2)
        val M3 = findViewById<TextView>(R.id.numberM3)
        val R1 = findViewById<TextView>(R.id.numberR1)
        val R2 = findViewById<TextView>(R.id.numberR2)
        val R3 = findViewById<TextView>(R.id.numberR3)

        L1.text = (0..randomMax).random().toString()
        L2.text = (0..randomMax).random().toString()
        L3.text = (0..randomMax).random().toString()
        M1.text = (0..randomMax).random().toString()
        M2.text = (0..randomMax).random().toString()
        M3.text = (0..randomMax).random().toString()
        R1.text = (0..randomMax).random().toString()
        R2.text = (0..randomMax).random().toString()
        R3.text = (0..randomMax).random().toString()

        L1.setTextColor(Color.BLACK)
        L2.setTextColor(Color.BLACK)
        L3.setTextColor(Color.BLACK)
        M1.setTextColor(Color.BLACK)
        M2.setTextColor(Color.BLACK)
        M3.setTextColor(Color.BLACK)
        R1.setTextColor(Color.BLACK)
        R2.setTextColor(Color.BLACK)
        R3.setTextColor(Color.BLACK)

        val conglat = findViewById<TextView>(R.id.conglat_text)
        conglat.text = ""
        conglat.visibility = View.INVISIBLE
    }
 */