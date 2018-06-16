package com.mjamsek.lozigorbox.schema

import javax.persistence.*

@Entity
@Table(name = "vloga")
data class Vloga(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Int = 0,
    
    @Column
    var naziv: String = "",
    
    @Column
    var sifra: String = ""
)

enum class Vloge(val sifra: String) {
    ADMIN("ADMIN"),
    USER("USER"),
    MOD("MOD")
}