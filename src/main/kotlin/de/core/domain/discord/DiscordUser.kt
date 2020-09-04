package de.core.domain.discord

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id

@Entity
class DiscordUser {

    @Id
    @GeneratedValue
    var id: Long? = null

    lateinit var userId: String

    var verified: Boolean = false
}

data class DiscordUserDTO(
    val id: Long,
    val userId: String,
    val verified: Boolean
)
