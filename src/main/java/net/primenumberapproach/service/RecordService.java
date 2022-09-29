package net.primenumberapproach.service;

import net.primenumberapproach.dto.Approach;
import net.primenumberapproach.exception.InvalidRequestBody;
import net.primenumberapproach.model.PrimeNumber;
import net.primenumberapproach.repository.RecordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;

import java.util.List;


@Service
public class RecordService {
    @Autowired
    private RecordRepository recordRepository;

     Approach[] approaches = { new Approach(1 , "Basic Approach"), new Approach(2 ,"Optimized Basic Approach"),
    new Approach(3, "Using Recursion"), new Approach(4, "Mod Method"), new Approach(5, "Sieve Of Eratosthenes")};

    public Approach[] getAllApproaches(){
        return approaches;
    }

    public Integer[] calculatePrime(int lowerBound, int upperBound, int approachId){
        PrimeNumber primeNumber = new PrimeNumber();
        primeNumber.setLowerBound(lowerBound);
        primeNumber.setUpperBound(upperBound);
        primeNumber.setApproach(approachId);

        Instant startTime = Instant.now();
        Integer[] result = approach(lowerBound, upperBound, approachId);

        Instant endTime = Instant.now();

        primeNumber.setTimeElapsed(endTime.getNano() - startTime.getNano());
        Timestamp executedAt= Timestamp.from(Instant.now());
        primeNumber.setExecutedAt(executedAt);

        primeNumber.setCountOfPrime(result.length);

        recordRepository.save(primeNumber);
        return approach(lowerBound, upperBound, approachId);
    }


    public  Integer[] approach(int start , int end, int approachId){

        switch(approachId){
            case 1: return this.basicApproach(start, end);
            case 2: return this.optimizedBasicApproach(start, end);
            case 3: return this.usingRecursion(start, end);
            case 4: return this.modMethod(start, end);
            case 5: return this.sieveOfEratosthenes(start, end);
            default: throw  new InvalidRequestBody("Invalid Choice for approach");
        }

    }
    public  Integer[] basicApproach(int start , int end){
        List<Integer> result = new ArrayList<>();
        for(int i = start; i <= end ; i++) {
            if (i > 1) {
                boolean isPrime = true;
                for (int j = 2; j < i; j++) {
                    if (i % j == 0) {
                        isPrime = false;
                        break;
                    }
                }
                if (isPrime == true) {
                    result.add(i);
                }
            }
        }

        return result.toArray(new Integer[result.size()]);
    }
    public  Integer[] optimizedBasicApproach(int start , int end){
        List<Integer> result = new ArrayList<>();
        for(int i = start; i <= end ; i++) {
            if (i > 1) {
                boolean isPrime = true;
                for (int j = 2; j <= Math.sqrt(i); j++) {
                    if (i % j == 0) {
                        isPrime = false;
                        break;
                    }
                }
                if (isPrime == true) {
                    result.add(i);
                }
            }
        }

        return result.toArray(new Integer[result.size()]);
    }
    public  Integer[] usingRecursion(int start, int end){
        List<Integer> result = new ArrayList<>();
        for(int i = start; i <= end ; i++){
            if(isPrimeUsingRecursion(i, 2)){
                result.add(i);
            }
        }
        return result.toArray(new Integer[result.size()]);
    }
    public  boolean isPrimeUsingRecursion(int n, int i){
        if(n == i){
            return true;
        }
        else if(n%i==0){
            return false;
        }
        else{
            return isPrimeUsingRecursion(n, i+1);
        }

    }
    public  Integer[] modMethod(int start, int end){
        List<Integer> result = new ArrayList<>();
        for(int i = start ; i <= end ; i++){
            boolean isPrime = true;
            if( i == 2 || i == 3){
                isPrime = true;
            }
           else if(i <= 1 || i % 2 == 0 || i % 3 ==0){
                isPrime = false;
            }
            for(int j = 5 ; j*j <= i; j+=6){
                if(i%j==0 || i % (j+2) ==0){
                    isPrime = false;
                }
            }
            if(isPrime == true){
                result.add(i);
            }

        }
        return result.toArray(new Integer[result.size()]);
    }
    public  Integer[] sieveOfEratosthenes(int start, int end){
        List<Integer> result = new ArrayList<>();
        boolean prime[] = new boolean[end+1];
        for(int i = 2;i <= end ; i++){
            prime[i] = true;
        }
        for(int j = 2; j <= end ; j++){
            if(prime[j] == true){
                if(j>=start) {
                    result.add(j);
                }
                for(int k = j*j; k <= end; k+=j){
                    prime[k] = false;
                }
            }
        }
        return result.toArray(new Integer[result.size()]);
    }
}
