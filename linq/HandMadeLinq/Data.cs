using System;
using System.Collections.Generic;
using static System.DateTime;

using static Common.Gender;
using static Common.Nation;
using static Common.Role;

namespace Common {
    public static class Data {

        static List<Band> _band_list;

        /// <summary>
        /// 全てのバンドを取得します
        /// </summary>
        public static List<Band> AllBand {
            get {
                if (_band_list is null) { _band_list = new(); create(); } 
                return _band_list;
            }
        }

        /// <summary>
        /// バンドのデータを生成します
        /// </summary>
        static void create() {
            _band_list.Add(item: new() {
                Name = "The Beatles", Nation = UnitedKingdom,
                Period = new() { Begin = Parse("1960/01/01"), End = Parse("1970/12/31") }, Term = 1,
                Member = new() {
                    new() { FirstName = "John", LastName = "Lennon", Gender = Male, Born = Parse("1940/10/09"), Role = new[] { Vocal, Guitar } },
                    new() { FirstName = "Paul", LastName = "McCartney", Gender = Male, Born = Parse("1942/06/18"), Role = new[] { Vocal, Bass } },
                    new() { FirstName = "George", LastName = "Harrison", Gender = Male, Born = Parse("1943/02/25"), Role = new[] { Vocal, Guitar } },
                    new() { FirstName = "Ringo", LastName = "Starr", Gender = Male, Born = Parse("1940/07/07"), Role = new[] { Vocal, Drums } }
                },
            });
            _band_list.Add(item: new() {
                Name = "The Rolling Stones", Nation = UnitedKingdom,
                Period = new() { Begin = Parse("1962/01/01"), End = Parse("1969/12/31") }, Term = 1,
                Member = new() {
                    new() { FirstName = "Brian", LastName = "Jones", Gender = Male, Born = Parse("1942/02/28"), Role = new[] { Guitar } },
                    new() { FirstName = "Mick", LastName = "Jagger", Gender = Male, Born = Parse("1943/07/26"), Role = new[] { Vocal } },
                    new() { FirstName = "Keith", LastName = "Richards", Gender = Male, Born = Parse("1943/12/18"), Role = new[] { Guitar } },
                    new() { FirstName = "Bill", LastName = "Wyman", Gender = Male, Born = Parse("1936/10/24"), Role = new[] { Bass } },
                    new() { FirstName = "Charlie", LastName = "Watts", Gender = Male, Born = Parse("1941/06/02"), Role = new[] { Drums } }
                },
            });
            _band_list.Add(item: new() {
                Name = "The Kinks", Nation = UnitedKingdom,
                Period = new() { Begin = Parse("1964/01/01"), End = Parse("1969/12/31") }, Term = 1,
                Member = new() {
                    new() { FirstName = "Ray", LastName = "Davies", Gender = Male, Born = Parse("1944/06/21"), Role = new[] { Vocal, Guitar } },
                    new() { FirstName = "Dave", LastName = "Davies", Gender = Male, Born = Parse("1947/02/03"), Role = new[] { Vocal, Guitar } },
                    new() { FirstName = "Pete", LastName = "Quaife", Gender = Male, Born = Parse("1943/12/31"), Role = new[] { Bass } },
                    new() { FirstName = "Mick", LastName = "Avory", Gender = Male, Born = Parse("1944/02/15"), Role = new[] { Drums } }
                },
            });
            _band_list.Add(item: new() {
                Name = "The Who", Nation = UnitedKingdom,
                Period = new() { Begin = Parse("1964/01/01"), End = Parse("1978/12/31") }, Term = 1,
                Member = new() {
                    new() { FirstName = "Roger", LastName = "Daltrey", Gender = Male, Born = Parse("1944/03/01"), Role = new[] { Vocal } },
                    new() { FirstName = "Pete", LastName = "Townshend", Gender = Male, Born = Parse("1945/05/19"), Role = new[] { Vocal, Guitar } },
                    new() { FirstName = "John", LastName = "Entwistle", Gender = Male, Born = Parse("1944/10/09"), Role = new[] { Vocal, Bass } },
                    new() { FirstName = "Keith", LastName = "Moon", Gender = Male, Born = Parse("1946/08/23"), Role = new[] { Drums } }
                },
            });
        }
    }

    /// <summary>
    /// パーソン
    /// </summary>
    public record Person {
        public string FirstName { get; set; }
        public string LastName { get; set; }
        public Gender Gender { get; set; }
        public DateTime Born { get; set; }
        public string FullName { get => $"{FirstName} {LastName}"; }
    }

    /// <summary>
    /// バンドメンバー
    /// </summary>
    public record BandMan : Person {
        public Role[] Role { get; set; }
    }

    /// <summary>
    /// バンド
    /// </summary>
    public record Band {
        public string Name { get; set; }
        public Nation Nation { get; set; }
        public Period Period { get; set; }
        public int Term { get; set; }
        public List<BandMan> Member { get; set; }
    }

    /// <summary>
    /// 期間
    /// </summary>
    public record Period {
        public DateTime Begin { get; set; }
        public DateTime End { get; set; }
    }

    /// <summary>
    /// 性別
    /// </summary>
    public enum Gender {
        Male,
        Femail
    }

    /// <summary>
    /// 役割
    /// </summary>
    public enum Role {
        Vocal,
        Chorus,
        Guitar,
        Bass,
        Keyboard,
        Drums,
        Percussion
    }

    /// <summary>
    /// 国籍
    /// </summary>
    public enum Nation {
        UnitedKingdom,
        UnitedStates
    }
}
