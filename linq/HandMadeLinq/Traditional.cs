using System.Collections.Generic;
using static System.Console;

namespace Common {
    public static class Traditional {

        /// <summary>
        /// データの最初のバンドの全メンバーを表示
        /// </summary>
        public static void Select1() {
            List<Band> list = Data.AllBand;
            foreach (Band band in list) {
                foreach (BandMan band_man in band.Member) {
                    WriteLine(band_man.FullName);
                }
                break;
            }
        }

        /// <summary>
        /// データの最初のバンドのベーシストだけを表示
        /// </summary>
        public static void Where1() {
            List<Band> list = Data.AllBand;
            foreach (Band band in list) {
                foreach (BandMan band_man in band.Member) {
                    foreach (Role role in band_man.Role) {
                        if (role.ToString().Contains(Role.Bass.ToString())) {
                            WriteLine(band_man.FullName);
                        }
                    }
                }
                break;
            }
        }

        /// <summary>
        /// データの全てのバンドの全メンバーを表示
        /// </summary>
        public static void SelectMany1() {
            List<Band> list = Data.AllBand;
            foreach (Band band in list) {
                foreach (BandMan band_man in band.Member) {
                    WriteLine(band_man.FullName);
                }
            }
        }

        /// <summary>
        /// データの全てのバンドのベーシストだけを表示
        /// </summary>
        public static void Where2() {
            List<Band> list = Data.AllBand;
            foreach (Band band in list) {
                foreach (BandMan band_man in band.Member) {
                    foreach (Role role in band_man.Role) {
                        if (role.ToString().Contains(Role.Bass.ToString())) {
                            WriteLine(band_man.FullName);
                        }
                    }
                }
            }
        }
    }
}
