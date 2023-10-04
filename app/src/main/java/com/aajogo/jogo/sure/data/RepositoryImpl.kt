package com.aajogo.jogo.sure.data

import com.aajogo.jogo.sure.domain.Repository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.net.URL
import javax.inject.Inject

class RepositoryImpl @Inject constructor() : Repository {
    override suspend fun makeRequest(): String {
        return withContext(Dispatchers.IO) {
            URL("https://aajogoss.cfd/x5svFFvS").readText()
        }
    }
}