package com.example.moveis_ui


import androidx.compose.material3.windowsizeclass.WindowHeightSizeClass
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.ui.test.assert
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performScrollToIndex
import com.example.moveis_ui.details.CastState
import com.example.moveis_ui.details.DetailsState
import com.example.movie_domain.details.MoviesDetailsEntity
import com.example.movie_domain.people.CastEntity
import org.junit.Rule
import org.junit.Test

class DetailTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun test_overview() {
        val mde = MoviesDetailsEntity(
            "Title",
        "",
        "",
        "Released",
        "2000",
        "7.5",
        "150",
        "$50m",
        "$100m",
        "120",
        listOf("Drama"),
        listOf("English"),
        "This is the test overview."
        )

        composeTestRule.setContent {
            MovieOverview(WindowWidthSizeClass.Compact, state = DetailsState.Ready(mde))
        }

        composeTestRule
            .onNodeWithText("Title")
            .assertIsDisplayed()

        composeTestRule
            .onNodeWithText("2000")
            .assertIsDisplayed()

        composeTestRule
            .onNodeWithText("Revenue: $100m")
            .assertIsDisplayed()
    }

    @Test
    fun test_overview_with_tags() {
        val mde = MoviesDetailsEntity(
            "Title",
            "",
            "",
            "Released",
            "2000",
            "7.5",
            "150",
            "$50m",
            "$100m",
            "120",
            listOf("Drama"),
            listOf("English"),
            "This is the test overview."
        )

        composeTestRule.setContent {
            MovieOverview(WindowWidthSizeClass.Compact, state = DetailsState.Ready(mde))
        }

        composeTestRule
            .onNodeWithTag("title")
            .assert(hasText("Title"))

        composeTestRule
            .onNodeWithTag("rating")
            .assert(hasText("7.5"))

        composeTestRule
            .onNodeWithTag("revenue")
            .assert(hasText("Revenue: $100m"))

        composeTestRule
            .onNodeWithTag("budget")
            .assert(hasText("Budget: $50m"))

        composeTestRule
            .onNodeWithTag("duration")
            .assert(hasText("120 mins"))

        composeTestRule
            .onNodeWithTag("release_date")
            .assert(hasText("2000"))
    }

    @Test
    fun test_cast() {
        val range = (0..20)

        val cast = range.map {
            CastEntity(
                it, 
                "Name $it",
                "Character $it",
                ""
            )
        }

        composeTestRule.setContent {
            MovieCast(WindowWidthSizeClass.Compact, state = CastState.Ready(cast))
        }

        for (position in range) {
            composeTestRule
                .onNodeWithTag("cast_members")
                .performScrollToIndex(position)

            composeTestRule
                .onNodeWithTag("line_1_$position")
                .assert(hasText(cast[position].name))

            composeTestRule
                .onNodeWithTag("line_2_$position")
                .assert(hasText(cast[position].character))
        }
    }



}