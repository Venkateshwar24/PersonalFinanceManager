# Personal Finance Manager - An Android Application

A modern, production-quality personal finance app built with Kotlin and Jetpack Compose, following MVVM, Repository, and DI best practices.

## Description

Personal Finance Manager helps users visualize their balance, review recent transactions, and send money to frequent recipients—all wrapped in a clean Material 3 UI. The app is structured for scalability and testability with a clear separation of concerns and dependency injection.

## Demo
![App Demo](https://github.com/Venkateshwar24/PersonalFinanceManager/blob/main/assets/personalFinanceManagerAppDemo.gif)
- Watch the full demo video: [(https://github.com/Venkateshwar24/PersonalFinanceManager/blob/main/assets/personalFinanceManagerDemo.mp4?raw=true)](https://github.com/Venkateshwar24/PersonalFinanceManager/blob/main/assets/personalFinanceManagerAppDemo.mp4)


## Technology and Architecture

- Platform: Android (API 28+), Kotlin
- UI: Jetpack Compose + Material Design 3
- Architecture: MVVM + Repository pattern
- State: Kotlin Coroutines + Flow/StateFlow
- Navigation: Navigation Compose
- Dependency Injection: Dagger 2 (annotation processing via KSP)
- Charts: MPAndroidChart via Compose interop (`AndroidView`)

High-level data flow
```
UI (Composables)
    ↓
ViewModel (StateFlow)
    ↓
Repository (Abstraction)
    ↓
Data Source (Mock data - It will be updated with remote or local source(Room Db))
```

## Libraries Used

- Jetpack Compose BOM (UI toolkit)
- Material3 and Material Icons Extended
- Navigation Compose
- AndroidX Lifecycle (ViewModel)
- Kotlin Coroutines + Flow
- MPAndroidChart (JitPack) for interactive charts
- Coil for image loading
- Dagger 2 for DI (with KSP compiler)

## Features

- Balance dashboard with prominent current balance
- Interactive line chart with period selector (1D, 5D, 1M, 3M, 6M, 1Y)
- Quick recipients carousel with avatars (Coil)
- Recent transaction history with categories and icons
- Bottom navigation (Home, Stats, Profile)
- Floating action button to add transactions

## Project Structure

```
app/src/main/java/com/demo/personalfinancemanager/
├── data/
│   ├── model/              # Data classes (Transaction, Category, Recipient, User, ...)
│   ├── source/             # Data sources (MockDataSource)
│   └── repository/         # Repository layer (FinanceRepository)
├── ui/
│   ├── theme/              # Material3 theme
│   ├── navigation/         # Navigation setup (Screen)
│   ├── screens/
│   │   ├── home/           # Home screen + components
│   │   ├── stats/          # Placeholder
│   │   └── profile/        # Placeholder
│   └── PersonalFinanceManagerScreen.kt       # Main container with bottom nav
├── util/                   # Utilities (FormatUtils.kt)
└── MainActivity.kt        # Entry point
└── PersonalFinanceManagerApp.kt   # Application Class     
```

## Dependency Injection (Dagger 2 with KSP)

- App graph: `di/AppComponent.kt` (`@Singleton` scope)
- ViewModel map bindings + factory: `di/ViewModelModule.kt`, `di/ViewModelKey.kt`, `di/DaggerViewModelFactory.kt`
- App init: `PersonalFinanceManagerApp.kt` builds the component
- Usage: `MainActivity` injects `ViewModelProvider.Factory`; screens obtain VMs via `viewModel(factory = viewModelFactory)`
- Add a new ViewModel: annotate constructor with `@Inject`, bind it in `ViewModelModule`, and request it via the injected factory

Build configuration
- Gradle plugin: `com.google.devtools.ksp`
- Compiler dep: `ksp("com.google.dagger:dagger-compiler:<version>")`

## Getting Started

Prerequisites
- Android Studio (latest)
- JDK 11+
- Android SDK API 28+

Build and Run
1. Open the project in Android Studio
2. Sync Gradle
3. Run on an emulator or device (API 28+)

## Notes on State & UI

- UI observes a sealed `HomeUiState` (Loading/Success/Error) exposed via `StateFlow`
- Compose reads state and updates automatically
- MPAndroidChart is embedded using `AndroidView` for a smooth chart experience

## Roadmap

- Future Enhancements 
  - Room database/ Remote server integration
  - Add/Edit transaction flow and details screen
  - Category management, search, and filtering
  - Stats and analytics screen
  - Profile and settings
- Phase 3
  - Optional migration to Hilt
  - Backend integration (REST), authentication
  - Budgeting, recurring transactions
  - Export (CSV/PDF), dark mode, biometrics, notifications

## License

This project is for educational and portfolio purposes.

---

Status: Home Screen Complete

Last Updated: November 5, 2025

