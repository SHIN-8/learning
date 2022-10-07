
package org.examproject.optional.model;

import java.util.Date;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

/**
 * Person クラス
 */
@Getter
@RequiredArgsConstructor
public class Person {

    /**
     * ID
     */
    @NonNull
    private long id;

    /**
    * 名前
    */
    @NonNull
    private String name;

    /**
     * 性別 1:男性、2:女性
     */
    @NonNull
    private int gender;

    /**
     * 出生日
     */
    @NonNull
    private Date born;
}
