package main.kotlin.dao
import org.jetbrains.exposed.sql.Table

/* Author: Dominic Triano
 * Date: 4/6/2020
 * Language: Kotlin
 * Project: Halo
 * Description:
 * DAO object for schedules, this will allow a schedule to be saved on the website for later viewing
 *
 */

//TODO -- Might make this into a schedule for humans to see what their schedule is, or integrate google clendar.
object Schedules : Table() {
    var client = varchar("clientId", 25)
    var doctor = varchar("docId" ,25)
    var day = datetime("day")
}