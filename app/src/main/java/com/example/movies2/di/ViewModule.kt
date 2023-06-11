package com.example.movies2.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.CreationExtras
import com.example.moveis_ui.HomeNowShowingState
import com.example.moveis_ui.HomeViewModel
import com.example.moveis_ui.HomeViewModelImpl
import com.example.movie_domain.GetNowShowingMoviesUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import dagger.hilt.android.scopes.ActivityRetainedScoped
import kotlinx.coroutines.flow.MutableStateFlow

@InstallIn(ActivityRetainedComponent::class)
@Module
class ViewModule {

//    @ActivityRetainedScoped
//    @Provides
//    fun provideHomeViewModel(getNowShowingMoviesUseCase: GetNowShowingMoviesUseCase): HomeViewModel {
//        return HomeViewModelImpl(
//            getNowShowingMoviesUseCase,
//            MutableStateFlow(HomeNowShowingState.Loading)
//        )
//    }

//    @ActivityRetainedScoped
//    @Provides
//    fun provideViewModelFactory(
//        getNowShowingMoviesUseCase: GetNowShowingMoviesUseCase
//    ): ViewModelProvider.Factory {
//        return object : ViewModelProvider.Factory {
//            override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T {
//                val ssh = extras.createSavedStateHandle()
//                if (modelClass.isAssignableFrom(HomeViewModelImpl::class.java)) {
//                    return HomeViewModelImpl(
//                        getNowShowingMoviesUseCase,
//                        MutableStateFlow(HomeNowShowingState.Loading)
//                    ) as T
//                }
//                return super.create(modelClass, extras)
//            }
//        }
//    }

}