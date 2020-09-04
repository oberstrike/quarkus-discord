package de.core.discord

import de.core.domain.client.UserAuthClient
import io.restassured.http.ContentType
import io.restassured.module.kotlin.extensions.Given
import io.restassured.module.kotlin.extensions.When
import io.restassured.response.Response
import org.eclipse.microprofile.config.inject.ConfigProperty
import org.eclipse.microprofile.rest.client.inject.RestClient
import javax.inject.Inject
import javax.json.bind.Jsonb
import javax.json.bind.JsonbBuilder


//@TestInstance(TestInstance.Lifecycle.PER_CLASS)
open class AbstractRestTest {

    @Inject
    @RestClient
    lateinit var userAuthClient: UserAuthClient

    @ConfigProperty(name = "quarkus.http.test-port")
    var port: Int = 0

    private var jsonB: Jsonb = JsonbBuilder.create()

    protected fun toJson(obj: Any): String {
        return jsonB.toJson(obj)
    }


    protected fun sendGet(
            path: String,
            auth: Pair<String, String>? = null,
            bearerToken: String? = null,
            params: Map<String, *>? = null
    ): Response {
        return Given {
            if (auth != null)
                auth().preemptive().basic(auth.first, auth.second)
            if (bearerToken != null)
                auth().preemptive().oauth2(bearerToken)
            if (params != null)
                params(params)
            port(port)
            log().all()
        }.When {
            get(path)
        }
    }

    protected fun sendPost(
            path: String,
            body: Any,
            bearerToken: String? = null,
            auth: Pair<String, String>? = null
    ): Response {
        return sendPost(
                path = path,
                body = toJson(body),
                bearerToken = bearerToken,
                auth = auth
        )
    }

    protected fun sendPost(
            path: String,
            body: String,
            bearerToken: String? = null,
            auth: Pair<String, String>? = null
    ): Response {
        return Given {
            if (auth != null)
                auth().preemptive().basic(auth.first, auth.second)
            if (bearerToken != null)
                auth().preemptive().oauth2(bearerToken)
            port(port)
            log().all()
            body(body)
            contentType(ContentType.JSON)
        }.When {
            post(path)
        }
    }

    protected fun sendPatch(
            path: String,
            body: String,
            auth: Pair<String, String>? = null,
            params: Map<String, *>? = null
    ): Response {
        return Given {
            if (auth != null)
                auth().preemptive().basic(auth.first, auth.second)
            port(port)
            if (params != null)
                params(params)
            log().all()
            body(body)
            contentType(ContentType.JSON)
        }.When {
            patch(path)
        }

    }
}

