package org.kit.utility;
/*
import java.util.Arrays;

import com.arangodb.ArangoConfigure;
import com.arangodb.ArangoDriver;
import com.arangodb.ArangoException;
import com.arangodb.entity.EdgeDefinitionEntity;
*/
public class ArangoDBUtil {
/*
	private ArangoDriver driver;
	private ArangoConfigure configure;

	private String DATABASE_NAME;
	private String GRAPH_NAME;

	public ArangoDBUtil(String DATABASE_NAME, String GRAPH_NAME) {
		this.DATABASE_NAME = DATABASE_NAME;
		this.GRAPH_NAME = GRAPH_NAME;

		configure = new ArangoConfigure();
		configure.setDefaultDatabase(DATABASE_NAME);
		configure.setUser("root");
		configure.setPassword("");
		configure.init();
		driver = new ArangoDriver(configure);
	}

	public void createCollections() {
		boolean dbCheck = false;
		try {
			dbCheck = driver.getDatabases().getResult().contains(DATABASE_NAME);
		} catch (ArangoException e) {
			e.printStackTrace();
		}

		if (dbCheck) {
			try {
				driver.deleteDatabase(DATABASE_NAME);
			} catch (ArangoException e) {
				e.printStackTrace();
			}
		}

		try {

			driver.createDatabase(DATABASE_NAME, null);
			driver.setDefaultDatabase(DATABASE_NAME);
			driver.createCollection("MomlCollections");
		} catch (ArangoException e) {
			e.printStackTrace();
		}

		try {
			driver.createGraph(GRAPH_NAME, true);
		} catch (ArangoException e) {
			e.printStackTrace();
		}

	}

	public void createNode(Object wrkPojo, String _id, String _collectionName) {

		try {
			driver.createDocument(_collectionName, _id, wrkPojo);
		} catch (ArangoException e) {
			e.printStackTrace();
		}

	}

	public void createEdge(String _collection, String from, String to, String fromCollection, String toCollection) {

		EdgeDefinitionEntity entity = new EdgeDefinitionEntity();
		entity.setCollection(_collection);
		entity.setFrom(Arrays.asList(fromCollection));
		entity.setTo(Arrays.asList(toCollection));
		boolean flag = false;
		try {
			flag = driver.graphGetEdgeCollections(GRAPH_NAME).contains(_collection);
		} catch (ArangoException e1) {
			e1.printStackTrace();
		}

		if (!flag) {
			try {
				driver.graphCreateEdgeDefinition(GRAPH_NAME, entity);
			} catch (ArangoException e) {
				e.printStackTrace();
			}
		}

		try {
			driver.graphCreateEdge(GRAPH_NAME, _collection, fromCollection + "/" + from, toCollection + "/" + to,
					entity, true);
		} catch (ArangoException e) {
			e.printStackTrace();
		}
	}

	public void createCollectionss() {
		boolean dbCheck = false;
		try {
			dbCheck = driver.getDatabases().getResult().contains(DATABASE_NAME);
		} catch (ArangoException e) {
			e.printStackTrace();
		}

		if (dbCheck) {
			try {
				driver.deleteDatabase(DATABASE_NAME);
			} catch (ArangoException e) {
				e.printStackTrace();
			}
		}

		try {

			driver.createDatabase(DATABASE_NAME, null);
			driver.setDefaultDatabase(DATABASE_NAME);
			driver.createCollection("WorkFlow");
			driver.createCollection("Processor");
			driver.createCollection("inport");
			driver.createCollection("outport");
			driver.createCollection("DataLink");
			driver.createCollection("SEQCTRLLINK");
			
			
		} catch (ArangoException e) {
			e.printStackTrace();
		}

		try {
			driver.createGraph(GRAPH_NAME, true);
		} catch (ArangoException e) {
			e.printStackTrace();
		}

	}*/
}
