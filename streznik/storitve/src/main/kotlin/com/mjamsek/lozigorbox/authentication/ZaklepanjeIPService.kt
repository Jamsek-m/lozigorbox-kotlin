package com.mjamsek.lozigorbox.authentication

interface ZaklepanjeIPService {
    
    fun preveriIpZaklep(ip: String)
    
    fun dodajNeveljavnoPrijavo(ip: String)
    
    fun pocistiIpZaklep(ip: String)
}