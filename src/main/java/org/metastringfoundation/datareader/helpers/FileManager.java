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

import org.apache.commons.io.IOUtils;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileManager {
    public static Reader getFileReader(Path nioPath) throws FileNotFoundException {
        String path = nioPath.toString();
        return new FileReader(path);
    }

    public static Path getPathFromString(String path) {
        return Paths.get(path);
    }

    public static String getFileContentsAsString(String path) throws IOException {
        return IOUtils.toString(new FileInputStream(path), StandardCharsets.UTF_8);
    }
}
