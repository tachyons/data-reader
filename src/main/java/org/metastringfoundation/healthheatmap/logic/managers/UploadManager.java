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

package org.metastringfoundation.healthheatmap.logic.managers;

import org.metastringfoundation.healthheatmap.entities.Report;
import org.metastringfoundation.healthheatmap.entities.Source;
import org.metastringfoundation.healthheatmap.entities.Upload;
import org.metastringfoundation.healthheatmap.storage.HibernateManager;

import javax.persistence.EntityManager;

public class UploadManager extends DimensionManager {
    public static Upload newUpload(Report report, Source source, EntityManager entityManager) {
        Upload upload = new Upload();
        upload.setReport(report);
        upload.setSource(source);
        entityManager.persist(upload);
        return upload;
    }
}
