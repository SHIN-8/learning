using System;

namespace Sample {
    // シンプルな IObserver 実装クラス
    public class HogeObserver : IObserver<string> {

        public void OnCompleted() {
            throw new NotImplementedException(); // ※実装省略
        }

        public void OnError(Exception error) {
            throw new NotImplementedException(); // ※実装省略
        }

        // 変更通知を受け取るメソッド
        public void OnNext(string value) {
            Console.WriteLine("OnNext: " + value); // ここではコンソールに表示する
        }
    }
}
