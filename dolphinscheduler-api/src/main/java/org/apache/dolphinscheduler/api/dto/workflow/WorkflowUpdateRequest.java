/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.dolphinscheduler.api.dto.workflow;

import org.apache.dolphinscheduler.common.enums.ReleaseState;
import org.apache.dolphinscheduler.common.enums.WorkflowExecutionTypeEnum;
import org.apache.dolphinscheduler.common.utils.JSONUtils;
import org.apache.dolphinscheduler.dao.entity.WorkflowDefinition;

import java.util.Date;

import lombok.Data;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;

/**
 * workflow update request
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class WorkflowUpdateRequest {

    @Schema(example = "workflow's name")
    private String name;

    @Schema(example = "workflow's description")
    private String description;

    @Schema(allowableValues = "ONLINE / OFFLINE", example = "OFFLINE")
    private String releaseState;

    @Schema(example = "[{\"prop\":\"key\",\"value\":\"value\",\"direct\":\"IN\",\"type\":\"VARCHAR\"}]")
    private String globalParams;

    @Schema(example = "2")
    private int warningGroupId;

    @Schema(example = "60")
    private int timeout;

    @Schema(allowableValues = "PARALLEL / SERIAL_WAIT / SERIAL_DISCARD / SERIAL_PRIORITY", example = "PARALLEL", description = "default PARALLEL if not provide.")
    private String executionType;

    @Schema(example = "[{\\\"taskCode\\\":7009653961024,\\\"x\\\":312,\\\"y\\\":196}]")
    private String location;

    /**
     * Merge workflowUpdateRequest information into exists processDefinition object
     *
     * @param workflowDefinition exists processDefinition object
     * @return process definition
     */
    public WorkflowDefinition mergeIntoProcessDefinition(WorkflowDefinition workflowDefinition) {
        WorkflowDefinition workflowDefinitionDeepCopy =
                JSONUtils.parseObject(JSONUtils.toJsonString(workflowDefinition), WorkflowDefinition.class);
        assert workflowDefinitionDeepCopy != null;
        if (this.name != null) {
            workflowDefinitionDeepCopy.setName(this.name);
        }
        if (this.description != null) {
            workflowDefinitionDeepCopy.setDescription(this.description);
        }
        if (this.releaseState != null) {
            workflowDefinitionDeepCopy.setReleaseState(ReleaseState.valueOf(this.releaseState));
        }
        if (this.globalParams != null) {
            workflowDefinitionDeepCopy.setGlobalParams(this.globalParams);
        }
        if (this.warningGroupId != 0) {
            workflowDefinitionDeepCopy.setWarningGroupId(this.warningGroupId);
        }
        if (this.timeout != 0) {
            workflowDefinitionDeepCopy.setTimeout(this.timeout);
        }
        if (this.executionType != null) {
            workflowDefinitionDeepCopy.setExecutionType(WorkflowExecutionTypeEnum.valueOf(this.executionType));
        }
        if (this.location != null) {
            workflowDefinitionDeepCopy.setLocations(this.location);
        }

        workflowDefinitionDeepCopy.setUpdateTime(new Date());
        return workflowDefinitionDeepCopy;
    }
}
