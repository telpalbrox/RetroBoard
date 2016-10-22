import React from 'react';
import ReactDOM from 'react-dom';
import { createStore, applyMiddleware, compose } from 'redux';
import thunk from 'redux-thunk';
import { Router, Route, IndexRoute, useRouterHistory } from 'react-router';
import { createHistory } from 'history';
import { syncHistoryWithStore, routerMiddleware } from 'react-router-redux';
import { Provider } from 'react-redux';
import App from './App';
import appReducer from './reducers' ;
import HomePage from './pages/HomePage';
import BoardPage from './pages/BoardPage';
import './index.css';

const browserHistory = useRouterHistory(createHistory)({
    basename: process.env.PUBLIC_URL ? '/RetroBoard/': '/'
});

require('react-tap-event-plugin')();

const store = createStore((appReducer), compose(applyMiddleware(thunk, routerMiddleware(browserHistory)), window.devToolsExtension ? window.devToolsExtension() : (f) => f));

const history = syncHistoryWithStore(browserHistory, store);

ReactDOM.render(
    <Provider store={store}>
        <Router history={history}>
            <Route path="/" component={App}>
                <IndexRoute component={HomePage}/>
                <Route path="/board/:name" component={BoardPage}/>
            </Route>
        </Router>
    </Provider>,
  document.getElementById('root')
);
