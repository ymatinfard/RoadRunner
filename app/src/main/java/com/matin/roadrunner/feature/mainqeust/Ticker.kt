package com.matin.roadrunner.feature.mainqeust

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import javax.inject.Inject

interface Ticker {
    val tick: StateFlow<Long>

    fun start(scope: CoroutineScope)

    fun stop()
}

class TickerImpl
    @Inject
    constructor() : Ticker {
        private val _tick = MutableStateFlow(0L)
        override val tick: StateFlow<Long> = _tick.asStateFlow()

        private val delayMill = 2000L
        private var job: Job? = null

        override fun start(scope: CoroutineScope) {
            stop()
            job =
                scope.launch {
                    while (isActive) {
                        _tick.value++
                        delay(delayMill)
                    }
                }
        }

        override fun stop() {
            job?.cancel()
            job = null
        }
    }
