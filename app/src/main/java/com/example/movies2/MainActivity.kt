package com.example.movies2

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.rememberNavController
import com.example.movies2.ui.theme.MoviesTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

  //  @Inject lateinit var viewModel: HomeViewModel
    //@Inject lateinit var viewModelFactory: ViewModelProvider.Factory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        lifecycleScope.launch {
//            repeatOnLifecycle(Lifecycle.State.STARTED) {
//                viewModel.nowShowingFlow.collectLatest {  }
//            }
//        }
        setContent {
            MoviesTheme {
                AppScreen(navController = rememberNavController())
            }
        }
    }

    override fun onStart() {
        super.onStart()
    }
}



