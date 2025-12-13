📈 Stock Predictor ML – Backend

The Stock Predictor ML Backend serves as the primary engine of the platform, providing secure REST APIs for authentication, market data retrieval, and machine-learning-based stock forecasts. It integrates seamlessly with a dedicated Python ML microservice to deliver real-time and historical prediction capabilities.

In addition to powering API interactions, the backend periodically ingests live stock quotes and financial news, ensuring fast, reliable access to up-to-date market insights.

🔍 Key Features

User Management – Secure user registration and authentication using JWT.
Market Data Pipeline – Fetches and stores live stock quotes for rapid querying.
News Aggregation – Collects and persists stock-related market news.
ML Prediction Integration – Sends prediction requests to a Python-based ML microservice.
Automated Scheduling – Background jobs keep market data and news continuously refreshed.

⚙️ Technology Stack

Java (Spring Boot) – Core backend logic, REST APIs, and scheduled tasks

Python Microservice – Machine learning models and prediction processing

MySQL – Persistent datastore for users, stocks, and news

Docker & Docker Compose – Fully containerized environment and service orchestration

JWT – Secure authentication and authorization across endpoints

🛠️ Getting Started

Clone the repository and launch the full stack with Docker:

git clone https://github.com/DanielOgnyanov/Stock-Predictor-ML.git
cd Stock-Predictor-ML
docker-compose up --build


Once running:

Backend API: http://localhost:8080

Python ML Service: http://localhost:5000
