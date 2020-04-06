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
    fun changePwrd(userId : String, newPwrd: String)
    fun deleteHuman(userId: String, grpCode: String)
    fun createMedCenter(hidId : String, grpCode : String)
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
        SchemaUtils.create(MedCenters)

        val humans = listOf(Human("smith1", "1234", "John", "Smith", "GHP", "" ,1 ),
            Human("doe1", "1234", "Jane" , "Doe", "JAMC", "" , 2))


        Humans.batchInsert(humans){ human ->
            this[Humans.user] = human.user
            this[Humans.pass] = human.pass
            this[Humans.f_name] = human.fname
            this[Humans.l_name] = human.lname
            this[Humans.groupId] = human.groupId
            this[Humans. access] = human.access
        }

        val medCenters = listOf(MedCenter("Good Hospital", "GHP"),
            MedCenter("Just an Alright Medical Center", "JAMC"))

        MedCenters.batchInsert(medCenters) { medCenter ->
            this[MedCenters.hid] = medCenter.hid
            this[MedCenters.groupid] = medCenter.groupId
        }
        Unit
    }

    override fun createHuman(userId: String, pwrd: String, fname: String, lname: String, grpCode: String, lvl: Int) = transaction(db){
        Humans.insert {
            it[Humans.user] = userId;
            it[Humans.pass] = pwrd;
            it[Humans.f_name] = fname;
            it[Humans.l_name] = lname;
            it[Humans.groupId] = grpCode;
            it[Humans. access] = lvl;
        }
        Unit
    }

    override fun changePwrd(userId: String, newPwrd: String) = transaction(db) {
        Humans.update({Humans.user eq userId}){
            it[Humans.pass] = newPwrd;
        }
        Unit
    }

    override fun deleteHuman(userId: String, grpCode: String) = transaction(db) {
        Humans.deleteWhere {Humans.user eq userId}
        Unit
    }

    override fun getHuman(userId: String, grpCode : String): Human? = transaction(db){
        Humans.select{Humans.user eq userId}.map{
            Human(
                it[Humans.user],
                it[Humans.groupId],
                it[Humans.pass],
                it[Humans.f_name],
                it[Humans.l_name],
                it[Humans.schedule],
                it[Humans.access]
            )
        }.singleOrNull()
    }

    override fun getAllHumans() = transaction(db) {
        Humans.selectAll().map{
            Human(
                it[Humans.user],
                it[Humans.groupId],
                it[Humans.pass],
                it[Humans.f_name],
                it[Humans.l_name],
                it[Humans.schedule],
                it[Humans.access]
            )
        }
    }

    override fun getMedHumans(grpCode: String) = transaction(db){
        Humans.select{Humans.groupId eq grpCode}.map{
            Human(
                it[Humans.user],
                it[Humans.groupId],
                it[Humans.pass],
                it[Humans.f_name],
                it[Humans.l_name],
                it[Humans.schedule],
                it[Humans.access]
            )
        }
    }

    override fun createMedCenter(hidId: String, grpCode: String) = transaction(db) {
        MedCenters.insert {
            it[MedCenters.hid] = hidId;
            it[MedCenters.groupid] = grpCode;
        }

        Unit
    }

    override fun deleteMedCenter(grpCode: String) = transaction(db){
        MedCenters.deleteWhere{MedCenters.groupid eq grpCode}
        Unit
    }

    override fun getMedCenter(grpCode: String) = transaction(db){
        MedCenters.select{MedCenters.groupid eq grpCode}.map{
            MedCenter(
                it[MedCenters.hid],
                it[MedCenters.groupid]
            )
        }.singleOrNull()
    }

    override fun getAllMedCenters() = transaction(db){
        MedCenters.selectAll().map{
            MedCenter(
                it[MedCenters.hid],
                it[MedCenters.groupid]
            )
        }
    }

    override fun close(){ }

}