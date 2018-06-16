package com.mjamsek.lozigorbox.utils;

import java.util.Date
import java.util.Calendar

class DateUtil {
    
    companion object {
        
        fun incrementDateForSeconds(datum: Date, sekunde: Int): Date {
            val koledar: Calendar = Calendar.getInstance()
            koledar.time = datum
            koledar.add(Calendar.SECOND, sekunde)
            return koledar.time
        }
        
    }
    
}
