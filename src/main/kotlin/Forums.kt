import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.Table

/* Author: Dominic Triano
 * Date: 2/20/2020
 * Language: Kotlin
 * Project: Halo
 * Description:
 *  DSL Object for the forums
 *
 */object Forums : Table() {
    var fname : Column<String> = varchar("forum", 50).primaryKey()//The name of the forum
    var user: Column<String> = varchar("user", 50) //The username for the human
    var comments: Column<String> = varchar("comm",300) //The comment that the user made
}