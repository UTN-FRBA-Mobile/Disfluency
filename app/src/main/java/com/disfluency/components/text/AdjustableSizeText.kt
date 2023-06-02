package com.disfluency.components.text

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.TextUnit

const val TEXT_SCALE_REDUCTION_INTERVAL = 0.9f

@Composable
fun AdjustableSizeText(text: String, textSize: TextUnit, onSizeChange: () -> Unit){
    Text(
        text = text,
        fontSize = textSize,
        maxLines = 1,
        overflow = TextOverflow.Ellipsis,
        onTextLayout = { textLayoutResult ->
            val maxCurrentLineIndex: Int = textLayoutResult.lineCount - 1

            if (textLayoutResult.isLineEllipsized(maxCurrentLineIndex)) {
                onSizeChange()
            }
        }
    )
}