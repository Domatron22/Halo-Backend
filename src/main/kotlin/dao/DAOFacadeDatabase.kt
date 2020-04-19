package main.kotlin.dao

import main.kotlin.model.Human
import main.kotlin.model.MedCenter
import main.kotlin.model.Schedule
import main.kotlin.model.File
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction
import java.io.Closeable

/* Author: Dominic Triano
 * Date: 4/3/2020
 * Language: Kotlin
 * Project: Halo
 * Description:
 * This file includes all of the database operations needed for this to funtion
 *
 */

interface DAOFacade: Closeable{
    fun init() //Initiation for the databases
    fun createHuman(userId : String, pwrd : String, fname: String, lname: String, grpCode : String, lvl : Int) //creates a human object with the passed in parameters
    fun changePwrd(userId : String, newPwrd: String)//changes of the password of a selected user
    fun deleteHuman(userId: String, grpCode: String)//deletes a human from the database
    fun createMedCenter(hidId : String, grpCode : String)//creates the entry for a medCenter
    fun deleteMedCenter(grpCode: String)//deletes a medCenter from the database
    fun getHuman(userId : String, pass : String): Human?//selects a human from the database
    fun authentication(userId : String, pass : String): Boolean//authenticates the users user and password
    fun getAllHumans(): List<Human>//gets all humans
    fun getMedCenter(grpCode : String): MedCenter?//selects a medCenter from the database
    fun getMedHumans(grpCode: String): List<Human>//selects all humans belonging to a medCenter
    fun getAllMedCenters(): List<MedCenter>//selects all medCenters
    fun getSchedule(): List<Schedule> //returns the current schedule
    fun getAccess(user : String) : Int //returns a users access level
    fun getUserFiles(user : String) : List<File>//returns the list of files relating to a user
    fun getClients(hid : String) : List<Human> //returns the clients of a doctor
}

class DAOFacadeDatabase(val db: Database): DAOFacade{

    override fun init() = transaction(db){
        SchemaUtils.create(Humans)
        SchemaUtils.create(MedCenters)
        SchemaUtils.create(Files)
        SchemaUtils.create(Schedules)

        val humans = listOf(Human("smith1", "1234", "John", "Smith", "GHP", "Empty" ,1 ),
            Human("doe1", "1234", "Jane" , "Doe", "JAMC", "Empty" , 2))


        Humans.batchInsert(humans){ human ->
            this[Humans.user] = human.user
            this[Humans.pass] = human.pass
            this[Humans.f_name] = human.fname
            this[Humans.l_name] = human.lname
            this[Humans.groupId] = human.groupId
            this[Humans.schedule] = human.schedule
            this[Humans.access] = human.access
        }

        val medCenters = listOf(MedCenter("Good Hospital", "GHP"),
            MedCenter("Just an Alright Medical Center", "JAMC"))

        MedCenters.batchInsert(medCenters) { medCenter ->
            this[MedCenters.hid] = medCenter.hid
            this[MedCenters.groupid] = medCenter.groupId
        }

        val files = listOf(File("smith1" , "John Smith" , "HeartScan.jpg" , "GHP"),
            File("smith1" , "John Smith" , "ShoulderSurgeryDescription.pdf" , "GHP"))

        Files.batchInsert(files){ file ->
            this[Files.user] = file.user
            this[Files.fullName] = file.fullName
            this[Files.file] = file.fileName
            this[Files.hid] = file.hid
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

    override fun getHuman(userId: String, pass : String): Human? = transaction(db){
        Humans.select{Humans.user eq userId}.map{
            Humans.select { Humans.pass eq pass }.map{
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
        }.singleOrNull()
    }

    override fun authentication(userId: String, pass: String) : Boolean{

        val human : ArrayList<Human> = arrayListOf()
        transaction(db) {
            Humans.select { Humans.user eq userId }.map {
                Humans.select { Humans.pass eq pass }.map {
                    human.add(
                    Human(
                        user = it[Humans.user],
                        groupId = it[Humans.groupId],
                        pass = it[Humans.pass],
                        fname = it[Humans.f_name],
                        lname = it[Humans.l_name],
                        schedule = it[Humans.schedule],
                        access = it[Humans.access]
                        )
                    )
                }
            }
        }

        return human.isNotEmpty()
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

    override fun getSchedule() = transaction(db){
        Schedules.selectAll().map {
            Schedule(
                it[Schedules.month],
                it[Schedules.day],
                it[Schedules.year],
                it[Schedules.open],
                it[Schedules.close]
            )
        }
    }

    override fun getAccess(user: String) : Int {

        val human : ArrayList<Human> = arrayListOf()
        transaction(db) {
            Humans.select { Humans.user eq user }.map {
                human.add(
                    Human(
                        user = it[Humans.user],
                        groupId = it[Humans.groupId],
                        pass = it[Humans.pass],
                        fname = it[Humans.f_name],
                        lname = it[Humans.l_name],
                        schedule = it[Humans.schedule],
                        access = it[Humans.access]
                    )
                )
            }
        }

        return human[0].access

    }

    override fun getUserFiles(user: String): List<File>{
        val fileList : ArrayList<File> = arrayListOf()
        transaction(db){
            Files.select { Files.user eq user }.map{
                fileList.add(
                    File(
                        user = it[Files.user],
                        fullName = it[Files.fullName],
                        fileName = it[Files.file],
                        hid = it[Files.hid]
                    )
                )
            }

        }

        return fileList
    }

    override fun getClients(user: String): List<Human>{
        val humanList : ArrayList<Human> = arrayListOf()
        transaction(db){
            Humans.select { Humans.user eq user }.map{
                humanList.add(
                    Human(
                        user = it[Humans.user],
                        groupId = it[Humans.groupId],
                        pass = it[Humans.pass],
                        fname = it[Humans.f_name],
                        lname = it[Humans.l_name],
                        schedule = it[Humans.schedule],
                        access = it[Humans.access]
                    )
                )
            }
        }

        return humanList
    }



    override fun close(){ }

}