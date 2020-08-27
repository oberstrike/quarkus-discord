package de.core.domain.discord

import de.core.domain.verification.VerificationToken
import de.core.domain.verification.VerificationTokenDTO
import de.core.domain.verification.VerificationTokenRepository
import io.quarkus.oidc.runtime.OidcJwtCallerPrincipal
import io.quarkus.security.Authenticated
import io.quarkus.security.identity.SecurityIdentity
import org.eclipse.microprofile.openapi.annotations.tags.Tag
import org.eclipse.microprofile.openapi.annotations.tags.Tags
import java.util.*
import javax.inject.Inject
import javax.transaction.Transactional
import javax.ws.rs.*
import javax.ws.rs.core.MediaType

@Path("/discord")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Tags(
        Tag(name = "DiscordUser", description = "The path to manage the discord user")
)
class DiscordUserResource {

    @Inject
    lateinit var discordUserRepository: DiscordUserRepository

    @Inject
    lateinit var securityIdentity: SecurityIdentity

    @Inject
    lateinit var verificationTokenRepository: VerificationTokenRepository

    @GET
    @Authenticated
    @Transactional
    fun addDiscordUserResource(): VerificationTokenDTO {
        val userId = (securityIdentity.principal as OidcJwtCallerPrincipal).claims.getClaimValueAsString("sub")

        val discordUser = with(discordUserRepository.findByUserId(userId)) {
            if (this == null) {
                val discordUser = DiscordUser()
                discordUser.userId = userId
                discordUserRepository.persist(discordUser)
                discordUser
            } else
                this
        }
        if (discordUser.verified)
            throw BadRequestException()


        val verificationToken = with(verificationTokenRepository.findByUserId(userId))
        {
            if (this == null) {
                val verificationToken = VerificationToken()
                verificationToken.discordUser = discordUser
                verificationToken.value = UUID.randomUUID().toString()
                verificationTokenRepository.persist(verificationToken)
                verificationToken
            } else {
                this
            }
        }
        return VerificationTokenDTO(verificationToken.value)
    }


    @Path("/token")
    @GET
    @Authenticated
    @Transactional
    fun confirmUserWithToken(@QueryParam("token") token: String?) {
        if (token == null)
            throw NotFoundException()

        val verificationToken = verificationTokenRepository.findByValue(token) ?: throw  NotFoundException()
        val user = verificationToken.discordUser
        user.verified = true
        verificationTokenRepository.delete(verificationToken)
    }
}
