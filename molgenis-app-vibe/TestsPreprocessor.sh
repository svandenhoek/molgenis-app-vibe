#!/usr/bin/env bash

echo "# Retrieving information from pom.xml"
readonly VIBE_VERSION=$(mvn -q help:evaluate -Dexpression=vibe.version -DforceStdout)
readonly TDB_ARCHIVE=$(mvn -q -f local-maven-repo/org/molgenis/vibe/${VIBE_VERSION}/vibe-${VIBE_VERSION}.pom help:evaluate -Dexpression=vibe-tdb.archive -DforceStdout)
readonly TDB_DOWNLOAD=$(mvn -q -f local-maven-repo/org/molgenis/vibe/${VIBE_VERSION}/vibe-${VIBE_VERSION}.pom help:evaluate -Dexpression=vibe-tdb.download -DforceStdout)

cd src/test/resources/app_data_root/data

echo "# Removing old data"
rm -rf vibe

echo "# Downloading data"
curl -L -O ${TDB_DOWNLOAD}

echo "# Preparing data for unit-tests"
tar -xzvf ${TDB_ARCHIVE}
mv ${TDB_ARCHIVE%.tar.gz} vibe
rm ${TDB_ARCHIVE}

cd ../../../../../