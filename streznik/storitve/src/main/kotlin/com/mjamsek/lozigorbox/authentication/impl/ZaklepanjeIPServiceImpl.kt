package com.mjamsek.lozigorbox.authentication.impl

import com.mjamsek.lozigorbox.authentication.ZaklepanjeIPService
import com.mjamsek.lozigorbox.error.ZaklepIpException
import com.mjamsek.lozigorbox.repositories.IpZaklepRepository
import com.mjamsek.lozigorbox.schema.IpZaklep
import com.mjamsek.lozigorbox.schema.NastavitevConstants
import com.mjamsek.lozigorbox.services.NastavitevService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.util.*

@Service
class ZaklepanjeIPServiceImpl: ZaklepanjeIPService {
    
    @Autowired
    private lateinit var nastavitevService: NastavitevService
    
    @Autowired
    private lateinit var ipZaklepRepository: IpZaklepRepository
    
    override fun preveriIpZaklep(ip: String) {
        val steviloDovoljenihPoskusov: Int = nastavitevService.get(NastavitevConstants.ZAKLEP_POSKUSI.kljuc).toInt()
        val steviloSekundPoZadnjiPrijavi: Int = nastavitevService.get(NastavitevConstants.ZAKLEP_TRAJANJE.kljuc).toInt()
        
        val steviloMinutZaklepa: Int = steviloSekundPoZadnjiPrijavi / 60
        val datumNSekundNazaj: Date = this.vrniDatumNSekundNazaj(steviloSekundPoZadnjiPrijavi)
        if (ipZaklepRepository.prestejSteviloPrijav(ip, datumNSekundNazaj) >= steviloDovoljenihPoskusov) {
            throw ZaklepIpException(steviloMinutZaklepa)
        }
    }
    
    override fun dodajNeveljavnoPrijavo(ip: String) {
        val zaklep: IpZaklep = IpZaklep.fromIP(ip)
        ipZaklepRepository.save(zaklep)
    }
    
    override fun pocistiIpZaklep(ip: String) {
        ipZaklepRepository.izbrisiVsePrijaveIPja(ip)
    }
    
    private fun vrniDatumNSekundNazaj(nSekund: Int): Date {
        val trenutniCas = Date()
        val koledar: Calendar = Calendar.getInstance()
        koledar.time = trenutniCas
        koledar.add(Calendar.SECOND, nSekund)
        return koledar.time
    }
}