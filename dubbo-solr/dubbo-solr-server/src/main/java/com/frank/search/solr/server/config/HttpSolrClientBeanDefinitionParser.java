/*
 * Copyright 2012-2015 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.frank.search.solr.server.config;

import com.frank.search.solr.server.support.HttpSolrClientFactoryBean;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.xml.AbstractBeanDefinitionParser;
import org.springframework.beans.factory.xml.ParserContext;
import org.w3c.dom.Element;

/**
 * {@link HttpSolrClientBeanDefinitionParser} replaces
 * HttpSolrServerBeanDefinitionParser from version 1.x.
 * 
 * @author Christoph Strobl
 * @since 2.0
 */
public class HttpSolrClientBeanDefinitionParser extends AbstractBeanDefinitionParser {

	@Override
	protected AbstractBeanDefinition parseInternal(Element element, ParserContext parserContext) {
		BeanDefinitionBuilder builder = BeanDefinitionBuilder.rootBeanDefinition(HttpSolrClientFactoryBean.class);
		setSolrHome(element, builder);
		return getSourcedBeanDefinition(builder, element, parserContext);
	}

	private void setSolrHome(Element element, BeanDefinitionBuilder builder) {
		builder.addPropertyValue("collection", element.getAttribute("collection"));
		builder.addPropertyValue("zkConnectTimeout", element.getAttribute("zkConnectTimeout"));
		builder.addPropertyValue("zkClientTimeout", element.getAttribute("zkClientTimeout"));
		builder.addPropertyValue("mode", element.getAttribute("mode"));
		builder.addPropertyValue("url", element.getAttribute("url"));
		builder.addPropertyValue("timeout", element.getAttribute("timeout"));
		builder.addPropertyValue("maxConnections", element.getAttribute("maxConnections"));
		builder.addPropertyValue("maxConnectionsPerHost", element.getAttribute("maxConnectionsPerHost"));
	}

	private AbstractBeanDefinition getSourcedBeanDefinition(BeanDefinitionBuilder builder, Element source,
			ParserContext context) {

		AbstractBeanDefinition definition = builder.getBeanDefinition();
		definition.setSource(context.extractSource(source));
		return definition;
	}

}
