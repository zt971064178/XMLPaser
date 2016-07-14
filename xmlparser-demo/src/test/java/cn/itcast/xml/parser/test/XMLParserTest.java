package cn.itcast.xml.parser.test;

import java.util.List;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.XMLConfiguration;
import org.apache.commons.configuration.tree.xpath.XPathExpressionEngine;
import org.junit.Test;
import org.w3c.dom.NodeList;

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
		System.out.println(size);
		for(int i = 0; i <= size; i++) {
			System.out.println(config.getString("databases.database("+i+").name"));
			System.out.println(config.getInt("databases.database("+i+").port"));
			System.out.println(config.getString("databases.database("+i+").password"));
		}
	}
	
	@Test
	public void testOther2() throws ConfigurationException {
		XMLConfiguration config = new XMLConfiguration(this.getClass().getClassLoader().getResource("multi.xml"));
		
		NodeList list = config.getDocument().getElementsByTagName("database") ;
		System.out.println(list.getLength());
		for(int i = 0; i < list.getLength(); i++) {
			System.out.println(config.getString("databases.database("+i+").name"));
			System.out.println(config.getInt("databases.database("+i+").port"));
			System.out.println(config.getString("databases.database("+i+").password"));
		}
	}
}
