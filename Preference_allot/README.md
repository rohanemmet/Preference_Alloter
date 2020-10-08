# How To Run

The project folder includes a prepackaged jar for you to run in the "target" folder. To run it, you have two options.

You can simply double click on it, as you would any executable and it will run quietly in the background. The trouble with this method is that to stop the program you have to go to Task Manager and find the "OpenJDK Platform binary" process and kill it from there.

The method we would suggest is to run it by opening a command prompt, navigating to the folder in which the jar is contained, and typing "java -jar projectallocation-0.0.1-SNAPSHOT.jar". This will open the console for the program in the command prompt window, and allow it to be stopped at any time with a simple Ctrl+C.

If you have made some changes to the code, and wish to recompile it, use Maven to package it. Open command prompt and navigate to the root directory of the project, then type "mvn package" and let Maven do the rest. From there, just follow the previous steps to run it.
