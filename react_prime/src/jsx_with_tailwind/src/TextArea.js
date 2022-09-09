import * as React from 'react'

export function TextArea() {
    const [checkbox, setCheckBox] = React.useState(false)

    return (<div>
        <label htmlFor="other">その他理由</label>
        <input namae="other" type="checkbox" className='accent-pink-300 focus:accent-pink-500' onChange={(e) => {
            setCheckBox(e.target.checked)
        }} /><br />
        {
            checkbox && (
                <textarea className='border-solid border border-indigo-600'>

                </textarea>
            )
        }
    </div>)
}