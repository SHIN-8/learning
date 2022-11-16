#!/bin/bash
cd `dirname $0`
DIR=`pwd`

if [ $# -ne 1 ];
then
    echo "please input filename with arguments"
    exit 1
fi

echo `kotlinc $DIR/src/$1.kt -include-runtime -d $DIR/build/$1.jar`
echo `chmod a+x $DIR/build/$1.jar`
echo `kotlin $DIR/build/$1.jar`