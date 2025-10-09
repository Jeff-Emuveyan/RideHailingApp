package com.example.ride.di

import com.example.ride.IRideRepository
import com.example.ride.RideRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
internal abstract class RideRepositoryModule {
	
	@Binds
	abstract fun bindCommentsRepository(impl: RideRepository): IRideRepository
}