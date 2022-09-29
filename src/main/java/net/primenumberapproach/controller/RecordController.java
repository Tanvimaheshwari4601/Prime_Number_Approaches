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
    @GetMapping("/approaches")
    public Approach[] getAllApproaches(){
        return recordService.getAllApproaches();
    }

    @PostMapping("/calculation")
    public Integer[] calculatePrime(@RequestBody CalculateRequest calculateRequest){

        if(calculateRequest.getLowerBound() > calculateRequest.getUpperBound()){
            throw  new InvalidRequestBody("Invalid Range");
        }

        return  recordService.calculatePrime(calculateRequest.getLowerBound(), calculateRequest.getUpperBound(),
                calculateRequest.getApproachId());
    }
}
