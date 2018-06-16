package com.mjamsek.lozigorbox.schema

import java.util.*
import javax.persistence.*

@Entity
@Table(
    name = "ip_zaklep",
    indexes = [
        Index(name = "IP_INDEX", columnList = "ip_naslov")
    ]
)
data class IpZaklep(
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0,
    
    @Column(name = "ip_naslov", length = 100)
    var ipNaslov: String = "",
    
    @Column(name = "datum_prijave")
    var datumPrijave: Date = Date()
) {
    companion object {
        fun fromIP(ip: String): IpZaklep {
            return IpZaklep(0, ip, Date())
        }
    }
}