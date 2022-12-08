import * as React from 'react'
import * as ReactDOM from 'react-dom'

//---------------------------
//component
//---------------------------
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