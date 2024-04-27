package com.hossain.wallet.presentation

import android.content.Context
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.credentials.CredentialManager
import androidx.credentials.GetCredentialRequest
import androidx.credentials.exceptions.GetCredentialException
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import com.google.android.libraries.identity.googleid.GoogleIdTokenParsingException
import com.hossain.wallet.data.local.WalletDatabase
import com.hossain.wallet.utils.Constants
import dagger.hilt.android.lifecycle.HiltViewModel
import io.realm.kotlin.mongodb.App
import io.realm.kotlin.mongodb.Credentials
import io.realm.kotlin.mongodb.GoogleAuthType
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(
) : ViewModel() {

    private var _isSignIn by mutableStateOf(false)
    val isSignIn: Boolean = _isSignIn

    init {
        viewModelScope.launch {
            checkedSignIn()
        }
    }

    private suspend fun checkedSignIn() {
        val user = App.create(Constants.APP_ID).currentUser
        if (user != null) {
            WalletDatabase.configRealmDb()
            delay(500)
            _isSignIn = true
            delay(500)
            Log.e("MainTag", "user log on $isSignIn")
        } else {
            _isSignIn = false
            Log.e("MainTag", "user failed $isSignIn")

        }
    }

    fun onSignUpClicked(context: Context) {
        val credentialManager = CredentialManager.create(context)

        val googleIdOption = GetGoogleIdOption.Builder()
            .setFilterByAuthorizedAccounts(false)
            .setServerClientId(Constants.G_CLIENT_ID)
            .build()
        val getCredentialRequest = GetCredentialRequest.Builder()
            .addCredentialOption(googleIdOption)
            .build()
        viewModelScope.launch {
            credentialManager.getCredential(
                context,
                getCredentialRequest
            )
        }
    }


    fun onSignInClicked(context: Context) {
        val credentialManager = CredentialManager.create(context)

        val googleIdOption = GetGoogleIdOption.Builder()
            .setFilterByAuthorizedAccounts(true)
            .setServerClientId(Constants.G_CLIENT_ID)
            .build()

        val getCredentialRequest = GetCredentialRequest.Builder()
            .addCredentialOption(googleIdOption)
            .build()

        viewModelScope.launch {
            try {
                val result = credentialManager.getCredential(
                    context = context,
                    request = getCredentialRequest
                )

                val credentialData = result.credential.data
                val getIdTokenCredential = GoogleIdTokenCredential
                    .createFrom(credentialData)
                val atlasSignResult = App.create(Constants.APP_ID).login(
                    Credentials.google(
                        token = getIdTokenCredential.idToken,
                        type = GoogleAuthType.ID_TOKEN
                    )
                )

                if (atlasSignResult.loggedIn) {
                    WalletDatabase.configRealmDb()
                    delay(500)
                    println("user log on")
                    checkedSignIn()
                } else {
                    println("log on fail")
                    _isSignIn = false
                }

            } catch (e: GetCredentialException) {
                println(e.message)
            } catch (e: GoogleIdTokenParsingException) {
                println(e.message)
            }

        }
    }
}
