package com.example.movies2

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.navigation.compose.rememberNavController
import com.example.movies2.ui.theme.MoviesTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

  //  @Inject lateinit var viewModel: HomeViewModel
    //@Inject lateinit var viewModelFactory: ViewModelProvider.Factory

    @OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        lifecycleScope.launch {
//            repeatOnLifecycle(Lifecycle.State.STARTED) {
//                viewModel.nowShowingFlow.collectLatest {  }
//            }
//        }

        setContent {
            MoviesTheme {
                AppScreen(
                    navController = rememberNavController(),
                    windowSize = calculateWindowSizeClass(this)
                )
            }
        }
    }

    override fun onStart() {
        super.onStart()
    }
}



