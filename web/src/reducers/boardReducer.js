import { actions } from '../actions/boards';

const initialState = {
    loading: true,
    error: null,
    board: null
};

export const boardReducer = (state = initialState, action) => {
    switch(action.type) {
        case actions.GET_BOARD:
            return Object.assign({}, state, { board: null, loading: true, error: null });
        case actions.GET_BOARD_SUCCESS:
            return Object.assign({}, state, { board: action.board, loading: false, error: null });
        case actions.GET_BOARD_ERROR:
            return Object.assign({}, state, { board: null, loading: false, error: action.error });
        default:
            return state;
    }
};
