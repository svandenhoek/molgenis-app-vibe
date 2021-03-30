# molgenis-app-vibe
User interface for [VIBE][vibe]: a pipeline-friendly software tool for
genome diagnostics to prioritize genes by matching patient symptoms to literature.

## For developers

### Create local test environment
1. Follow the instructions as described on [Developing MOLGENIS][molgenis_developing] and [Using an IDE (Intellij)][molgenis_idea_setup] (select Java 11 JDK & language level 11 instead).
2. Extract the content of the [vibe database][vibe_database] to `<MOLGENIS_HOME>/data/vibe`.
3. Launch the application.
4. Sign in as superuser.
5. Update homepage:
    1. Navigate to the data explorer.
    2. Select 'Static content' in the entity type dropdown.
    3. Edit the row with id 'home'.
    4. Copy the content of [this file](./molgenis-app-vibe/src/test/resources/vibe.html) into the Content field.
    5. Click on the save button.
6. Update setings:
    1. Navigate to the data explorer.
    2. Select 'Application Settings' in the entity type dropdown.
    3. Edit the row with Application Title 'MOLGENIS'.
    4. Under 'CSS href', add:  
    `vibe.css`
    5. Click on the save button.

### Build new version for deployment.
1. `cd /path/to/vibe`
2. `mvn clean install`
3. `mvn install:install-file -Dfile=/path/to/vibe/app/target/vibe-with-dependencies-<version>.jar -DgroupId=org.molgenis -DartifactId=vibe -Dversion=<version> -Dpackaging=jar`
4. `cp -r /path/to/.m2/repository/org/molgenis/vibe/<version> /path/to/molgenis-app-vibe/molgenis-app-vibe/local-maven-repo/org/molgenis/vibe`
5. `rm /path/to/molgenis-app-vibe/molgenis-app-vibe/local-maven-repo/org/molgenis/vibe/<version>/_remote.repositories`
6. `cd /path/to/molgenis-app-vibe`
7. Edit `molgenis-app-vibe/pom.xml` to use new vibe-version as dependency (and if a new TDB is required, adjust this as well).
8. `mvn clean install`



[vibe]: https://github.com/molgenis/vibe
[molgenis_developing]: https://molgenis.gitbooks.io/molgenis/content/v/8.1/guide-development.html
[molgenis_idea_setup]: https://molgenis.gitbooks.io/molgenis/content/v/8.1/guide-using-an-ide.html
[vibe_database]: https://molgenis.org/downloads/vibe/vibe-5.0.0-hdt.tar.gz