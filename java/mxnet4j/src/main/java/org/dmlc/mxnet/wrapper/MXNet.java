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
/**
 * util java class for MXNetJNI
 * @author hzx
 */


public class MXNet {
    public final static native int MXRandomSeed(int seed);
    public final static native int MXNotifyShutdown();
    //ndarry
    public final static native int MXNDArrayCreateNone(NDArrayHandle handle);
    public final static native int MXNDArrayCreate(long[] shape, long ndim, int dev_type, int dev_id, int delay_alloc, NDArrayHandle handle);
    public final static native int MXNDArrayLoadFromRawBytes(long buf, int size, long[] out);
    public final static native int MXNDArraySaveRawBytes(long handle, String[] out_buf);
    public final static native int MXNDArraySyncCopyFromCPU(long handle, float[] data);
    public final static native int MXNDArraySyncCopyToCPU(long handle, float[] data);
    public final static native int MXNDArrayWaitToWrite(long handle);
    public final static native int MXNDArraySave(String fname, long[] args, String[] keys);
    public final static native int MXNDArrayLoad(String fname, long[][] out_arr, String[][] out_names);
    public final static native int MXNDArrayFree(long handle);
    public final static native int MXNDArraySlice(long handle, int slice_begin, int slice_end, long[] out);
    public final static native int MXNDArrayGetShape(long handle, long[][] out_pdata);
    public final static native int MXNDArrayGetData(long handle, float[][] out_pdata);
    public final static native int MXNDArrayGetContext(long handle, int[] out_dev_type, int[] out_dev_id);
    //funcs
    public final static native int MXListFunctions(long[][] out_array);
    public final static native int MXGetFunction(String name, long[] out);
    public final static native int MXFuncGetInfo(long fun, String[] name, String[] description, String[][] arg_names, String[][] arg_type_infos, String[][] arg_descriptions);
    public final static native int MXFuncDescribe(long fun, int[] num_use_vars, int[] num_scalars, int[] num_mutate_vars, int[] type_mask);
    public final static native int MXFuncInvoke(long fun, long[] use_vars, float[] scalar_args, long[] mutate_vars);
    //symbolic config generation
    public final static native int MXSymbolListAtomicSymbolCreators(long[][] out_array);
    public final static native int MXSymbolGetAtomicSymbolName(long creator, String[] out);
    public final static native int MXSymbolGetAtomicSymbolInfo(long creator, String[] name, String[] description, String[][] arg_names, String[][] arg_type_infos, String[][] arg_descriptions, String[] key_var_num_args);
    public final static native int MXSymbolCreateAtomicSymbol(long creator, String[] keys, String[] vals, long[] out);
    public final static native int MXSymbolCreateVariable(String name, long[] out);
    public final static native int MXSymbolCreateGroup(long[] symbols, long[] out);
    public final static native int MXSymbolGetOutput(long symbol, int index, long[] out);
    public final static native int MXSymbolGetInternals(long symbol, long[] out);
    public final static native int MXSymbolCreateFromFile(String fname, long[] out);
}
