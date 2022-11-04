using System;
using System.Collections.Generic;
using System.Linq;
using Microsoft.VisualBasic;
using Microsoft.VisualStudio.TestTools.UnitTesting;
using static Microsoft.VisualStudio.TestTools.UnitTesting.Assert;
using static Tests.Utils;

namespace Tests {
    [TestClass()]
    public class ExampleTests {
#nullable enable
        [TestMethod()]
        public void Test1() {
            var hoge = "ほげ";
            AreEqual("ほげ", hoge);
        }

        [TestMethod()]
        public void Test2() {
            Func<string> hoge = () => "ほげ";
            AreEqual("ほげ", hoge());
            //Console.WriteLine(greet());
        }

        /// <summary>
        /// 関数リテラルの構文: 無名関数を変数に代入しています
        /// </summary>
        [TestMethod()]
        public void Test3() {
            Func<int, int, int> add = (x, y) => x + y;
            AreEqual(5, add(2, 3));
        }

        /// <summary>
        /// C# の LINQ ライブラリで引数にラムダ式を渡す例
        /// </summary>
        [TestMethod()]
        public void Test4() {
            List<int> src = new() {0, 1, 2, 3, 4, 5};
            List<int> exp = new() {0, 2, 4, 6, 8, 10};
            src = src.Select(x => x * 2).ToList();
            CollectionAssert.AreEqual(exp, src);
        }

        /// <summary>
        /// 変数に代入できるラムダ式の例
        /// </summary>
        [TestMethod()]
        public void Test5() {
            Func<int, int> doubleValue = x => x * 2;
            AreEqual(4, doubleValue(2));
        }

        /// <summary>
        /// 関数の引数に渡せるラムダ式の例
        /// </summary>
        [TestMethod()]
        public void Test6() {
            AreEqual(4, doValueTwo(x => x * 2));
        }

        /// <summary>
        /// 関数の戻り値にできるラムダ式の例
        /// </summary>
        [TestMethod()]
        public void Test7() {
            AreEqual(4, toDoubleValue(2)());
        }

        /// <summary>
        /// 関数の戻り値にできるラムダ式の例②
        /// </summary>
        [TestMethod()]
        public void Test8() {
            AreEqual(4, toDoubleValue2()(2));
        }

        /// <summary>
        /// 式形式のラムダ
        /// </summary>
        [TestMethod()]
        public void Test9() {
            Func<int, int, int> add = (x, y) => x + y;
            AreEqual(5, add(2, 3));
        }

        /// <summary>
        /// ステートメント形式のラムダ
        /// </summary>
        [TestMethod()]
        public void Test10() {
            Func<int, int, int> add = (x, y) => {
                return x + y;
            };
            AreEqual(5, add(2, 3));
        }

        /// <summary>
        /// LINQ でのラムダ式使用例
        /// </summary>
        [TestMethod()]
        public void Test11() {
            List<string> src = new() {"りんご", "みかん", "バナナ", "メロン", "ぶどう"};
            List<string> exp = new() {"リンゴ", "ミカン", "ブドウ"};
            src = src.Where(x => isHiragana(x)).Select(x => toKatakana(x)).ToList();
            CollectionAssert.AreEqual(exp, src);
        }

        /// <summary>
        /// LINQ でのラムダ式使用例②
        /// </summary>
        [TestMethod()]
        public void Test12() {
            List<Fruit> src = new() {
                new Fruit("りんご", 100),　new Fruit("みかん", 160),　new Fruit("メロン", 350),　new Fruit("ぶどう", 250)
            };
            List<string> exp = new() {"ミカン", "ブドウ"};
            var ret = src.Where(x => isHiragana(x.Name) && x.Price > 150).Select(x => toKatakana(x.Name)).ToList();
            CollectionAssert.AreEqual(exp, ret);
        }

        /// <summary>
        /// 従来型の記述方式
        /// </summary>
        [TestMethod()]
        public void Test13() {
            List<Fruit> src = new() {
                new Fruit("りんご", 100), new Fruit("みかん", 160), new Fruit("メロン", 350), new Fruit("ぶどう", 250)
            };
            List<string> exp = new() {"ミカン", "ブドウ"};
            List<string> ret = new();
            foreach (var fruit in src) {
                if (isHiragana(fruit.Name) && fruit.Price > 150) {
                    ret.Add(toKatakana(fruit.Name));
                }
            }
            CollectionAssert.AreEqual(exp, ret);
        }

        /// <summary>
        /// IEnumerable でのラムダ式使用例
        /// </summary>
        [TestMethod()]
        public void Test14() {
            List<int> ret = Enumerable.Range(0, 10).Where(x => x % 2 == 0).Select(x => x).ToList();
            List<int> exp = new() {0, 2, 4, 6, 8};
            CollectionAssert.AreEqual(exp, ret);
        }

        /// <summary>
        /// 従来型の記述方式
        /// </summary>
        [TestMethod()]
        public void Test15() {
            List<int> ret = new();
            for (var i = 0; i < 10; i++) {
                if (i % 2 == 0) {
                    ret.Add(i);
                }
            }
            List<int> exp = new() {0, 2, 4, 6, 8};
            CollectionAssert.AreEqual(exp, ret);
        }

        /// <summary>
        /// テンプレート
        /// </summary>
        [TestMethod()]
        public void TestN() {
        }

        /// <summary>
        /// 関数の引数に渡せるラムダ式の例
        /// </summary>
        int doValueTwo(Func<int, int> func) {
            return func(2);
        }

        /// <summary>
        /// 関数の戻り値にできるラムダ式の例
        /// </summary>
        Func<int> toDoubleValue(int value) {
            return () => value * 2;
        }

        /// <summary>
        /// 関数の戻り値にできるラムダ式の例②
        /// </summary>
        Func<int, int> toDoubleValue2() {
            return value => value * 2;
        }

        ///////////////////////////////////////////////////////////////////////////////////////////////

        [TestMethod()]
        public void Test2_1() {
            AreEqual(true, isHiragana("りんご"));
            AreEqual(false, isHiragana("リンゴ"));
            AreEqual(true, isHiragana("みかん"));
            AreEqual(false, isHiragana("ミカン"));
            AreEqual(true, isHiragana("ばなな"));
            AreEqual(false, isHiragana("バナナ"));
            AreEqual(true, isHiragana("めろん"));
            AreEqual(false, isHiragana("メロン"));
            AreEqual(true, isHiragana("ぶどう"));
            AreEqual(false, isHiragana("ブドウ"));
        }

        [TestMethod()]
        public void Test2_2() {
            AreEqual("リンゴ", toKatakana("りんご"));
            AreEqual("ミカン", toKatakana("みかん"));
            AreEqual("バナナ", toKatakana("ばなな"));
            AreEqual("メロン", toKatakana("めろん"));
            AreEqual("ブドウ", toKatakana("ぶどう"));
        }
    }

    public record Fruit(string Name, int Price);

    public class Utils {
        /// <summary>
        /// 文字列がひらがなかどうか
        /// </summary>
        public static bool isHiragana(string word) {
            if (word is null) {
                throw new ArgumentNullException("need a letter.");
            }
            foreach (char letter in word) {
                if (!isHiragana(letter)) {
                    return false;
                }
            }
            return true;
        }
        /// <summary>
        /// 文字がひらがなかどうか
        /// </summary>
        public static bool isHiragana(char letter) {
            return (
                '\u3041' <= letter && letter <= '\u309F') || 
                letter == '\u30FC' || letter == '\u30A0';
        }
        /// <summary>
        /// ひらがなをカタカナに変換
        /// </summary>
        public static string toKatakana(string word) {
            System.Text.Encoding.RegisterProvider(System.Text.CodePagesEncodingProvider.Instance);
            return Strings.StrConv(word, VbStrConv.Katakana, 0x411);
        }
    }
}
