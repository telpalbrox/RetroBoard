import React, { Component } from 'react';
import Paper from 'material-ui/Paper';
import { red500 } from 'material-ui/styles/colors';
import IconButton from 'material-ui/IconButton';
import Ticket from './Ticket';
import './Section.css';

export default class Section extends Component {

    constructor(props) {
        super(props);
        this.paperStyle = {
            flex: '1 0 200px',
            maxWidth: '250px',
            margin: '28px 15px',
            padding: '5px',
            minHeight: '300px',
            position: 'relative'
        };

        this.removeButtonStyle = {
            position: 'absolute',
            top: '0',
            right: '0',
            padding: '6px'
        };

        this.removeIconStyle = {
            width: '30px',
            height: '30px',
            color: red500
        };
    }

    render() {
        const { section } = this.props;
        if (this.props.children) {
            return (
                <Paper style={this.paperStyle}>
                    { this.props.children }
                </Paper>
            );
        }
        return (
            <Paper style={this.paperStyle}>
                <section className="section">
                    <IconButton
                        onTouchTap={() => this.props.onRemoveSection()}
                        style={this.removeButtonStyle}
                        iconClassName="material-icons section__remove"
                        iconStyle={this.removeIconStyle}
                        color={red500}>delete
                    </IconButton>
                    <h3 className="section__name">{section.name}</h3>
                    {section.tickets.map((ticket) => <Ticket key={ticket.uuid} ticket={ticket} />)}
                </section>
            </Paper>
        );
    }
}
