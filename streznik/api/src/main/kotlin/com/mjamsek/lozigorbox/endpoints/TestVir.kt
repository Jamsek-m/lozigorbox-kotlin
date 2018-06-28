package com.mjamsek.lozigorbox.endpoints

import com.mjamsek.lozigorbox.authentication.JWTokenService
import com.mjamsek.lozigorbox.authentication.JeAvtenticiran
import com.mjamsek.lozigorbox.repositories.UporabnikRepository
import com.mjamsek.lozigorbox.schema.Uporabnik
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.*
import javax.servlet.http.HttpSession

@RestController
@RequestMapping("api/v1")
class TestVir(val serv: UporabnikRepository) {
    
    private final val logger: Logger = LoggerFactory.getLogger(TestVir::class.java)
   
    @Autowired
    lateinit var session: HttpSession
    
    @JeAvtenticiran
    @GetMapping("", "/")
    fun index(): ResponseEntity<Any> {
        logger.info("=========> Seja je bila ustvarjena ob {} in poteče ob {}", Date(session.creationTime), Date(session.maxInactiveInterval*1000 + session.creationTime))
        session.creationTime
 
        val hash: HashMap<String, Any> = HashMap()
        hash["uporabnik"] = Uporabnik()
        return ResponseEntity.ok(hash)
    }
    
    @GetMapping("echo")
    fun echo(): ResponseEntity<Any> {
        val hash: HashMap<String, String> = HashMap()
        hash["cmd"] = "ECHO"
        return ResponseEntity.ok(hash)
    }
    
    
}