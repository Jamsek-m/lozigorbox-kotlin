package com.mjamsek.lozigorbox.authentication.impl

import com.mjamsek.lozigorbox.authentication.JWTokenService
import com.mjamsek.lozigorbox.configs.JWTokenConfiguration
import com.mjamsek.lozigorbox.configs.SpringSessionConfig
import com.mjamsek.lozigorbox.objects.JWToken
import com.mjamsek.lozigorbox.objects.JWTokenConstants
import com.mjamsek.lozigorbox.schema.Uporabnik
import com.mjamsek.lozigorbox.schema.Vloga
import io.jsonwebtoken.Claims
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.core.env.Environment
import org.springframework.core.env.get
import org.springframework.stereotype.Service
import java.util.*
import javax.annotation.PostConstruct
import javax.servlet.http.HttpSession

@Service
class JWTokenServiceImpl: JWTokenService {
    
    @Autowired
    lateinit var jwtConfig: JWTokenConfiguration
    
    @Autowired
    lateinit var sessionConfig: SpringSessionConfig
    
    private lateinit var expirationDate: Date
    
    @Autowired
    private lateinit var session: HttpSession
    
    @Autowired
    private lateinit var env: Environment
    
    @PostConstruct
    fun init() {
        val sekunde: Int = sessionConfig.timeout
        this.expirationDate = this.getExpirationDate(sekunde)
    }
    
    override fun generirajJWToken(uporabnik: Uporabnik): String {
        val zeton: JWToken = JWToken.new()
            .setSession(session)
            .setHeader(jwtConfig.header)
            .setSecret(jwtConfig.secret)
            .setUser(uporabnik)
            .sign()
        return zeton.getToken()
    }
    
    override fun validirajZeton(token: String): Boolean {
        val zeton: JWToken = extractToken(token)
        return zeton.isValid()
    }
    
    override fun getVeljavnostZetona(token: String): Date {
        val zeton: JWToken = extractToken(token)
        return zeton.getExpirationDate()!!
        
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
            .setHeader(jwtConfig.header)
            .setSecret(jwtConfig.secret)
    }
    
    
}