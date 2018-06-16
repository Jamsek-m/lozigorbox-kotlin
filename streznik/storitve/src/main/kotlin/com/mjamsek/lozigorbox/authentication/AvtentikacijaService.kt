package com.mjamsek.lozigorbox.authentication

import com.mjamsek.lozigorbox.api.request.PrijavaRequest
import com.mjamsek.lozigorbox.api.response.PrijavaResponse
import com.mjamsek.lozigorbox.schema.Uporabnik
import java.util.*
import javax.servlet.http.Cookie
import javax.servlet.http.HttpServletResponse

interface AvtentikacijaService {
    
    fun prijaviUporabnika(response: HttpServletResponse, zahteva: PrijavaRequest): PrijavaResponse
    
    fun odjaviUporabnika(response: HttpServletResponse): HttpServletResponse
    
    fun prijavaJeVeljavna(): Boolean
    
    fun osveziZeton(response: HttpServletResponse): Cookie?
    
    fun getVeljavnostPrijave(): Date?
    
    fun getPrijavljenUporabnik(): Uporabnik?
    
    fun getSessionId(): String?
}