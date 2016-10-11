import React, { Component } from 'react';
import {Card, CardText} from 'material-ui/Card';

export default class Ticket extends Component {

    constructor(props) {
        super(props);
        this.ticket = props.ticket;
        this.containerStyle = {
            margin: '7px 0'
        };
    }

    render() {
        return (
            <Card containerStyle={this.containerStyle}>
                <CardText>
                    { this.ticket.content }
                </CardText>
            </Card>
        );
    }
}
