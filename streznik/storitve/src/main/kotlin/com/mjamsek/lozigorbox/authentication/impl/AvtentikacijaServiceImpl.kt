package com.mjamsek.lozigorbox.authentication.impl

import com.mjamsek.lozigorbox.api.request.PrijavaRequest
import com.mjamsek.lozigorbox.api.response.PrijavaResponse
import com.mjamsek.lozigorbox.authentication.AvtentikacijaService
import com.mjamsek.lozigorbox.authentication.JWTokenService
import com.mjamsek.lozigorbox.authentication.SecurityKonstante
import com.mjamsek.lozigorbox.authentication.ZaklepanjeIPService
import com.mjamsek.lozigorbox.constants.KlientTranslateCodes
import com.mjamsek.lozigorbox.error.NapacnaAvtorizacijaException
import com.mjamsek.lozigorbox.error.NiPrijavljenException
import com.mjamsek.lozigorbox.schema.NastavitevConstants
import com.mjamsek.lozigorbox.schema.Uporabnik
import com.mjamsek.lozigorbox.services.NastavitevService
import com.mjamsek.lozigorbox.services.UporabnikService
import org.mindrot.jbcrypt.BCrypt
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.util.*
import javax.servlet.http.Cookie
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse
import javax.servlet.http.HttpSession

@Service
class AvtentikacijaServiceImpl : AvtentikacijaService {
    
    @Autowired
    private lateinit var jwTokenService: JWTokenService
    
    @Autowired
    private lateinit var uporabnikService: UporabnikService
    
    @Autowired
    private lateinit var zaklepanjeIPService: ZaklepanjeIPService
    
    @Autowired
    private lateinit var nastavitevService: NastavitevService
    
    @Autowired
    private lateinit var request: HttpServletRequest
    
    @Autowired
    private lateinit var session: HttpSession
    
    override fun odjaviUporabnika(response: HttpServletResponse): Cookie {
        session.invalidate()
        return createCookie(null, 0)
    }
    
    override fun prijavaJeVeljavna(): Boolean {
        val cookie: Cookie? = this.getLoginCookie()
        return cookie != null && jwTokenService.validirajZeton(cookie.value)
    }
    
    override fun osveziZeton(response: HttpServletResponse): Cookie? {
        val cookie: Cookie? = this.getLoginCookie()
        
        if (cookie != null && session.id != this.getSessionId()) {
            throw NapacnaAvtorizacijaException("Identifikator seje se ne ujema!")
        }
        
        val uporabnik: Uporabnik = this.getPrijavljenUporabnik() ?: return null
        
        // poskrbi da ni veljavnost zetona vecja od veljavnosti seje
        val veljavnostZetona: Int = this.getVeljavnostOsvezenegaZetona().toInt()
        
        val zeton: String = jwTokenService.generirajJWToken(uporabnik, veljavnostZetona)
        
        return createCookie(zeton, veljavnostZetona)
    }
    
    override fun getVeljavnostPrijave(): Date? {
        val cookie: Cookie? = this.getLoginCookie()
        return if (cookie != null) {
            this.jwTokenService.getVeljavnostZetona(cookie.value)
        } else {
            null
        }
    }
    
    override fun prijaviUporabnika(response: HttpServletResponse, zahteva: PrijavaRequest): PrijavaResponse {
        session.creationTime
        val ip: String = request.remoteAddr
        zaklepanjeIPService.preveriIpZaklep(ip)
        
        val uporabnik: Uporabnik? = uporabnikService.pridobiZEmailom(zahteva.email)
        if (uporabnik == null) {
            zaklepanjeIPService.dodajNeveljavnoPrijavo(ip)
            throw NapacnaAvtorizacijaException(KlientTranslateCodes.NAV_LOGIN_ERROR_NO_SUCH_USER.kljuc)
        }
        if (!BCrypt.checkpw(zahteva.geslo, uporabnik.geslo)) {
            zaklepanjeIPService.dodajNeveljavnoPrijavo(ip)
            throw NapacnaAvtorizacijaException(KlientTranslateCodes.NAV_LOGIN_ERROR_PASS.kljuc)
        }
        //generate new session
        
        /*session.invalidate()
        val newSession: HttpSession = request.session
        newSession.setAttribute(SecurityKonstante.SESSION_IS_LOGGED_IN.value, true)*/
        
        val zeton: String = jwTokenService.generirajJWToken(uporabnik)
        val veljavnostZetona: Int = nastavitevService.get(NastavitevConstants.JWT_VELJAVNOST.kljuc).toInt()
        
        val cookie: Cookie = createCookie(zeton, veljavnostZetona)
        
        response.setHeader(SecurityKonstante.ALLOW_CREDENTIALS_HEADER.value, "true")
        response.addCookie(cookie)
        
        val datumPoteka: Date? = jwTokenService.getVeljavnostZetona(zeton)
        val odgovor = PrijavaResponse(datumPoteka!!, response, uporabnik, cookie)
        zaklepanjeIPService.pocistiIpZaklep(ip)
        return odgovor
    }
    
    override fun getPrijavljenUporabnik(): Uporabnik? {
        val cookie: Cookie = this.getLoginCookie() ?: return null
        val uporabnikId: Long = jwTokenService.getUporabnikId(cookie.value)
        return uporabnikService.pridobiZId(uporabnikId) ?: throw NiPrijavljenException()
    }
    
    override fun getSessionId(): String {
        val cookie: Cookie = this.getLoginCookie() ?: return ""
        return jwTokenService.getSessionId(cookie.value)
    }
    
    private fun createCookie(zeton: String?, sekunde: Int): Cookie {
        val cookie = Cookie(SecurityKonstante.COOKIE_NAME.value, zeton)
        cookie.path = "/"
        cookie.secure = false
        cookie.isHttpOnly = true
        cookie.maxAge = sekunde
        return cookie
    }
    
    private fun getLoginCookie(): Cookie? {
        val cookies: Array<Cookie> = request.cookies ?: return null
        if (cookies.isEmpty()) {
            return null
        }
        return cookies.find { c: Cookie -> c.name == SecurityKonstante.COOKIE_NAME.value }
    }
    
    private fun getVeljavnostOsvezenegaZetona(): Long {
        // trenutni cas v milisekundah
        val trenutniCas = System.currentTimeMillis() % 1000
        // preostanek veljavnosti seje
        val veljavnostSeje: Long = (session.creationTime + session.maxInactiveInterval * 1000) - trenutniCas
        // privzeta veljavnost zetona
        val veljavnostZetona: Long = nastavitevService.get(NastavitevConstants.JWT_VELJAVNOST.kljuc).toLong()
        
        return Math.min(veljavnostSeje, veljavnostZetona)
    }
}