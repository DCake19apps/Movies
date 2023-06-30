package com.example.movies2

import androidx.compose.runtime.collectAsState
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.lifecycle.SavedStateHandle
import androidx.test.espresso.core.internal.deps.guava.base.Joiner.on
import com.example.moveis_ui.details.CastState
import com.example.moveis_ui.details.CreditsViewModel
import com.example.moveis_ui.details.CrewState
import com.example.moveis_ui.details.DetailsState
import com.example.moveis_ui.details.DetailsViewModel
import com.example.movie_domain.details.MoviesDetailsEntity
import com.example.movie_domain.people.CastEntity
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
//import org.mockito.Mockito.mock
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock

//import org.mockito.kotlin.doReturn

class FullDetailTest {

    @get:Rule
    val composeTestRule = createComposeRule()

//    @Mock
//    lateinit var detailsViewModel: DetailsViewModel
//    @Mock
//    lateinit var creditsViewModel: CreditsViewModel
//@Mock lateinit var myClass: MyClass
//    @Before
//    fun before() {
//        MockitoAnnotations.openMocks(this)
//    }

//    @Test
//    fun test_get_info() {
//      //  Mockito.`when`(myClass.getInfo()).thenReturn("test info")
//
//        val myClass = mock<MyClass> {
//            on { getInfo() } doReturn "test"
//        }
//
//        assert(myClass.getInfo() == "test")
//    }

//    @Test
//    fun test_full_details() {
//        val detailsFlow = MutableStateFlow<DetailsState>(DetailsState.Loading)
//        val castFlow = MutableStateFlow<CastState>(CastState.Loading)
//        val crewFlow = MutableStateFlow<CrewState>(CrewState.Loading)
//
//       // Mockito.`when`(detailsViewModel.getId()).thenReturn(3)
//
//        Mockito.`when`(detailsViewModel.detailsFlow).thenReturn(detailsFlow)
//        Mockito.`when`(creditsViewModel.castFlow).thenReturn(castFlow)
//        Mockito.`when`(creditsViewModel.crewFlow).thenReturn(crewFlow)
//
//        composeTestRule.setContent {
//            MovieDetailsScreen(detailsViewModel, creditsViewModel)
//        }
//
//        Thread.sleep(2000)
//    }

    @Test
    fun test_full_details() {
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
            MovieDetailsScreen(
                DetailsState.Ready(mde),
                CastState.Ready(cast),
                CrewState.Loading
            )
        }

        composeTestRule
            .onNodeWithTag("tab_overview")
            .assertExists()

        composeTestRule
            .onNodeWithTag("tab_cast")
            .assertExists()

        composeTestRule
            .onNodeWithTag("tab_crew")
            .assertExists()

        composeTestRule
            .onNodeWithTag("tab_cast")
            .performClick()

        composeTestRule
            .onNodeWithTag("tab_crew")
            .performClick()

    }

//    @Test
//    fun test_full_details_2() {
//        val movieId = 1
//
//        val ssh = SavedStateHandle()
//        ssh["movieId"] = movieId
//
//
//    }



}