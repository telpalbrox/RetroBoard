import React, { Component } from 'react';
import { connect } from 'react-redux';
import TextField from 'material-ui/TextField';
import RaisedButton from 'material-ui/RaisedButton';
import { createBoard } from '../actions/boards';

class HomePage extends Component {
    static mapStateToProps(state) {
        return state.createBoard;
    }

    render() {
        return (
            <div className="home-page">
                <h1>Create board</h1>
                <TextField
                    hintText="Retro Sprint 41"
                    floatingLabelText="Name your board"
                    ref={(node) => this.nameInput = node}
                />
                <RaisedButton label="Create" primary={true} onTouchTap={() => this.onCreateBoard()} />
            </div>
        );
    }

    onCreateBoard() {
        if (!this.nameInput.getValue()) {
            return;
        }
        this.props.dispatch(createBoard(this.nameInput.getValue()));
    }
}

export default connect(HomePage.mapStateToProps)(HomePage);
