import React, { Component } from 'react';
import AppBar from 'material-ui/AppBar';
import { connect } from 'react-redux';
import { getBoard } from '../actions/boards';
import Section from '../components/Section';

class BoardPage extends Component {
    static mapStateToProps(state) {
        const { loading, error, board} = state.board;
        return { loading, error, board};
    }

    componentWillMount() {
        this.props.dispatch(getBoard(this.props.params.name));
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
                    title={`Retro board ${this.props.board.name}`}
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
