package com.mjamsek.lozigorbox.schema

import com.fasterxml.jackson.annotation.JsonIgnore
import javax.persistence.*

@Entity
@Table(
    name = "uporabnik",
    indexes = [
        Index(name = "UPORABNIK_EMAIL_INDEX", columnList = "email")
    ]
)
data class Uporabnik(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0,
    
    @Column(name = "email", length = 300)
    var email: String = "",
    
    @Column
    @JsonIgnore
    var geslo: String = "",
    
    @Column
    var ime: String = "",
    
    @ManyToOne
    @JoinColumn(name = "status_id")
    var status: UporabnikStatus? = null,
    
    @ManyToMany(cascade = [(CascadeType.ALL)])
    @JoinTable(
        name = "uporabniske_vloge",
        joinColumns = [(JoinColumn(name = "uporabnik_id"))],
        inverseJoinColumns = [(JoinColumn(name = "vloga_id"))]
    )
    var vloge: Set<Vloga> = emptySet()
) {
    
    fun imaVlogo(sifrant: Vloge): Boolean {
        val result = vloge.find { vloga: Vloga -> vloga.sifra == sifrant.sifra }
        return result != null
    }
    
}
