package com.example.androidtest_battle

//import com.google.common.truth.Truth.*


import android.app.Activity
import android.graphics.Color
import android.graphics.Color.CYAN
import android.graphics.drawable.ColorDrawable
import android.os.Looper.loop
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.core.text.isDigitsOnly
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.testing.TestNavHostController
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.BoundedMatcher
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.androidtest_battle.databinding.ActivityMainBinding
import com.example.androidtest_battle.databinding.FragmentGameBinding
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.Matchers.`is`
import org.junit.Before
//import org.junit.Assert.*
import org.junit.Test
//import org.junit.runner.Description
import org.junit.runner.RunWith
import java.lang.Character.isDigit


//テスト予定
// TODO: 2022/01/21 start, stopを押した後（複数回でも）不自然な挙動にならないか
// TODO: 2022/01/21 ビンゴ判定正しく行えるか 
// TODO: 2022/01/21 スレッドのテストのやり方？何を確認する？
// TODO:

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {
//    @Test
//    fun useAppContext() {
//        // Context of the app under test.
//        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
//        assertEquals("com.example.androidtest_battle", appContext.packageName)
//    }

//    @get :Rule
//    val activityScenarioRule : ActivityScenarioRule<MainActivity> = ActivityScenarioRule(MainActivity::class.java)


//    private lateinit var scenario = launchFragmentInContainer<TitleFragment>()

//    private var gmF = GameFragment()


//    val binding : FragmentGameBinding = TODO()
//    binding.numberL1.text = "1"
//    binding.executeBindings()

//    @Test // TODO: faliする
//    fun testfortest(){
//        launchFragmentInContainer<TitleFragment>()
//        onView(withId(R.id.titleFragment)).check(matches(isDisplayed()))
//    }
//    lateinit var gmVM : GameViewModel
//
//    @Before
//    fun setUp() {
//        gmVM = GameViewModel()
//    }

//    @Test
//    fun stop_button_first_nothing_changes(){
//        launchFragmentInContainer<GameFragment>()
//        onView(withId(R.id.stop_buttonL)).perform(click())
////        onView(withId(R.id.numberL1)).check(matches(withText("0")))
////        onView(withId(R.id.numberL2)).check(matches(withText("0")))
////        onView(withId(R.id.numberL2)).check(matches(withText("0")))
//    }

//    @Test
//    fun start_button_first_numbers_move(){
//        launchFragmentInContainer<GameFragment>()
//        onView(withId(R.id.start_button)).perform(click())
//
////        onView(withId(R.id.numberL1)).check(matches(textIsDigit()))
////        onView(withId(R.id.numberM1)).check(matches(textIsDigit()))
////        onView(withId(R.id.numberR1)).check(matches(textIsDigit()))
//    }

//    @Test // TODO:
//    fun stop_button_twice_after_start_nothing_changes() {
//        val gameScenario = launchFragmentInContainer<GameFragment>()
//
//        gameScenario.onFragment{ fragment ->
//            val viewModel = ViewModelProvider(fragment).get(GameViewModel::class.java)
//
//            onView(withId(R.id.stop_buttonL)).perform(click())
//            val tmp = viewModel.numsL.value!![0]
//            onView(withId(R.id.stop_buttonL)).perform(click())
//            assert(tmp == viewModel.numsL.value!![0] )
//        }
//    }

//    @Test // TODO:
//    fun start_button_again_after_start_nothing_changes() {
//
//    }

//    @Test
//    fun fragmentswitch_titletoGame(){
//        launchFragmentInContainer<TitleFragment>()
//        onView(withId(R.id.play_button)).perform(click())
//        onView(withId(R.id.gameFragment)).check(matches(isDisplayed()))

    @Test // TODO:  import assert注意
    fun navigation_title_to_game(){
        val navController = TestNavHostController(
            ApplicationProvider.getApplicationContext())
        val titleScenario = launchFragmentInContainer<TitleFragment>()

        titleScenario.onFragment{ fragment ->
            navController.setGraph(R.navigation.navigation)
            Navigation.setViewNavController(fragment.requireView(), navController)
        }

        onView(withId(R.id.play_button)).perform(click())
//        assertThat(navController.currentDestination?.id).isEqualTo(R.id.gameFragment) //truth
        assertThat((navController.currentDestination?.id), `is`(R.id.gameFragment))
    }

    @Test // TODO:  
    fun background_color_change_at_bingo(){
        val gameScenario = launchFragmentInContainer<GameFragment>()
        val gmF = GameFragment()

        gameScenario.onFragment{ fragment ->
            val viewModel = ViewModelProvider(fragment).get(GameViewModel::class.java)

//            assert(viewModel.numsL.value!![0] == 0)
            viewModel.startFlag = true
            viewModel.numsL[0] = 1
            viewModel.numsM[0] = 1
            viewModel.numsR[0] = 1


            assert(viewModel.bingoCheck())
            assert(viewModel.bingoCount > 0)
        }

        onView(withId(R.id.numberL1)).check(matches(backgroundColorMatcher(Color.CYAN)))
    }

    @Test
    fun bingoCount_become5_on_all1(){
        val gameScenario = launchFragmentInContainer<GameFragment>()

        gameScenario.onFragment{ fragment ->
            val viewModel = ViewModelProvider(fragment).get(GameViewModel::class.java)

            viewModel.startFlag = true
            viewModel.numsL[0] = 1
            viewModel.numsM[0] = 1
            viewModel.numsR[0] = 1
            viewModel.numsL[1] = 1
            viewModel.numsM[1] = 1
            viewModel.numsR[1] = 1
            viewModel.numsL[2] = 1
            viewModel.numsM[2] = 1
            viewModel.numsR[2] = 1

            assert(viewModel.bingoCheck())
            assert(viewModel.bingoCount == 5)
        }
    }


    @Test
    fun conglat_text_show_correctly_count0(){
        val gameScenario = launchFragmentInContainer<GameFragment>()

        gameScenario.onFragment { fragment ->
            val viewModel = ViewModelProvider(fragment).get(GameViewModel::class.java)
            viewModel.bingoCount = 0
            viewModel.conglaturation()
        }
        onView((withId(R.id.conglat_text))).check(matches(withText("")))
    }

    @Test
    fun conglat_text_show_correctly_count1(){
        val gameScenario = launchFragmentInContainer<GameFragment>()

        gameScenario.onFragment { fragment ->
            val viewModel = ViewModelProvider(fragment).get(GameViewModel::class.java)
            viewModel.bingoCount = 1
            viewModel.conglaturation()
        }
        onView((withId(R.id.conglat_text))).check(matches(withText("おめでとう！")))
    }

    @Test
    fun conglat_text_show_correctly_count5(){
        val gameScenario = launchFragmentInContainer<GameFragment>()

        gameScenario.onFragment { fragment ->
            val viewModel = ViewModelProvider(fragment).get(GameViewModel::class.java)
            viewModel.bingoCount = 5
            viewModel.conglaturation()
        }
        onView((withId(R.id.conglat_text))).check(matches(withText("おめでとう！\nもう何も教えることはない")))
    }

    @Test // TODO:
    fun start_and_stop_until_bingo(){
        val gameScenario = launchFragmentInContainer<GameFragment>()
//        val gmVM = GameViewModel()

        onView(withId(R.id.switch_speed)).perform(click())

        for (i in 0..2) {
            onView(withId(R.id.start_button)).perform(click())
            Thread.sleep(200)
            onView(withId(R.id.stop_buttonL)).perform(click())
            onView(withId(R.id.stop_buttonM)).perform(click())
            onView(withId(R.id.stop_buttonR)).perform(click())
        }

        gameScenario.onFragment { fragment ->
            val viewModel = ViewModelProvider(fragment).get(GameViewModel::class.java)
            //            assert(viewModel.bingoCount > 0)
//                if (viewModel.bingoCount > 0) break
        }

    }

    @Test
    fun button_enabled_test() {
        launchFragmentInContainer<GameFragment>()
        onView(withId(R.id.stop_buttonL)).perform(click())
        onView(withId(R.id.stop_buttonL)).check(matches(buttonIsEnabled(true)))

        onView(withId(R.id.start_button)).perform(click())
        onView(withId(R.id.stop_buttonL)).perform(click())
        onView(withId(R.id.stop_buttonL)).check(matches(buttonIsEnabled(false)))

        onView(withId(R.id.start_button)).perform(click())
        onView(withId(R.id.stop_buttonL)).check(matches(buttonIsEnabled(true)))
    }

}

// TODO:  
fun textViewTextColorMatcher(matcherColor: Int): Matcher<View?>? {
    return object : BoundedMatcher<View?, TextView>(TextView::class.java) {
        override fun describeTo(description: Description) {
            description.appendText("with text color: $matcherColor")
        }

        override fun matchesSafely(textView: TextView): Boolean {
            return matcherColor == textView.currentTextColor
        }
    }
}
//how to use it
//onView(withId(R.id.search_action_button)).check(matches(textViewTextColorMatcher(TEXT_BTN_COLOR_DISABLED)));

fun backgroundColorMatcher(matcherColor: Int): Matcher<View?>? {
    return object : BoundedMatcher<View?, ImageView>(ImageView::class.java) {
        override fun describeTo(description: Description) {
            description.appendText("with background color: $matcherColor")
        }

        override fun matchesSafely(imageView: ImageView): Boolean {
            val back = imageView.background as ColorDrawable
            return matcherColor == back.color
        }
    }
}

fun textIsDigit(): Matcher<View?>? {
    return object : BoundedMatcher<View?, TextView>(TextView::class.java){
        override fun describeTo(description: Description?) {
            description?.appendText("this is not digit")
        }

        override fun matchesSafely(item: TextView?): Boolean {
            return (item?.text)!!.isDigitsOnly()
        }

//        fun isInteger(input : String) {
//            val intChars = '0'..'9'
//            input.all {it in intChars}
//        }
    }
}
fun buttonIsEnabled(bool : Boolean): Matcher<View?>? {
    return object : BoundedMatcher<View?, Button>(Button::class.java) {
        override fun describeTo(description: Description) {
            description.appendText("this button isn't $bool")
        }

        override fun matchesSafely(button: Button): Boolean {
            return button.isEnabled == bool
        }
    }
}



