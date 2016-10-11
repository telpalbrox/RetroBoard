import { actions } from '../actions/boards';

const initialState = {
    loading: false,
    error: null,
    boardUuid: null
};

export const createBoardReducer = (state = initialState, action) => {
    switch(action.type) {
        case actions.CREATE_BOARD:
            return Object.assign({}, state, { loading: true, error: null, boardUuid: null });
        case actions.CREATE_BOARD_SUCCESS:
            return Object.assign({}, state, { loading: false, error: null, boardUuid: action.uuid });
        case actions.CREATE_BOARD_ERROR:
            return Object.assign({}, state, { loading: false, error: action.error, boardUuid: null });
        default:
            return state;
    }
};
