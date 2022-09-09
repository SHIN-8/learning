import * as React from 'react'

export class Welcome extends React.Component {
    constructor(props) {
        super(props);
        this.state = {message: "hello"};
    }

    render() {
        return <div>{this.state.message}</div>
    }
}