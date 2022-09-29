package net.primenumberapproach.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class CalculateRequest {
    private int lowerBound;
    private int upperBound;
    private int approachId;
}
