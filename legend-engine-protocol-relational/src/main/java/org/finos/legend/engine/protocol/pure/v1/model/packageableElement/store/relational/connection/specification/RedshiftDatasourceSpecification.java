package org.finos.legend.engine.protocol.pure.v1.model.packageableElement.store.relational.connection.specification;

public class RedshiftDatasourceSpecification extends DatasourceSpecification
{
    public String clusterName;
    public String clusterID;
    public String databaseName;
    public int port;
    public String region;

    @Override
    public <T> T accept(DatasourceSpecificationVisitor<T> datasourceSpecificationVisitor)
    {
        return datasourceSpecificationVisitor.visit(this);
    }
}
