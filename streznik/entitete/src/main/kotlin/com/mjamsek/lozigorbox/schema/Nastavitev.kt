package com.mjamsek.lozigorbox.schema

import javax.persistence.*

@Entity
@Table(
    name = "nastavitev",
    indexes = [
        Index(name = "NASTAVITEV_KLJUC", columnList = "kljuc")
    ]
)
data class Nastavitev(
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0,
    
    @Column(name = "kljuc", length = 200)
    var kljuc: String = "",
    
    @Column
    var vrednost: String = ""
)

enum class NastavitevConstants(val kljuc: String) {
    ZAKLEP_POSKUSI("zaklep.poskusi"),
    ZAKLEP_TRAJANJE("zaklep.trajanje"),
    JWT_VELJAVNOST("jwt.veljavnost"),
    JWT_SECRET("jwt.secret"),
    JWT_HEADER("jwt.header")
}