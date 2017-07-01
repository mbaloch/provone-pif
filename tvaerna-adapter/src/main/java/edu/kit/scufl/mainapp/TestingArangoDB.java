package edu.kit.scufl.mainapp;

import java.util.HashMap;
import java.util.Map;

import com.arangodb.ArangoConfigure;
import com.arangodb.ArangoDriver;
import com.arangodb.ArangoException;
import com.arangodb.DocumentCursor;
import com.arangodb.entity.BaseDocument;
import com.arangodb.entity.DocumentEntity;

public class TestingArangoDB {

	public static void main(String[] args) {

		ArangoConfigure configure = new ArangoConfigure();

		configure.setDefaultDatabase("MetaStoreDB");
		configure.setUser("root");
		configure.setPassword("");
		configure.init();
		ArangoDriver arango = new ArangoDriver(configure);

		StringBuilder allIDDocQuery = new StringBuilder();
		allIDDocQuery
				.append("FOR doc IN MetaStoreRegistry filter doc.mainXmlHandler==@mainXmlHandler && doc.type==@type ");
		Map<String, Object> bindingVals = new HashMap<>();
		bindingVals.put("mainXmlHandler", "vb124");
		bindingVals.put("type", "http://datamanager.kit.edu/masi/chem/1.0");

		/*
		 * allIDDocQuery.append("&& doc.id==@id"); bindingVals.put("id", "DM2");
		 */
		allIDDocQuery.append(" return doc");
		System.out.println("GenerateedQuery:>>" + allIDDocQuery.toString());
		String str = allIDDocQuery.toString();
		try {
			DocumentCursor<BaseDocument> smpl = arango.executeDocumentQuery(str, bindingVals, null, BaseDocument.class);
			DocumentCursor<BaseDocument> smpl2 = arango.executeDocumentQuery(str, bindingVals, null,
					BaseDocument.class);
			int counter = smpl.asEntityList().size();
			System.out.println("count:" + counter);
			for (DocumentEntity<BaseDocument> documentEntity : smpl2) {
				System.out.println("=====" + documentEntity.getEntity().getAttribute("id"));
				System.out.println("Size = " + smpl2.asList().size());
			}

		} catch (ArangoException e) {
			e.printStackTrace();
		}

	}
}
