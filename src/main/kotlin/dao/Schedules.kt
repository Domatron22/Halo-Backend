package main.kotlin.dao
import org.jetbrains.exposed.sql.Table

/* Author: Dominic Triano
 * Date: 4/6/2020
 * Language: Kotlin
 * Project: Halo
 * Description:
 *
 *
 */
object Schedules : Table() {
    var month = varchar("month", 10)
    var day = integer("day")
    var year = integer("year")
    var open = integer("openHr")
    var close = integer("closeHr")
}