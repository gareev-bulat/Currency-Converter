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


## 📦 Dependencies


org.jsoup:jsoup:1.17.2 - HTML scraping for live rates
com.google.android.material:material:1.11.0 - Material 3 widgets
AndroidX – AppCompat, ConstraintLayout, DrawerLayout - Core UI & navigation


## Important Info

Exchange-rate data by https://www.x-rates.com