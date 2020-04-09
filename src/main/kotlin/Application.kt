package main.kotlin

import freemarker.template.Configuration
import freemarker.cache.* // template loaders live in this package
import freemarker.cache.ClassTemplateLoader
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
            }
        }
    }.start(wait=true)
}