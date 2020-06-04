package marvel_universe_api

import com.google.gson.Gson
import io.ktor.application.call
import io.ktor.http.ContentType
import io.ktor.response.respondText
import io.ktor.routing.get
import io.ktor.routing.routing
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction

object Heroes : Table("heroes") {
    val id = integer("id").autoIncrement()
    val name = varchar("name", 45)
    val description = varchar("description", 250)
    val poster = varchar("poster", 250)
    override val primaryKey = PrimaryKey(Heroes.id)
}

data class Fruit(val id: Int, val name: String, val description: String, val poster: String)

/*
    Init MySQL database connection
 */
fun initDB() {
    val url = "jdbc:mysql://root:root@localhost:3306/marvel_universe_db?useUnicode=true&serverTimezone=UTC"
    val driver = "com.mysql.cj.jdbc.Driver"
    Database.connect(url, driver)
}

/*
    Getting fruit data from database
 */
fun getAllHeroes(): String {
    var json: String = ""
    transaction {
        val res = Heroes.selectAll()
        val c = ArrayList<Fruit>()
        for (f in res) {
            c.add(
                Fruit(
                    id = f[Heroes.id],
                    name = f[Heroes.name],
                    description = f[Heroes.description],
                    poster = f[Heroes.poster]
                )
            )
        }
        json = Gson().toJson(c);
    }
    return json
}

/*
    Main function
 */
fun main(args: Array<String>) {
    initDB()
    embeddedServer(Netty, 8080) {
        routing {
            get("/") {
                call.respondText(getAllHeroes(), ContentType.Text.Html)
            }
        }
    }.start(wait = true)
}
