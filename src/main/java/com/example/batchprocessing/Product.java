package com.example.batchprocessing;

import java.util.Date;

import lombok.Data;

@Data
public class Product {

	private String product_code;
	private String name;
	private String kana;
	private Date created_at;
    private Integer created_user_id;
    private Date updated_at;
    private Integer updated_user_id;

	public Product() {

	}

	public Product(String product_code, String name, String kana, Date created_at, Integer created_user_id, Date updated_at, Integer updated_user_id) {
		this.product_code = product_code;
		this.name = name;
		this.kana = kana;
		this.created_at = created_at;
		this.created_user_id = created_user_id;
		this.updated_at = updated_at;
		this.updated_user_id = updated_user_id;

	}

	@Override
    public String toString() {
        return "商品コード：" + product_code + ", 商品名：" + name + ", 商品カナ：" + kana;
    }

}
