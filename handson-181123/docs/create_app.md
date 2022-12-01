### TODOアプリの作成
デモアプリとして、以下の機能を持つTODOアプリを作成を取り上げます。

#### TODOアプリの仕様
デモで作成するTODOアプリは、以下の機能を持ちます。

- 文字列型のテキストフィールドを複数持つ。
- TODOのテキストフィールドを1つ追加するボタンを持つ。
- TODOのテキストフィールドをを削除ボタンを持つ。

機能的には単純で、テキストフィールドを編集することおよびテキストフィールドの数を増減させるのみです。TODOの期限管理や保留・終了・未着手などのステータス管理やDBへの保存は扱いません。しかし、余力があれば挑戦してみるといいと思います。

#### TODOコンポーネントの作成
まずは、コンポーネントから追加します。今回作成するTODOアプリはTODOの追加ボタンと編集可能なテキストフィールドの配列、テキストフィールドごとの削除ボタンがあります。
ザクッと書くと、以下のようなコードになるかと思います。

```jsx
export function Todo(props) {
    let key = 0
    return (<div style={{ textAlign: 'left', margin: 12 }}>
        <button
            onClick={props.onClickIncrement} >
            TODOを追加する
        </button><br />
        <table style={{ align: 'center' }}>
            <thead>
                <tr>
                    <th>内容</th>
                    <th>操作</th>
                </tr>
            </thead>
            <tbody>
                {
                    props.todo.map((element, index, array) => {
                        return <tr>
                            <td>
                                <input value={element} onChange={props.onChange.bind(this, index)} key={key++} />
                            </td>
                            <td>
                                <button onClick={props.onClickDecrement.bind(this, index)} key={key++} >
                                    削除する
                    </button>
                            </td>
                        </tr>
                    })
                }
            </tbody>
        </table>
    </div>)

}
```

まずトップに追加ボタンがあって、テーブルレイアウトを使って左揃えにテキストフィールドと対応する削除ボタンを表示しています。

#### コンポーネントとストアの接続
作成したReactコンポーネントと、`redux`のストアを接続するには`react-redux`の`connect`関数を使います。`connect`関数は、`state`を`props`に割り当てる`mapStateToProps`と、`ActionCreator`を作成して`dispatch`関数で`reducer`へ通知する`mapDispatchToProps`の2つの関数を受け取ります。

定義した`mapStateToProps`と`mapDispatchToProps`および`React`コンポーネントを`connect`関数に渡すと`Redux`のストアと接続されたコンテナコンポーネントを作成できます。

```js
import * as React from 'react'
import * as ReactDom from 'react-dom'
import { Todo } from '../component/Todo'
import { connect } from 'react-redux'
import * as TodoModule from '../module/Todo'

function mapStateToProps(state) {
    return {
        ...state.todo
    };
}

function mapDispatchToAciton(dispatch) {
    return {
        onChange(index, e) {
            dispatch(TodoModule.onChange({ index: index, value: e.target.value }))
        },
        onClickIncrement() {
            dispatch(TodoModule.onIncrement())
        },
        onClickDecrement(index) {
            dispatch(TodoModule.onDecliment({ index: index }))
        },
    }
}

export default connect(
    mapStateToProps,
    mapDispatchToAciton
)(Todo);
```

#### ActionCreatorの実装
`ActionCreator`は、`Action`と呼ばれる名前と`Action`ごとのデータを持つオブジェクトを作成する関数です。`ActionCreator`は`redux-actions`の`createAction`関数を使って実装できます。

`createAction`関数には`Aciton`名を渡し`mapDispatchToProps`から渡される`dispatch`メソッドを適用することで、`Reducer`を使ってストアを更新できます。

```js
//-----------------------
//Action
//-----------------------
export const OnChangeActionName = "Todo/onChange"
export const OnClickInclimentActionName = "Todo/onClickIncrement"
export const OnClickDeclimentActionName = "Todo/onClickDecliment"


//-----------------------
//ActionCreator
//-----------------------

export const onChange = createAction(OnChangeActionName)
export const onIncrement = createAction(OnClickInclimentActionName)
export const onDecliment = createAction(OnClickDeclimentActionName)
```



#### Reducerの作成
`Reducer`は、`ActionCreator`から渡されたアクション名とアクションごとのデータを受け取って、ストアを更新する関数です。`Reducer`は、`redux-actions`の`handleActions`を使って次のように書くことができます。

```js
export const TodoReducer = handleActions({
  [OnChangeActionName]: (state, action) => {
    const todo = state.todo.slice(0)
    const { index, value } = action.payload
    console.log(todo)
    todo[index] = value
    return { todo: todo }

  },

  [OnClickInclimentActionName]: (state, action) => {
    const todo = state.todo.slice(0)
    todo.push('')
    console.log(todo)
    return { todo: todo }
  },

  [OnClickDeclimentActionName]: (state, action) => {
    const todo = state.todo.slice(0)
    const { index } = action.payload
    todo.splice(index, 1)
    console.log(todo)
    return { todo: todo }

  },


}, InitialTodoStore)


export default TodoReducer
```

#### Storeの実装
最後にストアを実装します。ストアは`combineReducer`と`createStore`を使って実装します。`combineReducer`は複数の`Reducer`を1つにまとめる関数で、`createStore`は与えられた`reudcer`からストアを作成する関数です。

```js

import { createStore, combineReducers, applyMiddleware, compose } from 'redux';
import { handleActions } from 'redux-actions'

//Reducers
import { TodoReducer } from './module/Todo'


const reducers = Object.assign({},
  { todo: TodoReducer });

const appReducer = combineReducers(reducers)

export const Store = createStore(
  appReducer, {});
```

#### ReduxストアとRectの接続
react-reduxのProviderコンポーネントを使って、実装したストアとreactのコンポーネントを接続します。

```js
import React from 'react';
import ReactDOM from 'react-dom';
import './index.css';
import App from './App';
import * as serviceWorker from './serviceWorker';
import { Provider } from 'react-redux'
import * as Store from './Store'

ReactDOM.render(
  <Provider store={Store.Store}>
    <App />
  </Provider>, document.getElementById('root'));

// If you want your app to work offline and load faster, you can change
// unregister() to register() below. Note this comes with some pitfalls.
// Learn more about service workers: http://bit.ly/CRA-PWA
serviceWorker.unregister();

```