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
package org.dmlc.mxnet.io;

import org.dmlc.mxnet.NDArray;
import org.dmlc.mxnet.wrapper.util.MXNetError;

/**
 * interface of DataIters for mxnet
 * @author hzx
 */
public interface IDataIter {
    /**
     * reset the iterator
     * @throws org.dmlc.mxnet.wrapper.util.MXNetError
     */
    public abstract void reset()  throws MXNetError ;
    /**
     * Iterate to next batch
     * @return whether the move is successful
     * @throws org.dmlc.mxnet.wrapper.util.MXNetError
     */
    public abstract boolean iterNext()  throws MXNetError ;
    /**
     * get data of current batch
     * @return the data of current batch
     * @throws org.dmlc.mxnet.wrapper.util.MXNetError
     */
    public abstract NDArray getData()  throws MXNetError ;
    /**
     * Get label of current batch
     * @return the label of current batch
     * @throws org.dmlc.mxnet.wrapper.util.MXNetError
     */
    public abstract NDArray getLabel()  throws MXNetError ;
    /**
     * get the number of padding examples
     * in current batch
     * @return Number of padding examples in current batch
     * @throws org.dmlc.mxnet.wrapper.util.MXNetError
     */
    public abstract int getPad()  throws MXNetError ;
}
