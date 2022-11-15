using System;
using System.Collections.Generic;

namespace Sample {
	// シンプルな IObservable 実装クラス
	public class HogeObservable : IObservable<string> {
		// observer のリストを持ちます
		List<IObserver<string>> observers = new List<IObserver<string>>();

		public IDisposable Subscribe(IObserver<string> observer) {
			observers.Add(observer);
			return null; // ※ IDisposable には対応してない
		}

		// 登録された observer に変更内容を通知する
		public void Notify(string value) {
			// 中でリストをループ処理しています
			foreach (var observer in observers) {
				observer.OnNext(value);
			}
		}
	}
}
