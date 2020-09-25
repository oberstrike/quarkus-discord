package de.core.discord

import com.maju.container.IContainerCreator
import com.maju.container.keycloak.KeycloakContainerCreateHandler
import com.maju.container.keycloak.KeycloakDefaultContainerCreatorImpl
import com.maju.container.postgres.PostgresContainerCreatorImpl
import com.maju.container.postgres.PostgresOnContainerCreateHandler
import com.maju.quarkus.AbstractDockerTestResource
import de.core.domain.client.UserAuthClient


class DockerTestResource : AbstractDockerTestResource() {

    override val containerCreators: List<IContainerCreator<*>> = listOf(
        PostgresContainerCreatorImpl(
            PostgresOnContainerCreateHandler.default(
                dbName = "discord"
            )
        ),
        KeycloakDefaultContainerCreatorImpl(
            KeycloakContainerCreateHandler.default(
                pPort = 8888,
                pRealmName = "quarkus",
                pRealmImportFile = "/imports/realm-export.json",
                pListOfClass = listOf(UserAuthClient::class.java)
            )
        )
    )
}
