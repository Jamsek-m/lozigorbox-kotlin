package com.mjamsek.lozigorbox.error

open class LozigorboxException(override var message: String, var status: Int) : RuntimeException(message)
