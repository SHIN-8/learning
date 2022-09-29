Ktor Frameworkを使ったサンプルサーバー
====

# 環境セットアップ
1. IntelliJ IDEA* をインストールする
1. IntelliJ IDEA > Preference > Plugins から Ktorサポートをインストールする
Ktolinサポートはデフォルトで有効になっている

# プロジェクト作成

ktorプラグインをインストールするとプロジェクト欄にウィザードが追加されるのでウィザードに従って作成するのが良い（今回は未使用）。

![](./images/1.png)
![](./images/2.png)
![](./images/3.png)

# アプリケーションの作成
## HelloWorldアプリケーションの作成
作成したプロジェクトのsrc/main/kotlinにSampleApp.ktを追加する
Netty(サーバーエンジン）にrouting設定を記述し、My Sample Appという文字列を返す。

```kotlin
package sample

import io.ktor.application.*
import io.ktor.http.*
import io.ktor.response.*
import io.ktor.routing.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*

fun main(args: Array<String>) {
    embeddedServer(Netty, 8080) {
        routing {
            get("/") {
                call.respondText("My Sample App", ContentType.Text.Html)
            }
        }
    }.start(wait=true)
}

```

## JSONを返すよう
jacsonを使うとJSONが返せる。

```kotlin
package sample

import com.fasterxml.jackson.databind.SerializationFeature
import io.ktor.application.*
import io.ktor.response.*
import io.ktor.routing.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.jackson.*
import java.util.*

data class Item(val text: String)

val snippets = Collections.synchronizedList(
            mutableListOf(
                    Item("Granblue Fantasy"),
                    Item("Princess Connect Re:Dive")
            )
)

fun main(args: Array<String>) {
    embeddedServer(Netty, 8080) {
           install(io.ktor.features.ContentNegotiation) {
               jackson {
                   enable(SerializationFeature.INDENT_OUTPUT) // Pretty Prints the JSON
               }
           }
            routing {
                   get("/") {
                       call.respond(mapOf("snippets" to synchronized(snippets){ snippets.toList()}))
                   }
               }
    }.start(wait=true)
}

```