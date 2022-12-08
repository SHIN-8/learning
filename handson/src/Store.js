
import { createStore, combineReducers, applyMiddleware, compose } from 'redux';
import { handleActions } from 'redux-actions'

//Reducers
import { TodoReducer } from './module/Todo'


const reducers = Object.assign({},
  { todo: TodoReducer });

const appReducer = combineReducers(reducers)

export const Store = createStore(
  appReducer, {});
