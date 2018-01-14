#!/usr/bin/env bash

: '
Edu application
Parameters:
  -s=<spark_home> | --spark=<spark_home>: sets the Spark home directory.
'

# Default values for parameters
SPARK_HOME_DEFAULT="/usr/local/spark"
CLUSTER_MANAGER_DEFAULT="local"
DEPLOY_MODE_DEFAULT="client"
PARALLELISM_DEFAULT=128
BASE_DIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )"
#BASE_DIR="/Users/sdmt/Personal/sapo/sapo/target/scala-2.11/"
BASE_DIR="/Users/sdmt/Personal/Esic/BigDataII/edu/target/scala-2.11/"
echo ${BASE_DIR}

# Parse parameters
for i in "$@"
do
case $i in
    -s=*|--spark=*)
    SPARK_HOME="${i#*=}"
    shift # past argument=value
    ;;
    -cm=*|--cluster_manager=*)
    CLUSTER_MANAGER="${i#*=}"
    shift # past argument=value
    ;;
    -j=*|--jar=*)
    JAR="${i#*=}"
    shift # past argument=value
    ;;
esac
done

# Check if SPARK_HOME is set and set to default if not
if [ -z ${SPARK_HOME+x} ]
    then SPARK_HOME=${SPARK_HOME_DEFAULT}
fi

# Check if cluster manager argument is set and set to default if not
if [ -z ${CLUSTER_MANAGER+x} ]
    then CLUSTER_MANAGER=${CLUSTER_MANAGER_DEFAULT}
fi

# Check if jar argument is set and set to default if not
if [ -z ${JAR+x} ]
    then JAR=$(find ${BASE_DIR}/ -iname 'edu-assembly-*.jar' | tail -n 1)
fi
echo 'Using JAR '${JAR}
#echo 'Using JAR '${BASE_DIR}

echo $SPARK_HOME
echo $CLUSTER_MANAGER
echo $DEPLOY_MODE_DEFAULT
${SPARK_HOME}/bin/spark-submit \
    --class es.sdmt.edu.core.Edu \
    --master ${CLUSTER_MANAGER} \
    --deploy-mode ${DEPLOY_MODE_DEFAULT} \
    --num-executors 1 \
    --executor-cores 4 \
    --driver-memory 4g \
    --executor-memory 10g \
    --conf spark.default.parallelism=4096 \
    --conf spark.sql.parquet.compression.codec=snappy \
    ${JAR} $@
