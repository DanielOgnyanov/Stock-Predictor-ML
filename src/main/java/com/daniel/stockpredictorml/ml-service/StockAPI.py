from flask import Flask, request, jsonify
import pandas as pd
from stock_model import StockModel

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
            df = pd.DataFrame(data)
            predictions = self.model.predict(df)
            return jsonify(predictions.tolist())

    def run(self):
        self.app.run(debug=True)
