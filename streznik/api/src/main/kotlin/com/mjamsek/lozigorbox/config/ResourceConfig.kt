package com.mjamsek.lozigorbox.config

import com.mjamsek.lozigorbox.services.DevService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.config.annotation.CorsRegistry
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@Configuration
class ResourceConfig {
    
    @Autowired
    private lateinit var devService: DevService
    
    @Bean
    fun setResourceHandlers(): WebMvcConfigurer {
        return object : WebMvcConfigurer {
            override fun addViewControllers(registry: ViewControllerRegistry) {
                registry.addViewController("/").setViewName("forward:/index.html")
            }
    
            override fun addCorsMappings(registry: CorsRegistry) {
                if (devService.isInDevMode()) {
                    registry.addMapping("**")
                        .allowedMethods("GET", "POST", "DELETE", "PUT", "OPTIONS")
                        .allowedOrigins("*")
                        .allowCredentials(true)
                        .allowedHeaders("*")
                }
            }
    
            override fun addResourceHandlers(registry: ResourceHandlerRegistry) {
                if(!registry.hasMappingForPattern("/**")) {
                    registry
                        .addResourceHandler("/**")
                        .addResourceLocations("classpath:/static/", "classpath:/angular/")
                }
            }
        }
    }
    
}