package main.kotlin.dao
import org.jetbrains.exposed.sql.Table

/* Author: Dominic Triano
 * Date: 4/21/2020
 * Language: Kotlin
 * Project: Halo
 * Description:
 *
 *
 */object Doctors : Table() {
    var user = varchar("user", 50).primaryKey()
    var pass = varchar("pass",50)
    var name = varchar("fullName", 25)
    var groupId = varchar("groupId", 50)
    var scheduleId = varchar("schedule", 1000)
}