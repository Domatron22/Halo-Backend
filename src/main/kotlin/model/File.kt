package main.kotlin.model

/* Author: Dominic Triano
 * Date: 4/17/2020
 * Language: Kotlin
 * Project: Halo
 * Description:
 * Data class for creating Files to enter into the database
 *
 */
data class File(val user: String, var fullName : String, var fileName: String,
                var hid: String)