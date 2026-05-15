# Siri‑Dhanya Hub 🌾  
Android App Development using GenAI – Agriculture (Millets)

> VTU MindMatrix Internship Project – Project Title: 74  

Siri‑Dhanya Hub is an Android app that showcases how millets (Siri‑Dhanya) can be promoted through a simple “Millet Value Chain” application.  
It serves **both farmers and consumers** by showing simulated mandi prices, millet recipes, health benefits, and a simulated direct‑buy flow from local Farmer Producer Organizations (FPOs).

---

## 📌 Problem Statement

Karnataka is a leader in millets (Siri‑Dhanya). However:

- Small farmers often do not know the current **market price** of millets in different cities.  
- Consumers do not know how to **cook different types of millets** (Ragi, Navane, Sajje, Saame, Baragu), or why they are healthier.  

There is a need for a simple mobile app that brings together **price information, recipes, health facts, and farmer connectivity** in one place.

---

## 🎯 Objectives

- Build a “**Millet Value Chain**” Android app that serves both farmers and consumers.  
- Show **real‑time (simulated)** mandi prices for millets in cities like Davangere and Bengaluru, including **7‑day High/Low** and price trends.  
- Provide **step‑by‑step Kannada recipes** for millet‑based dishes, with the ability for users to **save favourites**.  
- Present simple **health benefits** for each millet type in everyday language.  
- Simulate a **Direct Buy** flow where users can discover local farmers / FPOs and add millets to a **cart**.  
- Design an **earthy, natural UI** using brown/green tones that reflect agriculture and sustainability.  
- Experiment with **GenAI** (Perplexity / ChatGPT) to speed up development and content creation.

---

## ✨ Features

### 1. Mandi Watch

- Shows a list of millets with:
  - City (e.g., Davangere, Bengaluru)  
  - Current **simulated price** per kg  
  - **Trend arrow** (↑ rising / ↓ falling)  
  - **7‑day HIGH/LOW** prices  
- Includes a **search bar** to filter by city or millet name.  
- Displays a climate note: e.g., “Millets require ~70% less water than paddy — climate‑resilient crops.”

### 2. Recipe Lab

- Millet‑based recipes such as **Ragi Mudde, Ragi Dosa, Navane Upma, Navane Pongal, Baragu Kheer, Sajje Rotti**, etc.  
- Each recipe card shows:
  - Emoji and name  
  - Millet type  
  - Language (Kannada)  
  - Food **image** from drawables  
- Step‑by‑step instructions shown in a **dialog** (stored as `"step1|step2|..."`).  
- Users can **save / un‑save** recipes with a heart icon; saved count is shown.  
- Filter chips for millet type: `All, Ragi, Navane, Saame, Sajje, Baragu` plus a **text search**.

### 3. Health Benefits

- Cards with millet‑specific health information, e.g.:
  - Ragi – high in calcium, good for bones.  
  - Navane – low glycemic index, good for diabetics.  
  - Baragu – heart‑friendly.  
  - Saame – good plant protein.  
- Each card has emoji, title, millet name, description, and optional **image**.  
- **Search** by millet name or health benefit.

### 4. Direct Buy (Simulated)

- List of 6 farmers with Indian names, alternating male/female:
  - Ravi, Lakshmi, Manjunath, Savitha, Suresh, Kavya  
- Each farmer card shows:
  - Location (Tumakuru, Davangere, Chitradurga, etc.)  
  - Millets they grow (Ragi, Navane, Sajje, Saame, Baragu, Oodalu, Korale)  
  - FPO name and contact number  
  - **Farmer photo** from drawable resources  
- **Add to cart** button simulates selecting millets from that farmer.  
- Top banner clearly states this is a **simulated** connection with FPOs.

### 5. Cart & Order Simulation

- Shows all selected millet items with:
  - Farmer name  
  - Millet name  
  - Quantity controls (+/–)  
- Displays **total item count** and a **Clear cart** option.  
- “**Place order (simulation)**” button opens a confirmation dialog:
  > “Order placed! This is a simulated order. In a real app, your details would be sent to the FPO.”

---

## 🧱 Architecture

The app uses a simple **3‑layer structure** with Jetpack Compose and MVVM‑style state management.

- **Presentation Layer (UI)**  
  - `MandiWatchScreen`, `RecipeLabScreen`, `HealthScreen`, `DirectBuyScreen`, `CartScreen`  
  - Reusable composables: `TopBanner`, `RecipeCard`, `HealthFactCard`, `FarmerCard`, `CartItemCard`  
  - Bottom navigation bar with icons (Home, Recipes, Health, Farmers, Cart)

- **State / ViewModel Layer**  
  - `MilletViewModel`, `RecipeViewModel`, `HealthViewModel`, `FarmerViewModel`, `CartViewModel`  
  - Use `mutableStateOf` and `remember` to hold screen state in Jetpack Compose.  
  - Handle filtering, search, favourites, and cart operations.

- **Data Layer**  
  - Entities located in `data/entity/Entities.kt`:
    - `MilletEntity`, `RecipeEntity`, `HealthFactEntity`, `FarmerEntity`, `CartItemEntity`  
  - Currently uses **in‑memory sample data** (simulated), but entities are designed to be used with **Room** in the future.

---

## 🛠️ Technologies Used

- **Language:** Kotlin  
- **UI:** Jetpack Compose  
- **IDE:** Android Studio  
- **Architecture Style:** MVVM‑inspired, Compose state + ViewModel‑like classes  
- **Data Models:** Room‑style entities (no persistent DB wired yet)  
- **Assets:** Drawable images for recipes, millets, and farmer photos  
- **GenAI:** Perplexity / ChatGPT used for code generation, content drafting, and UI ideas

---

## 🚀 Getting Started

### Prerequisites

- Android Studio (Giraffe / Hedgehog or newer)  
- JDK 17 (recommended with recent Android Studio)  
- Android device or emulator (Android 7.0+)

### Setup & Run

## 📦 Repository & APK

- **GitHub Repository:** https://github.com/thejashwini2004/SiriDhanyaHub

2. **Open in Android Studio**

   - `File > Open` → select the project folder.

3. **Sync Gradle**

   - Android Studio will automatically sync.  
   - If prompted, install any missing SDK components.

4. **Run the app**

   - Connect an Android device with USB debugging enabled, or start an emulator.  
   - Click the **Run ▶** button and choose the app module.

---

## 🔮 Future Enhancements

- Connect to a **real backend API** or open data source for live mandi prices.  
- Integrate **Room database** so recipes, favourites, farmers, and cart items persist across app restarts.  
- Implement **user authentication** for farmers and consumers.  
- Send real orders via **WhatsApp/SMS** or a dedicated FPO backend.  
- Add **multi‑language support** (Kannada, English, etc.) using localization.  
- Extend health facts using verified nutrition datasets.

---

## 📚 Acknowledgements

- VTU MindMatrix Internship Program – problem statement and guidance.  
- Android Developers documentation for Jetpack Compose and state management.  
- GenAI tools (Perplexity / ChatGPT‑style assistants) for support in code, content, and design exploration.

---

## 📦 Download APK

You can directly install the latest build of the app using this APK:

- **Siri‑Dhanya Hub APK:** [Download here](https://drive.google.com/file/d/1KYcTCtD-zxqbySi0GKtNFQzAAwjywq6c/view?usp=sharing)

> Note: If installing on a physical device, enable “Install from unknown sources” in Android settings.
