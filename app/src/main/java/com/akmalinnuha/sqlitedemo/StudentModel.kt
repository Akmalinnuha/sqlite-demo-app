package com.akmalinnuha.sqlitedemo

import kotlin.random.Random
import kotlin.random.nextInt

data class StudentModel(
    var id: Int = getAutoId(),
    var name: String = "",
    var email: String = ""
) {
    companion object {
        fun getAutoId(): Int {
            return Random.nextInt(100)
        }
    }
}
