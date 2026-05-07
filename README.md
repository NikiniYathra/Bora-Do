# Bora Do - Android To-Do List App

Bora Do is a Kotlin-based Android To-Do List application developed for the CS3082 Mobile Computing assignment. It allows users to create multiple to-do lists, manage list items, search content, and store data locally using Room Database.

## Features

- Home screen with developer information
- Create and maintain multiple to-do lists
- Add, edit, and delete to-do items
- Search lists and items
- Local storage using Room Database
- RecyclerView-based display
- MVVM-style architecture using ViewModel and Repository
- Material Design UI components

## Technologies Used

- Kotlin
- Android Studio
- Room Database
- ViewModel and LiveData
- RecyclerView
- Navigation Component
- Material Components

## Project Structure

```text
app/
 └── src/
     └── main/
         ├── java/lk/kdu/ac/mc/todolistapp/
         │   ├── data/
         │   ├── ui/
         │   └── viewmodel/
         └── res/
             ├── layout/
             ├── drawable/
             └── navigation/

## Main Functional Modules
```Home Screen

Displays the welcome message and developer information, with navigation to the list screen.

```Todo Lists

Allows users to create and view multiple to-do lists.

```Todo Items

Allows users to add, edit, delete, search, and manage items inside a selected list.

```Local Database

The app uses Room Database to store lists and items locally on the device.