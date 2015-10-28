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

import java.util.HashMap;
import java.util.Map;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dmlc.mxnet.wrapper.MXNet;
import org.dmlc.mxnet.wrapper.MXNetHandles.DataIterCreator;
import org.dmlc.mxnet.wrapper.MXNetHandles.DataIterHandle;
import org.dmlc.mxnet.wrapper.util.MXDataIterInfo;
import org.dmlc.mxnet.wrapper.util.MXNetError;

/**
 * class for DataIter creators
 * @author hzx
 */
public class MXDataIterCreator {
    private static final Log logger = LogFactory.getLog(MXDataIterCreator.class);
    /**
     * map which stored all mxnet dataitercreators
     * with key is the name of creator
     */
    static Map<String, MXDataIterCreator> creatorMap;
    
    static {
        try {
            creatorMap = getCreatorMap();
        } catch (MXNetError ex) {
            logger.error(ex);
            System.exit(0);
        }
    }
    
    DataIterCreator creator;
    MXDataIterInfo info;
    
    public MXDataIterCreator(DataIterCreator creator) throws MXNetError {
        this.creator = creator;
        this.info = MXNet.MXDataIterGetIterInfo(creator);
    }
    
    public MXDataIter create(Map<String, Object> params) throws MXNetError {
        String[] keys = new String[params.size()];
        String[] values = new String[params.size()];
        params.keySet().toArray(keys);
        params.values().toArray(values);
        DataIterHandle handle = MXNet.MXDataIterCreateIter(creator, keys, values);
        return new MXDataIter(handle);
    }
    
    public String getCreatorName() {
        return info.name;
    }
    
    public MXDataIterInfo getDataIterInfo() {
        return info;
    }
    
    /**
     * create an MXDataIter with given iterName and params
     * @param iterName name of iterator
     * @param params parameters
     * @return
     * @throws MXNetError 
     */
    public static MXDataIter createIter(String iterName, Map<String, Object> params) throws MXNetError {
        return creatorMap.get(iterName).create(params);
    }
    
    /**
     * create a <name, creator> map to store all mxnet DataIterCreators
     * @return
     * @throws MXNetError 
     */
    private static Map<String, MXDataIterCreator> getCreatorMap() 
            throws MXNetError {
        DataIterCreator[] creatorHandles = MXNet.MXListDataIters();
        Map<String, MXDataIterCreator> localCreatorMap = new HashMap<>();
        for(DataIterCreator creatorHandle : creatorHandles) {
            MXDataIterCreator creator = new MXDataIterCreator(creatorHandle);
            localCreatorMap.put(creator.getCreatorName(), creator);
        }
        return localCreatorMap;
    }
}
