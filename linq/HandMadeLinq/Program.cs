
namespace Common {
    public class Program {
        static void Main(string[] args) {
            if (true) {
                Traditional.Select1();
                Traditional.Where1();
                Traditional.SelectMany1();
                Traditional.Where2();
            }
            if (true) {
                UseLinq.Select1();
                UseLinq.Where1();
                UseLinq.SelectMany1();
                UseLinq.Where2();
            }
            if (true) {
                OwnImpl.Select1();
                OwnImpl.Where1();
                OwnImpl.SelectMany1();
                OwnImpl.Where2();
            }
        }
    }
}
