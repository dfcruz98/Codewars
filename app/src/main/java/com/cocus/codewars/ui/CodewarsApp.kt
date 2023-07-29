package com.cocus.codewars.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.cocus.codewars.ui.navigation.CodewarsNaveHost

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CodewarsApp(
    modifier: Modifier = Modifier
) {

    val navController = rememberNavController()

    Surface(
        color = MaterialTheme.colorScheme.background,
        modifier = Modifier.fillMaxSize(),
    ) {
        Scaffold(
            modifier = modifier
        ) { paddings ->
            CodewarsNaveHost(
                navController = navController,
                modifier = Modifier.padding(paddings),
            )
        }
    }


}