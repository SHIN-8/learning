
package org.examproject.optional.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;

import org.examproject.optional.model.Person;
import org.examproject.optional.model.PersonRepository;

/**
 * Person オブジェクトを提供するサービスクラス
 */
@Service
public class PersonService {

    /**
     * PersonRepository オブジェクト
     */
    private PersonRepository personRepository;

    /**
     * Person オブジェクトのリストを設定します
     * @param personList Person オブジェクトのリストが提供されます
     */
    public void setPersonList(List<Person> personList) {
        personRepository = new PersonRepository(personList);
    }

    /**
     * Person オブジェクトを Object 型のオブジェクトとして返します
     * @param personId Person オブジェクトの ID が提供されます
     * @return Object 型のオブジェクトを返します
     */
    public Object getObject(long personId) {
        return personRepository.findByPersonId(personId);
    }

    /**
     * Person オブジェクトを返します
     * @param personId Person オブジェクトの ID が提供されます
     * @return Person オブジェクトを返します
     */
    public Person getPerson(long personId) {
        return personRepository.findByPersonId(personId);
    }

    /**
     * Optional 型でラップした Person オブジェクトを返します
     * @param personId Person オブジェクトの ID が提供されます
     * @return Optional 型でラップした Person オブジェクトを返します
     */
    public Optional<Person> getOptional(long personId) {
        return Optional.ofNullable(personRepository.findByPersonId(personId));
    }

    /**
     * [検証用] null を返します
     * @param personId Person オブジェクトの ID が提供されます
     * @return Optional null を返します
     */
    public Optional<Person> getOptionalAsNull(long personId) {
        return null;
    }

    /**
     * 全ての Person オブジェクトを返します
     * @return Person オブジェクトのリストを返します
     */
    public List<Person> getAll() {
        return personRepository.getAll();
    }

    /**
     * [検証用] 空の List を返します
     * @return 空の List を返します
     */
    public List<Person> getAllAsEmpty() {
        return new ArrayList<Person>();
    }

    /**
     * [検証用] null を返します
     * @return null を返します
     */
    public List<Person> getAllAsNull() {
        return null;
    }

    /**
     * Optional 型でラップした全ての Person オブジェクトを返します
     * @return Optional 型でラップした Person オブジェクトのリストを返します
     */
    public Optional<List<Person>> getAllAsOptional() {
        return Optional.ofNullable(personRepository.getAll());
    }

    /**
     * [検証用] Optional 型でラップした null を返します
     * @return Optional 型でラップした null を返します
     */
    public Optional<List<Person>> getAllAsNullOptional() {
        return Optional.ofNullable(null);
    }
}
