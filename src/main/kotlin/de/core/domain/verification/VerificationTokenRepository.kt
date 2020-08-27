package de.core.domain.verification

import io.quarkus.hibernate.orm.panache.kotlin.PanacheRepository
import io.quarkus.panache.common.Parameters
import javax.enterprise.context.ApplicationScoped

@ApplicationScoped
class VerificationTokenRepository : PanacheRepository<VerificationToken> {
    fun findByUserId(userId: String) = find("discordUser.userId = :userId", Parameters.with("userId", userId)).firstResult()

    fun findByValue(value: String) = find("value", value).firstResult()
}