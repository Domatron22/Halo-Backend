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
                    val postParameters = call.receiveParameters()
                    val action = (call.request.queryParameters["action"] ?: "empty")

                    print("testingg1")
                    print(postParameters["action"])
                    print(postParameters["inputUser"])
                    print(postParameters["inputPassword"])
                    when(action){
                        "signin" -> {
                            print("testingg2")
                            val id = postParameters["inputUser"] ?: "empty"
                            val pass = postParameters["inputPassword"] ?: "empty"
                            if(dao.authentication(id, pass))
                            {
                                print("testingg3")
                                when(dao.getAccess(id)){
                                    1 -> call.respond(FreeMarkerContent("Client.ftl", dao.getHuman(id, pass)))
                                    2 -> call.respond(FreeMarkerContent("Staff.ftl", dao.getHuman(id, pass)))
                                    3 -> call.respond(FreeMarkerContent("Dev.ftl", dao.getHuman(id, pass)))
                                    else -> call.respond(FreeMarkerContent("SignIn.ftl", null))
                                }
                            }else{
                                call.respond(FreeMarkerContent("SignIn.ftl", null))
                            }
                        }
                    }
                }

            }

            route("/authorization"){
                get{

                }
                post{

                }
            }
        }
    }.start(wait=true)
}