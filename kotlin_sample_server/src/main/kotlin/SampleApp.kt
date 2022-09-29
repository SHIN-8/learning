package sample

import com.fasterxml.jackson.databind.SerializationFeature
import io.ktor.application.*
import io.ktor.response.*
import io.ktor.routing.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.jackson.*
import java.util.*

data class Item(val text: String)

val snippets = Collections.synchronizedList(
            mutableListOf(
                    Item("Granblue Fantasy"),
                    Item("Princess Connect Re:Dive")
            )
)

fun main(args: Array<String>) {
    embeddedServer(Netty, 8080) {
           install(io.ktor.features.ContentNegotiation) {
               jackson {
                   enable(SerializationFeature.INDENT_OUTPUT) // Pretty Prints the JSON
               }
           }
            routing {
                   get("/") {
                       call.respond(mapOf("snippets" to synchronized(snippets){ snippets.toList()}))
                   }
               }
    }.start(wait=true)
}
