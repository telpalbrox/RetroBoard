import React, { Component } from 'react';
import {Card, CardText} from 'material-ui/Card';

export default class Ticket extends Component {

    constructor(props) {
        super(props);
        this.containerStyle = {
            margin: '7px 0'
        };
    }

    render() {
        return (
            <Card containerStyle={this.containerStyle}>
                <CardText>
                    { this.props.ticket.content }
                </CardText>
            </Card>
        );
    }
}
