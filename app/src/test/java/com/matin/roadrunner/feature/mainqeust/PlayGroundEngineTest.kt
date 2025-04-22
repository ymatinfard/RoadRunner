package com.matin.roadrunner.feature.mainqeust

import org.junit.Assert.assertEquals
import org.junit.Test

class PlayGroundEngineTest {

    @Test
    fun `getPlaygroundCellState returns list with correct size`() {
        assertEquals(10, PlayGroundEngine(10, 5).getPlaygroundCellState().size)
    }

    @Test
    fun `getPlaygroundCellState returns list with correct driver count`() {
        assertEquals(5, PlayGroundEngine(10, 5).getPlaygroundCellState().flatten().count { it.driver != null })
    }
}