package com.mjamsek.lozigorbox.repositories

import com.mjamsek.lozigorbox.schema.Nastavitev
import org.springframework.data.jpa.repository.JpaRepository

interface NastavitevRepository: JpaRepository<Nastavitev, Long> {
    
    fun findByKljuc(kljuc: String): Nastavitev
    
}