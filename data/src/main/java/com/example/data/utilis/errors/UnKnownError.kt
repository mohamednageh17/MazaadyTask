package com.example.data.utilis.errors

class UnKnownError(
    val code: Int? = null,
    override val message: String? = null,
    val messageEN: String? = null,
) : Throwable(message)
