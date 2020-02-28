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

package org.metastringfoundation.healthheatmap.dataset;

import java.util.List;

public interface Table {
    List<List<String>> getTable();

    List<String> getRow(int rowNumber);

    List<String> getColumn(int columnNumber);

    default String getCell(int rowNumber, int columnNumber) {
        return getRow(rowNumber).get(columnNumber);
    }

    int getNumberOfRows();
    int getNumberOfColumns();
}
