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

package org.finos.legend.engine.plan.execution.stores.relational.connection.authentication.strategy.keys;

import java.util.Objects;

public class RedshiftPublicAuthenticationStrategyKey implements AuthenticationStrategyKey
{
    private final String userName;

    public RedshiftPublicAuthenticationStrategyKey(String userName)
    {
        this.userName = userName;
    }

    public String getUserName() {
        return this.userName;
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
        RedshiftPublicAuthenticationStrategyKey that = (RedshiftPublicAuthenticationStrategyKey) o;
        return Objects.equals(userName, that.userName);
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(userName);
    }

    @Override
    public String shortId()
    {
        return "type:" + type() +
                "_userName:" + userName;
    }

    @Override
    public String type()
    {
        return "RedshiftPublic";
    }
}
