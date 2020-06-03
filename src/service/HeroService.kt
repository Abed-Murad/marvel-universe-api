package marvel_universe_api.service

import marvel_universe_api.model.Hero
import marvel_universe_api.model.Heroes
import marvel_universe_api.model.NewHero
import marvel_universe_api.service.DatabaseFactory.dbQuery
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.selectAll

class HeroService {


    suspend fun getAllWidgets(): List<Hero> = dbQuery {
        Heroes.selectAll().map { toHero(it) }
    }

    suspend fun addHero(hero: NewHero) {
        var key = 0
        dbQuery {
            key = (Heroes.insert {
                it[name] = hero.name
                it[color] = hero.color
                it[quote] = hero.quote
                it[poster] = hero.poster
            } get Heroes.id)
        }
    }


    private fun toHero(row: ResultRow): Hero =
        Hero(
            id = row[Heroes.id],
            name = row[Heroes.name],
            color = row[Heroes.color],
            quote = row[Heroes.quote],
            poster = row[Heroes.poster]
        )
}
