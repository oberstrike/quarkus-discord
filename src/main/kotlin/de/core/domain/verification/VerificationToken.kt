package de.core.domain.verification

import de.core.domain.discord.DiscordUser
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id
import javax.persistence.OneToOne

@Entity
class VerificationToken {
    @Id
    @GeneratedValue
    var id: Long? = null

    lateinit var value: String

    @OneToOne
    lateinit var discordUser: DiscordUser

}

data class VerificationTokenDTO(
        var value: String = ""
)