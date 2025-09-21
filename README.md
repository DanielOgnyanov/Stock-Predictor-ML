Stock Predictor ML (Backend)

Java + Python microservice handling stock data & predictions

🔍 What it is

Backend service for a stock market predictor that:

Exposes REST API endpoints for registering users, login, and fetching live stock quotes + news.

Uses an ML model (in Python microservice) to generate predictions.

Periodically fetches stock & news data and persists them for fast access.

⚙️ Tech Stack

Java (Spring Boot) for main API & scheduled tasks

Python microservice for ML model

MySQL 

Docker & docker-compose for setup

REST endpoints secured with basic auth / token (JWT etc.)
