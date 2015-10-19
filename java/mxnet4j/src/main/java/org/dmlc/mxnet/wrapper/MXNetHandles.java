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

import org.dmlc.mxnet.wrapper.util.MXNetError;

/**
 * Handles for MXNet 
 * @author hzx
 */
public class MXNetHandles {
    /**
     * Base class for handles
     */
    public static abstract class BaseHandle{
        protected boolean isInit = false;  //a handle can be used to do processing only if isInit=true
        protected long[] handle = new long[1];
        protected String name = null; //name of this handle
        
        public long getHandle() throws MXNetError {
            if(!isInit) {
                throw new MXNetError("a handle could be used only after it is initialied");
            }
            return handle[0];
        }
        
        public void setHandle(long handle) throws MXNetError {
            if(isInit) {
                throw new MXNetError("can not set another handle info for an initialized handle");
            }
            this.handle[0] = handle;
            this.setInit();
        }
        
        /**
         * return a handle[] with length=1, used as a pointer to native funcs
         * @return 
         */
        public long[] getHandleRef() {
            return handle;
        }
        
        public void setInit() {
            isInit = true;
        }
        
        public boolean isInit() {
            return isInit;
        }
        
        public String getName() {
            return name;
        }
        
        public void setName(String name) {
            this.name = name;
        }
        
        @Override
        protected void finalize(){
            delete();
        }
    
        public abstract void delete();
    }
    
    /**
     * handle for NDArray
     */
    public static class NDArrayHandle extends BaseHandle {
        @Override
        public synchronized void delete() {
            if(this.handle[0]!=0){
                MXNetJNI.MXNDArrayFree(this.handle[0]);
                this.handle[0] = 0;
                this.isInit = false;
            }
        }    
    }
    
    /**
     * handle for Function
     */
    public static class FunctionHandle extends BaseHandle {
        @Override
        public void delete() {
            //nothing need to free this handle
            this.handle[0] = 0;
            this.isInit = false;
        }
    }
    
    /**
     * handle for SymbolCreator 
     */
    public static class AtomicSymbolCreator extends BaseHandle {
        @Override
        public void delete() {
            //nothing need to free this handle
            this.handle[0] = 0;
            this.isInit = false;
        }
    }
    
    /**
     * handle for Symbol
     */
    public static class SymbolHandle extends BaseHandle {
        @Override
        public void delete() {
            MXNetJNI.MXSymbolFree(this.handle[0]);
            this.handle[0] = 0;
            this.isInit = false;
        }        
    }
    
    /**
     * handle for Executor
     */
    public static class ExecutorHandle extends BaseHandle {
        @Override
        public void delete() {
            //nothing need to free for this handle
            this.handle[0] = 0;
            this.isInit = false;
        }        
    }
    
    /**
     * handle for DataIterCreator
     */
    public static class DataIterCreator extends BaseHandle {
        @Override
        public void delete() {
            //nothing need to free for this handle
            this.handle[0] = 0;
            this.isInit = false;
        }       
    }
    
    /**
     * handle for DataIter
     */
    public static class DataIterHandle extends BaseHandle {
        @Override
        public void delete() {
            MXNetJNI.MXDataIterFree(this.handle[0]);
            this.handle[0] = 0;
            this.isInit = false;
        }        
    }
    
    /**
     * handle for KVStore
     */
    public static class KVStoreHandle extends BaseHandle {
        @Override
        public void delete() {
            MXNetJNI.MXKVStoreFree(this.handle[0]);
            this.handle[0] = 0;
            this.isInit = false;
        }       
    }
    
    
    //util funcs
    
    /**
     * transfer handle array to long array, which could be pass to the native funcs
     * @param <T> hanlde classes extends BaseHandle
     * @param handles handle array
     * @return long array
     * @throws org.dmlc.mxnet.wrapper.util.MXNetError
     */
    public static <T extends BaseHandle> long[] transferHandleArray(T[] handles) throws MXNetError {
        long[] args = new long[handles.length];
        for(int i=0; i<handles.length; i++) {
            args[i] = handles[i].getHandle();
        }
        return args;
    }
 }
