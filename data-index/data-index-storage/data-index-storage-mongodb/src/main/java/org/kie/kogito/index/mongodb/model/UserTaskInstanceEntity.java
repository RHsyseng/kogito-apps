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

package org.kie.kogito.index.mongodb.model;

import java.util.Objects;
import java.util.Set;

import io.quarkus.mongodb.panache.MongoEntity;
import io.quarkus.mongodb.panache.PanacheMongoEntityBase;
import org.bson.Document;
import org.bson.codecs.pojo.annotations.BsonId;

import static org.kie.kogito.index.Constants.USER_TASK_INSTANCES_STORAGE;

@MongoEntity(collection = USER_TASK_INSTANCES_STORAGE)
public class UserTaskInstanceEntity extends PanacheMongoEntityBase {

    @BsonId
    public String id;

    public String description;

    public String name;

    public String priority;

    public String processInstanceId;

    public String state;

    public String actualOwner;

    public Set<String> adminGroups;

    public Set<String> adminUsers;

    public Long completed;

    public Long started;

    public Set<String> excludedUsers;

    public Set<String> potentialGroups;

    public Set<String> potentialUsers;

    public String referenceName;

    public Long lastUpdate;

    public String processId;

    public String rootProcessId;

    public String rootProcessInstanceId;

    public Document inputs;

    public Document outputs;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        UserTaskInstanceEntity that = (UserTaskInstanceEntity) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(description, that.description) &&
                Objects.equals(name, that.name) &&
                Objects.equals(priority, that.priority) &&
                Objects.equals(processInstanceId, that.processInstanceId) &&
                Objects.equals(state, that.state) &&
                Objects.equals(actualOwner, that.actualOwner) &&
                Objects.equals(adminGroups, that.adminGroups) &&
                Objects.equals(adminUsers, that.adminUsers) &&
                Objects.equals(completed, that.completed) &&
                Objects.equals(started, that.started) &&
                Objects.equals(excludedUsers, that.excludedUsers) &&
                Objects.equals(potentialGroups, that.potentialGroups) &&
                Objects.equals(potentialUsers, that.potentialUsers) &&
                Objects.equals(referenceName, that.referenceName) &&
                Objects.equals(lastUpdate, that.lastUpdate) &&
                Objects.equals(processId, that.processId) &&
                Objects.equals(rootProcessId, that.rootProcessId) &&
                Objects.equals(rootProcessInstanceId, that.rootProcessInstanceId) &&
                Objects.equals(inputs, that.inputs) &&
                Objects.equals(outputs, that.outputs);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, description, name, priority, processInstanceId, state, actualOwner, adminGroups, adminUsers, completed, started, excludedUsers, potentialGroups, potentialUsers, referenceName, lastUpdate, processId, rootProcessId, rootProcessInstanceId, inputs, outputs);
    }
}
