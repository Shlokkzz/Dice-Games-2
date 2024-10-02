package androidsamples.java.dicegames;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.clearText;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.pressBack;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isChecked;
import static androidx.test.espresso.matcher.ViewMatchers.isRoot;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;


import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


import androidx.lifecycle.ViewModelProvider;
import androidx.test.espresso.accessibility.AccessibilityChecks;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.filters.LargeTest;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Spy;


/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
@LargeTest
public class ExampleInstrumentedTest {
    @Rule
    public ActivityScenarioRule<MainActivity> activityRule = new ActivityScenarioRule<>(MainActivity.class);

    @Spy
    private GamesViewModel m = mock(GamesViewModel.class);

    private final Die mDie = mock(Die6.class);

    private final Die die1 = mock(Die6.class);
    private final Die die2 = mock(Die6.class);
    private final Die die3 = mock(Die6.class);
    private final Die die4 = mock(Die6.class);

    @BeforeClass
    public static void enableAccessibilityChecks() {
        AccessibilityChecks.enable().setRunChecksFromRootView(true);
    }

    @Test
    public void dummyAccessibilityTest() {
        onView(withId(R.id.btn_die)).perform(click());
    }

    @Test
    public void testRadioButtonSelection() {
        // to gamesFragment
        onView(withId(R.id.btn_games)).perform(click());
        onView(withId(R.id.radio2Alike)).perform(click());
        // Check if the radio button is selected
        onView(withId(R.id.radio2Alike)).check(matches(isChecked()));
    }

    @Test
    public void testAddWagerText(){
        // to gamesFragment
        onView(withId(R.id.btn_games)).perform(click());
        onView(withId(R.id.addWager)).perform(clearText(),typeText("5432"));
        onView(withId(R.id.addWager)).check(matches(withText("5432")));
    }

    // WalletFragments
    @Test
    public void balanceUpdatedOn6(){
        when(mDie.value()).thenReturn(6);

        activityRule.getScenario().onActivity(activity -> {
            m = new ViewModelProvider(activity).get(GamesViewModel.class);
            m.setDie(mDie);
        });
        m.setCurrRoll(3);
        m.setBalance(100);

        onView(withId(R.id.btn_die)).perform(click());
        onView(withId(R.id.txt_balance)).check(matches(withText("105")));
    }

    @Test
    public void balanceNotUpdatedOn5(){
        when(mDie.value()).thenReturn(5);

        activityRule.getScenario().onActivity(activity -> {
            m = new ViewModelProvider(activity).get(GamesViewModel.class);
            m.setDie(mDie);
        });
        m.setCurrRoll(3);
        m.setBalance(100);

        onView(withId(R.id.btn_die)).perform(click());
        onView(withId(R.id.txt_balance)).check(matches(withText("100")));
    }

    // GamesFragments
    @Test
    public void wager5Alike2Balance100Win(){

        when(die1.value()).thenReturn(1);
        when(die2.value()).thenReturn(1);
        when(die3.value()).thenReturn(3);
        when(die4.value()).thenReturn(5);
        activityRule.getScenario().onActivity(activity -> {
            m = new ViewModelProvider(activity).get(GamesViewModel.class);
            m.setDies(die1,die2,die3,die4);
            m.setBalance(100);
            m.setWager(5);
            m.setGameType(GameType.TWO_ALIKE);
        });
        // to gamesFragment
        onView(withId(R.id.btn_games)).perform(click());

        onView(withId(R.id.radio2Alike)).perform(click());
        onView(withId(R.id.addWager)).perform(clearText(), typeText("5"));
        onView((withId(R.id.goButton))).perform(click());

        onView(withId(R.id.coin_counter)).check(matches(withText("110")));
    }

    @Test
    public void wager5Alike3Balance100Lose(){

        when(die1.value()).thenReturn(2);
        when(die2.value()).thenReturn(4);
        when(die3.value()).thenReturn(3);
        when(die4.value()).thenReturn(5);
        activityRule.getScenario().onActivity(activity -> {
            m = new ViewModelProvider(activity).get(GamesViewModel.class);
            m.setDies(die1,die2,die3,die4);
            m.setBalance(100);
            m.setWager(5);
            m.setGameType(GameType.THREE_ALIKE);
        });
        // to gamesFragment
        onView(withId(R.id.btn_games)).perform(click());

        onView(withId(R.id.radio3Alike)).perform(click());
        onView(withId(R.id.addWager)).perform(clearText(), typeText("5"));
        onView((withId(R.id.goButton))).perform(click());

        onView(withId(R.id.coin_counter)).check(matches(withText("85")));
    }

    @Test
    public void BalancePersistsAfterChangingFragments(){

        activityRule.getScenario().onActivity(activity -> {
            m = new ViewModelProvider(activity).get(GamesViewModel.class);
            m.setBalance(100);
        });
        // to gamesFragment
        onView(withId(R.id.btn_games)).perform(click());
        onView((withId(R.id.infoButton))).perform(click());
        onView(isRoot()).perform(pressBack());

        onView(withId(R.id.coin_counter)).check(matches(withText("100")));
        onView(isRoot()).perform(pressBack());

        onView(withId(R.id.txt_balance)).check(matches(withText("100")));
    }
}
