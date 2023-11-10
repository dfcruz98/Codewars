package com.example.codewars.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun CodewarsChip(language: String, modifier: Modifier = Modifier) {
    Box(modifier = modifier.padding(top = 5.dp)) {
        Surface(
            modifier = Modifier.padding(horizontal = 5.dp),
            shape = MaterialTheme.shapes.medium,
            color = MaterialTheme.colorScheme.primary
        ) {
            Text(modifier = Modifier.padding(7.dp), text = language)
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun ChipPreview() {
    CodewarsChip("javascript")
}