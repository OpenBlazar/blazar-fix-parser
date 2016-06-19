/*
 * Copyright 2016 Wojciech Zankowski.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.blazarquant.bfp.fix.parser.definition.data;

/**
 * @author Wojciech Zankowski
 */
public enum XMLLoaderType {

    QUICKFIX_LOADER("QF");

    private final String tag;

    XMLLoaderType(String tag) {
        this.tag = tag;
    }

    public String getTag() {
        return tag;
    }

    public static XMLLoaderType getXMLLoaderTypeFromTag(String tag) {
        for (XMLLoaderType loaderType : values()) {
            if (loaderType.getTag().equals(tag)) {
                return loaderType;
            }
        }
        throw new IllegalArgumentException("Illegal XML Loader tag - " + tag + ".");
    }
}
