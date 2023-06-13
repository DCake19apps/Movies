package com.example.movies2.di

import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent

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