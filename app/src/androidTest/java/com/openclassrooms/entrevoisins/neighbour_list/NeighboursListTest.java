
package com.openclassrooms.entrevoisins.neighbour_list;

import android.support.test.espresso.ViewInteraction;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import com.openclassrooms.entrevoisins.R;
import com.openclassrooms.entrevoisins.ui.neighbour_list.ListNeighbourActivity;
import com.openclassrooms.entrevoisins.utils.DeleteViewAction;
import com.openclassrooms.entrevoisins.utils.RecyclerViewUtils;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.Espresso.pressBack;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.swipeLeft;
import static android.support.test.espresso.action.ViewActions.swipeRight;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition;
import static android.support.test.espresso.matcher.ViewMatchers.assertThat;
import static android.support.test.espresso.matcher.ViewMatchers.hasMinimumChildCount;
import static android.support.test.espresso.matcher.ViewMatchers.isCompletelyDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withClassName;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withParent;
import static com.openclassrooms.entrevoisins.utils.RecyclerViewItemCountAssertion.withItemCount;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.core.IsNull.notNullValue;



/**
 * Test class for list of neighbours
 */
@RunWith(AndroidJUnit4.class)
public class NeighboursListTest {

    // This is fixed
    private static int ITEMS_COUNT = 12;

    private ListNeighbourActivity mActivity;

    @Rule
    public ActivityTestRule<ListNeighbourActivity> mActivityRule =
            new ActivityTestRule(ListNeighbourActivity.class);

    @Before
    public void setUp() {
        mActivity = mActivityRule.getActivity();
        assertThat(mActivity, notNullValue());
    }

    /**
     * We ensure that our recyclerview is displaying at least on item
     */
    @Test
    public void myNeighboursList_shouldNotBeEmpty() {
        // First scroll to the position that needs to be matched and click on it.
        onView(allOf(isDisplayed(), withId(R.id.list_neighbours)))
                .check(matches(hasMinimumChildCount(1)));
    }

    /**
     * When we delete an item, the item is no more shown
     */
    @Test
    public void myNeighboursList_deleteAction_shouldRemoveItem() {
        // Given : We remove the element at position 2
        onView(allOf(isDisplayed(), withId(R.id.list_neighbours))).check(withItemCount(ITEMS_COUNT));
        // When perform a click on a delete icon
        onView(allOf(isDisplayed(), withId(R.id.list_neighbours)))
                .perform(RecyclerViewActions.actionOnItemAtPosition(1, new DeleteViewAction()));
        // Then : the number of element is 11
        ITEMS_COUNT = ITEMS_COUNT-1;
        onView(allOf(isDisplayed(), withId(R.id.list_neighbours))).check(withItemCount(ITEMS_COUNT));

    }

    @Test
    public void checkIfFavoriteRecyclerViewIsEmpty() {
        onView(withId(R.id.container)).perform(swipeLeft());

        onView(allOf(isDisplayed(), withId(R.id.list_neighbours))).check(new RecyclerViewUtils.ItemCount(0));
    }

    @Test
    public void listNeighbourActivityTest() {
        onView(allOf(isDisplayed(), withId(R.id.list_neighbours))).perform(actionOnItemAtPosition(0, click()));

        onView(ViewMatchers.withId(R.id.activity_details_neighbour_float_favoris)).perform(click());

        pressBack();

        onView(withId(R.id.container)).perform(swipeLeft());

        onView(allOf(isDisplayed(), withId(R.id.list_neighbours))).check(new RecyclerViewUtils.ItemCount(1));

        onView(withId(R.id.container)).perform(swipeRight());

        onView(allOf(isDisplayed(), withId(R.id.list_neighbours))).perform(actionOnItemAtPosition(0, click()));

        onView(ViewMatchers.withId(R.id.activity_details_neighbour_float_favoris)).perform(click());

        pressBack();

        onView(withId(R.id.container)).perform(swipeLeft());

        onView(allOf(isDisplayed(), withId(R.id.list_neighbours))).check(new RecyclerViewUtils.ItemCount(0));
    }

    private static Matcher<View> childAtPosition(
            final Matcher<View> parentMatcher, final int position) {

        return new TypeSafeMatcher<View>() {
            @Override
            public void describeTo(Description description) {
                description.appendText("Child at position " + position + " in parent ");
                parentMatcher.describeTo(description);
            }

            @Override
            public boolean matchesSafely(View view) {
                ViewParent parent = view.getParent();
                return parent instanceof ViewGroup && parentMatcher.matches(parent)
                        && view.equals(((ViewGroup) parent).getChildAt(position));
            }
        };
    }
}