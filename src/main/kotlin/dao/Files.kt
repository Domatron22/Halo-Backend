package main.kotlin.dao
import org.jetbrains.exposed.sql.Table

/* Author: Dominic Triano
 * Date: 4/17/2020
 * Language: Kotlin
 * Project: Halo
 * Description:
 *
 *
 */
object Files : Table() {
    var user = varchar("user", 25) //Instead of a username, this will be the full name of the user
    var fullName = varchar("name" , 50)
    var file = varchar("file", 50) //all of the files on file for a user
    var hid = varchar("hid" ,10)
    }