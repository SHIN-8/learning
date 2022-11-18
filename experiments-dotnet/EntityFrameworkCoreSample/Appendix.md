概要
====

## C#とは
C#は、Microsoft社が開発した __.NET__ プラットフォームに特化したマルチパラダイム言語です。
Microsoft社が提唱した __.NET__ 構想の中核を握る開発言語であると同時に、ヨーロッパの標準化団体であるECMA-Standard Information Communication Systemsにより、「ECMA-334　C#　Specfication」として標準化がされています。

また、C#はCLI（Common Language Infrastructure、共通言語基盤）上で動作します。CLIもECMAによって「ECMA-335 Common Language Infrastructure」として標準化がされているため、Microsoft社以外の3rdパーティがCLIを実装できます。


## .NET Framework
.NET Frameworkには、大きく分けて2つの意味があります。1)プログラムの実行環境、2)クラスライブラリ　です。
.NET　Frameworkは、プログラムの実行環境としてCLR（CLIのMicrosoft実装）を提供しています。
CLRが提供している機能には、以下のものがあります。
- CTS（Common Type System、共通型システム）
- CLS（Common Language Specification、共通言語仕様）
- JIT（Compiler(Just-Intime Compiler、JITコンパイラ）
- CIL（Common Intemidiate Language、共通中間言語）
- VES（Vitural Execution System、仮想実行システム）

CLRはJavaVMとよく比較されますが、JavaVMとの大きな違いは、CLRは言語に依存しないという点です。一般にはVBとC#しか使いませんが、それ以外の言語をCLR上で実行するように拡張できます。実際、CLR上で動作するRubyの処理系などがあります。

.NET Frameworkといえば、一般的には2)のクラスライブラリのことを指します。クラスライブラリには、WindowsAPIをラップしたもの、.NETで作成されたユーティリティ、クラスライブラリを扱いやすくするためのフレームワークなどが含まれています。また、.NETが提供しているアプリケーションフレームワークはWPF(Windows Presentation Foundation)やWCF(Windows Communication Foundation)、ASP.NETなどがあります。

## マネージコード
C言語やC++言語で書かれたコードは、マシン上で直接実行可能なコードです。しかし、C#言語はCLRがなければ実行することができません。このようにCLRに完全に依存しているコードをマネージコードと言います。C#で書かれたプログラムは基本的にマネージコードです。
マネージコンパイラ（マネージコード用のコンパイラ）は、そのまま実行可能な状態ではなくIL形式（Intemidate Language、中間言語）のコードを出力します。このIL形式のプログラムは実行時にJITCompilerによってネイティブコードに変換されます。

## C#言語の構造
まずは、Hello,World!を例にC#の構造を見ていきます。
```cs
using System;

class Program
{
  static int Main(string[] args)
  {
    Console.WriteLine("Hello world!");

    return 0;
  }
}
```
これが、ほぼ最小のC#プログラムとなります。

まず、1行目のusing Systemは、usingディレクティブと呼び、usingキーワードの後ろに指定した名前空間で定義されている型を導入します。名前空間についての詳細は後述します。
先の例では、System名前空間の導入しています。System名前空間には、.NETFrameworkが提供している基本的なクラス・インタフェース・列挙子などが定義されています。

usingディレクティブの一般的な宣言は
```cs
using [識別子];
```
となります。

C#もJavaと同じく、プログラムの基本構成単位はクラスです。C#のプログラムは、必ず1つ以上のクラスを持ちます。クラスを定義するには、classキーワードを使います。
```cs
class [識別子]
{
//クラスの定義
}
```
となります。上の例では、class Programがそれに相当します。また、次の
```cs
static int Main(string[] args)
```
は、C言語におけるmain関数に相当しプログラムのエントリポイントとなります。

## C#プログラムのコンパイルと実行
C#のプログラムは、CLR(Common Language Runtime)上で動作します。
ビルドおよび実行にはVisual Studioのビルドを使ってもいいですが、コマンドラインからのコンパイル・実行方法も知っておくといいでしょう。
C#コンパイラー(csc.exe)は、C:\Windows\Microsoft.NET\Framework\(.NET version)\以下にあります。
C#のソースファイルをコマンドラインからコンパイルするには、

```
$(PATH)csc test.cs
```

とします。プログラムを実行するには

```sh
$ test.exe
```

とします。なお、cscのコンパイルオプションはMSDNを参照してください。

mono上でコンパイルするには、

```sh
$msc test.cs
```

とします。プログラムの実行には

```sh
$mono test.exe
```

とします。

## 入出力の基本
標準入出力はSystem.Consoleクラスを利用します

### 標準入力
標準入力からユーザ入力を読み取るには、ConsoleクラスのReadLineメソッド、ReadKeyメソッド、Readメソッドを使います。

__【list1-1 ユーザ入力の読み込み】__
```cs
using System;

class Program 
{
	static void Main(string[] args)
	{
		//一行読み込み
		string line = Console.ReadLine();

		//一文字読み込み
		int ch = Console.Read()
		
		//入力された文字と修飾キーを取得する
		ConsoleKeyInfo ch = Console.ReadKey();		
	}
}
```

### 標準出力
データを標準出力に出力するには、ConsoleクラスのWriteLineメソッド、Writeメソッドを使います。
WriteLineは自動改行しますが、Writeメソッドは改行しません。

__【list1-2 標準出力】__
```cs
using System;

class Program
{
    static void Main(string[] args)
　　{
        //ブール値(改行あり）
	Console.WriteLine(true);

        //文字(改行あり）
	Console.WriteLine('a');

        //文字列(改行あり）
	Console.WriteLine("Hello, World!");

        //実数(改行あり）		
	Console.WriteLine(0.354);

        //整数(改行あり）
	Console.WriteLine(1);

        //書式付(改行あり）
    Console.WriteLine("{0} + {1} = {2}", 1, 1, 2);
        
        //改行だけ出力
    Console.WriteLine();
		
		//ブール値(改行なし）
	Console.Write(true);

        //文字(改行なし）
	Console.Write('a');

        //文字列(改行なし）
	Console.Write("Hello, World!");

        //実数(改行なし）
	Console.Write(0.354);

        //整数(改行なし）
	Console.Write(1);

        //書式付(改行なし）
	Console.Write("{0} + {1} = {2}", 1, 1, 2);

        //改行出力
        Console.Write("\n")
　　}
}
```

### 標準エラー出力
標準エラー出力はメソッドの前に`Error.`が付く以外、標準出力とほぼ同じです。

__【list1-3 標準エラー出力】__
```cs
using System;

class Program
{
    static void Main(string[] args)
　　{
        //ブール値(改行あり）
　　　　Console.Error.WriteLine(true);

        //文字(改行あり）
        Console.Error.WriteLine('a');

        //文字列(改行あり）
        Console.Error.WriteLine("Hello, World!");

        //実数(改行あり）		
        Console.Error.WriteLine(0.354);

        //整数(改行あり）
        Console.Error.WriteLine(1);

        //書式付(改行あり）
        Console.Error.WriteLine("{0} + {1} = {2}", 1, 1, 2);
        
        //改行だけ出力
        Console.Error.WriteLine();

        //ブール値(改行なし）
        Console.Error.Write(true);

        //文字(改行なし）
        Console.Error.Write('a');

        //文字列(改行なし）
        Console.Error.Write("Hello, World!");

        //実数(改行なし）
        Console.Error.Write(0.354);

        //整数(改行なし）
        Console.Error.Write(1);

        //書式付(改行なし）
        Console.Error.Write("{0} + {1} = {2}", 1, 1, 2);

        //改行出力
        Console.Error.Write("\n")
　　}
}
```
## 変数と演算
- 変数の宣言は、型名 [識別子];
- 数値の計算に、算術演算子を用いる。

###  定義
C#の変数宣言は、

```cs
[型名] [変数名];
```

とします。たとえば、int型（Int32型）の変数valueの定義は

```cs
int value;
```
です。

変数に値を入れる（代入する）には、=演算子（単純代入演算子）を使います。
```cs
[変数名] = [値];
```

同様に、int(Int32)型の変数valueに値10を代入するには
```cs
value = 10;
```
とします。変数への代入は、変数の宣言時に行うこともできます。なお、ローカル変数を初期化せずに使うコンパイルエラーとなります。

__【list2-1】__
```cs
using Systme;

class Program
{
	static void Main(string[] args)
	{
		//変数の定義と初期化
		int value1;
		value1 = 10;
		Console.WriteLine(value1);
		
		//変数の定義と同時に初期化
		int value2 = 11;
		Console.WriteLine(value2);
		
		//変数が初期化されていないのでエラー
		//int value3;
		//Console.WriteLine(value3);

	}
}
```

###  算術演算
数値計算には、算術演算子を用います。算術演算子には、次のようなものがあります。

|演算子|意味|
|:----|:----|
|a+b |aとbを足す|
|a-b |aからbを引く|
|a*b |aとbをかける|
|a/b |aをbで割る|
|a%b|aをbで割った余りを求める|


__【list2-2】__
```cs
using Systme;

class Program
{
	static void Main(string[] args)
	{
		int lhs, rhs;
		int result;
		lhs = Console.Read();
		rhs = Console.Read();
		
		//加算
		result = lhs + rhs;
		Console.WriteLine("{0} + {1} = {2}", lhs, rhs, result);
		
		//減算
		result = lhs - rhs;
		Console.WriteLine("{0} - {1} = {2}", lhs, rhs, result);
		
		//乗算
		result = lhs * rhs;
		Console.WriteLine("{0} * {1} = {2}", lhs, rhs, result);
		
		//除算
		result = lhs / rhs;
		Console.WriteLine("{0} / {1} = {2}", lhs, rhs, result);
		
		//剰余算
		result = lhs % rhs;
		Console.WriteLine("{0} % {1} = {2}", lhs, rhs, result);
		
		
	}
}
```

list2-2では、演算した結果を代入するのに

```cs
value = a op b;
```
としています。ここでopは任意の算術演算子です。C言語などでは、これの簡便な表現として

```cs
a op= b;
```

とすることができ、演算と代入を一緒にできます。このように演算と代入を行う演算子を複合代入演算子といいます。C#でも複合代入演算子がサポートされています。複合代入演算子には +=演算子、-=演算子、*=演算子、/=演算子、%=演算子、などがあります。 

|演算子|意味|
|:----|:----|
|a+=b |aにaとbを加算した値を代入する|
|a-=b |aにaとbを減算した値を代入する|
|a*=b |aにaとbを乗算した値を代入する|
|a/=b |aにaをbで除算した値を代入する|
|a%=b |aにaをbで除算した余りを代入する|

## 条件分岐
- 条件分岐には、if文を用いる
- 条件式の記述は、論理演算子と関係演算子を用いる
- 多分岐には、switch-case文
- gotoの多用は好ましくないが、利用すべきシチュエーションは存在する

###  if文と論理演算
プログラムを分岐させるもっとも基本的な制御文はif文です。

ifは、__…が…であれば…する__ という制御を表現します。

__…が…であれば__ という部分は条件式（論理式やブール式とも言います）を使います。

if文の構文は、
```cs
if (条件式) {
	条件式が成立した場合の式
}
```
となります。

__【list3-1】__
```cs
using System;

class Program 
{
	static void Main() 
	{
		string line = Console.ReadLine();
		int value = Int32.Parse(line);
		
		if (value > 10)
		{
			Console.WriteLine("入力された値は10より大きいです。");
		}		
	}
}
```

ifの後に、elseをつなげることもできます。
```cs
if (条件式) 
{
	条件式が成立した場合の式
}
else 
{
	条件式が成立しなかった場合の式
}
```

__【list3-2】__
```cs
using System;

class Program 
{
	static void Main() 
	{
		string line = Console.ReadLine();
		int value = Int32.Parse(line);
		
		if (value > 10)
		{
			Console.WriteLine("入力された値は10より大きいです。");
		}		
		else 
		{
			Console.WriteLine("入力された値は10以下です。");		
		}
	}
}
```

else節の後には、if文をつなげることもできます。

```cs
if (条件式1) 
{
	条件式1が成立した場合の式
}
else if (条件式2)
{
	条件式2が成立した場合の式
}
else 
{
	条件式1と条件式2が成立しなかった場合の式
}

```

__【list3-3】__
```
using System;

class Program 
{
	static void Main() 
	{
		string line = Console.ReadLine();
		int value = Int32.Parse(line);
		
		if (value > 10)
		{
			Console.WriteLine("入力された値は10より大きいです。");
		}		
		else if (value > 5)
		{
			Console.WriteLine("入力された値は5より大きいです。");		
		}	
		else 
		{
			Console.WriteLine("入力された値は5以下です。");		
		}
	}
}
```

また、if文の中の処理が1つの式のみで成り立っているならば、if文の{}は省略できます。list3-4は次のように書いたものと等価です。

__【list3-4】__
```cs
using System;

class Program 
{
	static void Main() 
	{
		string line = Console.ReadLine();
		int value = Int32.Parse(line);
		
		if (value > 10)
			Console.WriteLine("入力された値は10より大きいです。");
		else if (value > 5)
			Console.WriteLine("入力された値は5より大きいいです。");		
		else 
			Console.WriteLine("入力された値は5以下です。");		
	}
}
```

##  入れ子構造のif文
if文は入れ子構造にできます。つまりif文の中にif文を記述することできます。

__【list4-1】__
```cs
using System;

class Program 
{
	static void Main() 
	{
		string line = Console.ReadLine();
		int value = Int32.Parse(line);
		
		if (value > 10)
		{ 
			if (value > 20)
			{
				Console.WriteLine("入力された値は20より小さいです。");		
			}
			else 
			{
				Console.WriteLine("入力された値は10より大きく20以下です。");		
			}
		}		  
		else 
		{
			Console.WriteLine("入力された値は10以下です。");		
		}
	}
}
```

## 論理演算子と関係演算子
条件式の記述には、論理演算子と関係演算子を用いることができます。
論理演算子は、bool値に対して定義されており、&&演算子（論理積演算子）、||演算子（論理和演算子）、^演算子（排他的論理和演算子）!演算子（論理否定演算子）があります。
なお、論理演算子は、演算子オーバロードできます。

|論理演算子||
|:----|:----|
|a && b|aとbの論理積を返す|
|a &brvbar;&brvbar; b|aとbの論理和を返す|
|a ^ b|aとbの排他的論理和を返す|
|!a|aの論理否定を返す|


list-6
```cs
using System;

class Program
{
    static void Main(string[] args)
    {
        bool x = true;
        bool y = false;

        Console.WriteLine("!{0} = {1}", x, !x);
        Console.WriteLine("{0} && {1} = {2}", x, y, x && y);
        Console.WriteLine("{0} || {1} = {2}", x, y, x || y);
        Console.WriteLine("{0} ^  {1} = {2}", x, y, x ^ y);           
    }
}
```

関係演算子（比較演算子）は、2つのオペランド間の2項関係を記述します。関係演算子には、>演算子、>=演算子、<=演算子、==演算子、!=演算子があります。
なお、関係演算子は、演算子オーバロードが可能です。

関係演算子
|a > b|aがbより大きければ、trueを返す。それ以外ではfalseを返す。|
|a >=b|aがb以上ならば、trueを返す。それ以外では、falseを返す。|
|a <= b|aがb以下ならば、trueを返す。それ以外では、falseを返す。|
|a < b|aがbより小さければ、trueを返す。それ以外ではfalseを返す。|
|a==b|aとbが等値であるなら、trueを返す。それ以外はfalseを返す|
|a!=b|aとbが非等値であるなら、trueを返す。それ以外はfalseを返す|


list-6
```
using System;

class Program
{
    static void Main(string[] args)
    {
        int a = 5, b = 3;

		Console.WriteLine("{0} == {1} = {2}", a, b, a == b);
		Console.WriteLine("{0} != {1} = {2}", a, b, a != b);
        Console.WriteLine("{0} >  {1} = {2}", a, b, a > b);
        Console.WriteLine("{0} >= {1} = {2}", a, b, a >= b);
        Console.WriteLine("{0} == {1} = {2}", a, b, a == b);
        Console.WriteLine("{0} <= {1} = {2}", a, b, a <= b);
        Console.WriteLine("{0} <  {1} = {2}", a, b, a < b);           
    }
}
```

##  switch文
switch文は、caseキーワードで記述された分岐リストから、条件式と一致する分岐を選択する制御構文です。
switch式は、1つ以上のcaseラベルと1つ以上のステートメントのリストが含まれます。


```
switch (条件式)
{
    case 定数:
		式
        break;
    case 定数:
		式
        break;
    default:
        break;
}
```

各caseラベルでは、定数値を指定します。switch式の値と一致するcaseラベルへ制御が移動します。一致する値がcaseラベルになくdefaultが指定されている場合、defaultセクションに制御が移動します。 defaultセクションがなければ何も実行されず、switch式の外部に移動します。 

list-7
```cs
using System;

class Program 
{
	static void Main() 
	{
	
		Console.WriteLine("1か2の数値を入力してください:")
		int x =Int32.Parse(Console.ReadLine());

		switch(x)
		{
		case 1:
			Console.WriteLine("{0}", x);
			break;
		case 2: 
			Console.WriteLine("{0}", x);
			break;
		default:
			Console.WriteLine("{0}は範囲外の数値です", x)
			break;
		}
	}
}
```

なお、C++とは異なり、C#のswitch文はフォールスルーではありません。つまり、連続したcaseラベルを連続して実行することはできません。caseラベルで記述する処理は必ずbreakかジャンプ式（≒goto）で終わっている必要があります。したがって、次のコードはエラーとなります。

```cs
int condition = 1;
switch (condition)
{
    case 1:
        Console.WriteLine("Case 1");
        // breakキーワードかジャンプ式が必要
    case 2:
        Console.WriteLine("Case 2");
		break;
}
```

##  goto文
goto文は、指定したラベルの位置に強制的にジャンプします。CやC++でも同じですがgoto文を多用したプログラムは、処理の流れを追いづらいため、プログラミング上の作法としてgotoの使用はあまり好ましくありません。ですが、用途に限っていえば、いくつか有用な利用場面があります。具体的にはswitch文で複数のcaseラベルを実行したい時、ネストされた繰り返し文からの脱出です。

list複数のcaseラベルを実行
```cs
int condition = 1;
switch (condition)
{
    case 1:
        Console.WriteLine("Case 1");
        goto case 2;
    case 2:
        Console.WriteLine("Case 2");
		break;
}
```


lis1t1繰り返し分からの脱出
```
for (...) 
{
	for (...) 
	{
		//繰り返したい処理
		
		//特定の条件でループを脱出する
		if (...) 
		{
			goto EXIT:
		}
	}
}

EXIT:
```
##  3項演算子
条件によって代入したい値を変更したい場合には、3項演算子が便利です。
3項演算子
```cs
(条件式) ? (条件式が成立する時に返す値) : (条件式が成立しなかった時に返す値);
```


list-8
```cs
using System;

class Program 
{
	static void Main(string[] args) 
	{
		Console.WriteLine("0か1の値を入力してください");
		
		string line = Console.ReadLine();
		int value = Int32.Parse(line);
		
		Console.WriteLine("{0:b}です。", (value == 0)? false: true);
	}
}
```

以上で条件分岐の主な内容は関しては抑えていると思います。次は、繰り返しです。まぁ実際は、Linqを積極手に使うとifとかforを書く機会がだいぶ減るんですがね。
より詳細な内容は以下を参照。
[http://msdn.microsoft.com/ja-jp/library/vstudio/618ayhy6.aspx:title="C# リファレンス"]

## 繰り返し
- 繰り返し回数が決まっている場合にはfor文
- 繰り返し回数が決まっていない場合にはwhile文
- 配列などのコレクションをすべて走査する場合はforeach文

### for文
__…から…まで__ 同じ処理を繰り返すということを表現するには、for文を用います

```
for (1.初期化子; 2.条件式; 3.反復子)
{
 反復したい処理
}
```
1の初期化子には、繰り返しの初期値を設定します。この部分は、繰り返しが実行される前に1度だけ実行されます。
2の条件式には、繰り返しを続行するか終了するかを決定する条件式を、記述します
3の反復子には、ループの後に実行される処理を記述子ます。通常はここにインクリメント/デクリメントといったループの更新処理を記述します。ここで、インクリメントとは変数の値を1増やす処理の事です。デクリメントは逆に変数の値を1減らす処理です。

たとえば、0から10まで加算を繰り返すには、次のように記述します。

list-1
```cs
using System;

class Program
{
	static void Main(string[] args)
	{	
		int sum = 0;
		for (int i=0; i <= 10; i+=1) 
		{
			sum = sum + i;
		}
		
		Console.WriteLine("{0}から{1}までの和は、{2}です。", 0, 10, sum);
	}
}
```
## インクリメントとデクリメント
インクリメントは
```
int i = 0;
i += 1;
```
とも書けますが、C#ではインクリメントにを行うのに簡便な、`++`演算子（インクリメント演算子）が用意されています。デクリメントも同様に、`--`演算子（デクリメント演算子）が用意されています。
list-1をインクリメントを使うように書き直すと、次のようになります。

list-2
```cs
using System;

class Program
{
	static void Main(string[] args)
	{	
		int sum = 0;
		for (int i=0; i <= 10; i++) 
		{
			sum = sum + i;
		}
		
		Console.WriteLine("{0}から{1}までの和は、{2}です。", 0, 10, sum);
	}
}
```


また、`++`演算子（`--`演算子）には、2種類の形式があり変数の前に置く形式を前置インクリメント（デクリメント）、変数の後ろに置く形式を後置インクリメント（デクリメント）といいます。

```cs
int value = 0;
++value; //(1) 前置インクリメント
value++; //(2) 後置インクリメント
```

前置インクリメント（デクリメント）と後置インクリメント（デクリメント）の違いは、評価の順番です。たとえば、次のコード。

list-3
```cs
using System;
using System.Linq;

class Program
{
	static void Main(string[] args)
	{
			int v1 = 1;
			int v2 = 2;
			int i = ++v1;
			int j = v2++;
			Console.WriteLine("v1を前置インクリメントすると{0}", i);
			Console.WriteLine("v2を後置インクリメントすると{0}", j);
	}
}
```

これの実行結果は

```
v1を前置インクリメントすると2
v2を後置インクリメントすると2
```

となり、v1を前置インクリメントすると値が増えて代入されているのに、v2を後置インクリメントして代入しても2のままです。これは前置インクリメントが式を評価する前に実行されるのに対して、後置インクリメントは式を評価した後に実行するからです。

## while
__…まで__ 処理を繰り返すということを表現するには、while文を用います。while文は、特定の条件式が成立するまで反復します。

```cs
while (条件式)
{
 反復したい処理
}
```

for文と異なり、繰り返しの初期化や繰り返しの更新処理は不要です。必要な場合は、while文の内外に記述します。

たとえば、ユーザの入力が正の間は和を計算して、負の入力になったら和を表示して繰り返しを抜ける場合には次のようにかけます。

```cs
using System;

class Program
{
	static void Main(string[] args)
	{
		int sum = 0;
		while (true) {
			string line = Console.ReadLine();
			int value = Int32.Parse(line);
			
			if (value < 0) {
				Console.WriteLine("入力値の和は{0}です", sum);
				break;
			}
			
			sum += value;
		}
	}
}
```

また、for文とwhile文は相互に書き換えが可能で、list-1のコードは次のように書くこともできます。

list-5
```cs
using System;

class Program
{
	static void Main(string[] args)
	{	
		int sum = 0;
		int i = 0;
		while (i <= 10) 
		{
			sum = sum + i;
			++i;
		}
		
		Console.WriteLine("{0}から{1}までの和は、{2}です。", 0, 10, sum);
	}
}
```

## do-while文
while文とよく似た制御構造を提供するdo-while文もあります。do-whileはwhile文と異なり必ず1回は実行されます。
```
do
{
 反復したい処理
}while (条件式);
```


list-2の内容をdo-whileで書き下すと次のようになります。

list-6
```cs
using System;

class Program
{
	static void Main(string[] args)
	{	
		int sum = 0;
		int i = 0;
		do 
		{
			sum = sum + i;
			++i;
		}
		while(i<=10);
		
		Console.WriteLine("{0}から{1}までの和は、{2}です。", 0, 10, sum);
	}
}

```

## foreach文
C#には、配列やコレクションのすべての要素を走査する`foreach`文があります。`foreach`は`Linq`と相性がいいのでLinqを積極的に使う場合には、`for`文より`foreach`の使う機会が増えると思います。

たとえば、1-10までの和を求めるには次のようにかけます。
list-7
```
using System;
using System.Linq;

class Program
{
	static void Main(string[] args)
        {
		//1から数えて10個の整数のシーケンスを作成する
		var range = Enumerable.Range(1, 10);
		
		int sum = 0;
		foreach (var item in range) 
		{
			sum += item;
		}
		
		Console.WriteLine("{0}から{1}までの和は{2}です。", 1, 10, sum);

	}
}
```
*1384355804*[C#]クラス
## クラスとは
クラスの概念はオブジェクト志向において非常に重要な概念です。クラスは、データとそれに対する操作の組で表現されます。クラスの持つデータ（属性）は、クラス内の変数として宣言され、フィールドの操作はメソッドというクラス内に定義された関数で行われます。なお、クラスにはモデリングしやすいという利点もあります。

## オブジェクトの生成
クラスはコード上で概念を表現したものでありそれ自体は実体ではありません。クラスの実体（インスタンス、オブジェクト）を作るには、`new`演算子を用います。list-1のようにしてオブジェクトを生成します。

list-1
```
型名　変数名 = new 型名
```

new演算子を呼び出した時には、コンストラクターという、オブジェクトを初期化するための型名と同じ特別な関数が呼ばれます。

list-2
```cs
	class クラス
	{
		public クラス() {
		   //初期化
		}
	}
```

たとえば、点を表現するPointクラスであれば

list-3
```cs
	class Point
	{
		public Point() {
		    Console.WriteLine("コンストラクタを呼び出しました");
		}
		
	}
```
として書くことができます。

## フィールド
クラスの持つ属性を、フィールドといいます。フィールドはクラス内の変数として表現されます。点を表現するクラスは当然座標を持っていますから、list4のように定義できます。フィールドはコンストラクターで初期化しなければ既定の値で初期化されます。

list-4
```cs
using System;

namespace Sample
{
	class Point
	{
		public Point() {
		    Console.WriteLine("コンストラクタを呼び出しました");
		}
		
		public Point(int x, int y) {
			_x = x;
			_y = y;
		    Console.WriteLine("コンストラクタでオブジェクトを({0},{1})で初期化しました。", x, y);
		}
		
		private int _x;
		private int _y;
	}
	
	class Program {
		[STAThread]
		public static void Main(string[] args) {
			Point p = new Point(10, 11);			
		}
	}
```

## メソッド
メソッドはフィールドに対する操作を定義します。なんのことはないただの関数です。たとえば、Pointクラスの座標値を表示するPrintメソッドをlist4に追加すると以下のようになります
list-5
```cs
	class Point
	{
		public Point() {
		    Console.WriteLine("コンストラクタを呼び出しました");
		}
		
		public Point(int x, int y) {
			_x = x;
			_y = y;
			
			Console.WriteLine("コンストラクタでオブジェクトを({0},{1})で初期化しました。", x, y);
		}
		
		public void Print() {
			Console.WriteLine("({0},{1})", _x, _y);
		}
		

		private int _x;
		private int _y;
	}
	
	class Program {
		[STAThread]
		public static void Main(string[] args) {
			Point p = new Point(10, 11);
			
			p.Print();
		}
	}
```

## プロパティ
C++では、クラスのもつ属性に外部からアクセスしたいときは、公開レベルをpublicにして変数を公開するかget,setメソッドを実装する必要がありました。
C#では、フィールドに対してアクセスする手段としてプロパティがあります。

list-6
```cs
using System;

namespace Sample
{
	class Point
	{
		public Point() {
			Console.WriteLine("コンストラクタを呼び出しました");
		}
		
		public Point(int x, int y) {
			Console.WriteLine("コンストラクタを呼び出しました");
			X = x;
			Y = y;
			Console.WriteLine("{0}, {1}で初期化しました", x, y);
			
		}
		public void Print() {
			Console.WriteLine("({0},{1})", X, Y);
		}
		
		private int _x;
		public int X
		{
			get {
				return _x;
			}
			set {
				_x = value;
			}
		}
		
		private int _y;
		public int Y 
		{
			get {
				return _y;
			}
			set {
				_y = value;
			}
		}
	}
	
	class Program {
		[STAThread]
		public static void Main(string[] args) {
			Point p = new Point(10, 11);
			p.Print();
			
			p.X = 12;
			p.Y = 13;
			p.Print();
		}
	}
}
```

`get`および`set`アクセサーを用意することで、変数を公開レベルをpublicにするより、フィールドの入力値をチェックしたり変更点を追跡できるようになるため、安全になります。
また、get setの定義を省略した場合には、コンパイラがコンパイル時にプロパティを自動的に生成してくれます。これを自動実装プロパティといいます。

list-7
```cs
using System;

namespace Sample
{
	class Point
	{
		public Point() {
			Console.WriteLine("コンストラクタを呼び出しました");
		}
		
		public Point(int x, int y) {
			Console.WriteLine("コンストラクタを呼び出しました");
			X = x;
			Y = y;
			Console.WriteLine("{0}, {1}で初期化しました", x, y);
			
		}
	
		public void Print() {
			Console.WriteLine("({0},{1})", X, Y);
		}
		
		public int X
		{
			get;
			set;
		}
	
		public int Y 
		{
			get;
			set;
		}
	}
	
	class Program {
		[STAThread]
		public static void Main(string[] args) {
			Point p = new Point(10, 11);
			p.Print();
			
			p.X = 12;
			p.Y = 13;
			p.Print();
		}
	}
}
```
また、プロパティはnew statementでも呼び出すことができます。

list-8
```cs
class Program {
     [STAThread]
      public static void Main(string[] args) {
	  Point p = new Point()
	  {
                X = 10,
                Y = 11
          };
			
	  p.Print();
	}
}
```
