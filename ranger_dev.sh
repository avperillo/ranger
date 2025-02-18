#!/bin/bash

# Licensed to the Apache Software Foundation (ASF) under one
# or more contributor license agreements.  See the NOTICE file
# distributed with this work for additional information
# regarding copyright ownership.  The ASF licenses this file
# to you under the Apache License, Version 2.0 (the
# "License"); you may not use this file except in compliance
# with the License.  You may obtain a copy of the License at
#
#     http://www.apache.org/licenses/LICENSE-2.0
#
# Unless required by applicable law or agreed to in writing, software
# distributed under the License is distributed on an "AS IS" BASIS,
# WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
# See the License for the specific language governing permissions and
# limitations under the License.

# env variables
if [ -z "${RANGER_HOME}" ]
then
    rhd=`dirname $0`
    RANGER_HOME=$(cd ${rhd}; pwd)
fi
RD_HOME=${RANGER_HOME}/dev-support/ranger-docker

ENV_FILE=${RD_HOME}/.env

source "${ENV_FILE}"

# export RANGER_DB_TYPE=postgres
# ALL_SERVICES="ranger-base,ranger,postgres,ranger-usersync,hadoop,hive,hbase,knox,kms,tagsync,kafka"
ALL_SERVICES="ranger-base,ranger,postgres,ranger-usersync,kms,tagsync"
COMPOSE_FILES=""

if [ "${REMOTE_JVM_DEBUG}" == "true" ]
then
    ALL_SERVICES="${ALL_SERVICES},ranger-debug,ranger-usersync-debug,ranger-kms-debug,ranger-tagsync-debug"
fi


# action
if [ $# -eq 1 ]
then
    DOCKER_ACTION="$1"
else
    DOCKER_ACTION=""
fi

valid_actions=("up" "down" "rebuild" "config")
if [[ ! " ${valid_actions[@]} " =~ " ${DOCKER_ACTION} " ]]; then
    echo "ERROR: Invalid argument [${DOCKER_ACTION}]"
    echo "USAGE: $0 <up|down|rebuild|config>"
    exit 1
fi

if [ "${DOCKER_ACTION}" == "rebuild" ]; then
  DOCKER_ACTION="up -d --no-deps --force-recreate --build ranger ranger-usersync ranger-kms ranger-tagsync"
#  DOCKER_ACTION="up -d --no-deps --force-recreate --build ranger-tagsync"

  cp -v target/ranger-* dev-support/ranger-docker/dist/
  cp -v target/version dev-support/ranger-docker/dist/

elif [ "${DOCKER_ACTION}" == "up" ]; then
  DOCKER_ACTION="up -d"
elif [ "${DOCKER_ACTION}" == "down" ]; then
  DOCKER_ACTION="down --remove-orphans"
elif [ "${DOCKER_ACTION}" == "config" ]; then
  DOCKER_ACTION="config"
fi

for service in ${ALL_SERVICES//,/ }; do
    if [[ "${service}" == ranger* ]]; then
        serviceFile="docker-compose.${service}.yml"
    else
        serviceFile="docker-compose.ranger-${service}.yml"
    fi
    COMPOSE_FILES="${COMPOSE_FILES} -f ${serviceFile}"
done

#echo "docker-compose "${COMPOSE_FILES}" "${DOCKER_ACTION}""
cd ${RD_HOME}
docker-compose ${COMPOSE_FILES} ${DOCKER_ACTION}

echo
echo "################### LIST OF DOCKER PROCESSES EXPOSING PORTS #####################"
echo
docker container ls --format "table {{.Names}}\t{{.Ports}}" -a | grep ranger | \
        grep -v '^$' | awk '{ for(i = 2 ; i <= NF; i++) { print $1, $i } }' | \
        grep  -- '->' | sed -e 's:,::g' | awk '{ s = $2 ; split(s,a, "->") ; f = split(a[1],b,":");  print $1, b[f] }' | \
        sort  | uniq | awk '{ printf("SERVICE: %25s ExposedPort: %10s\n", $1, $2 ) ; }'
echo
echo "###################################################################################"
echo
echo "Now, You can run  access RANGER portal via http://localhost:6080 (admin/rangerR0cks!)"
echo