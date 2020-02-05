package org.metastringfoundation.healthheatmap;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

class ListUtilsTest {

    @Test
    void transposeEqualOnes() {
        List<List<Integer>> inputList = Arrays.asList(
                Arrays.asList(1, 2, 3),
                Arrays.asList(4, 5, 6),
                Arrays.asList(7, 8, 9)
        );

        List<List<Integer>> expectedTranspose = Arrays.asList(
                Arrays.asList(1, 4, 7),
                Arrays.asList(2, 5, 8),
                Arrays.asList(3, 6, 9)
        );

        assertEquals(expectedTranspose, ListUtils.transpose(inputList));
    }

    @Test
    void transposeUnequalOnes() {
        List<List<Integer>> inputList = Arrays.asList(
                Arrays.asList(1, 2, 3, 4),
                Arrays.asList(4, 5),
                Arrays.asList(7, 8, 9)
        );

        List<List<Integer>> expectedTranspose = Arrays.asList(
                Arrays.asList(1, 4, 7),
                Arrays.asList(2, 5, 8),
                Arrays.asList(3, 9),
                Arrays.asList(4)
        );

        assertEquals(expectedTranspose, ListUtils.transpose(inputList));
    }
}