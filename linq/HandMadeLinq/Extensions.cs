using System;
using System.Collections.Generic;

namespace OwnImplementation {
    public static class Extensions {

        /// <summary>
        /// Select の簡易実装
        /// </summary>
        public static IEnumerable<TResult> xSelect<TSource, TResult>(this IEnumerable<TSource> source, Func<TSource, TResult> selector) {
            foreach (TSource item in source) {
                yield return selector(item);
            }
        }

        /// <summary>
        /// SelectMany の簡易実装
        /// </summary>
        public static IEnumerable<TResult> xSelectMany<TSource, TResult>(this IEnumerable<TSource> source, Func<TSource, IEnumerable<TResult>> selector) {
            foreach (TSource item in source) {
                foreach (TResult result in selector(item)) {
                    yield return result;
                }
            }
        }

        /// <summary>
        /// Where の簡易実装
        /// </summary>
        public static IEnumerable<TSource> xWhere<TSource>(this IEnumerable<TSource> source, Func<TSource, bool> predicate) {
            foreach (TSource item in source) {
                if (predicate(item)) {
                    yield return item;
                }
            }
        }

        /// <summary>
        /// First の簡易実装
        /// </summary>
        public static TSource xFirst<TSource>(this IEnumerable<TSource> source) {
            foreach (TSource item in source) {
                return item;
            }
            return default(TSource);
        }

        /// <summary>
        /// ForEach の簡易実装
        /// </summary>
        public static void xForEach<TSource>(this IEnumerable<TSource> source, Action<TSource> action) {
            foreach (TSource item in source) {
                action(item);
            }
        }

        /// <summary>
        /// ToList の簡易実装
        /// </summary>
        public static List<TSource> xToList<TSource>(this IEnumerable<TSource> source) {
            return new List<TSource>(source);
        }

        /// <summary>
        /// Contains の簡易実装
        /// </summary>
        public static bool xContains<TSource>(this IEnumerable<TSource> source, TSource value) {
            foreach (TSource item in source) {
                if (EqualityComparer<TSource>.Default.Equals(item, value)) {
                    return true;
                }
            }
            return false;
        }
    }
}
