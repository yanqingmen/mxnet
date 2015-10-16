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
 * MXNet jni wrapper funcs for  src/c_api.cc
 * hints: use a long[] (length=1) as container of handle to get the output handle
 *            use a int[](length=1) as container to get the output size
 *            use a String[](length=1) as container to get the output String
 * @author hzx
 */
public class MXNetJNI {
    public final static native int MXRandomSeed(int seed);
    public final static native int MXNotifyShutdown();
    //ndarry
    public final static native int MXNDArrayCreateNone(long[] out);
    public final static native int MXNDArrayCreate(long[] shape, long ndim, int dev_type, int dev_id, int delay_alloc, long[] out);
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
    public final static native int MXSymbolCreateFromJSON(String json,long[] out);
    public final static native int MXSymbolSaveToFile(long symbol, String fname);
    public final static native int MXSymbolSaveToJSON(long symbol, String[] out_json);
    public final static native int MXSymbolFree(long symbol);
    public final static native int MXSymbolCopy(long symbol, long[] out);
    public final static native int MXSymbolPrint(long symbol, String[] out_str);
    public final static native int MXSymbolListArguments(long symbol, String[][] out_str_array);
    public final static native int MXSymbolListOutputs(long symbol, String[][] out_str_array);
    public final static native int MXSymbolListAuxiliaryStates(long symbol, String[][] out_str_array);
    public final static native int MXSymbolCompose(long sym, String name, String[] keys, long[] args);
    public final static native int MXSymbolGrad(long sym, String[] wrt, long[] out);
    public final static native int MXSymbolInferShape(long sym, long jarg2, long jarg3, long jarg4, long jarg5, long jarg6, long jarg7, long jarg8, long jarg9, long jarg10, long jarg11, long jarg12, long jarg13, long jarg14, long jarg15);
    public final static native int MXExecutorPrint(long jarg1, long jarg2);
    public final static native int MXExecutorForward(long jarg1, int jarg2);
    public final static native int MXExecutorBackward(long jarg1, long jarg2, long jarg3);
    public final static native int MXExecutorOutputs(long jarg1, long jarg2, long jarg3);
    public final static native int MXExecutorBind(long jarg1, int jarg2, int jarg3, long jarg4, long jarg5, long jarg6, long jarg7, long jarg8, long jarg9, long jarg10);
    public final static native int MXListDataIters(long jarg1, long jarg2);
    public final static native int MXDataIterGetIterInfo(long jarg1, long jarg2, long jarg3, long jarg4, long jarg5, long jarg6, long jarg7);
    public final static native int MXDataIterCreateIter(long jarg1, long jarg2, long jarg3, long jarg4, long jarg5);
    public final static native int MXDataIterFree(long jarg1);
    public final static native int MXDataIterBeforeFirst(long jarg1);
    public final static native int MXDataIterNext(long jarg1, long jarg2);
    public final static native int MXDataIterGetLabel(long jarg1, long jarg2);
    public final static native int MXDataIterGetData(long jarg1, long jarg2);
    public final static native int MXDataIterGetPadNum(long jarg1, long jarg2);
    public final static native int MXKVStoreCreate(String jarg1, long jarg2);
    public final static native int MXKVStoreFree(long jarg1);
    public final static native int MXKVStoreInit(long jarg1, long jarg2, long jarg3, long jarg4);
    public final static native int MXKVStorePush(long jarg1, long jarg2, long jarg3, long jarg4, int jarg5);
    public final static native int MXKVStorePull(long jarg1, long jarg2, long jarg3, long jarg4, int jarg5);
    public final static native int MXKVStoreSetUpdater(long jarg1, long jarg2);
}
