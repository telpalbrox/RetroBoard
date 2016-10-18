import axios from 'axios';
import config from '../config';
import SockJS from 'sockjs-client';
import Stomp from 'stompjs';

export const actions = {
    CREATE_BOARD: 'CREATE_BOARD',
    CREATE_BOARD_SUCCESS: 'CREATE_BOARD_SUCCESS',
    CREATE_BOARD_ERROR: 'CREATE_BOARD_ERROR',
    GET_BOARD: 'GET_BOARD',
    GET_BOARD_SUCCESS: 'GET_BOARD_SUCCESS',
    GET_BOARD_ERROR: 'GET_BOARD_ERROR',
    ADD_TICKET: 'ADD_TICKET',
    REMOVE_TICKET: 'REMOVE_TICKET',
    ADD_SECTION: 'ADD_SECTION',
    REMOVE_SECTION: 'REMOVE_SECTION',
    REMOVE_SECTION_SUCCESS: 'REMOVE_SECTION_SUCCESS',
    REMOVE_SECTION_ERROR: 'REMOVE_SECTION_ERROR',
    TICKET_DELETED: 'TICKET_DELETED',
    SECTION_DELETED: 'SECTION_DELETED',
    CREATE_SECTION: 'CREATE_SECTION',
    CREATE_SECTION_SUCCESS: 'CREATE_SECTION_SUCCESS',
    CREATE_SECTION_ERROR: 'CREATE_SECTION_ERROR',
};

let stompClient = null;

export const createBoard = (name) => async (dispatch) => {
    dispatch({ type: actions.CREATE_BOARD });
    try {
        const response = await axios.post(`${config.apiUrl}/boards`, { name });
        dispatch({ type: actions.CREATE_BOARD_SUCCESS, uuid: response.data.uuid });
        return response.data;
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
        return response.data;
    } catch(e) {
        dispatch({ type: actions.GET_BOARD_ERROR, error: e });
        throw e;
    }
};

export const connectBoard = (uuid) => (dispatch) => {
    const socket = new SockJS(`${config.apiUrl}/socket`);
    stompClient = Stomp.over(socket);
    stompClient.connect({}, () => stompClient.subscribe(`/boards/${uuid}`, (event) => {
        event = JSON.parse(event.body);
        if (event.type === 'ADDED' && event.entity === 'ticket') {
            return dispatch({ type: actions.ADD_TICKET, ticket: event.payload });
        }
        if (event.type === 'ADDED' && event.entity === 'section') {
            return dispatch({ type: actions.ADD_SECTION, section: event.payload });
        }
        if (event.type === 'REMOVED' && event.entity === 'ticket') {
            return dispatch({ type: actions.TICKET_DELETED, ticket: event.payload });
        }
        if (event.type === 'REMOVED' && event.entity === 'section') {
            return dispatch({ type: actions.SECTION_DELETED, section: event.payload });
        }
    }));
}

export const createSection = ({ boardUuid, name }) => async (dispatch) => {
    dispatch({ type: actions.CREATE_BOARD, name });
    try {
        const response = await axios.post(`${config.apiUrl}/boards/${boardUuid}/sections`, { name });
        dispatch({ type: actions.CREATE_BOARD_SUCCESS, section: response.data });
        return response.data;
    } catch (e) {
        dispatch({ type: actions.CREATE_BOARD_ERROR, error: e });
        throw e;
    }
};

export const removeSection = ({ boardUuid, section }) => async (dispatch) => {
    dispatch({ type: actions.REMOVE_SECTION, section });
    try {
        const response = await axios.delete(`${config.apiUrl}/boards/${boardUuid}/sections/${section.uuid}`);
        dispatch({ type: actions.REMOVE_SECTION_SUCCESS, section: response.data });
        return response.data;
    } catch (e) {
        dispatch({ type: actions.REMOVE_SECTION_ERROR, error: e });
        throw e;
    }
};
