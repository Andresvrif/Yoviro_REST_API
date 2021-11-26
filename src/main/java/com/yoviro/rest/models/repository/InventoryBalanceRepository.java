package com.yoviro.rest.models.repository;

import com.yoviro.rest.models.entity.InventoryBalance;
import com.yoviro.rest.models.entity.Product;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InventoryBalanceRepository extends PagingAndSortingRepository<InventoryBalance, Long> {
    InventoryBalance findByProduct(Product product);
}
