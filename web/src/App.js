import React, { Component } from 'react';
import MuiThemeProvider from 'material-ui/styles/MuiThemeProvider';
import getMuiTheme from 'material-ui/styles/getMuiTheme';
import './App.css';

class App extends Component {
    constructor() {
        super();
        this.muiTheme = getMuiTheme();
    }

    render() {
        return (
            <MuiThemeProvider muiTheme={this.muiTheme}>
                { this.props.children }
            </MuiThemeProvider>
        );
    }
}

export default App;
