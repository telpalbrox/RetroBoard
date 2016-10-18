import React, { Component } from 'react';
import { connect } from 'react-redux';
import AppBar from 'material-ui/AppBar';
import RaisedButton from 'material-ui/RaisedButton';
import TextField from 'material-ui/TextField';
import { getBoard, connectBoard, createSection, removeSection } from '../actions/boards';
import Section from '../components/Section';
import './BoardPage.css';

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
                            return (<Section key={section.uuid} section={section} boardUuid={board.uuid} onRemoveSection={() => this.onRemoveSection(section)} />);
                        })}
                        <Section>
                            <div className="create-section">
                                <h4>Create a new section</h4>
                                <div className="create-section__input">
                                    <TextField
                                        inputStyle={this.createSectionInputStyles}
                                        fullWidth={true}
                                        hintText="Action items"
                                        floatingLabelText="Name your section"
                                        ref={(node) => this.sectionNameInput = node}
                                    />
                                </div>
                                <RaisedButton disabled={this.props.loading} label="Create" primary={true} onTouchTap={() => this.onCreateSection()} />
                            </div>
                        </Section>
                    </div>
                </div>
            </div>
        );
    }

    async onCreateSection() {
        if (!this.sectionNameInput.getValue()) {
            return;
        }
        await this.props.dispatch(createSection({ boardUuid: this.props.board.uuid, name: this.sectionNameInput.getValue() }));
        this.sectionNameInput.input.value = '';
        this.sectionNameInput.setState({ hasValue: false });
    }

    onRemoveSection(section) {
        this.props.dispatch(removeSection({ boardUuid: this.props.board.uuid, section }));
    }
}

export default connect(BoardPage.mapStateToProps)(BoardPage);
