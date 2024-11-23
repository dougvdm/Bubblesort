const fs = require('fs');
const path = require('path');

// Definindo path para o arquivo a ser lido e onde salvar o novo arquivo
const inputPathArquivo = path.join(__dirname, 'arq.txt'); 
const outputPathArquivo = path.join(__dirname, 'insertionsort_JS.txt'); 

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

// Função para ler o arquivo e adicionar no Array criado acima
function lerArquivo(filePath, callback) {
    const startTime = process.hrtime();
    const startMemory = process.memoryUsage();

    fs.readFile(filePath, 'utf8', (err, data) => {
        // Caso de erro
        if (err) {
            console.error('Erro ao ler o arquivo:', err);
            return;
        }
        
        // Separando os números pela quebra de linha
        const linhas = data.split('\n');
        // Criando um mapa com os números
        const numeros = linhas.map(linhas => parseFloat(linhas.trim())).filter(num => !isNaN(num));
        // Adicionando os números ao array
        arrayNumeros = arrayNumeros.concat(numeros); 
        // Usando a função para o print da performance
        printPerformance(startTime, startMemory);
        // Usando a função de callback
        callback();
    });
}

// Função de Insertion Sort
function insertionSort(arr) {
    let n = arr.length;
    for (let i = 1; i < n; i++) {
        let key = arr[i];
        let j = i - 1;
        
        // Move os elementos maiores que a chave para uma posição à frente
        while (j >= 0 && arr[j] > key) {
            arr[j + 1] = arr[j];
            j--;
        }
        
        // Coloca a chave na posição correta
        arr[j + 1] = key;
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
    insertionSort(arrayNumeros); // Usando Insertion Sort
    escreverArquivo(outputPathArquivo, arrayNumeros, () => {
        console.log('Processo concluído.');
    });
}

// Chamando a função para iniciar o programa
lerArquivo(inputPathArquivo, ordenarESalvar);