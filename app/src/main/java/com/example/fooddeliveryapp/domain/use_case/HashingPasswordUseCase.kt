package com.example.fooddeliveryapp.domain.use_case

import java.security.MessageDigest
import javax.inject.Inject

class HashingPasswordUseCase @Inject constructor() {
    fun execute(userPassword: String): String {
        val md = MessageDigest.getInstance("SHA-256")
        md.update(userPassword.toByteArray())
        val digest = md.digest()
        return digest.fold("") { str, it -> str + "%02x".format(it) }
    }
}