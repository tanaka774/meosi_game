package com.example.androidtest_battle

import android.content.Intent
import android.graphics.Color
import android.media.Image
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.androidtest_battle.databinding.FragmentGameBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class GameFragment : Fragment() {

    private lateinit var binding: FragmentGameBinding
    private lateinit var viewModel :GameViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate<FragmentGameBinding>(inflater,
                        R.layout.fragment_game, container, false)

        viewModel = ViewModelProvider(this).get(GameViewModel::class.java)

        binding.gameViewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner


//        viewModel.numsL.observe(viewLifecycleOwner, Observer{
//            binding.numberL1.text = it[0].toString()
//            binding.numberL2.text = it[1].toString()
//            binding.numberL3.text = it[2].toString()
//        })
        viewModel.changeFlagL.observe(viewLifecycleOwner, Observer{
//            binding.numberL1.text = viewModel.numsL.value!![0].toString()
//            binding.numberL2.text = viewModel.numsL.value!![1].toString()
//            binding.numberL3.text = viewModel.numsL.value!![2].toString()
//            binding.numberL1.text = viewModel.numsL[0].toString()
//            binding.numberL2.text = viewModel.numsL[1].toString()
//            binding.numberL3.text = viewModel.numsL[2].toString()
            setImage(viewModel.numsL[0], 'L', 1)
            setImage(viewModel.numsL[1], 'L', 2)
            setImage(viewModel.numsL[2], 'L', 3)
        })

        viewModel.changeFlagM.observe(viewLifecycleOwner, Observer{
            setImage(viewModel.numsM[0], 'M', 1)
            setImage(viewModel.numsM[1], 'M', 2)
            setImage(viewModel.numsM[2], 'M', 3)
        })

        viewModel.changeFlagR.observe(viewLifecycleOwner, Observer{
            setImage(viewModel.numsR[0], 'R', 1)
            setImage(viewModel.numsR[1], 'R', 2)
            setImage(viewModel.numsR[2], 'R', 3)
        })

        viewModel.colorL1.observe(viewLifecycleOwner, Observer { newColor -> binding.numberL1.setBackgroundColor(newColor) })
        viewModel.colorL2.observe(viewLifecycleOwner, Observer { newColor -> binding.numberL2.setBackgroundColor(newColor) })
        viewModel.colorL3.observe(viewLifecycleOwner, Observer { newColor -> binding.numberL3.setBackgroundColor(newColor) })
        viewModel.colorM1.observe(viewLifecycleOwner, Observer { newColor -> binding.numberM1.setBackgroundColor(newColor) })
        viewModel.colorM2.observe(viewLifecycleOwner, Observer { newColor -> binding.numberM2.setBackgroundColor(newColor) })
        viewModel.colorM3.observe(viewLifecycleOwner, Observer { newColor -> binding.numberM3.setBackgroundColor(newColor) })
        viewModel.colorR1.observe(viewLifecycleOwner, Observer { newColor -> binding.numberR1.setBackgroundColor(newColor) })
        viewModel.colorR2.observe(viewLifecycleOwner, Observer { newColor -> binding.numberR2.setBackgroundColor(newColor) })
        viewModel.colorR3.observe(viewLifecycleOwner, Observer { newColor -> binding.numberR3.setBackgroundColor(newColor) })

        viewModel.conglatStr.observe(viewLifecycleOwner, Observer { newText -> binding.conglatText.text = newText })
        viewModel.conglatVis.observe(viewLifecycleOwner, Observer { newVis -> binding.conglatText.visibility = newVis })

        viewModel.stopLVisible.observe(viewLifecycleOwner, Observer { binding.stopButtonL.isEnabled = it })
        viewModel.stopMVisible.observe(viewLifecycleOwner, Observer { binding.stopButtonM.isEnabled = it })
        viewModel.stopRVisible.observe(viewLifecycleOwner, Observer { binding.stopButtonR.isEnabled = it })

        viewModel.sumCount.observe(viewLifecycleOwner, Observer { binding.textCount.text = it.toString() })

        binding.switchSpeed.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) viewModel.fallSpeed = 100
            else viewModel.fallSpeed = 500
        }

        return binding.root
    }


    private fun setImage(num : Int, row : Char, column : Int) {
        lateinit var tmp : ImageView
        if(row == 'L') {
            when(column){
                1 -> tmp = binding.numberL1
                2 -> tmp = binding.numberL2
                3 -> tmp = binding.numberL3
            }
        }

        if(row == 'M') {
            when(column){
                1 -> tmp = binding.numberM1
                2 -> tmp = binding.numberM2
                3 -> tmp = binding.numberM3
            }
        }

        if(row == 'R') {
            when(column){
                1 -> tmp = binding.numberR1
                2 -> tmp = binding.numberR2
                3 -> tmp = binding.numberR3
            }
        }

        when(num) {
            0 -> tmp.setImageResource(R.drawable.number0)
            1 -> tmp.setImageResource(R.drawable.number1)
            2 -> tmp.setImageResource(R.drawable.number2)
            3 -> tmp.setImageResource(R.drawable.number3)
            4 -> tmp.setImageResource(R.drawable.number4)
            5 -> tmp.setImageResource(R.drawable.number5)
        }
    }
}