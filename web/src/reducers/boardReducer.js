/* eslint-disable */
import { actions } from '../actions/boards';

const initialState = {
    loading: true,
    error: null,
    board: null
};

const getSectionByUuid = (sections, uuid) => {
    return sections.find((section) => section.uuid === uuid);
};

const getTicketByUuid = (tickets, uuid) => {
    return tickets.find((ticket) => ticket.uuid === uuid);
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
            var section = getSectionByUuid(state.board.sections, action.ticket.sectionUuid);
            var sectionIndex = state.board.sections.indexOf(section);
            var newSection = Object.assign({}, section, { tickets: [...section.tickets, action.ticket] });
            return Object.assign({}, state, {
                board: Object.assign({}, state.board, {
                    sections: [...state.board.sections.slice(0, sectionIndex), newSection, ...state.board.sections.slice(sectionIndex + 1)]
                })
            });
        case actions.SECTION_DELETED:
            var section = getSectionByUuid(state.board.sections, action.ticket.sectionUuid);
            var sectionIndex = state.board.sections.indexOf(section);
            return Object.assign({}, state, {
                board: Object.assign({}, state.board, {
                    sections: [...state.board.sections.slice(0, sectionIndex), ...state.board.sections.slice(sectionIndex + 1)]
                })
            });
        case actions.TICKET_DELETED:
            console.log('ticket deleted');
            var section = getSectionByUuid(state.board.sections, action.ticket.sectionUuid);
            var sectionIndex = state.board.sections.indexOf(section);
            var ticket = getTicketByUuid(section.tickets, action.ticket.uuid);
            var ticketIndex = section.tickets.indexOf(ticket);
            var newSection = Object.assign({}, section, { tickets: [...section.tickets.slice(0, ticketIndex), ...section.tickets.slice(ticketIndex + 1)] });
            return Object.assign({}, state, {
                board: Object.assign({}, state.board, {
                    sections: [...state.board.sections.slice(0, sectionIndex), newSection, ...state.board.sections.slice(sectionIndex + 1)]
                })
            });
        default:
            return state;
    }
};
