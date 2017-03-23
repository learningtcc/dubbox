package com.frank.search.provider;

import com.alibaba.dubbo.config.annotation.Service;
import com.frank.search.transmitData.Request;
import com.frank.search.transmitData.ResponseDefault;
import com.frank.search.domain.PageProductResponse;
import com.frank.search.domain.ProductRequestDto;
import com.frank.search.model.Product;
import com.frank.search.service.CustomSolrRepository;
import com.frank.search.solrInterface.ICategorySearchQueryProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

/**
 * Created with IntelliJ IDEA. User: frank Date: 15-11-3 Time: 下午1:59 To change
 * this template use File | Settings | File Templates.
 */
@Service(version = "1.0.0")
public class CategorySearchQueryProviderImpl extends CommonProvider implements ICategorySearchQueryProvider {

	@Autowired
	private CustomSolrRepository customSolrRepository;

	public ResponseDefault searchCategoryGoods(Request request) throws Exception {

		// Product initial = createProduct("GB18030TEST");
		// request.getContent();

		Product product = (Product) request.getContent(Product.class);

		Page<Product> page = customSolrRepository.findProductsByCustomImplementation(
				Product.GOODS_NAME + ":" + product.getGoodsName(), new PageRequest(0, 10));

		ProductRequestDto productRequestDto = new ProductRequestDto();
		productRequestDto.setRows(10);
		productRequestDto.setStart(0);

		// Assert.assertEquals(1, page.getTotalElements());
		return factory.createResult(productRequestDto);
	}

	/**
	 * HelloWorld测试
	 *
	 * @param request
	 * @return
	 */
	public ResponseDefault searchCategory(Request request) throws Exception {
		Product product = (Product) request.getContent(Product.class);
		PageProductResponse pageModel = customSolrRepository.findProductsByCustom(product);
		return factory.createResult(pageModel);
	}

	/**
	 * HelloWorld测试
	 *
	 * @param request
	 * @return
	 */
	public void searchGoods(Request request) {
		/*
		 * ICategorySearchQueryProvider.class.getTypeParameters()
		 * ParameterizedType parameterizedType =
		 * (ParameterizedType)this.getClass().getGenericSuperclass(); Type
		 * genType = clazz.getGenericSuperclass();
		 */
	}

	protected Product createProduct(String id) {
		Product product = new Product();
		// product.setId(id);
		product.setStoresName("上海浦东东方微店");
		return product;
	}

}
