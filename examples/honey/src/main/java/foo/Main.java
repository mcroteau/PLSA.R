package foo;

import net.plsar.*;
import net.plsar.drivers.Drivers;
import net.plsar.environments.Environments;
import net.plsar.schemes.RenderingScheme;

public class Main {

    public static void main(String[] args){
        PLSA.R plsar = new PLSA.R(9001);
        plsar.setNumberOfPartitions(7);
        plsar.setNumberOfRequestExecutors(10);

        PersistenceConfig persistenceConfig = new PersistenceConfig();
        persistenceConfig.setDriver(Drivers.H2);
        persistenceConfig.setUrl("jdbc:h2:~/PLSAR_DB");
        persistenceConfig.setUser("sa");
        persistenceConfig.setPassword("");

        SchemaConfig schemaConfig = new SchemaConfig();
        schemaConfig.setSchema("schema.sql");
        schemaConfig.setEnvironment(Environments.DEVELOPMENT);

        plsar.setPersistenceConfig(persistenceConfig);
        plsar.setSchemaConfig(schemaConfig);

        ViewConfig viewConfig = new ViewConfig();
        viewConfig.setViewsPath("");
        viewConfig.setResourcesPath("assets");
        viewConfig.setViewExtension(".jsp");
        plsar.setViewConfig(viewConfig);

        plsar.setPageRenderingScheme(RenderingScheme.RELOAD_EACH_REQUEST);
        plsar.start();
    }
}