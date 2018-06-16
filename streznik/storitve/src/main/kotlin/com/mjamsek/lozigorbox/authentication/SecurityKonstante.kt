package com.mjamsek.lozigorbox.authentication

enum class SecurityKonstante(val value: String) {
    COOKIE_NAME("lozigorbox-session"),
    ALLOW_CREDENTIALS_HEADER("Access-Control-Allow-Credentials"),
    SESSION_IS_LOGGED_IN("is_logged_in")
}