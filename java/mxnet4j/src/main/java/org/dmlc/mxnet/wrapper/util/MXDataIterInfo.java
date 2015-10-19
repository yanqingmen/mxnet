/*
 Copyright (c) 2015 by Contributors 

 Licensed under the Apache License, Version 2.0 (the "License");
 you may not use this file except in compliance with the License.
 You may obtain a copy of the License at
    
 http://www.apache.org/licenses/LICENSE-2.0

 Unless required by applicable law or agreed to in writing, software
 distributed under the License is distributed on an "AS IS" BASIS,
 WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 See the License for the specific language governing permissions and
 limitations under the License.
 */
package org.dmlc.mxnet.wrapper.util;

/**
 * detailed information about data iterator.
 * @author hzx
 */
public class MXDataIterInfo {
    /**
     * detailed information about data iterator.
     * @param name name of the creator.
     * @param description description of the dataiter
     * @param argNames Name of the arguments.
     * @param argTypeInfos Type informations about the arguments.
     * @param argDescriptions Description information about the arguments.
     */
    public MXDataIterInfo(String name, String description, String[] argNames, 
            String[] argTypeInfos, String[] argDescriptions) {
        this.name = name;
        this.description = description;
        this.argNames = argNames;
        this.argTypeInfos = argTypeInfos;
        this.argDescriptions = argDescriptions;
    }
    
    /**
     * name of the creator.
     */
    public String name;
    /**
     * description of the dataiter
     */
    public String description;
    /**
     * Name of the arguments.
     */
    public String[] argNames;
    /**
     * Type informations about the arguments.
     */
    public String[] argTypeInfos;
    /**
     *  Description information about the arguments.
     */
    public String[] argDescriptions;
}
