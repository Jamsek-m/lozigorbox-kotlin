package com.mjamsek.lozigorbox.angular

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import javax.servlet.http.HttpSession

@Controller
class AngularEndpoint {
    
    @Autowired
    private lateinit var session: HttpSession

    @GetMapping("/**/{path:[^\\\\.]*}\"")
    fun forwardToAngular(@PathVariable("path") path: String): String {
        session.creationTime
        return "forward:/"
    }
}