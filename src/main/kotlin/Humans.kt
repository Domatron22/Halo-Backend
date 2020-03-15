import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.Table

/* Author: Dominic Triano
 * Date: 2/15/2020
 * Language: Kotlin
 * Project: Halo
 * Description:
 *  DSL Object for A Human (Homo-sapiens), an individual who can be observed medically. This object will keep all of
 * their data in an easily accessible format
 *
 */object Humans : Table() {
    var user: Column<String> = varchar("user", 50).primaryKey() //The username for the human
    var pass: Column<String> = varchar("pass",50) //The password for the user, it will be hashed before getting here
    var uid: Column<Int> = integer("uid").primaryKey() //the user Id for the human
    var fpath: Column<String> = varchar("fpath", 50) //the file path on the server that the human has files located
    var groupId: Column<String> = varchar("groupId", 50).primaryKey() //The hospital id they belong to (possibly linking it to other tables)
    var access: Column<Int> = integer("accesslvl") // How much power does this user have?
}