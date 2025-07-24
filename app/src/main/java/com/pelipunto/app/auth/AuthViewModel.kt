package com.pelipunto.app.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
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
        // Verifica si ya hay usuario autenticado
        _authState.value = _authState.value.copy(user = auth.currentUser)
    }

    fun loginWithEmail(email: String, password: String) {
        _authState.value = _authState.value.copy(result = AuthResult.Loading)
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    _authState.value = AuthState(user = auth.currentUser, result = AuthResult.Success(auth.currentUser))
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
                    // Actualiza el nombre del usuario
                    val user = auth.currentUser
                    user?.updateProfile(com.google.firebase.auth.UserProfileChangeRequest.Builder().setDisplayName(name).build())
                    _authState.value = AuthState(user = user, result = AuthResult.Success(user))
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
                    _authState.value = AuthState(user = auth.currentUser, result = AuthResult.Success(auth.currentUser))
                } else {
                    _authState.value = _authState.value.copy(result = AuthResult.Error(task.exception?.localizedMessage ?: "Error con Google"))
                }
            }
    }

    fun logout() {
        auth.signOut()
        _authState.value = AuthState(user = null, result = AuthResult.Idle)
    }

    fun clearResult() {
        _authState.value = _authState.value.copy(result = AuthResult.Idle)
    }
}
