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


     // array of available approaches
    Approach[] approaches = { new Approach(1 , "Basic Approach"), new Approach(2 ,"Optimized Basic Approach"),
    new Approach(3, "Using Recursion"), new Approach(4, "6k ± 1 Rule"), new Approach(5, "Sieve Of Eratosthenes")};

    /**
     * to get all the available approaches for calculating Prime Number
     * @return list of approaches
     */
    public Approach[] getAllApproaches(){
        return approaches;
    }

    /**
     * to calculate prime numbers according to provided approachId
     * to save the execution record in database
     * @param lowerBound Starting range
     * @param upperBound Ending range
     * @param approachId Id of selected approach
     * @return Array of Prime Numbers
     */
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

    /**
     * to handle execution of prime number based on different Id's
     * on selecting unavailable approach it throws exception
     * @param start starting Range
     * @param end ending Range
     * @param approachId Selected approach id
     * @return Array of Prime Number
     */
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

    /**
     * basic approach to calculate prime number
     * to check the divisibility of a number from 2 to n(number)
     * time complexity O(n) for checking every individual number
     * @param start starting range
     * @param end ending range
     * @return Array of Prime number
     */
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

    /**
     * optimized method of basic approach
     * to check the divisibility pf a number from 2 to sqrt(n)
     * a larger factor of n must be a multiple of a smaller factor that has been already checked
     * time complexity √n for checking every individual number
     * @param start starting range
     * @param end ending range
     * @return array of Prime numbers
     */
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

    /**
     * to give recursive method for calculating prime number between given range
     * for every number it will call method isPrimeUsingRecursion
     * time complexity √n for checking every individual number
     * @param start
     * @param end
     * @return Array of Prime Numbers
     */
    public  Integer[] usingRecursion(int start, int end){
        List<Integer> result = new ArrayList<>();
        for(int i = start; i <= end ; i++){
            if(isPrimeUsingRecursion(i, 2)){
                result.add(i);
            }
        }
        return result.toArray(new Integer[result.size()]);
    }

    /**
     * to check whether the number is Prime or not
     * @param n number to be checked
     * @param i divisor value
     * @return checks for next divisor
     */
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

    /**
     * to check the prime number using  6k ± 1 Rule
     * all the Prime numbers greater than 3 can be expressed in the form of 6k ± 1 where k>0
     * time complexity √n for checking every individual number
     * @param start Starting Range
     * @param end Ending Range
     * @return array of Prime number
     */
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

    /**
     * to check the prime number in given range by Sieve of Eratosthenes
     * mark all the numbers multiples of prime number (for ex. 2, 3 , 5 and so on) and set to composite
     * time complexity O(n*log(log(n))) for checking every individual number
     * @param start starting range
     * @param end ending range
     * @return array of prime numbers
     */
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
