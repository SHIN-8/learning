import React from 'react';
import logo from './logo.svg';
import './App.css';
import { Students } from "./Students"
import { TextArea } from "./TextArea"
function App() {
  return (
    <div className="App m-10">
      <Students></Students>
      <TextArea />
    </div>
  );
}

export default App;
