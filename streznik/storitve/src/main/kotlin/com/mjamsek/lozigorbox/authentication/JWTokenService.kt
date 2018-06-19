package com.mjamsek.lozigorbox.authentication

import com.mjamsek.lozigorbox.schema.Uporabnik
import com.mjamsek.lozigorbox.schema.Vloga
import io.jsonwebtoken.Claims
import java.util.*

interface JWTokenService {
    
    fun getExpirationDate(secondsFromNow: Int): Date
    
    fun generirajJWToken(uporabnik: Uporabnik): String
    
    fun generirajJWToken(uporabnik: Uporabnik, sekunde: Int): String
    
    fun validirajZeton(token: String): Boolean
    
    fun getVeljavnostZetona(token: String): Date?
    
    fun getPodatkeIzZetona(token: String): Claims?
    
    fun getUporabnikId(token: String): Long
    
    fun getSessionId(token: String): String
    
    fun getUporabnikVloge(token: String): List<Vloga>

}