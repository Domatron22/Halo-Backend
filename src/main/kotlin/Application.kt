
import io.ktor.application.*
import io.ktor.response.*
import io.ktor.request.*
import io.ktor.routing.*
import io.ktor.http.*
import io.ktor.html.*
import kotlinx.html.*
import io.ktor.features.*
import io.ktor.gson.*
import io.ktor.client.*
import io.ktor.client.engine.apache.*
import io.ktor.client.features.json.*
import io.ktor.client.request.*
import kotlinx.coroutines.*
import org.jetbrains.exposed.sql.*

/* Author: Dominic Triano
 * Date: 2/15/2020
 * Language: Kotlin
 * Project: Halo
 * Description:
 * Processing of the data coming into the database
 *
 */

fun main(args: Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)


@Suppress("unused") // Referenced in application.conf
@kotlin.jvm.JvmOverloads
fun Application.module(testing: Boolean = false) {




    install(CORS) {

        method(HttpMethod.Options)
        method(HttpMethod.Put)
        method(HttpMethod.Delete)
        method(HttpMethod.Patch)
        header(HttpHeaders.Authorization)
        header("userId")
        header("pwrd")
        header("grpCode")
        header("lvl")
        header("medId")
        header("forum")
        allowCredentials = true
        anyHost()
    }

    install(ContentNegotiation) {
        gson {
        }
    }

    val client = HttpClient(Apache) {
        install(JsonFeature) {
            serializer = GsonSerializer()
        }
    }
    runBlocking {
        // Sample for making a HTTP Client request
        /*
        val message = client.post<JsonSampleClass> {
            url("http://127.0.0.1:8080/path/to/endpoint")
            contentType(ContentType.Application.Json)
            body = JsonSampleClass(hello = "world")
        }
        */
    }

    routing {
        //        get("/") {
//            call.respondText("HELLO WORLD!", contentType = ContentType.Text.Plain)
//        }


        //Registers a medical center using the inputted data
        get("/reg-center")
        {
            val medId : String = call.request.header("medId") ?: "0"
            val grpId : String = call.request.header("grpCode") ?: "0"

            try {
                registerCenter(medId, grpId)
            }catch(e: java.lang.IllegalStateException)
            {
                call.respond(HttpStatusCode(1150, "Invoked at an Illegal time, not in an appropriate state"), "Does Not Exist")
            }catch (e: java.util.NoSuchElementException)
            {
                call.respond(HttpStatusCode(1151, "Requested an element that does not exist"), "Does Not Exist")

            }catch (e: org.jetbrains.exposed.exceptions.ExposedSQLException)
            {
                call.respond(HttpStatusCode(1152, "SQL Query Invalid or table does not exist/corrupted"), "Does Not Exist")

            }

        }

        //Resisters a human to a hospital
        get("/reg-hum")
        {
            val usrId : String = call.request.header("userId") ?: "0"
            val grpId : String = call.request.header("grpCode") ?: "0"
            val pwrd : String = call.request.header("pass") ?: "0"
            val lvl : String = call.request.header("lvl") ?: "0"

            try {
                registerHuman(usrId, pwrd, grpId, lvl.toInt())
            }catch(e: java.lang.IllegalStateException)
            {
                call.respond(HttpStatusCode(1150, "Invoked at an Illegal time, not in an appropriate state"), "Does Not Exist")
            }catch (e: java.util.NoSuchElementException)
            {
                call.respond(HttpStatusCode(1151, "Requested an element that does not exist"), "Does Not Exist")

            }catch (e: org.jetbrains.exposed.exceptions.ExposedSQLException)
            {
                call.respond(HttpStatusCode(1152, "SQL Query Invalid or table does not exist/corrupted"), "Does Not Exist")

            }

        }

        //Probes the database to see if the user exists in the hospital
        get("/probe")
        {
            val usrId : String = call.request.header("userId") ?: "0"
            val grpId : String = call.request.header("grpCode") ?: "0"

            try {
                call.respondText ( humanProbe(usrId, grpId))
            }catch(e: java.lang.IllegalStateException)
            {
                call.respond(HttpStatusCode(1150, "Invoked at an Illegal time, not in an appropriate state"), "Does Not Exist")
            }catch (e: java.util.NoSuchElementException)
            {
                call.respond(HttpStatusCode(1151, "Requested an element that does not exist"), "Does Not Exist")

            }catch (e: org.jetbrains.exposed.exceptions.ExposedSQLException)
            {
                call.respond(HttpStatusCode(1152, "SQL Query Invalid or table does not exist/corrupted"), "Does Not Exist")

            }

        }

        //Finds the directory path for the user
        get("/files")
        {
            val usrId : String = call.request.header("userId") ?: "0"
            val grpId : String = call.request.header("grpCode") ?: "0"

            try {
                call.respondText ( fileManager(usrId, grpId))
            }catch(e: java.lang.IllegalStateException)
            {
                call.respond(HttpStatusCode(1150, "Invoked at an Illegal time, not in an appropriate state"), "Does Not Exist")
            }catch (e: java.util.NoSuchElementException)
            {
                call.respond(HttpStatusCode(1151, "Requested an element that does not exist"), "Does Not Exist")

            }catch (e: org.jetbrains.exposed.exceptions.ExposedSQLException)
            {
                call.respond(HttpStatusCode(1152, "SQL Query Invalid or table does not exist/corrupted"), "Does Not Exist")

            }

        }

        //Returns all of the comments associated with the forum requested
        get("/forums")
        {
            val forum : String = call.request.header("forum") ?: "0"

            try {

                var comments = forumGet(forum)
                for(x in comments.second)//for every item in the second mutable list in the pair
                {
                    call.respondText( x )
                }


            }catch(e: java.lang.IllegalStateException)
            {
                call.respond(HttpStatusCode(1150, "Invoked at an Illegal time, not in an appropriate state"), "Does Not Exist")
            }catch (e: java.util.NoSuchElementException)
            {
                call.respond(HttpStatusCode(1151, "Requested an element that does not exist"), "Does Not Exist")

            }catch (e: org.jetbrains.exposed.exceptions.ExposedSQLException)
            {
                call.respond(HttpStatusCode(1152, "SQL Query Invalid or table does not exist/corrupted"), "Does Not Exist")

            }

        }

    }

}

data class JsonSampleClass(val hello: String)
