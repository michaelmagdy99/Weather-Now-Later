# Weather Now & Later

A modularized Android application that fetches and displays current weather as well as a 7-day forecast for a given city. The app is designed using clean architecture principles and implements multiple architectural patterns.

## Features

- **City Input**: Allow users to input a city name.
- **Current Weather Display**: Shows the current weather, including temperature, condition (e.g., cloudy, sunny, rainy), and an appropriate icon.
- **7-Day Forecast**: Displays a 7-day weather forecast in a list format.

## Technical Requirements

### Architecture

- **MVVM**: Used for the city input screen and current weather display.
- **MVI**: Used for the 7-day forecast list.

### Clean Architecture

- **Presentation Layer**
- **Use Cases Layer**
- **Repository Layer**
- **Data Source Layer**: Includes both local storage for the last searched city and remote fetching of weather data.

### Dependency Injection

- **Dagger-Hilt**: Used for dependency injection throughout the application.

### UI

- **Jetpack Compose**: Used for building the user interface.

### Modularity

The application is structured into the following modules:

- **App**: Main module.
- **Core**: Common utilities and shared components.
- **Data**: Data sources and repository.
- **Features**: Sub-modules for each feature/screen (e.g., city input, current weather, 7-day forecast).

### Unit Testing

- Achieved over 80% code coverage with unit tests.
- Utilized a mocking framework for testing.

### CI/CD

- Set up a basic CI/CD pipeline using GitHub Actions that:
  - Lints the code.
  - Runs unit tests.
  - Builds and generates an APK.

- Added dark mode support.

## API Used

The application uses the [OpenWeatherMap API](https://openweathermap.org/api) for fetching weather data. An API key is required to access the data.
