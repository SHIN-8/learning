# JPA
JavaEEという仕様に含まれるJPAについて発表します  
JPAの説明の後、ハンズオン形式で実際の機能に触れていきます  

### 準備
発表前に以下を終わらせておいてください  

* このリポジトリのclone
* jdk14のインストール
* MySQL/phpMyAdminコンテナの起動
* 外部ライブラリのインストール

また、ポート3306,8080,8081番を空けておいてください  
以下のような環境が出来上がります

* 3306->mysql
* 8080->phpMyAdmin
* 8081->Tomcat  

**MySQLコンテナの起動**

このプロジェクトのdockerディレクトリ内で以下のコマンドを実行します  

```
docker-compose up -d
```

dockerとphpMyAdminが起動したことを確認してください  

```
jpa-demo\docker>docker ps

CONTAINER ID        IMAGE                   COMMAND                  CREATED             STATUS              PORTS                               NAMES
c3d184d4f225        phpmyadmin/phpmyadmin   "/docker-entrypoint.…"   11 hours ago        Up About a minute   0.0.0.0:8080->80/tcp                test_phpmyadmin
7b6739c21d35        mysql:5.7               "docker-entrypoint.s…"   11 hours ago        Up About a minute   0.0.0.0:3306->3306/tcp, 33060/tcp   mysql_host
```

**外部ライブラリのインストール**

このリポジトリのトップディレクトリにて以下のコマンドを実行してください  

```
//Mac
//ファイルに実行権限が無ければ追加してください
./gradlew build

//Windows
gradlew.bat build
```

### 本日発表すること

* JavaEEとは
* JPAとは
* JPAのハンズオン
    * 単純なエンティティの作成
    * EntityManager
    * リレーション
    * カスケードタイプ
    * Fetchタイプ
    * JPQL
        * Netbeansだと補完してくれる
        * 名前付きクエリ
    * CriteriaAPI

### JavaEEとは
Javaは当初、デスクトップやブラウザ上で動作するアプリケーションで利用されていました  
そこでサーバーサイドで開発するためのフレームワークJ2EE 1.2を作成しました  
J2EE 1.2には、JSPやServletが含まれていたそうです  
それから1.3、1.4とリリースが進み、その次のバージョンアップではJava Platform Enterprise Edition(Java EE)と改名されたそうです  
JavaEEは企業システムの開発に必要な機能を一つにまとめた仕様の総称です  
例えば以下の機能に関する仕様が含まれています  

**Java Persistence API(JPA)**

ORマッパー  
今回の勉強会のメインとなります

**Java Message Service(JMS)**

外部のメッセージングサービスと非同期で通信が行える  

**Servlet**

Tomcatやjettyといったサーブレットコンテナ上で動作し、Httpリクエストに対してプログラムを実行しレスポンスを返します  
JavaEEにはJSFというフレームワークがありますが、裏ではServletが動いています  

### JPAとは
Java EEに含まれているORMです  
Javaのオブジェクトにアノテーションを付けることで、DBの値をマッピングしてくれます  
JPAにはキャッシュ機能が含まれており、DBへの負荷を減らしてくれます  

JPAには以下の主要な構成要素があります  

**エンティティ**

```java
@Entity(name = "customers")
class Customer {
    @Id
    var id: Int? = null

    var isCompany: Boolean = false

    var company: String? = null

    @OneToMany(mappedBy = "customer")
    var addressList: List<Address> = listOf()
}
```

**エンティティマネージャ**

```kotlin
@RestController
class HomeController {
    @PersistenceContext(unitName = "default")
    lateinit var em: EntityManager

    @GetMapping("/")
    @Transactional
    fun getIndex(): List<Customer> {
        val query = em.createQuery("SELECT c FROM com.kingprinters.jpademo.entities.Customer c left join fetch c.orders o", Customer::class.java)

        val result = query.resultList
        println(result)
        return result
    }
}
```

**クエリ**

```java
@NamedQuery(name = "Customer.findAll", query = "SELECT c FROM com.kingprinters.jpademo.entities.Customer c left join fetch c.orders WHERE c.id=:id")
```

**永続化ユニット**

```xml
<?xml version="1.0" encoding="UTF-8"?>
<persistence version="2.1"
             xmlns="http://xmlns.jcp.org/xml/ns/persistence" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
             xsi:schemaLocation="http://xmlns.jcp.org/xml/ns/persistence http://xmlns.jcp.org/xml/ns/persistence/persistence_2_1.xsd">
    <persistence-unit name="default_pu" transaction-type="RESOURCE_LOCAL">
        <properties>
            <property name="javax.persistence.jdbc.driver" value="org.apache.derby.jdbc.EmbeddedDriver" />
            <property name="javax.persistence.jdbc.url" value="jdbc:derby:mydb;create=true" />
            <property name="javax.persistence.jdbc.user" value="root" />
            <property name="javax.persistence.jdbc.password" value="rootpw" />
        </properties>
    </persistence-unit>
</persistence>
```

JPAは単なる仕様であり、インタフェースが定義されているだけで有名な以下二つの実装があります  

* eclipse link
* hibernate

基本的にJPA標準のAPIを利用していれば両者は置き換えが可能だそうです  
また、それぞれ独自の追加機能も実装されています  

**マルチテナント(EclipseLink/Hibernate)**

```
HashMap properties = new HashMap();
properties.put("tenant.id", "707");
...     
EntityManager em = Persistence.createEntityManagerFactory("multi-tenant", 
      properties).createEntityManager();
```

**データベース変更通知のハンドリング(EclipseLink)**

```xml
<?xml version="1.0" encoding="UTF-8"?>
<persistence xmlns="http://java.sun.com/xml/ns/persistence"
                xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
                xsi:schemaLocation="http://java.sun.com/xml/ns/persistence persistence_2_0.xsd"
                version="2.0">
    <persistence-unit name="acme" transaction-type="RESOURCE_LOCAL">
        <provider>org.eclipse.persistence.jpa.PersistenceProvider</provider>
        <class>model.Order</class>
        <class>model.OrderLine</class>
        <class>model.Customer</class>
        <exclude-unlisted-classes>false</exclude-unlisted-classes>
        <properties>
            <property name="eclipselink.cache.database-event-listener" value="DCN"/>
        </properties>
    </persistence-unit>
</persistence>
```

**自然キーのサポート(Hibernate)**

```java
@Entity
public class Book {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = “id”, updatable = false, nullable = false)
  private Long id;

  @NaturalId
  private String isbn;
}
```

**主キーによる複数エンティティ取得のサポート(Hibernate)**

```java
Session session = em.unwrap(Session.class);
MultiIdentifierLoadAccess<PersonEntity> multiLoadAccess = session.byMultipleIds(PersonEntity.class);
List<PersonEntity> persons = multiLoadAccess.multiLoad(1L, 2L, 3L);
```


**作成日時・更新日時の管理(Hibernate)**

```java
@Entity
public class MyEntity {
 
    @Id
    @GeneratedValue
    private Long id;
 
    @CreationTimestamp
    private LocalDateTime createDateTime;
 
    @UpdateTimestamp
    private LocalDateTime updateDateTime;
}
```

**関連付けられていないテーブルの結合(Hibernate)**

```java
EntityManager em = emf.createEntityManager();
em.getTransaction().begin();
	
List<Object[]> results = em.createQuery("SELECT p.firstName, p.lastName, n.phoneNumber FROM Person p LEFT JOIN PhoneBookEntry n ON p.firstName = n.firstName AND p.lastName = n.lastName").getResultList();
```

### JPAハンズオン
ここからはハンズオン形式でJPAの標準的な機能を使ってみます  
ユーザ情報・注文情報といった一般的なデータを扱います  
事前に作成頂いたMySQLコンテナを利用します  

※ファイルを置くディレクトリは記載していないのですが、パッケージと一致させています  

**エンティティの作成**

まずはCustomerとOrderのエンティティを作成します  

```kotlin
package com.kingprinters.jpademo.entities

import javax.persistence.*

@Entity(name = "customers")
class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Int? = null

    var isCompany: Boolean? = null
    var company: String? = null
}
```

```kotlin
package com.kingprinters.jpademo.entities

import java.time.LocalDateTime
import javax.persistence.*

@Entity(name = "orders")
class Order(
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Int? = null

    var price: Int? = null
    var deliveryDate: LocalDateTime? = null
    var product: String? = null
}
```

次に、phpMyAdminからkind_db内のテーブルを確認してください  
まだ何のテーブルも含まれていないことが確認できるかと思います  
続けて以下のコマンドでSpringBootを起動します  

```
gradlew bootRun
```

このコマンドによってTomcatサーバー上でSpringBootが実行されます  
http://localhost:8082にアクセスするとtestが返ってくるはずです  
package com.kingprinters.jpademo.controllers.HomeControllerのgetIndexメソッドが実行された為です  
以降はこのメソッドからデータベースの登録などをして動作確認をします  
再度データベースを確認すると、customersテーブルとordersテーブルが作成されたことを確認できるかと思います  
ここで、src/main/resources/application.propertiesを開いてみます  
このファイルはSpringBootの設定ファイルで、データベースの設定やアプリケーションサーバーのポート番号が書かれてあります  
この中にhibernateのDDLに関する設定もあります  

```
spring.jpa.hibernate.ddl-auto=create
```

この設定により、SpringBoot起動時にエンティティの定義に基づいて自動でDDLを実行してくれます  
ddl-autoは以下の値を取ります  

* none->DDLを実行しない
* validate->DDLを実行しない
* update->エンティティに対応するテーブルがなければ作成する
* create->エンティティに更新があればテーブルを再作成
* create-drop->終了後にテーブルを削除

これによりcustomersテーブルが作成されました  

では次に一般的なリレーションを作成します  

**1対多**

CustomerとOrderのリレーションを一対多で関連させます  
Customerに以下のフィールドを追加します

```kotlin
@OneToMany(mappedBy = "customer")
var orders: MutableList<Order> = mutableListOf()
```

Orderに以下のフィールドを追加します

```kotlin
@ManyToOne
var customer: Customer? = null
```

SpringBootを起動します  

```
gradlew bootRun
```

ordersテーブルにcustomer_idが作成されたことが確認できます  
外部キー制約もちゃんとついています  

**多対多**

@ManyToManyというアノテーションを使用すること以外ほとんど変わりません  
中間テーブルが自動で生成されます  

**Entityの保存**

これから先ほど作成したエンティティを使用して、DBへの書き込みと読み込みをしてみます  
JPAではDBへのアクセスにEntityManagerを利用します  
まずは使用例を見てみましょう  
HomeControllerにフィールドの追加とメソッドの変更を行います  

```kotlin
package com.kingprinters.jpademo.controllers

import com.kingprinters.jpademo.entities.Customer
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import javax.persistence.EntityManager
import javax.persistence.PersistenceContext

@RestController
class HomeController {
    @PersistenceContext(unitName = "default")
    lateinit var em: EntityManager

    @GetMapping("/create")
    @Transactional
    fun create(): String {
        val customer = Customer()
        customer.company = "テスト会社"
        customer.isCompany = true

        val order = Order()
        order.price = 100
        order.product = "テスト商品"
        order.deliveryDate = LocalDateTime.now()

        customer.orders.add(order)
        order.customer = customer
        em.persist(customer)

        return "created"
    }
}
```

SpringBoot再起動後にhttp://localhost:8082/createへアクセスすると、customersテーブルに保存されたことが確認できます  

**カスケード**

先ほどのコードではordersテーブルが保存されませんでした  
これはcustomerオブジェクトが永続化コンテキストでMANAGED状態となった時、関連するオブジェクトの状態は変わらなかった為です  
カスケード機能を使うと、永続化コンテキスト内で関連するオブジェクトの状態を連動させることができます  
Customerエンティティ内のOneToManyアノテーションにてcascadeを指定します  

```kotlin
@OneToMany(mappedBy = "customer", cascade = [CascadeType.ALL])
var orders: MutableList<Order> = mutableListOf()
```

再度http://localhost:8082/createへアクセスすると、ordersテーブルにも保存されることが確認できます  


**Entityの取得**

保存したエンティティを取得するためにコントローラへメソッドを追加します  

```kotlin
@GetMapping("/customers/1")
@Transactional
fun first(): Customer {
    val customer = em.find(Customer::class.java, 1)
    return customer
}
```

また、CustomerとOrderは互いに依存しているため、customerをjsonに変換する際に無限ループが発生します  
それを避けるためにOrderのcustomerフィールドを変更します  

```kotlin
    @ManyToOne
    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator::class, property = "id")
    @JsonIdentityReference(alwaysAsId = true)
    var customer: Customer? = null
```

SpringBoot再起動後にhttp://localhost:8082/customers/1へアクセスすると、customersが取得できます  

**フェッチ戦略**

http://localhost:8082/customers/1へアクセスした際、以下のログが出力されたはずです

```
select customer0_.id as id1_0_0_, customer0_.company as company2_0_0_, customer0_.is_company as is_compa3_0_0_ from customers customer0_ where customer0_.id=?
select orders0_.customer_id as customer5_1_0_, orders0_.id as id1_1_0_, orders0_.id as id1_1_1_, orders0_.customer_id as customer5_1_1_, orders0_.delivery_date as delivery2_1_1_, orders0_.price as price3_1_1_, orders0_.product as product4_1_1_ from orders orders0_ where orders0_.customer_id=?
```

これはcustomerエンティティ取得時にorderが取得されず、必要なタイミングで関連するorderを読み込んでいるためです  
関連するエンティティを取得するタイミングをフェッチ戦略で変えることができます  
フェッチ戦略にはEagerとLazyがあります  
以下のようにフェッチ戦略をEagerに変えてみます  

```kotlin
@OneToMany(mappedBy = "customer", cascade = [CascadeType.ALL], fetch = FetchType.EAGER)
var orders: MutableList<Order> = mutableListOf()
```

再度試してみるとcustomer取得時にorderも合わせて取得されることが分かります  

```
select customer0_.id as id1_0_0_, customer0_.company as company2_0_0_, customer0_.is_company as is_compa3_0_0_, orders1_.customer_id as customer5_1_1_, orders1_.id as id1_1_1_, orders1_.id as id1_1_2_, orders1_.customer_id as customer5_1_2_, orders1_.delivery_date as delivery2_1_2_, orders1_.price as price3_1_2_, orders1_.product as product4_1_2_ from customers customer0_ left outer join orders orders1_ on customer0_.id=orders1_.customer_id where customer0_.id=?
```

**EntityGraph**

調べれなかったです  
雰囲気だけでも感じてください  

```java
@NamedEntityGraph(
  name = "post-entity-graph-with-comment-users",
  attributeNodes = {
    @NamedAttributeNode("subject"),
    @NamedAttributeNode("user"),
    @NamedAttributeNode(value = "comments", subgraph = "comments-subgraph"),
  },
  subgraphs = {
    @NamedSubgraph(
      name = "comments-subgraph",
      attributeNodes = {
        @NamedAttributeNode("user")
      }
    )
  }
)
@Entity
public class Post {

    @OneToMany(mappedBy = "post")
    private List<Comment> comments = new ArrayList<>();
   //...
}
```

```java
EntityGraph entityGraph = entityManager.getEntityGraph("post-entity-graph");
Map<String, Object> properties = new HashMap<>();
properties.put("javax.persistence.fetchgraph", entityGraph);
Post post = entityManager.find(Post.class, id, properties);
```

**JPQL**

続いてJPQLと呼ばれる機能を使って登録したデータを取得してみます  
コントローラーにメソッドを追加しましょう  

```kotlin
@GetMapping("/jpql")
@Transactional
fun jpql(): List<Customer> {
    val query = em.createQuery("SELECT c FROM com.kingprinters.jpademo.entities.Customer c", Customer::class.java)

    return query.resultList
}
```

createQueryメソッドの第一引数で渡されているのがJPQLです  
JPQLはJPAで仕様が定められているSQLのような問い合わせ言語です  
エンティティベースで記述ができます  
http://localhost:8082/createへ何度かアクセスした後で、http://localhost:8082/jpqlにアクセスすると登録したエンティティが表示されます  
ログに出力されたSQLの通りcustomer取得時にはorderは取得されていません  
customerに紐づくorderも一緒に取得してみます  

```kotlin
@GetMapping("/jpql")
@Transactional
fun jpql(): List<Customer> {
    val query = em.createQuery("SELECT c FROM com.kingprinters.jpademo.entities.Customer c left join fetch c.orders", Customer::class.java)

    return query.resultList
}
```

`left join fetch c.orders`によって関連するエンティティも合わせて取得します  
JPQLは今回のようにEntityManagerのcreateQueryでも使用できますが、エンティティに対して定義することもできます  
Customerエンティティ・コントローラをそれぞれ以下のように修正します  

```kotlin
@Entity(name = "customers")
@NamedQuery(name = "Customer.findAll", query = "SELECT c FROM com.kingprinters.jpademo.entities.Customer c left join fetch c.orders WHERE c.id=:id")
class Customer
```

```kotlin
@GetMapping("/namedQuery")
@Transactional
fun namedQuery(): List<Customer> {
    val query = em.createNamedQuery("Customer.findAll", Customer::class.java)
    query.setParameter("id", 3)
    return query.resultList
}
```

JPQLのwhere句とqueryにパラメータを設定している通り、idが3のcustomerのみ取得できました  

**CriteriaAPI**

基本的にJPQLは文字列で書かれているので構文ミスが検知できません  
※IDEにNetbeansを利用しているとできるらしいです  
CriteriaAPIはメソッドを使ってSQLを組み立てるので構文ミスが起きにくいです  
HomeControllerに以下のメソッドを追加します  

```kotlin
@GetMapping("/criteria")
@Transactional
fun criteria(): List<Customer> {
    val builder = em.criteriaBuilder
    val query = builder.createQuery(Customer::class.java)

    val root = query.from(Customer::class.java)
    query.select(root)
            .where(builder.lessThan(root.get("id"), 3))

    val q = em.createQuery<Customer>(query)

    return q.resultList
}
```

http://localhost:8082/criteriaにアクセスするとidが3より小さいcustomerが取得できたことを確認できます  

**メタモデル**

先ほどのコードでもクエリを組み立てることは出来るのですが、  

> .where(builder.lessThan(root.get("id"), 3))

としたようにカラム名を文字列で指定しており、間違える可能性があります  
JPAのメタモデルという仕組みを利用することで型付けされたフィールドが指定できます  
時間がなかったのですが、大体以下のように指定できるそうです  

```kotlin
val builder = em.criteriaBuilder
val query = builder.createQuery(Customer::class.java)
val root = query.from(Customer::class.java)
query.select(root)
        .where(builder.lessThan(root.get(Customer_.id), 3))
val q = em.createQuery<Customer>(query)
```

Customer_クラスがメタモデルとなります  
これは設定をすることで、コンパイル時に自動で生成されるそうです  