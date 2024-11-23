const fs = require('fs');
const path = require('path');

// Definindo path para o arquivo a ser lido e onde salvar o novo arquivo

const inputPathArquivo = path.join(__dirname, 'arq.txt'); 
const outputPathArquivo = path.join(__dirname, 'arquivo_ordenadoJS.txt'); 

// Array para manipular os numeros

let arrayNumeros = [];

// Função para printar o uso de memoria e o tempo de execução
function printPerformance(startTime, startMemory) {
    const endTime = process.hrtime(startTime);
    const endMemory = process.memoryUsage();
    
    console.log('Tempo de execução: %ds %dms', endTime[0], endTime[1] / 1e6);
    console.log('Memória usada:');
    console.log('  Heap Used: %d KB', endMemory.heapUsed / 1024);
}

// Função para ler o arquivo e adicionar no Array criado a cima

function lerArquivo(filePath, callback) {
    const startTime = process.hrtime();
    const startMemory = process.memoryUsage();

    fs.readFile(filePath, 'utf8', (err, data) => {
        // Caso de erro
        if (err) {
            console.error('Erro ao ler o arquivo:', err);
            return;
        }
        
        // Separando os numeros pela quebra de linha
        const linhas = data.split('\n');
        // Criando um mapa com os numeros
        const numeros = linhas.map(linhas => parseFloat(linhas.trim())).filter(num => !isNaN(num));
        // Adicionando os numeros ao array
        arrayNumeros = arrayNumeros.concat(numeros); 
        // Usando a função para o print da performance
        printPerformance(startTime, startMemory);
        // Usando a função de callback
        callback();
    });
}

// Função de Bubble Sort
function bubbleSort(arr) {
    let len = arr.length;
    for (let i = 0; i < len; i++) {
        for (let j = 0; j < len - 1 - i; j++) {
            if (arr[j] > arr[j + 1]) {
                let temp = arr[j];
                arr[j] = arr[j + 1];
                arr[j + 1] = temp;
            }
        }
    }
}

// Função para gravar o array ordenado em um novo arquivo
function escreverArquivo(filePath, numerosArray, callback) {
    const data = numerosArray.join('\n') + '\n'; // Formata os números como linhas separadas

    const startTime = process.hrtime();
    const startMemory = process.memoryUsage();

    fs.writeFile(filePath, data, 'utf8', (err) => {
        if (err) {
            console.error('Erro ao gravar o arquivo:', err);
            return;
        }
        console.log('Arquivo gravado com sucesso:', filePath);

        // Mensurar o desempenho após a gravação do arquivo
        printPerformance(startTime, startMemory);

        callback();
    });
}

// Função de callback para usar o array após a leitura, ordenação e gravação
function ordenarESalvar() {
    bubbleSort(arrayNumeros);
    escreverArquivo(outputPathArquivo, arrayNumeros, () => {
        console.log('Processo concluído.');
    });
}

// Chamando a função para iniciar o programa
lerArquivo(inputPathArquivo, ordenarESalvar);