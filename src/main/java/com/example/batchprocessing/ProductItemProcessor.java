package com.example.batchprocessing;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;

public class ProductItemProcessor implements ItemProcessor<Product, Product> {

	private static final Logger log = LoggerFactory.getLogger(ProductItemProcessor.class);

	@Override
    public Product process(final Product product) throws Exception {
        final String product_code = product.getProduct_code();
        final String name = product.getName();
        final String kana = product.getKana();
        final Date created_at = new Date();
        final Integer created_user_id = 99999999;
        final Date updated_at = new Date();
        final Integer updated_user_id = 99999999;


		final Product transformedProduct = new Product(product_code, name, kana, created_at, created_user_id, updated_at, updated_user_id);

        log.info("登録データ＞＞＞＞＞＞" + transformedProduct + ")");

        return transformedProduct;
    }

}
