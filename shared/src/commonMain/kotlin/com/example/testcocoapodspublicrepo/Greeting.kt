package com.example.testcocoapodspublicrepo

public class Greeting {
    private val platform: Platform = getPlatform()

    public fun greet(): String {
        return "Hello, ${platform.name}!"
    }

    public fun feb17greet(): String {
        return "Hello, ${platform.name}!"
    }
}