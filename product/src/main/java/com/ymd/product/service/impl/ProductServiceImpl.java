package com.ymd.product.service.impl;

import com.ymd.product.dataObject.ProductInfo;
import com.ymd.product.dto.CartDTO;
import com.ymd.product.enums.GoodsEnum;
import com.ymd.product.enums.ProductStatusEnum;
import com.ymd.product.exception.ProductException;
import com.ymd.product.repository.ProductInfoRepository;
import com.ymd.product.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductInfoRepository productInfoRepository;

    @Override
    public List<ProductInfo> findUpAll() {
        return productInfoRepository.findByProductStatus(ProductStatusEnum.UP.getCode());
    }

    @Override
    public List<ProductInfo> findList(List<String> productIdList) {
        return productInfoRepository.findByProductIdIn(productIdList);
    }

    @Override
    @Transactional
    public void decreaseStock(List<CartDTO> cartDTOList) {
        for (CartDTO cartDTO : cartDTOList){
            Optional<ProductInfo> productInfoOptional = productInfoRepository.findById(cartDTO.getProductId());
            //判断商品是否存在
            if (!productInfoOptional.isPresent()){
                throw new ProductException(GoodsEnum.PRODUCT_NOT_EXIST);
            }
            ProductInfo productInfo = productInfoOptional.get();
            //判断库存是否足够
            Integer result = productInfo.getProductStock() - cartDTO.getProducQuantity();
            if (result < 0) {
                throw new ProductException(GoodsEnum.PRODUCT_STOCK_ERROR);
            }

            productInfo.setProductStock(result);
            productInfoRepository.save(productInfo);
        }
    }
}
