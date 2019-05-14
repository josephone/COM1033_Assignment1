Creating a dynamic and interactive football league table in Java

Features:
The user is able to view a football league table
The user is able to search for a specific team and isolate their results to be viewed solely on the table
The top 8 teams with the highest points will be placed into a text file and declared as having qualified for a knockouts competition
The top 8 teams with the highest points will be displayed to the user in a JTextArea
The user is able to register an account with a unique username, password and a role
The user is able to log into an account with the correct username and password combination and be directed to the correct frame for their role
If the user logs in as a 'League developer' then they are able to provide information for a match that will be inserted into the database
If the user logs in as a 'League developer' then they are able to edit the team statistics (Goals scored and goals conceded)
If the user logs in as a 'Player' then they are able to search for a specific team and their relevant statistics will be shown
If the user logs in as a 'Manager' then they are able to create a teamsheet which is prefixed by the name of the team and will be written to a .txt file

Player login details:
			Username: ankuroo
			Password: shadow12

Manager login details:
			Username: eugene
			Password: likescats69

League developer login details
				Username: headDev
				Password: develop123

How to use this software and access it's full functionalities:
1. If there is an error with the build path upon installation, you will need to install the sqlite-jdbc.jar file. This must be downloaded from bitbucket. Once this has been installed you should right click on the overall folder, select Build Path -> Configure Build Path -> Libraries -> Add External JARs -> Locate and select the sqlite-jdbc.jar file -> Apply and close
2. Register a new account with a username, a password that is 5 characters or longer and is a series of letters followed by a series of numbers. The role is either 'Player', 'Manager' or 'League developer'. In order to access the full functionalities I would recommend making three accounts which each takes one of the roles. There are already accounts set up for this purpose but you may wish to create your own
3. You will automatically be redirected to the Log in page where you will be prompted to enter a username and password. If the you enters this successfully then you will be taken to the appropriate page depending on your role.

If you log in as a 'League developer':
1. Enter the name of the team you wish to provide information about. There is already data inputted into the database and so to check if the program is able to update information enter any of the following team names: Stoke, Spurs, Arsenal, Chelsea, Villa, Palace, Everton, Liverpool and West Brom
2. If you wish to create a new row, simply provide a team name of your choice and enter the amount of goals scored and the amount of goals conceded
3. Press the 'Edit statistics' button to alter the amount of goals scored and goals conceded for the team that has been chosen. Press the submit button to apply these changes.

If you log in as a 'Manager':
1. Enter the name of the team that you are entering details for
2. Enter the names of the goalkeeper, defenders, midfielders, attackers
3. Press the 'Update team sheets' button 
4. Navigate to C:\Users\Public to locate the .txt file

If you log in as a 'Player':
1. Enter the name of the team whose statistics you want to view
2. Press the search button