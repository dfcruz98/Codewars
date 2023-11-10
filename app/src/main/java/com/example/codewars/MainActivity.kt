package com.example.codewars

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.codewars.ui.CodewarsApp
import com.example.codewars.ui.theme.CodewarsTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CodewarsTheme{
                CodewarsApp()
            }
        }
    }
}