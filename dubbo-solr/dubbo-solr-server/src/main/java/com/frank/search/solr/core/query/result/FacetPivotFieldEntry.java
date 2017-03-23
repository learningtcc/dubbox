package com.frank.search.solr.core.query.result;

import java.util.List;

/**
 * Entry for facet pivot field.
 * 
 * @author Francisco Spaeth
 * 
 */
public interface FacetPivotFieldEntry extends FacetFieldEntry {

	/**
	 * Get the associated pivot to this {@link FacetFieldEntry}.
	 * 
	 * @return
	 */
	List<FacetPivotFieldEntry> getPivot();

}
