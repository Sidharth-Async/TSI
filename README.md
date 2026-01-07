# 🚦 IoT Smart Traffic Optimizer

A Real-Time Smart Traffic Management System that uses **IoT Sensors, AI, and WebSockets** to optimize traffic flow, reduce congestion, and prioritize emergency vehicles (Ambulances) dynamically.


## 🚀 Key Features

* **Live Traffic Map:** Real-time visualization of city traffic using OpenStreetMap & Leaflet.
* **IoT Integration:** Simulates C-based hardware sensors sending live vehicle counts and speed data.
* **AI Traffic Control:** An intelligent algorithm analyzes traffic density and automatically adjusts traffic light timings (e.g., Green light extended for heavy traffic).
* **Ambulance Priority:** Detects emergency vehicles and triggers a "Green Corridor" protocol.
* **Real-Time Updates:** Uses **WebSockets (STOMP)** to push updates instantly to the frontend without page refreshes.
* **Heatmap Visualization:** Dynamic coloring of roads (Red/Green) based on live congestion levels.

## 🛠️ Tech Stack

* **Frontend:** React.js, Material UI, Leaflet Maps, SockJS (WebSockets).
* **Backend:** Java Spring Boot, Spring WebSocket, Spring Data JPA.
* **Database:** MySQL / MariaDB (or PostgreSQL).
* **IoT/Simulation:** Python (Traffic Simulator), C++ (Sensor Logic).
* **Tools:** Postman, pgAdmin/DBeaver, IntelliJ IDEA, VS Code.

---

## ⚙️ Installation & Setup

### 1. Database Setup (MySQL/MariaDB)

Create a database named `traffic_db` and configure your `application.properties`.

```sql
CREATE DATABASE traffic_db;

```

### 2. Backend (Spring Boot)

1. Navigate to the `backend` folder.
2. Update `src/main/resources/application.properties` with your database credentials.
3. Run the application:
```bash
mvn spring-boot:run

```



### 3. Frontend (React)

1. Navigate to the `traffic-optimizer-ui` repo:
2. Install dependencies:
```bash
npm install

```


3. Start the development server:
```bash
npm run dev

```


Access the app at `http://localhost:5173`.

### 4. Run Traffic Simulation

To see the map come alive, run the Python simulator script. This mimics real sensors sending data to the backend.

```bash
python traffic_simulator.py

```

---

## 🐛 Known Bugs & To-Do List

### ⚠️ Current Issues (Work in Progress)

1. **Static Road Segments:**
* **Issue:** The road lines connecting stations (e.g., Connaught Place -> India Gate) are manually hardcoded in `MapPage.jsx`.
* **Fix Needed:** Implement a Routing Engine (like OSRM or Google Directions API) to automatically draw paths between any two dynamic coordinates.


2. **ID Mismatch Sensitivity:**
* **Issue:** If the database IDs (e.g., 6, 7, 8) do not match the Simulator IDs (1, 2, 3), the map will show "Waiting for AI...".
* **Workaround:** Ensure `traffic_simulator.py` target IDs match the SQL `intersection` table exactly.


3. **Ambulance Pathing:**
* **Issue:** The ambulance icon follows a pre-set loop and doesn't genuinely react to the *specific* red light conditions (it just drives over them).
* **Goal:** Make the ambulance calculate a new route dynamically to avoid Red zones.



### 🔮 Future Improvements

* [ ] **Analytics Dashboard:** Add charts for Peak Hours and Weekly Traffic Trends.
* [ ] **Admin Panel:** Allow manual override of traffic lights from the UI.
* [ ] **Mobile Support:** Optimize the dashboard for mobile screens.

---

## 🤝 Contributing

1. Fork the repository.
2. Create your Feature Branch (`git checkout -b feature/AmazingFeature`).
3. Commit your changes (`git commit -m 'Add some AmazingFeature'`).
4. Push to the branch (`git push origin feature/AmazingFeature`).
5. Open a Pull Request.

---

### 📝 License
No license.
