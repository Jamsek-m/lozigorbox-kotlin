package com.mjamsek.lozigorbox.schema

import javax.persistence.*

@Entity
@Table(name = "uporabniski_status")
class UporabnikStatus(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Int = 0,
    
    @Column
    var sifra: String = "",
    
    @Column
    var naziv: String = ""
)

enum class UporabnikStatusi(val sifra: String) {
    AKTIVEN("AKTIVEN"),
    NEAKTIVEN("NEAKTIVEN")
}