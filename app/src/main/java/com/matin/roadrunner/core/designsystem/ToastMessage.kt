package com.matin.roadrunner.core.designsystem

import android.content.Context
import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import com.matin.roadrunner.R
import com.matin.roadrunner.core.common.MessageType
import com.matin.roadrunner.core.common.ToastMessageModel

@Composable
fun ToastMessage(
    message: ToastMessageModel,
    context: Context,
) {
    LaunchedEffect(message) {
        when (message.type) {
            MessageType.EMPTY_CELL -> {
                Toast.makeText(
                    context,
                    context.getString(R.string.this_cell_is_empty),
                    Toast.LENGTH_SHORT,
                ).show()
            }

            else -> {}
        }
    }
}
