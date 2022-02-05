import pandas as pd
import numpy as np
import matplotlib.pyplot as plt

df = pd.read_csv('stats.csv')

x = df['time']
y1 = df['firstCheckout']
y2 = df['secCheckout']
plt.xlabel("Simulation time")
plt.ylabel("Number of customers")
plt.plot(x, y1, c='r', label='firstCheckout')
plt.plot(x, y2, c='g', label='secCheckout')
plt.legend()
plt.show()
