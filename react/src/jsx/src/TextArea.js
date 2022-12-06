import * as React from 'react'

export function TextArea() {
    const [checkbox, setCheckBox] = React.useState(false)

    return (<div>
        <label htmlFor="other">その他理由</label>
        <input namae="other" type="checkbox" onChange={(e) => {
            setCheckBox(e.target.checked)
        }} /><br />
        {
            checkbox && (
                <textarea>

                </textarea>
            )
        }
    </div>)
}