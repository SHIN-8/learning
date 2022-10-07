
package org.examproject.optional.model;

import java.util.List;
import org.springframework.stereotype.Component;

/**
 * Person オブジェクトのリポジトリクラス
 */
@Component
public class PersonRepository {

    /**
     * Person オブジェクトのリスト
     */
    private List<Person> personList;

    /**
     * コンストラクタ
     */
    public PersonRepository(List<Person> personList){
        this.personList = personList;
    }

    /**
     * 引数 id の Person オブジェクトを返します
     * @param personId
     * @return Person オブジェクトを返します
     */
    public Person findByPersonId(long personId) {
        for (Person p : personList) {
            if (p.getId() == personId) {
                return p;
            }
        }
        return null;
    }

    /**
     * 全ての Person オブジェクトを返します
     * @return Person オブジェクトのリストを返します
     */
    public List<Person> getAll() {
        return personList;
    }
}
