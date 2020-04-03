package main.kotlin.dao

import main.kotlin.model.Human
import main.kotlin.model.MedCenter
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction
import java.io.Closeable

/* Author: Dominic Triano
 * Date: 4/3/2020
 * Language: Kotlin
 * Project: Halo
 * Description:
 *
 *
 */

interface DAOFacade: Closeable{
    fun init()
    fun createHuman(userId : String, pwrd : String, fname: String, lname: String, grpCode : String, lvl : Int)
    fun manageHuman(userId : String, grpCode: String)
    fun deleteHuman(id:Int)
    fun createMedCenter(hidId : String, grpCode : String)
    fun manageMedCenter(grpCode: String)
    fun deleteMedCenter(grpCode: String)
    fun getHuman(userId : String, grpCode : String): Human?
    fun getAllHumans(): List<Human>
    fun getMedCenter(grpCode : String): MedCenter?
    fun getMedHumans(grpCode: String): List<Human>
    fun getAllMedCenters(): List<MedCenter>
}

class DAOFacadeDatabase(val db: Database): DAOFacade{

    override fun init() = transaction(db){
        SchemaUtils.create(Humans)

        val humans = listOf(Human("smith1", "1234", "John", "Smith", "smith1", "0001", 1 ),
            Human("doe1", "1234", "Jane" , "Doe", "doe1", "0002", 2))

        Humans.batchInsert(humans){ human ->
            this[Humans.user] = human.user
            this[Humans.pass] = human.pass
            this[Humans.f_name] = human.fname
            this[Humans.l_name] = human.lname
            this[Humans.fpath] = human.fpath
            this[Humans.groupId] = human.groupId
            this[Humans. access] = human.access
        }
        Unit
    }

    override fun createHuman(userId: String, pwrd: String, fname: String, lname: String, grpCode: String, lvl: Int)
}
}

}