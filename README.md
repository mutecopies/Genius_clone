# 🎶 Genius Clone - JavaFX Music App

A desktop application inspired by [Genius.com](https://genius.com), built in Java using JavaFX. Users can explore songs and lyrics, follow artists, post comments, and suggest lyric edits. Artists can upload songs and albums, and admins manage approvals — all through a visually modern GUI.

---

## 📚 Table of Contents

- [🎯 Project Description](#-project-description)
- [🚀 Features](#-features)
- [🛠️ Technologies Used](#-technologies-used)
- [📸 Demo](#-demo)
- [📦 Usage](#-usage)
- [👥 Roles and Permissions](#-roles-and-permissions)
- [📝 Changelog](#-changelog)
- [👏 Credits](#-credits)

---

## 🎯 Project Description

This is a desktop application inspired by Genius.com, where users can view and comment on song lyrics, follow their favorite artists, and contribute to improving lyrics. Artists can publish songs and albums, and admins oversee approvals. The app was built using object-oriented design and features a clean JavaFX-based GUI.

---

## 🚀 Features

### ✅ **User Authentication**
- Sign up and log in as **User**, **Artist**, or **Admin**
- Role-based navigation and actions
- Login info persisted in files

### 👤 **User Role**
- View song lyrics
- Search for songs, artists, and albums
- Follow artists
- Comment on songs
- Suggest lyric edits
- View profile (name, followed artists, comments)

### 🎤 **Artist Role**
- Create new **songs** and **albums**
- Add **lyrics** to songs
- View and **approve/reject lyric edit requests**
- View their own albums and songs

### 🛡️ **Admin Role**
- Approve or reject **pending artist registrations**
- Review **user-submitted lyric edits** for inactive artists

### 📚 **Albums & Songs**
- Artists can:
  - Create albums (with title, release date)
  - Add songs to albums (with tracklist order)
- View album pages with:
  - Title, artist, release date, full tracklist
  - Clickable songs that open a **Song Page** to view lyrics
- Each song has:
  - Lyrics display
  - Comment section

---

## 🛠️ Technologies Used

- **Java 17+**
- **JavaFX 17+** – GUI framework
- **SHA-256 Hashing** – for secure password storage
- **File I/O (TXT & JSON)** – to save user data, songs, and edits
- **Object-Oriented Design** – inheritance, interfaces, encapsulation

---

## 📸 Demo
![image](https://github.com/user-attachments/assets/a0ea3b9e-a53c-40ac-90fa-83bf6d5fe705)

USER PAGE
![image](https://github.com/user-attachments/assets/82cecb29-e31a-4bf3-96bd-f7f1ffc158e3)
---

## 📦 Usage

1. Clone the repository:
   ```bash
   git clone https://github.com/your-username/genius-clone-javafx.git
   cd genius-clone-javafx
---

## 🚀 How to Run (JavaFX)

### 📋 Prerequisites

- Java JDK 17+
- JavaFX SDK (Download from [gluonhq.com](https://gluonhq.com/products/javafx/))
- IntelliJ IDEA or VS Code

---

### 🛠️ JavaFX Setup in IntelliJ

1. **Download JavaFX SDK** and extract it.
2. Go to `File > Project Structure > Libraries` → Add the `/lib` folder from the JavaFX SDK.
3. Open `Run > Edit Configurations` and in the **VM Options** field, add:

   ```bash
   --module-path "PATH_TO_FX_LIB" --add-modules javafx.controls,javafx.fxml
_ _ _

## 📜 Changelog

### v1.0

- Initial release  
- Role-based system (`User`, `Artist`, `Admin`)  
- JavaFX user interface completed  
- JSON-based data saving implemented  
- 🔐 Bonus: Secure login using password hashing (SHA-256)  
- 🎨 Bonus: Full JavaFX integration for UI

## 🙌 Credits

- Developed by [mutecopies](https://github.com/mutecopies)
- JavaFX – Used for building the user interface  
- Gson – Used for saving and reading data in JSON format  
- SHA-256 – Used for hashing passwords securely  
- Genius.com – Inspiration for the app's design and functionality  
- IntelliJ IDEA – Primary IDE used for development  
- [OpenJDK](https://openjdk.org/) – Java Development Kit  

