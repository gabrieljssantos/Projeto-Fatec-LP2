# Projeto LP2

## Descrição
Projeto da disciplina de LP2, focado em resoluções para
conferência e casos de testes usados para conferência do problema registrado, de forma
similar ao sistema BOCA.

## Ferramentas e Tecnologias Utilizadas
- **Java**: 17
- **Spring Boot**: 3.1.2
- **MySQL**: 8.0
- **Maven**: 3.8.5
- **Docker Compose**: 3.8
- **Dependências**:
  - Hibernate
  - Lombok
- **Build Tool**: Maven
- **Postman**: Utilizado para envio das requisições
- **Gitpod**: Uma forma para acessar o projeto via browser, facilitando o acesso.

## Build Do Projeto

O Projeto está utilizando docker-compose, buscando facilitar o momento de preparação do ambiente, e foi desenvolvido via Gitpod, que é uma forma de desenvolvimento facilitado via browser.

Recomendo a utilização do projeto via Gitpod, mas caso necessário, pode-se clonar o projeto e rodar local

#### Preparação de ambiente via Gitpod (recomendado):

1. Acesse o gitpod pelo link https://gitpod.io/
2. Faça login pela propia conta do github
3. Clique em "New Workspace", e coloque o link do projeto https://github.com/gabrieljssantos/Projeto-Fatec-LP2
4. Após acessar o projeto via Gitpod, com interface do VSCode, podemos rodar os comandos necessários para buildar o projeto

#### Preparação de ambiente via Clone do Repositorio:

1. Clone o repositorio do Projeto https://github.com/gabrieljssantos/Projeto-Fatec-LP2
2. Entre dentro da raiz do repositorio
3. Abra com a IDE de preferencia
4. Após acesso a IDE, podemos rodar os comandos necessários para buildar o projeto

####  Comandos Necessários para Build

1. Rodar o comando do docker compose: "docker-compose up --build"
2. Dentro do terminal, podemos acessar a área de "PORTS" para obter a URL do projeto.<br>
    Exemplo de URL/Porta: https://8080-gabrieljssa-projetofate-x7j86dqiofb.ws-us114.gitpod.io
3. Essa será a URL utilizada dentro do POSTMAN para envio das requisições.

## Endpoints

#### ActivityController

<b>`GET /activity:`</b>Retorna todas as atividades. <br>
<b>`GET /activity/{activityCode}:`</b> Retorna uma atividade pelo código da atividade.<br>
<b>`POST /activity:`</b> Cria uma nova atividade.<br>
<b>`DELETE /activity/{activityCode}:`</b> Remove uma atividade pelo código da atividade.<br>
<br>

Exemplo de body para as requisições, via POSTMAN nesse endpoint:

1. Coloque a URL do endpoint;
2. O tipo de requisição será POST
2. Selecione Body;
3. Selecione o tipo de body "raw";

```json
{
    "filename": "file3",            (texto)
    "activityCode": "activity3",    (texto)
    "lps": "python"                 (texto)
}
```


#### ResultController

<b>`GET /activity/solution:`</b> Retorna todas as soluções.<br>
<b>`GET /activity/solution/{id}:`</b> Retorna uma solução pelo ID.<br>
<b>`POST /activity/solution:`</b> Envia uma solução.<br>
<b>`DELETE /activity/solution/{resultId}:`</b> Remove uma solução pelo ID.<br>
<br>

Exemplo de body para as requisições, via POSTMAN nesse endpoint:

1. Coloque a URL do endpoint;
2. O tipo de requisição será POST
2. Selecione Body;
3. Selecione o tipo de body "form-data";

```json
{
    "author": "file3",                          (texto)
    "activityCode": "activity3",                (texto)
    "filename": "python"                        (texto)
    "sourceCode": <<example.py>>                (file)
}

obs.: No ultimo campo, 'sourceCode', certifique-se de selecionar o arquivo
obs.: O arquivo que será enviado em 'sourceCode', é o arquivo responsavel pela resolução do problema. Ao decorrer do read.me estará disponivel 3 arquivos de testes ("zerinho ou um", "mergulho", "a elevação de um numero (exemplo)")
```

#### TestCaseController

<b>`GET /tc:`</b> Retorna todos os casos de teste.<br>
<b>`GET /tc/{activityCode}:`</b> Retorna os casos de teste pelo código da atividade.<br>
<b>`POST /tc:`</b> Faz o upload de um caso de teste.<br>
<b>`DELETE /tc/{tcId}:`</b> Remove um caso de teste pelo ID.<br>

<br>

Exemplo de body para as requisições, via POSTMAN nesse endpoint:

1. Coloque a URL do endpoint;
2. O tipo de requisição será POST
2. Selecione Body;
3. Selecione o tipo de body "form-data";

```json
{
    "activityCode": "activity3",                    (texto)
    "inputFile": <<inputFile.txt>>,                 (file)
    "outputFile": <<outputFile.t>>                  (file)
}

obs.: Nos 2 ultimos campos, 'inputFile' e 'outputFile', certifique-se de selecionar o arquivo
obs 2.: Os arquivos 'inputFile' e 'outputFile', são arquivos de texto (.txt), onde vão ser enviados os arquivos de entrada e saida, respectivamente, que serão testados posteriormente
```

## Acesso ao Banco de Dados

Além de visualizarmos nossos dados salvos pelo POSTMAN, podemos acessa-los pelo terminal do projeto, em nosso Mysql.

1. Após o projeto estar rodando em um terminal, criamos outro terminal para acessar o banco.
2. Rodamos o comando "docker ps"
3. Depois disso o comando "docker exec -it mysql_container mysql -u user -ppass123" <br>
    OBS.: esses dados são, user e senha, se encontram no arquivo "docker-compose.yml"
4. Após isso, estaremos com acesso em nosso Mysql
5. Rode o comando "USE spring;"
6. Rode o comando "Show Tables;"
7. Após isso, aparecerá todas tabelas disponiveis para consulta; <br>
    Exemplo: SELECT * FROM activity;

## Exemplos de arquivos .py, para resolução

### - Zerinho ou Um

```py
# zerinho_ou_um.py
import sys

# Ler a entrada do stdin
input_data = sys.stdin.read().strip()

# Dividir a entrada em três inteiros
A, B, C = map(int, input_data.split())

# Determinar o vencedor
if A == B == C:
    print('*')
elif A != B and A != C:
    print('A')
elif B != A and B != C:
    print('B')
else:

    print('C')


```



### - Mergulho

```py
# mergulho.py
import sys

# Ler a entrada do stdin
input_data = sys.stdin.read().strip().split('\n')

# Primeira linha da entrada
N, R = map(int, input_data[0].split())
returned = list(map(int, input_data[1].split()))

# Determinar os voluntários que não retornaram
all_volunteers = set(range(1, N + 1))
returned_set = set(returned)
not_returned = sorted(all_volunteers - returned_set)

# Produzir a saída conforme especificado
if not_returned:
    print(' '.join(map(str, not_returned)) + ' ')
else:
    print('*')
```


### - Elevar número

```py
# elevacao.py
import sys

# Ler a entrada do stdin
input_data = sys.stdin.read().strip()

# Converter a entrada para um número inteiro
num = int(input_data)

# Calcular o quadrado do número
resultado = num ** 2

# Imprimir o resultado
print(resultado)

```


