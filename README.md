Quiz App
Welcome to the Quiz App, a feature-rich Android application built to test your general knowledge while showcasing modern Android development skills. This app was developed as part of a selection process for a software company, following the provided specifications. It includes seamless API integration, local data storage, and a smooth user experience.

📱 Features
Fetch Questions from API: Retrieves multiple-choice questions from the Open Trivia Database (OTDB) API to provide fresh and challenging quiz questions.
Store Questions Locally: Uses the Room database to save questions locally, ensuring the app functions offline without needing an active internet connection.
Score Calculation: Automatically calculates your score based on the number of correct answers submitted during the quiz.
Quiz Timer: Implements a timer that limits the time to complete the quiz, making it more engaging and competitive.
High Scores: Keeps track of your best score using SharedPreferences and displays it for future reference.
State Persistence: Ensures that the quiz and timer state are preserved during orientation changes or if the app goes into the background.
Smooth Navigation: Offers seamless navigation between the Home Screen, Quiz Screen, and Result Screen, providing a fluid user experience.
Responsive Design: Handles orientation changes effectively without disrupting the quiz progress or causing data loss.
💡 Tech Stack
Programming Language: Kotlin
Database: Room Database (with SQLite backend)
API: Open Trivia Database (OTDB) API
UI Components: Fragments, Activities, ViewModels
State Management: LiveData, ViewModel, SavedStateHandle
Local Storage: Room Database, SharedPreferences
Navigation: Jetpack Navigation Component
Timer Handling: CountDownTimer
Architecture: MVVM (Model-View-ViewModel)
🛠 How to Build
Clone this repository:

bash
Copy code
git clone https://github.com/Biplavroy2001/QuizApp.git
Open the project in Android Studio.

Build the project and run the app on your device or emulator.

Ensure you have internet access initially to fetch the quiz questions from the API.


# Technologies Used
Retrofit | RxJava3 | Kotlin | ViewBinding 


# API
This project uses the Open Trivia Database API. <a href="https://opentdb.com/">Click here for reference.</a>

# Author
<b>Biplav Roy</b>

find me on linkedin: https://www.linkedin.com/in/biplavroy
