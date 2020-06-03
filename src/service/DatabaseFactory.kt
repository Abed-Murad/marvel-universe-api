package marvel_universe_api.service

import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import marvel_universe_api.model.Heroes
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.transactions.transaction
import org.jetbrains.exposed.sql.SchemaUtils.create
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction

object DatabaseFactory {
    fun init() {
        Database.connect(hikari())
        transaction {
            create(Heroes)
            Heroes.insert {
                it[name] = "Abed"
                it[quote] = "Abed is good"
                it[color] = "Green"
                it[poster] = "The poster"
            }
            Heroes.insert {
                it[name] = "Abed2"
                it[quote] = "Abed is good2"
                it[color] = "Green2"
                it[poster] = "The poster2"
            }
        }
    }


    private fun hikari(): HikariDataSource {
        val config = HikariConfig()
        config.driverClassName = "org.h2.Driver"
        config.jdbcUrl = "jdbc:h2:mem:test"
        config.maximumPoolSize = 3
        config.isAutoCommit = false
        config.transactionIsolation = "TRANSACTION_REPEATABLE_READ"
        config.validate()
        return HikariDataSource(config)
    }


    suspend fun <T> dbQuery(block: suspend () -> T): T = newSuspendedTransaction { block() }


}