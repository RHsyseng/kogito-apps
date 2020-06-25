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

import javax.inject.Inject;

import io.quarkus.test.common.QuarkusTestResource;
import io.quarkus.test.junit.QuarkusTest;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.kie.kogito.index.model.ProcessInstance;
import org.kie.kogito.index.model.ProcessInstanceState;
import org.kie.kogito.index.mongodb.TestUtils;
import org.kie.kogito.persistence.api.Storage;
import org.kie.kogito.persistence.mongodb.MongoDBServerTestResource;
import org.kie.kogito.persistence.mongodb.storage.MongoDBStorageFactory;

import static org.kie.kogito.index.Constants.PROCESS_INSTANCES_STORAGE;

@QuarkusTest
@QuarkusTestResource(MongoDBServerTestResource.class)
class ProcessInstanceStorageTest {

    @Inject
    MongoDBStorageFactory storageFactory;

    Storage<String, ProcessInstance> storage;

    @BeforeEach
    void setUp() {
        this.storage = (Storage<String, ProcessInstance>) storageFactory.getStorage(PROCESS_INSTANCES_STORAGE);
    }

    @AfterEach
    void tearDown() {
        storage.clear();
    }

    @Test
    void testCache() {
        String processInstanceId = UUID.randomUUID().toString();
        ProcessInstance processInstance1 = TestUtils.createProcessInstance(processInstanceId, RandomStringUtils.randomAlphabetic(5), UUID.randomUUID().toString(), RandomStringUtils.randomAlphabetic(10), ProcessInstanceState.ACTIVE.ordinal());
        ProcessInstance processInstance2 = TestUtils.createProcessInstance(processInstanceId, RandomStringUtils.randomAlphabetic(5), UUID.randomUUID().toString(), RandomStringUtils.randomAlphabetic(10), ProcessInstanceState.COMPLETED.ordinal());
        StorageTestBase.testStorage(storage, processInstanceId, processInstance1, processInstance2);
    }
}