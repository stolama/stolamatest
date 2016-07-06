import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

public class Test {
	
	public static void main(String args[]) throws ParserConfigurationException, SAXException, IOException{
		System.out.println("START");
		
		String aaa = "ARAMIS, WINTERPROMOS";
		
		String[] a2 = aaa.split("\\s*,\\s*");
		
		System.out.println("A2: <" + a2[1] + ">");
		
		
		List<String> bbb = Arrays.asList(aaa.trim().split("\\s*,\\s*"));
		
		System.out.println("BBB: " + bbb);
		System.out.println("<"+bbb.get(1)+">");
		
//		String jsonString = "1234567890123456789012345678901234567890";
		String jsonString = "1234567890123456789012345678901";
//		String jsonString = "123";
		
		System.out.println(jsonString);
		if(jsonString.length()>30){
			jsonString = jsonString.substring(0, 30);
		}
		
		
		System.out.println(jsonString);
		
		
		System.out.println("ENDE");
	}
	

	public static void main2(String args[]) throws Exception {
		System.out.println("START");

		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		dbf.setNamespaceAware(true);
		DocumentBuilder builder = dbf.newDocumentBuilder();
		
		
//		final String xmlStr = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n"+
//                "<Emp id=\"1\"><name>Pankaj</name><age>25</age>\n"+
//                "<role>Developer</role><gen>Male</gen></Emp>";
//		Document dap = builder.parse(new InputSource(new StringReader(xmlStr)));
		
		
		Document dap = builder.newDocument();
		Element request = dap.createElement("REQUEST");
		request.setAttribute("xmlns", "http://isep.centertel.pl/schema/dap/WalletStateChangedNotification");
		request.setAttribute("xmlns:xsi", "http://www.w3.org/2001/XMLSchema-instance");
		request.setAttribute("xsi:schemaLocation", "http://isep.centertel.pl/schema/dap/WalletStateChangedNotification http://10.11.131.124:8080/xml/schema/dap/WalletStateChangedNotification.xsd");
		Element body = dap.createElement("BODY");
		Element timestamp = dap.createElement("TIME_STAMP");
		String formattedDateTime = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
		timestamp.setTextContent(formattedDateTime);
		body.appendChild(timestamp);
		Element cli = dap.createElement("CLI");
		cli.setTextContent("123");
		body.appendChild(cli);
		Element newState = dap.createElement("NEW_STATE");
		body.appendChild(newState);
		Element oldState = dap.createElement("OLD_STATE");
		body.appendChild(oldState);
		Element notificationTime = dap.createElement("NOTIFICATION_NAME");
		body.appendChild(notificationTime);
		Element walletName = dap.createElement("WALLET_NAME");
		body.appendChild(walletName);
		Element productType = dap.createElement("PRODUCT_TYPE");
		body.appendChild(productType);
		Element serviceName = dap.createElement("SERVICE_NAME");
		body.appendChild(serviceName);
		request.appendChild(body);
		dap.appendChild(request);
		
		
		System.out.println("URI: " + dap.getDocumentElement().getNamespaceURI());
		String out = prettyPrint(dap);
		
		
		System.out.println("-----------------------------");
		
		Document dap2 = builder.parse(new InputSource(new StringReader(out)));
		
		System.out.println("URI2: " + dap2.getDocumentElement().getNamespaceURI());

		prettyPrint(dap2);
		
		System.out.println("ENDE");

	}
	
	
	
    public static final String prettyPrint(Document xml) throws Exception {
        Transformer tf = TransformerFactory.newInstance().newTransformer();
        tf.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
        tf.setOutputProperty(OutputKeys.INDENT, "yes");
        Writer out = new StringWriter();
        tf.transform(new DOMSource(xml), new StreamResult(out));
        System.out.println(out.toString());
        return out.toString();
    }

	
}
