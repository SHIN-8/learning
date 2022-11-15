using System;

namespace Sample {
    // デリゲートを処理出来る IObservable の機能を提供します
    public class AnonymousObservable<T> : IObservable<T> {

        Func<IObserver<T>, IDisposable> subscribe = null;
        
        public AnonymousObservable(Func<IObserver<T>, IDisposable> subscribe) { // 引数がデリゲートである
            this.subscribe = subscribe;
        }

        public IDisposable Subscribe(IObserver<T> observer) {
            var disposable = this.subscribe(observer); // デリゲートが実行されます ※ネストされた Observable を持ちます
            return disposable;
        }
    }
}
