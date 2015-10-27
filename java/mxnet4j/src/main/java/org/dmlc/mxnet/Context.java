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
package org.dmlc.mxnet;

import java.util.HashMap;
import java.util.Map;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Context management API of mxnet.
 * @author hzx
 */
public class Context {
    private static final Log logger = LogFactory.getLog(Context.class);
    //static variables
    static Context defaultContext = new Context("cpu", 0);
    static Map<Integer, String> devTypeId2Str = new HashMap<Integer, String>() {
        {
            put(1, "cpu");
            put(2, "gpu");
        }
    };
    static Map<String, Integer> str2DevTypeId = new HashMap<String, Integer>() {
        {
            put("cpu", 1);
            put("gpu", 2);
        }
    };
    
    public static Context defaultContext() {
        return defaultContext;
    }
    
    /**
     * Return a CPU context.
     * @param devId
     * @return 
     */
    public static Context cpu(int devId) {
        return new Context("cpu", devId);
    }
    
    /**
     * Return a GPU context.
     * @param devId
     * @return 
     */
    public static Context gpu(int devId) {
        return new Context("gpu", devId);
    }
    
    //device type id
    int devTypeId;
    //device id
    int devId;   
    Context oldContext = null;
    
    public Context(int devType, int devId) {
        this.devTypeId = devType;
        this.devId = devId;
    }
    
    public Context(String devType, int devId){
        this.devTypeId = str2DevTypeId.get(devType);
        this.devId = devId;
    }
    
    public Context(Context context) {
        this.devTypeId = context.devTypeId;
        this.devId = context.devId;
    }
    
    /**
     * get device type
     * @return device type of current context.
     */
    public String getDevType() {
        return devTypeId2Str.get(devTypeId);
    }
    
    @Override
    public String toString() {
        return String.format("%s(%d)", this.getDevType(), this.devId);
    }
}
