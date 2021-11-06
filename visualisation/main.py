import pandas as pd
import numpy as np
import matplotlib.pyplot as plt

df = pd.read_csv('stats.csv')

x = df['time']
y1 = df['firstCheckout']
y2 = df['secCheckout']
plt.plot(x, y1, c='r', label='l1');
plt.plot(x, y2, c='g', label='l2');
plt.show()
