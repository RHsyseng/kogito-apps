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

package org.kie.kogito.index.mongodb.storage;

import java.util.UUID;

import com.fasterxml.jackson.databind.node.ObjectNode;
import io.quarkus.test.common.QuarkusTestResource;
import io.quarkus.test.junit.QuarkusTest;
import org.bson.Document;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.kie.kogito.index.mongodb.TestUtils;
import org.kie.kogito.index.mongodb.model.DomainEntityMapper;
import org.kie.kogito.persistence.api.Storage;
import org.kie.kogito.persistence.mongodb.MongoDBServerTestResource;
import org.kie.kogito.persistence.mongodb.storage.MongoStorage;

import static org.kie.kogito.persistence.mongodb.storage.StorageUtils.getCollection;

@QuarkusTest
@QuarkusTestResource(MongoDBServerTestResource.class)
public class DomainStorageIT {

    Storage<String, ObjectNode> storage;

    @BeforeEach
    void setUp() {
        this.storage = new MongoStorage<>(getCollection("travels_domain", Document.class), "org.acme.travels.travels.Travels", new DomainEntityMapper());
    }

    @AfterEach
    void tearDown() {
        storage.clear();
    }

    @Test
    void testCache() {
        String processInstanceId = UUID.randomUUID().toString();
        ObjectNode node1 = TestUtils.createDomainData(processInstanceId, "John", "Doe");
        ObjectNode node2 = TestUtils.createDomainData(processInstanceId, "Jane", "Toe");
        StorageTestBase.testStorage(storage, processInstanceId, node1, node2);
    }
}
