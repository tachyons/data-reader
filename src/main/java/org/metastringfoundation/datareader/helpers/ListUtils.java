/*
 *    Copyright 2020 Metastring Foundation
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

package org.metastringfoundation.datareader.helpers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

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
    public static List<String> splitStringToArray(String input, String delimitor) {
        if (input.contains(delimitor)) {
            return Arrays.asList(input.split(delimitor));
        } else {
            return Collections.singletonList(input);
        }
    }

    public static List<Long> stringArrayToLong(List<String> input) {
        return input.stream()
                .map(Long::parseLong)
                .collect(Collectors.toList());
    }

}
