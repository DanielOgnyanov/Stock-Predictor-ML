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



