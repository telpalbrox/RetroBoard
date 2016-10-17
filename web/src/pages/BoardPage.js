import React, { Component } from 'react';
import AppBar from 'material-ui/AppBar';
import { connect } from 'react-redux';
import { getBoard, connectBoard } from '../actions/boards';
import Section from '../components/Section';

class BoardPage extends Component {
    static mapStateToProps(state) {
        const { loading, error, board} = state.board;
        return { loading, error, board};
    }

    async componentWillMount() {
        const board = await this.props.dispatch(getBoard(this.props.params.name));
        this.props.dispatch(connectBoard(board.uuid));
    }

    render() {
        if (this.props.loading) {
            return (<div>Loading...</div>);
        }
        if (this.props.error) {
            return (<div>Error :(</div>)
        }

        const { board } = this.props;
        return (
            <div>
                <AppBar
                    title={`Retro board ${board.name}`}
                />
                <div className="content">
                    <div className="sections-container">
                        {board.sections.map((section) => {
                            return (<Section key={section.uuid} section={section} />);
                        })}
                    </div>
                </div>
            </div>
        );
    }
}

export default connect(BoardPage.mapStateToProps)(BoardPage);
