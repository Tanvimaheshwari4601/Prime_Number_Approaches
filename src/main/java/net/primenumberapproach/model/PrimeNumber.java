package net.primenumberapproach.model;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "execution_records")
public class PrimeNumber {
    @Id
    @GeneratedValue
    private int id;
    @Column(name = "lower_bound")
private int lowerBound;
    @Column(name = "upper_bound")
private int upperBound;
    @Column(name = "approach")
private int approach;
    @Column(name = "time_elapsed")
private int timeElapsed;
    @Column(name = "count_of_prime_number")
private int countOfPrime;
    @Column(name = "executed_at")
    private Timestamp executedAt;

}
