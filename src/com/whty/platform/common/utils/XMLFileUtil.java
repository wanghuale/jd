package com.whty.platform.common.utils;

import java.io.ByteArrayInputStream;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.xml.sax.InputSource;

import com.whty.platform.modules.kamen.entity.GoodsCatalogInfo;

public class XMLFileUtil {

	private static Logger logger = LogManager.getLogger(XMLFileUtil.class);

	public static Document LoadXmlFile(String filePath) {
		SAXReader reader = new SAXReader();
		Document doc = null;
		try {
			String urlString = null;
			if (filePath.startsWith("/")) {
				urlString = "file://" + filePath;
			} else {
				urlString = "file:///" + filePath;
			}
			logger.debug("XML File's URL :" + urlString);
			doc = reader.read(new URL(urlString));
		} catch (Exception ex) {
			logger.info("Can not load " + filePath);
			logger.debug(ex.getMessage(), ex);
		}
		return doc;
	}

	public static Document LoadXmlString(String xml) {
		Document doc = null;
		try {
			SAXReader reader = new SAXReader();
			doc = reader.read(new InputSource(new ByteArrayInputStream(xml.getBytes("UTF-8"))));
		} catch (Exception ex) {
			logger.info("Can not load " + xml);
			logger.debug(ex.getMessage(), ex);
		}
		return doc;
	}

	@SuppressWarnings("rawtypes")
	public static String getAttributeValue(Element element, String attributeName) {
		String attributeValue = null;
		for (Iterator i = element.attributeIterator(); i.hasNext();) {
			Attribute attribute = (Attribute) i.next();
			if (attribute.getName().equals(attributeName)) {
				attributeValue = (String) attribute.getData();
				break;
			}
		}
		return attributeValue;
	}

	@SuppressWarnings("rawtypes")
	public static Element findElement(Element searchedElement, String targetNodePrefix, String targetNodeAttributeName,
			String targetNodeAttributeValue) {
		Element elementTarget = null;
		for (Iterator i = searchedElement.elementIterator(targetNodePrefix); i.hasNext();) {
			Element element = (Element) i.next();
			String strManagerName = XMLFileUtil.getAttributeValue(element, targetNodeAttributeName);
			if (strManagerName.equals(targetNodeAttributeValue)) {
				elementTarget = element;
				break;
			}
		}
		return elementTarget;
	}

	@SuppressWarnings("rawtypes")
	public static Element findElement(Element searchedElement, String targetNodePrefix) {
		Element elementTarget = null;
		for (Iterator i = searchedElement.elementIterator(targetNodePrefix); i.hasNext();) {
			Element element = (Element) i.next();
			elementTarget = element;
			break;
		}
		return elementTarget;
	}

	public static String decode(String value) {
		String str = "";
		try {
			str = java.net.URLDecoder.decode(value, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return value;
		}
		return str;
	}

	public static String encode(String value) {
		String str = "";
		try {
			str = java.net.URLEncoder.encode(value, "UTF-8").toLowerCase();
		} catch (Exception e) {
			e.printStackTrace();
			return value;
		}
		return str;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static <T> List<T> getDate(String xml, String datanode, Class clazz, XMLAdapter<T> k) {
		List<T> list = new ArrayList<T>();
		Document xmlDomContent = XMLFileUtil.LoadXmlString(xml);
		for (Iterator i = xmlDomContent.getRootElement().elementIterator(datanode); i.hasNext();) {
			T o = null;
			try {
				o = (T) clazz.newInstance();
			} catch (Exception e) {
				e.printStackTrace();
				return list;
			}
			Element element = (Element) i.next();
			for (Iterator e = element.elementIterator(); e.hasNext();) {
				Element temp = (Element) e.next();
				k.rowdata(temp.getQName().getName(), XMLFileUtil.decode(temp.getText()), o);
			}
			list.add(o);
		}
		return list;
	}
	

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static <T> T getDate(String xml, Class clazz, XMLAdapter k) {
		Object o = null;
		try {
			o = clazz.newInstance();
		} catch (Exception e) {
			e.printStackTrace();
		}
		Document xmlDomContent = XMLFileUtil.LoadXmlString(xml);
		for (Iterator i = xmlDomContent.getRootElement().elementIterator(); i.hasNext();) {
			Element element = (Element) i.next();
			k.rowdata(element.getQName().getName(), XMLFileUtil.decode(element.getText()), o);
		}
		return (T) o;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static <T> T getAttributeData(String xml, Class clazz, XMLAttributeAdapter k) {
		Object o = null;
		try {
			o = clazz.newInstance();
		} catch (Exception e) {
			e.printStackTrace();
		}
		Document xmlDomContent = XMLFileUtil.LoadXmlString(xml);
		Element root = xmlDomContent.getRootElement();
		Map rAttrb = new HashMap();
		for (Iterator rAttrIt = root.attributeIterator(); rAttrIt.hasNext();) {	
			Attribute attr = (Attribute)rAttrIt.next();
			rAttrb.put(attr.getName().toUpperCase(), attr.getText());
		}
	
		k.rowdata(root.getQName().getName(), root.getText(),rAttrb,o);
		for (Iterator i = root.elementIterator(); i.hasNext();) {		
			getAttributeData((Element)i.next(),o,k);
		}
		return (T) o;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static void getAttributeData(Element element, Object o, XMLAttributeAdapter k) {
		Map attrb = new HashMap();
		for (Iterator attrIt = element.attributeIterator(); attrIt.hasNext();) {	
			Attribute attr = (Attribute)attrIt.next();
			attrb.put(attr.getName().toUpperCase(), attr.getText());
		}

		k.rowdata(element.getQName().getName(), element.getText(),attrb,o);
		for (Iterator it = element.elementIterator(); it.hasNext();) {	
			getAttributeData((Element)it.next(),o,k);
		}
	}

	public static void main(String[] args) {
		 String xml =
		 "<?xml version=\"1.0\" encoding=\"utf-8\"?><GoodsCatalogs><GoodsCatalogInfo><GoodsCatalogID>4668</GoodsCatalogID><GoodsCatalogName>%e6%b9%96%e5%8c%97%e7%a7%bb%e5%8a%a8</GoodsCatalogName></GoodsCatalogInfo></GoodsCatalogs>";
		 Document xmlDomContent = XMLFileUtil.LoadXmlString(xml);
		
		 for (Iterator i =
		 xmlDomContent.getRootElement().elementIterator("GoodsCatalogInfo");
		 i.hasNext();) {
		 GoodsCatalogInfo info = new GoodsCatalogInfo();
		 Element element = (Element) i.next();
		 Element textE = element.element("GoodsCatalogID");
		 if (textE != null) {
		 info.setGoodsCatalogID(textE.getText());
		 }
		 }

	}
}
