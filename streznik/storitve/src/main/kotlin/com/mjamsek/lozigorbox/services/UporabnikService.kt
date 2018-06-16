package com.mjamsek.lozigorbox.services

import com.mjamsek.lozigorbox.schema.Uporabnik

interface UporabnikService {

    fun pridobiZEmailom(email: String): Uporabnik?
    
    fun pridobiZId(id: Long): Uporabnik?

}