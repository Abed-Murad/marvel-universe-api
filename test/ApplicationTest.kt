package marvel_universe_api

import io.ktor.application.Application
import io.ktor.http.HttpMethod
import io.ktor.http.HttpStatusCode
import io.ktor.server.testing.handleRequest
import io.ktor.server.testing.withTestApplication
import kotlin.test.Test
import kotlin.test.assertEquals

class ApplicationTest {
    @Test
    fun testRequests() = withTestApplication(Application::module) {
        with(handleRequest(HttpMethod.Get, "/v1/public/heroes")) {
            assertEquals(HttpStatusCode.OK, response.status())
        }
        with(handleRequest(HttpMethod.Get, "/v1/public/heroes/2")) {
            assertEquals(HttpStatusCode.OK, response.status())
        }
        with(handleRequest(HttpMethod.Get, "/v1/public/heroes/1/movies")) {
            assertEquals(HttpStatusCode.OK, response.status())
        }
        with(handleRequest(HttpMethod.Get, "/v1/public/movies")) {
            assertEquals(HttpStatusCode.OK, response.status())
        }
        with(handleRequest(HttpMethod.Get, "/v1/public/movies/1/heroes")) {
            assertEquals(HttpStatusCode.OK, response.status())
        }
    }
}

