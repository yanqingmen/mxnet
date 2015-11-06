/*!
 * Copyright (c) 2015 by Contributors
 * \file mxnet4j_wrapper.cpp
 * \brief java wrapper code for mxnet
 */

#include <mxnet/c_api.h>
#include "./mxnet4j_wrapper.h"

/*
 * Class:     org_dmlc_mxnet_wrapper_MXNetJNI
 * Method:    MXGetLastError
 * Signature: ()Ljava/lang/String;
 */
JNIEXPORT jstring JNICALL Java_org_dmlc_mxnet_wrapper_MXNetJNI_MXGetLastError
  (JNIEnv *jenv, jclass jcls) {
    jstring jresult = 0 ;
    char* result = 0;
    result = MXGetLastError();
    if (result) jresult = jenv->NewStringUTF((const char *)result);
    return jresult;
}

/*
 * Class:     org_dmlc_mxnet_wrapper_MXNetJNI
 * Method:    MXRandomSeed
 * Signature: (I)I
 */
JNIEXPORT jint JNICALL Java_org_dmlc_mxnet_wrapper_MXNetJNI_MXRandomSeed
  (JNIEnv *jenv, jclass jcls, jint jseed) {
    jint flag = MXRandomSeed((int) jseed);
    return flag;
}

/*
 * Class:     org_dmlc_mxnet_wrapper_MXNetJNI
 * Method:    MXNotifyShutdown
 * Signature: ()I
 */
JNIEXPORT jint JNICALL Java_org_dmlc_mxnet_wrapper_MXNetJNI_MXNotifyShutdown
  (JNIEnv *jenv, jclass jcls) {
    jint flag = MXNotifyShutdown();
    return flag;
}

/*
 * Class:     org_dmlc_mxnet_wrapper_MXNetJNI
 * Method:    MXNDArrayCreateNone
 * Signature: ([J)I
 */
JNIEXPORT jint JNICALL Java_org_dmlc_mxnet_wrapper_MXNetJNI_MXNDArrayCreateNone
  (JNIEnv *jenv, jclass jcls, jlongArray jout) {
    void* result[1];
    unsigned long out[1];
    jint flag = MXNDArrayCreateNone(result);
     *(void **)&out[0] = *result;
     jenv->SetLongArrayRegion(jout, 0, 1, (const jlong *) out);
    return flag;
}

/*
 * Class:     org_dmlc_mxnet_wrapper_MXNetJNI
 * Method:    MXNDArrayCreate
 * Signature: ([IJIII[J)I
 */
JNIEXPORT jint JNICALL Java_org_dmlc_mxnet_wrapper_MXNetJNI_MXNDArrayCreate
  (JNIEnv *jenv, jclass jcls, jintArray jshape, jint jdev_type , jint jdev_id, jint jdellay_alloc, jlongArray jout) {
    void* result[1];
    unsigned long out[1];
    jsize len = jenv->GetArrayLength(jshape);
    jint* shape = jenv->GetIntArrayElements(jshape, 0);
    
    jint flag = MXNDArrayCreate((const mx_uint *)shape, (mx_uint) len, jdev_type, jdev_id, jdellay_alloc, result);
    
    *(void **)&out[0] = *result;
     jenv->SetLongArrayRegion(jout, 0, 1, (const jlong *) out);
     
     //release
     jenv->ReleaseIntArrayElements(jshape, shape, 0);
     return flag;
}

/*
 * Class:     org_dmlc_mxnet_wrapper_MXNetJNI
 * Method:    MXNDArrayLoadFromRawBytes
 * Signature: ([B[J)I
 */
JNIEXPORT jint JNICALL Java_org_dmlc_mxnet_wrapper_MXNetJNI_MXNDArrayLoadFromRawBytes
  (JNIEnv *jenv, jclass jcls, jbyteArray jbuf, jlongArray jout) {
    void* result[1];
    unsigned long out[1];
    jsize len = jenv->GetArrayLength(jbuf);
    jbyte* buf = jenv->GetByteArrayElements(jbuf, 0);
    
    jint flag = MXNDArrayLoadFromRawBytes((const void *) buf, (size_t) len, result);
    
    *(void **)&out[0] = *result;
     jenv->SetLongArrayRegion(jout, 0, 1, (const jlong *) out);
    
    //release
    jenv->ReleaseByteArrayElements(jbuf, buf, 0);
    return flag;
}

/*
 * Class:     org_dmlc_mxnet_wrapper_MXNetJNI
 * Method:    MXNDArraySaveRawBytes
 * Signature: (J[Ljava/lang/String;)I
 */
JNIEXPORT jint JNICALL Java_org_dmlc_mxnet_wrapper_MXNetJNI_MXNDArraySaveRawBytes
  (JNIEnv *jenv, jclass jcls, jlong jhandle, jobjectArray jout_buf) {
    void* handle;
    char *result[1];
    size_t out_size[1];
    
    handle = *(void **)&jhandle;
    jint flag = MXNDArraySaveRawBytes(handle, out_size, (const char **) result);
    
    jbyteArray jinfo = jenv->NewByteArray((jsize) out_size[0]);
    if (*result) jenv->SetByteArrayRegion(jinfo, 0, (jsize) out_size[0], (const jbyte*) result[0]);
    jenv->SetObjectArrayElement(jout_buf, 0, jinfo);
    
    return flag;
}

/*
 * Class:     org_dmlc_mxnet_wrapper_MXNetJNI
 * Method:    MXNDArraySyncCopyFromCPU
 * Signature: (J[F)I
 */
JNIEXPORT jint JNICALL Java_org_dmlc_mxnet_wrapper_MXNetJNI_MXNDArraySyncCopyFromCPU
  (JNIEnv *jenv, jclass jcls, jlong jhandle, jfloatArray jdata) {
    void* handle;
    handle = *(void **)&jhandle;
    size_t size = (size_t) jenv->GetArrayLength(jdata);
    jfloat *data = jenv->GetFloatArrayElements(jdata, 0);
    
    jint flag = MXNDArraySyncCopyFromCPU(handle, (const mx_float *) data, size);
    
    //release
    jenv->ReleaseFloatArrayElements(jdata, data, 0);
    
    return flag;
}

/*
 * Class:     org_dmlc_mxnet_wrapper_MXNetJNI
 * Method:    MXNDArraySyncCopyToCPU
 * Signature: (J[FI)I
 */
JNIEXPORT jint JNICALL Java_org_dmlc_mxnet_wrapper_MXNetJNI_MXNDArraySyncCopyToCPU
  (JNIEnv *jenv, jclass jcls, jlong jhandle, jfloatArray jdata, jint jlen) {
    void* handle;
    handle = *(void **)&jhandle;
    jfloat *data = jenv->GetFloatArrayElements(jdata, 0);
    jsize dlen = jenv->GetArrayLength(jdata);
    jint flag = MXNDArraySyncCopyToCPU(handle, data, (size_t) jlen);
    
    jenv->SetFloatArrayRegion(jdata, 0, dlen, data);
    
    //release
    jenv->ReleaseFloatArrayElements(jdata, data, 0);
    
    return flag;
}

/*
 * Class:     org_dmlc_mxnet_wrapper_MXNetJNI
 * Method:    MXNDArrayWaitToRead
 * Signature: (J)I
 */
JNIEXPORT jint JNICALL Java_org_dmlc_mxnet_wrapper_MXNetJNI_MXNDArrayWaitToRead
  (JNIEnv *jenv, jclass jcls, jlong jhandle) {
    void* handle;
    handle = *(void **)&jhandle;
    
    jint flag = MXNDArrayWaitToRead(handle);
    
    return flag;
}

/*
 * Class:     org_dmlc_mxnet_wrapper_MXNetJNI
 * Method:    MXNDArrayWaitToWrite
 * Signature: (J)I
 */
JNIEXPORT jint JNICALL Java_org_dmlc_mxnet_wrapper_MXNetJNI_MXNDArrayWaitToWrite
  (JNIEnv *jenv, jclass jcls, jlong jhandle) {
    void* handle;
    handle = *(void **)&jhandle;
    
    jint flag = MXNDArrayWaitToWrite(handle);
    
    return flag;
}

/*
 * Class:     org_dmlc_mxnet_wrapper_MXNetJNI
 * Method:    MXNDarrayWaitAll
 * Signature: ()I
 */
JNIEXPORT jint JNICALL Java_org_dmlc_mxnet_wrapper_MXNetJNI_MXNDArrayWaitAll
  (JNIEnv *jenv, jclass jcls) {
    jint flag = MXNDArrayWaitAll();
    return flag;
}

/*
 * Class:     org_dmlc_mxnet_wrapper_MXNetJNI
 * Method:    MXNDArraySave
 * Signature: (Ljava/lang/String;[J[Ljava/lang/String;)I
 */
JNIEXPORT jint JNICALL Java_org_dmlc_mxnet_wrapper_MXNetJNI_MXNDArraySave
  (JNIEnv *jenv, jclass jcls, jstring jfname, jlongArray jargs, jobjectArray jkeys) {
    char* fname; 
    void **args;
    char **keys = NULL;
    
    fname = (char *)jenv->GetStringUTFChars(jfname, 0);
    jint arg_len = jenv->GetArrayLength(jargs);   
    jint key_len = jenv->GetArrayLength(jkeys);
    
    //set handles and keys
    jlong* jargs_array = NULL;
     if(arg_len>0) {
        args = new void*[arg_len];
        jlong* jargs_array = jenv->GetLongArrayElements(jargs, 0);
        for(int i=0; i<arg_len; i++) {
            args[i] = *(void **)&jargs_array[i];
        }
     }   
    if(key_len>0) {
        keys = new char*[key_len];
        for(int j=0; j<key_len; j++) {
            jstring jkey = (jstring) jenv->GetObjectArrayElement(jkeys, j);
            keys[j] = (char* )jenv->GetStringUTFChars(jkey, 0);
        }
    }
    
    jint flag = MXNDArraySave((const char*) fname, (mx_uint) arg_len, args, (const char**) keys);
    
    //release all
    if(arg_len > 0) {
        delete[] args;
        jenv->ReleaseLongArrayElements(jargs, jargs_array, 0);
    }
    if(key_len > 0) {
        for(int j=0; j<key_len; j++) {
            jstring jkey = (jstring) jenv->GetObjectArrayElement(jkeys, j);
            jenv->ReleaseStringUTFChars(jkey, (const char*)keys[j]);
        }
        delete[] keys;
    }
    
    return flag;
}

/*
 * Class:     org_dmlc_mxnet_wrapper_MXNetJNI
 * Method:    MXNDArrayLoad
 * Signature: (Ljava/lang/String;[[J[[Ljava/lang/String;)I
 */
JNIEXPORT jint JNICALL Java_org_dmlc_mxnet_wrapper_MXNetJNI_MXNDArrayLoad
  (JNIEnv *jenv, jclass jcls, jstring jfname, jobjectArray jout_array, jobjectArray jout_names) {
    char *fname;
    mx_uint out_size[1];
    mx_uint out_name_size[1];
    void **out_array[1];
    char **out_names[1];
    
    fname = (char*) jenv->GetStringUTFChars(jfname, 0);
    
    jint flag = MXNDArrayLoad((const char*) fname, out_size, out_array, out_name_size, (const char***) out_names);
    
    //set handles
    jlongArray handles = jenv->NewLongArray(out_size[0]);
    jlong* handle_array = jenv->GetLongArrayElements(handles, 0);
    for(int i=0; i<out_size[0]; i++) {
        *(void **)&handle_array[i] = out_array[0][i];
    }
    jenv->SetLongArrayRegion(handles,0, out_size[0], (const jlong*) handle_array);
    jenv->SetObjectArrayElement(jout_array, 0, (jobject) handles);
    //release
    jenv->ReleaseLongArrayElements(handles, handle_array, 0);
    
    //set names
    jobjectArray jnames = jenv->NewObjectArray(out_name_size[0], jenv->FindClass("java/lang/String"), jenv->NewStringUTF(""));
    for(int i=0; i<out_name_size[0]; i++) {
        jenv->SetObjectArrayElement(jnames, i, jenv->NewStringUTF((const char*) out_names[0][i]));
    }
    jenv->SetObjectArrayElement(jout_names, 0, jnames);
    
    return flag;
}

/*
 * Class:     org_dmlc_mxnet_wrapper_MXNetJNI
 * Method:    MXNDArrayFree
 * Signature: (J)I
 */
JNIEXPORT jint JNICALL Java_org_dmlc_mxnet_wrapper_MXNetJNI_MXNDArrayFree
  (JNIEnv *jenv, jclass jcls, jlong jhandle) {
    void* handle;
    handle = *(void **)&jhandle;
    
    jint flag = MXNDArrayFree(handle);
    
    return flag;
}

/*
 * Class:     org_dmlc_mxnet_wrapper_MXNetJNI
 * Method:    MXNDArraySlice
 * Signature: (JII[J)I
 */
JNIEXPORT jint JNICALL Java_org_dmlc_mxnet_wrapper_MXNetJNI_MXNDArraySlice
  (JNIEnv *jenv, jclass jcls, jlong jhandle, jint jslice_begin, jint jslice_end, jlongArray jout) {
    void* handle;
    unsigned long out[1];
    handle = *(void **)&jhandle;
    
    jint flag = MXNDArraySlice(handle, (mx_uint) jslice_begin, (mx_uint) jslice_end, out);
    jenv->SetLongArrayRegion(jout, 0, 1, out);
    
    return flag;
}

/*
 * Class:     org_dmlc_mxnet_wrapper_MXNetJNI
 * Method:    MXNDArrayGetShape
 * Signature: (J[[I)I
 */
JNIEXPORT jint JNICALL Java_org_dmlc_mxnet_wrapper_MXNetJNI_MXNDArrayGetShape
  (JNIEnv *jenv, jclass jcls, jlong jhandle, jobjectArray jout) {
    void* handle;
    mx_uint out_dim[1];
    mx_uint* out_pdata[1];
    
    handle = *(void **)&jhandle;
    jint flag = MXNDArrayGetShape(handle, out_dim, (const mx_uint **) out_pdata);
    jintArray jshape = jenv->NewIntArray(out_dim[0]);
    jenv->SetIntArrayRegion(jshape, 0, out_dim[0], out_pdata[0]);
    jenv->SetObjectArrayElement(jout, 0, jshape);
    
    return flag;
}

/*
 * Class:     org_dmlc_mxnet_wrapper_MXNetJNI
 * Method:    MXNDArrayGetData
 * Signature: (J[[F)I
 */
JNIEXPORT jint JNICALL Java_org_dmlc_mxnet_wrapper_MXNetJNI_MXNDArrayGetData
  (JNIEnv *jenv, jclass jcls, jlong jhandle, jobjectArray jout) {
    void* handle;
    mx_uint shape_dim[1];
    mx_uint* shape_data[1];
    mx_float * out_data[1];
    jint flag;
    handle = *(void **)&jhandle;
    
    flag = MXNDArrayGetShape(handle, shape_dim, (const mx_uint **) shape_data);
    if(flag != 0) {
        return flag;
    }
    
    flag = MXNDArrayGetData(handle, out_data);
    
    jsize len = 1;
    for(int i=0; i<shape_dim[0]; i++) {
        len *= shape_data[0][i];
    }
    jfloatArray jdata = jenv->NewFloatArray(len);
    jenv->SetFloatArrayRegion(jdata, 0, len, out_data[0]);
    jenv->SetObjectArrayElement(jout, 0, jdata);
    
    return flag;
}

/*
 * Class:     org_dmlc_mxnet_wrapper_MXNetJNI
 * Method:    MXNDArrayGetContext
 * Signature: (J[I[I)I
 */
JNIEXPORT jint JNICALL Java_org_dmlc_mxnet_wrapper_MXNetJNI_MXNDArrayGetContext
  (JNIEnv *jenv, jclass jcls, jlong jhandle, jintArray jout_dev_type, jintArray jout_dev_id) {
    void* handle;
    int out_dev_type[1];
    int out_dev_id[1];
    
    handle = *(void **)&jhandle;
    jint flag = MXNDArrayGetContext(handle, out_dev_type, out_dev_id);
    jenv->SetIntArrayRegion(jout_dev_type, 0, 1,(const jint*) out_dev_type);
    jenv->SetIntArrayRegion(jout_dev_id, 0, 1,(const jint*) out_dev_id);
    
    return flag;
}

/*
 * Class:     org_dmlc_mxnet_wrapper_MXNetJNI
 * Method:    MXListFunctions
 * Signature: ([[J)I
 */
JNIEXPORT jint JNICALL Java_org_dmlc_mxnet_wrapper_MXNetJNI_MXListFunctions
  (JNIEnv *jenv, jclass jcls, jobjectArray jout_array) {
    mx_uint out_size[1];
    void** out_array[1];
    jlongArray jhandles;
    
    jint flag = MXListFunctions(out_size, out_array);
    
    //put out handles to jhandles
    jhandles = jenv->NewLongArray((jsize) out_size[0]);
    jlong* jhandle_array = jenv->GetLongArrayElements(jhandles, 0);
    for(int i=0; i<out_size[0]; i++) {
        *(void **)&jhandle_array[i] = out_array[0][i];
    }
    jenv->SetLongArrayRegion(jhandles, 0, out_size[0], jhandle_array);
    //put jhandles to jout_array
    jenv->SetObjectArrayElement(jout_array, 0, jhandles);
    //release
    jenv->ReleaseLongArrayElements(jhandles, jhandle_array, 0);
    
    return flag;
}

/*
 * Class:     org_dmlc_mxnet_wrapper_MXNetJNI
 * Method:    MXGetFunction
 * Signature: (Ljava/lang/String;[J)I
 */
JNIEXPORT jint JNICALL Java_org_dmlc_mxnet_wrapper_MXNetJNI_MXGetFunction
  (JNIEnv *jenv, jclass jcls, jstring jname, jlongArray jout) {
    void* result[1];
    unsigned long out[1];
    char* name = (char *) jenv->GetStringUTFChars(jname, 0);
    
    jint flag = MXGetFunction((const char*) name, result);
    //put handle to jout
    *(void **)&out[0] = result[0];
    jenv->SetLongArrayRegion(jout, 0, 1, (const jlong*) out);
    
    //release name
    jenv->ReleaseStringUTFChars(jname, (const char*) name);
    
    return flag;
}

/*
 * Class:     org_dmlc_mxnet_wrapper_MXNetJNI
 * Method:    MXFuncGetInfo
 * Signature: (J[Ljava/lang/String;[Ljava/lang/String;[[Ljava/lang/String;[[Ljava/lang/String;[[Ljava/lang/String;)I
 */
JNIEXPORT jint JNICALL Java_org_dmlc_mxnet_wrapper_MXNetJNI_MXFuncGetInfo
  (JNIEnv *jenv, jclass jcls, jlong jfun, jobjectArray jname, jobjectArray jdescription,
        jobjectArray jarg_names, jobjectArray jarg_type_infos, jobjectArray jarg_descriptions) {
    //todo
}

/*
 * Class:     org_dmlc_mxnet_wrapper_MXNetJNI
 * Method:    MXFuncDescribe
 * Signature: (J[I[I[I[I)I
 */
JNIEXPORT jint JNICALL Java_org_dmlc_mxnet_wrapper_MXNetJNI_MXFuncDescribe
  (JNIEnv *, jclass, jlong, jintArray, jintArray, jintArray, jintArray) {
    //todo
}

/*
 * Class:     org_dmlc_mxnet_wrapper_MXNetJNI
 * Method:    MXFuncInvoke
 * Signature: (J[J[F[J)I
 */
JNIEXPORT jint JNICALL Java_org_dmlc_mxnet_wrapper_MXNetJNI_MXFuncInvoke
  (JNIEnv *, jclass, jlong, jlongArray, jfloatArray, jlongArray) {
    //todo
}

/*
 * Class:     org_dmlc_mxnet_wrapper_MXNetJNI
 * Method:    MXSymbolListAtomicSymbolCreators
 * Signature: ([[J)I
 */
JNIEXPORT jint JNICALL Java_org_dmlc_mxnet_wrapper_MXNetJNI_MXSymbolListAtomicSymbolCreators
  (JNIEnv *jenv, jclass jcls, jobjectArray jout_array) {
    jlongArray jhandles;
    void** out_array[1];
    mx_uint out_size[1];
    
    jint flag = MXSymbolListAtomicSymbolCreators(out_size, out_array);
    //put pointers to jhandles
    jhandles = jenv->NewLongArray((jsize) out_size[0]);
    jlong* jhandle_array = jenv->GetLongArrayElements(jhandles, 0);
    for(int i=0; i<out_size[0]; i++) {
        *(void **)&jhandle_array[i] = out_array[0][i];
    }
    jenv->SetLongArrayRegion(jhandles, 0, out_size[0], jhandle_array);
    jenv->SetObjectArrayElement(jout_array, 0, jhandles);
    
    //release
    jenv->ReleaseLongArrayElements(jhandles, jhandle_array, 0);
    
    return flag;
}

/*
 * Class:     org_dmlc_mxnet_wrapper_MXNetJNI
 * Method:    MXSymbolGetAtomicSymbolInfo
 * Signature: (J[Ljava/lang/String;[Ljava/lang/String;[[Ljava/lang/String;[[Ljava/lang/String;[[Ljava/lang/String;[Ljava/lang/String;)I
 */
JNIEXPORT jint JNICALL Java_org_dmlc_mxnet_wrapper_MXNetJNI_MXSymbolGetAtomicSymbolInfo
  (JNIEnv *, jclass, jlong, jobjectArray, jobjectArray, jobjectArray, jobjectArray, jobjectArray, jobjectArray) {
    //todo
}

/*
 * Class:     org_dmlc_mxnet_wrapper_MXNetJNI
 * Method:    MXSymbolCreateAtomicSymbol
 * Signature: (J[Ljava/lang/String;[Ljava/lang/String;[J)I
 */
JNIEXPORT jint JNICALL Java_org_dmlc_mxnet_wrapper_MXNetJNI_MXSymbolCreateAtomicSymbol
  (JNIEnv *, jclass, jlong, jobjectArray, jobjectArray, jlongArray);

/*
 * Class:     org_dmlc_mxnet_wrapper_MXNetJNI
 * Method:    MXSymbolCreateVariable
 * Signature: (Ljava/lang/String;[J)I
 */
JNIEXPORT jint JNICALL Java_org_dmlc_mxnet_wrapper_MXNetJNI_MXSymbolCreateVariable
  (JNIEnv *, jclass, jstring, jlongArray);

/*
 * Class:     org_dmlc_mxnet_wrapper_MXNetJNI
 * Method:    MXSymbolCreateGroup
 * Signature: ([J[J)I
 */
JNIEXPORT jint JNICALL Java_org_dmlc_mxnet_wrapper_MXNetJNI_MXSymbolCreateGroup
  (JNIEnv *, jclass, jlongArray, jlongArray);

/*
 * Class:     org_dmlc_mxnet_wrapper_MXNetJNI
 * Method:    MXSymbolGetOutput
 * Signature: (JI[J)I
 */
JNIEXPORT jint JNICALL Java_org_dmlc_mxnet_wrapper_MXNetJNI_MXSymbolGetOutput
  (JNIEnv *, jclass, jlong, jint, jlongArray);

/*
 * Class:     org_dmlc_mxnet_wrapper_MXNetJNI
 * Method:    MXSymbolGetInternals
 * Signature: (J[J)I
 */
JNIEXPORT jint JNICALL Java_org_dmlc_mxnet_wrapper_MXNetJNI_MXSymbolGetInternals
  (JNIEnv *, jclass, jlong, jlongArray);

/*
 * Class:     org_dmlc_mxnet_wrapper_MXNetJNI
 * Method:    MXSymbolCreateFromFile
 * Signature: (Ljava/lang/String;[J)I
 */
JNIEXPORT jint JNICALL Java_org_dmlc_mxnet_wrapper_MXNetJNI_MXSymbolCreateFromFile
  (JNIEnv *, jclass, jstring, jlongArray);

/*
 * Class:     org_dmlc_mxnet_wrapper_MXNetJNI
 * Method:    MXSymbolCreateFromJSON
 * Signature: (Ljava/lang/String;[J)I
 */
JNIEXPORT jint JNICALL Java_org_dmlc_mxnet_wrapper_MXNetJNI_MXSymbolCreateFromJSON
  (JNIEnv *, jclass, jstring, jlongArray);

/*
 * Class:     org_dmlc_mxnet_wrapper_MXNetJNI
 * Method:    MXSymbolSaveToFile
 * Signature: (JLjava/lang/String;)I
 */
JNIEXPORT jint JNICALL Java_org_dmlc_mxnet_wrapper_MXNetJNI_MXSymbolSaveToFile
  (JNIEnv *, jclass, jlong, jstring);

/*
 * Class:     org_dmlc_mxnet_wrapper_MXNetJNI
 * Method:    MXSymbolSaveToJSON
 * Signature: (J[Ljava/lang/String;)I
 */
JNIEXPORT jint JNICALL Java_org_dmlc_mxnet_wrapper_MXNetJNI_MXSymbolSaveToJSON
  (JNIEnv *, jclass, jlong, jobjectArray);

/*
 * Class:     org_dmlc_mxnet_wrapper_MXNetJNI
 * Method:    MXSymbolFree
 * Signature: (J)I
 */
JNIEXPORT jint JNICALL Java_org_dmlc_mxnet_wrapper_MXNetJNI_MXSymbolFree
  (JNIEnv *, jclass, jlong);

/*
 * Class:     org_dmlc_mxnet_wrapper_MXNetJNI
 * Method:    MXSymbolCopy
 * Signature: (J[J)I
 */
JNIEXPORT jint JNICALL Java_org_dmlc_mxnet_wrapper_MXNetJNI_MXSymbolCopy
  (JNIEnv *, jclass, jlong, jlongArray);

/*
 * Class:     org_dmlc_mxnet_wrapper_MXNetJNI
 * Method:    MXSymbolPrint
 * Signature: (J[Ljava/lang/String;)I
 */
JNIEXPORT jint JNICALL Java_org_dmlc_mxnet_wrapper_MXNetJNI_MXSymbolPrint
  (JNIEnv *, jclass, jlong, jobjectArray);

/*
 * Class:     org_dmlc_mxnet_wrapper_MXNetJNI
 * Method:    MXSymbolListArguments
 * Signature: (J[[Ljava/lang/String;)I
 */
JNIEXPORT jint JNICALL Java_org_dmlc_mxnet_wrapper_MXNetJNI_MXSymbolListArguments
  (JNIEnv *, jclass, jlong, jobjectArray);

/*
 * Class:     org_dmlc_mxnet_wrapper_MXNetJNI
 * Method:    MXSymbolListOutputs
 * Signature: (J[[Ljava/lang/String;)I
 */
JNIEXPORT jint JNICALL Java_org_dmlc_mxnet_wrapper_MXNetJNI_MXSymbolListOutputs
  (JNIEnv *, jclass, jlong, jobjectArray);

/*
 * Class:     org_dmlc_mxnet_wrapper_MXNetJNI
 * Method:    MXSymbolListAuxiliaryStates
 * Signature: (J[[Ljava/lang/String;)I
 */
JNIEXPORT jint JNICALL Java_org_dmlc_mxnet_wrapper_MXNetJNI_MXSymbolListAuxiliaryStates
  (JNIEnv *, jclass, jlong, jobjectArray);

/*
 * Class:     org_dmlc_mxnet_wrapper_MXNetJNI
 * Method:    MXSymbolCompose
 * Signature: (JLjava/lang/String;[Ljava/lang/String;[J)I
 */
JNIEXPORT jint JNICALL Java_org_dmlc_mxnet_wrapper_MXNetJNI_MXSymbolCompose
  (JNIEnv *, jclass, jlong, jstring, jobjectArray, jlongArray);

/*
 * Class:     org_dmlc_mxnet_wrapper_MXNetJNI
 * Method:    MXSymbolGrad
 * Signature: (J[Ljava/lang/String;[J)I
 */
JNIEXPORT jint JNICALL Java_org_dmlc_mxnet_wrapper_MXNetJNI_MXSymbolGrad
  (JNIEnv *, jclass, jlong, jobjectArray, jlongArray);

/*
 * Class:     org_dmlc_mxnet_wrapper_MXNetJNI
 * Method:    MXSymbolInferShape
 * Signature: (J[Ljava/lang/String;[I[I[[I[[J[I[[I[[[I[I[[I[[[I[I)I
 */
JNIEXPORT jint JNICALL Java_org_dmlc_mxnet_wrapper_MXNetJNI_MXSymbolInferShape
  (JNIEnv *, jclass, jlong, jobjectArray, jintArray, jintArray, jobjectArray, jobjectArray, jintArray, jobjectArray, jobjectArray, jintArray, jobjectArray, jobjectArray, jintArray);

/*
 * Class:     org_dmlc_mxnet_wrapper_MXNetJNI
 * Method:    MXExecutorPrint
 * Signature: (J[Ljava/lang/String;)I
 */
JNIEXPORT jint JNICALL Java_org_dmlc_mxnet_wrapper_MXNetJNI_MXExecutorPrint
  (JNIEnv *, jclass, jlong, jobjectArray);

/*
 * Class:     org_dmlc_mxnet_wrapper_MXNetJNI
 * Method:    MXExecutorForward
 * Signature: (JI)I
 */
JNIEXPORT jint JNICALL Java_org_dmlc_mxnet_wrapper_MXNetJNI_MXExecutorForward
  (JNIEnv *, jclass, jlong, jint);

/*
 * Class:     org_dmlc_mxnet_wrapper_MXNetJNI
 * Method:    MXExecutorBackward
 * Signature: (J[J)I
 */
JNIEXPORT jint JNICALL Java_org_dmlc_mxnet_wrapper_MXNetJNI_MXExecutorBackward
  (JNIEnv *, jclass, jlong, jlongArray);

/*
 * Class:     org_dmlc_mxnet_wrapper_MXNetJNI
 * Method:    MXExecutorOutputs
 * Signature: (J[[J)I
 */
JNIEXPORT jint JNICALL Java_org_dmlc_mxnet_wrapper_MXNetJNI_MXExecutorOutputs
  (JNIEnv *, jclass, jlong, jobjectArray);

/*
 * Class:     org_dmlc_mxnet_wrapper_MXNetJNI
 * Method:    MXExecutorBind
 * Signature: (JII[J[J[I[J[J)I
 */
JNIEXPORT jint JNICALL Java_org_dmlc_mxnet_wrapper_MXNetJNI_MXExecutorBind
  (JNIEnv *, jclass, jlong, jint, jint, jlongArray, jlongArray, jintArray, jlongArray, jlongArray);

/*
 * Class:     org_dmlc_mxnet_wrapper_MXNetJNI
 * Method:    MXListDataIters
 * Signature: ([[J)I
 */
JNIEXPORT jint JNICALL Java_org_dmlc_mxnet_wrapper_MXNetJNI_MXListDataIters
  (JNIEnv *, jclass, jobjectArray);

/*
 * Class:     org_dmlc_mxnet_wrapper_MXNetJNI
 * Method:    MXDataIterGetIterInfo
 * Signature: (J[Ljava/lang/String;[Ljava/lang/String;[[Ljava/lang/String;[[Ljava/lang/String;[[Ljava/lang/String;)I
 */
JNIEXPORT jint JNICALL Java_org_dmlc_mxnet_wrapper_MXNetJNI_MXDataIterGetIterInfo
  (JNIEnv *, jclass, jlong, jobjectArray, jobjectArray, jobjectArray, jobjectArray, jobjectArray);

/*
 * Class:     org_dmlc_mxnet_wrapper_MXNetJNI
 * Method:    MXDataIterCreateIter
 * Signature: (J[Ljava/lang/String;[Ljava/lang/String;[J)I
 */
JNIEXPORT jint JNICALL Java_org_dmlc_mxnet_wrapper_MXNetJNI_MXDataIterCreateIter
  (JNIEnv *, jclass, jlong, jobjectArray, jobjectArray, jlongArray);

/*
 * Class:     org_dmlc_mxnet_wrapper_MXNetJNI
 * Method:    MXDataIterFree
 * Signature: (J)I
 */
JNIEXPORT jint JNICALL Java_org_dmlc_mxnet_wrapper_MXNetJNI_MXDataIterFree
  (JNIEnv *, jclass, jlong);

/*
 * Class:     org_dmlc_mxnet_wrapper_MXNetJNI
 * Method:    MXDataIterBeforeFirst
 * Signature: (J)I
 */
JNIEXPORT jint JNICALL Java_org_dmlc_mxnet_wrapper_MXNetJNI_MXDataIterBeforeFirst
  (JNIEnv *, jclass, jlong);

/*
 * Class:     org_dmlc_mxnet_wrapper_MXNetJNI
 * Method:    MXDataIterNext
 * Signature: (J[I)I
 */
JNIEXPORT jint JNICALL Java_org_dmlc_mxnet_wrapper_MXNetJNI_MXDataIterNext
  (JNIEnv *, jclass, jlong, jintArray);

/*
 * Class:     org_dmlc_mxnet_wrapper_MXNetJNI
 * Method:    MXDataIterGetLabel
 * Signature: (J[J)I
 */
JNIEXPORT jint JNICALL Java_org_dmlc_mxnet_wrapper_MXNetJNI_MXDataIterGetLabel
  (JNIEnv *, jclass, jlong, jlongArray);

/*
 * Class:     org_dmlc_mxnet_wrapper_MXNetJNI
 * Method:    MXDataIterGetData
 * Signature: (J[J)I
 */
JNIEXPORT jint JNICALL Java_org_dmlc_mxnet_wrapper_MXNetJNI_MXDataIterGetData
  (JNIEnv *, jclass, jlong, jlongArray);

/*
 * Class:     org_dmlc_mxnet_wrapper_MXNetJNI
 * Method:    MXDataIterGetPadNum
 * Signature: (J[I)I
 */
JNIEXPORT jint JNICALL Java_org_dmlc_mxnet_wrapper_MXNetJNI_MXDataIterGetPadNum
  (JNIEnv *, jclass, jlong, jintArray);

/*
 * Class:     org_dmlc_mxnet_wrapper_MXNetJNI
 * Method:    MXKVStoreCreate
 * Signature: (Ljava/lang/String;[J)I
 */
JNIEXPORT jint JNICALL Java_org_dmlc_mxnet_wrapper_MXNetJNI_MXKVStoreCreate
  (JNIEnv *, jclass, jstring, jlongArray);

/*
 * Class:     org_dmlc_mxnet_wrapper_MXNetJNI
 * Method:    MXKVStoreFree
 * Signature: (J)I
 */
JNIEXPORT jint JNICALL Java_org_dmlc_mxnet_wrapper_MXNetJNI_MXKVStoreFree
  (JNIEnv *, jclass, jlong);

/*
 * Class:     org_dmlc_mxnet_wrapper_MXNetJNI
 * Method:    MXKVStoreInit
 * Signature: (J[I[J)I
 */
JNIEXPORT jint JNICALL Java_org_dmlc_mxnet_wrapper_MXNetJNI_MXKVStoreInit
  (JNIEnv *, jclass, jlong, jintArray, jlongArray);

/*
 * Class:     org_dmlc_mxnet_wrapper_MXNetJNI
 * Method:    MXKVStorePush
 * Signature: (J[I[JI)I
 */
JNIEXPORT jint JNICALL Java_org_dmlc_mxnet_wrapper_MXNetJNI_MXKVStorePush
  (JNIEnv *, jclass, jlong, jintArray, jlongArray, jint);

/*
 * Class:     org_dmlc_mxnet_wrapper_MXNetJNI
 * Method:    MXKVStorePull
 * Signature: (J[I[JI)I
 */
JNIEXPORT jint JNICALL Java_org_dmlc_mxnet_wrapper_MXNetJNI_MXKVStorePull
  (JNIEnv *, jclass, jlong, jintArray, jlongArray, jint);

/*
 * Class:     org_dmlc_mxnet_wrapper_MXNetJNI
 * Method:    MXKVStoreSetUpdater
 * Signature: (JJ)I
 */
JNIEXPORT jint JNICALL Java_org_dmlc_mxnet_wrapper_MXNetJNI_MXKVStoreSetUpdater
  (JNIEnv *, jclass, jlong, jlong);

