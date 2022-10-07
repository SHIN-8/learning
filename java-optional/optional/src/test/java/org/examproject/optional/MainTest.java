
package org.examproject.optional;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import javax.inject.Inject;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import org.examproject.optional.model.Person;
import org.examproject.optional.model.Magician;
import org.examproject.optional.service.PersonService;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.junit.Assert.*;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * java.util.Optional クラスについて
 */
@RunWith(SpringRunner.class)
public class MainTest {

    /**
     * 日付のフォーマット文字列
     */
    private static String DATE_FORMAT = "yyyy-MM-dd";

    /**
     * Person オブジェクトのリスト
     */
    private List<Person> list;

    /**
     * PersonService オブジェクトが DI で提供されます
     */
    @Inject
    private PersonService service;

    /**
     * コンストラクタ
     */
    public MainTest() {
        list = new ArrayList<Person>();
    }

    /**
     * Object 型の変数でオブジェクトを受け取るとは1
     */
    @Test
    public void to_receive_a_variable1() throws ParseException { // キャストの説明
        // Person 型のオブジェクトを作成します
        Person p = new Person(1, "テスト太郎", 1, new SimpleDateFormat(DATE_FORMAT).parse("1996-07-31"));

        // Object 型として変数に取得します
        Object o = (Object) p;

        // ここで o のクラス名は何でしょうか？

        // o 変数のクラス名を取得します ※ ? はワイルドカード
        Class<?> calzz = o.getClass();

        // Object 型 の変数で受けても o オブジェクトは Person 型なのです
        assertEquals(Person.class, calzz);
    }

    /**
     * Object 型の変数でオブジェクトを受け取るとは2
     */
    @Test
    public void to_receive_a_variable2() {
        // サービスクラスのオブジェクトから Object 型として変数に取得します
        Object o = service.getObject(1);

        // o 変数のクラス名を取得します ※ ? はワイルドカード
        Class<?> calzz = o.getClass();

        // ID:1 のオブジェクトは Person 型なので

        // Object 型 の変数で受けても o オブジェクトは Person 型なのです
        assertEquals(Person.class, calzz);
    }

    /**
     * Object 型の変数でオブジェクトを受け取るとは3
     */
    @Test
    public void to_receive_a_variable3() {
        // サービスクラスのオブジェクトから Object 型として変数に取得します
        Object o = service.getObject(3);

        // o 変数のクラス名を取得します ※ ? はワイルドカード
        Class<?> calzz = o.getClass();

        // ID:3 のオブジェクトは Magician 型です

        // Object 型 の変数で受けても o オブジェクトは Magician 型です
        assertEquals(Magician.class, calzz);
    }

    /**
     * 型キャストするとは
     */
    @Test
    public void to_cast_a_object1() {
        // Object 型として変数に取得します
        Object o = service.getObject(1);

        // Person 型に 'キャスト' します
        Person p = (Person) o;

        // Person クラスの getName メソッドが使えます
        assertEquals("テスト太郎", p.getName());
    }

    /**
     * 型キャストするとは2
     */
    @Test
    public void to_cast_a_object2() {
        // Object 型として変数に取得します
        Object o = service.getObject(3);

        // ID:3 は Magician 型なので
        // Magician 型に 'キャスト' します
        Magician m = (Magician) o;

        // Magician は Person クラスのサブクラスなので getName メソッドが使えます
        assertEquals("マジシャン三郎", m.getName());
    }

    /**
     * 型キャストするとは3
     */
    @Test
    public void to_cast_a_object3() {
        // Object 型として変数に取得します
        Object o = service.getObject(3);

        // ID:3 は Magician 型ですが
        // Person 型に 'キャスト' します
        Person p = (Person) o;

        // Person クラスの getName メソッドが使えます
        assertEquals("マジシャン三郎", p.getName());

        // このような性質を 'ポリモーフィズム' と言います
        // Person 型を継承するオブジェクトは Person 型として扱えます
        // フレームワークの内部ではこのような仕組みを多用しています

        // ※ただしキャストは間違ったコードを書くと実行時エラーになります
        // コンパイル時にエラーを検出することが出来ないので注意が必要です
    }

    /**
     * 型キャストするとは4
     */
    @Test
    public void to_cast_a_object4() {
        // もちろんはじめから Person 型として取得出来ます

        // サービスクラスのオブジェクトから Person 型として変数に取得します
        Person p = service.getPerson(3);

        // Person クラスの getName メソッドが使えます
        assertEquals("マジシャン三郎", p.getName());
    }

    /**
     * ジェネリック型 List<T> に型パラメータを使わない場合
     */
    @Test
    public void to_make_a_list1() throws ParseException {
        // リストを作成します
        List list = new ArrayList();

        // テスト用のオブジェクトをリストに追加します
        list.add(new Person(1, "テスト太郎", 1, new SimpleDateFormat(DATE_FORMAT).parse("1996-07-31")));
        list.add(new Magician(3, "マジシャン三郎", 1, new SimpleDateFormat(DATE_FORMAT).parse("2001-12-07"), 30));

        // エンティティオブジェクトの名前を表示する為には
        for (Object o : list) {
            // キャストする必要があります
            Person p = (Person) o;
            // 標準出力に表示します
            System.out.println(METHOD() + p.getName());
        }

        // 型パラメータを指定しない List では一旦 Object 型のオブジェクトとしてしか受け取れません
    }

    /**
     * ジェネリック型 List<T> に型パラメータを使う場合
     */
    @Test
    public void to_make_a_list2() throws ParseException {
        // 型パラメータを指定してリストを作成します
        List<Person> list = new ArrayList<Person>();

        // テスト用のオブジェクトをリストに追加します
        list.add(new Person(1, "テスト太郎", 1, new SimpleDateFormat(DATE_FORMAT).parse("1996-07-31")));
        list.add(new Magician(3, "マジシャン三郎", 1, new SimpleDateFormat(DATE_FORMAT).parse("2001-12-07"), 30));

        // エンティティオブジェクトの名前を表示します
        for (Person p : list) {
            // 標準出力に表示します
            System.out.println(METHOD() + p.getName());
        }

        // Person 型の List からは Person 型のオブジェクトとして利用出来ます
        // 危険なキャストも使わないので安全なコードになります
    }

    /**
     * 他のオブジェクトから取得したオブジェクトをチェックなしで使用したケース
     */
    @Test(expected = NullPointerException.class)
    public void to_use_a_optional1() {
        // サービスオブジェクトから ID:7 を取得しようとしています
        Person p = service.getPerson(7);

        // 注意 ID:7 のオブジェクトは存在しません

        // エンティティオブジェクトの名前を表示してみます
        System.out.println(METHOD() + p.getName());

        // java.lang.NullPointerException が発生します！
    }

    /**
     * オブジェクトが null でないかチェックしてから使用したケース
     */
    @Test
    public void to_use_a_optional2() {
        // サービスオブジェクトから ID:7 を取得しようとしています
        Person p = service.getPerson(7);

        // オブジェクトが null じゃないか確認します
        if (p != null) {
            // p が null なのでこの処理は実行されません
           System.out.println(METHOD() + p.getName());
        }
    }

    /**
     * オブジェクトを Optional 型でラップしてから使用したケース
     */
    @Test
    public void to_use_a_optional3() {
        // Person 型の型パラメータを設定した Optional 型のオブジェクトを作成します
        Optional<Person> pOpt = Optional.ofNullable(service.getPerson(7));

        // オブジェクトが null ではない場合の記述方法を書かざるを得なくなります
        pOpt.ifPresent(
            p -> System.out.println(METHOD() + p.getName())
        );
    }

    /**
     * オブジェクトが null でないかチェックしてから使用したケース ＋ 値がない場合の処理
     */
    @Test
    public void to_use_a_optional4() {
        // サービスオブジェクトから ID:7 を取得しようとしています
        Person p = service.getPerson(7);

        // オブジェクトが null じゃないか確認します
        if (p != null) {
            // p が null なのでこの処理は実行されません
           System.out.println(METHOD() + p.getName());
        } else {
           System.out.println(METHOD() + "ないです");
        }
    }

    /**
     * オブジェクトを Optional 型でラップしてから使用したケース ＋ 値がない場合の処理
     */
    @Test
    public void to_use_a_optional5() throws ParseException {
        // Person 型の型パラメータを設定した Optional 型のオブジェクトを作成します
        Optional<Person> pOpt = Optional.ofNullable(service.getPerson(5));

        // デフォルトの Person オブジェクトを作成しておく
        // "ないです" を表示させたいだけで他の値に意味はありません
        Person defaultP = new Person(0, "ないです", 0, new SimpleDateFormat(DATE_FORMAT).parse("1970-01-01"));

        // Optional 型のオブジェクトから Person 型のオブジェクトを取得
        Person p = pOpt.orElse(
            // 値がなければこちらが取得される
            defaultP
        );

        // エンティティオブジェクトの名前を表示
        System.out.println(METHOD() + p.getName());

        // この様に "オブジェクトをが null で返ってくる場合" を想定したコードを書かざるを得ません
        // ※ Java 8 には ifPresentOrElse が存在しないのでこのような無様な書き方になりました
    }

    /**
     * 実際には手元のコードでオブジェクトを Optional 型でラップしてから使用したケースはまれだと思います
     */
    @Test
    public void to_use_a_optional6() throws ParseException {
        // サービスオブジェクトから Optional 型のオブジェクトを取得します
        Optional<Person> pOpt = service.getOptional(7);

        // ※ Java 8 には ifPresentOrElse が存在しないのでベタに書きます

        // 事前にわざわざ値の存在をチェックをする
        if (pOpt.isPresent()) {
            // Optional 型のオブジェクトから Person オブジェクトを取得
            // ※ null ではないことは保障されている
            Person p = pOpt.get();
            // エンティティオブジェクトの名前を表示
            System.out.println(METHOD() + p.getName());
        } else {
            System.out.println(METHOD() + "ないです");
        }

        // 実際には if, else を使った方が分かりやすいコードになることも多いです
    }

    /**
     * しかし Optional 型を返して来る外部オブジェクトも信用出来ないケースがあります
     */
    @Test(expected = NullPointerException.class)
    public void to_use_a_optional7() throws ParseException {
        // サービスオブジェクトから Optional 型のオブジェクトを取得します
        Optional<Person> pOpt = service.getOptionalAsNull(7);

        // そもそもこの pOpt が null かどうかチェックしないと意味がないのでは？

        // 上のコードと同じですが Optional が null の可能性はあります
        if (pOpt.isPresent()) {
            Person p = pOpt.get();
            System.out.println(METHOD() + p.getName());
        } else {
            System.out.println(METHOD() + "ないです");
        }

        // 当然 java.lang.NullPointerException が発生します！
    }

    /**
     * 他のオブジェクトからリストを取得してループするケース1
     */
    @Test
    public void to_loop_a_list1() {
        // サービスオブジェクトからエンティティオブジェクトのリストを取得します
        List<Person> list = service.getAll();

        // エンティティオブジェクトの名前を表示します
        for (Person p : list) {
            // 標準出力に表示します
            System.out.println(METHOD() + p.getName());
        }
    }

    /**
     * 他のオブジェクトからリストを取得してループするケース2
     */
    @Test
    public void to_loop_a_list2() {
        // サービスオブジェクトからエンティティオブジェクトのリストを取得します
        List<Person> list = service.getAllAsEmpty();

        // エンティティオブジェクトの名前を表示します
        for (Person p : list) {
            // 標準出力に表示します
            System.out.println(METHOD() + p.getName());
        }

        // 空のリストだとループ処理が走らないのでエラーは起きません
    }

    /**
     * 他のオブジェクトからリストを取得してループするケース3
     */
    @Test(expected = NullPointerException.class)
    public void to_loop_a_list3() {
        // サービスオブジェクトからエンティティオブジェクトのリストを取得します
        List<Person> list = service.getAllAsNull();

        // エンティティオブジェクトの名前を表示します
        for (Person p : list) {
            // 標準出力に表示します
            System.out.println(METHOD() + p.getName());
        }

        // java.lang.NullPointerException が発生します！
    }

    /**
     * 他のオブジェクトからリストを取得してループするケース4
     */
    @Test
    public void to_loop_a_list4() {
        // サービスオブジェクトからエンティティオブジェクトのリストを取得します
        Optional<List<Person>> listOpt = service.getAllAsOptional();

        // エンティティオブジェクトの名前を表示します
        listOpt.ifPresent(list -> list.stream().forEach(
            // 標準出力に表示します
            p -> System.out.println(METHOD() + p.getName())
        ));
    }

    /**
     * 他のオブジェクトからリストを取得してループするケース5
     */
    @Test
    public void to_loop_a_list5() {
        // サービスオブジェクトからエンティティオブジェクトのリストを取得します
        Optional<List<Person>> listOpt = service.getAllAsNullOptional();

        // エンティティオブジェクトの名前を表示します
        listOpt.ifPresent(list -> list.stream().forEach(
            // 標準出力に表示します
            p -> System.out.println(METHOD() + p.getName())
        ));

        // Optional 型に null がラップされていてもエラーになりません
    }

    /**
     * 他のオブジェクトからリストを取得してループするケース6
     */
    @Test(expected = NullPointerException.class)
    public void to_loop_a_list6() {
        // Optional 型のオブジェクト自体が null の場合
        Optional<List<Person>> listOpt = null;

        // エンティティオブジェクトの名前を表示します
        listOpt.ifPresent(list -> list.stream().forEach(
            // 標準出力に表示します
            p -> System.out.println(METHOD() + p.getName())
        ));

        // 当然 java.lang.NullPointerException が発生します！
    }

    /**
     * おまけ： Stream API map メソッドの使用例
     * Select 相当
     */
    @Test
    public void to_use_map_func() {
        // サービスオブジェクトからエンティティオブジェクトのリストを取得します
        List<Person> list = service.getAll();

        // エンティティオブジェクトの名前を抽出してリスト化します
        List<String> nameList = list.stream().map(
            p -> p.getName()
        ).collect(Collectors.toList());

        // 名前を表示します
        for (String s : nameList) {
            // 標準出力に表示します
            System.out.println(METHOD() + s);
        }
    }

    /**
     * おまけ： Stream API filter メソッドの使用例
     * Where 相当
     */
    @Test
    public void to_use_filter_func() throws ParseException {
        // サービスオブジェクトからエンティティオブジェクトのリストを取得します
        List<Person> list = service.getAll();

        // 21世紀生まれのエンティティオブジェクトを抽出してリスト化します
        Date millennial = new SimpleDateFormat(DATE_FORMAT).parse("1999-12-31");
        List<Person> millennialList = list.stream().filter(
            p -> p.getBorn().after(millennial)
        ).collect(Collectors.toList());

        // 名前を表示します
        for (Person p : millennialList) {
            // 標準出力に表示します
            System.out.println(METHOD() + p.getName());
        }
    }

    /**
     * テストの前処理
     */
    @Before
    public void setUp() throws ParseException {
        /**
         * テスト用の Person or サブクラスのオブジェクトのリストを作成します
         */
        list.add(new Person(1, "テスト太郎", 1, new SimpleDateFormat(DATE_FORMAT).parse("1996-07-31")));
        list.add(new Person(2, "テスト次郎", 1, new SimpleDateFormat(DATE_FORMAT).parse("1999-03-04")));
        list.add(new Magician(3, "マジシャン三郎", 1, new SimpleDateFormat(DATE_FORMAT).parse("2001-12-07"), 30));
        list.add(new Magician(4, "エレガント弥生", 2, new SimpleDateFormat(DATE_FORMAT).parse("2009-04-01"), 50));

        /**
         * サービスオブジェクトに Person オブジェクトのリストを設定します
         */
        service.setPersonList(list);
    }

    /**
     * Spring TEST の初期設定
     */
    @Configuration
    @ComponentScan("org.examproject.optional")
    static class Config {
    }

    /**
     * メソッド名表示用のヘルパ関数
     * @return メソッド名の文字列
     */
    private static String METHOD() {
        // 呼び出し元のメソッド名を取得します
        return Thread.currentThread().getStackTrace()[2].getMethodName() + "(): ";
    }
}
