package com.yoviro.rest.service;

import com.yoviro.rest.dto.search.SearchProductDTO;
import com.yoviro.rest.models.entity.Product;
import com.yoviro.rest.models.repository.ProductRepository;
import com.yoviro.rest.models.repository.specification.handler.OperatorEnum;
import com.yoviro.rest.models.repository.specification.handler.SearchFilter;
import com.yoviro.rest.models.repository.specification.handler.SearchQuery;
import com.yoviro.rest.models.repository.specification.handler.SpecificationUtil;
import com.yoviro.rest.service.interfaces.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

@Service
public class ProductServiceImpl implements IProductService {

    @Autowired
    ProductRepository productRepository;

    @Override
    public Page<Product> searchEnableProducts(Pageable pageable,
                                              SearchProductDTO searchProductDTO) {
        //Define Search Criteria
        searchProductDTO.setEnable(Boolean.TRUE);
        SearchQuery qry = new SearchQuery();
        List<SearchFilter> productCriteria = instanceContactCriteria(searchProductDTO); //Product Filter
        qry.setSearchFilter(productCriteria);

        Specification<Product> specification = SpecificationUtil.bySearchQuery(qry, Product.class, Boolean.FALSE);
        return productRepository.findAll(specification, pageable);
    }

    @Override
    public List<Product> findAllBySkuIn(List<String> skus) {
        return productRepository.findAllBySkuIn(skus);
    }

    static List<SearchFilter> instanceContactCriteria(SearchProductDTO searchProductDTO) {
        List<SearchFilter> filters = new ArrayList<>();
        SearchFilter productFilter;
        if (searchProductDTO.getSku() != null) {
            productFilter = new SearchFilter();
            productFilter.setProperty("sku");
            productFilter.setValue(searchProductDTO.getSku());
            productFilter.setOperator(OperatorEnum.LIKE);

            filters.add(productFilter);
        }

        if (searchProductDTO.getName() != null) {
            productFilter = new SearchFilter();
            productFilter.setProperty("name");
            productFilter.setValue(searchProductDTO.getName());
            productFilter.setOperator(OperatorEnum.LIKE);

            filters.add(productFilter);
        }

        if (searchProductDTO.getDescription() != null) {
            productFilter = new SearchFilter();
            productFilter.setProperty("description");
            productFilter.setValue(searchProductDTO.getDescription());
            productFilter.setOperator(OperatorEnum.LIKE);

            filters.add(productFilter);
        }

        if (searchProductDTO.getCategory() != null) {
            productFilter = new SearchFilter();
            productFilter.setProperty("category");
            productFilter.setValue(searchProductDTO.getCategory());
            productFilter.setOperator(OperatorEnum.EQUALS);

            filters.add(productFilter);
        }

        return filters;
    }
}