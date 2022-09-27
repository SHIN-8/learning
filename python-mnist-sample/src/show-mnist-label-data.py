# 必要なライブラリのインポート
from keras.datasets import mnist
import matplotlib.pyplot as plt

# mnist データをダウンロード
(train_images, train_labels), (test_images, test_labels) = mnist.load_data()

# 画像データとラベルの要素数を表示
print("画像データの要素数", train_images.shape)
print("ラベルデータの要素数", train_labels.shape)

# ラベルと画像データを表示
for i in range(0, 10):
    print("ラベル", train_labels[i])
    plt.imshow(train_images[i].reshape(28, 28), cmap='Greys')
    plt.show()
