package com.gameloop.laboratorioclinicoproc.database

import androidx.room.TypeConverter

class Converters {
    @TypeConverter
    fun fromRecommendations(recommendations: List<String>): String {
        return recommendations.reduce { acc, s -> "$acc;;$s" }
    }

    @TypeConverter
    fun stringToRecommendations(str: String): List<String> {
        return str.split(";;")
    }
}