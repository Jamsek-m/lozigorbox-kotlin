package com.mjamsek.lozigorbox.api.response

import com.mjamsek.lozigorbox.schema.Uporabnik
import java.util.*
import javax.servlet.http.HttpServletResponse
import javax.servlet.http.Cookie

class PrijavaResponse(
    var expiration: Date,
    var response: HttpServletResponse,
    var uporabnik: Uporabnik,
    var cookie: Cookie
)