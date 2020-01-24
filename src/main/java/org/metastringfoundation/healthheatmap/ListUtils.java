package org.metastringfoundation.healthheatmap;

import java.util.ArrayList;
import java.util.List;

public class ListUtils <T> {
    public static <T> List<List<T>> transpose(List<List<T>> listToTranspose) {
        List<List<T>> tranposedList = new ArrayList<>();
        for (int row = 0; row < listToTranspose.size(); row++) {
            List<T> currentRow = listToTranspose.get(row);
            for (int column = 0; column < currentRow.size(); column++) {
                T currentValue = listToTranspose.get(row).get(column);
                List<T> targetRow;
                try {
                    targetRow = tranposedList.get(column);
                    targetRow.add(currentValue);
                } catch (IndexOutOfBoundsException e) {
                    targetRow = new ArrayList<>();
                    targetRow.add(currentValue);
                    tranposedList.add(targetRow);
                }
            }
        }

        return tranposedList;
    }
}
