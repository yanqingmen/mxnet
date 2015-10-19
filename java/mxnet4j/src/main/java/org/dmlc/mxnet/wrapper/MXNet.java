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
import org.dmlc.mxnet.wrapper.util.MXDataIterInfo;
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
    
    /**
     * Create a Symbol by grouping list of symbols together
     * @param symbols array of symbol handles
     * @return SymbolHandle
     * @throws MXNetError 
     */
    public static SymbolHandle MXSymbolCreateGroup(SymbolHandle[] symbols) throws MXNetError {
        SymbolHandle out = new SymbolHandle();
        int ret = MXNetJNI.MXSymbolCreateGroup(MXNetHandles.transferHandleArray(symbols), 
                out.getHandleRef());
        HandleError(ret);
        
        return out;
    }
    
    /**
     * Load a symbol from a json file.
     * @param fname the file name.
     * @return SymbolHandle
     * @throws MXNetError 
     */
    public static SymbolHandle MXSymbolCreateFromFile(String fname) throws MXNetError {
        SymbolHandle out = new SymbolHandle();
        int ret = MXNetJNI.MXSymbolCreateFromFile(fname, out.getHandleRef());
        HandleError(ret);
        
        return out;
    }
    
    /**
     * Load a symbol from a json string.
     * @param json the json string.
     * @return SymbolHandle
     * @throws MXNetError 
     */
    public static SymbolHandle MXSymbolCreateFromJSON(String json) throws MXNetError {
        SymbolHandle out = new SymbolHandle();
        int ret = MXNetJNI.MXSymbolCreateFromJSON(json, out.getHandleRef());
        HandleError(ret);
        
        return out;
    }
    
    /**
     * Save a symbol into a json file.
     * @param symbol the input symbol.
     * @param fname the file name.
     * @throws MXNetError 
     */
    public static void MXSymbolSaveToFile(SymbolHandle symbol, String fname) throws MXNetError {
        int ret = MXNetJNI.MXSymbolSaveToFile(symbol.getHandle(), fname);
        HandleError(ret);
    }
    
    /**
     * ave a symbol into a json string
     * @param symbol the input symbol.
     * @return output json string.
     * @throws MXNetError 
     */
    public static String MXSymbolSaveToJSON(SymbolHandle symbol) throws MXNetError {
        String[] out = new String[1];
        int ret = MXNetJNI.MXSymbolSaveToJSON(symbol.getHandle(), out);
        HandleError(ret);
        
        return out[0];
    }
    
    /**
     * Copy the symbol to another handle
     * @param symbol the source symbol
     * @return the result symbol of copy
     * @throws MXNetError 
     */
    public static SymbolHandle MXSymbolCopy(SymbolHandle symbol) throws MXNetError {
        SymbolHandle out = new SymbolHandle();
        int ret = MXNetJNI.MXSymbolCopy(symbol.getHandle(), out.getHandleRef());
        HandleError(ret);
        
        return out;
    }
    
    /**
     * Print the content of symbol, used for debug.
     * @param symbol the symbol
     * @return the output string of the printing.
     * @throws MXNetError 
     */
    public static String MXSymbolPrint(SymbolHandle symbol) throws MXNetError {
        String[] out_str = new String[1];
        int ret = MXNetJNI.MXSymbolPrint(symbol.getHandle(), out_str);
        HandleError(ret);
        
        return out_str[0];
    }
    
    /**
     * List arguments in the symbol.
     * @param symbol the symbol
     * @return output string array
     * @throws MXNetError 
     */
    public static String[] MXSymbolListArguments(SymbolHandle symbol) throws MXNetError {
        String[][] out_str_array = new String[1][];
        int ret = MXNetJNI.MXSymbolListArguments(symbol.getHandle(), out_str_array);
        HandleError(ret);
        
        return out_str_array[0];
    }
    
    /**
     * List returns in the symbol.
     * @param symbol the symbol
     * @return output string array
     * @throws MXNetError 
     */
    public static String[] MXSymbolListOutputs(SymbolHandle symbol) throws MXNetError {
        String[][] out_str_array = new String[1][];
        int ret = MXNetJNI.MXSymbolListOutputs(symbol.getHandle(), out_str_array);
        HandleError(ret);
        
        return out_str_array[0];
    }
    
    /**
     * Get a symbol that contains all the internals.
     * @param symbol The symbol
     * @return The output symbol whose outputs are all the internals.
     * @throws MXNetError 
     */
    public static SymbolHandle MXSymbolGetInternals(SymbolHandle symbol) throws MXNetError {
        SymbolHandle out = new SymbolHandle();
        int ret = MXNetJNI.MXSymbolGetInternals(symbol.getHandle(), out.getHandleRef());
        HandleError(ret);
        
        return out;
    }
    
    /**
     * Get index-th outputs of the symbol.
     * @param symbol The source symbol
     * @param index the Index of the output.
     * @return output symbol whose output are  index-th outputs of the source symbol
     * @throws MXNetError 
     */
    public static SymbolHandle MXSymbolGetOutput(SymbolHandle symbol, 
            int index) throws MXNetError {
        SymbolHandle out = new SymbolHandle();
        int ret = MXNetJNI.MXSymbolGetOutput(symbol.getHandle(), index, out.getHandleRef());
        HandleError(ret);
        
        return out;
    }
    
    /**
     * List auxiliary states in the symbol.
     * @param symbol he symbol
     * @return the output string array
     * @throws MXNetError 
     */
    public static String[] MXSymbolListAuxiliaryStates(SymbolHandle symbol) throws MXNetError {
        String[][] out_str_array = new String[1][];
        int ret = MXNetJNI.MXSymbolListAuxiliaryStates(symbol.getHandle(), out_str_array);
        HandleError(ret);
        
        return out_str_array[0];
    }
    
    /**
     * Compose the symbol on other symbols.
     * This function will change the sym hanlde.
     * To achieve function apply behavior, copy the symbol first before apply.
     * @param sym the symbol to apply
     * @param name the name of symbol
     * @param keys key of keyword args
     * @param args arguments to sym
     * @throws MXNetError 
     */
    public static void MXSymbolCompose(SymbolHandle sym, String name, String[] keys, SymbolHandle[] args) throws MXNetError {
        int ret = MXNetJNI.MXSymbolCompose(sym.getHandle(), name, keys, MXNetHandles.transferHandleArray(args));
        HandleError(ret);
    }
    
    /**
     * Get the gradient graph of the symbol
     * @param sym symbol to get gradient
     * @param wrt name of the arguments to get gradient
     * @return symbol that has gradient
     * @throws MXNetError 
     */
    public static SymbolHandle MXSymbolGrad(SymbolHandle sym, String[] wrt) throws MXNetError {
        SymbolHandle out = new SymbolHandle();
        int ret = MXNetJNI.MXSymbolGrad(sym.getHandle(), wrt, out.getHandleRef());
        HandleError(ret);
        
        return out;
    }
    
    /**
     * MXSymbolInferShape is a little complicated, to do ...
    */
    
    //--------------------------------------------
    // Part 4: Executor interface
    //--------------------------------------------
    
    /**
     * Print the content of execution plan, used for debug.
     * @param ex_handle
     * @return 
     * @throws org.dmlc.mxnet.wrapper.util.MXNetError 
     */
    public static String MXExecutorPrint(ExecutorHandle ex_handle) throws MXNetError {
        String[] out_str = new String[1];
        int ret = MXNetJNI.MXExecutorPrint(ex_handle.getHandle(), out_str);
        HandleError(ret);
        
        return out_str[0];
    }
    
    /**
     * Executor forward method
     * @param handle executor handle
     * @param is_train bool value to indicate whether the forward pass is for evaluation
     * @throws MXNetError 
     */
    public static void MXExecutorForward(ExecutorHandle handle, int is_train) throws MXNetError {
        int ret = MXNetJNI.MXExecutorForward(handle.getHandle(), is_train);
        HandleError(ret);
    }
    
    /**
     * Excecutor run backward
     * @param handle execute handle
     * @param head_grads 
     * @throws MXNetError NDArray handle for heads' gradient
     */
    public static void MXExecutorBackward(ExecutorHandle handle, NDArrayHandle[] head_grads) throws MXNetError {
        int ret = MXNetJNI.MXExecutorBackward(handle.getHandle(), MXNetHandles.transferHandleArray(head_grads));
        HandleError(ret);
    }
    
    /**
     * Get executor's head NDArray
     * @param handle executor handle
     * @return output narray handles
     * @throws MXNetError 
     */
    public static NDArrayHandle[] MXExecutorOutputs(ExecutorHandle handle) throws MXNetError {
        long[][] out = new long[1][];
        int ret = MXNetJNI.MXExecutorOutputs(handle.getHandle(), out);
        HandleError(ret);
        
        NDArrayHandle[] handles = new NDArrayHandle[out[0].length];
        for(int i=0; i<handles.length; i++){
            handles[i] = new NDArrayHandle();
            handles[i].setHandle(out[0][i]);
        }
        
        return handles;
    }
    
    /**
     * Generate Executor from symbol
     * @param symbol_handle symbol handle
     * @param dev_type device type
     * @param dev_id device id
     * @param in_args in args array
     * @param arg_grad_store  arg grads handle array
     * @param grad_req_type grad req array
     * @param aux_states auxiliary states array
     * @return
     * @throws MXNetError 
     */
    public static ExecutorHandle MXExecutorBind(SymbolHandle symbol_handle, 
            int dev_type, int dev_id, NDArrayHandle[] in_args, 
            NDArrayHandle[] arg_grad_store, int[] grad_req_type, 
            NDArrayHandle[] aux_states) throws MXNetError {
        ExecutorHandle ex_handle = new ExecutorHandle();
        
        int ret = MXNetJNI.MXExecutorBind(symbol_handle.getHandle() , dev_type,
                dev_id, MXNetHandles.transferHandleArray(in_args), 
                MXNetHandles.transferHandleArray(arg_grad_store), grad_req_type, 
                MXNetHandles.transferHandleArray(aux_states), ex_handle.getHandleRef());
        HandleError(ret);
        
        return ex_handle;
    }
    
    //--------------------------------------------
    // Part 5: IO Interface
    //--------------------------------------------
    
    /**
     * List all the available iterator entries
     * @return the output iteratos entries
     * @throws org.dmlc.mxnet.wrapper.util.MXNetError 
     */
    public static DataIterCreator[] MXListDataIters() throws MXNetError {
        long[][] out_array = new long[1][];
        int ret = MXNetJNI.MXListDataIters(out_array);
        HandleError(ret);
        
        DataIterCreator[] creators = new DataIterCreator[out_array[0].length];
        for(int i=0; i<creators.length; i++) {
            creators[i] = new DataIterCreator();
            creators[i].setHandle(out_array[0][i]);
        }
        return creators;
    }
    
    /**
     * Init an iterator, init with parameters
     * @param handle handle of the iterator creator
     * @param keys parameter keys
     * @param vals parameter values
     * @return DataIterHandle
     * @throws MXNetError 
     */
    public static DataIterHandle MXDataIterCreateIter(
            DataIterCreator handle, String[] keys, String[] vals) throws MXNetError {
        DataIterHandle out = new DataIterHandle();
        int ret = MXNetJNI.MXDataIterCreateIter(handle.getHandle(), 
                keys, vals, out.getHandleRef());
        HandleError(ret);
        
        return out;
    }
    
    /**
     * Get the detailed information about data iterator.
     * @param creator the DataIterCreator.
     * @return MXDataIterInfo
     * @throws MXNetError 
     */
    public static MXDataIterInfo MXDataIterGetIterInfo(DataIterCreator creator) 
            throws MXNetError {
        String[] name = new String[1];
        String[] description = new String[1];
        String[][] arg_names = new String[1][];
        String[][] arg_type_infos = new String[1][];
        String[][] arg_descriptions = new String[1][];
        
        int ret = MXNetJNI.MXDataIterGetIterInfo(
                creator.getHandle(), name, description, 
                arg_names, arg_type_infos, arg_descriptions);
        
        HandleError(ret);
        return new MXDataIterInfo(name[0], description[0], 
                arg_names[0], arg_type_infos[0], arg_descriptions[0]);
    }
    
    /**
     * Move iterator to next position
     * @param handle the handle to iterator
     * @return value of next
     * @throws MXNetError 
     */
    public static int  MXDataIterNext(DataIterHandle handle)
            throws MXNetError {
        int[] out = new int[1];
        int ret = MXNetJNI.MXDataIterNext(handle.getHandle(), out);
        HandleError(ret);
        
        return out[0];
    }
    
    /**
     * Call iterator.Reset
     * @param handle the handle to iterator
     * @throws MXNetError 
     */
    public static void MXDataIterBeforeFirst(DataIterHandle handle)
            throws MXNetError {
        int ret = MXNetJNI.MXDataIterBeforeFirst(handle.getHandle());
        HandleError(ret);
    }
    
    /**
     * Get the handle to the NDArray of underlying data
     * @param handle data iterator handle
     * @return handle to underlying data NDArray
     * @throws MXNetError 
     */
    public static NDArrayHandle MXDataIterGetData(DataIterHandle handle)
            throws MXNetError {
        NDArrayHandle out = new NDArrayHandle();
        int ret = MXNetJNI.MXDataIterGetData(handle.getHandle(), out.getHandleRef());
        HandleError(ret);
        
        return out;
    }
    
    /**
     * Get the padding number in current data batch
     * @param handle data iterator handle
     * @return pad number
     * @throws MXNetError 
     */
    public static int MXDataIterGetPadNum(DataIterHandle handle) throws MXNetError {
        int[] pad = new int[1];
        int ret = MXNetJNI.MXDataIterGetPadNum(handle.getHandle(), pad);
        HandleError(ret);
        
        return pad[0];
    }
    
    /**
     * Get the handle to the NDArray of underlying label
     * @param handle data iterator handle
     * @return handle to underlying label NDArray
     * @throws MXNetError 
     */
    public static NDArrayHandle MXDataIterGetLabel(DataIterHandle handle)
            throws MXNetError {
        NDArrayHandle out = new NDArrayHandle();
        int ret = MXNetJNI.MXDataIterGetLabel(handle.getHandle(), out.getHandleRef());
        HandleError(ret);
        
        return out;
    }
    
    //--------------------------------------------
    // Part 5: KVStore interface
    //--------------------------------------------
    
    /**
     * Create a kvstore
     * @param type type of KVStore
     * @return output type of KVStore
     * @throws MXNetError 
     */
    public static KVStoreHandle MXKVStoreCreate(String type) throws MXNetError {
        KVStoreHandle out = new KVStoreHandle();
        int ret = MXNetJNI.MXKVStoreCreate(type, out.getHandleRef());
        HandleError(ret);
        
        return out;
    }
    
    /**
     * Init a list of (key,value) pairs in kvstore
     * @param handle handle to the kvstore
     * @param keys list of keys
     * @param vals list of values
     * @throws MXNetError 
     */
    public static void MXKVStoreInit(KVStoreHandle handle, 
            int[] keys, NDArrayHandle[] vals) throws MXNetError {
        int ret = MXNetJNI.MXKVStoreInit(handle.getHandle(), keys, 
                MXNetHandles.transferHandleArray(vals));
        HandleError(ret);
    }
    
    /**
     * Push a list of (key,value) pairs to kvstore
     * @param handle handle to the kvstore
     * @param keys list of keys
     * @param vals list of values
     * @param priority the priority of the action
     * @throws MXNetError 
     */
    public static void MXKVStorePush(KVStoreHandle handle, 
            int[] keys, NDArrayHandle[] vals, int priority) throws MXNetError {
        int ret = MXNetJNI.MXKVStorePush(handle.getHandle(), keys, 
                MXNetHandles.transferHandleArray(vals), priority);
        HandleError(ret);
    }
    
    /**
     * pull a list of (key, value) pairs from the kvstore
     * @param handle handle to the kvstore
     * @param keys list of keys
     * @param vals list of values
     * @param priority priority of the action
     * @throws MXNetError 
     */
    public static void MXKVStorePull(KVStoreHandle handle, 
            int[] keys, NDArrayHandle[] vals, int priority) throws MXNetError {
        int ret = MXNetJNI.MXKVStorePull(handle.getHandle(), keys, 
                MXNetHandles.transferHandleArray(vals), priority);
        HandleError(ret);
    }
}
