package marvel_universe_api

import com.google.gson.Gson
import io.ktor.application.call
import io.ktor.http.ContentType
import io.ktor.response.respondText
import io.ktor.routing.get
import io.ktor.routing.routing
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty
import marvel_universe_api.model.Heroes
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction


object Fruits : Table("fruits") {
    val id = integer("id").autoIncrement()
    val name = varchar("name", length = 64)
    val value = integer("value")
    override val primaryKey = PrimaryKey(Fruits.id)
}

data class Fruit(val id: Int, val name: String, val value: Int)

/*
    Init MySQL database connection
 */
fun initDB() {
    val url = "jdbc:mysql://root:root@localhost:3306/anychart_db?useUnicode=true&serverTimezone=UTC"
    val driver = "com.mysql.cj.jdbc.Driver"
    Database.connect(url, driver)
}

/*
    Getting fruit data from database
 */
fun getTopFruits(): String {
    var json: String = ""
    transaction {
        val res = Fruits.selectAll()
        val c = ArrayList<Fruit>()
        for (f in res) {
            c.add(Fruit(id = f[Fruits.id], name = f[Fruits.name], value = f[Fruits.value]))
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
                call.respondText(getTopFruits(), ContentType.Text.Html)
            }
        }
    }.start(wait = true)
}
