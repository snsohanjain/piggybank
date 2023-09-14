package com.sohan.piggybank;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.expression.spel.ast.Projection;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserTransactionRepository extends MongoRepository<UserTransactions,Long> {
    Optional<UserTransactions> findUserTransactionsByUserId(Long id);
    Optional<List> getUserTransactionsByUserId(Long id);
}
