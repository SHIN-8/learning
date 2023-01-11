# プログラミング入門 受講手引

## 演習資料について

演習では、Python Japanの[ゼロからのPython入門講座](https://www.python.jp/train/index.html) をベースに解説します。

また、練習問題と解説資料は当日ZIPファイルでMattermostのプログラミング入門研修のチャンネルに投稿しますので、ご確認をお願いします。

## 実行環境の準備

### 開発ツールの入手とインストール

開発ツールは最低限２つ必要です。エディタとPythonの実行環境です。

[Visual Studio Code](https://code.visualstudio.com/download)

[Python - macOS](https://www.python.jp/install/macos/index.html)

上記を参考にVSCodeとPythonをインストールしてください。
なお、演習環境はインストール済みです。

### Visual Studo Codeの拡張機能のインストール

下記の図を参考に、Python拡張機能をインストールします。

![vscode-2](./07_Pythonプログラミング入門.assets/vscode-2.png)

## 演習問題について

演習問題は、穴埋め形式として用意します。穴埋め箇所はTODOと書いているので練習問題の文意に沿って書き換え、次のPythonファイルの実行手順に従ってファイルを実行してください

```py
a = input('Please intput a')
b = input('Please intput b')

if TODO :
    print(TODO)
else:
    print(TODO)

```

## Pythonファイルの実行方法

Pythonは、スクリプト言語と呼ばれる種類のプログラミング言語です。スクリプト言語については、第3回のプログラミング入門で覚えましたね。

Pythonプログラムは、[Visual Studio Code](https://azure.microsoft.com/ja-jp/products/visual-studio-code/)やVimなどのエディターを使って編集をします。

![vscode-1](./07_Pythonプログラミング入門.assets/vscode-1.png)

編集をしたら、拡張子 `.py` をつけて保存します。

Pythonでは、コンパイルという作業が不要であるため、即座に実行することができます。

Pythonプログラムを実行するには、Python3コマンドを使います。macOSの場合にはアプリケーション＞ユーティリティ＞ターミナルを起動します。

![terminal-1](./07_Pythonプログラミング入門.assets/terminal-1.png)

ターミナルが起動したら、`python3` と入力し、作成したファイルをドラッグ・アンド・ドロップするとファイパスが入力されるのでENTERを押すことで実行できます。

![terminal-3](./07_Pythonプログラミング入門.assets/terminal-3.png)

あるいは、Unixコマンドに親しんでいるのであれば、cdコマンドを利用してファイルの保存フォルダに移動して実行しても構いません。

## REPLを使う

REPLとは、Read Eval Print Loopの略で、対話環境です。

REPLを利用するには、macOSの場合にはアプリケーション＞ユーティリティ＞ターミナルを起動します。

ターミナル起動後、 `python3`と入力し、ENTERを押すとREPLが起動します。

![terminal-2](./07_Pythonプログラミング入門.assets/terminal-2.png)

終了する際は、`exit()`と入力しENTERを押します。

## JupyterNotebookについて

研修の配布資料に含まれている、`~.ipnb`のファイルは、JupyterNotebookと呼ばれるファイルで、プログラムを書きながらドキュメントをかける環境です。

こちら、サンプル問題のところは、Pythonコードになっており編集可能ですので、適宜触ってみてください。

上記の作業でコンピュータが壊れるという可能性は低いので、トライ＆エラーを繰り返して覚えるのが一番の近道です。

## リファレンスなど

意欲のある人は、下記のPythonの公式サイトのドキュメントやUdemyなどを受講してみると良いと思います。

[Python 公式ドキュメント](https://docs.python.org/ja/3/)
