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
package org.dmlc.mxnet.util;

import java.util.HashMap;
import java.util.Map;
import org.dmlc.mxnet.Function;
import org.dmlc.mxnet.wrapper.MXNet;
import org.dmlc.mxnet.wrapper.MXNetHandles.FunctionHandle;
import org.dmlc.mxnet.wrapper.util.MXNetError;

/**
 * util class for mxnet function
 * @author hzx
 */
public class FunctionUtil {
    /**
     * create a <name, func> map to store all mxnet functions
     * @return
     * @throws MXNetError 
     */
    public static Map<String, Function> createFuncs() throws MXNetError {
        FunctionHandle[] funcs = MXNet.MXListFunctions();
        Map<String, Function> funcMap = new HashMap<>();
        for(FunctionHandle handle : funcs) {
            Function func = new Function(handle);
            String name = func.getInfo().name;
            funcMap.put(name, func);
        }
        
        return funcMap;
    }
}
