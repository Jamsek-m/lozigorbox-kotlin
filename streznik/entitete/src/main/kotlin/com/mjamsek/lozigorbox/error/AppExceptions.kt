package com.mjamsek.lozigorbox.error

import org.springframework.http.HttpStatus

class KonfliktException(override var message: String): LozigorboxException(message, HttpStatus.CONFLICT.value())

class JWTGeneratorException(override var message: String): LozigorboxException(message, HttpStatus.INTERNAL_SERVER_ERROR.value())

class ZaklepIpException(minuteZaklepa: Int): LozigorboxException("IP naslov je bil zaklenjen za $minuteZaklepa min, zaradi prevelikega števila poskusov!", HttpStatus.LOCKED.value())

class NeznanaNastavitevException(): LozigorboxException("Zahtevana nastavitev ne obstaja!", HttpStatus.INTERNAL_SERVER_ERROR.value())
