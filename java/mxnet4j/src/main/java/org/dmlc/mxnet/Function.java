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
import org.dmlc.mxnet.wrapper.MXNet;
import org.dmlc.mxnet.wrapper.MXNetHandles.*;
import org.dmlc.mxnet.wrapper.util.MXFuncDesc;
import org.dmlc.mxnet.wrapper.util.MXFuncInfo;
import org.dmlc.mxnet.wrapper.util.MXNetError;


/**
 * Function class for mxnet
 * @author hzx
 */
public class Function{
    private static final Log logger = LogFactory.getLog(Function.class);
    /**
     * map which stored all mxnet functions with key is the name of function
     */
    static Map<String, Function> funcMap;
    
    static {
        try {
            funcMap = createFuncs();
        } catch (MXNetError ex) {
            logger.error(ex);
            System.exit(0);
        }
    }
    
    FunctionHandle handle;
    MXFuncDesc desc;
    MXFuncInfo info;
    
    public Function(FunctionHandle handle) throws MXNetError {
        this.handle = handle;
        this.desc = MXNet.MXFuncDescribe(handle);
        this.info = MXNet.MXFuncGetInfo(handle);
    }
    
    /**
     * get function describe
     * @return 
     */
    public MXFuncDesc getDesc() {
        return desc;
    }
    
    /**
     * get function info
     * @return 
     */
    public MXFuncInfo getInfo() {
        return info;
    }
    
    /**
     * invoke function with given params
     * @param params key: varName; value: varValue
     * @throws MXNetError
     */
    public void invoke(Map<String, Object> params) throws MXNetError {
        NDArrayHandle[] usedVars = new NDArrayHandle[desc.usedVarsNum];
        int varIndex = 0;
        float[] scalars = new float[desc.scalarsNum];
        int scaIndex = 0;
        NDArrayHandle[] mutateVars = new NDArrayHandle[desc.mutateVarsNum];
        int mutIndex = 0;
        for(int i=0; i<info.argNames.length; i++) {
            String varName = info.argNames[i];
            if(varName.equals("out")){
                mutateVars[mutIndex] = (NDArrayHandle) params.get(varName);
                mutIndex++;
                continue;
            }
            String type = info.typeInfos[i];
            if(type.equals("real_t")){
                scalars[scaIndex] = (float) params.get(varName);
                scaIndex++;
            }
            if(type.equals("NDArray")) {
                usedVars[varIndex] = (NDArrayHandle) params.get(varName);
            }
        }
        
        MXNet.MXFuncInvoke(handle, usedVars, scalars, mutateVars);
    }
    
    public static void set(float src, NDArray out) throws MXNetError {
        Map<String, Object> params = new HashMap<>();
        params.put("out", out.handle);
        params.put("src", src);
        funcMap.get("_set_value").invoke(params);
    }
    
    public static void plus(NDArray lhs, NDArray rhs, NDArray out) throws MXNetError {
        Map<String, Object> params = new HashMap<>();
        params.put("out", out.handle);
        params.put("lhs", lhs.handle);
        params.put("rhs", rhs.handle);
        funcMap.get("_plus").invoke(params);
    }
    
    public static void minus(NDArray lhs, NDArray rhs, NDArray out) throws MXNetError {
        Map<String, Object> params = new HashMap<>();
        params.put("out", out.handle);
        params.put("lhs", lhs.handle);
        params.put("rhs", rhs.handle);
        funcMap.get("_minus").invoke(params);
    }
    
    public static void mul(NDArray lhs, NDArray rhs, NDArray out) throws MXNetError {
        Map<String, Object> params = new HashMap<>();
        params.put("out", out.handle);
        params.put("lhs", lhs.handle);
        params.put("rhs", rhs.handle);
        funcMap.get("_mul").invoke(params);
    }
    
    public static void div(NDArray lhs, NDArray rhs, NDArray out) throws MXNetError {
        Map<String, Object> params = new HashMap<>();
        params.put("out", out.handle);
        params.put("lhs", lhs.handle);
        params.put("rhs", rhs.handle);
        funcMap.get("_div").invoke(params);
    }
    
    public static void dot(NDArray lhs, NDArray rhs, NDArray out) throws MXNetError {
        Map<String, Object> params = new HashMap<>();
        params.put("out", out.handle);
        params.put("lhs", lhs.handle);
        params.put("rhs", rhs.handle);
        funcMap.get("dot").invoke(params);
    }
    
    public static void plusScalar(NDArray lhs, float rhs, NDArray out) throws MXNetError {
        Map<String, Object> params = new HashMap<>();
        params.put("out", out.handle);
        params.put("lhs", lhs.handle);
        params.put("rhs", rhs);
        funcMap.get("_plus_scalar").invoke(params);
    }
    
    public static void minusScalar(NDArray lhs, float rhs, NDArray out) throws MXNetError {
        Map<String, Object> params = new HashMap<>();
        params.put("out", out.handle);
        params.put("lhs", lhs.handle);
        params.put("rhs", rhs);
        funcMap.get("_minus_scalar").invoke(params);
    }
    
    public static void mulScalar(NDArray lhs, float rhs, NDArray out) throws MXNetError {
        Map<String, Object> params = new HashMap<>();
        params.put("out", out.handle);
        params.put("lhs", lhs.handle);
        params.put("rhs", rhs);
        funcMap.get("_mul_scalar").invoke(params);
    }
    
    public static void divScalar(NDArray lhs, float rhs, NDArray out) throws MXNetError {
        Map<String, Object> params = new HashMap<>();
        params.put("out", out.handle);
        params.put("lhs", lhs.handle);
        params.put("rhs", rhs);
        funcMap.get("_div_scalar").invoke(params);
    }
    
    public static void rminusScalar(NDArray lhs, float rhs, NDArray out) throws MXNetError {
        Map<String, Object> params = new HashMap<>();
        params.put("out", out.handle);
        params.put("lhs", lhs.handle);
        params.put("rhs", rhs);
        funcMap.get("_rminus_scalar").invoke(params);
    }
    
    public static void rdivScalar(NDArray lhs, float rhs, NDArray out) throws MXNetError {
        Map<String, Object> params = new HashMap<>();
        params.put("out", out.handle);
        params.put("lhs", lhs.handle);
        params.put("rhs", rhs);
        funcMap.get("_rdiv_scalar").invoke(params);
    }
    
    public static void copyTo(NDArray src, NDArray out) throws MXNetError {
        Map<String, Object> params = new HashMap<>();
        params.put("out", out.handle);
        params.put("src", src.handle);
        funcMap.get("_copyto").invoke(params);
    }
    
    public static void randomUniform(NDArray out) throws MXNetError {
        Map<String, Object> params = new HashMap<>();
        params.put("out", out.handle);
        funcMap.get("_random_uniform").invoke(params);
    }
    
    public static void randomGaussian(NDArray out) throws MXNetError {
        Map<String, Object> params = new HashMap<>();
        params.put("out", out.handle);
        funcMap.get("_random_gaussian").invoke(params);
    }
    
    public static void clip(NDArray src, float min, float max, NDArray out) throws MXNetError {
        Map<String, Object> params = new HashMap<>();
        params.put("out", out.handle);
        params.put("src", src.handle);
        params.put("a_min", min);
        params.put("a_max", max);
        funcMap.get("clip").invoke(params);
    }
    
    /**
     * create a <name, func> map to store all mxnet functions
     * @return
     * @throws MXNetError 
     */
    private static Map<String, Function> createFuncs() throws MXNetError {
        FunctionHandle[] funcs = MXNet.MXListFunctions();
        Map<String, Function> localFuncMap = new HashMap<>();
        for(FunctionHandle handle : funcs) {
            Function func = new Function(handle);
            String name = func.getInfo().name;
            localFuncMap.put(name, func);
        }        
        return localFuncMap;
    }
}
