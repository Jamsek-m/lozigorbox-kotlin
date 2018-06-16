package com.mjamsek.lozigorbox.authentication

import com.mjamsek.lozigorbox.error.NiPrijavljenException
import com.mjamsek.lozigorbox.error.NimaDovoljenjaException
import com.mjamsek.lozigorbox.error.SamoDevException
import com.mjamsek.lozigorbox.schema.Uporabnik
import com.mjamsek.lozigorbox.schema.Vloge
import com.mjamsek.lozigorbox.services.DevService
import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation.Around
import org.aspectj.lang.annotation.Aspect
import org.aspectj.lang.annotation.Pointcut
import org.aspectj.lang.reflect.MethodSignature
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.context.request.RequestContextHolder
import org.springframework.web.context.request.ServletRequestAttributes
import javax.servlet.http.Cookie
import javax.servlet.http.HttpServletResponse

@Aspect
class JeAvtenticiranInterceptor {
    
    @Autowired
    private lateinit var avtentikacijaService: AvtentikacijaService
    
    @Around("@annotation(com.mjamsek.lozigorbox.authentication.JeAvtenticiran)")
    fun interceptJeAvtenticiran(joinPoint: ProceedingJoinPoint): Any {
        if (!avtentikacijaService.prijavaJeVeljavna()) {
            throw NiPrijavljenException()
        }
        return joinPoint.proceed()
    }
}

@Aspect
class ImaVlogoInterceptor {
    
    @Autowired
    private lateinit var avtentikacijaService: AvtentikacijaService
    
    @Around("@annotation(com.mjamsek.lozigorbox.authentication.ImaVlogo)")
    fun interceptImaVlogo(joinPoint: ProceedingJoinPoint): Any {
        if (!avtentikacijaService.prijavaJeVeljavna()) {
            throw NiPrijavljenException()
        }
        val uporabnik: Uporabnik = avtentikacijaService.getPrijavljenUporabnik() ?: throw NiPrijavljenException()
        val anotacija: ImaVlogo = getAnottation(joinPoint)
        val dovoljeneVloge: Array<Vloge> = anotacija.vloge
        for (vl: Vloge in dovoljeneVloge) {
            if (uporabnik.imaVlogo(vl)) {
                return joinPoint.proceed()
            }
        }
        throw NimaDovoljenjaException()
    }
    
    private fun getAnottation(join: ProceedingJoinPoint): ImaVlogo {
        return (join.signature as MethodSignature).method.getAnnotation(ImaVlogo::class.java)
    }
}

@Aspect
class SamoDevInterceptor {
    
    @Autowired
    private lateinit var devService: DevService
    
    @Around("@annotation(com.mjamsek.lozigorbox.authentication.SamoDev)")
    fun interceptSamoDev(joinPoint: ProceedingJoinPoint): Any {
        if (devService.isInDevMode()) {
            return joinPoint.proceed()
        }
        throw SamoDevException()
    }
}

@Aspect
class OsveziZetonInterceptor {
    
    @Pointcut("@annotation(org.springframework.web.bind.annotation.GetMapping)")
    fun GET() {
    }
    
    @Pointcut("@annotation(org.springframework.web.bind.annotation.PostMapping)")
    fun POST() {
    }
    
    @Pointcut("@annotation(org.springframework.web.bind.annotation.PutMapping)")
    fun PUT() {
    }
    
    @Pointcut("@annotation(org.springframework.web.bind.annotation.DeleteMapping)")
    fun DELETE() {
    }
    
    @Pointcut("@annotation(com.mjamsek.lozigorbox.authentication.IgnoreRefreshToken)")
    fun ignore() {
    }
    
    @Autowired
    private lateinit var avtentikacijaService: AvtentikacijaService
    
    @Around("(GET() || POST() || PUT() || DELETE()) && !ignore()")
    fun osveziZetonOnRequest(joinPoint: ProceedingJoinPoint): Any {
        var response: HttpServletResponse = (RequestContextHolder.getRequestAttributes() as ServletRequestAttributes).response!!
    
        response.setHeader(SecurityKonstante.ALLOW_CREDENTIALS_HEADER.value, "true")
        val cookie: Cookie? = avtentikacijaService.osveziZeton(response)
        if (cookie != null) {
            response.addCookie(cookie)
        }
        return joinPoint.proceed()
    }
    
    
}