import * as React from 'react'

export function Students() {
    const students = [
        { id: 1, name: 'taro', age: 20, score: 50 },
        { id: 2, name: 'hanako', age: 20, score: 45 },
        { id: 3, name: 'kumiko', age: 20, score: 70 },
        { id: 4, name: 'jiro', age: 20, score: 88 }]

    return (<div>
        <table>
            <thead>
                <tr >
                    <td>ID</td>
                    <td>Name</td>
                    <td>Age</td>
                    <td>Score</td>
                </tr>
            </thead>
            <tbody>
                {
                    students.map(e => {
                        return (<tr>
                            <td>{e.id}</td>
                            <td>{e.name}</td>
                            <td>{e.age}</td>
                            <td>{e.score}</td>
                        </tr>)
                    })
                }
            </tbody>
        </table>
    </div>)
}