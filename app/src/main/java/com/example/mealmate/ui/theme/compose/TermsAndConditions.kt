package com.example.mealmate.ui.theme.compose

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mealmate.ui.theme.Orange
import com.example.mealmate.ui.theme.lightOrange

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TermsAndConditions(
    modifier: Modifier = Modifier,
    onAccept: () -> Unit
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            TopAppBar(
                title = {
                    Text(
                        text = "Terms & Conditions",
                        color = Color.White,
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center
                    )
                },
                colors = TopAppBarDefaults.smallTopAppBarColors(containerColor = Orange),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
            )

            Column(modifier = Modifier
                .padding(start = 32.dp, end = 32.dp, top = 30.dp)
                .weight(1f)
            ){
                Text(
                    text = "Welcome to Meal Mate!\n\n" +
                            "These Terms and Conditions (“Terms\") govern your access to and use of the Meal Mate application (\"App\"). By using the App, you agree to comply with and be bound by these Terms. Please read them carefully before using the App.\n\n" +
                            "By accessing and using Meal Mate, you agree to these Terms, as well as any additional guidelines or rules that may apply to specific features or services provided through the App.",
                    color = MaterialTheme.colorScheme.onBackground
                )

                Button(
                    onClick = { onAccept() },
                    modifier = Modifier
                        .padding(top = 30.dp)
                        .height(60.dp)
                        .width(110.dp)
                        .align(Alignment.CenterHorizontally),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Orange,
                        contentColor = Color.White
                    )
                ) {
                    Text(
                        text = "Accept",
                        fontSize = 16.sp
                    )
                }
            }
        }
    }
}