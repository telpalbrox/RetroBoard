import React, { Component } from 'react';
import { connect } from 'react-redux';
import TextField from 'material-ui/TextField';
import RaisedButton from 'material-ui/RaisedButton';
import { createBoard } from '../actions/boards';
import { withRouter } from "react-router-dom";

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
                <RaisedButton disabled={this.props.loading} label="Create" primary={true} onClick={() => this.onCreateBoard()} />
            </div>
        );
    }

    async onCreateBoard() {
        if (!this.nameInput.getValue()) {
            return;
        }
        try {
            await this.props.dispatch(createBoard(this.nameInput.getValue()));
            this.props.dispatch(this.props.history.push(`/board/${this.nameInput.getValue()}`));
        } catch(e) {
            console.error(e);
        }

    }
}

export default withRouter(connect(HomePage.mapStateToProps)(HomePage));
