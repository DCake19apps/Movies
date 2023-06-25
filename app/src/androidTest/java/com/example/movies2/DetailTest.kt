package com.example.movies2

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
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
            MovieOverview(state = DetailsState.Ready(mde))
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
    fun test_cast() {
        val cast = (1..20).map {
            CastEntity(
                it, 
                "Name $it",
                "Character $it",
                ""
            )
        }

        composeTestRule.setContent {
            MovieCast(state = CastState.Ready(cast))
        }

        Thread.sleep(5000)
    }
}