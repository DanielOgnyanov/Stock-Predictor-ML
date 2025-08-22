from flask import Flask, request, jsonify
import pandas as pd
from StockModel import StockModel
from DataHandler import DataHandler

class StockAPI:
    def __init__(self):
        self.app = Flask(__name__)
        self.model = StockModel()
        self.setup_routes()

    def setup_routes(self):

        @self.app.route('/ping', methods=['GET'])
        def ping():
            return "Stock API is running!"

        @self.app.route('/predict', methods=['POST'])
        def predict():
            data = request.json
            handler = DataHandler(data)
            X, y = handler.get_features_and_target(target_column="close")


            if self.model.model is None:
                self.model.train(X, y)


            predictions = self.model.predict(X)


            response = []
            for i, row in enumerate(data):
                response.append({
                    "symbol": row["symbol"],
                    "predicted_close": float(predictions[i])
                })

            return jsonify(response)

    def run(self):
        self.app.run(debug=True)


if __name__ == "__main__":
    api = StockAPI()
    api.run()
