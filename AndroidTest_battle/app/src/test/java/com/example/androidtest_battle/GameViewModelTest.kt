package com.example.androidtest_battle

import android.content.Context
import android.graphics.Color
import android.view.View
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.lifecycle.ViewModelProvider
import androidx.test.core.app.ApplicationProvider
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import io.mockk.*
import io.mockk.impl.annotations.MockK
//import androidx.test.platform.app.InstrumentationRegistry
//import org.junit.Assert.*
//import org.junit.Rule
//
////import org.junit.Test
//import org.junit.jupiter.api.Test
//import org.junit.jupiter.api.BeforeEach
import org.junit.runner.RunWith
//import org.junit.runner.manipulation.Ordering

import org.junit.Test
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.rules.TestRule
import org.mockito.BDDMockito
import org.mockito.BDDMockito.*
import org.mockito.BDDMockito.given
import org.mockito.Mock

//@RunWith(AndroidJUnit4ClassRunner::class) //これ入れるとNo instrumentation registered!←入れなくても？？？

class GameViewModelTest {
    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()


//    @Mock
    lateinit var gmVM : GameViewModel
//    private var gmVM = GameViewModel()

    @Before
    fun setUp() {
//        val context = ApplicationProvider.getApplicationContext<Context>()
        gmVM = GameViewModel()
    }

    @Test
    fun onStopL_flag_is_false_without_bingo(){
        gmVM.stopFlagL = false
//        given(gmVM.bingoCheck()).willReturn(true)
        gmVM.onStopL()
        assertTrue(gmVM.stopFlagL)
        assertEquals(gmVM.bingoCount, 0)
//        verify { gmVM.conglaturation() }
//        verify { gmVM.bingoCheck() }
//        verify(gmVM.conglaturation(), times(1))

//        val gmVMMockK = mockk<GameViewModel>()
//        every { gmVMMockK.stopFlagL} returns false
//        every { gmVMMockK.bingoCheck()} returns true
//        gmVM.onStopL()
//        assertTrue(gmVM.stopFlagL)
//        verify { gmVM.conglaturation() }
    }
    @Test
    fun onStopL_flag_is_true_without_bingo() {
        gmVM.stopFlagL = true
        gmVM.onStopL()
        assertTrue(gmVM.stopFlagL)
//        assertTrue(!gmVM.bingoCheck())
        assertEquals(gmVM.bingoCount, 0)
    }
    @Test
    fun onStopL_flag_is_false_with_bingo() {
        gmVM.stopFlagL = false
//        given(gmVM.bingoCheck()).willReturn(true)
        gmVM.numsL[0] = 1
        gmVM.numsM[0] = 1
        gmVM.numsR[0] = 1
        gmVM.stopFlagM = true
        gmVM.stopFlagR = true
        gmVM.startFlag = true
        gmVM.onStopL()
        assertTrue(gmVM.stopFlagL)
//        assertTrue(gmVM.bingoCheck())
        assertEquals(gmVM.bingoCount, 1)
        assertEquals(gmVM.conglatStr.value, "おめでとう！")
        assertEquals(gmVM.conglatVis.value, View.VISIBLE)
//        verify(exactly = 0) {
//            gmVM.conglaturation()
//        }
    }
    @Test
    fun onStopL_flag_is_true_with_bingo() {
        gmVM.stopFlagL = true
//        given(gmVM.bingoCheck()).willReturn(true)
        gmVM.numsL[0] = 1
        gmVM.numsM[0] = 1
        gmVM.numsR[0] = 1
        gmVM.stopFlagM = true
        gmVM.stopFlagR = true
        gmVM.startFlag = true
        gmVM.onStopL()
        assertTrue(gmVM.stopFlagL)
//        assertTrue(gmVM.bingoCheck())
        assertEquals(gmVM.bingoCount, 0)
//        verify { gmVM.conglaturation() }
    }

    @Test
    fun bingoCheck_default_return_false(){
        assertTrue(!gmVM.bingoCheck())
        assertEquals(gmVM.bingoCount, 0)
        assertEquals(gmVM.colorL1.value, Color.BLACK)
    }

    @Test
    fun bingoCheck_count1_return_true(){
        gmVM.stopFlagL = true
        gmVM.stopFlagM = true
        gmVM.stopFlagR = true
        gmVM.startFlag = true

        gmVM.numsL[0] = 1
        gmVM.numsM[0] = 1
        gmVM.numsR[0] = 1

        assertTrue(gmVM.bingoCheck())
        assertEquals(gmVM.bingoCount, 1)
        assertEquals(gmVM.colorL1.value, Color.CYAN)
    }

    @Test
    fun bingoCheck_count1_R_moving_return_false(){
        gmVM.stopFlagL = true
        gmVM.stopFlagM = true
        gmVM.stopFlagR = false
        gmVM.startFlag = true

        gmVM.numsL[0] = 1
        gmVM.numsM[0] = 1
        gmVM.numsR[0] = 1

        assertTrue(!gmVM.bingoCheck())
        assertEquals(gmVM.bingoCount, 0)
        assertEquals(gmVM.colorL1.value, Color.BLACK)
//        assertTrue(gmVM.bingoCheck())
//        assertEquals(gmVM.bingoCount, 1)
//        assertEquals(gmVM.colorL1.value, Color.CYAN)
    }

    @Test
    fun bingoCheck_count2_return_true(){
        gmVM.stopFlagL = true
        gmVM.stopFlagM = true
        gmVM.stopFlagR = true
        gmVM.startFlag = true

        gmVM.numsL[0] = 1
        gmVM.numsM[0] = 1
        gmVM.numsR[0] = 1
        gmVM.numsM[1] = 1
        gmVM.numsR[2] = 1

        assertTrue(gmVM.bingoCheck())
        assertEquals(gmVM.bingoCount, 2)
        assertEquals(gmVM.colorL1.value, Color.CYAN)
        assertEquals(gmVM.colorM2.value, Color.CYAN)
        assertEquals(gmVM.colorR3.value, Color.CYAN)
    }

    @Test
    fun bingoCheck_count5_return_true(){
        gmVM.stopFlagL = true
        gmVM.stopFlagM = true
        gmVM.stopFlagR = true
        gmVM.startFlag = true

        gmVM.numsL[0] = 1
        gmVM.numsL[1] = 1
        gmVM.numsL[2] = 1
        gmVM.numsM[0] = 1
        gmVM.numsM[1] = 1
        gmVM.numsM[2] = 1
        gmVM.numsR[0] = 1
        gmVM.numsR[1] = 1
        gmVM.numsR[2] = 1

        assertTrue(gmVM.bingoCheck())
        assertEquals(gmVM.bingoCount, 5)
        assertEquals(gmVM.colorL1.value, Color.CYAN)
        assertEquals(gmVM.colorM2.value, Color.CYAN)
        assertEquals(gmVM.colorR3.value, Color.CYAN)
    }



//    @Test
//    fun stopFlagL_spy(){
//        val gmVMSpy = spyk<GameViewModel>()
//        every { gmVMSpy.stopFlagL} returns false
//        every { gmVMSpy.bingoCheck()} returns true
//        every { gmVMSpy.conglaturation()} returns Unit
//        every { gmVMSpy.onStopL() } answers {callOriginal()}
//
//        gmVMSpy.onStopL()
//        verify { gmVMSpy.onStopL() }
////        assertTrue(!gmVMSpy.stopFlagL)
////        verify(exactly = 1) { gmVMSpy.bingoCheck() }
////        verify(exactly = 1) { gmVMSpy.conglaturation() }
//    }

//    @Test
//    fun stopFlagL_mock() {
//        val gmVMMockK = mockk<GameViewModel>()
//        every { gmVMMockK.stopFlagL} returns false
//        every { gmVMMockK.bingoCheck()} returns true
//        gmVMMockK.onStopL()
//        assertTrue(gmVMMockK.stopFlagL)
////        verify { gmVMMockK.bingoCheck() }
////        verify { gmVMMockK.conglaturation() }
//    }

//    @Test // TODO:
//    fun bingo_on_upperline_bingoCount_1() {
//
//        assertEquals(2, 1 + 1)

//        gmVM.numsL.value!![0] = 1
//        gmVM.numsM.value!![0] = 1
//        gmVM.numsR.value!![0] = 1
//        assertTrue(!gmVM.bingoCheck())

//        val gameScenario = launchFragmentInContainer<GameFragment>()
//
//        gameScenario.onFragment { fragment ->
//            val viewModel = ViewModelProvider(fragment).get(GameViewModel::class.java)
//
//            viewModel.numsL.value!![0] = 1
//            assert(viewModel.numsL.value!![0] == 1)

//            assert(viewModel.numsL.value!![0] == 0)
//            viewModel.startFlag = true
//            viewModel.numsL.value!![0] = 1
//            viewModel.numsM.value!![0] = 1
//            viewModel.numsR.value!![0] = 1
//
//            assertTrue(viewModel.bingoCheck())
//            assertTrue(viewModel.bingoCount == 1)
//      }
//    }

}