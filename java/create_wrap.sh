echo "build mxnet4j"
cd ..
make java
cd java

libPath="mxnet4j/src/main/resources/lib"
if [ ! -d "$libPath" ]; then
  mkdir -p "$libPath"
fi

rm -f mxnet4j/src/main/resources/lib/libxgboostjavawrapper.so
mv libmxnet4j.so mxnet4j/src/main/resources/lib/

echo "complete"
