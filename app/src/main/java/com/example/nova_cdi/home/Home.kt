package com.example.nova_cdi.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column


import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview


@Composable
fun Home(
    modifier: Modifier = Modifier
) {
    Box(modifier) {
        Column {
            Text(
                text = "Home"
            )
            Text(
                text = "Casa"
            )
        }


    }
}

@Preview(name = "Home")
@Composable
private fun PreviewHome() {
    Home()
}