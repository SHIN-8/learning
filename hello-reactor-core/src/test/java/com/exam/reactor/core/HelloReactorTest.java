package com.exam.reactor.core;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import org.junit.Test;
import static org.assertj.core.api.Assertions.*;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

/**
 * @author h.adachi
 */
public class HelloReactorTest {

    // Flux ///////////////////////////////////////////////////////////////////////////////////////
    //
    @Test
    public void flux() {
        List<String> received = new ArrayList<>();

        // subscirbe で取得します
        Flux.fromIterable(Arrays.asList("Hello", "World", "Reactor", "Flux"))
                .subscribe(s -> received.add(s));

        assertThat(received).containsExactly("Hello", "World", "Reactor", "Flux");
    }

    @Test
    public void fluxMap() {
        List<String> received = new ArrayList<>();

        // map で変換します
        Flux.fromIterable(Arrays.asList("Hello", "World", "Reactor", "Flux"))
                .map(s -> "★" + s + "★")
                .subscribe(s -> received.add(s));

        assertThat(received).containsExactly("★Hello★", "★World★", "★Reactor★", "★Flux★");
    }

    @Test
    public void fluxTake() {
        List<String> received = new ArrayList<>();

        //　take で、最初の2つを選びます
        Flux.fromIterable(Arrays.asList("Hello", "World", "Reactor", "Flux"))
                .take((2))
                .subscribe(s -> received.add(s));

        assertThat(received).containsExactly("Hello", "World");
    }

    @Test
    public void fluxFilter() {
        List<String> received = new ArrayList<>();

        // filter で絞り込みます
        Flux.fromIterable(Arrays.asList("Hello", "World", "Reactor", "Flux"))
                .filter(s -> s.contains("e"))
                .subscribe(s -> received.add(s));

        assertThat(received).containsExactly("Hello", "Reactor");
    }

    @Test
    public void fluxBuffer() {
        List<List<String>> received = new ArrayList<>();

        // buffer で、複数の要素をまとめて subscribe 時に List で受け取ります
        Flux.fromIterable(Arrays.asList("Hello", "World", "Reactor", "Flux"))
                .buffer(2)
                .subscribe(strings -> received.add(strings));

        assertThat(received).containsExactly(Arrays.asList("Hello", "World"), Arrays.asList("Reactor", "Flux"));
    }

    @Test
    public void fluxMergeWith() {
        List<String> received = new ArrayList<>();

        // 2つの Flux をマージします
        Flux.fromIterable(Arrays.asList("Hello", "World", "Reactor", "Flux"))
                .mergeWith(Flux.fromIterable(Arrays.asList("A", "B", "C", "D")))
                .subscribe(s -> received.add(s));

        assertThat(received).containsExactly("Hello", "World", "Reactor", "Flux", "A", "B", "C", "D");
    }

    @Test
    public void fluxZip() {
        List<String> received = new ArrayList<>();

        // 2つの Flux を zip で結合します
        Flux.fromIterable(Arrays.asList("Hello", "World", "Reactor", "Flux"))
                .zipWith(Flux.fromIterable(Arrays.asList("A", "B", "C", "D")))
                .subscribe(tuple -> received.add(tuple.getT1() + "-" + tuple.getT2()));

        assertThat(received).containsExactly("Hello-A", "World-B", "Reactor-C", "Flux-D");
    }

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

    @Test
    public void fluxBlock() {

        // subscribe で受け取るだけではなく、Iterable などの形で戻り値としても返却することも可能です
        Iterable<String> result = Flux.fromIterable(Arrays.asList("Hello", "World", "Reactor", "Flux"))
                .map(s -> "★" + s + "★")
                .toIterable();

        assertThat(result).containsExactly("★Hello★", "★World★", "★Reactor★", "★Flux★");
    }

    // Mono ///////////////////////////////////////////////////////////////////////////////////////
    //
    @Test
    public void mono() {
        AtomicReference<String> result = new AtomicReference<>();

        // subscirbe で取得します
        Mono.fromSupplier(() -> "Hello World")
                .subscribe(s -> result.set(s));

        assertThat(result.get()).isEqualTo("Hello World");
    }

    @Test
    public void monoMap() {
        AtomicReference<String> result = new AtomicReference<>();

        // Flux のように map などの操作が可能です
        Mono.fromSupplier(() -> "Hello World")
                .map(s -> "★" + s + "★")
                .subscribe(s -> result.set(s));

        assertThat(result.get()).isEqualTo("★Hello World★");
    }

    @Test
    public void monoResult() {

        // subscribe ではなく、結果を戻り値として受け取ることもできます
        String result = Mono.fromSupplier(() -> "Hello World")
                .map(s -> "★" + s + "★")
                .block();

        assertThat(result).isEqualTo("★Hello World★");
    }

    // Flux と Mono の変換 ////////////////////////////////////////////////////////////////////////
    //
    @Test
    public void fluxToMono() {

        // Flux から Mono にするには、Flux#nextなどが使えます
        // Flux#last や、単一要素のみ保持している場合には Flux#single が使えたりします。
        Mono<String> mono = Flux.fromIterable(Arrays.asList("Hello", "World", "Reactor", "Flux"))
                .next();

        assertThat(mono.block()).isEqualTo("Hello");
    }

    @Test
    public void fluxToMonoCollect() {

        // Flux#collect を使って、単一の要素にして Mono にすることもできます
        Mono<String> mono = Flux.fromIterable(Arrays.<CharSequence>asList("Hello", "World", "Reactor", "Flux"))
                .collect(Collectors.joining("-"));

        assertThat(mono.block()).isEqualTo("Hello-World-Reactor-Flux");
    }

    @Test
    public void monoToFlux() {

        // Mono から Flux　の変換では、Mono#flux が使えます
        Flux<String> flux = Mono.fromSupplier(() -> "Hello World")
                .flux();

        assertThat(flux.toIterable()).containsExactly("Hello World");
    }

    @Test
    public void monoToFluxFlatMap() {

        // Mono#flatMap で Flux にすることも出来ます
        Flux<String> flux = Mono.fromSupplier(() -> "Hello World")
                .flatMap(s -> Flux.fromIterable(Arrays.asList(s)));

        assertThat(flux.toIterable()).containsExactly("Hello World");
    }

    //  反応的アプローチ
    //
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

}
