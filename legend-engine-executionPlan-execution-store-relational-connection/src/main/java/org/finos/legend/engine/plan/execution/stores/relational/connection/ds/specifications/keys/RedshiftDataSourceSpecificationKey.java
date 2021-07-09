// Copyright 2021 Goldman Sachs
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//      http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

package org.finos.legend.engine.plan.execution.stores.relational.connection.ds.specifications.keys;

import org.finos.legend.engine.plan.execution.stores.relational.connection.ds.DataSourceSpecificationKey;

import java.util.Objects;

public class RedshiftDataSourceSpecificationKey implements DataSourceSpecificationKey
{
    private final String clusterName;
    private final String region;
    private final String databaseName;
    private final String profile;


    public RedshiftDataSourceSpecificationKey(String clusterName, String region, String databaseName, String profile)
    {
        this.clusterName = clusterName;
        this.region = region;
        this.databaseName = databaseName;
        this.profile = profile;
    }

    public String getClusterName()
    {
        return clusterName;
    }

    public String getRegion()
    {
        return region;
    }

    public String getDatabaseName()
    {
        return databaseName;
    }

    public String getProfile() {return profile;}

    @Override
    public String toString()
    {
        return "RedshiftDataSourceSpecificationKey{" +
                "clusterName='" + clusterName + '\'' +
                ", region='" + region + '\'' +
                ", databaseName='" + databaseName + '\'' +
                ", profile='" + profile + '\'' +
                '}';
    }

    @Override
    public String shortId()
    {
        return "Redshift_" +
                "clusterName:" + clusterName + "_" +
                "region:" + region + "_" +
                "databaseName:" + databaseName + "_" +
                "profile:" + profile;
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o)
        {
            return true;
        }
        if (o == null || getClass() != o.getClass())
        {
            return false;
        }
        RedshiftDataSourceSpecificationKey that = (RedshiftDataSourceSpecificationKey) o;
        return Objects.equals(clusterName, that.clusterName) &&
                Objects.equals(region, that.region) &&
                Objects.equals(databaseName, that.databaseName) &&
                Objects.equals(profile, that.profile);
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(clusterName, region, databaseName, profile);
    }
}
