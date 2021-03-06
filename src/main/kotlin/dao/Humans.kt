package main.kotlin.dao
import org.jetbrains.exposed.sql.Table

/* Author: Dominic Triano
 * Date: 2/15/2020
 * Language: Kotlin
 * Project: Halo
 * Description:
 *  DAO Object for A Human (Homo-sapiens), an individual who can be observed medically. This object will keep all of
 * their data in an easily accessible format
 *
 */
object Humans : Table() {
    var user = varchar("user", 50).primaryKey() //The username for the human
    var pass = varchar("pass",50) //The password for the user, it will be hashed before getting here
    var f_name = varchar("fname", 25)//User's first name
    var l_name = varchar("lname", 25)//User's last name
    var groupId = varchar("groupId", 50) //The hospital id they belong to (possibly linking it to other tables)
    var access = integer("accesslvl") // How much power does this user have? (1 is client, 2 is Doc, 3 is Dev)
}