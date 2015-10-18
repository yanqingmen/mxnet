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
 * describe class for MX function
 * @author hzx
 */
public class MXFuncDesc {
    public MXFuncDesc(int usedVarsNum, int scalarsNum, int mutateVarsNum, int typeMask) {
        this.usedVarsNum = usedVarsNum;
        this.scalarsNum = scalarsNum;
        this.mutateVarsNum = mutateVarsNum;
        this.typeMask = typeMask;
    }
    
    /**
     * how many NDArrays to be passed in this used_vars
     */
    public int usedVarsNum;
    /**
     * how many scalar variable is needed
     */
    public int scalarsNum;
    /**
     * how many NDArrays to be passed in as mutate_vars
     */
    public int mutateVarsNum;
    /**
     * the type maske of this function
     */
    public int typeMask;
}
