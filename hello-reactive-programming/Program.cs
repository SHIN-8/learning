using System;
using System.Collections.Generic;

namespace Sample {
    class Program {
        static void Main(string[] args) {

            if (false) {
                // Observable と Observer を使用
                var observable = new HogeObservable();
                observable.Subscribe(new HogeObserver());
                observable.Notify("Hello");
                observable.Notify("World");
            }

            if (false) {
                // Subject と Observer を使用
                var subject = new HogeSubject();
                subject.Subscribe(new HogeObserver());
                subject.OnNext("Hello");
                subject.OnNext("World");
            }

            if (false) {
                // Subject とラムダ式を使用
                var subject = new HogeSubject();
                subject.Subscribe(x => Console.WriteLine("OnNext: " + x));
                subject.OnNext("Hello");
                subject.OnNext("World");
            }

            if (false) {
                // Where を使用
                var subject = new HogeSubject();
                subject.Where(x => x.Contains("H"))
                .Subscribe(x => Console.WriteLine("OnNext: " + x));
                subject.OnNext("Hello");
                subject.OnNext("World");
            }

            if (false) {
                // Select を使用
                var subject = new HogeSubject();
                subject.Select(x => x + "ですねん")
                .Subscribe(x => Console.WriteLine("OnNext: " + x));
                subject.OnNext("Hello");
                subject.OnNext("World");
            }

            if (true) {
                // Where と Select を使用
                var subject = new HogeSubject();
                subject.Where(x => x.Contains("H"))
                .Select(x => x + "ですねん")
                .Subscribe(x => Console.WriteLine("OnNext: " + x));
                subject.OnNext("Hello");
                subject.OnNext("World");
            }

            if (false) {
                // for や if で実装
                var list = new List<string>();
                list.Add("Hello");
                list.Add("World");
                foreach (var item in list) {
                    if (item.Contains("H")) {
                        var result = item + "ですねん";
                        Console.WriteLine("result: " + result);
                    }
                }
            }

            Console.WriteLine("続行するには何かキーを押してください．．．");
            Console.ReadKey();
        }
    }
}
