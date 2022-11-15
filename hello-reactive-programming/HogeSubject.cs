using System;
using System.Collections.Generic;
using System.Reactive.Subjects;

namespace Sample {
    class HogeSubject : ISubject<string> {
        List<IObserver<string>> observers = new List<IObserver<string>>();

        public void OnCompleted() {
            throw new NotImplementedException(); // ※実装省略
        }

        public void OnError(Exception error) {
            throw new NotImplementedException(); // ※実装省略
        }

        // ここが呼ばれて初めて処理が走る
        public void OnNext(string value) {
            foreach (var observer in this.observers) {
                observer.OnNext(value);
            }
        }

        // ここでは observer が登録されるだけ
        public IDisposable Subscribe(IObserver<string> observer) {
            this.observers.Add(observer);
            return null; // ※ IDisposable には対応してない
        }
    }
}
