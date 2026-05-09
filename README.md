# Network Load Balancer Dashboard

A professional full-stack network load balancer system with real-time monitoring dashboard built using Java Spring Boot, Socket Programming, and Vanilla JavaScript.

![Load Balancer](https://img.shields.io/badge/Load%20Balancer-Round%20Robin-black)
![Java](https://img.shields.io/badge/Java-17+-black)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.2.0-black)
![License](https://img.shields.io/badge/License-MIT-black)

##  Features

- **Round Robin Load Balancing** - Distributes requests evenly across backend servers
- **Multithreading** - Handles concurrent client connections using ExecutorService
- **TCP Socket Programming** - Direct socket communication between components
- **Real-Time Dashboard** - Live monitoring with Chart.js visualizations
- **REST API** - Spring Boot endpoints for server stats and logs
- **Professional UI** - Clean black and white themed dashboard with responsive design

##  Architecture

```
Client → Load Balancer (Port 8000) → Backend Servers (9001, 9002, 9003)
                ↓
         Spring Boot API (Port 8080) → Frontend Dashboard
```

##  Prerequisites

- Java 17 or higher
- Maven 3.6+
- Modern web browser

##  Installation & Setup

### 1. Clone the Repository
```bash
git clone https://github.com/abhinavkr26104/load-balancer-java.git
cd load-balancer-java
```

### 2. Build Backend
```bash
cd backend
mvn clean install
```

### 3. Run Application

**Windows (PowerShell):**
```powershell
$env:JAVA_HOME="C:\Program Files\Java\jdk-24"
.\mvnw.cmd spring-boot:run
```

**Linux/Mac:**
```bash
export JAVA_HOME=/path/to/jdk
./mvnw spring-boot:run
```

The system will automatically start:
- 3 Backend Servers (ports 9001, 9002, 9003)
- Load Balancer (port 8000)
- REST API (port 8080)

### 4. Open Dashboard
Open `frontend/index.html` in your browser or navigate to:
```
file:///path/to/load-balancer-java/frontend/index.html
```

##  Testing

### Send Test Traffic

**Option 1: Run Test Client**
```bash
cd backend/src/main/java
javac com/loadbalancer/TestClient.java
java com.loadbalancer.TestClient
```



##  API Endpoints

| Endpoint | Method | Description |
|----------|--------|-------------|
| `/servers` | GET | Server health and status |
| `/stats` | GET | Request counts and distribution |
| `/logs` | GET | Recent traffic logs |
| `/connections` | GET | Active connection count |

**Example:**
```bash
curl http://localhost:8080/servers
curl http://localhost:8080/stats
```

##  Dashboard Features

- **Server Status Cards** - Real-time health monitoring
- **Active Connections** - Live connection counter
- **Total Requests** - Cumulative request tracking
- **Traffic Distribution Chart** - Visual request distribution
- **Live Request Logs** - Real-time traffic flow
- **Auto-refresh** - Updates every 2 seconds

##  Project Structure

```
load-balancer-java/
│
├── backend/
│   ├── pom.xml
│   ├── mvnw.cmd
│   ├── .mvn/
│   └── src/main/java/com/loadbalancer/
│       ├── LoadBalancerApplication.java
│       ├── TestClient.java
│       ├── controller/
│       │   └── MonitorController.java
│       ├── service/
│       │   └── LoadBalancerService.java
│       ├── model/
│       │   ├── ServerInfo.java
│       │   └── TrafficLog.java
│       └── socket/
│           ├── LoadBalancer.java
│           ├── BackendServer.java
│           └── ClientHandler.java
│
├── frontend/
│   ├── index.html
│   ├── style.css
│   ├── app.js
│   └── charts.js
│
└── README.md
```

##  How It Works

1. **Backend Servers** start on ports 9001-9003
2. **Load Balancer** listens on port 8000
3. **Client** connects to load balancer
4. **Round Robin** algorithm selects next server
5. **Request** forwarded via TCP socket
6. **Response** returned to client
7. **Stats** recorded and exposed via REST API
8. **Dashboard** polls API every 2 seconds

##  Key Concepts Demonstrated

- **Multithreading** - ExecutorService thread pool management
- **Socket Programming** - TCP client-server communication
- **Load Balancing** - Round Robin scheduling algorithm
- **REST API** - Spring Boot controller design
- **Real-Time Monitoring** - Polling-based dashboard updates
- **Responsive Design** - Modern CSS grid layout



##  Author

**Abhinav Kumar**
- GitHub: [@abhinavkr26104](https://github.com/abhinavkr26104)

⭐ Star this repository if you find it helpful!
