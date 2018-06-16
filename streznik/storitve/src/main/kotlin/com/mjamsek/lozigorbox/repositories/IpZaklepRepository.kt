package com.mjamsek.lozigorbox.repositories

import com.mjamsek.lozigorbox.schema.IpZaklep
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import java.util.*
import org.springframework.data.jpa.repository.Modifying
import javax.transaction.Transactional


interface IpZaklepRepository: JpaRepository<IpZaklep, Long> {
    
    @Query("SELECT COUNT(p.id) FROM IpZaklep p WHERE p.ipNaslov = :ip AND p.datumPrijave > :datum")
    fun prestejSteviloPrijav(@Param("ip") ip: String, @Param("datum") datum: Date): Int
    
    @Transactional
    @Modifying
    @Query("DELETE FROM IpZaklep p WHERE p.ipNaslov = :ip")
    fun izbrisiVsePrijaveIPja(@Param("ip") ip: String)
    
}