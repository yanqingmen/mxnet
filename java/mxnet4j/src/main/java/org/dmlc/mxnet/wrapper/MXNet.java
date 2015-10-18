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

import org.dmlc.mxnet.wrapper.MXNetHandles.*;
import org.dmlc.mxnet.wrapper.util.MXAtomicSymbolInfo;
import org.dmlc.mxnet.wrapper.util.MXFuncDesc;
import org.dmlc.mxnet.wrapper.util.MXFuncInfo;
import org.dmlc.mxnet.wrapper.util.MXNetError;
/**
 * util java class for MXNetJNI
 * @author hzx
 */


public class MXNet {
    /**
     * check result of native func
     * @param ret
     * @throws MXNetError 
     */
    public static void HandleError(int ret) throws MXNetError {
        if(ret != 0) {
            throw new MXNetError(MXGetLastError());
        }
    }
    
    /**
     * return str message of the last error
     * all function in this file will return 0 when success
     * and -1 when an error occured,
     * MXGetLastError can be called to retrieve the error
     * 
     * this function is threadsafe and can be called by different thread
     * @return error info
     */
    public static String MXGetLastError() {
        return MXNetJNI.MXGetLastError();
    }
    
    /**
     * Notify the engine about a shutdown,
     * This can help engine to print less messages into display.
     *
     *  User do not have to call this function.
     * 
     * @throws org.dmlc.mxnet.wrapper.util.MXNetError
     */
    public static void MXNotifyShutdown() throws MXNetError {
        int ret = MXNetJNI.MXNotifyShutdown();
        HandleError(ret);
    }
    
    /**
     * create a NDArray handle that is not initialized
     *  can be used to pass in as mutate variables
     *  to hold the result of NDArray
     * @return NDArrayHandle
     * @throws MXNetError 
     */
    public static NDArrayHandle MXNDArrayCreateNone() throws MXNetError {
        NDArrayHandle handle = new NDArrayHandle();
        int ret = MXNetJNI.MXNDArrayCreateNone(handle.getHandleRef());
        HandleError(ret);        
        handle.setInit();
        return handle;
    }
    
    /**
     * create a NDArray with specified shape
     * @param shape array of int  to specifiy the shape
     * @param dev_type device type, specify device we want to take
     * @param dev_id device id of the specific device
     * @param delay_alloc whether to delay allocation until the narray is first mutated
     * @return NDArrayHandle
     * @throws MXNetError 
     */
    public static NDArrayHandle MXNDArrayCreate(int[] shape, int dev_type, int dev_id, int delay_alloc) throws MXNetError {
        NDArrayHandle handle = new NDArrayHandle();
        int ret = MXNetJNI.MXNDArrayCreate(shape, dev_id, dev_type, dev_id, delay_alloc, handle.getHandleRef());
        HandleError(ret);        
        handle.setInit();
        return handle;
    }
    
    /**
     * create a NDArray handle that is loaded from raw bytes. 
     * @param buf String for bytes array
     * @return NDArrayHandle
     * @throws MXNetError 
     */
    public static NDArrayHandle MXNDArrayLoadFromRawBytes(String buf) throws MXNetError {
        NDArrayHandle handle = new NDArrayHandle();
        int ret = MXNetJNI.MXNDArrayLoadFromRawBytes(buf.getBytes(), handle.getHandleRef());
        HandleError(ret);
        handle.setInit();
        return handle;
    }
    
    /**
     * save the NDArray into raw bytes.
     * @param handle the NDArray handle
     * @return String for bytes array
     * @throws MXNetError 
     */
    public static String MXNDArraySaveRawBytes(NDArrayHandle handle) throws MXNetError {
        String[] out_buf = new String[1];
        int ret = MXNetJNI.MXNDArraySaveRawBytes(handle.getHandle(), out_buf);
        HandleError(ret);
        return out_buf[0];
    }
    
    /**
     * save array of narray to given file
     * @param fname name of the file
     * @param handles the array of NDArrayHandles to be save
     * @param keys the name of the NDArray, optional, can be NULL 
     * @throws MXNetError 
     */
    public static void MXNDArraySave(String fname, NDArrayHandle[] handles, String[] keys) throws MXNetError {
        long[] args = MXNetHandles.transferHandleArray(handles);
        int ret = MXNetJNI.MXNDArraySave(fname, args, keys);
        HandleError(ret);
    }
    
    /**
     * Load list of narray from the file
     * @param fname name of the file
     * @return array of NDArrayHandle
     * @throws MXNetError 
     */
    public static NDArrayHandle[] MXNDArrayLoad(String fname) throws MXNetError {
        long[][] out_arr = new long[1][];
        String[][] out_names = new String[1][];
        int ret = MXNetJNI.MXNDArrayLoad(fname, out_arr, out_names);
        HandleError(ret);
        NDArrayHandle[] handles = new NDArrayHandle[out_arr[0].length];
        for(int i=0; i>handles.length; i++) {
            handles[i] = new NDArrayHandle();
            handles[i].setHandle(out_arr[0][i]);
            if(out_names[0] != null && out_names[0].length>i) {
                handles[i].setName(out_names[0][i]);
            }
        }
        return handles;
    }
    
    /**
     * Perform a synchronize copy from a continugous CPU memory region.
     * 
     *  This function will call WaitToWrite before the copy is performed.
     *  This is useful to copy data from existing memory region that are
     *  not wrapped by NDArray(thus dependency not being tracked).
     * 
     * @param handle the NDArrayHandle 
     * @param data the data source to copy from
     * @throws MXNetError 
     */
    public static void MXNDArraySyncCopyFromCPU(NDArrayHandle handle, float[] data) throws MXNetError {
        int ret = MXNetJNI.MXNDArraySyncCopyFromCPU(handle.getHandle(), data);
        HandleError(ret);
    }
    
    /**
     * Perform a synchronize copyto a continugous CPU memory region.
     * 
     * This function will call WaitToRead before the copy is performed.
     * This is useful to copy data from existing memory region that are
     * not wrapped by NDArray(thus dependency not being tracked).
     * @param handle NDArray handle
     * @param size the memory size we want to copy into.
     * @return result data
     * @throws MXNetError 
     */
    public static float[] MXNDArraySyncCopyToCPU(NDArrayHandle handle, int size) throws MXNetError {
        //to do the size transfer between memory size and array size?
        float[] data = new float[size]; //?
        int ret = MXNetJNI.MXNDArraySyncCopyToCPU(handle.getHandle(), data, size);
        HandleError(ret);
        return data;
    }
    
    /**
     * Wait until all the pending writes with respect NDArray are finished.
     * Always call this before read data out synchronizely.
     * @param handle the NDArray handle
     * @throws MXNetError 
     */
    public static void MXNDArrayWaitToRead(NDArrayHandle handle) throws MXNetError {
        int ret = MXNetJNI.MXNDArrayWaitToRead(handle.getHandle());
        HandleError(ret);
    }
    
    /**
     * Wait until all the pending read/write with respect NDArray are finished.
     * Always call this before write data into NDArray synchronizely.
     * @param handle the NDArray handle
     * @throws MXNetError 
     */
    public static void MXNDArrayWaitToWrite(NDArrayHandle handle) throws MXNetError {
        int ret = MXNetJNI.MXNDArrayWaitToWrite(handle.getHandle());
        HandleError(ret);
    }
    
    /**
     * wait until all delayed operations in the system is completed
     * @throws MXNetError 
     */
    public static void MXNDArrayWaitAll() throws MXNetError {
        int ret = MXNetJNI.MXNDarrayWaitAll();
        HandleError(ret);
    }
    
    /**
     * Slice the NDArray along axis 0.
     * @param handle the handle to the narraya
     * @param slice_begin The beginning index of slice
     * @param slice_end The ending index of slice
     * @return The NDArrayHandle of sliced NDArray
     * @throws MXNetError 
     */
    public static NDArrayHandle MXNDArraySlice(NDArrayHandle handle, int slice_begin, int slice_end) throws MXNetError {
        NDArrayHandle tHandle = new NDArrayHandle();
        int ret = MXNetJNI.MXNDArraySlice(handle.getHandle(), slice_begin, slice_end, tHandle.getHandleRef());
        HandleError(ret);
        return tHandle;
    }
    
    /**
     * get the shape of the array
     * @param handle the handle to the narray
     * @return int array for shape info
     * @throws MXNetError 
     */
    public static int[] MXNDArrayGetShape(NDArrayHandle handle) throws MXNetError {
        int[][] out_pdata = new int[1][];
        int ret = MXNetJNI.MXNDArrayGetShape(handle.getHandle(), out_pdata);
        HandleError(ret);
        return out_pdata[0];
    }
    
    /**
     * get the content of the data in NDArray
     * @param handle the handle to the narray
     * @return
     * @throws MXNetError 
     */
    public static float[] MXNDArrayGetData(NDArrayHandle handle) throws MXNetError {
        float[][] out_pdata = new float[1][];
        int ret = MXNetJNI.MXNDArrayGetData(handle.getHandle(), out_pdata);
        HandleError(ret);
        return out_pdata[0];
    }
    
    /**
     * get the context of the NDArray
     * @param handle the handle to the narray
     * @return int array with length=2, array[0]=device type, array[1]=device id
     * @throws MXNetError 
     */
    public static int[] MXNDArrayGetContext(NDArrayHandle handle) throws MXNetError {
        int[] out_dev_type = new int[1];
        int[] out_dev_id = new int[1];
        int ret = MXNetJNI.MXNDArrayGetContext(handle.getHandle(), out_dev_type, out_dev_id);
        HandleError(ret);
        return new int[] {out_dev_type[0], out_dev_id[0]};        
    }
    
    //--------------------------------
    // Part 2: functions on NDArray
    //--------------------------------
    
    /**
     * list all the available functions handles
     * most user can use it to list all the needed functions
     * @return function handle array
     * @throws MXNetError 
     */
    public static FunctionHandle[] MXListFunctions() throws MXNetError {
        long[][] out_array = new long[1][];
        int ret = MXNetJNI.MXListFunctions(out_array);
        HandleError(ret);
        FunctionHandle[] handles = new FunctionHandle[out_array[0].length];
        for(int i=0; i<handles.length; i++) {
            handles[i] = new FunctionHandle();
            handles[i].setHandle(out_array[0][i]);
        }
        return handles;
    }
    
    /**
     * get the function handle by name
     * @param name the name of the function
     * @return the corresponding function handle
     * @throws MXNetError 
     */
    public static FunctionHandle MXGetFunction(String name) throws MXNetError {
        FunctionHandle handle = new FunctionHandle();
        int ret = MXNetJNI.MXGetFunction(name, handle.getHandleRef());
        HandleError(ret);
        return handle;
    }
    
    /**
     * Get the information of the function handle.
     * @param func The function handle.
     * @return MXFuncInfo
     * @throws MXNetError 
     */
    public static MXFuncInfo MXFuncGetInfo(FunctionHandle func) throws MXNetError {
        String[] name = new String[1];
        String[] description = new String[1];
        String[][] arg_names = new String[1][];
        String[][] arg_type_infos = new String[1][];
        String[][] arg_descriptions = new String[1][];
        
        int ret = MXNetJNI.MXFuncGetInfo(func.getHandle(), name, description, arg_names,
                arg_type_infos, arg_descriptions);
        HandleError(ret);
        MXFuncInfo funcInfo = new MXFuncInfo(name[0], description[0], arg_names[0], 
                arg_type_infos[0], arg_descriptions[0]);
        
        return funcInfo;
    }
    
    /**
     * get the argument requirements of the function
     * @param func input function handle
     * @return MXFuncDesc
     * @throws MXNetError 
     */
    public static MXFuncDesc MXFuncDescribe(FunctionHandle func) throws MXNetError {
        int[] num_use_vars = new int[1];
        int[] num_scalars = new int[1];
        int[] num_mutate_vars = new int[1];
        int[] type_mask = new int[1];
        
        int ret = MXNetJNI.MXFuncDescribe(func.getHandle(), num_use_vars, num_scalars, 
                num_mutate_vars, type_mask);
        HandleError(ret);
        MXFuncDesc funcDesc = new MXFuncDesc(num_use_vars[0], num_scalars[0],
                num_mutate_vars[0], type_mask[0]);
        
        return funcDesc;
    }
    
    /**
     * nvoke a function, the array size of passed in arguments
     * must match the values in the func describe
     * @param func the function
     * @param use_vars the normal arguments passed to function
     * @param scalar_args the scalar qarguments
     * @param mutate_vars the mutate arguments
     * @throws MXNetError 
     */
    public static void MXFuncInvoke(FunctionHandle func, NDArrayHandle[] use_vars, 
            float[] scalar_args, NDArrayHandle[] mutate_vars) throws MXNetError {
        int ret = MXNetJNI.MXFuncInvoke(func.getHandle(), MXNetHandles.transferHandleArray(use_vars), 
                scalar_args, MXNetHandles.transferHandleArray(mutate_vars));
        HandleError(ret);
    }
    
    /*--------------------------------------------
     * Part 3: symbolic configuration generation
     *--------------------------------------------
    */
    
    /**
     * list all the available AtomicSymbolEntry
     * @return the output AtomicSymbolCreator array
     * @throws MXNetError 
     */
    public static AtomicSymbolCreator[] MXSymbolListAtomicSymbolCreators() throws MXNetError {
        long[][] out_array = new long[1][];
        int ret = MXNetJNI.MXSymbolListAtomicSymbolCreators(out_array);
        HandleError(ret);
        AtomicSymbolCreator[] creators = new AtomicSymbolCreator[out_array[0].length];
        for(int i=0; i<creators.length; i++) {
            creators[i] = new AtomicSymbolCreator();
            creators[i].setHandle(out_array[0][i]);
        }
        return creators;
    }
    
    /**
     * Get the detailed information about atomic symbol.
     * @param creator he AtomicSymbolCreator.
     * @return MXAtomicSymbolInfo
     * @throws org.dmlc.mxnet.wrapper.util.MXNetError
     */
    public static MXAtomicSymbolInfo MXSymbolGetAtomicSymbolInfo(AtomicSymbolCreator creator)
            throws MXNetError {
        
        String[] name = new String[1];
        String[] description = new String[1];
        String[][] arg_names = new String[1][];
        String[][] arg_type_infos = new String[1][];
        String[][] arg_descriptions = new String[1][];
        String[] key_var_num_args = new String[1];
        
        int ret = MXNetJNI.MXSymbolGetAtomicSymbolInfo(creator.getHandle(), name, description, arg_names,
                arg_type_infos, arg_descriptions, key_var_num_args);
        HandleError(ret);
        
        MXAtomicSymbolInfo symbolInfo = new MXAtomicSymbolInfo(name[0], description[0], 
                arg_names[0], arg_type_infos[0], arg_descriptions[0], key_var_num_args[0]);
        return symbolInfo;
    }
    
    /**
     * Create an AtomicSymbol.
     * @param creator the AtomicSymbolCreator
     * @param keys the keys to the params
     * @param vals the vals of the params
     * @return SymbolHandle
     * @throws MXNetError 
     */
    public static SymbolHandle MXSymbolCreateAtomicSymbol(AtomicSymbolCreator creator,
            String[] keys, String[] vals) throws MXNetError {
        SymbolHandle handle = new SymbolHandle();
        int ret = MXNetJNI.MXSymbolCreateAtomicSymbol(creator.getHandle(), keys, vals, handle.getHandleRef());
        HandleError(ret);
        
        return handle;
    }
    
    /**
     * Create a Variable Symbol.
     * @param name name of the variable
     * @return SymbolHandle
     * @throws MXNetError 
     */
    public static SymbolHandle MXSymbolCreateVariable(String name) throws MXNetError {
        SymbolHandle out = new SymbolHandle();
        int ret = MXNetJNI.MXSymbolCreateVariable(name, out.getHandleRef());
        HandleError(ret);
        
        return out;
    }
    
    
}
