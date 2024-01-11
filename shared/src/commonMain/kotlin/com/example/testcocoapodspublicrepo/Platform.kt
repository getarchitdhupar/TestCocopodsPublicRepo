package com.example.testcocoapodspublicrepo

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform