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

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dmlc.mxnet.wrapper.MXNet;
import org.dmlc.mxnet.wrapper.MXNetHandles.NDArrayHandle;
import org.dmlc.mxnet.wrapper.util.MXNetError;

/**
 * NDArray is basic ndarray/Tensor like data structure in mxnet.
 * @author hzx
 */
public class NDArray {
    private static final Log logger = LogFactory.getLog(NDArray.class);
    
    /**
     * handle of NDArray
     */
    NDArrayHandle handle = null;
    /**
     * whether this NDArray is writable
     */
    boolean writable = false;
    
    public NDArray(NDArrayHandle handle, boolean writable) {
        this.handle = handle;
        this.writable = writable;
    }
    
    
    
    /**
     * add this NDArray with another NDArray, return an third NDArray
     * @param other NDArray
     * @return third NDArray
     * @throws MXNetError 
     */
    public NDArray add(NDArray other) throws MXNetError {
        NDArray out = new NDArray(MXNet.MXNDArrayCreateNone(), true);
        Function.plus(this, other, out);
        return out;
    }
    
    /**
     * add this NDArray with a scalar, return another NDArray
     * @param other scalar
     * @return another NDArray
     * @throws MXNetError 
     */
    public NDArray add(float other) throws MXNetError {
        NDArray out = new NDArray(MXNet.MXNDArrayCreateNone(), true);
        Function.plusScalar(this, other, out);
        return out;
    }
    
    /**
     * inplace add this NDArray with another NDArray, 
     * result will be hold by this NDArray
     * @param other other NDArray
     * @return 
     * @throws MXNetError 
     */
    public NDArray iAdd(NDArray other) throws MXNetError {
        Function.plus(this, other, this);
        return this;
    }
    
    /**
     * inplace add this NDArray with a scalar,
     * result will be hold by this NDArray
     * @param other scalar
     * @return 
     * @throws MXNetError 
     */
    public NDArray iAdd(float other) throws MXNetError {
        Function.plusScalar(this, other, this);
        return this;
    }
    
    /**
     * minus this NDArray by another NDArray, 
     * return an third NDArray
     * @param other NDArray
     * @return result NDArray
     * @throws MXNetError 
     */
    public NDArray sub(NDArray other) throws MXNetError {
        NDArray out = new NDArray(MXNet.MXNDArrayCreateNone(), true);
        Function.minus(this, other, out);
        return out;
    }
    
    /**
     * minus this NDArray by a scalar, 
     * return an third NDArray
     * @param other scalar
     * @return result NDArray
     * @throws MXNetError 
     */
    public NDArray sub(float other) throws MXNetError {
        NDArray out = new NDArray(MXNet.MXNDArrayCreateNone(), true);
        Function.minusScalar(this, other, out);
        return out;
    }
    
    /**
     * inplace minus this NDArray by another NDArray,
     * result will be hold by this NDArray
     * @param other
     * @return this NDArray
     * @throws MXNetError 
     */
    public NDArray iSub(NDArray other) throws MXNetError {
        Function.minus(this, other, this);
        return this;
    }
    
    /**
     * inplace minus this NDArray by a scalar,
     * result will be hold by this NDArray
     * @param other
     * @return 
     * @throws MXNetError 
     */
    public NDArray iSub(float other) throws MXNetError {
        Function.minusScalar(this, other, this);
        return this;
    }
    
    /**
     *  inplace rminus this NDArray by a scalar,
     *  result will be hold by this NDArray
     * @param other
     * @return 
     * @throws MXNetError 
     */
    public NDArray rSub(float other) throws MXNetError {
        Function.rminusScalar(this, other, this);
        return this;
    }
    
    /**
     * mutiply this NDArray with another NDArray, 
     * return an third NDArray
     * @param other NDArray
     * @return result NDArray
     * @throws MXNetError 
     */
    public NDArray multiply(NDArray other) throws MXNetError {
        NDArray out = new NDArray(MXNet.MXNDArrayCreateNone(), true);
        Function.mul(this, other, out);
        return out;
    } 
    
    /**
     *  mutiply this NDArray with a scalar, 
     * return another NDArray
     * @param other scalar
     * @return  result NDArray
     * @throws MXNetError 
     */
    public NDArray multiply(float other) throws MXNetError {
        NDArray out = new NDArray(MXNet.MXNDArrayCreateNone(), true);
        Function.mulScalar(this, other, out);
        return out;
    }
    
    /**
     * inplace mutiply this NDArray with another NDArray, 
     * result will hold by this NDArray
     * @param other NDArray
     * @return result NDArray
     * @throws MXNetError 
     */
    public NDArray iMultiply(NDArray other) throws MXNetError {
        Function.mul(this, other, this);
        return this;
    }
    
    /**
     * inplace mutiply this NDArray with a scalar, 
     * result will hold by this NDArray
     * @param other NDArray
     * @return result NDArray
     * @throws MXNetError 
     */
    public NDArray iMultiply(float other) throws MXNetError {
        Function.mulScalar(this, other, this);
        return this;
    }
    
    /**
     * inplace negative 
     * @return this handle
     * @throws MXNetError 
     */
    public NDArray negative() throws MXNetError {
        Function.mulScalar(this, -1.0f, this);
        return this;
    }
    
      /**
     * divide this NDArray by another NDArray,
     * return an third NDArray
     * @param other NDArray
     * @return result NDArray
     * @throws MXNetError 
     */
    public NDArray divide(NDArray other) throws MXNetError {
        NDArray out = new NDArray(MXNet.MXNDArrayCreateNone(), true);
        Function.div(this, other, out);
        return out;
    }
    
     /**
     * divide this NDArray by a scalar,
     * return another NDArray
     * @param other NDArray
     * @return result NDArray
     * @throws MXNetError 
     */
    public NDArray divide(float other) throws MXNetError {
        NDArray out = new NDArray(MXNet.MXNDArrayCreateNone(), true);
        Function.divScalar(this, other, out);
        return out;
    }
    
     /**
     * inplace divide this NDArray by another NDArray,
     * result will be hold by this NDArray
     * @param other NDArray
     * @return result NDArray
     * @throws MXNetError 
     */
    public NDArray iDivide(NDArray other) throws MXNetError {
        Function.div(this, other, this);
        return this;
    }
    
    /**
     * inplace divide this NDArray by a scalar,
     * result will be hold by this NDArray
     * @param other NDArray
     * @return result NDArray
     * @throws MXNetError 
     */
    public NDArray iDivide(float other) throws MXNetError {
        Function.divScalar(this, other, this);
        return this;
    }
    
    /**
     *  inplace right divide this NDArray by a scalar,
     * result will be hold by this NDArray
     * @param other
     * @return
     * @throws MXNetError 
     */
    public NDArray rDivide(float other) throws MXNetError {
        Function.rdivScalar(this, other, this);
        return this;
    }
    
    /**
     * save current NDArray to raw bytes
     * @return
     * @throws MXNetError 
     */
    public String saveRawBytes() throws MXNetError {
        return MXNet.MXNDArraySaveRawBytes(handle);
    }
    
    /**
     * load a NDArray from raw bytes
     * @param buf
     * @return
     * @throws MXNetError 
     */
    public static NDArray loadFromRawBytes(String buf) throws MXNetError {
        NDArrayHandle handle = MXNet.MXNDArrayLoadFromRawBytes(buf);
        return new NDArray(handle, true);
    }
    
    /**
     * set given value to this NDArray
     * @param value
     * @return this NDArray
     * @throws MXNetError 
     */
    public NDArray setValue(float value) throws MXNetError {
        Function.set(value, this);
        return this;
    }
    
    /**
     * set given values to this NDArray
     * @param value array of values
     * @return this NDArray
     * @throws MXNetError 
     */
    public NDArray setValue(float[] value) throws MXNetError {
        MXNet.MXNDArraySyncCopyFromCPU(handle, value);
        return this;
    }
    
    /**
     * set given value to a slice of this NDArray
     * @param begin sclice begin index
     * @param end slice end index
     * @param value 
     * @return slice of this NDArray which has been setted new value
     * @throws MXNetError 
     */
    public NDArray setValue(int begin, int end, float value) throws MXNetError {
        NDArrayHandle sliceHandle = MXNet.MXNDArraySlice(handle, begin, end);
        NDArray tmp = new NDArray(sliceHandle, this.writable);
        Function.set(value, tmp);
        return tmp;
    }
    
    /**
     * set given values to a slice of this NDArray
     * @param begin sclice begin index
     * @param end slice end index
     * @param value array of values
     * @return slice of this NDArray which has been setted new values
     * @throws MXNetError 
     */
    public NDArray setValue(int begin, int end, float[] value) throws MXNetError {
        NDArrayHandle sliceHandle = MXNet.MXNDArraySlice(handle, begin, end);       
        MXNet.MXNDArraySyncCopyFromCPU(sliceHandle, value);
        NDArray tmp = new NDArray(sliceHandle, this.writable);
        return tmp;
    }
    
    /**
     * Return a sliiced NDArray that shares memory with current one.
     * @param begin sclice begin index
     * @param end slice end index
     * @return slice of this NDArray
     * @throws MXNetError 
     */
    public NDArray slice(int begin, int end) throws MXNetError {
        NDArrayHandle sliceHandle = MXNet.MXNDArraySlice(handle, begin, end); 
        return new NDArray(sliceHandle, this.writable);
    }
    
    /**
     * Block until all pending writes operations 
     * on current NDArray are finished.
     * 
     * This function will return when all the pending writes to the current
     * NDArray finishes. There can still be pending read going on when the
     * function returns.
     * @throws org.dmlc.mxnet.wrapper.util.MXNetError
     */
    public void waitToRead() throws MXNetError {
        MXNet.MXNDArrayWaitToRead(handle);
    }
    
    /**
     * Get shape of current NDArray.
     * @return an array representing shape of current ndarray
     * @throws MXNetError 
     */
    public int[] shape() throws MXNetError {
        return MXNet.MXNDArrayGetShape(handle);
    }
    
    /**
     * Get context of current NDArray.
     * @return Context
     * @throws MXNetError 
     */
    public Context context() throws MXNetError {
        int[] cxt = MXNet.MXNDArrayGetContext(handle);
        return new Context(cxt[0], cxt[1]);
    }
    
    /**
     * Return a copied float array of this NDArray
     * @return float array
     * @throws MXNetError 
     */
    public float[] toArray() throws MXNetError {
        return MXNet.MXNDArrayGetData(handle);
    }
    
    /**
     * copy this NDArray to given NDArray
     * @param other
     * @return the other NDArray
     * @throws MXNetError 
     */
    public NDArray copyToNDArray(NDArray other) throws MXNetError {
        Function.copyTo(this, other);
        return other;
    }
    
    /**
     * copy this NDArray to another NDArray with given Context
     * @param ctx context of other NDArray to be copy to
     * @return the other NDArray
     * @throws MXNetError 
     */
    public NDArray copyToNDArray(Context ctx) throws MXNetError {
        NDArrayHandle newHandle = MXNet.MXNDArrayCreate(this.shape(), ctx.devTypeId, ctx.devId, 1);
        NDArray other = new NDArray(newHandle, true);
        Function.copyTo(this, other);
        return other;
    }
    
    /**
     * clip NDArray to range [-value, value] and remove NaN
     * @param array NDArray
     * @param value value > 0
     * @return NDArray
     * @throws MXNetError 
     */
    public static NDArray clip(NDArray array,  float value) throws MXNetError {
        if(value < 0) {
            throw new MXNetError("value must be larger than 0 for clip func,"
                    + " current value=" + value);
        }
        Function.clip(array, -value, value, array);
        return array;
    }
    
    /**
     * Create an empty uninitialized new NDArray, 
     * with specified shape ande Context
     * @param shape int array represent the shape
     * @param ctx Context
     * @return created NDArray
     * @throws MXNetError 
     */
    public static NDArray empty(int[] shape, Context ctx) throws MXNetError {
        NDArrayHandle handle = MXNet.MXNDArrayCreate(shape, ctx.devTypeId, ctx.devId, 0);
        return new NDArray(handle, true);
    }
    
    /**
     * Create an empty uninitialized new NDArray, 
     * with specified shape and default Context
     * @param shape int array represent the shape
     * @return created NDArray
     * @throws MXNetError 
     */
    public static NDArray empty(int[] shape) throws MXNetError {
        Context ctx = Context.defaultContext();
        NDArrayHandle handle = MXNet.MXNDArrayCreate(shape, ctx.devTypeId, ctx.devId, 0);
        return new NDArray(handle, true);
    }
    
    /**
     * Create a new NDArray filled with 0, 
     * with specified shape and context
     * @param shape int array represent the shape
     * @param ctx Context
     * @return created NDArray
     * @throws MXNetError 
     */
    public static NDArray zeros(int[] shape, Context ctx) throws MXNetError {
        return empty(shape, ctx).setValue(0.0f);
    }
    
    /**
     * Create a new NDArray filled with 0, 
     * with specified shape and default context
     * @param shape int array represent the shape
     * @return created NDArray
     * @throws MXNetError 
     */
    public static NDArray zeros(int[] shape) throws MXNetError {
        return empty(shape).setValue(0.0f);
    }
    
    /**
     * Create a new NDArray filled with 1, 
     * with specified shape and context.
     * @param shape int array represent the shape
     * @param ctx Context
     * @return created NDArray
     * @throws MXNetError 
     */
    public static NDArray ones(int[] shape, Context ctx) throws MXNetError {
        return empty(shape, ctx).setValue(1.0f);
    }
    
    /**
     * Create a new NDArray filled with 1, 
     * with specified shape and default context.
     * @param shape int array represent the shape
     * @return created NDArray
     * @throws MXNetError 
     */
    public static NDArray ones(int[] shape) throws MXNetError {
        return empty(shape).setValue(1.0f);
    }
    
    /**
     * Create a new NDArray that 
     * copies content from source_array.
     * with specified shape and context.
     * @param srcArray
     * @param shape
     * @param ctx
     * @return
     * @throws MXNetError 
     */
    public static NDArray array(float[] srcArray, int[] shape, Context ctx) throws MXNetError {
        return empty(shape, ctx).setValue(srcArray);
    }
    
    /**
     * Create a new NDArray that 
     * copies content from source_array.
     * with specified shape and default context.
     * @param srcArray
     * @param shape
     * @return
     * @throws MXNetError 
     */
    public static NDArray array(float[] srcArray, int[] shape) throws MXNetError {
        return empty(shape).setValue(srcArray);
    }
    
    /**
     * Load ndarray from binary file.
     * The advantage of load/save is the file is language agnostic.
     * This means the file saved using save can be loaded by other language binding of mxnet.
     * You also get the benefit being able to directly load/save from cloud storage(S3, HDFS)
     * @param fname The name of the file.Can be S3 or HDFS address
     *                              (remember built with S3 support).
     *                              example of fnames:
     *                               - `s3://my-bucket/path/my-s3-ndarray`
     *                               - `hdfs://my-bucket/path/my-hdfs-ndarray`
     *                               - `/path-to/my-local-ndarray`
     * @return array of NDArray
     * @throws MXNetError 
     */
    public NDArray[] load(String fname) throws MXNetError {
        NDArrayHandle[] handles = MXNet.MXNDArrayLoad(fname);
        NDArray[] arrays = new NDArray[handles.length];
        for(int i=0; i<arrays.length; i++) {
            arrays[i] = new NDArray(handles[i], true);
        }
        return arrays;
    }
    
    /**
     * Save array of NDArrays to binary file
     * The advantage of load/save is the file is language agnostic.
     * This means the file saved using save can be loaded by other language binding of mxnet.
      * You also get the benefit being able to directly load/save from cloud storage(S3, HDFS)
     * @param fname The name of the file.Can be S3 or HDFS address
     *                              (remember built with S3 support).
     * @param arrays array of NDArrays
     * @throws MXNetError 
     */
    public void save(String fname, NDArray[] arrays) throws MXNetError {
        NDArrayHandle[] handles = new NDArrayHandle[arrays.length];
        String[] names = null;
        int keys = 0;
        for(int i=0; i<arrays.length; i++) {
            handles[i] = arrays[i].handle;
            if(handles[i].getName()!=null) {
                keys++;
            }
        }
        if(keys==handles.length) {
            names = new String[handles.length];
            for(int j=0; j<handles.length; j++) {
                names[j] = handles[j].getName();
            }
        }
        MXNet.MXNDArraySave(fname, handles, names);
    }

}
