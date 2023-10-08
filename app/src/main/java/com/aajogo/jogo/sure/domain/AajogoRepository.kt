package com.aajogo.jogo.sure.domain

interface AajogoRepository {
    suspend fun getLinkFromAajogoUrl(): String
    fun initAajogoRep() {
        println("This is Aajogo Repository")
    }
}