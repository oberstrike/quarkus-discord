package de.core.discord

import de.core.domain.client.JWTToken
import de.core.domain.verification.VerificationTokenDTO
import io.quarkus.test.junit.QuarkusTest
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import javax.ws.rs.core.Form

@QuarkusTest
class DiscordUserTest : AbstractRestTest() {

    private val addDiscordUserPath = "/discord"
    private val verifyToken = "/discord/token"


    @Test
    fun addAndVerifyANewDiscordUserTest() = withLoggedIn {
        val response = sendGet(addDiscordUserPath, bearerToken = it.accessToken)
        assert(response.statusCode == 200)
        val verificationTokenDTO = response.body.`as`<VerificationTokenDTO>(VerificationTokenDTO::class.java) as VerificationTokenDTO
        val token = verificationTokenDTO.value

        val secondResponse = sendGet(verifyToken, params = mapOf("token" to token), bearerToken = it.accessToken)
        Assertions.assertEquals(204, secondResponse.statusCode)


    }


}

fun getLogInForm(username: String, password: String): Form {
    return Form()
            .param("grant_type", "password")
            .param("username", username)
            .param("password", password)
            .param("scope", "profile")
            .param("client_id", "backend-service")
            .param("client_secret", "secret")
}

fun AbstractRestTest.withLoggedIn(
        loginForm: Form = getLogInForm("alice", "alice"),
        block: (jwtToken: JWTToken) -> Unit
) {
    val result = userAuthClient.login(
            loginForm
    )

    block.invoke(
            JWTToken(
                    result["access_token"] as String,
                    result["refresh_token"] as String
            )
    )
}