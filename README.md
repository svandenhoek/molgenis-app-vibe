# molgenis-app-vibe
User interface for [VIBE](https://github.com/molgenis/vibe): a pipeline-friendly software tool for
genome diagnostics to prioritize genes by matching patient symptoms to literature.

## Installation

1. Build the molgenis-app-vibe similar to a [default molgenis app](https://molgenis.gitbooks.io/molgenis/content/install_molgenis/guide-local-compile.html)
2. Extract disgenetv5.0-rdf-v5.0.0-dump-TDB.zip to <MOLGENIS_HOME>/.molgenis/data/vibe
3. Launch the application
4. Sign in as superuser, navigate to the data explorer plugin, select 'Static content' in the entity type dropdown, edit the row with id 'home' and set the content of 'molgenis-app-vibe\src\test\resources\vibe.html' as value, save the row

## Build new version

1. `cd /path/to/vibe`
2. `mvn clean install`
3. `mvn install:install-file -Dfile=/path/to/vibe-with-dependencies-<version>.jar -DgroupId=org.molgenis -DartifactId=vibe -Dversion=<version> -Dpackaging=jar`
4. `cp -r /path/to/.m2/repository/org/molgenis/vibe/<version> /path/to/molgenis-app-vibe`
5. `cd /path/to/molgenis-app-vibe`
6. `mvn clean install`

