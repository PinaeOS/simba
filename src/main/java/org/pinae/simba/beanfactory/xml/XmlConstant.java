package org.pinae.simba.beanfactory.xml;

import org.pinae.simba.beanfactory.KernelConstant;

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