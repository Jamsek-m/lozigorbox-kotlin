package com.mjamsek.lozigorbox.services

import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service

@Service
class DevService {
    
    @Value("\${spring.profiles}")
    private lateinit var profil: String
    
    private val DEV_ENV = "dev"
    
    fun isInDevMode(): Boolean {
        return profil == DEV_ENV
    }
    
}