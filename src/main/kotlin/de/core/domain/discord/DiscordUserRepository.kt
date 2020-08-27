package de.core.domain.discord

import io.quarkus.hibernate.orm.panache.kotlin.PanacheRepository
import javax.enterprise.context.ApplicationScoped

@ApplicationScoped
class DiscordUserRepository: PanacheRepository<DiscordUser> {
    fun findByUserId(userId: String) = find("userId", userId).firstResult()
}