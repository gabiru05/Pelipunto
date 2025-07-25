// RUTA: app/src/main/java/com/pelipunto/app/auth/AuthViewModel.kt
package com.pelipunto.app.auth

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.pelipunto.app.R
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed class AuthResult {
    object Idle : AuthResult()
    object Loading : AuthResult()
    data class Success(val user: FirebaseUser?) : AuthResult()
    data class Error(val message: String) : AuthResult()
}

data class AuthState(
    val user: FirebaseUser? = null,
    val result: AuthResult = AuthResult.Idle
)

@HiltViewModel
class AuthViewModel @Inject constructor() : ViewModel() {
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()
    private val _authState = MutableStateFlow(AuthState())
    val authState: StateFlow<AuthState> = _authState.asStateFlow()

    init {
        auth.addAuthStateListener { firebaseAuth ->
            _authState.value = _authState.value.copy(user = firebaseAuth.currentUser)
        }
    }


    fun loginWithEmail(email: String, password: String) {
        _authState.value = _authState.value.copy(result = AuthResult.Loading)
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    _authState.value = _authState.value.copy(result = AuthResult.Success(auth.currentUser))
                } else {
                    _authState.value = _authState.value.copy(result = AuthResult.Error(task.exception?.localizedMessage ?: "Error al iniciar sesión"))
                }
            }
    }

    fun registerWithEmail(name: String, email: String, password: String) {
        _authState.value = _authState.value.copy(result = AuthResult.Loading)
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val user = auth.currentUser
                    user?.updateProfile(com.google.firebase.auth.UserProfileChangeRequest.Builder().setDisplayName(name).build())
                    _authState.value = _authState.value.copy(result = AuthResult.Success(user))
                } else {
                    _authState.value = _authState.value.copy(result = AuthResult.Error(task.exception?.localizedMessage ?: "Error al registrarse"))
                }
            }
    }

    fun loginWithGoogle(idToken: String) {
        _authState.value = _authState.value.copy(result = AuthResult.Loading)
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        auth.signInWithCredential(credential)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    _authState.value = _authState.value.copy(result = AuthResult.Success(auth.currentUser))
                } else {
                    _authState.value = _authState.value.copy(result = AuthResult.Error(task.exception?.localizedMessage ?: "Error con Google"))
                }
            }
    }

    fun logout(context: Context) {
        viewModelScope.launch {
            // Obtenemos el ID de cliente web desde tus recursos, como lo hace MainActivity
            val webClientId = context.getString(R.string.default_web_client_id)

            // 1.Cerramos la sesión del cliente de Google.
            //    Esto es CRUCIAL para que olvide la cuenta anterior y pida elegir de nuevo.
            val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(webClientId)
                .requestEmail()
                .build()
            GoogleSignIn.getClient(context.applicationContext, gso).signOut()

            // 2.Cerramos la sesión en Firebase.
            auth.signOut()
        }
    }

    fun clearResult() {
        _authState.value = _authState.value.copy(result = AuthResult.Idle)
    }
}