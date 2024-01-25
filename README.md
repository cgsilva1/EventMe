# EventMe 2.1

Project 2.1 - Team 27

Client: Brian Hyeongseok Kim


## EventMe

Team Name: Revenge of the SAL

Developers:

**Caroline Silva** - cgsilva@usc.edu

**Trevor Asbery** - asbery@usc.edu

**Nicholas Buxbaum** - nbuxbaum@usc.edu

## Preface

This is the version 1.0 of **EventMe**, an android-based application used to find and register for low-cost events nearby. This version establishes the main functionalities of the application. The expected readership of this document is our management team, designers, and engineers. Special thanks to our professor Chao Wang, and to our client Brian Hyeongseok Kim for the opportunity to bring their vision to life.

## Introduction

Have you ever wanted to go out but were worried about hurting your bank account? Introducing EventMe, now you can find affordable events right near you. Tired of spending thousands of dollars on NFL tickets or Harry styles concerts, well now you can find local events for reasonable prices.

EventMe is an app for people who want to go to events near them, but don’t necessarily have the funds to pay premium prices. It automatically filters events from low to high in price, but you can also choose from a variety of other filters on the explore page, including location and type of event. A map feature allows the user to see events near them on a Google Maps display. Registered users have the ability to book events directly through the app, and see all of the events that they have already registered for in the past. Our goal is to make the interface easy and accessible for all to use.

## Glossary

1. Secure Network Protocols: ensures the security and integrity of data in transit over a network connection (user data is kept secure)
2. Android: A mobile operating system built on Linux and released in 2008, for creating applications for smartphones and tablets
3. API (Application Program Interface): A software so that code created by others can easily be accessed and used
4. Application (App): a type of software that can be installed and run on a computer, tablet, smartphone or other electronic devices
5. Algorithm: a step-by-step recipe by which the computer completes a task
6. Data: Facts or statements which can be stored
7. Cloud Database: An organized collection of data stored in a remote server that is owned by a separate company
8. Firebase: A platform created by google which can hold databases for applications
9. Google Maps API: A specific API created by Google which allows use of their map
10. Java: An object-oriented programing language which applications can be coded in
11. Landing Page: The home page or first page that will be accessed of an application
12. System Process Diagram: A diagram which shows the sequence of events and processes in an application

## User Requirements Specification

### User Stories

1. As an EventMe user, **I want to register for an account**
2. As an EventMe member/user, **I want to be able to login to my account**
3. As an EventMe member/user, **I want to be able to search for events on the explore page**
4. As an EventMe member/user, **I want to be able to find events in proximity to my location using a map**
5. As an EventMe member/user, **I want to be able to view an event's information and register for that event.**
6. As an EventMe member/user, **I want to be able to view my profile**

## System Requirements Specification

### Functional Requirements

1. **Register an Account**
    - Each user will be able to create an account with their information
    - The registration form will require your name, birthday, email, and password, and it will set up an account for you.
2. **Login to Account**
    - Each registered member/user will be able to authenticate their information and log in.
    - Once the user has registered an account, they are able to log in using their email and password.
    - If the user attempts to register for an event and is not logged in, it will give the user the option to register an account or log in.
    - If the user is not already logged in, it will give the user the option to register a new account.
    - If already logged in, the user must log out to register a new account.
3. **View Events on the Explore Page**
    - The explore page is the landing page of the application, displaying events in a list of “Event Boxes”.
    - Events can be searched by keywords and/or categories, including music, arts, outdoors, food & drinks, sports, and health & wellness. Keywords include sponsoring organization for the event, event name, and event location. The events listed will represent the inputted search.
        - List can be sorted by: low to high price, closest to farthest distance from users location, soonest to latest date, and alphabetically
    - Indicate range of dates of events the user is available for events
4. **View Map of Events**
    - The Explore page has the option to view the events on a map.
    - Upon clicking map view, a local map will appear. It will show the user’s location as a blue pin and the event locations as a green pin. The events that appear will be the results of the user’s search and/or filters.
    - Upon clicking an event pin, an “Event box” will appear with basic information (event name, location, date, time, cost, and sponsoring organization) and a button to register for the event. If the “Event box” is ‘swiped up’ on then a list of “Event boxes” will show (refer to #3), similar to the default results view on the Explore page.
5. **Register for Events**
    - The user will be able to register for an event either from the explore page or from the map when the “event box” is displayed and the user clicks the register button.
    - Upon registering for an event, the user will see additional information about the event (venue photos, description of event, category, parking, number of people registered for the event).
    - If the user attempts to register for an event and is not logged in, it will redirect the user to the login page.
    - If the user is logged in and not registered for this event yet, it will check if the user is already registered for another event at this time and display an appropriate message (“You are already registered for this event, do you want to continue to register for this event or unregister from the conflicting event”).
    - If the user is logged in and is not registered for any other events at the same time, it will successfully register the user for the event.
    - If you are already registered for the event and click the register button an appropriate error message will show (“You are already registered for this event do you want to unregister”)
6. **Edit Profile**
    - Users will be able to view their account information (name, birthday, photo (if uploaded))
    - Users will be able to view their currently registered events as a list of “Event boxes”
    - Logout button
    - If the user is not logged in, instead of redirecting to the profile page they will automatically be directed to the login page

[](https://www.notion.so/83ed4381ca0148cf8dd8f2f23d119d9f?pvs=21)

### Non-Functional Requirements

1. Security
    1. The application will not store personal data (besides location), and will implement security network protocols
2. Speed
    1. EventMe uses optimized sorting and retrieval algorithms to ensure low latency when logging in, searching for events, and retrieving information from Firebase.

## System Model

UML Process Design 

![Screen Shot 2022-09-28 at 11.49.56 PM.png](https://s3-us-west-2.amazonaws.com/secure.notion-static.com/d996b657-2092-4e79-afd8-61378a17bc16/Screen_Shot_2022-09-28_at_11.49.56_PM.png)

UML Process Design demonstrates the users path through the EventMe app from launch to exit

## Architectural Design

Our overall architectural style is layered because it allows our application design to be based on increasing levels of abstraction, helps to ensures customer security because the database cannot be directly accessed without user authentication, and allows for maintainability and reusability of different components. The first layer, User Interface, displays all visuals and interacts with the user, and will make calls to the Main Functionality layer. The last two layers combined are usually called the Data Layer. In this app, these two layers are split for convenience. System Functionality allows for connectivity, and security processes, while Infrastructure allows for a database. Our application will be using Firebase specifically as our cloud database.

### **Map from Main Components to Functional Requirement:**

**User Interface Layer:**

- View Map
- Explore Page (search, categories, date search)
- Profile

**Main Functionality (Domain Layer):**

- View Event Information
- Reserve and unreserve from event
- Logout, Exit, Refresh View
- Search for events (sort and filter accordingly)

**System Functionality and Infrastructure (Data Layer):**

- Security and Privacy, Storage
- Network Connectivity

![Untitled](https://s3-us-west-2.amazonaws.com/secure.notion-static.com/e315e5d7-56f3-4308-8a2b-ca1e48729b42/Untitled.png)

## Detailed Design

### Classes Mapped to Requirements

- User: A user can search for and register for events.
    - Feature 2: Search for Events near the current user location
    - Feature 3: Register for Events
- Map: The map is displayed to find events near the current user location.
    - Feature 2: Search for Events on a Map
- Location: Location object is used to keep track of where Events are in relation to each other and the users current location.
    - Feature 2: Search for Events on a Map
- Events: Events are stored with certain characteristics such as their location, date, time, and price. These can searched for by different filters and registered for by users.
    - Feature 1: Explore Events
    - Feature 3: Register for Events

### UML Class Diagram

![Screen Shot 2022-10-10 at 10.32.39 PM.png](https://s3-us-west-2.amazonaws.com/secure.notion-static.com/39788c34-f502-422f-a9e3-c428d6109268/Screen_Shot_2022-10-10_at_10.32.39_PM.png)

### **UML Use Case Scenario Diagrams**

![Screen Shot 2022-10-10 at 10.36.38 PM.png](https://s3-us-west-2.amazonaws.com/secure.notion-static.com/911a7f03-9d19-414d-a757-2a4bc8c9fb36/Screen_Shot_2022-10-10_at_10.36.38_PM.png)

![Screen Shot 2022-10-10 at 10.38.18 PM.png](https://s3-us-west-2.amazonaws.com/secure.notion-static.com/883e1752-5874-45a4-948e-67cbf99546c2/Screen_Shot_2022-10-10_at_10.38.18_PM.png)

![Screen Shot 2022-10-10 at 10.39.03 PM.png](https://s3-us-west-2.amazonaws.com/secure.notion-static.com/ffb74cbe-d097-4f32-89cc-7e2dee0d2203/Screen_Shot_2022-10-10_at_10.39.03_PM.png)

![Screen Shot 2022-10-10 at 10.39.39 PM.png](https://s3-us-west-2.amazonaws.com/secure.notion-static.com/19c20be5-7859-43d2-b13b-a8b52ac387a3/Screen_Shot_2022-10-10_at_10.39.39_PM.png)

![Screen Shot 2022-10-10 at 10.40.07 PM.png](https://s3-us-west-2.amazonaws.com/secure.notion-static.com/4e4c0dcb-eae7-4570-b550-d4850d8e450d/Screen_Shot_2022-10-10_at_10.40.07_PM.png)

### System Refinement

The following explains the relationships between the main system components mentioned in the architectural design, the classes in the class diagram.

User Interface

- Display login/signup
    - Classes: User
- Display user profile
    - Classes: User, Events
- Display Map
    - Classes: Map, Location, Event
- Display Event boxes (event
    - Classes: Events

Main Functionality

- Login/logout
    - Classes: User, Firebase
- Create an Account
    - Classes: User, Firebase
- Register/unregister for events
    - Classes: User, Firebase

System Functionality and Infrastructure

- Retrieve information from database
    - Classes: Users, Events, Firebase
- Modify information in database
    - Classes: Users, Firebase
- End logged in session
    - Classes: Firebase
 
## Black-box Testing

All 13 tests were built using Espresso
 and are located at *app/java/**com.example.eventme(androidTest)***

1. LoginTest() ✅ 
    - What test does:
        - Launches Login Page —> checks that there exists a EditText for user name, EditText for password, and a Log In button
    - Rationale behind test:
        - Check that the User is able to view the Login page and action buttons
    - Bug Details (if any):
        - N/A
2. RegisterTest() ✅ 
    - What test does:
        - Launches Register Page —> checks that there exists a EditText for name, email, birthdate, password, and a button for register
    - Rationale behind test:
        - Check that the User is able to view the Login page and action buttons
    - Bug Details (if any):
        - N/A
3. SuccessfulLoginTest()✅ 
    - What test does:
        - Launches Login Page —>Fills in data —> verifies that user was successfully logged by clicking on the profile page and verifying  name and date of brith are correct
    - Rationale behind test:
        - Checks that the logged in users data was fetched from the database and populated on the Profile Page/Activity
    - Bug Details (if any):
        - N/A
4. DuplicateEmailRegisterTest() ✅ 
    - What test does:
        - Launches Register Page and fills in data —> sends user to Login Page since there is an account already registered with that email
    - Rationale behind test:
        - Check that there is only one account per email —> no duplicate emails
    - Bug Details (if any):
        - N/A
5. InvalidLoginTest()✅ 
    - What test does:
        - Launches Log In Page and fills in data—> Throws Error message that no account can be found for that email
    - Rationale behind test:
        - Checks the account authentication with the firebase database —> user cannot log in without a valid (registered) account
    - Bug Details (if any):
        - N/A
6. ProfileTest()✅ 
    - What test does:
        - Launches Main Activity → Launches Profile Page (from navigation bar)—> checks that there exists a button for ‘create account’ and ‘sign in’
    - Rationale behind test:
        - Check that the User can access the Profile page from the navigation bar
    - Bug Details (if any):
        - N/A
7. ProfileRedirect1Test()✅ 
    - What test does:
        - Launches Profile Page —> checks that there exists a button for ‘sign in’ → redirects to Login Page
    - Rationale behind test:
        - Check that the User is able to access the Login Page from the Profile Page
    - Bug Details (if any):
        - N/A
8. ProfileRedirect2Test()✅ 
    - What test does:
        - Launches Profile Page —> checks that there exists a button for ‘create account’ → redirects to Register Page
    - Rationale behind test:
        - Check that the User is able to access theRegister Page from the Profile Page
    - Bug Details (if any):
        - N/A
9. MapTest()✅ 
    - What test does:
        - Launches Explore Page —> navigates to Map → checks that the Google API Map loads correctly
    - Rationale behind test:
        - Check that the User is able to see the Map on the Map page
    - Bug Details (if any):
        - N/A
10. RadioButtonTest()✅ 
    - What test does:
        - Launches Explore Page —> clicks on all radio buttons → checks that they function correctly
    - Rationale behind test:
        - Check that the user is able to use the radio buttons
    - Bug Details (if any):
        - N/A
11. SpinnerTest()✅ 
    - What test does:
        - Launches Explore Page —> verify the spinner is displays & is able to be clicked on and display a list of categories
    - Rationale behind test:
        - Check that the user is able to use the spinner to filter events by category
    - Bug Details (if any):
        - N/A
12. SignOutTest()✅ 
    - What test does:
        - Launches Profile Page —> Sign’s in a User —> navigates to Explore→and returns to profile page to ensure the user can successfully Sign Out
    - Rationale behind test:
        - Check that a user can log out of their profile and return to the guest state
    - Bug Details (if any):
        - N/A
13. RVTest()✅ 
    - What test does:
        - Launches Explore Page —> verify the recycler view holding the event boxes displays
    - Rationale behind test:
        - Check that the user is able to view the recycler view with a list of events
    - Bug Details (if any):
        - N/A

## 5. White-box Testing

All 18 tests were built using **JUnit**
 and are located at *app/java/**com.example.eventme(androidTest)***

- **Log-In**

*`checkLoginInfo()` returns 0 if true, and another integer identifying the problem if an input is incorrectly formatted*

1. invalidEmail() ✅ 
    - Runs the checkLoginInfo method with incorrect email (the email not having an @ sign) and returns 1 (meaning failed)
2. invalidPassword()✅
    - Runs the checkLoginInfo method with incorrect password (password is shorter than 6 characters) and returns 2(meaning failed)
3. validInfo()✅
    - Runs the checkFields method with correct inputs (email having an @ sign and the password being longer than 6 characters) and returns 0 (meaning success)
- **Sign-Up**

`checkReginfo()` *returns 0 if true, and another integer identifying the problem if an input is incorrectly formatted*

1. emptyName() ✅
    - Inputs an empty name (“”) into the checkReginfo method and returns 1 (meaning failed)
2. emptyEmail()✅
    - Inputs an empty email (””) into the checkReginfo method and returns 2 (meaning failed)
3. emptyDOB()✅
    - Inputs an empty birthdate (””) into the checkReginfo method and returns 3 (meaning failed)
4. emptyPassword()✅
    - Inputs an empty password (””) into the checkReginfo method and returns 4 (meaning failed)
5. InvalidPassword()✅
    - Inputs an invalid password (one that is shorter than 6 characters) into the checkData method and returns 5 (meaning failed)
6. InvalidName()✅
    - Inputs an invalid name (one with no space between first and last name) into the checkReginfo method and returns 6 (meaning failed)
7. invalidEmail()✅
    - Inputs an invalid email (one with no @ sign) into the checkReginfo method and returns 7 (meaning failed)
8. validInfo()✅
    - Inputs information that all follows the guidelines into the checkData method and returns 0 (meaning success)
- **Explore (Search)**
1. searchCategory()✅ 
    - Ensures correct output matches key word entered in search bar
2. searchSoonest()✅ 
    - Ensures events are displayed in order by soonest date to farthest by date when key word entered in search bar
3. searchName()✅ 
    - Ensures the correct event is returned by the event name key word entered into the search bar
4. searchLocation()✅ 
    - Ensures the correct event is returned by the event location key word entered into the search bar
5. sortAlpha()✅ 
    - Ensures events are displayed in alphabetical ordered when key word entered in search bar
6. sortDistance()✅ 
    - Ensures events are displayed by closest to farthest distance from logged in users current location
7. sortCost()✅
    - Ensures events are displayed in descending price order when key word entered in search bar
  

## The Sprint Plan

- Product Backlog
    
    **User Stories** from EventMe 2.2
    
    1. User wants to login to an account
        - Status: Fulfilled ✅
    2. User wants to register for an account
        - Status: Fulfilled ✅
    3. User wants to register for an event
        - Status: Fulfilled ✅
    4. User wants to cancel registration for an event
        - Status: Fulfilled ✅
    5. User wants to view profile information
        - Status: Fulfilled ✅
- Sprint Backlog
    1. User wants to be able to see more information about an event 
        1. Status: Fulfilled ✅
    2. User will receive an email confirmation when registering for an event
        1. Status: Fulfilled ✅
    3. User will be able to choose a category to view events
        1. Status: Fulfilled ✅
    4. User be able to upload and view a profile picture 
        1. Status: Fulfilled ✅

