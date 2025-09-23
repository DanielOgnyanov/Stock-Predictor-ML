📈 Stock Predictor ML – Backend

The Stock Predictor ML Backend is the core service powering the platform.
It exposes REST APIs for authentication, stock market data, and predictions, while coordinating with a Python-based ML microservice for stock forecasting.

The backend also periodically fetches stock quotes and market news, storing them for fast access and reliable insights.

🔍 What It Does

User Management – Register and login with secure authentication (JWT).

Stock Data – Fetch and persist live stock quotes.

Market News – Collect and store news articles tied to stocks.

ML Predictions – Delegate prediction requests to a Python microservice.

Scheduling – Periodic background jobs for keeping data fresh.

⚙️ Tech Stack

Java (Spring Boot) – Core backend, APIs, schedulers

Python microservice – ML model for predictions

MySQL – Persistent storage for users, stocks, and news

Docker + docker-compose – Containerized setup and orchestration

JWT – Authentication and secure endpoints

🛠️ Getting Started

Clone the repo and run with Docker:

git clone https://github.com/DanielOgnyanov/Stock-Predictor-ML.git
cd Stock-Predictor-ML
docker-compose up --build


Backend API: http://localhost:8080

Python ML service: http://localhost:5000
