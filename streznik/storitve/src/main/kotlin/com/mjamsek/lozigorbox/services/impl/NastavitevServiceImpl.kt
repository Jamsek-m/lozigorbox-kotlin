package com.mjamsek.lozigorbox.services.impl

import com.mjamsek.lozigorbox.error.NeznanaNastavitevException
import com.mjamsek.lozigorbox.repositories.NastavitevRepository
import com.mjamsek.lozigorbox.schema.Nastavitev
import com.mjamsek.lozigorbox.services.NastavitevService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class NastavitevServiceImpl : NastavitevService {
    
    @Autowired
    private lateinit var nastavitevRepository: NastavitevRepository
    
    override fun get(kljuc: String): String {
        val nastavitev: Nastavitev = nastavitevRepository.findByKljuc(kljuc) ?: throw NeznanaNastavitevException()
        return nastavitev.vrednost
    }
    
}