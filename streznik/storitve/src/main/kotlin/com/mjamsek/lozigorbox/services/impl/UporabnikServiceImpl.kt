package com.mjamsek.lozigorbox.services.impl

import com.mjamsek.lozigorbox.error.EntitetaNeObstajaException
import com.mjamsek.lozigorbox.repositories.UporabnikRepository
import com.mjamsek.lozigorbox.repositories.UporabnikStatusRepository
import com.mjamsek.lozigorbox.schema.Uporabnik
import com.mjamsek.lozigorbox.schema.UporabnikStatus
import com.mjamsek.lozigorbox.schema.UporabnikStatusi
import com.mjamsek.lozigorbox.services.UporabnikService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class UporabnikServiceImpl : UporabnikService {
    
    @Autowired
    private lateinit var uporabnikRepository: UporabnikRepository
    
    @Autowired
    private lateinit var uporabnikStatusRepository: UporabnikStatusRepository
    
    override fun pridobiZId(id: Long): Uporabnik? {
        return uporabnikRepository.getOne(id)
            //.orElseThrow { EntitetaNeObstajaException("Uporabnik z id: $id ne obstaja!") }
    }
    
    override fun pridobiZEmailom(email: String): Uporabnik? {
        return uporabnikRepository.findByEmailAndStatus(email, this.pridobiAktivenStatus())
    }
    
    private fun pridobiAktivenStatus(): UporabnikStatus {
        return uporabnikStatusRepository.findBySifra(UporabnikStatusi.AKTIVEN.sifra)
    }
}