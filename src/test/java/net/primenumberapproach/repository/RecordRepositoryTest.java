package net.primenumberapproach.repository;

import net.primenumberapproach.model.PrimeNumber;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.shadow.com.univocity.parsers.common.record.Record;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import static org.assertj.core.api.AssertionsForClassTypes.*;

import static org.junit.jupiter.api.Assertions.*;
@DataJpaTest

@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)

class RecordRepositoryTest {
    @Autowired
    private RecordRepository recordRepository;

    @Test
    void createExecutionRecord() {
        PrimeNumber primeNumber = new PrimeNumber();
        primeNumber.setLowerBound(2);
        primeNumber.setUpperBound(50);
        primeNumber.setApproach(4);
        primeNumber.setTimeElapsed(100);
        Timestamp executedAt = Timestamp.from(Instant.now());
        primeNumber.setExecutedAt(executedAt);

        primeNumber.setCountOfPrime(5);

        recordRepository.save(primeNumber);
        List<PrimeNumber> listOfPrime = recordRepository.findAll();
        assertThat(listOfPrime.size()).isEqualTo(1);

    }
    @AfterEach
    void afterEach(){
        recordRepository.deleteAll();
    }

}