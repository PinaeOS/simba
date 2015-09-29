package org.pinae.simba.factory.xml;

import org.pinae.simba.factory.KernelConstant;

/**
 * XML解析的常量信息
 * 
 * @author Huiyugeng
 *
 */
public interface XmlConstant extends KernelConstant{
	static final String schemaFeatures = "http://apache.org/xml/features/validation/schema";
	static final String schemaProperties = "http://apache.org/xml/properties/schema/external-noNamespaceSchemaLocation";
	static final String defaultSchemaFile = "bean.xsd";
}