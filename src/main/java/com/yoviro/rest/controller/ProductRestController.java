package com.yoviro.rest.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.yoviro.rest.config.AppConfig;
import com.yoviro.rest.config.enums.ProductCategoryEnum;
import com.yoviro.rest.dto.search.SearchProductDTO;
import com.yoviro.rest.models.entity.Product;
import com.yoviro.rest.service.interfaces.IProductService;
import com.yoviro.rest.util.JSONUtil;
import com.yoviro.rest.util.PageUtil;
import com.yoviro.rest.util.StringUtil;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/product")
public class ProductRestController {
    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    ModelMapper modelMapper;

    @Autowired
    IProductService productService;

    @GetMapping("/search")
    public Map<String, Object> search(@RequestParam(required = false) String sku,
                                      @RequestParam(required = false) String name,
                                      @RequestParam(required = false) ProductCategoryEnum category,
                                      @RequestParam(required = false) String word,
                                      @RequestParam(required = true) String page) {
        //Define Search criteria
        SearchProductDTO searchProductDTO = new SearchProductDTO();
        searchProductDTO.setSku(sku);
        searchProductDTO.setName(name);
        searchProductDTO.setDescription(word);
        searchProductDTO.setCategory(category);

        //Define Page criteria
        Integer pageNumber = PageUtil.definePageNumber(page);
        Pageable pageableByName = PageRequest.of(pageNumber, AppConfig.PAGE_SIZE, Sort.by("name").ascending());

        Page<Product> pageResult = productService.searchEnableProducts(pageableByName, searchProductDTO);

        //Define Response
        Map<String, Object> response = new HashMap<String, Object>();
        response.put(AppConfig.METADATA_TAG, JSONUtil.pageToJson(pageResult));
        response.put("content", wrapSearchResult(pageResult.getContent()));

        return response;
    }

    private ArrayList<Map> wrapSearchResult(List<Product> products) {
        List response = new ArrayList<>();
        Map content = null;

        for (Product product : products) {
            content = Map.ofEntries(
                    Map.entry("sku", product.getSku()),
                    Map.entry("category", product.getCategory()),
                    Map.entry("name", StringUtil.capitalizeWord(product.getName())),
                    Map.entry("description", product.getDescription()),
                    Map.entry("unitMeasure", product.getUnitMeasure())
            );

            response.add(content);
        }
        return (ArrayList<Map>) response;
    }
}