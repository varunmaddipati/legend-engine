package org.finos.legend.engine.language.pure.compiler.test;

import org.eclipse.collections.api.tuple.Pair;
import org.finos.legend.engine.language.pure.compiler.toPureGraph.PureModel;
import org.finos.legend.engine.protocol.pure.v1.model.SourceInformation;
import org.finos.legend.engine.protocol.pure.v1.model.context.PureModelContextData;
import org.finos.legend.pure.generated.Root_meta_pure_alloy_connections_RelationalDatabaseConnection;
import org.finos.legend.pure.generated.Root_meta_pure_alloy_connections_alloy_authentication_DelegatedKerberosAuthenticationStrategy_Impl;
import org.finos.legend.pure.generated.Root_meta_pure_alloy_connections_alloy_authentication_RedshiftPublicAuthenticationStrategy_Impl;
import org.finos.legend.pure.generated.Root_meta_pure_alloy_connections_alloy_specification_RedshiftDatasourceSpecification_Impl;
import org.junit.Assert;
import org.junit.Test;

import static org.finos.legend.engine.language.pure.compiler.test.TestCompilationFromGrammar.TestCompilationFromGrammarTestSuite.test;

public class TestRelationalConnectionCompilationRoundtrip
{
    @Test
    public void testConnectionPropertiesPropagatedToCompiledGraph()
    {
        Pair<PureModelContextData, PureModel> compiledGraph = test(TestRelationalCompilationFromGrammar.DB_INC +
                "###Connection\n" +
                "RelationalDatabaseConnection simple::H2Connection\n" +
                "{\n" +
                "  store: model::relational::tests::dbInc;\n" +
                "  type: H2;\n" +
                "  quoteIdentifiers: true;\n" +
                "  specification: LocalH2\n" +
                "  {\n" +
                "  };\n" +
                "  auth: DelegatedKerberos\n" +
                "  {\n" +
                "    serverPrincipal: 'dummyPrincipal';" +
                "  };\n" +
                "}\n");
        Root_meta_pure_alloy_connections_RelationalDatabaseConnection connection = (Root_meta_pure_alloy_connections_RelationalDatabaseConnection) compiledGraph.getTwo().getConnection("simple::H2Connection", SourceInformation.getUnknownSourceInformation());
        String serverPrincipal = ((Root_meta_pure_alloy_connections_alloy_authentication_DelegatedKerberosAuthenticationStrategy_Impl) connection._authenticationStrategy())._serverPrincipal();
        Boolean quoteIdentifiers = connection._quoteIdentifiers();

        Assert.assertTrue(quoteIdentifiers);
        Assert.assertEquals("dummyPrincipal", serverPrincipal);
    }

    @Test
    public void testRedshiftConnectionPropertiesPropagatedToCompiledGraph()
    {
        Pair<PureModelContextData, PureModel> compiledGraph = test(TestRelationalCompilationFromGrammar.DB_INC +
                "###Connection\n" +
                "RelationalDatabaseConnection simple::H2Connection\n" +
                "{\n" +
                "  store: model::relational::tests::dbInc;\n" +
                "  type: Redshift;\n" +
                "  specification: Redshift\n" +
                "  {\n" +
                "    clusterName: 'cluster-name';\n" +
                "    region: 'region';\n" +
                "    name: 'dev';\n" +
                "    profile: 'user1';\n" +
                "  };\n" +
                "  auth: RedshiftPublic\n" +
                "  {\n" +
                "    userName: 'username';\n" +
                "  };\n" +
                "}\n");
        Root_meta_pure_alloy_connections_RelationalDatabaseConnection connection = (Root_meta_pure_alloy_connections_RelationalDatabaseConnection) compiledGraph.getTwo().getConnection("simple::H2Connection", SourceInformation.getUnknownSourceInformation());

        //dataSourceSpecification
        String clusterName = ((Root_meta_pure_alloy_connections_alloy_specification_RedshiftDatasourceSpecification_Impl) connection._datasourceSpecification())._clusterName();
        String region = ((Root_meta_pure_alloy_connections_alloy_specification_RedshiftDatasourceSpecification_Impl) connection._datasourceSpecification())._region();;
        String databaseName = ((Root_meta_pure_alloy_connections_alloy_specification_RedshiftDatasourceSpecification_Impl) connection._datasourceSpecification())._databaseName();
        String profile = ((Root_meta_pure_alloy_connections_alloy_specification_RedshiftDatasourceSpecification_Impl) connection._datasourceSpecification())._profile();

        Assert.assertEquals("cluster-name", clusterName);
        Assert.assertEquals("region", region);
        Assert.assertEquals("dev", databaseName);
        Assert.assertEquals("user1", profile);

        //authenticationStrategy
        String userName = ((Root_meta_pure_alloy_connections_alloy_authentication_RedshiftPublicAuthenticationStrategy_Impl) connection._authenticationStrategy())._userName();

        Assert.assertEquals("username", userName);
    }
}
