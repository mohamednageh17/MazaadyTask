package com.example.data.utilis.errors

class UnAuthorizedError(
    override val message: String? = "UnAuthorized"
) : Throwable(message)
