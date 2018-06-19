package com.mjamsek.lozigorbox.config

import com.mjamsek.lozigorbox.error.LozigorboxException
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.context.request.WebRequest
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler

@ControllerAdvice
class ExceptionMapper : ResponseEntityExceptionHandler() {

    @ExceptionHandler(LozigorboxException::class)
    fun handleException(ex: LozigorboxException, request: WebRequest): ResponseEntity<Any> {
        val response = ExceptionMapperResponse.buildFromException(ex)
        return handleExceptionInternal(ex, response, HttpHeaders(), HttpStatus.valueOf(ex.status), request)
    }

}

data class ExceptionMapperResponse(var napaka: String, var status: Int) {
    companion object {
        fun buildFromException(exc: LozigorboxException): ExceptionMapperResponse {
            return ExceptionMapperResponse(exc.message, exc.status)
        }
    }
}