# CardCreator

### Introduction

Here we are creating a java web application which creates and stores a card created by a user. The cards you can consider them as tasks that needs to be performed. 
The cards will have attributes like Title, Description, Creator and whom it is assigned to. The cards have also have column attribute which tells us in which state the task(card) is currently in, i.e. it will have states like todo,inProgress,done.
We will store this cards in database and retrieve it while loading it on the main page.

### Domain Model
The Directory 'CardCreator/src/main/java/solomon/app/domain/' specifies all the basic entities such as Card, User, Column. It also have a RuntimeConsole file which can simulate the basic command line application.

### Database
Each time you create the card we will update that card in the database for which we will use the DAO classes which are specified in the 'CardCreator/src/main/java/solomon/repository/' directory. 
We can execute the "InitiateMysqlDatabases.java" to create the tables in the MySQL locally. Once the tables are created we can start the application using Tomcat.

### Webapp
As per the Maven directory structure you can find webapp related files in 'CardCreator/src/main/webapp/'
The servlet files for login and other functionalities are stored on path 'CardCreator/src/main/java/solomon/app/login/'

### How it Works
1. First user login to the site for which we will use 'LoginProcess.java' servlet in the background to verify the user credentials.
2. If user is not available they can register on the signup page which calls the 'SignUpProcess.java' in the backend to store the user credentials in the database.
3. Once user successfully login to the site we will store the user details(in this case we will stoer email) using a HTTP session in the browser session data
4. After which 'LoginProcess.java'/'SignUpProcess.java' calls the 'Loader.java' servlet which will create a JSON file (in the specified directory) with any cards previously stored in the database by user.
5. Now once the JSON file is created, we will route to the 'homepage.html' to the 'cardAddition.js' once it loads
6. The 'cardAddition.js' will load the card data from the JSON file and will show it on the page if no card is created previously no data will be shown
7. User can create the card in specified column by clicking on the '+' button shown on the page. User will see a dialog with the Card related fields and once user fills them out and submit it the data will be added to the database.

### To Build
You can pull the repo and build it using maven :).
