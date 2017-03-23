/*
 * Copyright 2012 - 2013 the original author or authors.
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
package com.frank.search.solr.core.query.result;

import org.springframework.data.domain.Page;

import java.util.List;

/**
 * @author Christoph Strobl
 */
public interface HighlightPage<T> extends Page<T> {

	/**
	 * @return empty list of not set
	 */
	List<HighlightEntry<T>> getHighlighted();

	/**
	 * @param entity
	 * @return empty list if none found
	 */
	List<HighlightEntry.Highlight> getHighlights(T entity);

}
