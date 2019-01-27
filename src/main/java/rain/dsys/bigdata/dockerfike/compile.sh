#!/bin/bash

# compile hadoop 2.7.3 by default
version=${1:-2.7.3}

cd /hadoop-$version-src

echo -e "\n\ncomile hadoop $version..."
mvn package -Pdist,native -DskipTests -Dtar

if [[ $? -eq 0 ]]; then
	echo -e "\n\ncomile hadoop $version success!\n\n"
	#mv ./hadoop-dist/target/hadoop-$version.tar.gz /binary
else
        echo -e "\n\ncomile hadoop $version fail!\n\n"
fi
