import * as React from 'react'

export class Message extends React.Component {
    constructor(props) {
        super(props);
        this.state = { message: '' };
    }

    render() {
        return <div>
            <input type="text" value={this.state.message} onChange={this.handleChange} /><br />
            <p>入力された値は{this.state.message}</p>
        </div>
    }

    handleChange = (e) => {
        this.setState({ message: e.target.value })
    }
}