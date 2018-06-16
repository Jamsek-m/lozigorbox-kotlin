package com.mjamsek.lozigorbox.error

import org.springframework.http.HttpStatus

// 404
class EntitetaNeObstajaException(override var message: String) : LozigorboxException(message, HttpStatus.NOT_FOUND.value())

// 400
class SlabaZahtevaException(override var message: String) : LozigorboxException(message, HttpStatus.BAD_REQUEST.value())

// 401
class NiPrijavljenException() : LozigorboxException("Uporabnik ni prijavljen!", HttpStatus.UNAUTHORIZED.value())

// 401
class NapacnaAvtorizacijaException(override var message: String) : LozigorboxException(message, HttpStatus.UNAUTHORIZED.value())

// 403
class NimaDovoljenjaException() : LozigorboxException("Uporabnik nima dovoljenja za ta vir!", HttpStatus.FORBIDDEN.value())

// 423
class SamoDevException : LozigorboxException("Vir je na voljo samo v dev profilu!", HttpStatus.LOCKED.value())
