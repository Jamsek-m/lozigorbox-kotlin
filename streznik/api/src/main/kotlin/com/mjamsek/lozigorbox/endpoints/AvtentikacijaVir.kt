package com.mjamsek.lozigorbox.endpoints

import com.mjamsek.lozigorbox.api.request.PrijavaRequest
import com.mjamsek.lozigorbox.api.response.PrijavaResponse
import com.mjamsek.lozigorbox.authentication.AvtentikacijaService
import com.mjamsek.lozigorbox.authentication.IgnoreRefreshToken
import com.mjamsek.lozigorbox.authentication.JeAvtenticiran
import com.mjamsek.lozigorbox.authentication.SecurityKonstante
import org.mindrot.jbcrypt.BCrypt
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@RestController
@RequestMapping("api/v1/avtentikacija")
class AvtentikacijaVir {
    
    @Autowired
    private lateinit var avtentikacijaService: AvtentikacijaService
    
    @PostMapping("/prijava")
    @IgnoreRefreshToken
    fun prijaviUporabnika(@RequestBody zahteva: PrijavaRequest, response: HttpServletResponse): ResponseEntity<HashMap<String, Any>> {
        val odgovor: PrijavaResponse = avtentikacijaService.prijaviUporabnika(response, zahteva)
        
        val responseEntity: HashMap<String, Any> = HashMap()
        responseEntity["expiration"] = odgovor.expiration
        responseEntity["uporabnik"] = odgovor.uporabnik
        
        response.setHeader(SecurityKonstante.ALLOW_CREDENTIALS_HEADER.value, odgovor.response.getHeader(SecurityKonstante.ALLOW_CREDENTIALS_HEADER.value))
        response.addCookie(odgovor.cookie)
        return ResponseEntity.ok().body(responseEntity)
    }
    
    @GetMapping("/gen")
    @IgnoreRefreshToken
    fun getPass(): ResponseEntity<String> {
        val geslo = BCrypt.hashpw("geslo123", BCrypt.gensalt())
        return ResponseEntity.ok(geslo)
    }
    
    @GetMapping("/test")
    @JeAvtenticiran
    fun get(): ResponseEntity<String> {
        return ResponseEntity.ok("OK")
    }
    
}