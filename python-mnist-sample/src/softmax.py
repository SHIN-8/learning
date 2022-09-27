import numpy as np
import matplotlib.pyplot as plt


def softmax(x):
    u = np.sum(np.exp(x))
    return np.exp(x)/u


x = np.arange(-10, 10, 1)
y = softmax(x)

plt.plot(x, y)
plt.show()
