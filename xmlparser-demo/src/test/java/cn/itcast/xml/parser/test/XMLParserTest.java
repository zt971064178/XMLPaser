package cn.itcast.xml.parser.test;

import java.util.List;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.XMLConfiguration;
import org.apache.commons.configuration.tree.xpath.XPathExpressionEngine;
import org.junit.Test;

public class XMLParserTest {

	@Test
	public void testSimpleXmlParser() throws ConfigurationException {
		XMLConfiguration configuration = new XMLConfiguration(this.getClass().getClassLoader().getResource("simple.xml")) ;
		System.out.println(configuration.getString("database.url"));
		System.out.println(configuration.getInt("database.port"));
		System.out.println(configuration.getString("database.login"));
	}
	
	@Test
	public void testMultiXmlParser() throws ConfigurationException {
		XMLConfiguration configuration = new XMLConfiguration(this.getClass().getClassLoader().getResource("multi.xml")) ;
		// configuration.setDelimiterParsingDisabled(true);
		System.out.println(configuration.getString("databases.database(0).url"));
		System.out.println(configuration.getList("databases.database(1).password"));
		
		System.out.println("===========================");
		
		List<Object> list = configuration.getList("person.address") ;
		System.out.println(list);
		System.out.println(configuration.getList("person.info[@count]"));
	}
	
	@Test
	public void testXPath() throws ConfigurationException {
		XMLConfiguration config = new XMLConfiguration(this.getClass().getClassLoader().getResource("multi.xml"));
		config.setExpressionEngine(new XPathExpressionEngine());
		
		// 127.0.0.1
		String dev =  config.getString("databases/database[name = 'dev']/url");       
		 
		// 192.23.44.100
		String production = config.getString("databases/database[name = 'production']/url");
		System.out.println(dev);
		System.out.println(production);
	}
	
	@Test
	public void testOther() throws ConfigurationException {
		XMLConfiguration config = new XMLConfiguration(this.getClass().getClassLoader().getResource("multi.xml"));
		int size = config.getMaxIndex("databases.database") ;
		for(int i = 0; i <= size; i++) {
			int length = config.getMaxIndex("databases.database("+i+").time") ;
			for(int j = 0; j <= length; j++) {
				System.out.println(config.getString("databases.database("+i+").time("+j+").st_date"));
				System.out.println(config.getString("databases.database("+i+").time("+j+").ed_date"));
			}
		}
	}
}
