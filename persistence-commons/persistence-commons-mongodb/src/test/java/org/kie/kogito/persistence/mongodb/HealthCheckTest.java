/*
 * Copyright 2020 Red Hat, Inc. and/or its affiliates.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.kie.kogito.persistence.mongodb;

import java.util.HashMap;
import java.util.Map;

import io.quarkus.test.common.QuarkusTestResource;
import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasItem;

@QuarkusTest
@QuarkusTestResource(MongoDBServerTestResource.class)
public class HealthCheckTest {

    @Test
    void testHealthCheck() {
        Map<String, Object> expectedBodyChecksData = new HashMap<>();
        expectedBodyChecksData.put("default", "admin, config, local");
        Map<String, Object> expectedBodyChecks = new HashMap<>();
        expectedBodyChecks.put("name", "MongoDB connection health check");
        expectedBodyChecks.put("status", "UP");
        expectedBodyChecks.put("data", expectedBodyChecksData);

        given()
                .when().get("/health/ready")
                .then()
                .statusCode(200)
                .body("status", equalTo("UP"))
                .body("checks", hasItem(expectedBodyChecks));
    }
}