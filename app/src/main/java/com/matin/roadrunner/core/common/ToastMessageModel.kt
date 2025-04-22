package com.matin.roadrunner.core.common

data class ToastMessageModel(
    val type: MessageType = MessageType.NOTHING,
    val timeStamp: Long = System.currentTimeMillis(),
)

enum class MessageType {
    EMPTY_CELL,
    NOTHING,
}
