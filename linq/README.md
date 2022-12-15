---
marp: true
paginate: true

---
# 勉強会: LINQ の Select と Where 相当の機能を実装してみる
2022-11-01  
株式会社キングプリンターズ システム課

---
## 目的
+ C# LINQ(統合言語クエリ: リンク) の Select、Where 相当の機能を自作して理解を深めます
    + ※本資料では .NET 5.0 を使用しています
    + ※本資料では Visual Studio (※codeではない方) はインストールしません
    + ※本資料で説明するコードの完全版が付属しているので詳細はそちらで確認して下さい

---
## 目次
1. .NET SDK をインストールする方法
1. アプリを実行する方法
1. LINQ について
1. Select について
1. Where について
1. 拡張メソッドについて
1. Select の簡易実装
1. Where の簡易実装
1. おまけ

---
### .NET SDK をインストールする方法

+ パッケージリポジトリの追加
```sh
$ cd ~
$ wget https://packages.microsoft.com/config/ubuntu/20.04/packages-microsoft-prod.deb -O packages-microsoft-prod.deb
$ sudo dpkg -i packages-microsoft-prod.deb
```

+ インストール
```sh
$ sudo apt update
$ sudo apt install dotnet-sdk-5.0
```

+ 確認
```sh
$ dotnet --list-sdks
5.0.408 [/usr/share/dotnet/sdk]
$ dotnet --version
5.0.408
```

---
### アプリを実行する方法
+ ※コマンドラインから実行します

+ コンソールアプリをビルド・実行する

```sh
$ cd go_to_this_app_dir
$ dotnet run
```

+ ※デフォルトで Program.cs の Main メソッドの内の処理が実行されます
    + 必要に応じてコメントアウトしてください

---
## LINQ について

> 統合言語クエリ (LINQ) は、C# 言語への直接的なクエリ機能の統合に基づくテクノロジのセットの名前です。

from [Microsoft](https://learn.microsoft.com/ja-jp/dotnet/csharp/programming-guide/concepts/linq/)

---
## LINQ について

# 🤔

---
## LINQ について

> 統合言語クエリとは、.NET Framework 3.5において、様々な種類のデータ集合に対して標準化された方法でデータを問い合わせること（クエリ）を可能にするために、言語に統合された機能のことである

from [Wikipedia](https://ja.wikipedia.org/wiki/%E7%B5%B1%E5%90%88%E8%A8%80%E8%AA%9E%E3%82%AF%E3%82%A8%E3%83%AA)

---
## LINQ について

# 🤔

---
## LINQ について

+ [※個人的な見解]
    + 自分の理解では、データから目的の要素を取り出す用途で使います
        + LINQ は C# の特徴の一つとなっていると思います
        + 今や C# のコーディングで LINQ を使わないことなど考えられないと感じています

---
## LINQ について

+ この資料では なぜ LINQ のような機能が必要とされるのか？ を考えてみます
    + ※個人的な見解なので詳細は各自でリファレンスを参照して下さい

---
## Select について

+ まずあるデータから値を取り出す処理について考えてみます

---
## Select について

+ このようなデータ構造から任意の情報を出力に表示してみます

```csharp
List<Band> _band_list.Add(item: new() {
    Name = "The Beatles", Nation = UnitedKingdom,
    Period = new() { Begin = Parse("1960/01/01"), End = Parse("1970/12/31") }, Term = 1,
    Member = new() {
        new() { FirstName = "John", LastName = "Lennon", Gender = Male, Born = Parse("1940/10/09"), Role = new[] { Vocal, Guitar } },
        new() { FirstName = "Paul", LastName = "McCartney", Gender = Male, Born = Parse("1942/06/18"), Role = new[] { Vocal, Bass } },
        new() { FirstName = "George", LastName = "Harrison", Gender = Male, Born = Parse("1943/02/25"), Role = new[] { Vocal, Guitar } },
        new() { FirstName = "Ringo", LastName = "Starr", Gender = Male, Born = Parse("1940/07/07"), Role = new[] { Vocal, Drums } }
    },
});
_band_list.Add(item: new() {
    Name = "The Rolling Stones", Nation = UnitedKingdom,
    Period = new() { Begin = Parse("1962/01/01"), End = Parse("1969/12/31") }, Term = 1,
    Member = new() {
        new() { FirstName = "Brian", LastName = "Jones", Gender = Male, Born = Parse("1942/02/28"), Role = new[] { Guitar } },
        new() { FirstName = "Mick", LastName = "Jagger", Gender = Male, Born = Parse("1943/07/26"), Role = new[] { Vocal } },
        new() { FirstName = "Keith", LastName = "Richards", Gender = Male, Born = Parse("1943/12/18"), Role = new[] { Guitar } },
        new() { FirstName = "Bill", LastName = "Wyman", Gender = Male, Born = Parse("1936/10/24"), Role = new[] { Bass } },
        new() { FirstName = "Charlie", LastName = "Watts", Gender = Male, Born = Parse("1941/06/02"), Role = new[] { Drums } }
    },
});
// 以下、何組かのバンドの情報を設定
```

---
## Select について

##### データの最初のバンドの全メンバーを表示してみます

+ 初めにベタな foreach 文で書いてみます

```csharp
/// <summary>
/// データの最初のバンドの全メンバーを表示
/// </summary>
public static void Select1() {
    List<Band> list = Data.AllBand;
    foreach (Band band in list) {
        foreach (BandMan band_man in band.Member) {
            WriteLine(band_man.FullName);
        }
        break;
    }
}
```

---
## Select について

+ 出力結果

```sh
John Lennon
Paul McCartney
George Harrison
Ringo Starr
```

---
## Select について

+ LINQ で同様の処理を記述してみます

```csharp
/// <summary>
/// データの最初のバンドの全メンバーを表示
/// </summary>
public static void Select1() {
    List<Band> list = Data.AllBand;
    List<BandMan> member_list = list.Select(x => x.Member).First();
    member_list.ForEach(x => WriteLine(x.FullName));
}
```

---
## Select について

+ 出力結果

```sh
John Lennon
Paul McCartney
George Harrison
Ringo Starr
```

---
## Select について

+ LINQ を使用してベタな foreach 文を置き換えることが出来ました

---
## Where について

##### 次にデータの最初のバンドのベーシストを表示してみます

+ 初めにベタな foreach 文と if 文で書いてみます

```csharp
/// <summary>
/// データの最初のバンドのベーシストを表示
/// </summary>
public static void Where1() {
    List<Band> list = Data.AllBand;
    foreach (Band band in list) {
        foreach (BandMan band_man in band.Member)
            foreach (Role role in band_man.Role)
                if (role.ToString().Contains(Role.Bass.ToString()))
                    WriteLine(band_man.FullName);
        break;
    }
}
```

---
## Where について

+ 出力結果

```sh
Paul McCartney
```

---
## Where について

+ LINQ で同様の処理を記述してみます

```csharp
/// <summary>
/// データの最初のバンドのベーシストを表示
/// </summary>
public static void Where1() {
    List<Band> list = Data.AllBand;
    List<BandMan> member_list = list.Select(x => x.Member).First()
        .Where(x => x.Role.Contains(Role.Bass)).ToList();
    member_list.ForEach(x => WriteLine(x.FullName));
}
```

---
## Where について

+ 出力結果

```sh
Paul McCartney
```

---
## Where について

+ LINQ を使用してベタな foreach 文と if 文を置き換えることが出来ました

---
## ここまでのまとめ(※個人的感想)

+ LINQ を使うことにより foreach(for) 文と if 文を使用せずデータから値を取り出すことが出来ました

---
## 拡張メソッドについて

+ ところで LINQ の Select メソッド、Where メソッド は何をしているのでしょうか？ 🤔

---
## 拡張メソッドについて

+ ここから C# の機能 拡張メソッド を使用して  LINQ の Select メソッド、Where メソッド相当の機能を自作してみます

---
## 拡張メソッドについて

> 拡張メソッドを使用すると、新規の派生型の作成、再コンパイル、または元の型の変更を行うことなく既存の型にメソッドを "追加" できます。 拡張メソッドは静的メソッドですが、拡張された型のインスタンス メソッドのように呼び出します。

from [Microsoft](https://learn.microsoft.com/ja-jp/dotnet/csharp/programming-guide/classes-and-structs/extension-methods)

---
## 拡張メソッドについて

+ [※個人的な見解]
    + 拡張メソッドも C# の特徴の一つとなっていると思います
    + LINQ と同様に C# のコーディングで 拡張メソッド を使わないことなど考えられないと感じます

---
## Select の簡易実装

+ それでは早速 自作の Select メソッドを作成してみましょう

```csharp
/// <summary>
/// Select の簡易実装
/// </summary>
public static IEnumerable<TResult> xSelect<TSource, TResult>(
    this IEnumerable<TSource> source, Func<TSource, TResult> selector) {
    foreach (TSource item in source) {
        yield return selector(item);
    }
}
```

---
## Select の簡易実装

+ 純正 LINQ の時と同じように実行してみます
    + ※ xSelect, xFirst, xForEach を自作しています

```csharp
/// <summary>
/// データの最初のバンドの全メンバーを表示
/// </summary>
public static void Select1() {
    List<Band> list = Data.AllBand;
    List<BandMan> member_list = list.xSelect(x => x.Member).xFirst();
    member_list.xForEach(x => WriteLine(x.FullName));
}
```

---
## Select の簡易実装

+ 出力結果

```sh
John Lennon
Paul McCartney
George Harrison
Ringo Starr
```

---
## Select の簡易実装

+ foreach 文が自作の xSelect メソッドの中に書かれているのが分かります
+ 純正 LINQ の Select メソッドと同じ出力を得ることは出来ました

---
## Where の簡易実装

+ 次に自作の Where メソッドを作成してみましょう

```csharp
/// <summary>
/// Where の簡易実装
/// </summary>
public static IEnumerable<TSource> xWhere<TSource>(
    this IEnumerable<TSource> source, Func<TSource, bool> predicate) {
    foreach (TSource item in source) {
        if (predicate(item)) {
            yield return item;
        }
    }
}
```

---
## Where の簡易実装

+ 純正 LINQ の時と同じように実行してみます
    + ※ xSelect, xFirst, xWhere, xContains, xToList, xForEach を自作しています

```csharp
/// <summary>
/// データの最初のバンドのベーシストを表示
/// </summary>
public static void Where1() {
    List<Band> list = Data.AllBand;
    List<BandMan> member_list = list.xSelect(x => x.Member).xFirst()
        .xWhere(x => x.Role.xContains(Role.Bass)).xToList();
    member_list.xForEach(x => WriteLine(x.FullName));
}
```

---
## Where の簡易実装

+ 出力結果

```sh
Paul McCartney
```

---
## Where の簡易実装

+ foreach 文と if 文が自作の xWhere メソッドの中に書かれているのが分かります
+ 純正 LINQ の Where メソッドと同じ出力を得ることは出来ました

---
## まとめ(※個人的感想)

+ 拡張メソッドの中に foreach 文と if 文実装することによりそれら制御構文を内部に隠蔽することが出来ました
+ そのメソッドを利用する側はそれら制御構文を意識せず処理の内容にフォーカスすることが出来ました
+ 拡張メソッドを記述することにより LINQ の Select と Where 相当の機能を実装してみることが出来ました

---
## おまけ

+ 歴史的バンドの中からベーシストを列記してみます！
    + ※ xSelectMany, xToList, xWhere, xContains, xForEach を自作しています

```csharp
/// <summary>
/// データの全てのバンドのベーシストだけを表示
/// </summary>
public static void Where2() {
    List<Band> list = Data.AllBand;
    List<BandMan> member_list = list.xSelectMany(x => x.Member).xToList()
        .xWhere(x => x.Role.xContains(Role.Bass)).xToList(); ;
    member_list.xForEach(x => WriteLine(x.FullName));
}
```

---
## おまけ

+ 出力結果

```sh
Paul McCartney
Bill Wyman
Pete Quaife
John Entwistle
```

---
## ご清聴ありがとうございました

---
## 参考

https://source.dot.net/
