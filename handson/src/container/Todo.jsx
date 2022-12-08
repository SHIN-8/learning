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