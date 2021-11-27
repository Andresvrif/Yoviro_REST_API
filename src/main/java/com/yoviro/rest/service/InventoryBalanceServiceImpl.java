package com.yoviro.rest.service;

import com.yoviro.rest.models.entity.*;
import com.yoviro.rest.models.repository.InventoryBalanceRepository;
import com.yoviro.rest.models.repository.InventoryTransactionByInventoryRequestRepository;
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

    @Autowired
    InventoryTransactionByInventoryRequestRepository inventoryTransactionByInventoryRequestRepository;

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

        //Save Transactions
        InputTransactionByPurchaseOrder inputTrx = instanceInventoryTrxFromPurchaseOrder(purchaseOrder);
        inputTrx = inventoryTransactionByPurchaseOrderRepository.save(inputTrx);
        HashSet<InventoryBalance> balances = (HashSet<InventoryBalance>) inputTrx.getDetails().stream().map(e -> e.getBalance()).collect(Collectors.toSet());
        return balances.stream().collect(Collectors.toUnmodifiableList());
    }

    @Override
    public List<InventoryBalance> processInventoryRequests(List<InventoryRequest> inventoryRequests) {
        //Execute Trxs
        inventoryRequests.forEach(e -> processInventoryRequest(e));

        HashSet<Product> productsToBeFind = new HashSet<>();
        for (InventoryRequest inventoryRequest : inventoryRequests) {
            for (InventoryRequestDetail detail : inventoryRequest.getDetails()) {
                productsToBeFind.add(detail.getProduct());
            }
        }

        //Inventory balance
        HashSet<InventoryBalance> inventoryBalances = new HashSet<>();
        for (Product product : productsToBeFind) {
            inventoryBalances.add(inventoryBalanceRepository.findByProduct(product));
        }

        return inventoryBalances.stream().collect(Collectors.toList());
    }

    public List<InventoryBalance> processInventoryRequest(InventoryRequest inventoryRequest) {
        if (inventoryRequest == null) return null;

        //Save Transactions
        OutputTransactionByInventoryRequest inputTrx = instanceInventoryTrxFromInventoryRequest(inventoryRequest);
        inputTrx = inventoryTransactionByInventoryRequestRepository.save(inputTrx);
        HashSet<InventoryBalance> balances = (HashSet<InventoryBalance>) inputTrx.getDetails().stream().map(e -> e.getBalance()).collect(Collectors.toSet());
        return balances.stream().collect(Collectors.toUnmodifiableList());
    }

    public OutputTransactionByInventoryRequest instanceInventoryTrxFromInventoryRequest(InventoryRequest inventoryRequest) {
        //Bring balances
        List<InventoryBalance> inventoryBalances = bringInventoryBalances(inventoryRequest);
        inventoryBalances = (List<InventoryBalance>) inventoryBalanceRepository.saveAll(inventoryBalances);

        OutputTransactionByInventoryRequest inventoryTransaction = new OutputTransactionByInventoryRequest();
        inventoryTransaction.setInventoryRequest(inventoryRequest);

        InventoryBalance balanceRelated;
        InventoryTransactionDetail trxDetail;
        for (InventoryRequestDetail detail : inventoryRequest.getDetails()) {
            balanceRelated = inventoryBalances.stream().filter(e -> e.getProduct().getSku().compareToIgnoreCase(detail.getProduct().getSku()) == 0).findFirst().get();
            //Instance Trx Detail
            trxDetail = new InventoryTransactionDetail();
            trxDetail.setInventoryTransaction(inventoryTransaction);
            trxDetail.setProduct(detail.getProduct());
            trxDetail.setQuantity(detail.getQuantity());

            //Instance Balance
            //InventoryBalance balance = getOrCreateInventoryBalance(e.getProduct());
            updateBalance(balanceRelated, trxDetail);

            //Map
            balanceRelated.getDetails().add(trxDetail);
            trxDetail.setBalance(balanceRelated);
            inventoryTransaction.getDetails().add(trxDetail);
        }

        return inventoryTransaction;
/*
        List<InventoryTransactionDetail> details = inventoryRequest.getDetails().stream().map(e -> {
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

        return inventoryTransaction;*/
    }

    /***
     * Author : Andrés V.
     * Desc : Creates the input transaction with their details accord the purchase order
     * @param purchaseOrder
     * @return
     */
    public InputTransactionByPurchaseOrder instanceInventoryTrxFromPurchaseOrder(PurchaseOrder purchaseOrder) {
        //Bring balances
        List<InventoryBalance> inventoryBalances = bringInventoryBalances(purchaseOrder);
        inventoryBalances = (List<InventoryBalance>) inventoryBalanceRepository.saveAll(inventoryBalances);

        //TODO evaluar si es necesario crear una relación de purchase order a inventory
        InputTransactionByPurchaseOrder inventoryTransaction = new InputTransactionByPurchaseOrder();
        inventoryTransaction.setPurchaseOrder(purchaseOrder);

        InventoryBalance balanceRelated;
        InventoryTransactionDetail trxDetail;
        for (PurchaseOrderDetail detail : purchaseOrder.getDetails()) {
            balanceRelated = inventoryBalances.stream().filter(e -> e.getProduct().getSku().compareToIgnoreCase(detail.getProduct().getSku()) == 0).findFirst().get();
            //Instance Trx Detail
            trxDetail = new InventoryTransactionDetail();
            trxDetail.setInventoryTransaction(inventoryTransaction);
            trxDetail.setProduct(detail.getProduct());
            trxDetail.setQuantity(detail.getQuantity());

            //Instance Balance
            //InventoryBalance balance = getOrCreateInventoryBalance(e.getProduct());
            updateBalance(balanceRelated, trxDetail);

            //Map
            balanceRelated.getDetails().add(trxDetail);
            trxDetail.setBalance(balanceRelated);
            inventoryTransaction.getDetails().add(trxDetail);
        }

        return inventoryTransaction;
    }


    private void updateBalance(InventoryBalance balance,
                               InventoryTransactionDetail trxDetail) {
        Product product = balance.getProduct() == null ? trxDetail.getProduct() : balance.getProduct();
        InventoryTransaction trx = trxDetail.getInventoryTransaction();
        BigDecimal quantity = null;
        if (trx instanceof InputTransactionByPurchaseOrder) {
            quantity = balance.getQuantity() == null ? trxDetail.getQuantity() : balance.getQuantity().add(trxDetail.getQuantity());
        } else if (trx instanceof OutputTransactionByInventoryRequest) {
            quantity = balance.getQuantity() == null ? trxDetail.getQuantity() : balance.getQuantity().subtract(trxDetail.getQuantity());
        }

        if (balance.getProduct() == null) balance.setProduct(product);
        balance.setQuantity(quantity);
    }

    private List<InventoryBalance> bringInventoryBalances(PurchaseOrder purchaseOrder) {
        List<Product> productsToFind = new ArrayList<>();
        for (PurchaseOrderDetail detail : purchaseOrder.getDetails()) {
            productsToFind.add(detail.getProduct());
        }

        InventoryBalance inventoryBalance = null;
        HashSet<InventoryBalance> inventoryBalances = new HashSet<>();
        for (Product product : productsToFind) {
            if (inventoryBalances.stream().anyMatch(e -> e.getProduct() == product)) continue;
            inventoryBalance = getOrCreateInventoryBalance(product);
            inventoryBalances.add(inventoryBalance);
        }

        return inventoryBalances.stream().collect(Collectors.toList());
    }

    private List<InventoryBalance> bringInventoryBalances(InventoryRequest inventoryRequest) {
        List<Product> productsToFind = new ArrayList<>();
        for (InventoryRequestDetail detail : inventoryRequest.getDetails()) {
            productsToFind.add(detail.getProduct());
        }

        InventoryBalance inventoryBalance = null;
        HashSet<InventoryBalance> inventoryBalances = new HashSet<>();
        for (Product product : productsToFind) {
            if (inventoryBalances.stream().anyMatch(e -> e.getProduct() == product)) continue;
            inventoryBalance = getOrCreateInventoryBalance(product);
            inventoryBalances.add(inventoryBalance);
        }

        return inventoryBalances.stream().collect(Collectors.toList());
    }

    /**
     * Author : Andrés V.
     * Desc : Gets or create a inventory balance based in the product
     *
     * @param product
     * @return
     */
    private InventoryBalance getOrCreateInventoryBalance(Product product) {
        InventoryBalance balance = inventoryBalanceRepository.findByProduct(product);
        if (balance != null) {
            return balance;
        } else {
            balance = new InventoryBalance();
            balance.setQuantity(new BigDecimal(0));
            balance.setProduct(product);
            return balance;
        }
    }

}