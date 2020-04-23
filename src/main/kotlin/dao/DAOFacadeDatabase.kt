package main.kotlin.dao

import io.ktor.html.each
import main.kotlin.model.*
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction
import org.joda.time.DateTime
import java.io.Closeable
import java.util.Date

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
    fun getHuman(userId : String, pass : String): List<Human>//selects a human from the database
    fun getName(userId: String) : String
    fun getGroup(user: String) : String
    fun authentication(userId : String, pass : String): Boolean//authenticates the users user and password
    fun getAllHumans(): List<Human>//gets all humans
    fun getMedCenter(grpCode : String): MedCenter?//selects a medCenter from the database
    fun getMedHumans(grpCode: String): List<Human>//selects all humans belonging to a medCenter
    fun getAllMedCenters(): List<MedCenter>//selects all medCenters
    fun getAccess(user : String) : Int //returns a users access level
    fun getUserFiles(user : String) : List<File>//returns the list of files relating to a user
    fun getMedFiles(grpCode: String) : List<File>
    fun fileSearch(user: String, fileName : String) : List<File>
    fun getClients(groupId: String) : List<Human> //returns the clients of a doctor
    fun getSchedule(): List<Schedule> //returns the current schedule
    fun getClientSchedule(user: String): List<Schedule> //returns the clients current schedule
    fun getDocSchedule(doc: String): List<Schedule> //returns the doctors current schedule
    fun getDoctor(user: String, pass: String): List<Doctor> //selects a doctor
    fun docAuthentication(userId: String, pass: String) : Boolean //authenticates the doctor
    fun getDocGroup(user: String) : String //Checks their group
    fun getDoctors(grpCode: String) : List<Doctor>
    fun getDocName(user: String) : String
    fun createDoctor(user: String, pass: String, name: String, groupId: String)
}

class DAOFacadeDatabase(val db: Database): DAOFacade{

    override fun init() = transaction(db){
        SchemaUtils.create(Humans)
        SchemaUtils.create(MedCenters)
        SchemaUtils.create(Files)
        SchemaUtils.create(Schedules)
        SchemaUtils.create(Doctors)

        val humans = listOf(Human("smith1", "1234", "John", "Smith", "GHP" ,1 ),
            Human("doe1", "1234", "Jane" , "Doe", "JAMC" , 3),
            Human("roll" , "1234" , "Rock" , "N' Roll" , "JAMC" , 1),
            Human("doe2", "1234", "John" , "Doe", "GHP" , 3))


        Humans.batchInsert(humans){ human ->
            this[Humans.user] = human.user
            this[Humans.pass] = human.pass
            this[Humans.f_name] = human.fname
            this[Humans.l_name] = human.lname
            this[Humans.groupId] = human.groupId
            this[Humans.access] = human.access
        }

        val medCenters = listOf(MedCenter("Good Hospital", "GHP"),
            MedCenter("Just an Alright Medical Center", "JAMC"))

        MedCenters.batchInsert(medCenters) { medCenter ->
            this[MedCenters.hid] = medCenter.hid
            this[MedCenters.groupid] = medCenter.groupId
        }

        val files = listOf(File("smith1" , "John Smith" , "HeartScan.jpg" , "GHP"),
            File("smith1" , "John Smith" , "ShoulderSurgeryPictures.pdf" , "GHP"))

        Files.batchInsert(files){ file ->
            this[Files.user] = file.user
            this[Files.fullName] = file.fullName
            this[Files.file] = file.fileName
            this[Files.hid] = file.hid
        }

        val docs = listOf(Doctor("Calla" , "1234" , "T.C. Callahan" , "GHP"),
            Doctor("Alexa" , "1234" , "Jordan Alexander" , "GHP"),
            Doctor("Stran" , "1234" , "Stephen Strange" , "JAMC"))

        Doctors.batchInsert(docs){ doctor ->
            this[Doctors.user] = doctor.user
            this[Doctors.pass] = doctor.pass
            this[Doctors.groupId] = doctor.groupId
            this[Doctors.name] = doctor.name
        }

        val schedules = listOf(Schedule("smith1" , "Calla" , DateTime(2020, 5, 3, 14, 0)))

        Schedules.batchInsert(schedules){ schedule ->
            this[Schedules.client] = schedule.client
            this[Schedules.doctor] = schedule.doctor
            this[Schedules.day] = schedule.day
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

    override fun createDoctor(user: String, pass: String, name: String, groupId: String)= transaction(db){
        Doctors.insert{
            it[Doctors.user] = user;
            it[Doctors.groupId] = groupId;
            it[Doctors.name] = name;
            it[Doctors.pass] = pass;
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

    override fun getHuman(userId: String, pass : String): List<Human>{
        val humanList : ArrayList<Human> = arrayListOf()
        transaction(db) {
            Humans.select { Humans.user eq userId }.map {
                humanList.add(
                    Human(
                        user = it[Humans.user],
                        groupId = it[Humans.groupId],
                        pass = it[Humans.pass],
                        fname = it[Humans.f_name],
                        lname = it[Humans.l_name],
                        access = it[Humans.access]
                    )
                )

            }
        }

        return humanList
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
                it[Humans.pass],
                it[Humans.f_name],
                it[Humans.l_name],
                it[Humans.groupId],
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
                        access = it[Humans.access]
                    )
                )
            }
        }

        return human[0].access

    }

    override fun getName(userId: String): String{
        val human : ArrayList<Human> = arrayListOf()
        transaction(db) {
            Humans.select { Humans.user eq userId }.map {
                human.add(
                    Human(
                        user = it[Humans.user],
                        groupId = it[Humans.groupId],
                        pass = it[Humans.pass],
                        fname = it[Humans.f_name],
                        lname = it[Humans.l_name],
                        access = it[Humans.access]
                    )
                )
            }
        }

        return (human[0].fname + " " + human[0].lname)
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

    override fun getMedFiles(grpCode: String): List<File>{
        val fileList : ArrayList<File> = arrayListOf()
        transaction(db){
            Files.select { Files.hid eq grpCode }.map{
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

    override fun fileSearch(user: String, fileName: String): List<File>{
        var fileList : ArrayList<File> = arrayListOf()
        transaction(db){
            Files.select { Files.user eq user }.map{
                Files.select{Files.file.substring(0, fileName.indexOf(".")) eq fileName}.map {
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

        }

        return fileList
    }

    override fun getSchedule() = transaction(db){
        Schedules.selectAll().map {
            Schedule(
                it[Schedules.client],
                it[Schedules.doctor],
                it[Schedules.day]
            )
        }
    }

    override fun getClientSchedule(user: String): List<Schedule>{
        var schedule : ArrayList<Schedule> = arrayListOf()
        transaction(db){
            Schedules.select { Schedules.client eq user }.map{
                schedule.add(
                    Schedule(
                        client = it[Schedules.client],
                        doctor = it[Schedules.doctor],
                        day = it[Schedules.day]
                    )
                )
            }
        }

        for (schedule in schedule) {
            schedule.doctor = getDocName(schedule.doctor)
        }

        return schedule
    }

    override fun getDocSchedule(doc: String): List<Schedule>{
        var schedule : ArrayList<Schedule> = arrayListOf()
        transaction(db){
            Schedules.select { Schedules.doctor eq doc }.map{
                schedule.add(
                    Schedule(
                        client = it[Schedules.client],
                        doctor = it[Schedules.doctor],
                        day = it[Schedules.day]
                    )
                )
            }
        }

        for (schedule in schedule) {
            schedule.client = getName(schedule.client)
        }

        return schedule
    }

    override fun getDoctor(user: String, pass: String): List<Doctor> {
        val doc : ArrayList<Doctor> = arrayListOf()
        transaction(db) {
            Doctors.select { Doctors.user eq user }.map {
                doc.add(
                    Doctor(
                        user = it[Doctors.user],
                        groupId = it[Doctors.groupId],
                        pass = it[Doctors.pass],
                        name = it[Doctors.name]
                    )
                )

            }
        }

        return doc
    }




    override fun getDoctors(grpCode: String): List<Doctor>{
        val doc : ArrayList<Doctor> = arrayListOf()
        transaction(db) {
            Doctors.select { Doctors.groupId eq grpCode }.map {
                doc.add(
                    Doctor(
                        user = it[Doctors.user],
                        groupId = it[Doctors.groupId],
                        pass = it[Doctors.pass],
                        name = it[Doctors.name]
                    )
                )

            }
        }

        return doc
    }

    override fun docAuthentication(userId: String, pass: String): Boolean{
        val doc : ArrayList<Doctor> = arrayListOf()
        transaction(db) {
            Doctors.select { Doctors.user eq userId }.map {
                Doctors.select { Doctors.pass eq pass }.map {
                    doc.add(
                        Doctor(
                            user = it[Doctors.user],
                            groupId = it[Doctors.groupId],
                            pass = it[Doctors.pass],
                            name = it[Doctors.name]
                        )
                    )
                }
            }
        }

        return doc.isNotEmpty()
    }

    override fun getClients(groupId : String): List<Human>{
        val humanList : ArrayList<Human> = arrayListOf()
        transaction(db){
            Humans.select { Humans.groupId eq groupId }.map{
                humanList.add(
                    Human(
                        user = it[Humans.user],
                        groupId = it[Humans.groupId],
                        pass = it[Humans.pass],
                        fname = it[Humans.f_name],
                        lname = it[Humans.l_name],
                        access = it[Humans.access]
                    )
                )
            }
        }

        return humanList
    }

    override fun getDocGroup(user: String): String{
        val grp : ArrayList<Doctor> = arrayListOf()
        transaction(db) {
            Doctors.select { Doctors.user eq user }.map {
                grp.add(
                    Doctor(
                        user = it[Doctors.user],
                        groupId = it[Doctors.groupId],
                        pass = it[Doctors.pass],
                        name = it[Doctors.name]
                    )
                )
            }
        }

        return grp[0].groupId
    }

    override fun getGroup(user: String): String{
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
                        access = it[Humans.access]
                    )
                )
            }
        }

        return human[0].groupId
    }

    override fun getDocName(user: String): String{
        val doc : ArrayList<Doctor> = arrayListOf()
        transaction(db) {
            Doctors.select { Doctors.user eq user }.map {
                doc.add(
                    Doctor(
                        user = it[Doctors.user],
                        groupId = it[Doctors.groupId],
                        pass = it[Doctors.pass],
                        name = it[Doctors.name]
                    )
                )

            }
        }

        return doc[0].name
    }

    override fun close(){ }

}