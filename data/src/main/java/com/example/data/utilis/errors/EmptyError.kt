package com.example.data.utilis.errors

class EmptyError(
    override val message: String? = "Empty Body",
) : Throwable(message)
