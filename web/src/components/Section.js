import React, { Component } from 'react';
import Paper from 'material-ui/Paper';
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
            minHeight: '300px'
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
                <section>
                    <h3 className="section__name">{section.name}</h3>
                    {section.tickets.map((ticket) => <Ticket key={ticket.uuid} ticket={ticket} />)}
                </section>
            </Paper>
        );
    }
}
