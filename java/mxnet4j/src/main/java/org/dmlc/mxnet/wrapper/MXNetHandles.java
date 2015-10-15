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
package org.dmlc.mxnet.wrapper;

/**
 * Handles class for MXNet 
 * @author hzx
 */
public class MXNetHandles {
    /**
     * Base class for handles
     */
    public static abstract class BaseHandle{
        protected boolean isInit = false;  //a handle can be used to do processing only if isInit=true
        protected long[] handle = new long[1];
        
        public long[] getHandle() {
            return handle;
        }
        
        public void setInit() {
            isInit = true;
        }
        
        public boolean isInit() {
            return isInit;
        }
        
        @Override
        protected void finalize() {
            delete();
        }
    
        public abstract void delete();
    }
    
    /**
     * handle for NDArray
     */
    public static class NDArrayHandle extends BaseHandle{
        @Override
        public synchronized void delete() {
            if(this.handle[0]!=0){
                MXNetJNI.MXNDArrayFree(this.handle[0]);
                this.handle[0] = 0;
                this.isInit = false;
            }
        }    
    }
    
    
}
