import React from 'react';
import ReactDOM from 'react-dom';
import { createStore, applyMiddleware, compose } from 'redux';
import thunk from 'redux-thunk';
import {
    BrowserRouter as Router,
    Switch,
    Route
} from 'react-router-dom';
import { Provider } from 'react-redux';
import App from './App';
import appReducer from './reducers' ;
import HomePage from './pages/HomePage';
import BoardPage from './pages/BoardPage';
import './index.css';

const store = createStore((appReducer), compose(applyMiddleware(thunk), window.devToolsExtension ? window.devToolsExtension() : (f) => f));

const basename = process.env.PUBLIC_URL ? '/RetroBoard/': '/';

ReactDOM.render(
    <Provider store={store}>
        <Router basename={basename}>
            <App>
                <Switch>
                    <Route exact path="/">
                        <HomePage />
                    </Route>
                    <Route path="/board/:name">
                        <BoardPage />
                    </Route>
                </Switch>
            </App>
        </Router>
    </Provider>,
  document.getElementById('root')
);
