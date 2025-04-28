package com.matin.roadrunner.feature.mainqeust.di

import com.matin.roadrunner.feature.mainqeust.GameConfig
import com.matin.roadrunner.feature.mainqeust.engine.MovementStrategy
import com.matin.roadrunner.feature.mainqeust.engine.RandomMovementStrategy
import com.matin.roadrunner.feature.mainqeust.engine.Ticker
import com.matin.roadrunner.feature.mainqeust.engine.TickerImpl
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Qualifier

@Module
@InstallIn(SingletonComponent::class)
interface MainQuestModuleBinder {
    @Binds
    @RandomMovement
    fun bindRandomMovementStrategy(randomMovementStrategy: RandomMovementStrategy): MovementStrategy

    @Binds
    fun bindTicker(tickerImpl: TickerImpl): Ticker
}

@Module
@InstallIn(SingletonComponent::class)
object MainQuestModuleProvider {
    @Provides
    fun provideGameConfig() = GameConfig()
}

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class RandomMovement

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class DirectMovement
