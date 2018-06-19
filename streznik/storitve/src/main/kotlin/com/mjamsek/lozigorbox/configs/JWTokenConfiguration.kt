package com.mjamsek.lozigorbox.configs

import com.mjamsek.lozigorbox.schema.NastavitevConstants
import com.mjamsek.lozigorbox.services.NastavitevService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class JWTokenConfiguration {
    
    @Autowired
    private lateinit var nastavitevService: NastavitevService
    
    fun getHeader(): String {
        return nastavitevService.get(NastavitevConstants.JWT_HEADER.kljuc)
    }
    
    fun getSecret(): String {
        return nastavitevService.get(NastavitevConstants.JWT_SECRET.kljuc)
    }
    
}
