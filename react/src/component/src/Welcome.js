import * as React from 'react'

export class Welcome extends React.Component {
    constructor(props) {
        super(props);
        this.state = {};
    }

    render() {
        return <div>{this.props.message}</div>
    }
}