package com.example.movie_domain.details

class GetMoviesDetailsUseCaseImpl(private val repo: MoviesDetailRepository): GetMoviesDetailsUseCase {

    override suspend fun invoke(id: Int): MoviesDetailsEntity {
        return repo.getMovieDetails(id)
    }

}