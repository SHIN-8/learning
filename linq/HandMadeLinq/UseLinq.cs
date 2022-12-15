using System.Collections.Generic;
using System.Linq;
using static System.Console;

namespace Common {
    public static class UseLinq {

        /// <summary>
        /// データの最初のバンドの全メンバーを表示
        /// </summary>
        public static void Select1() {
            List<Band> list = Data.AllBand;
            List<BandMan> member_list = list.Select(x => x.Member).First();
            member_list.ForEach(x => WriteLine(x.FullName));
        }

        /// <summary>
        /// データの最初のバンドのベーシストだけを表示
        /// </summary>
        public static void Where1() {
            List<Band> list = Data.AllBand;
            List<BandMan> member_list = list.Select(x => x.Member).First().Where(x => x.Role.Contains(Role.Bass)).ToList();
            member_list.ForEach(x => WriteLine(x.FullName));
        }

        /// <summary>
        /// データの全てのバンドの全メンバーを表示
        /// </summary>
        public static void SelectMany1() {
            List<Band> list = Data.AllBand;
            List<BandMan> member_list = list.SelectMany(x => x.Member).ToList();
            member_list.ForEach(x => WriteLine(x.FullName));
        }

        /// <summary>
        /// データの全てのバンドのベーシストだけを表示
        /// </summary>
        public static void Where2() {
            List<Band> list = Data.AllBand;
            List<BandMan> member_list = list.SelectMany(x => x.Member).ToList().Where(x => x.Role.Contains(Role.Bass)).ToList(); ;
            member_list.ForEach(x => WriteLine(x.FullName));
        }
    }
}
