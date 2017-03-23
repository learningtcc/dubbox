/*
 * Copyright 2012 the original author or authors.
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
package com.frank.search.service.impl;

import com.frank.search.domain.PageProductResponse;
import com.frank.search.model.Product;
import com.frank.search.service.CustomSolrRepository;
import com.frank.search.solr.core.SolrOperations;
import com.frank.search.solr.core.query.SimpleQuery;
import com.frank.search.solr.core.query.SimpleStringCriteria;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Christoph Strobl
 */
@Service("customSolrRepository")
public class CustomSolrRepositoryImpl implements CustomSolrRepository {

	@Autowired
	private SolrOperations solrTemplate;

	public Page<Product> findProductsByCustomImplementation(String value, Pageable page) {
		return solrTemplate.queryForPage(new SimpleQuery(new SimpleStringCriteria(value)).setPageRequest(page),
				Product.class);
	}

	public PageProductResponse findProductsByCustom(Product product) {
		SolrQuery sq = new SolrQuery();

		sq.set("q", "goods_name:黄瓜花");
		sq.set("fl", "stores_name", "id", "goods_name");

		QueryResponse queryResponse = null;// solrTemplate.querySolrByCustomDefine(sq);
		List<Product> productList = queryResponse.getBeans(Product.class);
		PageProductResponse pageModel = new PageProductResponse();
		pageModel.setTotalPages(queryResponse.getResults().getNumFound());
		return pageModel;
	}

	/*
	 * @Override public void updateProductCategory(String productId,
	 * List<String> categories) { PartialUpdate update = new
	 * PartialUpdate(SearchableProduct.ID_FIELD, productId);
	 * update.setValueOfField(SearchableProduct.STORES_NAME, categories);
	 * 
	 * solrTemplate.saveBean(update); solrTemplate.commit(); }
	 */

}
