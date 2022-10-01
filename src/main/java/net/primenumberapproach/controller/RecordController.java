package net.primenumberapproach.controller;
import net.primenumberapproach.dto.Approach;
import net.primenumberapproach.dto.CalculateRequest;
import net.primenumberapproach.exception.InvalidRequestBody;
import net.primenumberapproach.service.RecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class RecordController {
    @Autowired
    RecordService recordService;

    /**
     * To get all the available Approaches to calculate Prime Numbers
     * @return list of approaches
     */
    @GetMapping("/approaches")
    public Approach[] getAllApproaches(){
        return recordService.getAllApproaches();
    }

    /**
     * To calculate Prime number based on lowerBound, upperBound and approachid provided by the user
     * upperBound value should be always greater than lowerBound otherwise it will throw exception
     * @param calculateRequest Request Body consisting lowerBound , upeerBound and approchId
     * @return List of prime Numbers
     */
    @PostMapping("/calculation")
    public Integer[] calculatePrime(@RequestBody CalculateRequest calculateRequest){

        if(calculateRequest.getLowerBound() > calculateRequest.getUpperBound()){
            throw  new InvalidRequestBody("Invalid Range");
        }

        return  recordService.calculatePrime(calculateRequest.getLowerBound(), calculateRequest.getUpperBound(),
                calculateRequest.getApproachId());
    }
}
