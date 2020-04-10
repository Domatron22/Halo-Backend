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
import io.ktor.response.respond
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty
import main.kotlin.dao.DAOFacadeDatabase
import main.kotlin.model.Human
import main.kotlin.model.MedCenter
import org.jetbrains.exposed.sql.Database
import java.io.File

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
                    call.respond(FreeMarkerContent("Home.ftl", null))
                }
            }

            route("/signIn"){
                get{
                    call.respond(FreeMarkerContent("SignIn.ftl", null))

                }
                post{
                    val postParameters: Parameters = call.receiveParameters()
                    val action = call.request.queryParameters["submit"]
                    print(postParameters["inputUser"] +" "+ postParameters["inputPassword"])
                    when(action){
                        "signin" -> if(dao.authentication(postParameters["inputUser"]!!, postParameters["inputPassword"]!!))
                        {
                            print("testing************************************************************************************")
                            when(dao.getAccess(postParameters["inputUser"]!!)){
                                1 -> call.respond(FreeMarkerContent("Client.ftl", dao.getHuman(postParameters["inputUser"]!!,postParameters["inputPassword"]!!)))
                                2 -> call.respond(FreeMarkerContent("Staff.ftl", dao.getHuman(postParameters["inputUser"]!!,postParameters["inputPassword"]!!)))
                                3 -> call.respond(FreeMarkerContent("Dev.ftl", dao.getHuman(postParameters["inputUser"]!!,postParameters["inputPassword"]!!)))
                                else -> call.respond(FreeMarkerContent("SignIn.ftl", null))
                            }
                        }else{
                            call.respond(FreeMarkerContent("SignIn.ftl", null))
                        }
                    }
                    }
            }

            route("/authorization"){
                post{
                    val postParameters: Parameters = call.receiveParameters()
                    val action = call.request.queryParameters["action"]
                    print(postParameters["inputUser"] +" "+ postParameters["inputPassword"])
                    when(action){
                        "signin" -> if(dao.authentication(postParameters["inputUser"]!!, postParameters["inputPassword"]!!))
                        {
                            print("testing************************************************************************************")
                            when(dao.getAccess(postParameters["inputUser"]!!)){
                                1 -> call.respond(FreeMarkerContent("Client.ftl", dao.getHuman(postParameters["inputUser"]!!,postParameters["inputPassword"]!!)))
                                2 -> call.respond(FreeMarkerContent("Staff.ftl", dao.getHuman(postParameters["inputUser"]!!,postParameters["inputPassword"]!!)))
                                3 -> call.respond(FreeMarkerContent("Dev.ftl", dao.getHuman(postParameters["inputUser"]!!,postParameters["inputPassword"]!!)))
                                else -> call.respond(FreeMarkerContent("SignIn.ftl", null))
                            }
                        }else{
                            call.respond(FreeMarkerContent("SignIn.ftl", null))
                        }
                    }
                }
            }
        }
    }.start(wait=true)
}