import { combineReducers } from 'redux';
import { routerReducer } from 'react-router-redux';
import { createBoardReducer } from '../reducers/createBoardReducer';

export default combineReducers({
    createBoard: createBoardReducer,
    routing: routerReducer,
});
