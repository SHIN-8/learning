package org.example.response;

import lombok.Getter;
import lombok.NonNull;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * JSON にマッピングされる POJO クラス
 */
@Getter
@AllArgsConstructor
@EqualsAndHashCode
@ToString
@Component(value="response") // bean の名前
@Scope(value="prototype")
public class Response {

    ///////////////////////////////////////////////////////////////////////////
    // Field

    @NonNull
    private String message; // メッセージの内容

    @NonNull
    private boolean isError; // エラーが発生したかどうか(※ダミー)
}