/**
 * 「名前」を表現する値オブジェクト
 *
 * @class Name
 */
class Name {
	private readonly value: string

	constructor(value: string) {
		if (!this.validate(value)) {
			// POINT:【不正な値を存在させない】
			// 不正な値ではインスタンス化ができない(存在できない)ため。
			throw new Error("不正な値がしていされています。")
		}

		this.value = value
	}

	/**
	 * バリデートする。
	 *
	 * POINT:【ロジックの散在を防ぐ】
	 * 値オブジェクト内にロジックを詰め込むことで一箇所で管理できる。
	 * またこのロジックはドメインのルールであるため、コードがドキュメント化する。
	 *
	 * @private
	 * @param {string} value
	 * @returns {boolean}
	 */
	private validate(value: string): boolean {
		const regex = new RegExp(/^[a-zA-Z]+$/) // 1文字以上の半角英字

		return regex.test(value)
	}

	public get(): string {
		return this.value
	}

	/**
	 * 名前のイニシャルを返す。
	 * 
	 * POINT:【ドメインに基づいたふるまい】
	 * ドメイン上必要な操作を値オブジェクトのメソッドとして定義できる。
	 *
	 * @returns {string}
	 */
	public getInitials(): string {
		return this.value.slice(0, 1)
	}

	/**
	 * インスタンス同士が等しければ true を返す。
	 * 
	 * POINT:【等価性によって比較される】
	 * 外部でフィールドを取り出して比較演算子で比較するのではなく、メソッドとして定義する。
	 *
	 * @param {Name} other
	 * @returns {boolean}
	 */
	public equals(other: Name): boolean {
		return this.value === other.get()
	}
}

export default Name
