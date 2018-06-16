package com.mjamsek.lozigorbox.config

import com.mjamsek.lozigorbox.authentication.ImaVlogoInterceptor
import com.mjamsek.lozigorbox.authentication.JeAvtenticiranInterceptor
import com.mjamsek.lozigorbox.authentication.OsveziZetonInterceptor
import com.mjamsek.lozigorbox.authentication.SamoDevInterceptor
import com.mjamsek.lozigorbox.services.DevService
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.config.annotation.CorsRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@Configuration
class MvcConfig(val devService: DevService) {
    
    @Bean
    fun setCORSheaders(): WebMvcConfigurer {
        return object : WebMvcConfigurer {
            override fun addCorsMappings(registry: CorsRegistry) {
                if (devService.isInDevMode()) {
                    registry.addMapping("/**")
                        .allowedMethods("GET", "POST", "DELETE", "PUT", "OPTIONS")
                        .allowedOrigins("*")
                        .allowCredentials(true)
                        .allowedHeaders("*")
                }
            }
        }
    }
    
    @Bean
    fun jeAvtenticiranIntercept(): JeAvtenticiranInterceptor {
        return JeAvtenticiranInterceptor()
    }
    
    @Bean
    fun imaVlogoIntercept(): ImaVlogoInterceptor {
        return ImaVlogoInterceptor()
    }
    
    @Bean
    fun samoDevIntercept(): SamoDevInterceptor {
        return SamoDevInterceptor()
    }
    
    @Bean
    fun refreshTokenIntercept(): OsveziZetonInterceptor {
        return OsveziZetonInterceptor()
    }
    
}