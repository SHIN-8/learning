from keras.datasets import mnist
from keras.utils import to_categorical
from keras.layers import Dense
from keras.models import Sequential
from keras.callbacks import TensorBoard
import numpy as np

# MNISTデータのロード
(x_train, y_train), (x_test, y_test) = mnist.load_data()

# データの初期状態の確認
print("訓練用データのshape 　: {}".format(x_train.shape))
print("テスト用データのshape : {}".format(x_test.shape))
print("訓練用ラベルのshape 　: {}".format(y_train.shape))
print("テスト用ラベルのshape : {}".format(y_test.shape))

# データの形状変更
x_train = x_train.reshape(60000, 784)
x_test = x_test.reshape(10000, 784)
print()

# データの標準化
raw_max_num = max(x_train[0])
print("標準化前データ内の最大数 : {}".format(raw_max_num))
x_train = x_train / raw_max_num
x_test = x_test / raw_max_num
new_max_num = max(x_train[0])
print("標準化後のデータ内の最大数 : {}".format(new_max_num))
print()


# one_hot_encoding
raw_label = list(set(y_train))
print("エンコード前のラベル・バリエーション : {}".format(raw_label))
y_train = to_categorical(y_train, 10)
y_test = to_categorical(y_test, 10)
new_label = [min(y_train[0]), max(y_train[0])]
print("エンコード後のラベル・バリエーション : {}".format(new_label))
print()


# モデルの全体を定義
model = Sequential()

# 一つ目の層を追加
model.add(
    Dense(
        units=50,
        input_shape=(784,),
        activation='sigmoid'
    )
)

# 二つ目の層を追加
model.add(
    Dense(
        units=30,
        input_shape=(784,),
        activation='sigmoid'
    )
)

# 三つ目の層を追加
model.add(
    Dense(
        units=10,
        activation='softmax'
    )
)

model.compile(
    optimizer='sgd',
    loss='categorical_crossentropy',
    metrics=['accuracy']
)

tsb = TensorBoard(log_dir='../logs')

history_adam = model.fit(
    x_train,
    y_train,
    batch_size=32,
    epochs=20,
    validation_split=0.2,
    callbacks=[tsb]
)

predict_result = model.predict(x_test)

print(x_test)

idx = [1001, 5010, 7701]

for i in idx:
    print("現在のインデックス : {}".format(i))
    print("正解ラベル : {}".format(np.argmax(y_test[i])))
    print("推測値 　　: {}".format(np.argmax(predict_result[i])))
