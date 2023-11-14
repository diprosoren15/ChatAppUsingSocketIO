package com.example.chatappusingsocketio.others

sealed class Resource<T> {
    class Success<T>(val data: T? = null) : Resource<T>()

    class Error<T> (val data : T? = null) : Resource<T>()
}