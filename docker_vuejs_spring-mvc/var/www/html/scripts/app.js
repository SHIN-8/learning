// データオブジェクト
var data = { message: "It works!" }

window.addEventListener("load", () => {
    // Vue インスタンスにオブジェクトを追加する
    var vm = new Vue({
        el: '#vue-app',
        data: data,
        // `methods` オブジェクトの下にメソッドを定義する
        methods: {
            getMessage() {
                // バックエンドサーバーへ Ajax リクエスト
                axios.get(
                    'http://localhost:8881/api/message', {
                        headers: {'content-type': 'application/json'}
                    })
                    .then(response => {
                        // Ajax リクエストの戻り値を取得
                        this.message = response.data.message;
                    })
                    .catch(error => {
                        // エラーメッセージを表示
                        this.message = error;
                    });
            }
        }
    })
});