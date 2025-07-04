# Currency Converter 📱💱

A modern Android app (Material 3 + Java) that lets you…

| Screen | What it does |
| ------ | ------------ |
| **Currency Rates** | Scrapes the up-to-date *x-rates.com* table, shows each currency as a Card (flag + name + 2 rates + % change). |
| **Currency Converter** | Converts any amount between 30+ currencies **offline** (after a single fetch) – rates are passed from the table screen, so converting continues to work without network. |
| **Bank-Note Gallery** | Tap through bank-note images and ISO codes for a quick visual reference. |

---

## Prerequisites 🛠️

| Tool | Version - tested | Notes |
| ---- | --------------- | ----- |
| **Android Studio** | Flamingo or higher (incl. Giraffe/Hedgehog) | Uses **Material 1.11.0** & Gradle **8.2+** wrapper. |
| **JDK** | 17 (LTS) | Bundled with recent Studio; no extra setup needed. |
| **Android SDK** | API 34 (Android 14) + **Build-Tools 34.0.0** | Older APIs compile fine, just change `compileSdk` / `targetSdk`. |
| **Internet access** | First run only | Fetches live rates via Jsoup. Afterwards conversion works offline for the current session. |




## ✨ Features
| | |
|---|---|
| ⚡ **Live FX rates** fetched with <br/> <code>Jsoup</code> from <em>x-rates.com</em> | 📶 **Offline converter** – once rates are cached you can convert without internet |
| 🎨 **Material 3 UI** – CardView rows, Chip tags, Navigation Drawer | 🛡️ **Robust handling** – null-safe, toast feedback, graceful failures |


## 🚀 Quick Start

```bash
# clone
git clone https://github.com/<your-handle>/CurrencyConverter.git
cd CurrencyConverter

# build & install
./gradlew assembleDebug
adb install app/build/outputs/apk/debug/app-debug.
```


## 🗂️ Project Layout
```
CurrencyConverter/
├─ app/
│   ├─ src/main/java/com/example/currencyconverter/
│   │   ├─ MainActivity.java        ← rates + drawer
│   │   ├─ CurrencyConverter.java   ← converter screen
│   │   ├─ BankBill.java            ← bank-note gallery
│   │   ├─ CustomArrayAdapter.java  ← list card adapter
│   │   └─ ListItemClass.java       ← POJO holder
│   ├─ src/main/res/
│   │   ├─ layout/
│   │   ├─ drawable/   ← 50+ flag PNGs
│   │   └─ values/
│   └─ build.gradle
├─ docs/            ← screenshots & banner
├─ .gitignore
├─ README.md
```


## 📦 Dependencies

```
org.jsoup:jsoup:1.17.2 - HTML scraping for live rates
com.google.android.material:material:1.11.0 - Material 3 widgets
AndroidX – AppCompat, ConstraintLayout, DrawerLayout - Core UI & navigation
```

## Important Info

Exchange-rate data by https://www.x-rates.com
