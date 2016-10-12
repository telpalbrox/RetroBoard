import { actions } from '../actions/boards';

const initialState = {
    loading: true,
    error: null,
    board: null
};

const getSectionByUuid = (sections, uuid) => {
    return sections.find((section) => section.uuid === uuid);
};

export const boardReducer = (state = initialState, action) => {
    switch(action.type) {
        case actions.GET_BOARD:
            return Object.assign({}, state, { board: null, loading: true, error: null });
        case actions.GET_BOARD_SUCCESS:
            return Object.assign({}, state, { board: action.board, loading: false, error: null });
        case actions.GET_BOARD_ERROR:
            return Object.assign({}, state, { board: null, loading: false, error: action.error });
        case actions.ADD_SECTION:
            return Object.assign({}, state, {
                board: Object.assign({}, state.board, {
                    sections: [...state.board.sections, action.section]
                })
            });
        case actions.ADD_TICKET:
            const section = getSectionByUuid(state.board.sections, action.ticket.sectionUuid);
            const sectionIndex = state.board.sections.indexOf(section);
            const newSection = Object.assign({}, section, { tickets: [...section.tickets, action.ticket] });
            return Object.assign({}, state, {
                board: Object.assign({}, state.board, {
                    sections: [...state.board.sections.slice(0, sectionIndex), newSection, ...state.board.sections.slice(sectionIndex + 1)]
                })
            });
        default:
            return state;
    }
};
