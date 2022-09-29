package net.primenumberapproach.service;

import net.primenumberapproach.exception.InvalidRequestBody;
import net.primenumberapproach.repository.RecordRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.AssertionsForClassTypes.*;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.validateMockitoUsage;


@ExtendWith(MockitoExtension.class)
class RecordServiceTest {

    @Autowired
    @InjectMocks
    @Spy
    private RecordService recordService;
    @Mock
    private RecordRepository recordRepository;


    @Test
    void getAllApproaches() {
        assertThat(recordService.getAllApproaches()).isEqualTo(recordService.approaches);
    }

    @Test
    void calculatePrime() {
        Integer[] mockedData = {2, 3, 5, 7};
        Mockito.doReturn(mockedData).when(recordService).approach(Mockito.anyInt(), Mockito.anyInt(), Mockito.anyInt());
        assertThat(recordService.calculatePrime(2, 10, 2)).isEqualTo(mockedData);

        Mockito.verify(recordRepository, Mockito.times(1)).save(Mockito.any());

    }

    @Test
    void approach() {
        recordService.approach(2, 10, 1);
        Mockito.verify(recordService, Mockito.times(1)).basicApproach(Mockito.anyInt(), Mockito.anyInt());

        recordService.approach(2, 10, 2);
        Mockito.verify(recordService, Mockito.times(1)).optimizedBasicApproach(Mockito.anyInt(), Mockito.anyInt());

        recordService.approach(2, 10, 3);
        Mockito.verify(recordService, Mockito.times(1)).usingRecursion(Mockito.anyInt(), Mockito.anyInt());

        recordService.approach(2, 10, 4);
        Mockito.verify(recordService, Mockito.times(1)).modMethod(Mockito.anyInt(), Mockito.anyInt());

        recordService.approach(2, 10, 5);
        Mockito.verify(recordService, Mockito.times(1)).sieveOfEratosthenes(Mockito.anyInt(), Mockito.anyInt());

        InvalidRequestBody thrown = assertThrows(
                InvalidRequestBody.class,
                () -> recordService.approach(2,10,8),
                "Expected doThing() to throw, but it didn't"
        );



        assertThat(thrown.getMessage()).isEqualTo("Invalid Choice for approach");
    }


    @Test
    void basicApproach() {
        Integer[] result = recordService.basicApproach(2,10);
        assertThat(result.length).isEqualTo(4);
        assertThat(result[result.length-1]).isEqualTo(7);
        assertThat(result[0]).isEqualTo(2);
        assertThat(result[result.length-2]).isEqualTo(5);

        Integer[] result1 = recordService.basicApproach(6,100);
        assertThat(result1.length).isEqualTo(22);
        assertThat(result1[result1.length-1]).isEqualTo(97);
        assertThat(result1[0]).isEqualTo(7);
        assertThat(result1[result1.length-2]).isEqualTo(89);



    }


    @Test
    void optimizedBasicApproach() {
        Integer[] result = recordService.optimizedBasicApproach(2,10);
        assertThat(result.length).isEqualTo(4);
        assertThat(result[result.length-1]).isEqualTo(7);
        assertThat(result[0]).isEqualTo(2);
        assertThat(result[result.length-2]).isEqualTo(5);

        Integer[] result1 = recordService.optimizedBasicApproach(6,100);
        assertThat(result1.length).isEqualTo(22);
        assertThat(result1[result1.length-1]).isEqualTo(97);
        assertThat(result1[0]).isEqualTo(7);
        assertThat(result1[result1.length-2]).isEqualTo(89);
    }

    @Test
    void usingRecursion() {
        Integer[] result = recordService.usingRecursion(2,10);
        assertThat(result.length).isEqualTo(4);
        assertThat(result[result.length-1]).isEqualTo(7);
        assertThat(result[0]).isEqualTo(2);
        assertThat(result[result.length-2]).isEqualTo(5);

        Integer[] result1 = recordService.usingRecursion(6,100);
        assertThat(result1.length).isEqualTo(22);
        assertThat(result1[result1.length-1]).isEqualTo(97);
        assertThat(result1[0]).isEqualTo(7);
        assertThat(result1[result1.length-2]).isEqualTo(89);

    }

    @Test
    void isPrimeUsingRecursion() {
        assertThat(recordService.isPrimeUsingRecursion(293, 2)).isTrue();

        assertThat(recordService.isPrimeUsingRecursion(5236, 2)).isFalse();


    }

    @Test
    void modMethod() {
        Integer[] result = recordService.modMethod(2,10);
        assertThat(result.length).isEqualTo(4);
        assertThat(result[result.length-1]).isEqualTo(7);
        assertThat(result[0]).isEqualTo(2);
        assertThat(result[result.length-2]).isEqualTo(5);

        Integer[] result1 = recordService.modMethod(6,100);
        assertThat(result1.length).isEqualTo(22);
        assertThat(result1[result1.length-1]).isEqualTo(97);
        assertThat(result1[0]).isEqualTo(7);
        assertThat(result1[result1.length-2]).isEqualTo(89);

    }

    @Test
    void sieveOfEratosthenes() {
        Integer[] result = recordService.sieveOfEratosthenes(2,10);
        assertThat(result.length).isEqualTo(4);
        assertThat(result[result.length-1]).isEqualTo(7);
        assertThat(result[0]).isEqualTo(2);
        assertThat(result[result.length-2]).isEqualTo(5);

        Integer[] result1 = recordService.sieveOfEratosthenes(6,100);
        assertThat(result1.length).isEqualTo(22);
        assertThat(result1[result1.length-1]).isEqualTo(97);
        assertThat(result1[0]).isEqualTo(7);
        assertThat(result1[result1.length-2]).isEqualTo(89);

    }


}