package com.example.codewars.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun CodewarsRank(
    level: String,
    color: Color,
) {
    Surface(
        modifier = Modifier.padding(end = 0.dp, bottom = 0.dp),
        shape = CutCornerShape(13.dp),
        border = BorderStroke(
            width = 2.dp,
            color = color
        )
    ) {
        Text(
            modifier = Modifier
                .padding(horizontal = 13.dp, vertical = 5.dp),
            text = level
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewCodewarsRank() {
    CodewarsRank("4 kyu", Color.Red)
}