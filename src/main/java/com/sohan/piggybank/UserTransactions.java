package com.sohan.piggybank;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.LocalDateTime;


@Document(collection = "userTransactions")
@Data
public class UserTransactions {

    @Id
    private Long userTransactionId;
    private Long userId;
    private LocalDateTime localDateTime;
    private Double credit;
    private Double debit;
    private Double balance;

}
