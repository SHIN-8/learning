## Jestとは
JestとはFacebook社が開発しているJavaScript向けのテストフレームワークの１つである。従来のmochaなどではカバレッジを取得するにはistanbul、モックを作成するためにはSinonなど関連するパッケージが複数あったが、Jestではこれらの機能がすべて内包されているため外部ライブラリの導入がこれまでに比べて少なくて済むメリットがある（一部必要なものもある）。

## Jestの導入
前回作成したTodoアプリのプロジェクトを開き、以下のコマンドを実行しnpm パッケージのインストールを行う。

__npm__ の場合

```
npm install --save-dev jest enzyme enzyme-adapter-react-16
```

__yarn__ の場合
```
yarn add -d jest enzyme enzyme-adapter-react-16
```

<!-- pagebreak -->

## テストを書く
### 設定ファイルの作成
Jestでテストを書く前に、jest.config.jsを作成し設定を記載する。

```javascript
module.exports = {
	//--------
	//basic options
	roots: [
		"__tests__"
	],
	testRegex: "(/__tests__/.*|(\\.|/)(test|spec))\\.jsx?$",

	verbose: true,

	//------
	//module resolution
	moduleFileExtensions: [
		"js",
		"jsx",
		"json",
		"node"
	],
	modulePaths: [
		'./src',
		'./node_modules'
	],

	modulePathIgnorePatterns: [
		"__data__",
		"__mock__"
	],

	//--------
	//coverage
	collectCoverage: true,
	coverageDirectory: "./dist/coverage"
}
```

<!-- pagebreak -->

### 関数のテスト

```javascript
function plus(a, b) {
    return a + b
}

it("plus", ()=> {
    const received = plus(1, 2);
    const expected = 3;

    expect(received).toBe(expected)
})

```

### クラスのテスト

```javascript
class Person {
    public name
    public age

    constructor(name, age) {
        this.name = name
        this.age  = age
    }
}


it("Person constructor", ()=> {
    const person = new Person("ほげほげさん", 20)
    const expectedName = "ほげほげさん"
    const expectedAge  = 20;

    expect(person.name).toBe(expectedName)
    expect(person.age).toBe(expectedAge)
})

```

### コンポーネントのテスト
```javascript
it("Person constructor", ()=> {
    const commponent = shallow(<TODO todo={[]}/>)
    expect(component).toMatchSnapshot()
})
```
