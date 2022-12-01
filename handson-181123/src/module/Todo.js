import * as React from 'react'
import * as ReactRedux from 'react-redux'
import { createAction, handleActions } from 'redux-actions'

//-----------------------
//Model
//-----------------------
export const InitialTodoStore = {
  todo: [''],
}

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


//-----------------------
//Reducer
//-----------------------
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

