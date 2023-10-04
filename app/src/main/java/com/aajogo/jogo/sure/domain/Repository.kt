package com.aajogo.jogo.sure.domain

interface Repository {
    suspend fun makeRequest(): String
}