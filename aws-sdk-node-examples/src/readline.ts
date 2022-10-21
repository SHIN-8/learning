import * as process from 'process'
import * as readline from 'readline'


process.stdin.resume();
process.stdin.setEncoding('utf8');
let input = ""
const reader = readline.createInterface({
    input: process.stdin,
    output: process.stdout
}).on('line', (data) => {
    input = data;
    console.log(`${data}が入力されました`)
}).on('close', () => {
})
