package com.example.movies_data

import java.io.File

object TestUtils {
    fun getJsonPath(path : String) : String {
        val uri = this.javaClass.classLoader!!.getResource(path)
        val file = File(uri.path)
        return String(file.readBytes())
    }
}