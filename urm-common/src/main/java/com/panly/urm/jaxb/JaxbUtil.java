package com.panly.urm.jaxb;


import java.io.StringReader;
import java.io.StringWriter;
import java.util.Collection;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.namespace.QName;

/**
 * 使用Jaxb2.0实现XML<->Java Object的Binder.
 * 
 * 特别支持Root对象是List的情形.
 * 
 * @author
 */
public class JaxbUtil {

	public static String toXml(Object o) {
		return toXml(o, "UTF-8");
	}

	public static String toXml(Object o, String encoding) {
		try {
			JAXBContext jaxbContext = JAXBContext.newInstance(o.getClass());
			StringWriter writer = new StringWriter();
			Marshaller marshaller = jaxbContext.createMarshaller();
			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
			marshaller.setProperty(Marshaller.JAXB_ENCODING, encoding);
			marshaller.marshal(o, writer);
			return writer.toString();
		} catch (JAXBException e) {
			throw new RuntimeException(e);
		}
	}

	public static <T> String toListXml(Collection<T> list, Class<T> clazz,String rootName) {
		return toListXml(list, clazz, rootName, "UTF-8");
	}
	
	public static <T> String toListXml(Collection<T> list, Class<T> clazz,String rootName, String encoding) {
		try {
			CollectionWrapper wrapper = new CollectionWrapper();
			wrapper.collection = list;

			JAXBElement<CollectionWrapper> wrapperElement = new JAXBElement<CollectionWrapper>(new QName(rootName),
					CollectionWrapper.class, wrapper);
			
			JAXBContext jaxbContext = JAXBContext.newInstance(clazz);
			
			StringWriter writer = new StringWriter();
			Marshaller marshaller = jaxbContext.createMarshaller();
			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
			marshaller.setProperty(Marshaller.JAXB_ENCODING, encoding);
			marshaller.marshal(wrapperElement, writer);
			return writer.toString();
		} catch (JAXBException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 封装Root Element 是 Collection的情况.
	 */
	public static class CollectionWrapper {
		@XmlAnyElement
		protected Collection<?> collection;
	}

	@SuppressWarnings("unchecked")
	public static <T> T toBean(String xml, Class<T> clazz) {
		try {
			JAXBContext jaxbContext = JAXBContext.newInstance(clazz);
			StringReader reader = new StringReader(xml);
			return (T) jaxbContext.createUnmarshaller().unmarshal(reader);
		} catch (JAXBException e) {
			throw new RuntimeException(e);
		}
	}

}
