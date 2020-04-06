package main.kotlin.model

/* Author: Dominic Triano
 * Date: 4/6/2020
 * Language: Kotlin
 * Project: Halo
 * Description:
 *
 *
 */

data class Schedule(val month: String, val day: Int,
                    val year: Int, val openHr: Int,
                    val closeHr: Int)