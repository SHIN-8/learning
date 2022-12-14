---
marp: true
paginate: true

---
# 勉強会: .NET で Linux の GNOME アプリを作ってみる
2022-08-30  
株式会社キングプリンターズ システム課

---
## 目的
+ Windows 11 の WSL2 Ubuntu で .NET を用いて Linux の GNOMEアプリを開発してみる
    + ※本記事では .NET 5.0 を使用しています
    + ※本記事では Visual Studio (※codeではない方) はインストールしません
---
## 意図
+ WSL2 で GNOMEアプリをビルド・実行出来るか確認します
    + Ubuntu での操作以降は macOS の方々にも参考になると思います
    + 以下全てのコマンド操作は Ubuntu 上で行います

---
## 目次
1. WSL2 について
1. Ubuntu について
1. .NET について
1. GNOME について
1. GTKプログラミング について
1. 開発環境について
1. おまけ

---
## WSL2 について
+ Windows Subsystem for Linux (WSL)とは、Linux のバイナリ実行ファイルを Windows OS 上でネイティブ実行するための互換レイヤーです

#### 特徴として
+ 本物の Linux カーネルを使用する
+ Linux カーネルを仮想マシン上で動作させる
+ Linux 側のファイルシステムは NTFS 上のディレクトリではなくディスクイメージファイルに格納する
+ ホストとなる Windows とは別のIPアドレスを使用する
などがあります

---
## Ubuntu について
+ Ubuntu は Debian GNU/Linux を母体としたオペレーティングシステム (OS)です
+ Linux ディストリビューションの1つであり、フリーソフトウェアとして提供されています
+ WSL2 では デフォルトの Linux ディストリビューションとして Ubuntu 20.04 LTS が提供されています

+ バージョン確認
```sh
$  cat /etc/os-release
NAME="Ubuntu"
VERSION="20.04 LTS (Focal Fossa)"
ID=ubuntu
ID_LIKE=debian
PRETTY_NAME="Ubuntu 20.04 LTS"
VERSION_ID="20.04"
HOME_URL="https://www.ubuntu.com/"
SUPPORT_URL="https://help.ubuntu.com/"
BUG_REPORT_URL="https://bugs.launchpad.net/ubuntu/"
PRIVACY_POLICY_URL="https://www.ubuntu.com/legal/terms-and-policies/privacy-policy"
VERSION_CODENAME=focal
UBUNTU_CODENAME=focal
```

---
## .NET について
+ .NET は、マイクロソフトが開発した .NET を実装したフリーでオープンソースなクロスプラットフォームマネージソフトウェアフレームワークです

#### 対応 OS
+ Windows
+ Linux
+ macOS

---
### .NET SDK をインストールする

+ パッケージリポジトリの追加
```sh
$ cd ~
$ wget https://packages.microsoft.com/config/ubuntu/20.04/packages-microsoft-prod.deb -O packages-microsoft-prod.deb
$ sudo dpkg -i packages-microsoft-prod.deb
```
+ ※CentOS7 の場合
```sh
$ cd ~
$ sudo rpm -Uvh https://packages.microsoft.com/config/centos/7/packages-microsoft-prod.rpm
```

+ インストール
```sh
$ sudo apt update
$ sudo apt install dotnet-sdk-5.0
```
+ ※CentOS7 の場合
```
$ sudo yum install dotnet-sdk-5.0
```

+ 確認
```sh
$ dotnet --list-sdks
5.0.408 [/usr/share/dotnet/sdk]
$ dotnet --version
5.0.408
```

---
### .NET のコンソールアプリを作成・実行してみる

+ 新しいコンソールアプリの作成
```sh
$ cd ~/tmp
$ dotnet new console -o MyApp -f net5.0
# MyApp がアプリ名です
```

+ コンソールアプリをビルド・実行する
```sh
$ cd ~/tmp/MyApp
$ dotnet run
Hello World!
```

---
### .NET のコンソールアプリの内容を確認

+ 構成要素の確認
```sh
$ cd ~/tmp/MyApp/
$ ls -la
MyApp.csproj
Program.cs
bin
obj
```
※ビルドされた成果物が **bin** ディレクトリに出力されます 

+ 実行ファイルの確認
```sh
$ cd ~/tmp/MyApp/bin/Debug/net5.0
$ ls -la
# MyApp が実行ファイルに相当します
```

+ プロジェクトファイルの確認
```sh
$ cd ~/tmp/MyApp/
$ cat MyApp.csproj
```
```xml
<Project Sdk="Microsoft.NET.Sdk">
    <PropertyGroup>
        <OutputType>Exe</OutputType>
        <TargetFramework>net5.0</TargetFramework>
    </PropertyGroup>
</Project>
```

+ プログラムの確認
```sh
$ cd ~/tmp/MyApp/
$ cat Program.cs
```
```csharp
using System;
namespace MyApp {
    class Program {
        static void Main(string[] args) {
            Console.WriteLine("Hello World!");
        }
    }
}
```

---
### ここまでのまとめ
+ Ubuntu に dotnet SDK をインストールして .NET の開発環境を構築しました
+ dotnet コマンドによりコンソールアプリを新規作成・ビルド・実行することが出来ました
+ dotnet コンソールアプリの最小構成要素は以下のファイルです
    + MyApp.csproj (※拡張子より前のファイル名はアプリ名と同じ)
    + Program.cs

---
## GNOME について
+ GNOME は X Window System 上で動作するデスクトップ環境、またはその開発プロジェクトです
    + ウィジェット・ツールキットには GTK が採用されています

### GTK について
+ クロスプラットフォームのウィジェット・ツールキットです
+ GNOMEデスクトップ環境のツールキット等として広く利用されています
+ FreeBSD や Linux、Windows、macOS 等に移植されています
+ 開発言語として C, C++, JavaScript, Perl, Python, Rust, Java, C# 等が利用できます

---
## GTKプログラミング について
#### ここでは GTK を使用して GUI アプリケーションを開発します
+ GTK のバージョンは3系を使用します

#### 開発環境の構築

+ [GTK 開発環境](https://www.gtk.org/)のインストール
```sh
$ sudo apt install libgtk-3-dev
```
+ ※CentOS7 の場合
```
$ sudo yum install gtk3-devel
```

---
### GtkSharp について
+ [GtkSharp](https://github.com/GtkSharp/GtkSharp) は GTK GUIツールキットおよび GNOME ライブラリの .NET バインディングセットです

### GtkSharp をインストールする

+ インストール
```sh
$ dotnet new --install GtkSharp.Template.CSharp
```

---
### Glade について
+ [Glade](https://glade.gnome.org/) は GTK 用 GUIビルダです
+ アプリの UI を XML 形式のファイルで定義します

### Glade をインストールする

+ インストール
```sh
$ sudo apt install glade
```
+ ※CentOS7 の場合
```
$ sudo yum install glade
```

---
### GTK GUIアプリを作成・実行してみる

+ 新規 GTK GUIプロジェクト作成
```sh
$ cd ~/tmp/
$ dotnet new gtkapp -t net5.0 -n MyGtkApp
# MyGtkApp がアプリ名です
# .NET バージョン 5.0 を指定しています
```

+ GUIアプリをビルド・実行する
```sh
$ cd ~/tmp/MyGtkApp
$ dotnet run
```

---
### .GTK GUIアプリの内容を確認

+ 実行ファイルの確認
```sh
$ cd ~/tmp/MyGtkApp/bin/Debug/net5.0
$ ls -la
#  MyGtkApp が実行ファイルに相当します
```

+ プロジェクトファイルの確認
```sh
$ cd ~/tmp/MyGtkApp/
$ cat MyGtkApp.csproj
```
```xml
<Project Sdk="Microsoft.NET.Sdk">
    <PropertyGroup>
        <OutputType>WinExe</OutputType>
        <TargetFramework>net5.0</TargetFramework>
    </PropertyGroup>
    <ItemGroup>
        <None Remove="**\*.glade" />
        <EmbeddedResource Include="**\*.glade">
            <LogicalName>%(Filename)%(Extension)</LogicalName>
        </EmbeddedResource>
    </ItemGroup>
    <ItemGroup>
        <PackageReference Include="GtkSharp" Version="3.24.24.*" />
    </ItemGroup>
</Project>
```

---
+ メインプログラムの確認
```sh
$ cd ~/tmp/MyGtkApp/
$ cat Program.cs
```
```csharp
using System;
using Gtk;
namespace MyGtkApp {
    class Program {
        [STAThread]
        public static void Main(string[] args) {
            Application.Init();
            var app = new Application("org.MyGtkApp.MyGtkApp", GLib.ApplicationFlags.None);
            app.Register(GLib.Cancellable.Current);
            var win = new MainWindow();
            app.AddWindow(win);
            win.Show();
            Application.Run();
        }
    }
}
```

---
+ ウインドウプログラムの確認
```sh
$ cd ~/tmp/MyGtkApp/
$ cat MainWindow.cs
```
```csharp
using System;
using Gtk;
using UI = Gtk.Builder.ObjectAttribute;
namespace MyGtkApp {
    class MainWindow : Window　{
        [UI] private Label _label1 = null;
        [UI] private Button _button1 = null;
        private int _counter;
        public MainWindow() : this(new Builder("MainWindow.glade")) { }
        private MainWindow(Builder builder) : base(builder.GetRawOwnedObject("MainWindow"))　{
            builder.Autoconnect(this);
            DeleteEvent += Window_DeleteEvent;
            _button1.Clicked += Button1_Clicked;
        }
        private void Window_DeleteEvent(object sender, DeleteEventArgs a)　{
            Application.Quit();
        }
        private void Button1_Clicked(object sender, EventArgs a)　{
            _counter++;
            _label1.Text = "Hello World! This button has been clicked " + _counter + " time(s).";
        }
    }
}
```

---
+ UIファイルの確認
```sh
$ cd ~/tmp/MyGtkApp/
$ cat MainWindow.glade
```
```xml
<?xml version="1.0" encoding="UTF-8"?>
<interface>
    <requires lib="gtk+" version="3.18"/>
    <object class="GtkWindow" id="MainWindow">
        <property name="can_focus">False</property>
        <property name="title" translatable="yes">Example Window</property>
        <property name="default_width">480</property>
        <property name="default_height">240</property>
        <child>
            <object class="GtkBox">
                <property name="visible">True</property>
                <property name="can_focus">False</property>
                <property name="margin_left">4</property>
                <property name="margin_right">4</property>
                <property name="margin_top">4</property>
                <property name="margin_bottom">4</property>
                <property name="orientation">vertical</property>
                <!-- 一部省略 -->
        </child>
    </object>
</interface>
```

---
### ここまでのまとめ
+ Ubuntu に GTK、GtkSharp SDK をインストールして GTK の開発環境を構築しました
+ dotnet コマンドにより GTK GUIアプリを新規作成・ビルド・実行することが出来ました
+ dotnet GTK GUIアプリの最小構成要素は以下のファイルです
    + MyGtkApp.csproj (※拡張子より前のファイル名はアプリ名と同じ)
    + Program.cs
    + MainWindow.cs
    + MainWindow.glade

---
## 開発環境について
+ VS Code で開発してみます
+ Glade UI エディタを使用してみます

---
### VS Code で開発してみます

#### ワークスペースファイルの作成

+ ファイル作成
```sh
$ cd ~/tmp/MyGtkApp/
$ touch vs.code-workspace
$ vim vs.code-workspace
$ code .
```
+ 記述する内容
```json
{
	"folders": [
		{
			"uri": "vscode-remote://wsl+ubuntu/home/kpu0560/tmp/MyGtkApp" // ※各自の環境で読みかえて下さい
		}
	],
	"remoteAuthority": "wsl+Ubuntu",
	"settings": {}
}
```

#### VS code のプラグインをインストール
+ Remote - SSH
+ C#

#### 実行とデバッグ設定
+ [実行とデバッグ]ボタン
    + 環境の選択
        + .NET 5.0+ and .NET Core
        + VS Code からデバッグブレークが可能

---
## Glade UI エディタを使用してみます

```sh
$ cd ~/tmp/MyGtkApp/
$ glade MainWindow.glade
```

---
## まとめ
+ Ubuntu で GNOMEアプリを作成・ビルド・実行することが出来ました
+ ウィジェット・ツールキット GTK は広く使用されているので、WEBブラウザを使用しないクロスプラットフォームUI開発の選択肢の一つになると思いました
+ .NET の Linux 対応により C# で開発出来るアプリの幅が広がったと思いました

---
## ご清聴ありがとうございました

---
## 参考資料
+ [GTK公式](https://docs.gtk.org/gtk3/)
+ [GtkSharp](https://www.mono-project.com/docs/gui/gtksharp/)

---
## おまけ

---
## Python で 同様の GTK GUIアプリを作成してみる

#### Python の実行環境の構築

+ バージョン確認
``` 
$ python3 --version
$ Python 3.8.2
```

+ [PyGObject](https://pygobject.readthedocs.io/en/latest/getting_started.html) をインストール
```sh
$ sudo apt install python3-gi python3-gi-cairo gir1.2-gtk-3.0
```

#### Python スクリプトの作成と実行

```sh
$ cd ~/tmp
$ touch mygtkapp.py
$ vim mygtkapp.py
```
```py
import gi

gi.require_version("Gtk", "3.0")
from gi.repository import Gtk

win = Gtk.Window()
win.connect("destroy", Gtk.main_quit)
win.show_all()
Gtk.main()
```

+ スクリプト実行
```sh
$ cd ~/tmp
$ python3 mygtkapp.py
```

#### Python スクリプトの修正①
+ [ボタンを押したらコンソールに "Hello World" と表示するアプリ](https://python-gtk-3-tutorial.readthedocs.io/en/latest/introduction.html#extended-example)

```sh
$ cd ~/tmp
$ vim mygtkapp.py
$ python3 mygtkapp.py
```
```py
import gi
gi.require_version('Gtk', '3.0')
from gi.repository import Gtk

class MyWindow(Gtk.Window):
    def __init__(self):
        super().__init__(title="Hello World")
        self.button = Gtk.Button(label="Click Here")
        self.button.connect("clicked", self.on_button_clicked)
        self.add(self.button)

    def on_button_clicked(self, widget):
        print("Hello World")

win = MyWindow()
win.connect("destroy", Gtk.main_quit)
win.show_all()
Gtk.main()
```

#### Python スクリプトの修正②
+ 上記 C# での実装を移植してみる

```sh
$ cd ~/tmp
$ vim mygtkapp.py
$ python3 mygtkapp.py
```
```py
import gi
gi.require_version('Gtk', '3.0')
from gi.repository import Gtk as App

class MainWindow(App.Window):
    def __init__(self):
        super().__init__(title="Example Window")
        self.counter: int = 0
        self.vbox = App.VBox()
        self.label1 = App.Label(label="Hello World!")
        self.button1 = App.Button(label="Click me!")
        self.button1.connect("clicked", self.on_button_clicked)
        self.add(self.vbox)
        self.vbox.pack_start(self.label1, expand = True, fill = True, padding = 0)
        self.vbox.pack_start(self.button1, expand = False, fill = True, padding = 0)
        self.set_default_size(480, 240)

    def on_button_clicked(self, widget):
        self.counter += 1
        self.label1.set_text("Hello World! This button has been clicked " + str(self.counter) + " time(s).")

win = MainWindow()
win.connect("destroy", App.main_quit)
win.show_all()
App.main()
```

---
#### [参考:The Python GTK+ 3 Tutorial](https://python-gtk-3-tutorial.readthedocs.io/en/latest/index.html)
+ [GTK+ layout management](https://zetcode.com/gui/gtk2/gtklayoutmanagement/)
+ [Python gtk.VBox() Examples](https://www.programcreek.com/python/example/583/gtk.VBox)

---
## GTK デモンストレーションアプリの使用

+ インストール
```sh
$ sudo apt install gtk-3-examples
```

+ 実行※それぞれ
```sh 
$ gtk3-demo
$ gtk3-icon-browser
$ gtk3-widget-factory
```
