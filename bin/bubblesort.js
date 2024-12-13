const fs = require('fs');
const os = require('os');

// Função de ordenação Bubble Sort
function bubbleSort(arr) {
    let n = arr.length;
    for (let i = 0; i < n; i++) {
        for (let j = 0; j < n - i - 1; j++) {
            if (arr[j] > arr[j + 1]) {
                [arr[j], arr[j + 1]] = [arr[j + 1], arr[j]];
            }
        }
    }
}

// Função principal
function main() {
    // Informações da linguagem e sistema
    console.log("Linguagem: JavaScript (Node.js)");
    console.log(`Versão: ${process.version}`);
    console.log(`Sistema Operacional: ${os.type()} ${os.release()}`);
    console.log(`CPU: ${os.cpus()[0].model}`);
    console.log(`Memória RAM: ${(os.totalmem() / (1024 ** 3)).toFixed(2)} GB\n`);

    // Lendo o arquivo
    const inputFile = 'src/arq.txt';
    const outputFile = 'bubblesort_JS.txt';

    try {
        const data = fs.readFileSync(inputFile, 'utf8');
        const numeros = data.split('\n').filter(line => line.trim() !== '').map(Number);

        // Bubble Sort
        const start = process.hrtime();
        bubbleSort(numeros);
        const end = process.hrtime(start);

        // Salvando em arquivo de saída
        fs.writeFileSync(outputFile, numeros.join('\n'), 'utf8');

        // Informações de tempo e memória
        const tempoMs = (end[0] * 1000) + (end[1] / 1e6);
        const memoriaKb = process.memoryUsage().heapUsed / 1024;
        console.log(`Tempo de execução: ${tempoMs.toFixed(2)} ms`);
        console.log(`Memória utilizada: ${memoriaKb.toFixed(2)} KB`);
    } catch (err) {
        console.error(`Erro: ${err.message}`);
    }
}

main();