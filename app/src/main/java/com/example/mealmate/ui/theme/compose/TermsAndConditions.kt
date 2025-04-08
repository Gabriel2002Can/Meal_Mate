package com.example.mealmate.ui.theme.compose

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.mealmate.ui.theme.CustomBackgroundColor

@Composable
fun TermsAndConditions(
    modifier: Modifier = Modifier,
    onAccept: () -> Unit
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier.fillMaxSize().background(CustomBackgroundColor)
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            Column(modifier = Modifier.weight(1f).padding(16.dp)) {
                Text(
                    text = buildAnnotatedString {
                        append("Terms and Conditions\n\n")
                        pushStyle(SpanStyle(fontWeight = FontWeight.Bold))
                        append("Introduction\n")
                        pop()
                        append("Welcome to Meal Mate... etc")
                    },
                    modifier = Modifier.padding(16.dp).weight(1f),
                    color = Color.White
                )

                Button(
                    onClick = { onAccept() },
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                ) {
                    Text(text = "Accept Terms and Conditions")
                }
            }
        }
    }
}