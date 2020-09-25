package de.core.discord

import io.quarkus.test.common.QuarkusTestResource
import io.quarkus.test.junit.QuarkusTest
import org.junit.jupiter.api.Test

@QuarkusTest
@QuarkusTestResource(DockerTestResource::class)
class DockerTest {

    @Test
    fun test(){
        assert(true)
    }

}

