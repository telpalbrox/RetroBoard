import { combineReducers } from 'redux';
import { createBoardReducer } from './createBoardReducer';
import { boardReducer } from './boardReducer';

export default combineReducers({
    createBoard: createBoardReducer,
    board: boardReducer
});
