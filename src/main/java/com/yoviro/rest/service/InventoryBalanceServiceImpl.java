package com.yoviro.rest.service;

import com.yoviro.rest.models.entity.*;
import com.yoviro.rest.models.repository.InventoryBalanceRepository;
import com.yoviro.rest.models.repository.InventoryTransactionByPurchaseOrderRepository;
import com.yoviro.rest.service.interfaces.IInventoryBalanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class InventoryBalanceServiceImpl implements IInventoryBalanceService {

    @Autowired
    InventoryBalanceRepository inventoryBalanceRepository;

    @Autowired
    InventoryTransactionByPurchaseOrderRepository inventoryTransactionByPurchaseOrderRepository;

    /***
     * Author : Andrés V.
     * Desc : This function handle the received purchase order to update the balance repository
     * @param purchaseOrder
     * @return
     */
    @Transactional
    @Override
    public List<InventoryBalance> processPurchaseOrder(PurchaseOrder purchaseOrder) {
        if (purchaseOrder == null) return null;
        List<InventoryBalance> inventoryBalancesToBeUpdated = new ArrayList<>();

        //Save Transactions
        InputTransactionByPurchaseOrder inputTrx = instanceInventoryTrxFromPurchaseOrder(purchaseOrder);
        inputTrx = inventoryTransactionByPurchaseOrderRepository.save(inputTrx);
        HashSet<InventoryBalance> balances = (HashSet<InventoryBalance>) inputTrx.getDetails().stream().map(e -> e.getBalance()).collect(Collectors.toSet());
        return balances.stream().collect(Collectors.toUnmodifiableList());
    }

    /***
     * Author : Andrés V.
     * Desc : Creates the input transaction with their details accord the purchase order
     * @param purchaseOrder
     * @return
     */
    public InputTransactionByPurchaseOrder instanceInventoryTrxFromPurchaseOrder(PurchaseOrder purchaseOrder) {
        //TODO evaluar si es necesario crear una relación de purchase order a inventory
        InputTransactionByPurchaseOrder inventoryTransaction = new InputTransactionByPurchaseOrder();
        inventoryTransaction.setPurchaseOrder(purchaseOrder);

        List<InventoryTransactionDetail> details = purchaseOrder.getDetails().stream().map(e -> {
            //Instance Trx Detail
            InventoryTransactionDetail detail = new InventoryTransactionDetail();
            detail.setInventoryTransaction(inventoryTransaction);
            detail.setProduct(e.getProduct());
            detail.setQuantity(e.getQuantity());

            //Instance Balance
            InventoryBalance balance = getOrCreateInventoryBalance(e.getProduct());
            updateBalance(balance, detail);

            //Map
            balance.getDetails().add(detail);
            detail.setBalance(balance);

            return detail;
        }).collect(Collectors.toList());
        inventoryTransaction.setDetails(details);

        return inventoryTransaction;
    }

    private void updateBalance(InventoryBalance balance,
                               InventoryTransactionDetail trxDetail) {
        Product product = balance.getProduct() == null ? trxDetail.getProduct() : balance.getProduct();
        BigDecimal quantity = balance.getQuantity() == null ? trxDetail.getQuantity() : balance.getQuantity().add(trxDetail.getQuantity());

        if (balance.getProduct() == null) balance.setProduct(product);
        balance.setQuantity(quantity);
    }

    /**
     * Author : Andrés V.
     * Desc : Gets or create a inventory balance based in the product
     *
     * @param product
     * @return
     */
    private InventoryBalance getOrCreateInventoryBalance(Product product/*,
                                                         List<InventoryBalance> loadedBalances*/) {
        InventoryBalance balance = inventoryBalanceRepository.findByProduct(product);
        return balance != null ? balance : new InventoryBalance();
/*        InventoryBalance inventoryBalance = null;
        if (loadedBalances == null) {
            Optional<InventoryBalance> optionalInventoryBalance = null;
            optionalInventoryBalance = loadedBalances.stream().filter(e -> e.getProduct() == product).findFirst();
            inventoryBalance = optionalInventoryBalance.isEmpty() ? inventoryBalanceRepository.findByProduct(product) : optionalInventoryBalance.get();
        } else {*/
  /*          inventoryBalance = inventoryBalanceRepository.findByProduct(product);
        }

        return inventoryBalance != null ? inventoryBalance : new InventoryBalance();*/
    }

}