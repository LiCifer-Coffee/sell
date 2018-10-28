package com.imooc.sell.service.impl;

import com.imooc.sell.dataobject.ProductCategory;
import com.imooc.sell.respository.ProductCategoryRespository;
import com.imooc.sell.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private ProductCategoryRespository respository;

    @Override
    public ProductCategory findOne(Integer categoryId) {
        return respository.findById(categoryId).orElse(null);
    }

    @Override
    public List<ProductCategory> findAll() {
        return respository.findAll();
    }

    @Override
    public List<ProductCategory> findByCategoryTypeIn(List<Integer> types) {
        return respository.findByCategoryTypeIn(types);
    }

    @Override
    public ProductCategory save(ProductCategory productCategory) {
        return respository.save(productCategory);
    }
}
