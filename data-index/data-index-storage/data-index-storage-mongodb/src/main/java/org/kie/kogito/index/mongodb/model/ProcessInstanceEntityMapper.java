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

import java.util.Optional;

import com.fasterxml.jackson.databind.JsonNode;
import io.quarkus.mongodb.panache.runtime.MongoOperations;
import org.kie.kogito.index.model.NodeInstance;
import org.kie.kogito.index.model.ProcessInstance;
import org.kie.kogito.index.model.ProcessInstanceError;
import org.kie.kogito.persistence.mongodb.model.MongoEntityMapper;

import static java.util.stream.Collectors.toList;
import static org.kie.kogito.index.mongodb.model.ModelUtils.documentToJsonNode;
import static org.kie.kogito.index.mongodb.model.ModelUtils.instantToZonedDateTime;
import static org.kie.kogito.index.mongodb.model.ModelUtils.jsonNodeToDocument;
import static org.kie.kogito.index.mongodb.model.ModelUtils.zonedDateTimeToInstant;

public class ProcessInstanceEntityMapper implements MongoEntityMapper<String, ProcessInstance, ProcessInstanceEntity> {

    static final String NODES_ID_ATTRIBUTE = "nodes.id";

    static final String MONGO_NODES_ID_ATTRIBUTE = "nodes." + MongoOperations.ID;

    @Override
    public Class<ProcessInstanceEntity> getEntityClass() {
        return ProcessInstanceEntity.class;
    }

    @Override
    public ProcessInstanceEntity mapToEntity(String key, ProcessInstance instance) {
        if (instance == null) {
            return null;
        }

        ProcessInstanceEntity entity = new ProcessInstanceEntity();
        entity.id = instance.getId();
        entity.processId = instance.getProcessId();
        entity.roles = instance.getRoles();
        entity.variables = jsonNodeToDocument(instance.getVariables());
        entity.endpoint = instance.getEndpoint();
        entity.nodes = Optional.ofNullable(instance.getNodes()).map(nodes -> nodes.stream().map(this::fromNodeInstance).collect(toList())).orElse(null);
        entity.state = instance.getState();
        entity.start = zonedDateTimeToInstant(instance.getStart());
        entity.end = zonedDateTimeToInstant(instance.getEnd());
        entity.rootProcessId = instance.getRootProcessId();
        entity.rootProcessInstanceId = instance.getRootProcessInstanceId();
        entity.parentProcessInstanceId = instance.getParentProcessInstanceId();
        entity.processName = instance.getProcessName();
        entity.error = Optional.ofNullable(instance.getError()).map(this::fromProcessInstanceError).orElse(null);
        entity.addons = instance.getAddons();
        entity.lastUpdate = zonedDateTimeToInstant(instance.getLastUpdate());
        entity.businessKey = instance.getBusinessKey();
        return entity;
    }

    @Override
    public ProcessInstance mapToModel(ProcessInstanceEntity entity) {
        if (entity == null) {
            return null;
        }

        ProcessInstance instance = new ProcessInstance();
        instance.setId(entity.id);
        instance.setProcessId(entity.processId);
        instance.setRoles(entity.roles);
        instance.setVariables(documentToJsonNode(entity.variables, JsonNode.class));
        instance.setEndpoint(entity.endpoint);
        instance.setNodes(Optional.ofNullable(entity.nodes).map(nodes -> nodes.stream().map(this::toNodeInstance).collect(toList())).orElse(null));
        instance.setState(entity.state);
        instance.setStart(instantToZonedDateTime(entity.start));
        instance.setEnd(instantToZonedDateTime(entity.end));
        instance.setRootProcessId(entity.rootProcessId);
        instance.setRootProcessInstanceId(entity.rootProcessInstanceId);
        instance.setParentProcessInstanceId(entity.parentProcessInstanceId);
        instance.setProcessName(entity.processName);
        instance.setError(Optional.ofNullable(entity.error).map(this::toProcessInstanceError).orElse(null));
        instance.setAddons(entity.addons);
        instance.setLastUpdate(instantToZonedDateTime(entity.lastUpdate));
        instance.setBusinessKey(entity.businessKey);
        return instance;
    }

    @Override
    public String convertAttribute(String attribute) {
        if (NODES_ID_ATTRIBUTE.equalsIgnoreCase(attribute)) {
            return MONGO_NODES_ID_ATTRIBUTE;
        }
        if (MongoEntityMapper.ID.equals(attribute)) {
            return MongoOperations.ID;
        }
        return attribute;
    }

    NodeInstance toNodeInstance(ProcessInstanceEntity.NodeInstanceEntity entity) {
        if (entity == null) {
            return null;
        }

        NodeInstance instance = new NodeInstance();
        instance.setId(entity.id);
        instance.setName(entity.name);
        instance.setNodeId(entity.nodeId);
        instance.setType(entity.type);
        instance.setEnter(instantToZonedDateTime(entity.enter));
        instance.setExit(instantToZonedDateTime(entity.exit));
        instance.setDefinitionId(entity.definitionId);
        return instance;
    }

    ProcessInstanceEntity.NodeInstanceEntity fromNodeInstance(NodeInstance instance) {
        if (instance == null) {
            return null;
        }

        ProcessInstanceEntity.NodeInstanceEntity entity = new ProcessInstanceEntity.NodeInstanceEntity();
        entity.id = instance.getId();
        entity.name = instance.getName();
        entity.nodeId = instance.getNodeId();
        entity.type = instance.getType();
        entity.enter = zonedDateTimeToInstant(instance.getEnter());
        entity.exit = zonedDateTimeToInstant(instance.getExit());
        entity.definitionId = instance.getDefinitionId();
        return entity;
    }

    ProcessInstanceError toProcessInstanceError(ProcessInstanceEntity.ProcessInstanceErrorEntity entity) {
        if (entity == null) {
            return null;
        }

        ProcessInstanceError error = new ProcessInstanceError();
        error.setNodeDefinitionId(entity.nodeDefinitionId);
        error.setMessage(entity.message);
        return error;
    }

    ProcessInstanceEntity.ProcessInstanceErrorEntity fromProcessInstanceError(ProcessInstanceError error) {
        if (error == null) {
            return null;
        }

        ProcessInstanceEntity.ProcessInstanceErrorEntity entity = new ProcessInstanceEntity.ProcessInstanceErrorEntity();
        entity.nodeDefinitionId = error.getNodeDefinitionId();
        entity.message = error.getMessage();
        return entity;
    }
}
