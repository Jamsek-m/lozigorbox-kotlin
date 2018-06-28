package com.mjamsek.lozigorbox.repositories

import com.mjamsek.lozigorbox.schema.Uporabnik
import com.mjamsek.lozigorbox.schema.UporabnikStatus
import com.mjamsek.lozigorbox.schema.Vloga
import org.springframework.data.jpa.repository.JpaRepository

interface UporabnikRepository : JpaRepository<Uporabnik, Long> {
    
    fun findByEmailAndStatus(email: String, status: UporabnikStatus): Uporabnik?
    
}

interface VlogaRepository : JpaRepository<Vloga, Int>

interface UporabnikStatusRepository: JpaRepository<UporabnikStatus, Int> {
    fun findBySifra(sifra: String): UporabnikStatus
}
