import React, { Component } from 'react';
import { connect } from 'react-redux';
import TextField from 'material-ui/TextField';
import RaisedButton from 'material-ui/RaisedButton';
import { push } from 'react-router-redux';
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
                <RaisedButton disabled={this.props.loading} label="Create" primary={true} onTouchTap={() => this.onCreateBoard()} />
            </div>
        );
    }

    async onCreateBoard() {
        if (!this.nameInput.getValue()) {
            return;
        }
        try {
            await this.props.dispatch(createBoard(this.nameInput.getValue()));
            this.props.dispatch(push(`/board/${this.nameInput.getValue()}`));
        } catch(e) { }

    }
}

export default connect(HomePage.mapStateToProps)(HomePage);
