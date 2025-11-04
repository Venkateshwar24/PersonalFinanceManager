# Quick Start Guide - Personal Finance Manager

## 🚀 Running the Application

### Method 1: Android Studio (Recommended)

1. **Open Project**
   ```
   File → Open → Select 'PersonalFinanceManager' folder
   ```

2. **Sync Gradle**
   - Android Studio will automatically prompt to sync
   - Or click: File → Sync Project with Gradle Files
   - Wait for all dependencies to download

3. **Select Device**
   - Choose an emulator or connected physical device
   - Minimum API Level: 28 (Android 9.0)
   - Recommended: API 34+ for best Material3 experience

4. **Run**
   - Click the green Run button (▶️)
   - Or press: Shift + F10 (Windows/Linux) or Ctrl + R (Mac)

### Method 2: Command Line

```bash
# Windows PowerShell
cd D:\PersonalFinanceManager
.\gradlew installDebug

# Start the app manually on device
adb shell am start -n com.demo.personalfinancemanager/.MainActivity
```

## 📱 What to Expect

### On Launch:
1. App displays a loading indicator briefly
2. Home screen appears with:
   - Welcome message: "Welcome, John!"
   - Balance: $13,553.00
   - Interactive chart with balance history
   - 5 recipient avatars (circular)
   - List of 8 recent transactions

### User Interactions Available:

#### ✅ Working Features:
1. **Chart Period Selection**
   - Tap any period button (1D, 5D, 1M, 3M, 6M, 1Y)
   - Chart updates with new data range

2. **Bottom Navigation**
   - Tap Home icon → Home screen (current)
   - Tap Stats icon → "Statistics Coming Soon" placeholder
   - Tap Profile icon → "Profile Coming Soon" placeholder

3. **Floating Action Button (FAB)**
   - Tap + button → Toast: "Add Transaction"

4. **Notification Bell**
   - Tap bell icon → Toast: "Notifications"

5. **Scrolling**
   - Vertical scroll on main content
   - Horizontal scroll on recipients
   - Horizontal scroll on period selector

#### 🔄 Currently Placeholders (Planned):
- Tapping on recipients (toast or no action)
- Tapping on transactions (toast or no action)
- Stats screen content
- Profile screen content

## 🧪 Testing Scenarios

### 1. UI Responsiveness
- ✅ Scroll up and down smoothly
- ✅ Switch between navigation tabs
- ✅ Change chart periods

### 2. State Management
- ✅ App loads data (Loading → Success)
- ✅ Chart updates when period changes
- ✅ Navigation state preserved when switching tabs

### 3. Visual Inspection
- ✅ Colors match design (dark bottom nav, light content)
- ✅ Balance displayed prominently
- ✅ Chart has white line on dark background
- ✅ Transactions show green (+) and red (-)
- ✅ Icons display correctly
- ✅ Avatars load (requires internet)

### 4. Error Scenarios (Manual Testing)
```kotlin
// To test error state, temporarily modify MockDataSource:
// Throw an exception in any function
throw Exception("Test error")
```

## 📊 Mock Data Details

### Hardcoded Test Data:

**User:**
- Name: John
- Balance: $13,553.00

**Recipients (5):**
1. Sarah (online)
2. Michael (online)
3. Emma (offline)
4. James (offline)
5. Olivia (online)

**Transactions (8):**
1. Food - Payment: -$40.99
2. AI-Bank - Deposit: +$460.00
3. Shopping - Payment: -$125.50
4. Transport - Payment: -$15.00
5. Salary - Deposit: +$3,500.00
6. Entertainment - Payment: -$45.00
7. Food - Payment: -$32.75
8. Investment - Deposit: +$500.00

**Categories (7):**
- Food 🍽️
- Transport 🚗
- Shopping 🛒
- Entertainment 🎬
- Salary 🏦
- Bank 🏦
- Investment 📈

## 🔧 Troubleshooting

### Issue: Gradle Sync Failed
**Solution:**
- Check internet connection
- File → Invalidate Caches → Invalidate and Restart
- Ensure JDK 11+ is installed

### Issue: Images Not Loading
**Solution:**
- Check internet permission in AndroidManifest.xml
- Verify internet connection on emulator/device
- Images use pravatar.cc - requires internet

### Issue: Chart Not Displaying
**Solution:**
- JitPack repository must be in settings.gradle.kts
- MPAndroidChart dependency must be downloaded
- Try: File → Sync Project with Gradle Files

### Issue: App Crashes on Launch
**Solution:**
- Check minimum SDK (API 28)
- View Logcat for error messages
- Rebuild project: Build → Rebuild Project

## 📸 Expected Screenshots

### Home Screen Should Show:
```
┌─────────────────────────┐
│ Welcome, John!       🔔 │
│                         │
│ $ 13,553.00             │
│ Balance                 │
│                         │
│ ┌─────────────────────┐ │
│ │   Chart (line)      │ │
│ │                     │ │
│ │ [1D 5D 1M 3M 6M 1Y] │ │
│ └─────────────────────┘ │
│                         │
│ Recipients              │
│ ○ ○ ○ ○ ○ → (scroll)   │
│                         │
│ Transactions history    │
│ 🍽️ Food          -40.99 │
│ 🏦 AI-Bank      +460.00 │
│ 🛒 Shopping     -125.50 │
│ ...                     │
│                         │
├─────────────────────────┤
│ [🏠]  [📊]  [👤]    [+] │
└─────────────────────────┘
```

## 🎨 Visual Verification Checklist

- [ ] Top bar is light gray background
- [ ] Welcome text has "John" in bold
- [ ] Balance is large and prominent
- [ ] Chart card is dark (black)
- [ ] Chart line is white
- [ ] Period buttons respond to taps
- [ ] Recipients are circular with images
- [ ] Transaction icons in rounded squares
- [ ] Green amounts have + prefix
- [ ] Red amounts have - prefix
- [ ] Bottom nav is dark
- [ ] FAB is dark with + icon
- [ ] Smooth scrolling

## 🐛 Known Limitations (By Design)

1. **Static Data**: All data is hardcoded mock data
2. **No Persistence**: Changes don't save (no database yet)
3. **No Real Actions**: Taps show toasts, no real functionality
4. **Internet Required**: For recipient avatars (pravatar.cc)
5. **Limited Transactions**: Only 8 hardcoded items

## 📝 Next Development Steps

When you're ready to extend:

1. **Add Room Database**
   ```kotlin
   // Replace MockDataSource with Room DAO
   @Database(entities = [Transaction::class, ...])
   abstract class AppDatabase : RoomDatabase()
   ```

2. **Add Transaction Form**
   ```kotlin
   // Create AddTransactionScreen.kt
   // Hook up to FAB click
   ```

3. **Implement Hilt**
   ```kotlin
   // Add @HiltViewModel to HomeViewModel
   // Inject Repository
   ```

4. **Add More Screens**
   - Transaction details
   - Statistics with charts
   - User profile and settings

## 💻 Development Environment

### Recommended Setup:
- **Android Studio**: Hedgehog (2023.1.1) or newer
- **JDK**: 11 or higher
- **Gradle**: 8.8.0 (configured)
- **Min SDK**: 28
- **Target SDK**: 36
- **Kotlin**: 2.0.0

### Build Variants:
- **Debug**: For development (current)
- **Release**: For production (not configured yet)

## 📱 Device Requirements

### Minimum:
- Android 9.0 (API 28)
- 2 GB RAM
- Internet connection (for images)

### Recommended:
- Android 12+ (API 31+) for best Material3
- 4 GB RAM
- Modern device (2020+)

## 🎯 Success Criteria

The app is working correctly if:
- ✅ Launches without crashes
- ✅ Shows home screen with all components
- ✅ Chart updates when periods are selected
- ✅ Navigation works between tabs
- ✅ Scrolling is smooth
- ✅ Colors match design
- ✅ No visible errors or warnings

---

**Enjoy your Personal Finance Manager! 🎉**

For questions or issues, check the IMPLEMENTATION_SUMMARY.md and README.md files.
