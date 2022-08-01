package com.example.sandbox.main.ui.login

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color.Companion.Blue
import androidx.compose.ui.graphics.Color.Companion.Green
import androidx.compose.ui.graphics.Color.Companion.Red
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import com.example.sandbox.R
import com.example.sandbox.main.ui.material.SandboxTheme

@Composable
fun Loader() {
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.wumpus_hi))
    Box(
        modifier = Modifier.fillMaxWidth().background(Green),
        contentAlignment = Alignment.TopCenter
    ) {
        LottieAnimation(
            composition,
            iterations = LottieConstants.IterateForever
        )
    }
}

@Composable
fun OutLineTextFieldUserName() {
    var text by remember { mutableStateOf(TextFieldValue("")) }
    OutlinedTextField(
        value = text,
        modifier = Modifier.fillMaxWidth().padding(20.dp, 0.dp),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
        label = { Text(text = "Enter Your username") },
        placeholder = { Text(text = "admin") },
        onValueChange = {
            text = it
        }
    )
}

@Composable
fun OutLineTextFieldPassword() {
    var text by remember { mutableStateOf(TextFieldValue("")) }
    OutlinedTextField(
        value = text,
        modifier = Modifier.fillMaxWidth().padding(20.dp, 0.dp),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
        label = { Text(text = "Enter Your password") },
        placeholder = { Text(text = "admin") },
        trailingIcon = { Icon(imageVector = Icons.Default.Add, contentDescription = null) },
        isError = true,
        onValueChange = {
            text = it
        }
    )
}

@Preview(showBackground = true)
@Composable
fun PreviewLoader() {
    SandboxTheme {
        Column {
            Loader()
            OutLineTextFieldUserName()
            OutLineTextFieldPassword()
        }
    }
}
