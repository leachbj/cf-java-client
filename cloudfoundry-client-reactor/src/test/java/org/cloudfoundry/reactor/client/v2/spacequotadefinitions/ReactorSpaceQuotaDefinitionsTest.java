/*
 * Copyright 2013-2016 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.cloudfoundry.reactor.client.v2.spacequotadefinitions;

import org.cloudfoundry.client.v2.Metadata;
import org.cloudfoundry.client.v2.jobs.JobEntity;
import org.cloudfoundry.client.v2.spacequotadefinitions.AssociateSpaceQuotaDefinitionRequest;
import org.cloudfoundry.client.v2.spacequotadefinitions.AssociateSpaceQuotaDefinitionResponse;
import org.cloudfoundry.client.v2.spacequotadefinitions.CreateSpaceQuotaDefinitionRequest;
import org.cloudfoundry.client.v2.spacequotadefinitions.CreateSpaceQuotaDefinitionResponse;
import org.cloudfoundry.client.v2.spacequotadefinitions.DeleteSpaceQuotaDefinitionRequest;
import org.cloudfoundry.client.v2.spacequotadefinitions.DeleteSpaceQuotaDefinitionResponse;
import org.cloudfoundry.client.v2.spacequotadefinitions.GetSpaceQuotaDefinitionRequest;
import org.cloudfoundry.client.v2.spacequotadefinitions.GetSpaceQuotaDefinitionResponse;
import org.cloudfoundry.client.v2.spacequotadefinitions.ListSpaceQuotaDefinitionsRequest;
import org.cloudfoundry.client.v2.spacequotadefinitions.ListSpaceQuotaDefinitionsResponse;
import org.cloudfoundry.client.v2.spacequotadefinitions.RemoveSpaceQuotaDefinitionRequest;
import org.cloudfoundry.client.v2.spacequotadefinitions.SpaceQuotaDefinitionEntity;
import org.cloudfoundry.client.v2.spacequotadefinitions.SpaceQuotaDefinitionResource;
import org.cloudfoundry.reactor.InteractionContext;
import org.cloudfoundry.reactor.TestRequest;
import org.cloudfoundry.reactor.TestResponse;
import org.cloudfoundry.reactor.client.AbstractClientApiTest;
import org.junit.Test;
import reactor.test.StepVerifier;

import java.time.Duration;

import static io.netty.handler.codec.http.HttpMethod.DELETE;
import static io.netty.handler.codec.http.HttpMethod.GET;
import static io.netty.handler.codec.http.HttpMethod.POST;
import static io.netty.handler.codec.http.HttpMethod.PUT;
import static io.netty.handler.codec.http.HttpResponseStatus.ACCEPTED;
import static io.netty.handler.codec.http.HttpResponseStatus.CREATED;
import static io.netty.handler.codec.http.HttpResponseStatus.NO_CONTENT;
import static io.netty.handler.codec.http.HttpResponseStatus.OK;

public final class ReactorSpaceQuotaDefinitionsTest extends AbstractClientApiTest {

    private final ReactorSpaceQuotaDefinitions spaceQuotaDefinitions = new ReactorSpaceQuotaDefinitions(CONNECTION_CONTEXT, this.root, TOKEN_PROVIDER);

    @Test
    public void associateSpace() {
        mockRequest(InteractionContext.builder()
            .request(TestRequest.builder()
                .method(PUT).path("/v2/space_quota_definitions/test-space-quota-definition-id/spaces/test-space-id")
                .build())
            .response(TestResponse.builder()
                .status(OK)
                .payload("fixtures/client/v2/space_quota_definitions/PUT_{id}_spaces_{id}_response.json")
                .build())
            .build());

        this.spaceQuotaDefinitions
            .associateSpace(AssociateSpaceQuotaDefinitionRequest.builder()
                .spaceId("test-space-id")
                .spaceQuotaDefinitionId("test-space-quota-definition-id")
                .build())
            .as(StepVerifier::create)
            .expectNext(AssociateSpaceQuotaDefinitionResponse.builder()
                .metadata(Metadata.builder()
                    .id("ea82f16c-c21a-4a8a-947a-f7606e7f63fa")
                    .url("/v2/space_quota_definitions/ea82f16c-c21a-4a8a-947a-f7606e7f63fa")
                    .createdAt("2015-11-30T23:38:46Z")
                    .build())
                .entity(SpaceQuotaDefinitionEntity.builder()
                    .name("name-1887")
                    .organizationId("e188543a-cb71-4786-8703-9addbebc5bbf")
                    .nonBasicServicesAllowed(true)
                    .totalServices(60)
                    .totalRoutes(1000)
                    .memoryLimit(20480)
                    .instanceMemoryLimit(-1)
                    .applicationInstanceLimit(-1)
                    .organizationUrl("/v2/organizations/e188543a-cb71-4786-8703-9addbebc5bbf")
                    .spacesUrl("/v2/space_quota_definitions/ea82f16c-c21a-4a8a-947a-f7606e7f63fa/spaces")
                    .build())
                .build())
            .expectComplete()
            .verify(Duration.ofSeconds(5));
    }

    @Test
    public void create() {
        mockRequest(InteractionContext.builder()
            .request(TestRequest.builder()
                .method(POST).path("/v2/space_quota_definitions")
                .payload("fixtures/client/v2/space_quota_definitions/POST_request.json")
                .build())
            .response(TestResponse.builder()
                .status(CREATED)
                .payload("fixtures/client/v2/space_quota_definitions/POST_response.json")
                .build())
            .build());

        this.spaceQuotaDefinitions
            .create(CreateSpaceQuotaDefinitionRequest.builder()
                .name("gold_quota")
                .nonBasicServicesAllowed(true)
                .totalServices(-1)
                .totalRoutes(10)
                .memoryLimit(5120)
                .organizationId("c9b4ac17-ab4b-4368-b3e2-5cbf09b17a24")
                .totalReservedRoutePorts(5)
                .build())
            .as(StepVerifier::create)
            .expectNext(CreateSpaceQuotaDefinitionResponse.builder()
                .metadata(Metadata.builder()
                    .id("17f055b8-b4c8-47cf-8737-0220d5706b4a")
                    .url("/v2/space_quota_definitions/17f055b8-b4c8-47cf-8737-0220d5706b4a")
                    .createdAt("2016-06-08T16:41:29Z")
                    .build())
                .entity(SpaceQuotaDefinitionEntity.builder()
                    .name("gold_quota")
                    .organizationId("c9b4ac17-ab4b-4368-b3e2-5cbf09b17a24")
                    .nonBasicServicesAllowed(true)
                    .totalServices(-1)
                    .totalRoutes(10)
                    .memoryLimit(5120)
                    .instanceMemoryLimit(-1)
                    .applicationInstanceLimit(-1)
                    .applicationTaskLimit(5)
                    .totalServiceKeys(-1)
                    .totalReservedRoutePorts(5)
                    .organizationUrl("/v2/organizations/c9b4ac17-ab4b-4368-b3e2-5cbf09b17a24")
                    .spacesUrl("/v2/space_quota_definitions/17f055b8-b4c8-47cf-8737-0220d5706b4a/spaces")
                    .build())
                .build())
            .expectComplete()
            .verify(Duration.ofSeconds(5));
    }

    @Test
    public void delete() {
        mockRequest(InteractionContext.builder()
            .request(TestRequest.builder()
                .method(DELETE).path("/v2/space_quota_definitions/test-space-quota-definition-id")
                .build())
            .response(TestResponse.builder()
                .status(NO_CONTENT)
                .build())
            .build());

        this.spaceQuotaDefinitions
            .delete(DeleteSpaceQuotaDefinitionRequest.builder()
                .spaceQuotaDefinitionId("test-space-quota-definition-id")
                .build())
            .as(StepVerifier::create)
            .expectComplete()
            .verify(Duration.ofSeconds(5));
    }

    @Test
    public void deleteAsync() {
        mockRequest(InteractionContext.builder()
            .request(TestRequest.builder()
                .method(DELETE).path("/v2/space_quota_definitions/test-space-quota-definition-id?async=true")
                .build())
            .response(TestResponse.builder()
                .status(ACCEPTED)
                .payload("fixtures/client/v2/space_quota_definitions/DELETE_{id}_async_response.json")
                .build())
            .build());

        this.spaceQuotaDefinitions
            .delete(DeleteSpaceQuotaDefinitionRequest.builder()
                .spaceQuotaDefinitionId("test-space-quota-definition-id")
                .async(true)
                .build())
            .as(StepVerifier::create)
            .expectNext(DeleteSpaceQuotaDefinitionResponse.builder()
                .metadata(Metadata.builder()
                    .id("2d9707ba-6f0b-4aef-a3de-fe9bdcf0c9d1")
                    .url("/v2/jobs/2d9707ba-6f0b-4aef-a3de-fe9bdcf0c9d1")
                    .createdAt("2016-02-02T17:16:31Z")
                    .build())
                .entity(JobEntity.builder()
                    .id("2d9707ba-6f0b-4aef-a3de-fe9bdcf0c9d1")
                    .status("queued")
                    .build())
                .build())
            .expectComplete()
            .verify(Duration.ofSeconds(5));
    }

    @Test
    public void getSpaceQuotaDefinition() {
        mockRequest(InteractionContext.builder()
            .request(TestRequest.builder()
                .method(GET).path("/v2/space_quota_definitions/test-space-quota-definition-id")
                .build())
            .response(TestResponse.builder()
                .status(OK)
                .payload("fixtures/client/v2/space_quota_definitions/GET_{id}_response.json")
                .build())
            .build());

        this.spaceQuotaDefinitions
            .get(GetSpaceQuotaDefinitionRequest.builder()
                .spaceQuotaDefinitionId("test-space-quota-definition-id")
                .build())
            .as(StepVerifier::create)
            .expectNext(GetSpaceQuotaDefinitionResponse.builder()
                .metadata(Metadata.builder()
                    .id("4b8e7d14-71bd-4abb-b474-183375c75c84")
                    .url("/v2/space_quota_definitions/4b8e7d14-71bd-4abb-b474-183375c75c84")
                    .createdAt("2015-11-30T23:38:46Z")
                    .build())
                .entity(SpaceQuotaDefinitionEntity.builder()
                    .name("name-1892")
                    .organizationId("0dbbac8c-16ac-4ba5-8f59-3d3a79874f5d")
                    .nonBasicServicesAllowed(true)
                    .totalServices(60)
                    .totalRoutes(1000)
                    .memoryLimit(20480)
                    .instanceMemoryLimit(-1)
                    .applicationInstanceLimit(-1)
                    .organizationUrl("/v2/organizations/0dbbac8c-16ac-4ba5-8f59-3d3a79874f5d")
                    .spacesUrl("/v2/space_quota_definitions/4b8e7d14-71bd-4abb-b474-183375c75c84/spaces")
                    .build())
                .build())
            .expectComplete()
            .verify(Duration.ofSeconds(5));
    }

    @Test
    public void list() {
        mockRequest(InteractionContext.builder()
            .request(TestRequest.builder()
                .method(GET).path("/v2/space_quota_definitions?page=-1")
                .build())
            .response(TestResponse.builder()
                .status(OK)
                .payload("fixtures/client/v2/space_quota_definitions/GET_response.json")
                .build())
            .build());

        this.spaceQuotaDefinitions
            .list(ListSpaceQuotaDefinitionsRequest.builder()
                .page(-1)
                .build())
            .as(StepVerifier::create)
            .expectNext(ListSpaceQuotaDefinitionsResponse.builder()
                .totalResults(1)
                .totalPages(1)
                .resource(SpaceQuotaDefinitionResource.builder()
                    .metadata(Metadata.builder()
                        .id("be2d5c01-3413-43db-bea2-49b0b60ec74d")
                        .url("/v2/space_quota_definitions/be2d5c01-3413-43db-bea2-49b0b60ec74d")
                        .createdAt("2015-07-27T22:43:32Z")
                        .build())
                    .entity(SpaceQuotaDefinitionEntity.builder()
                        .name("name-2236")
                        .organizationId("a81d5218-b473-474e-9afb-3223a8b2ae9f")
                        .nonBasicServicesAllowed(true)
                        .totalServices(60)
                        .totalRoutes(1000)
                        .memoryLimit(20480)
                        .instanceMemoryLimit(-1)
                        .organizationUrl("/v2/organizations/a81d5218-b473-474e-9afb-3223a8b2ae9f")
                        .spacesUrl
                            ("/v2/space_quota_definitions/be2d5c01-3413-43db-bea2-49b0b60ec74d/spaces")
                        .build())
                    .build())
                .build())
            .expectComplete()
            .verify(Duration.ofSeconds(5));
    }

    @Test
    public void removeSpace() {
        mockRequest(InteractionContext.builder()
            .request(TestRequest.builder()
                .method(DELETE).path("/v2/space_quota_definitions/test-space-quota-definition-id/spaces/test-space-id")
                .build())
            .response(TestResponse.builder()
                .status(NO_CONTENT)
                .build())
            .build());

        this.spaceQuotaDefinitions
            .removeSpace(RemoveSpaceQuotaDefinitionRequest.builder()
                .spaceId("test-space-id")
                .spaceQuotaDefinitionId("test-space-quota-definition-id")
                .build())
            .as(StepVerifier::create)
            .expectComplete()
            .verify(Duration.ofSeconds(5));
    }

}
