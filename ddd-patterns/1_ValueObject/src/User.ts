import Id from "./Id"
import Name from "./Name"

/**
 * ユーザを表現する値オブジェクト
 *
 * @class User
 * @property {Id}
 * @property {Name}
 *
 * POINT:【表現力を増す】
 * このクラスを確認することでユーザは ID と名前で構成されることがわかる(=コードがドキュメント化する)。
 */
class User {
	public id: Id

	public userName: Name

	constructor(id: Id, userName: Name) {
		this.id = id
		this.userName = userName
	}
}

//-----------------------------------------------------------------------------
// 以下利用例
//-----------------------------------------------------------------------------

const newId = new Id(1)
const newName = new Name("John")
const newUser = new User(newId, newName) // 新規 User を作成

// コンパイルエラー例1
newUser.id = new Name("Paul") // Id 型に Name 型は代入できない

// コンパイルエラー例2
newUser.id = 2 // Id 型に number 型は代入できない(!)

// POINT:【誤った代入を防ぐ】
// "id" フィールドを値オブジェクトで型宣言することで実行前にエラーが検出できる
