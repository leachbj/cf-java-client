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

package org.cloudfoundry.client.v2.spaces;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.cloudfoundry.Nullable;
import org.cloudfoundry.client.v2.InFilterParameter;
import org.cloudfoundry.client.v2.PaginatedRequest;
import org.immutables.value.Value;

import java.util.List;

/**
 * The request payload for the List all Developers for the Space operation
 */
@Value.Immutable
abstract class _ListSpaceDevelopersRequest extends PaginatedRequest {

    /**
     * The audited organization ids
     */
    @InFilterParameter("audited_organization_guid")
    @Nullable
    abstract List<String> getAuditedOrganizationIds();

    /**
     * The audited space ids
     */
    @InFilterParameter("audited_space_guid")
    @Nullable
    abstract List<String> getAuditedSpaceIds();

    /**
     * The billing managed organization ids
     */
    @InFilterParameter("billing_managed_organization_guid")
    @Nullable
    abstract List<String> getBillingManagedOrganizationIds();

    /**
     * The managed organization ids
     */
    @InFilterParameter("managed_organization_guid")
    @Nullable
    abstract List<String> getManagedOrganizationIds();

    /**
     * The managed space ids
     */
    @InFilterParameter("managed_space_guid")
    @Nullable
    abstract List<String> getManagedSpaceIds();

    /**
     * The organization ids
     */
    @InFilterParameter("organization_guid")
    @Nullable
    abstract List<String> getOrganizationIds();

    /**
     * The space id
     */
    @JsonIgnore
    abstract String getSpaceId();

}
