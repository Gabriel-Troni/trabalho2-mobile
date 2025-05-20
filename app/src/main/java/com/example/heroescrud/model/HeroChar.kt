package com.example.heroescrud.model

data class HeroChar(
    val id: Int = 0,
    val name: String,
    val company: String,
    val origin: String,
    val power: String//,
    //val image: Int
) {
    override fun toString(): String {
        return name
    }
}
