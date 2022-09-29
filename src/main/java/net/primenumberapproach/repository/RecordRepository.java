package net.primenumberapproach.repository;

import net.primenumberapproach.model.PrimeNumber;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RecordRepository extends JpaRepository<PrimeNumber, Long> {


}
