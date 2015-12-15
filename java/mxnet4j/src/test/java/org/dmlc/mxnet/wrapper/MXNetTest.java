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

import java.util.Arrays;
import static junit.framework.TestCase.assertTrue;
import static junit.framework.TestCase.assertEquals;
import org.dmlc.mxnet.wrapper.MXNetHandles.NDArrayHandle;
import org.dmlc.mxnet.wrapper.util.MXNetError;
import org.junit.Test;

/**
 *
 * @author hzx
 */
public class MXNetTest {
    @Test
    public void testNDarryCreate() throws MXNetError {
        NDArrayHandle handle = MXNet.MXNDArrayCreateNone();
        assertTrue(handle.isInit);
        
        int[] noneShape = MXNet.MXNDArrayGetShape(handle);
        assertTrue(noneShape.length==0);
        
        int[] testShape = new int[] {2, 3, 3, 2};
        int devType = 2;
        int devId = 0;
        NDArrayHandle handle2 = MXNet.MXNDArrayCreate(testShape, devType, devId, 0);
        assertTrue(handle2.isInit);
        
        int[] context = MXNet.MXNDArrayGetContext(handle2);
        assertEquals(context[0], devType);
        assertEquals(context[1], devId);
        
        int size = 1;
        for(int num: testShape) {
            size *= num;
        }
        float[] data = null;
        if(context[0]==2) {
            data = MXNet.MXNDArraySyncCopyToCPU(handle2, size);
        }
        else{
            data =  MXNet.MXNDArrayGetData(handle2);
        }
        assertEquals(data.length, size);
        
        int[] shape = MXNet.MXNDArrayGetShape(handle2);
        assertEquals(Arrays.toString(testShape), Arrays.toString(shape));
    }
    
    @Test
    public void testNDArraySave() throws MXNetError {
        int[] testShape = new int[] {2, 3, 3, 2};
        int devType = 1;
        int devId = 0;
        NDArrayHandle handle = MXNet.MXNDArrayCreate(testShape, devType, devId, 0);
        
        byte[] rawBytes = MXNet.MXNDArraySaveRawBytes(handle);
        String buf = new String(rawBytes);
        System.out.println(Arrays.toString(rawBytes));
        System.out.println(Arrays.toString(buf.getBytes()));
        //cannot use this way
        assertTrue(Arrays.equals(rawBytes, buf.getBytes()));
        NDArrayHandle handle2 = MXNet.MXNDArrayLoadFromRawBytes(buf);
        
        float[] data = MXNet.MXNDArrayGetData(handle);
        float[] data2 = MXNet.MXNDArrayGetData(handle2);
        
        System.out.println(Arrays.toString(data));
        System.out.println(Arrays.toString(data2));
        assertTrue(Arrays.equals(data, data2));
    }
}
