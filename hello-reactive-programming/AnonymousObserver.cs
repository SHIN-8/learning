using System;

namespace Sample {
    // デリゲートを処理出来る IObserver の機能を提供します
    public class AnonymousObserver<T> : IObserver<T> {

        Action<T> onNext = null;

        public AnonymousObserver(Action<T> onNext) { // 引数がデリゲートである
            this.onNext = onNext;
        }

        public void OnCompleted() {
            throw new NotImplementedException(); // ※実装省略
        }

        public void OnError(Exception error) {
            throw new NotImplementedException(); // ※実装省略
        }

        public void OnNext(T value) {
            this.onNext(value); // デリゲートが実行されます
        }
    }
}
