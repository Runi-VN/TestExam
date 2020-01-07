# Backend readme
After cloning the project and <ins>BEFORE</ins> setting up the frontend:

You might want to delete your `.git`-folder and initialize your repository with `git init`.

## Required
1. Open project in Netbeans
2. Change project properties (name, artifactID, etc) (right-click project -> refactor)
3. Set up your `database` and `database_test` on droplet and vagrant (local only). 
    * By default we use `security_base` & `security_test`
    * Go to /Other_Sources/src/main/resources/<default_package>/config.properties and change the database names as well as the remote connection key (~ line 41). The key must match the key that you create in step 6.

### Running local
4. Ensure Vagrant is up
### Running on droplet
4. In `pom.xml` change `remote.server` to `<YourDroplet>/manager/text` 
5. In `.travis.yml` rename your database to the `database_test` you made in step 3. (~line 43)
6. If not already done, SSH into your droplet and:

    * Open this file: `sudo nano /opt/tomcat/bin/setenv.sh`
    ```
    export DEPLOYED="DEV_ON_DIGITAL_OCEAN"
    export USER="YOUR_DB_USER"
    export PW="YOUR_DB_PASSWORD"
    export CONNECTION_STR="jdbc:mysql://localhost:3306/startcode"
    ```
    * Save the file, and restart Tomcat: `sudo systemctl restart tomcat`
7. Verify it works with the following script:  
`mvn clean test -Dremote.user=script_user -Dremote.password=PW_FOR_script_user tomcat7:deploy`
---

5. Change entity passwords in `utils.SetupTestUsers.java`
    - Add the following line to your gitignore: `**/SetupTestUsers.java`
    - Run the files' `main`-method.

6. Clean & Build -> Run Project.

## Extras

##### Modifying project URL
1. Change *context path* in `META-INF/context.xml`

##### Setting up the Travis CI Pipeline
1. Go to https://travis-ci.org/.
2. Select your repository and activate it.
3. Click `More options` and select `Settings`.
4. Create two new Environment Variables with the following variables:
    * REMOTE_PW: *Your value for the script_user password*
    * REMOTE_USER: *script_user*
5. Change something in the code, maybe `index.html`
6. Commit, push, and see your build run on https://travis-ci.org/.
7. Your project should be deployed to the URL you set earlier in `pom.xml`

### Issues?
* Run `mvn clean test` to find build issues
* For local purposes make sure to have Vagrant up and correct DB names.
* See the original startup guide: https://github.com/dat3startcode/rest-jpa-devops-startcode
