# 🏹 Minecraft Manhunt Plugin

![GitHub release (latest by date)](https://img.shields.io/github/v/release/Mikolaj0524/Manhunt)
![GitHub License](https://img.shields.io/github/license/Mikolaj0524/Manhunt)
![PaperMC](https://img.shields.io/badge/Server-Paper%201.20+-blue)
![Java](https://img.shields.io/badge/Java-17+-orange)
![PlaceholderAPI](https://img.shields.io/badge/PlaceholderAPI-Supported-success)

A **Paper/Spigot plugin** that adds two exciting Manhunt modes:
**Default Manhunt** and **Hunt Manhunt**, with worldborder-based survival mechanics.

---

## ✨ Features

* 🎯 **Two Manhunt types**

  * **Default Manhunt** – classic chase mode where hunters track the runner.
  * **Hunt Manhunt** – the runner must survive for a set time inside a configurable **worldborder**.
* 🧭 **Automatic world handling** – changes and manages worlds automatically.
* 🧰 **In-game control panel** – everything is managed from `/manhunt`.
* ⚙️ **Full PlaceholderAPI support**.
* 👥 **Supports multiple runners and hunters**.

---

## ⚙️ Commands

| Command    | Description                     | Permission |
| ---------- | ------------------------------- | ---------- |
| `/manhunt` | Opens the Manhunt control panel | `manhunt`  |

---

## 🧩 PlaceholderAPI Endpoints

| Placeholder               | Description                          |
| ------------------------- | ------------------------------------ |
| `%manhunt_time%`          | Current game time (minutes, seconds) |
| `%manhunt_type%`          | Current mode (`normal` or `hunt`)    |
| `%manhunt_worldborder%`   | Current worldborder size             |
| `%manhunt_runnersList_X%` | List of runners with ID              |
| `%manhunt_huntersList_X%`   | List of hunters with ID              |

---

## 🧭 Game Modes

### 🕹️ Default Manhunt

The runner’s goal is to beat the game while hunters chase them using compasses.

### 🔥 Hunt Manhunt

The runner must **survive for a specific amount of time** inside a **worldborder**.
Border size and survival time can be adjusted in the `/manhunt` panel.

---

## 🌍 World Handling

* Worlds are automatically managed and switched by the plugin.
* No manual teleportation or setup required.

---

## ⚡ Requirements

* Minecraft **Paper** or **Spigot** server (1.20+ recommended)
* **Java 17+**
* *(Optional)* **PlaceholderAPI** for placeholder support

---

## 🧪 Example Usage

```text
/manhunt
```

Open the GUI control panel to start or configure your Manhunt session.

---

## 📜 Permissions

| Permission | Description                                |
| ---------- | ------------------------------------------ |
| `manhunt`  | Grants access to the Manhunt control panel |

---

## 💾 Installation

1. Download the latest `.jar` from the [Releases](../../releases) page.
2. Place it inside your server’s `/plugins/` folder.
3. Restart or reload your server.
4. Run `/manhunt` to open the panel.

---

## 🧰 Building from Source

```bash
git clone https://github.com/Mikolaj0524/Manhunt.git
cd Manhunt
mvn clean package
```

The compiled `.jar` will be in the `target/` folder.

---

## 🧑‍💻 Contributing

Contributions, bug reports, and feature suggestions are welcome!
Open a Pull Request or an Issue on GitHub to help improve the plugin.

---

## 📜 License

This project is licensed under the **MIT License**.
See the [LICENSE](LICENSE) file for more details.

---

README.md by ChatGPT.
