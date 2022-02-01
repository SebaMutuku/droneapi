## Drones

[[_TOC_]]

---

:scroll: **START**


### Introduction

There is a major new technology that is destined to be a disruptive force in the field of transportation: **the drone**. Just as the mobile phone allowed developing countries to leapfrog older technologies for personal communication, the drone has the potential to leapfrog traditional transportation infrastructure.

Useful drone functions include delivery of small items that are (urgently) needed in locations with difficult access.

---

### Task description

We have a fleet of **10 drones**. A drone is capable of carrying devices, other than cameras, and capable of delivering small loads. For our use case **the load is medications**.

A **Drone** has:
- serial number (100 characters max);
- model (Lightweight, Middleweight, Cruiserweight, Heavyweight);
- weight limit (500gr max);
- battery capacity (percentage);
- state (IDLE, LOADING, LOADED, DELIVERING, DELIVERED, RETURNING).

Each **Medication** has: 
- name (allowed only letters, numbers, ‘-‘, ‘_’);
- weight;
- code (allowed only upper case letters, underscore and numbers);
- image (picture of the medication case).

Develop a service via REST API that allows clients to communicate with the drones (i.e. **dispatch controller**). The specific communicaiton with the drone is outside the scope of this task. 

The service should allow:
- registering a drone;
- loading a drone with medication items;
- checking loaded medication items for a given drone; 
- checking available drones for loading;
- check drone battery level for a given drone;

> Feel free to make assumptions for the design approach. 

---

### Requirements

While implementing your solution **please take care of the following requirements**: 

#### Functional requirements

- There is no need for UI;
- Prevent the drone from being loaded with more weight that it can carry;
- Prevent the drone from being in LOADING state if the battery level is **below 25%**;
- Introduce a periodic task to check drones battery levels and create history/audit event log for this.

---

#### Non-functional requirements

- Input/output data must be in JSON format;
- Your project must be buildable and runnable;
- Your project must have a README file with build/run/test instructions (use DB that can be run locally, e.g. in-memory, via container);
- Required data must be preloaded in the database.
- JUnit tests are optional but advisable (if you have time);
- Advice: Show us how you work through your commit history.

---

:scroll: **END** 
##Drone API 

###Prerequisites 

Below are the requirements for this project to run. Ensure you have the below mentioned modules or dependencies
- Java 11 or openjdk 11 (https://openjdk.java.net/projects/jdk/11/)
- Maven 3.6.3 (https://maven.apache.org)

### How to run tests on the project

- Clone the project repository from github repo https://github.com/SebaMutuku/droneapi. Ensure you are on main branch. You can create a branch of your choice from main
- There are two ways to run the tests
1. Navigate to the root of the project and execute command mvn test. This will execute all test classes .Other ways are mvn -Dtest=com.drone.sebastianmutuku.DroneServiceTest test to execute a specific class
2. Open to project on IDE of preference, navigate to tests folder, open DroneServiceTest class and run it on the top left section

##How to run the application
- Open the project with the IDE of your choice and run it as a springboot application
- Navigate to the project root folder and execute the command mvn spring-boot:run

##Other information
- The application runs on port 4501 specified on application.yml file. Feel free to change it to the port of your choice
- The database password is encrypted with jasypt encryption method. You can read more about it here https://www.geeksforgeeks.org/how-to-encrypt-passwords-in-a-spring-boot-project-using-jasypt
- To create an executable file, run the command mvn package -DskipTests
