---
marp: true
paginate: true

---
# 勉強会: ラムダ式を使ってみる
2022-03-03  
株式会社キングプリンターズ システム課

---
## 目的
+ ラムダ式の概要を理解してラムダ式を使ってみます
    + ※本資料のコードは全てC# 9で記述しています

---
## 目次
1. ラムダ式とは
1. 無名関数とは
1. なぜ無名関数が必要なのか
1. ラムダ式の特徴
1. ラムダ式の形式
1. ラムダ式利用する例

---
## ラムダ式とは
+ 簡単に説明すると無名関数を定義する記法と言えます

---
#### ラムダ式の書き方
※C#での記法の一例  

```sh
(入力パラメータ) => 式
```

#### 無名関数
※C#では匿名関数と呼ばれる  

```csharp
(x, y) => x + y;
```

これは x, y を引数として x + y の計算結果を返す無名関数です

---
## 無名関数とは
+ 関数名を記述せずに引数や代入に使用する関数です

#### 名前がないのにどうやって使うのかという利用例を示します

+ 関数リテラルの構文: 無名関数を変数に代入しています

```csharp
Func<int, int, int> add = (x, y) => x + y;
AreEqual(5, add(2, 3));
```

---
## なぜ無名関数が必要なのか
+ 今日においては無名関数の使用を前提としたライブラリなどが多くそれらを理解・活用するためにも必要です

---
#### C# の LINQ ライブラリで引数にラムダ式を渡す例

+ LINQ の `Select`メソッドの引数にラムダ式を渡しています

```csharp
List<int> src = new() {0, 1, 2, 3, 4, 5};
List<int> exp = new() {0, 2, 4, 6, 8, 10};
src = src.Select(x => x * 2).ToList();
CollectionAssert.AreEqual(exp, src);
```

+ ラムダ式で入力値を2倍して返す無名関数を定義しています

```csharp
x => x * 2;
```

このように C# で LINQ を使用する為にはラムダ式の理解は必須となっています

---
## ラムダ式の特徴
(※ここではC#でのラムダ式の特徴を記します)  

+ 変数に代入できる
+ 関数の引数に渡せる
+ 関数の戻り値にできる

---
#### `変数に代入できる`の例

```csharp
Func<int, int> doubleValue = x => x * 2;
AreEqual(4, doubleValue(2));
```

+ 引数を2倍するラムダ式を`doubleValue`変数に代入しています
+ C#では`doubleValue`変数をデリゲート型で定義しています
+ `doubleValue`変数に`(2)`と引数を渡して実行しています

---
#### `関数の引数に渡せる`の例

```csharp
// 関数(メソッド)定義
int doValueTwo(Func<int, int> func) {
    return func(2);
}
// 使用例
AreEqual(4, doValueTwo(x => x * 2));
```

+ `Func`デリゲート型の変数を引き数に取る`doValueTwo()`関数(メソッド)を定義しています
+ `doValueTwo()`関数の引数にラムダ式を渡して実行しています
+ `doValueTwo()`関数内で引き数の`func`変数が`(2)`と引数を渡されて実行されています

---
#### `関数の戻り値にできる`の例

```csharp
// 関数(メソッド)定義
Func<int> toDoubleValue(int value) {
    return () => value * 2;
}
// 使用例
AreEqual(4, toDoubleValue(2)());
```

+ `Func`デリゲート型を戻り値に返し数値を引数に取る`toDoubleValue()`関数(メソッド)を定義しています
+ `toDoubleValue()`関数の引数に`2`を渡し実行しています
+ `toDoubleValue()`関数内で`Func`型の戻り値としてラムダ式が返されます
+ `Func`型の戻り値の変数を`()`と引数なしで実行しています

---
#### `関数の戻り値にできる`の例②

```csharp
// 関数(メソッド)定義
Func<int, int> toDoubleValue2() {
    return value => value * 2;
}
// 使用例
AreEqual(4, toDoubleValue2()(2));
```

+ `Func`デリゲート型を戻り値に返す`toDoubleValue2()`関数(メソッド)を定義しています
+ `toDoubleValue2()`関数を引数なしで実行しています
+ `toDoubleValue2()`関数内で`Func`型の戻り値としてラムダ式が返されます
+ `Func`型の戻り値の変数に`(2)`と引数を渡して実行しています

---
## ラムダ式の形式
(※ここではC#でのラムダ式の特徴を記します)  

+ 式形式のラムダ
+ ステートメント形式のラムダ

---
#### 式形式のラムダ

```sh
(入力パラメータ) => 式
```

```csharp
Func<int, int, int> add = (x, y) => x + y;
AreEqual(5, add(2, 3));
```

+ ラムダ式で記述した無名関数を`add`変数に代入しています
+ ラムダ式は右辺を`式形式`で記述しています

---
#### ステートメント形式のラムダ

```sh
(入力パラメータ) => ステートメントブロック
```

```csharp
Func<int, int, int> add = (x, y) => {
    return x + y;
};
AreEqual(5, add(2, 3));
```

+ ラムダ式で記述した無名関数を`add`変数に代入しています
+ ラムダ式は右辺を`ステートメントブロック形式`で記述しています

---
## ラムダ式使用する例
C# でラムダ式を使用するライブラリの使用例を紹介  

+ LINQ: Where(), Select() メソッドでラムダ式を使う例

---
#### LINQ: Where(), Select() メソッドでラムダ式を使う例

```csharp
List<Fruit> src = new() {
    new Fruit("りんご", 100), new Fruit("みかん", 160),　
    new Fruit("メロン", 350), new Fruit("ぶどう", 250)
};
List<string> exp = new() {"ミカン", "ブドウ"};
var ret = src
    .Where(x => isHiragana(x.Name) && x.Price > 150)
    .Select(x => toKatakana(x.Name))
    .ToList();
CollectionAssert.AreEqual(exp, ret);
```

+ 150円以上のひらがな名の果物の名前をカタカナに変換する処理とします
+ 真偽値を返す`isHiragana()`、ひらがなをカタカナに変換する`toKatakana()`関数が定義されているとします
+ LINQの`Where()`: 値のフィルタリング、`Select()`: 値の射影を行います

---
#### LINQ: Where(), Select() メソッドでラムダ式を使う例

+ LINQ + ラムダ式を使用しない記述に置き換えてみます

```csharp
var ret = src
    .Where(x => isHiragana(x.Name) && x.Price > 150)
    .Select(x => toKatakana(x.Name))
    .ToList();
```
↓
```csharp
List<string> ret = new();
foreach (var fruit in src) {
    if (isHiragana(fruit.Name) && fruit.Price > 150) {
        ret.Add(toKatakana(fruit.Name));
    }
}
```

---
## ご清聴ありがとうございました