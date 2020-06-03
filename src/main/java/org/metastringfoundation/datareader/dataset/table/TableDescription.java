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

package org.metastringfoundation.datareader.dataset.table;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.metastringfoundation.datareader.dataset.table.csv.FieldDescriptionNormalizer;
import org.metastringfoundation.datareader.helpers.FileManager;
import org.metastringfoundation.datareader.helpers.Jsonizer;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

public class TableDescription {
    public static TableDescription fromPath(String path) throws IOException {
        String description = FileManager.getFileContentsAsString(path);
        return fromString(description);
    }

    public static TableDescription fromString(String jsonString) throws IOException {
        return (TableDescription) Jsonizer.fromJSON(jsonString, TableDescription.class);
    }

    @JsonProperty("fields")
    private List<FieldDescriptionIntelligent> fieldDescriptionList;

    public List<FieldDescription> getFieldDescriptionList() {
        return FieldDescriptionNormalizer.normalize(fieldDescriptionList);
    }

    public void setFieldDescriptionList(List<FieldDescriptionIntelligent> fieldDescriptionList) {
        this.fieldDescriptionList = fieldDescriptionList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TableDescription that = (TableDescription) o;
        return Objects.equals(fieldDescriptionList, that.fieldDescriptionList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(fieldDescriptionList);
    }

    @Override
    public String toString() {
        return "TableDescription{" +
                "fieldDescriptionList=" + fieldDescriptionList +
                '}';
    }
}
