package com.hossain.wallet.presentation

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController

@Composable
fun SignInScreen(
    navController: NavController,
    vm: SignInViewModel = viewModel()
) {
    val context = LocalContext.current

    LaunchedEffect(key1 = vm.isSignIn) {
        if (vm.isSignIn) {
            Log.e("MainTag", "user navigate")
            navController.navigate("MainScreen")
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column {
            Button(onClick = { vm.onSignUpClicked(context) }) {
                Text(text = "SignUp With Google")
            }
            Button(onClick = { vm.onSignInClicked(context) }) {
                Text(text = "SignIn With Google")
            }
        }
    }

}