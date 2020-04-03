package main.kotlin.dao
import org.jetbrains.exposed.sql.Table

/* Author: Dominic Triano
 * Date: 4/3/2020
 * Language: Kotlin
 * Project: Halo
 * Description:
 *
 *
 */
object MedCenters : Table() {
    var hid = varchar("hid", 50).primaryKey()
    var groupid = varchar("groupId", 50).primaryKey()
}