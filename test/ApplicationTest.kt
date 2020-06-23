package marvel_universe_api

import com.am.module
import io.ktor.application.Application
import io.ktor.http.HttpMethod
import io.ktor.http.HttpStatusCode
import io.ktor.server.testing.handleRequest
import io.ktor.server.testing.withTestApplication
import kotlin.test.Test
import kotlin.test.assertEquals

class ApplicationTest {
    @Test
    fun testHeroesRequest() = withTestApplication(Application::module) {
        with(handleRequest(HttpMethod.Get, "/v1/public/heroes")) {
            assertEquals(HttpStatusCode.OK, response.status())
        }
    }

    @Test
    fun testMoviesRequest() = withTestApplication(Application::module) {
        with(handleRequest(HttpMethod.Get, "/v1/public/heroes/1/movies")) {
            assertEquals(HttpStatusCode.OK, response.status())
        }
    }

    @Test
    fun testHeroMoviesRequest() = withTestApplication(Application::module) {
        with(handleRequest(HttpMethod.Get, "/v1/public/heroes/1/movies")) {
            assertEquals(HttpStatusCode.OK, response.status())
        }
    }

    @Test
    fun testGetHeroByIdRequest() = withTestApplication(Application::module) {
        with(handleRequest(HttpMethod.Get, "/v1/public/heroes/2")) {
            assertEquals(HttpStatusCode.OK, response.status())
        }
    }

    @Test
    fun testMovieHeroesRequest() = withTestApplication(Application::module) {
        with(handleRequest(HttpMethod.Get, "/v1/public/movies/1/heroes")) {
            assertEquals(HttpStatusCode.OK, response.status())
        }
    }
}