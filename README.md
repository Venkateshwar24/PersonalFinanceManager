# Personal Finance Manager - Android App

A production-quality personal finance management application built with Jetpack Compose, following MVVM architecture and Android best practices.

## 📱 Features

### Current Implementation (Home Screen)
- **Balance Dashboard**: Display current account balance prominently
- **Interactive Chart**: Line chart showing balance history over multiple time periods (1D, 5D, 1M, 3M, 6M, 1Y)
- **Quick Recipients**: Horizontal scrollable list of contacts for quick transfers
- **Transaction History**: Recent transactions with categories, icons, and amounts
- **Bottom Navigation**: Three-tab navigation (Home, Stats, Profile)
- **Floating Action Button**: Quick access to add new transactions

## 🏗️ Architecture

### MVVM Pattern
```
UI Layer (Composables)
    ↓
ViewModel (StateFlow)
    ↓
Repository (Data abstraction)
    ↓
Data Source (Mock data)
```

### Project Structure
```
app/src/main/java/com/demo/personalfinancemanager/
├── data/
│   ├── model/              # Data classes
│   │   ├── Transaction.kt
│   │   ├── Category.kt
│   │   ├── Recipient.kt
│   │   ├── User.kt
│   │   ├── TransactionType.kt
│   │   └── BalanceDataPoint.kt
│   ├── source/             # Data sources
│   │   └── MockDataSource.kt
│   └── repository/         # Repository layer
│       └── FinanceRepository.kt
├── ui/
│   ├── theme/              # Material3 theme
│   │   ├── Color.kt
│   │   ├── Type.kt
│   │   └── Theme.kt
│   ├── navigation/         # Navigation setup
│   │   └── Screen.kt
│   ├── screens/
│   │   ├── home/           # Home screen
│   │   │   ├── HomeScreen.kt
│   │   │   ├── HomeViewModel.kt
│   │   │   ├── HomeUiState.kt
│   │   │   └── components/
│   │   │       ├── HomeHeader.kt
│   │   │       ├── BalanceDisplay.kt
│   │   │       ├── ChartCard.kt
│   │   │       ├── RecipientsSection.kt
│   │   │       └── TransactionHistorySection.kt
│   │   ├── stats/          # Stats screen (placeholder)
│   │   │   └── StatsScreen.kt
│   │   └── profile/        # Profile screen (placeholder)
│   │       └── ProfileScreen.kt
│   └── MainScreen.kt       # Main container with bottom nav
├── util/                   # Utilities
│   └── FormatUtils.kt
└── MainActivity.kt         # Entry point
```

## 🛠️ Tech Stack

### Core Technologies
- **Kotlin**: Modern, concise programming language
- **Jetpack Compose**: Declarative UI framework
- **Material Design 3**: Latest Material Design guidelines
- **Coroutines & Flow**: Asynchronous programming and reactive streams

### Libraries
- **Navigation Compose**: Type-safe navigation
- **Lifecycle Components**: ViewModel and lifecycle-aware components
- **MPAndroidChart**: Interactive chart visualization
- **Coil**: Efficient image loading
- **Material Icons Extended**: Comprehensive icon set

### Architecture Components
- **StateFlow**: State management
- **Repository Pattern**: Data layer abstraction
- **MVVM**: Separation of concerns
- **Dagger 2**: Dependency Injection for repositories and ViewModels

## 📊 Data Models

### Transaction
Represents a financial transaction (credit or debit)
- ID, title, subtitle, amount
- Type (CREDIT/DEBIT)
- Category with icon
- Date and optional recipient

### Category
Predefined transaction categories
- Food, Transport, Shopping, Entertainment
- Salary, Bank, Investment
- Each with Material icon

### Recipient
Quick transfer contacts
- Name, avatar URL
- Online status

### User
Current user information
- Name, email, avatar
- Current balance

## 🎨 UI Components

### Home Screen Components

#### 1. HomeHeader
- Welcome message with user name
- Notification bell icon button

#### 2. BalanceDisplay
- Large, prominent balance amount
- "Balance" label

#### 3. ChartCard
- MPAndroidChart line chart
- Interactive balance history
- Period selector (1D, 5D, 1M, 3M, 6M, 1Y)
- Smooth cubic bezier curves

#### 4. RecipientsSection
- Horizontal scrollable list
- Circular avatars loaded with Coil
- Recipient names

#### 5. TransactionHistorySection
- Vertical scrollable list
- Category icons in rounded containers
- Transaction title and subtitle
- Color-coded amounts (green for credit, red for debit)

## 🚀 Getting Started

### Prerequisites
- Android Studio (latest version)
- JDK 11 or higher
- Android SDK API 28+

### Build and Run
1. Open project in Android Studio
2. Sync Gradle files
3. Run on emulator or physical device (API 28+)

### Dependencies Setup
All dependencies are configured in `build.gradle.kts`:
- Jetpack Compose BOM
- Navigation Compose
- Lifecycle components
- Coroutines
- MPAndroidChart (via JitPack)
- Coil
- Material3
- Dagger 2 with KSP (no kapt)

## 🎯 Design Decisions

### Why Mock Data?
- Rapid prototyping and development
- Easy to test UI components
- Foundation ready for Room database migration
- No backend dependency for initial development

### Why MVVM?
- Clear separation of concerns
- Testable business logic
- Reactive UI updates with StateFlow
- Industry standard for Android apps

### Why Jetpack Compose?
- Modern declarative UI
- Less boilerplate than XML
- Better state management
- Improved performance

### Why MPAndroidChart?
- Mature and stable library
- Rich chart customization
- Good performance
- Easy integration with Compose via AndroidView

## 📝 Code Quality

### Best Practices Implemented
- ✅ Comprehensive documentation and comments
- ✅ Clear and descriptive naming conventions
- ✅ Proper package structure
- ✅ Separation of concerns
- ✅ Reactive state management
- ✅ Reusable composable components
- ✅ Type-safe navigation
- ✅ Material Design 3 theming

### Code Organization
- Each screen has its own package
- Components are modularized
- Utilities are separated
- Data layer is abstracted

## 🔄 State Management

### UI State Pattern
```kotlin
sealed interface HomeUiState {
    object Loading : HomeUiState
    data class Success(...) : HomeUiState
    data class Error(val message: String) : HomeUiState
}
```

### Data Flow
1. ViewModel fetches data from Repository
2. Repository provides data from DataSource
3. ViewModel combines multiple streams
4. UI observes StateFlow and reacts to changes

## 🎨 Theming

### Color Scheme
- Primary: Dark (#1A1A1A)
- Background Light: Light gray (#F5F5F5)
- Surface: White (#FFFFFF)
- Credit Green: #4CAF50
- Debit Red: #FF5252
- Chart Background: Dark with white line

### Typography
- Display: Large balance amounts
- Headline: Section headers
- Title: Card titles
- Body: Regular content
- Label: Buttons and small text

## 🔮 Future Enhancements

### Phase 2 (Next Steps)
- [ ] Room Database integration
- [ ] Add/Edit transaction functionality
- [ ] Transaction details screen
- [ ] Category management
- [ ] Search and filter
- [ ] Statistics and analytics screen
- [ ] User profile and settings

### Phase 3 (Advanced Features)
- [ ] Optionally migrate to Hilt on top of Dagger for reduced boilerplate
- [ ] Backend integration (REST API)
- [ ] User authentication
- [ ] Budget tracking
- [ ] Recurring transactions
- [ ] Export data (CSV, PDF)
- [ ] Dark mode support
- [ ] Biometric authentication
- [ ] Notifications

## 🧪 Testing

### Testing Strategy (Future)
- Unit tests for ViewModels
- Unit tests for Repository
- UI tests for Composables
- Integration tests
- End-to-end tests

## 📚 Learning Resources

### Key Concepts Used
- Jetpack Compose
- Material Design 3
- MVVM Architecture
- Kotlin Coroutines
- StateFlow & Compose State
- Navigation Compose
- Repository Pattern

## 📄 License

This project is for educational and portfolio purposes.

## 👨‍💻 Development

### Code Style
- Kotlin coding conventions
- Compose best practices
- Clean architecture principles
- SOLID principles

### Version Control
- Clear commit messages
- Feature-based branching (recommended)
- Semantic versioning

## 🙏 Acknowledgments

- Material Design 3 Guidelines
- Android Jetpack Documentation
- MPAndroidChart Library
- Coil Image Loading Library

---

**Status**: ✅ Home Screen Complete - Ready for Phase 2

**Last Updated**: November 5, 2025

## 🧩 Dependency Injection (Dagger 2 with KSP)

This project uses Dagger 2 to provide a lightweight, explicit DI setup without Hilt.

Structure:
- `di/AppComponent.kt` – App-wide graph with `@Singleton` scope
- `di/ViewModelModule.kt` – Multibinding map for ViewModels and a `ViewModelProvider.Factory`
- `di/ViewModelKey.kt` – MapKey for ViewModel bindings
- `di/DaggerViewModelFactory.kt` – Factory used by Compose screens
- `PersonalFinanceManagerApp.kt` – Initializes `AppComponent` on startup

Usage:
- `MainActivity` is injected with `ViewModelProvider.Factory` and passes it to `MainScreen`
- Screens create ViewModels via `viewModel(factory = viewModelFactory)`
- `HomeViewModel` uses constructor injection: `@Inject constructor(FinanceRepository)`

Build configuration:
- Plugin: `com.google.devtools.ksp`
- Dependency: `ksp("com.google.dagger:dagger-compiler:<version>")`

Add a new ViewModel:
1. Add `@Inject constructor(...)` to your `ViewModel`
2. Bind it in `ViewModelModule`:
    ```kotlin
    @Binds @IntoMap @ViewModelKey(MyViewModel::class)
    abstract fun bindMyViewModel(vm: MyViewModel): ViewModel
    ```
3. Use the injected factory: `val vm: MyViewModel = viewModel(factory = viewModelFactory)`

