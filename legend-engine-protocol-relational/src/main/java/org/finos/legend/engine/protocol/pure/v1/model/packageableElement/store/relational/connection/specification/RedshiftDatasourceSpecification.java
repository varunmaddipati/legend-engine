package org.finos.legend.engine.protocol.pure.v1.model.packageableElement.store.relational.connection.specification;

public class RedshiftDatasourceSpecification extends DatasourceSpecification
{
    public String clusterName;
    public String region;
    public String databaseName;
    public String profile;

//    public Boolean quotedIdentifiersIgnoreCase;

    @Override
    public <T> T accept(DatasourceSpecificationVisitor<T> datasourceSpecificationVisitor)
    {
        return datasourceSpecificationVisitor.visit(this);
    }
}
