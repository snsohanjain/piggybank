package com.sohan.piggybank;

import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class TransactionService {

    private final UserRepository userRepository;
    private final UserTransactionRepository userTransactionRepository;

    public TransactionService(UserRepository userRepository, UserTransactionRepository userTransactionRepository) {
        this.userRepository = userRepository;
        this.userTransactionRepository = userTransactionRepository;
    }
    public User createPiggyBankUser(User user){
        User user1 = new User();
        user1.setUserId(RandomID.randomId());
        user1.setFirstName(user.getFirstName());
        user1.setLastName(user.getLastName());
        user1.setEmail(user.getEmail());
        user1.setBroken(true);
        return userRepository.save(user1);
    }
    public List getAllUser(){
        return userRepository.findAll();
    }
    public Optional<User> getUser(Long id){
        return userRepository.findById(id);
    }
    public String addMoney(Long id, double amount) throws ChangeSetPersister.NotFoundException {
        User user = null;
        if(userRepository.existsById(id)){
            user = userRepository.findUserByUserId(id).orElseThrow(() -> new ChangeSetPersister.NotFoundException());
            if(user.isBroken() == true){
                user.setBalance(user.getBalance() + amount);
                user.setBroken(false);
                userRepository.save(user);
            }else {
                return "Piggy Bank is Already created";
            }

            LocalDateTime localDateTime = LocalDateTime.now();
            UserTransactions userTransactions = new UserTransactions();
            userTransactions.setUserTransactionId(RandomID.randomTransactionId());
            userTransactions.setUserId(id);
            userTransactions.setLocalDateTime(localDateTime);
            userTransactions.setCredit(amount);
            userTransactionRepository.save(userTransactions);
            return "Total Balance: " + user.getBalance();
        }
        return "Something Went Wrong";
    }
    public double getPiggyBankBalance(Long id) throws ChangeSetPersister.NotFoundException {
        User user = userRepository.findUserByUserId(id).orElseThrow(() -> new ChangeSetPersister.NotFoundException());
        return user.getBalance();
    }

    public String breakPiggyBank(Long id)  {
        User user = null;
        if(userRepository.existsById(id)) {
            try {
                user = userRepository.findUserByUserId(id).orElseThrow(() -> new ChangeSetPersister.NotFoundException());
                LocalDateTime localDateTime = LocalDateTime.now();
                UserTransactions userTransactions = new UserTransactions();
                userTransactions.setUserTransactionId(RandomID.randomTransactionId());
                userTransactions.setUserId(id);
                userTransactions.setLocalDateTime(localDateTime);
                userTransactions.setDebit(user.getBalance());
                userTransactionRepository.save(userTransactions);
                return user.getBalance() + " Amount is SuccessFully Transferd to LinkedBank Account";
            }catch (ChangeSetPersister.NotFoundException e){
                return e.getMessage() + "User Not Found";
            }finally {
                user.setBroken(true);
                user.setBalance(0.0);
                userRepository.save(user);
            }
        }
        return "Something Went Wrong ";

    }
    public List<UserTransactions> getAllTheTransactionsOfTheUser(Long id) throws ChangeSetPersister.NotFoundException {
        List<UserTransactions> list= userTransactionRepository.getUserTransactionsByUserId(id).orElseThrow(() -> new ChangeSetPersister.NotFoundException());
        return list;
    }
}
