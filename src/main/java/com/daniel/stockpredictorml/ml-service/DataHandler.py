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

