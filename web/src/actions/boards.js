import axios from 'axios';
import config from '../config';

export const actions = {
    CREATE_BOARD: 'CREATE_BOARD',
    CREATE_BOARD_SUCCESS: 'CREATE_BOARD_SUCCESS',
    CREATE_BOARD_ERROR: 'CREATE_BOARD_ERROR',
    GET_BOARD: 'GET_BOARD',
    GET_BOARD_SUCCESS: 'GET_BOARD_SUCCESS',
    GET_BOARD_ERROR: 'GET_BOARD_ERROR'
};

export const createBoard = (name) => async (dispatch) => {
    dispatch({ type: actions.CREATE_BOARD });
    try {
        const response = await axios.post(`${config.apiUrl}/boards`, { name });
        dispatch({ type: actions.CREATE_BOARD_SUCCESS, uuid: response.data.uuid });
    } catch(e) {
        dispatch({ type: actions.CREATE_BOARD_ERROR, error: e });
        throw e;
    }
};

export const getBoard = (name) => async (dispatch) => {
    dispatch({ type: actions.GET_BOARD });
    try {
        const response = await axios.get(`${config.apiUrl}/boards/${name}`);
        dispatch({ type: actions.GET_BOARD_SUCCESS, board: response.data });
    } catch(e) {
        dispatch({ type: actions.GET_BOARD_ERROR, error: e });
        throw e;
    }
};
