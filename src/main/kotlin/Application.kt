package main.kotlin

import freemarker.template.Configuration
import freemarker.cache.* // template loaders live in this package
import io.ktor.application.install
import io.ktor.application.call
import io.ktor.features.CallLogging
import io.ktor.features.ContentNegotiation
import io.ktor.freemarker.FreeMarker
import io.ktor.freemarker.FreeMarkerContent
import io.ktor.routing.routing
import io.ktor.routing.route
import io.ktor.routing.get
import io.ktor.routing.post
import io.ktor.request.receive
import io.ktor.request.receiveParameters
import io.ktor.http.content.resources
import io.ktor.http.content.static
import io.ktor.http.Parameters
import io.ktor.http.content.LocalFileContent
import io.ktor.response.respond
import io.ktor.response.respondFile
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty
import io.ktor.request.receiveMultipart
import main.kotlin.dao.DAOFacadeDatabase
import main.kotlin.model.Human
import main.kotlin.model.MedCenter
import org.jetbrains.exposed.sql.Database
import java.io.File
import io.ktor.routing.header


/* Author: Dominic Triano
 * Date: 2/15/2020
 * Language: Kotlin
 * Project: Halo
 * Description:
 * Processing of the data coming to and from the database, these are the main functions
 *
 */

val dao = DAOFacadeDatabase(Database.connect("jdbc:h2:mem:test;DB_CLOSE_DELAY=-1", driver = "org.h2.Driver"))
fun main() {
    embeddedServer(Netty, port = 8080) {
        //Configuration cfg = new Configuration(Configuration.VERSION_2_3_29)
        dao.init()
        install(FreeMarker){
            //allow the connection with the ftl files
            templateLoader = ClassTemplateLoader(this::class.java.classLoader, "templates")
            print("Test")
        }

        routing {
            route("/"){
                get{
                    //responds with said .ftl file
                    call.respond(FreeMarkerContent("Home.ftl", null))
                    //call.respondFile(File("resources", "name of file"))
                }
                post{

                }
            }

            route("/signIn"){
                get{
                    //responds with said .ftl file
                    call.respond(FreeMarkerContent("SignIn.ftl", null))

                }
                post{
                    //recieves the parameters sent from the .ftl file associated with the path
                    val postParameters = call.receiveParameters()
                    val action = postParameters["action"]
//                    testing where data is going missing
//                    print("testingg1")
//                    print(postParameters["action"])
//                    print(postParameters["inputUser"])
//                    print(postParameters["inputPassword"])
                    when(action){
                        "signIn" -> {
                            //print("testingg2")
                            val id = postParameters["inputUser"] ?: "empty"
                            val pass = postParameters["inputPassword"] ?: "empty"
                            if(dao.authentication(id, pass))
                            {
                                //print("testingg3")
                                //whatever access level they have says what they are
                                //1 - Client, 2 - Doctor, 3 - Developer, Failed to get it? Try again
                                when(dao.getAccess(id)){
                                    1 -> call.respond(FreeMarkerContent("index.ftl", mapOf("files" to dao.getUserFiles(id))))
                                    3 -> call.respond(FreeMarkerContent("dev.ftl", dao.getAllHumans()))
                                    else -> call.respond(FreeMarkerContent("SignIn.ftl", null))
                                }
                            }else if (dao.docAuthentication(id, pass)){
                                call.respond(FreeMarkerContent("staff.ftl", dao.getClients(dao.getDocGroup(id))))
                            }else{
                                call.respond(FreeMarkerContent("SignIn.ftl", null))
                            }
                        }
                        //There should be no other possible action, but just in case, send them back
                        else -> call.respond(FreeMarkerContent("SignIn.ftl", null))
                    }
                }

            }

            route("/client"){
                get{
//                    val user = call.request.queryParameters["user"] ?: "empty"
//                    call.respond(FreeMarkerContent("index.ftl", mapOf("files" to dao.getUserFiles(user))))
                }
                post{

                }
            }
            route("/staff"){
                get{
                    val user = call.request.queryParameters["user"] ?: "empty"
                    call.respond(FreeMarkerContent("staff.ftl" , mapOf("humans" to dao.getClients(user))))
                }
                post{

                }
            }
            route("/dev"){
                get{
                    val user = call.request.queryParameters["user"] ?: "empty"
                    call.respond(FreeMarkerContent("dev.ftl" , mapOf("humans" to dao.getAllHumans())))
                }

                post{

                }
            }

            route("/download"){
                get {
                    //call.respondFile(File("resources", "name of file"))
                    val action = call.request.queryParameters["action"] ?: "empty"
                    val user = call.request.queryParameters["user"] ?: "empty"
                    val fileName = call.request.queryParameters["name"] ?: "empty"
//                    println("testing" + action + " " + user + " " + fileName)
                    when(action){
                        "download" -> call.respondFile(File("resources/userFiles/" + user, fileName))
                        else -> call.respond(FreeMarkerContent("index.ftl", mapOf("files" to dao.getUserFiles(user))))
                    }
                }

                post{

                }
            }

//            route("/uploads"){
//                get{
//                    call.respond(FreeMarkerContent("upload.ftl" , null))
//                }
//                post{
//                    val postParameters = call.receiveParameters()
//                    val multipart = call.receiveMultipart()
//                    multipart.forEachPart { part ->
//                        // if part is a file (could be form item)
//                        if(part is PartData.FileItem) {
//                            // retrieve file name of upload
//                            val name = part.originalFileName!!
//                            val file = File("/uploads/$name")
//
//                            // use InputStream from part to save file
//                            part.streamProvider().use { its ->
//                                // copy the stream to the file with buffering
//                                file.outputStream().buffered().use {
//                                    // note that this is blocking
//                                    its.copyTo(it)
//                                }
//                            }
//                        }
//                        // make sure to dispose of the part after use to prevent leaks
//                        part.dispose()
//                    }
//                }
//            }


        }
    }.start(wait=true)
}