package org.finos.legend.engine.language.pure.compiler.test;

import org.eclipse.collections.api.tuple.Pair;
import org.finos.legend.engine.language.pure.compiler.toPureGraph.PureModel;
import org.finos.legend.engine.protocol.pure.v1.model.SourceInformation;
import org.finos.legend.engine.protocol.pure.v1.model.context.PureModelContextData;
import org.finos.legend.pure.generated.Root_meta_pure_alloy_connections_RelationalDatabaseConnection;
import org.finos.legend.pure.generated.Root_meta_pure_alloy_connections_alloy_authentication_DelegatedKerberosAuthenticationStrategy_Impl;
import org.finos.legend.pure.generated.Root_meta_pure_alloy_connections_alloy_specification_RedshiftDatasourceSpecification_Impl;
import org.finos.legend.pure.generated.Root_meta_pure_alloy_connections_alloy_authentication_UserPasswordAuthenticationStrategy_Impl;
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
                "    clusterID: 'cluster-id';\n" +
                "    clusterName: 'cluster-name';\n" +
                "    name: 'dev';\n" +
                "    port: 5439;\n" +
                "    region: 'region';\n" +
                "  };\n" +
                "  auth: UserPassword\n" +
                "  {\n" +
                "    userName: 'username';\n" +
                "    passwordVaultReference: 'password';\n" +
                "  };\n" +
                "}\n");
        Root_meta_pure_alloy_connections_RelationalDatabaseConnection connection = (Root_meta_pure_alloy_connections_RelationalDatabaseConnection) compiledGraph.getTwo().getConnection("simple::H2Connection", SourceInformation.getUnknownSourceInformation());

        //dataSourceSpecification
        String clusterID = ((Root_meta_pure_alloy_connections_alloy_specification_RedshiftDatasourceSpecification_Impl) connection._datasourceSpecification())._clusterID();
        String clusterName = ((Root_meta_pure_alloy_connections_alloy_specification_RedshiftDatasourceSpecification_Impl) connection._datasourceSpecification())._clusterName();
        long port = ((Root_meta_pure_alloy_connections_alloy_specification_RedshiftDatasourceSpecification_Impl) connection._datasourceSpecification())._port();
        String databaseName = ((Root_meta_pure_alloy_connections_alloy_specification_RedshiftDatasourceSpecification_Impl) connection._datasourceSpecification())._databaseName();
        String region = ((Root_meta_pure_alloy_connections_alloy_specification_RedshiftDatasourceSpecification_Impl) connection._datasourceSpecification())._region();

        Assert.assertEquals("cluster-id", clusterID);
        Assert.assertEquals("cluster-name", clusterName);
        Assert.assertEquals(5439, port);
        Assert.assertEquals("dev", databaseName);
        Assert.assertEquals("region", region);

        //authenticationStrategy
        String userName = ((Root_meta_pure_alloy_connections_alloy_authentication_UserPasswordAuthenticationStrategy_Impl) connection._authenticationStrategy())._userName();
        String passwordVaultReference = ((Root_meta_pure_alloy_connections_alloy_authentication_UserPasswordAuthenticationStrategy_Impl) connection._authenticationStrategy())._passwordVaultReference();

        Assert.assertEquals("username", userName);
        Assert.assertEquals("password", passwordVaultReference);
    }
}
