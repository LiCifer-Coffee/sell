package com.imooc.sell.respository;

import com.imooc.sell.dataobject.ProductInfo;
import com.imooc.sell.dto.CartDto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductInfoRespository  extends JpaRepository<ProductInfo,String>{

    List<ProductInfo> findByProductStatus(Integer productStatus);

    void increaseStock(List<CartDto> cartDtos);

}
