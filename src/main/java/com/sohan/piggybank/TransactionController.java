package com.sohan.piggybank;

import jakarta.validation.Valid;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.web.bind.annotation.*;
import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/piggybank")
public class TransactionController {
    private TransactionService transactionService;
    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @PostMapping(path = "/create")
    public User createPiggyBankAccount(@Valid @RequestBody User user) {
        return transactionService.createPiggyBankUser(user);
    }
    @GetMapping(path ="balance/{userId}")
    public double getPiggyBankBalance(@PathVariable ("userId") Long id) throws ChangeSetPersister.NotFoundException {
       return transactionService.getPiggyBankBalance(id);
    }
    @GetMapping(path = "/getAllUsers")
     public List getAllUser() {
        return transactionService.getAllUser();
    }
    @GetMapping(path = "getAllTransactions/{userId}")
     public List<UserTransactions> getAllTransactionsOfTheUser(@PathVariable ("userId") Long id) throws ChangeSetPersister.NotFoundException {
        return transactionService.getAllTheTransactionsOfTheUser(id);
    }
    @GetMapping(path ="{userId}")
    public List getAllUser(@PathVariable("userId") Long id) {
        return Collections.singletonList(transactionService.getUser(id));
    }
    @PostMapping(path ="{userId}/deposit/{amount}")
    public String  addMoneyToPiggyBank(@PathVariable("userId") Long id, @PathVariable("amount") double amount) throws ChangeSetPersister.NotFoundException {
        return transactionService.addMoney(id,amount);
    }
    @PutMapping(path ="break/{userId}")
    public String breakPiggyBank(@PathVariable("userId") Long id) {
        return transactionService.breakPiggyBank(id);
    }
}
