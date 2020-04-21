package main.kotlin.model

/* Author: Dominic Triano
 * Date: 4/6/2020
 * Language: Kotlin
 * Project: Halo
 * Description:
 * Data class for creating schedules to enter into the database
 *
 */

data class Schedule(var client: String,
                    var doctor: String, var day: String)