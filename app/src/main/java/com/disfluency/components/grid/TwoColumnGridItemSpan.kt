package com.disfluency.components.grid

import androidx.compose.foundation.lazy.grid.GridItemSpan

/**
 * Custom GridItemSpan to allow grids of two columns
 * to have its last element occupy the width of both columns
 */
fun TwoColumnGridItemSpan(index: Int, listSize: Int): GridItemSpan{
    val lineSpan = if(index == listSize - 1 && listSize % 2 == 1) 2 else 1
    return GridItemSpan(lineSpan)
}