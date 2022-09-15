package org.example.controller;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import org.example.response.Response;

/**
 * Ajax通信で HTTP リクエストされる Controller POJO クラス
 */
@RequiredArgsConstructor
@Controller
public class ApiController {

    ///////////////////////////////////////////////////////////////////////////
    // Field

    @NonNull
    private ApplicationContext context; // lombok が自動でコンストラクタをつくりそこからインジェクションされます

    ///////////////////////////////////////////////////////////////////////////
    // public methods

    // Ajax リクエストを受けメッセージを返します
    @CrossOrigin
    @RequestMapping(
        value="/message",
        method=RequestMethod.GET,
        headers="Accept=application/json"
    )
    public @ResponseBody Response getMessage() {
        // 本来ならここでビジネスロジックを処理して

        // レスポンス用のオブジェクトを返す
        return (Response) context.getBean(
            "response", // bean の名前を指定
            "Did you call me?", // メッセージの内容
            false
        );
    }
}
