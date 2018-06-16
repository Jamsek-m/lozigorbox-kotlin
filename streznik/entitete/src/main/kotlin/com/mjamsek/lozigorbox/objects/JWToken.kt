package com.mjamsek.lozigorbox.objects

import com.mjamsek.lozigorbox.error.JWTGeneratorException
import com.mjamsek.lozigorbox.schema.Uporabnik
import com.mjamsek.lozigorbox.utils.DateUtil
import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import java.io.UnsupportedEncodingException
import java.nio.charset.Charset
import java.util.*
import javax.servlet.http.HttpSession

class JWToken private constructor() {
    private var header: String = ""
    private var secret: String = ""
    private var expirationDate: Date? = null
    private var token: String = ""
    private var uporabnik: Uporabnik? = null
    private var session: HttpSession? = null
    
    companion object {
        fun new(): JWToken {
            return JWToken()
        }
        
        fun from(token: String): JWToken {
            return JWToken().setToken(token)
        }
    }
    
    fun setHeader(header: String): JWToken {
        this.header = header
        return this
    }
    
    fun setUser(uporabnik: Uporabnik): JWToken {
        this.uporabnik = uporabnik
        return this
    }
    
    fun setSecret(secret: String): JWToken {
        this.secret = secret
        return this
    }
    
    fun setSession(session: HttpSession): JWToken {
        this.session = session
        this.expirationDate = DateUtil.incrementDateForSeconds(Date(session.creationTime), session.maxInactiveInterval)
        return this
    }
    
    fun setToken(token: String): JWToken {
        this.token = token
        return this
    }
    
    fun getExpirationDate(): Date? {
        return this.expirationDate
    }
    
    fun getToken(): String {
        if (!token.isEmpty() && token != "") {
            return token
        } else {
            throw JWTGeneratorException("Zahtevaš prazen token!")
        }
    }
    
    fun sign(): JWToken {
        if (uporabnik == null) {
            throw JWTGeneratorException("Uporabnik za JWT je null!")
        }
        if (session == null) {
            throw JWTGeneratorException("Seja za JWT je null!")
        }
        
        try {
            this.token = Jwts.builder()
                .setSubject(header)
                .setIssuedAt(Date())
                .setExpiration(expirationDate)
                .claim(JWTokenConstants.UPORABNIK_ID.field, uporabnik!!.id)
                .claim(JWTokenConstants.UPORABNIK_VLOGE.field, uporabnik!!.vloge)
                .claim(JWTokenConstants.SESSION_ID.field, session!!.id)
                .signWith(SignatureAlgorithm.HS256, secret.toByteArray(Charset.forName("UTF-8")))
                .compact()
            
            return this
            
        } catch (exc: UnsupportedEncodingException) {
            throw JWTGeneratorException(exc.message!!)
        }
    }
    
    fun getData(): Claims? {
        return try {
            val claims: Claims = Jwts.parser()
                .requireSubject(header)
                .setSigningKey(secret.toByteArray(Charset.forName("UTF-8")))
                .parseClaimsJws(token)
                .body
            claims
        } catch (exc: Exception) {
            null
        }
    }
    
    fun isValid(): Boolean {
        return try {
            Jwts.parser()
                .requireSubject(header)
                .setSigningKey(secret.toByteArray(Charset.forName("UTF-8")))
                .parse(token)
            true
        } catch (exc: Exception) {
            false
        }
    }
}

enum class JWTokenConstants(val field: String) {
    UPORABNIK_ID("uporabnik_id"),
    UPORABNIK_VLOGE("uporabnik_vloge"),
    SESSION_ID("session_id")
    
}