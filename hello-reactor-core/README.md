---
marp: true
---

# 勉強会 Java リアクティブプログラミング(Reactor Core)

---
## この資料で説明すること
+ Reactor Core を、とにかく動かしてみる。

---
## Reactor Core とは？  
Reactor Core はリアクティブプログラミングモデルを実装するJava 8ライブラリです。これはリアクティブアプリケーションを構築するための標準である http://www.reactive-streams.org/ の上に構築されています。

---
## Reactive Streams とは?  
ノンブロッキングな back pressure 可能な非同期ストリーム処理の標準的な仕様を提供しようというもので、実装としては Akka Streams、RxJava、Reactor Composable、Ratpack などがあります。  
Reactor Core も Reactive Streams の実装の一つです。

---
## そもそも リアクティブプログラミングとは何なのか？  
リアクティブプログラミング（Reactive Programming）は、最近注目されているプログラミングパラダイムの一つで、特定の言語やフレームワークにかかわらず適用できるスタイルのようなものです。

+ データフローの定義
+ 変更の伝搬

--- 
## Maven でライブラリを取得する
```xml
<dependency>
    <groupId>io.projectreactor</groupId>
    <artifactId>reactor-core</artifactId>
    <version>3.0.5.RELEASE</version>
</dependency>
```

---
## Flux をとにかく使う

---
+ subscirbe で取得します
```java
@Test
public void flux() {
    List<String> received = new ArrayList<>();

    // subscirbe で取得します
    Flux.fromIterable(Arrays.asList("Hello", "World", "Reactor", "Flux"))
            .subscribe(s -> received.add(s));

    assertThat(received).containsExactly("Hello", "World", "Reactor", "Flux");
}
```

---
+ map で変換します
```java
@Test
public void fluxMap() {
    List<String> received = new ArrayList<>();

    // map で変換します
    Flux.fromIterable(Arrays.asList("Hello", "World", "Reactor", "Flux"))
            .map(s -> "★" + s + "★")
            .subscribe(s -> received.add(s));

    assertThat(received).containsExactly("★Hello★", "★World★", "★Reactor★", "★Flux★");
}
```

---
+ take で、最初の2つを選びます
```java
@Test
public void fluxTake() {
    List<String> received = new ArrayList<>();

    // take で、最初の2つを選びます
    Flux.fromIterable(Arrays.asList("Hello", "World", "Reactor", "Flux"))
            .take((2))
            .subscribe(s -> received.add(s));

    assertThat(received).containsExactly("Hello", "World");
}
```

---
+ filter で絞り込みます
```java
@Test
public void fluxFilter() {
    List<String> received = new ArrayList<>();

    // filter で絞り込みます
    Flux.fromIterable(Arrays.asList("Hello", "World", "Reactor", "Flux"))
            .filter(s -> s.contains("e"))
            .subscribe(s -> received.add(s));

    assertThat(received).containsExactly("Hello", "Reactor");
}
```

---
+ buffer で、複数の要素をまとめて subscribe 時に List で受け取ります
```java
@Test
public void fluxBuffer() {
    List<List<String>> received = new ArrayList<>();

    // buffer で、複数の要素をまとめて subscribe 時に List で受け取ります
    Flux.fromIterable(Arrays.asList("Hello", "World", "Reactor", "Flux"))
            .buffer(2)
            .subscribe(strings -> received.add(strings));

    assertThat(received).containsExactly(Arrays.asList("Hello", "World"), Arrays.asList("Reactor", "Flux"));
}
```

---
+ 2つの Flux をマージします
```java
@Test
public void fluxMergeWith() {
    List<String> received = new ArrayList<>();

    // 2つの Flux をマージします
    Flux.fromIterable(Arrays.asList("Hello", "World", "Reactor", "Flux"))
            .mergeWith(Flux.fromIterable(Arrays.asList("A", "B", "C", "D")))
            .subscribe(s -> received.add(s));

    assertThat(received).containsExactly("Hello", "World", "Reactor", "Flux", "A", "B", "C", "D");
}
```

---
+ 2つの Flux を zip で結合します
```java
@Test
public void fluxZip() {
    List<String> received = new ArrayList<>();

    // 2つの Flux を zip で結合します
    Flux.fromIterable(Arrays.asList("Hello", "World", "Reactor", "Flux"))
            .zipWith(Flux.fromIterable(Arrays.asList("A", "B", "C", "D")))
            .subscribe(tuple -> received.add(tuple.getT1() + "-" + tuple.getT2()));

    assertThat(received).containsExactly("Hello-A", "World-B", "Reactor-C", "Flux-D");
}
```

---
+ これらの操作を組み合わせて使うこともできます
```java
@Test
public void fluxUsing() {
    List<String> received = new ArrayList<>();

    // これらの操作を組み合わせて使うこともできます
    Flux.fromIterable(Arrays.asList("Hello", "World", "Reactor", "Flux"))
            .take(2)
            .map(s -> "★" + s + "★")
            .mergeWith(Flux.fromIterable(Arrays.asList("A", "B")))
            .buffer(2)
            .subscribe(strings -> received.add(strings.get(0) + "-" + strings.get(1)));

    assertThat(received).containsExactly("★Hello★-★World★", "A-B");
}
```

---
+ subscribe で受け取るだけではなく、Iterable などの形で戻り値としても返却することも可能です
```java
@Test
public void fluxBlock() {

    // subscribe で受け取るだけではなく、Iterable などの形で戻り値としても返却することも可能です
    Iterable<String> result = Flux.fromIterable(Arrays.asList("Hello", "World", "Reactor", "Flux"))
            .map(s -> "★" + s + "★")
            .toIterable();

    assertThat(result).containsExactly("★Hello★", "★World★", "★Reactor★", "★Flux★");
}
```

---
## Mono をとにかく使う

---
+ subscirbe で取得します
```java
@Test
public void mono() {
    AtomicReference<String> result = new AtomicReference<>();

    // subscirbe で取得します
    Mono.fromSupplier(() -> "Hello World")
            .subscribe(s -> result.set(s));

    assertThat(result.get()).isEqualTo("Hello World");
}
```

---
+ Flux のように map などの操作が可能です
```java
@Test
public void monoMap() {
    AtomicReference<String> result = new AtomicReference<>();

    // Flux のように map などの操作が可能です
    Mono.fromSupplier(() -> "Hello World")
            .map(s -> "★" + s + "★")
            .subscribe(s -> result.set(s));

    assertThat(result.get()).isEqualTo("★Hello World★");
}
```

---
+ subscribe ではなく、結果を戻り値として受け取ることもできます
```java
@Test
public void monoResult() {

    // subscribe ではなく、結果を戻り値として受け取ることもできます
    String result = Mono.fromSupplier(() -> "Hello World")
            .map(s -> "★" + s + "★")
            .block();

    assertThat(result).isEqualTo("★Hello World★");
}
```

---
## Flux と Mono の変換も出来ます

---
+ Flux から Mono にするには、Flux#nextなどが使えます
```java
@Test
public void fluxToMono() {

    // Flux から Mono にするには、Flux#nextなどが使えます
    // Flux#last や、単一要素のみ保持している場合には Flux#single が使えたりします。
    Mono<String> mono = Flux.fromIterable(Arrays.asList("Hello", "World", "Reactor", "Flux"))
            .next();

    assertThat(mono.block()).isEqualTo("Hello");
}
```

---
+ Flux#collect を使って、単一の要素にして Mono にすることもできます
```java
@Test
public void fluxToMonoCollect() {
    
    // Flux#collect を使って、単一の要素にして Mono にすることもできます
    Mono<String> mono = Flux.fromIterable(Arrays.<CharSequence>asList("Hello", "World", "Reactor", "Flux"))
            .collect(Collectors.joining("-"));

    assertThat(mono.block()).isEqualTo("Hello-World-Reactor-Flux");
}
```

---
+ Mono から Flux の変換では、Mono#flux が使えます
```java
@Test
public void monoToFlux() {

    // Mono から Flux の変換では、Mono#flux が使えます
    Flux<String> flux = Mono.fromSupplier(() -> "Hello World")
            .flux();

    assertThat(flux.toIterable()).containsExactly("Hello World");
}
```

---
+ Mono#flatMap で Flux にすることも出来ます
```java
@Test
public void monoToFluxFlatMap() {

    // Mono#flatMap で Flux にすることも出来ます
    Flux<String> flux = Mono.fromSupplier(() -> "Hello World")
            .flatMap(s -> Flux.fromIterable(Arrays.asList(s)));

    assertThat(flux.toIterable()).containsExactly("Hello World");
}
```

---
## Java Stream API っぽいけど何が違うの？  
主な違いは、Reactiveがプッシュモデルであるのに対し、Java 8 Streams はプルモデルであることです。 反応的アプローチです。イベントが購読者に「プッシュ」されます。

---
+ 例えば以下のような操作が可能です
```java
@Test
public void givenFlux_whenApplyingBackPressure_thenPushElementsInBatches() {

    List<Integer> elements = new ArrayList<>();

    Flux.just(1, 2, 3, 4)
        .log()
        .subscribe(new Subscriber<Integer>() {
            private Subscription s;
            int onNextAmount;

            @Override
            public void onSubscribe(final Subscription s) {
                this.s = s;
                s.request(2);
            }

            @Override
            public void onNext(final Integer integer) {
                elements.add(integer);
                onNextAmount++;
                if (onNextAmount % 2 == 0) {
                    s.request(2);
                }
            }

            @Override
            public void onError(final Throwable t) {
            }

            @Override
            public void onComplete() {
            }
        });

    assertThat(elements).containsExactly(1, 2, 3, 4);
}
```

---
+ 以下のようにロギングされます
```sh
Running com.exam.reactor.core.HelloReactorTest
15:06:12.825 [main] DEBUG reactor.util.Loggers$LoggerFactory - Using Slf4j logging framework
15:06:13.029 [main] INFO  reactor.Flux.Array.1 - | onSubscribe([Synchronous Fuseable] FluxArray.ArraySubscription)
15:06:13.032 [main] INFO  reactor.Flux.Array.1 - | request(2)
15:06:13.033 [main] INFO  reactor.Flux.Array.1 - | onNext(1)
15:06:13.033 [main] INFO  reactor.Flux.Array.1 - | onNext(2)
15:06:13.033 [main] INFO  reactor.Flux.Array.1 - | request(2)
15:06:13.033 [main] INFO  reactor.Flux.Array.1 - | onNext(3)
15:06:13.033 [main] INFO  reactor.Flux.Array.1 - | onNext(4)
15:06:13.033 [main] INFO  reactor.Flux.Array.1 - | request(2)
15:06:13.033 [main] INFO  reactor.Flux.Array.1 - | onComplete()
Tests run: 17, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 0.732 sec
```
コードを実行すると、Subscription 型の request（2） が呼び出され、続いて onNext（） が2回呼び出されてから、再度 Subscription 型の request（2） が呼び出されます。  

このように プッシュモデルで処理を記述することが実現できます。

---
## まとめ  
Reactive Core で、簡単なストリームの操作を行いました。  
しかし、今回は [reactor-core](https://github.com/reactor/reactor-core) の本質については調査不足で説明しきれなかったので、機会があれば再度説明したいと思います。

---
## ちなみにですが  
自作のモバイル用3Dアクションゲーム(※開発中) [super-nekokun](https://github.com/hiroxpepe/super-nekokun) は Unity の C# 実装ですが、UniRx と呼ばれるライブラリを使用しており、通常の Unity の作法ではなく、リアクティブプログラミング の UniRx の作法で記述しています。  

---
+ 例えば敵キャラのAIルーチン(※と言えるほどでもないですが…)
```csharp
// パンチ攻撃中には
this.UpdateAsObservable()
    .Where(_ => doUpdate.attacking && !_damaged)
    .Subscribe(_ => {
        simpleAnime.Play("Punch"); // パンチアニメを表示する
        transform.LookAt(new Vector3(
            _playerObject.transform.position.x,
            transform.position.y,
            _playerObject.transform.position.z
        ));
    });
```
---
```csharp
// プレイヤー接触時にパンチ攻撃する
this.OnCollisionEnterAsObservable()
    .Where(x => !doUpdate.attacking && !_damaged && x.IsPlayer())
    .Subscribe(_ => {
        doUpdate.ApplyAttacking();
        soundSystem.PlayDamageClip();
        say("Punch!", 65); // セリフ表示
        _playerObject.GetPlayer().DecrementLife();
        _playerObject.GetPlayer().DamagedByEnemy(transform.forward);
        Observable.TimerFrame(12) // 12フレ後に追跡状態に移行する
            .Subscribe(__ => {
                doUpdate.ApplyChasing();
            });
    });
```
---
```csharp
// プレイヤー接触中はパンチ攻撃を繰り返す
bool _wait = false;
this.OnCollisionStayAsObservable()
    .Where(x => !doUpdate.attacking && !_damaged && x.IsPlayer())
    .Subscribe(_ => {
        doUpdate.ApplyChasing();
        Observable.TimerFrame(24).Where(__ => !_wait)  // 24フレ後に以下のアクションを実行する
            .Subscribe(__ => {
            _wait = true;
            doUpdate.ApplyAttacking();
            soundSystem.PlayDamageClip(); // パンチ効果音を鳴らす
            say("Take this!", 60); // セリフを表示する
            _playerObject.GetPlayer().DecrementLife(); // プレイヤーのライフを削る
            _playerObject.GetPlayer().DamagedByEnemy(transform.forward);
            Observable.TimerFrame(12) // 12フレ後に追跡状態に移行する
                .Subscribe(___ => {
                    doUpdate.ApplyChasing();
                    _wait = false;
                });
        });
    });
```
このように、ゲームのプログラムではイベントが発火したら何かの処理をするという、リアクティブプログラミングのプッシュモデルは相性が良いと思いました。

---
Reactive Extensions の総本山はここです
[Reactive Extensions](https://docs.microsoft.com/en-us/previous-versions/dotnet/reactive-extensions/hh242985(v=vs.103)?redirectedfrom=MSDN)

---
ご清聴ありがとうございました。🙇‍♂
