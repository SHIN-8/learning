import * as React from 'react'

export function Students() {
    const students = [
        { id: 1, name: 'taro', age: 20, score: 50 },
        { id: 2, name: 'hanako', age: 20, score: 45 },
        { id: 3, name: 'kumiko', age: 20, score: 70 },
        { id: 4, name: 'jiro', age: 20, score: 88 }]

    return (<div>
        <table className='border-separate border accecent-slate-400'>
            <thead>
                <tr >
                    <td className='border border-slate-300'>ID</td>
                    <td className='border border-slate-300'>Name</td>
                    <td className='border border-slate-300'>Age</td>
                    <td className='border border-slate-300'>Score</td>
                </tr>
            </thead>
            <tbody>
                {
                    students.map(e => {
                        return (<tr>
                            <td className='border border-slate-300'>{e.id}</td>
                            <td className='border border-slate-300'>{e.name}</td>
                            <td className='border border-slate-300'>{e.age}</td>
                            <td className='border border-slate-300'>{e.score}</td>
                        </tr>)
                    })
                }
            </tbody>
        </table>
    </div>)
}