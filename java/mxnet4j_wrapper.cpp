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
  (JNIEnv *, jclass, jintArray, jlong, jint, jint, jint, jlongArray);

/*
 * Class:     org_dmlc_mxnet_wrapper_MXNetJNI
 * Method:    MXNDArrayLoadFromRawBytes
 * Signature: ([B[J)I
 */
JNIEXPORT jint JNICALL Java_org_dmlc_mxnet_wrapper_MXNetJNI_MXNDArrayLoadFromRawBytes
  (JNIEnv *, jclass, jbyteArray, jlongArray);

/*
 * Class:     org_dmlc_mxnet_wrapper_MXNetJNI
 * Method:    MXNDArraySaveRawBytes
 * Signature: (J[Ljava/lang/String;)I
 */
JNIEXPORT jint JNICALL Java_org_dmlc_mxnet_wrapper_MXNetJNI_MXNDArraySaveRawBytes
  (JNIEnv *, jclass, jlong, jobjectArray);

/*
 * Class:     org_dmlc_mxnet_wrapper_MXNetJNI
 * Method:    MXNDArraySyncCopyFromCPU
 * Signature: (J[F)I
 */
JNIEXPORT jint JNICALL Java_org_dmlc_mxnet_wrapper_MXNetJNI_MXNDArraySyncCopyFromCPU
  (JNIEnv *, jclass, jlong, jfloatArray);

/*
 * Class:     org_dmlc_mxnet_wrapper_MXNetJNI
 * Method:    MXNDArraySyncCopyToCPU
 * Signature: (J[FI)I
 */
JNIEXPORT jint JNICALL Java_org_dmlc_mxnet_wrapper_MXNetJNI_MXNDArraySyncCopyToCPU
  (JNIEnv *, jclass, jlong, jfloatArray, jint);

/*
 * Class:     org_dmlc_mxnet_wrapper_MXNetJNI
 * Method:    MXNDArrayWaitToRead
 * Signature: (J)I
 */
JNIEXPORT jint JNICALL Java_org_dmlc_mxnet_wrapper_MXNetJNI_MXNDArrayWaitToRead
  (JNIEnv *, jclass, jlong);

/*
 * Class:     org_dmlc_mxnet_wrapper_MXNetJNI
 * Method:    MXNDArrayWaitToWrite
 * Signature: (J)I
 */
JNIEXPORT jint JNICALL Java_org_dmlc_mxnet_wrapper_MXNetJNI_MXNDArrayWaitToWrite
  (JNIEnv *, jclass, jlong);

/*
 * Class:     org_dmlc_mxnet_wrapper_MXNetJNI
 * Method:    MXNDarrayWaitAll
 * Signature: ()I
 */
JNIEXPORT jint JNICALL Java_org_dmlc_mxnet_wrapper_MXNetJNI_MXNDarrayWaitAll
  (JNIEnv *, jclass);

/*
 * Class:     org_dmlc_mxnet_wrapper_MXNetJNI
 * Method:    MXNDArraySave
 * Signature: (Ljava/lang/String;[J[Ljava/lang/String;)I
 */
JNIEXPORT jint JNICALL Java_org_dmlc_mxnet_wrapper_MXNetJNI_MXNDArraySave
  (JNIEnv *, jclass, jstring, jlongArray, jobjectArray);

/*
 * Class:     org_dmlc_mxnet_wrapper_MXNetJNI
 * Method:    MXNDArrayLoad
 * Signature: (Ljava/lang/String;[[J[[Ljava/lang/String;)I
 */
JNIEXPORT jint JNICALL Java_org_dmlc_mxnet_wrapper_MXNetJNI_MXNDArrayLoad
  (JNIEnv *, jclass, jstring, jobjectArray, jobjectArray);

/*
 * Class:     org_dmlc_mxnet_wrapper_MXNetJNI
 * Method:    MXNDArrayFree
 * Signature: (J)I
 */
JNIEXPORT jint JNICALL Java_org_dmlc_mxnet_wrapper_MXNetJNI_MXNDArrayFree
  (JNIEnv *, jclass, jlong);

/*
 * Class:     org_dmlc_mxnet_wrapper_MXNetJNI
 * Method:    MXNDArraySlice
 * Signature: (JII[J)I
 */
JNIEXPORT jint JNICALL Java_org_dmlc_mxnet_wrapper_MXNetJNI_MXNDArraySlice
  (JNIEnv *, jclass, jlong, jint, jint, jlongArray);

/*
 * Class:     org_dmlc_mxnet_wrapper_MXNetJNI
 * Method:    MXNDArrayGetShape
 * Signature: (J[[I)I
 */
JNIEXPORT jint JNICALL Java_org_dmlc_mxnet_wrapper_MXNetJNI_MXNDArrayGetShape
  (JNIEnv *, jclass, jlong, jobjectArray);

/*
 * Class:     org_dmlc_mxnet_wrapper_MXNetJNI
 * Method:    MXNDArrayGetData
 * Signature: (J[[F)I
 */
JNIEXPORT jint JNICALL Java_org_dmlc_mxnet_wrapper_MXNetJNI_MXNDArrayGetData
  (JNIEnv *, jclass, jlong, jobjectArray);

/*
 * Class:     org_dmlc_mxnet_wrapper_MXNetJNI
 * Method:    MXNDArrayGetContext
 * Signature: (J[I[I)I
 */
JNIEXPORT jint JNICALL Java_org_dmlc_mxnet_wrapper_MXNetJNI_MXNDArrayGetContext
  (JNIEnv *, jclass, jlong, jintArray, jintArray);

/*
 * Class:     org_dmlc_mxnet_wrapper_MXNetJNI
 * Method:    MXListFunctions
 * Signature: ([[J)I
 */
JNIEXPORT jint JNICALL Java_org_dmlc_mxnet_wrapper_MXNetJNI_MXListFunctions
  (JNIEnv *, jclass, jobjectArray);

/*
 * Class:     org_dmlc_mxnet_wrapper_MXNetJNI
 * Method:    MXGetFunction
 * Signature: (Ljava/lang/String;[J)I
 */
JNIEXPORT jint JNICALL Java_org_dmlc_mxnet_wrapper_MXNetJNI_MXGetFunction
  (JNIEnv *, jclass, jstring, jlongArray);

/*
 * Class:     org_dmlc_mxnet_wrapper_MXNetJNI
 * Method:    MXFuncGetInfo
 * Signature: (J[Ljava/lang/String;[Ljava/lang/String;[[Ljava/lang/String;[[Ljava/lang/String;[[Ljava/lang/String;)I
 */
JNIEXPORT jint JNICALL Java_org_dmlc_mxnet_wrapper_MXNetJNI_MXFuncGetInfo
  (JNIEnv *, jclass, jlong, jobjectArray, jobjectArray, jobjectArray, jobjectArray, jobjectArray);

/*
 * Class:     org_dmlc_mxnet_wrapper_MXNetJNI
 * Method:    MXFuncDescribe
 * Signature: (J[I[I[I[I)I
 */
JNIEXPORT jint JNICALL Java_org_dmlc_mxnet_wrapper_MXNetJNI_MXFuncDescribe
  (JNIEnv *, jclass, jlong, jintArray, jintArray, jintArray, jintArray);

/*
 * Class:     org_dmlc_mxnet_wrapper_MXNetJNI
 * Method:    MXFuncInvoke
 * Signature: (J[J[F[J)I
 */
JNIEXPORT jint JNICALL Java_org_dmlc_mxnet_wrapper_MXNetJNI_MXFuncInvoke
  (JNIEnv *, jclass, jlong, jlongArray, jfloatArray, jlongArray);

/*
 * Class:     org_dmlc_mxnet_wrapper_MXNetJNI
 * Method:    MXSymbolListAtomicSymbolCreators
 * Signature: ([[J)I
 */
JNIEXPORT jint JNICALL Java_org_dmlc_mxnet_wrapper_MXNetJNI_MXSymbolListAtomicSymbolCreators
  (JNIEnv *, jclass, jobjectArray);

/*
 * Class:     org_dmlc_mxnet_wrapper_MXNetJNI
 * Method:    MXSymbolGetAtomicSymbolName
 * Signature: (J[Ljava/lang/String;)I
 */
JNIEXPORT jint JNICALL Java_org_dmlc_mxnet_wrapper_MXNetJNI_MXSymbolGetAtomicSymbolName
  (JNIEnv *, jclass, jlong, jobjectArray);

/*
 * Class:     org_dmlc_mxnet_wrapper_MXNetJNI
 * Method:    MXSymbolGetAtomicSymbolInfo
 * Signature: (J[Ljava/lang/String;[Ljava/lang/String;[[Ljava/lang/String;[[Ljava/lang/String;[[Ljava/lang/String;[Ljava/lang/String;)I
 */
JNIEXPORT jint JNICALL Java_org_dmlc_mxnet_wrapper_MXNetJNI_MXSymbolGetAtomicSymbolInfo
  (JNIEnv *, jclass, jlong, jobjectArray, jobjectArray, jobjectArray, jobjectArray, jobjectArray, jobjectArray);

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

