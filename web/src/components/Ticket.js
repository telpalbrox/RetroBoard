import React, { Component } from 'react';
import IconButton from 'material-ui/IconButton';
import {Card, CardText} from 'material-ui/Card';

export default class Ticket extends Component {

    constructor(props) {
        super(props);
        this.containerStyle = {
            margin: '7px 0',
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
            fontSize: '15px'
        };
    }

    render() {
        return (
            <Card containerStyle={this.containerStyle}>
                <IconButton
                    onTouchTap={() => this.props.onRemoveTicket()}
                    style={this.removeButtonStyle}
                    iconClassName="material-icons"
                    iconStyle={this.removeIconStyle}>delete
                </IconButton>
                <CardText>
                    { this.props.ticket.content }
                </CardText>
            </Card>
        );
    }
}
