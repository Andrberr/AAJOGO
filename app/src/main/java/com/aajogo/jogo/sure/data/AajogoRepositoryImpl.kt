package com.aajogo.jogo.sure.data

import com.aajogo.jogo.sure.domain.AajogoRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.net.URL
import javax.inject.Inject

class AajogoRepositoryImpl @Inject constructor() : AajogoRepository {
    override suspend fun getLinkFromAajogoUrl(): String {
        return withContext(Dispatchers.IO) {
            URL("https://aajogoss.cfd/x5svFFvS").readText()
        }
    }
}