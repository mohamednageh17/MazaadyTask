package com.example.data.utilis.errors

class ConflictError(
    val code: Int? = null,
    override val message: String? = null,
) : Throwable(message)
