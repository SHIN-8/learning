
package org.examproject.optional.model;

import java.util.Date;

import lombok.Getter;
import lombok.NonNull;

/**
 * 魔法使いクラス
 */
@Getter
public class Magician extends Person{

    /**
     * マジックポイント
     */
    @NonNull
    private long mp;

    /**
     * コンストラクタ
     * @param id     IDが提供されます
     * @param name   名前が提供されます
     * @param gender 性別が提供されます
     * @param born   出生日が提供されます
     * @param mp     マジックポイントが提供されます
     */
    public Magician(long id, String name, int gender, Date born, long mp) {
        // スーパークラスのコンストラクタを呼び出します
        super(id, name, gender, born);

        // マジックポイントを初期化します
        this.mp = mp;
    }
}
