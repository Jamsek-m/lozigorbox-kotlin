package com.mjamsek.lozigorbox.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@Configuration
class ResourceConfig {
    
    @Bean
    fun setResourceHandlers(): WebMvcConfigurer {
        return object : WebMvcConfigurer {
            override fun addViewControllers(registry: ViewControllerRegistry) {
                registry.addViewController("/").setViewName("forward:/index.html")
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