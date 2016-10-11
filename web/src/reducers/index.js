import { combineReducers } from 'redux';
import { routerReducer } from 'react-router-redux';
import { createBoardReducer } from './createBoardReducer';
import { boardReducer } from './boardReducer';

export default combineReducers({
    routing: routerReducer,
    createBoard: createBoardReducer,
    board: boardReducer
});
