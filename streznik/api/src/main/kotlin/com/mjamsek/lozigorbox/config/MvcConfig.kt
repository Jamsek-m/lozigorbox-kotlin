package com.mjamsek.lozigorbox.config

import com.mjamsek.lozigorbox.authentication.ImaVlogoInterceptor
import com.mjamsek.lozigorbox.authentication.JeAvtenticiranInterceptor
import com.mjamsek.lozigorbox.authentication.OsveziZetonInterceptor
import com.mjamsek.lozigorbox.authentication.SamoDevInterceptor
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class MvcConfig {
    
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
