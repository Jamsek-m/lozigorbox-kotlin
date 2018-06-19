package com.mjamsek.lozigorbox.authentication.impl

import com.mjamsek.lozigorbox.authentication.JWTokenService
import com.mjamsek.lozigorbox.configs.JWTokenConfiguration
import com.mjamsek.lozigorbox.objects.JWToken
import com.mjamsek.lozigorbox.objects.JWTokenConstants
import com.mjamsek.lozigorbox.schema.NastavitevConstants
import com.mjamsek.lozigorbox.schema.Uporabnik
import com.mjamsek.lozigorbox.schema.Vloga
import com.mjamsek.lozigorbox.services.NastavitevService
import io.jsonwebtoken.Claims
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.util.*
import javax.servlet.http.HttpSession

@Service
class JWTokenServiceImpl: JWTokenService {
    
    @Autowired
    lateinit var jwtConfig: JWTokenConfiguration
    
    @Autowired
    private lateinit var session: HttpSession
    
    @Autowired
    private lateinit var nastavitevService: NastavitevService
    
    override fun generirajJWToken(uporabnik: Uporabnik): String {
        val sekunde: Int = nastavitevService.get(NastavitevConstants.JWT_VELJAVNOST.kljuc).toInt()
        return this.generirajJWToken(uporabnik, sekunde)
    }
    
    override fun generirajJWToken(uporabnik: Uporabnik, sekunde: Int): String {
        val zeton: JWToken = JWToken.new()
            .setSession(session)
            .setExpirationDate(sekunde)
            .setHeader(jwtConfig.getHeader())
            .setSecret(jwtConfig.getSecret())
            .setUser(uporabnik)
            .sign()
        return zeton.getToken()
    }
    
    override fun validirajZeton(token: String): Boolean {
        val zeton: JWToken = extractToken(token)
    
        val data: Claims? = zeton.getData()
        return if (data == null) {
            false
        } else {
            val sessionId: String = data[JWTokenConstants.SESSION_ID.field].toString()
            return zeton.isValid() && sessionId == session.id
        }
    }
    
    override fun getVeljavnostZetona(token: String): Date? {
        val zeton: JWToken = extractToken(token)
        val data: Claims? = zeton.getData()
        return if (data == null) {
            null
        } else {
            Date(data[JWTokenConstants.EXPIRATION.field].toString().toLong())
        }
        
    }
    
    override fun getPodatkeIzZetona(token: String): Claims? {
        val zeton: JWToken = extractToken(token)
        return zeton.getData()
    }
    
    override fun getUporabnikId(token: String): Long {
        val zeton: JWToken = extractToken(token)
        val data: Claims? = zeton.getData()
        return if (data == null) {
            0
        } else {
            data[JWTokenConstants.UPORABNIK_ID.field].toString().toLong()
        }
    }
    
    override fun getSessionId(token: String): String {
        val zeton: JWToken = extractToken(token)
        val data: Claims? = zeton.getData()
        return if (data == null) {
            ""
        } else {
            data[JWTokenConstants.SESSION_ID.field].toString()
        }
    }
    
    override fun getUporabnikVloge(token: String): List<Vloga> {
        val zeton: JWToken = extractToken(token)
        val data: Claims? = zeton.getData()
        return if (data == null) {
            emptyList()
        } else {
            data[JWTokenConstants.UPORABNIK_VLOGE.field] as List<Vloga>
        }
    }
    
    override fun getExpirationDate(secondsFromNow: Int): Date {
        val trenutniCas = Date()
        val koledar: Calendar = Calendar.getInstance()
        koledar.time = trenutniCas
        koledar.add(Calendar.SECOND, secondsFromNow)
        return koledar.time
    }
    
    private fun extractToken(token: String): JWToken {
        return JWToken.from(token)
            .setSession(session)
            .setHeader(jwtConfig.getHeader())
            .setSecret(jwtConfig.getSecret())
    }
    
    
}