using System;

namespace Sample {
    public static class Extensions {
        // Where() オペレーターをシンプルに実装します
        public static IObservable<T> Where<T>(this IObservable<T> self, Func<T, bool> predicate) {
            var observable = new AnonymousObservable<T>(observer => {
                var disposable = self.Subscribe(new AnonymousObserver<T>(value => {
                    // ここが OnNext される内容
                    // 条件に一致したときだけ observer に値を流します
                    if (predicate(value)) {
                        observer.OnNext(value);
                    }
                }));
                return disposable;
            });
            return observable; // self ではなく別の observable を返す
        }

        // Select() オペレーターをシンプルに実装します
        public static IObservable<TResult> Select<T, TResult>(this IObservable<T> self, Func<T, TResult> selector) {
            var observable = new AnonymousObservable<TResult>(observer => {
                var disposable = self.Subscribe(new AnonymousObserver<T>(value => {
                    // ここが OnNext される内容
                    // 渡ってきた値を加工して observer にその値を流します
                    TResult result = selector(value); 
                    observer.OnNext(result);
                }));
                return disposable;
            });
            return observable; // self ではなく別の observable を返す
        }

        // Subscribe() をシンプルに実装します
        public static IDisposable Subscribe<T>(this IObservable<T> self, Action<T> onNext) {
            var disposable = self.Subscribe(new AnonymousObserver<T>(onNext)); // IObserver の実装を生成します
            return disposable;
        }
    }
}
