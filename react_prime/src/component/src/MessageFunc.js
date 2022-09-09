import * as React from 'react'

export function Message() {
    const [message, setMessage] = React.useState("")
    return <div>
        <input type="text" value={message} onChange={(event) => {
            setMessage(event.target.value)
        }} /><br />
        <p>入力された値は{message}</p>
    </div>
}