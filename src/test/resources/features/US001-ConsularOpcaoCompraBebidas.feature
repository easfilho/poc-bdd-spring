# language: pt

Funcionalidade: Consultar Opcao Compra Bebidas
  Eu como consumidor de bebidas
  Quero consultar opção de compra de bebidas
  Para saber as possíveis bebidas que posso comprar com a minha quantidade de dinheiro

  Contexto:
    Dado que tenho as seguintes bebidas para compra
      | Nome      | Valor | Qualidade |
      | Corote    | 3.00  | 1         |
      | Kaiser    | 4.50  | 2         |
      | Budweiser | 6.00  | 3         |
      | Red Label | 98.00 | 4         |

  Cenário: Deve consultar opcoes de bebida para 20,20 reais ordenando pela menor qualidade
    Dado que tenho "20.20" reais para comprar bebidas
    E que quero consultar as bebidas de menor qualidade
    Quando consultar as bebidas
    Então deve retornar as seguintes opções de bebidas
      | Nome   | Quantidade |
      | Corote | 6          |
    E deve sobrar de troco "2.20" reais
    E deve retornar o status 200

  Cenário: Deve consultar opções de bebida para 102,50 ordenando pela maior qualidade sem troco
    Dado que tenho "102.50" reais para comprar bebidas
    E que quero consultar as bebidas de maior qualidade
    Quando consultar as bebidas
    Então deve retornar as seguintes opções de bebidas
      | Nome      | Quantidade |
      | Red Label | 1          |
      | Kaiser    | 1          |
    E deve sobrar de troco "0.00" reais
    E deve retornar o status 200

  Cenário: Deve consultar opcoes de bebida para 22,25 reais ordenando pela maior qualidade
    Dado que tenho "22.25" reais para comprar bebidas
    E que quero consultar as bebidas de maior qualidade
    Quando consultar as bebidas
    Então deve retornar as seguintes opções de bebidas
      | Nome      | Quantidade |
      | Budweiser | 3          |
      | Corote    | 1          |
    E deve sobrar de troco "1.25" reais
    E deve retornar o status 200

  Cenário: Deve consultar opções de bebidas para 1,00 reais ordenando pela menor qualidade
    Dado que tenho "1.00" reais para comprar bebidas
    E que quero consultar as bebidas de menor qualidade
    Quando consultar as bebidas
    Então não deve retornar bebidas
    E deve retornar o status 200

  Cenário: Deve validar se o valor a gastar foi informado
    Dado que não informo o valor a gastar
    E que quero consultar as bebidas de maior qualidade
    Quando consultar as bebidas
    Então deve retornar a mensagem "Valor a gastar deve ser informado."
    E deve retornar o status 400