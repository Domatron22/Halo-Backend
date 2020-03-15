import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.Table

/* Author: Dominic Triano
 * Date: 2/15/2020
 * Language: Kotlin
 * Project: Halo
 * Description:
 * This is the DSL object that holds the general data for the medical center in which the humans attend
 *
 */object MedCenter : Table() {
    var hid: Column<String> = varchar("hid", 50).primaryKey() //Name of the medical center
    var groupId: Column<String> = varchar("groupId", 50).primaryKey() //group code to reference humans
}