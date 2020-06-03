package marvel_universe_api.model

data class Hero(
        val id: Int,
        val name: String,
        val quote: String,
        val color: String,
        val poster: String,
        val details: List<Detail>
)

data class Detail(
        val id: Int,
        val name: String,
        val plot: String,
        val poster: String,
        val rootId: Int
)

