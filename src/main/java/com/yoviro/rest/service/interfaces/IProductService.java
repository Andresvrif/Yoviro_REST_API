package com.yoviro.rest.service.interfaces;

import com.yoviro.rest.dto.search.SearchJobDTO;
import com.yoviro.rest.dto.search.SearchProductDTO;
import com.yoviro.rest.models.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IProductService {
    Page<Product> searchEnableProducts(Pageable pageable, SearchProductDTO searchProductDTO);
}
