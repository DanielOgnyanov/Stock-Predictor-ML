import pandas as pd
import numpy as np
from sklearn.linear_model import LinearRegression
from sklearn.model_selection import train_test_split
from sklearn.metrics import mean_squared_error
import joblib
import os

class StockModel:
    def __init__(self, model_path="stock_model.pkl"):

        self.model_path = model_path
        self.model = None
        if os.path.exists(model_path):
            self.model = joblib.load(model_path)

    def train(self, X, y):

        self.model = LinearRegression()
        self.model.fit(X, y)
        # Save the trained model
        joblib.dump(self.model, self.model_path)
        return self.model

    def predict(self, X):

        if not self.model:
            raise ValueError("Model is not trained yet!")
        return self.model.predict(X)

    def evaluate(self, X, y):

        if not self.model:
            raise ValueError("Model is not trained yet!")
        predictions = self.model.predict(X)
        mse = mean_squared_error(y, predictions)
        return mse
