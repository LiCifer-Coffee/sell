package com.imooc.sell.controller;

import com.imooc.sell.VO.ProductInfoVo;
import com.imooc.sell.VO.ProductVo;
import com.imooc.sell.VO.ResultVo;
import com.imooc.sell.dataobject.ProductCategory;
import com.imooc.sell.dataobject.ProductInfo;
import com.imooc.sell.service.CategoryService;
import com.imooc.sell.service.ProductService;
import com.imooc.sell.util.ResultVoUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("buyer/product")
public class BuyerProductController {

    @Autowired
    private ProductService productService;

    @Autowired
    private CategoryService categoryService;


    @GetMapping("list")
    public ResultVo list(){

        List<ProductInfo> upAll = productService.findUpAll();

        List<Integer> categoryList = upAll.stream().map(e -> e.getCategoryType()).collect(Collectors.toList());

        List<ProductCategory> categories = categoryService.findByCategoryTypeIn(categoryList);

        List<ProductVo> productVos = new ArrayList<>();
        for (ProductCategory pc:categories) {
            ProductVo productVo = new ProductVo();
            productVo.setCategoryName(pc.getCategoryName());
            productVo.setCategoryType(pc.getCategoryType());


            List<ProductInfoVo> productInfoVos = new ArrayList<>();
            for (ProductInfo p:upAll) {
                if(p.getCategoryType().equals(pc.getCategoryType())){

                    ProductInfoVo piv = new ProductInfoVo();
                    piv.setProductDescription(p.getProductDescription());
                    piv.setProductIcon(p.getProductIcon());
                    piv.setProductId(p.getProductId());
                    piv.setProductPrice(p.getProductPrice());

                    productInfoVos.add(piv);
                }
            }

            productVo.setProductInfos(productInfoVos);

            productVos.add(productVo);
        }


        return ResultVoUtil.success(productVos);
    }


}
