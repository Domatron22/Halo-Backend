package main.kotlin.model

/* Author: Dominic Triano
 * Date: 4/3/2020
 * Language: Kotlin
 * Project: Halo
 * Description:
 * Data class for creating Humans to enter into the database
 *
 */

data class Human(val user: String, val pass: String,
                 val fname: String, val lname: String,
                 val groupId: String, val access: Int)