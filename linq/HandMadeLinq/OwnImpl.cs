using System.Collections.Generic;
using static System.Console;

using OwnImplementation;

namespace Common {
    public static class OwnImpl {

        /// <summary>
        /// データの最初のバンドの全メンバーを表示
        /// </summary>
        public static void Select1() {
            List<Band> list = Data.AllBand;
            List<BandMan> member_list = list.xSelect(x => x.Member).xFirst();
            member_list.xForEach(x => WriteLine(x.FullName));
        }

        /// <summary>
        /// データの最初のバンドのベーシストだけを表示
        /// </summary>
        public static void Where1() {
            List<Band> list = Data.AllBand;
            List<BandMan> member_list = list.xSelect(x => x.Member).xFirst().xWhere(x => x.Role.xContains(Role.Bass)).xToList();
            member_list.xForEach(x => WriteLine(x.FullName));
        }

        /// <summary>
        /// データの全てのバンドの全メンバーを表示
        /// </summary>
        public static void SelectMany1() {
            List<Band> list = Data.AllBand;
            List<BandMan> member_list = list.xSelectMany(x => x.Member).xToList();
            member_list.xForEach(x => WriteLine(x.FullName));
        }

        /// <summary>
        /// データの全てのバンドのベーシストだけを表示
        /// </summary>
        public static void Where2() {
            List<Band> list = Data.AllBand;
            List<BandMan> member_list = list.xSelectMany(x => x.Member).xToList().xWhere(x => x.Role.xContains(Role.Bass)).xToList(); ;
            member_list.xForEach(x => WriteLine(x.FullName));
        }
    }
}
