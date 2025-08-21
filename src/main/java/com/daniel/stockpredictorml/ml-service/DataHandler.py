import pandas as pd

class DataHandler:
    def __init__(self, data=None):

        self.data = data
        self.df = None

    def to_dataframe(self):

        if self.data is None:
            raise ValueError("No data provided")
        self.df = pd.DataFrame(self.data)
        return self.df

    def get_features_and_target(self, target_column="close"):

        if self.df is None:
            self.to_dataframe()
        X = self.df.drop(columns=[target_column, "symbol", "name", "currency"])  # only numeric features
        y = self.df[target_column]
        return X, y
