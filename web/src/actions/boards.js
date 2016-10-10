import axios from 'axios';
import config from '../config';

export const actions = {
    CREATE_BOARD: 'CREATE_BOARD',
    CREATE_BOARD_SUCCESS: 'CREATE_BOARD_SUCCESS',
    CREATE_BOARD_ERROR: 'CREATE_BOARD_ERROR'
};

export const createBoard = (name) => async (dispatch) => {
    dispatch({ type: actions.CREATE_BOARD });
    try {
        const response = await axios.post(`${config.apiUrl}/boards`, { name });
        dispatch({ type: actions.CREATE_BOARD_SUCCESS, uuid: response.data.uuid });
    } catch(e) {
        dispatch({ type: actions.CREATE_BOARD_ERROR, error: e });
    }
};
