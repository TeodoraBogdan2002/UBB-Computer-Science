import React from 'react';
import ReactDOM from 'react-dom/client';
import './index.css';
import App from './App';
import 'bootswatch/dist/flatly/bootstrap.min.css'

const root = ReactDOM.createRoot(document.getElementById('root'));
document.body.style.backgroundColor = '#F7DED0';
root.render(
  <React.StrictMode>
    <App />
  </React.StrictMode>
);
