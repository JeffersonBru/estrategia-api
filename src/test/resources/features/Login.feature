@Login
Feature: Realizar login

  Background: Usuario autenticando através da api
    Given Desejo utilizar os servicos disponíves através do endereço "http://54.207.100.192"
    Then Devo possuir um usuario cadastrado
    And Devo utilizar como basePath o "auth" e como path o "authenticate"

  Scenario Outline: Realizar validacao do "<validacao>"
    When Eu realizo um  "<tipoOperacao>"
    Then Valido a resposta e mensagem quando tiver "<mensagem>"
    And  Valido o status da requisicao "<statusCode>"
    Examples:
    | tipoOperacao | validacao                  | statusCode | mensagem        |
    | post    	   | login com sucesso		    	| 	 200		 | N/A             |
    | post         | login com dados vazio      |    400     | User not found  |
    | post         | login somente usuario      |    400     | User not found  |
    | post         | login somente senha        |    400     | User not found  |
    | post         | login usuario inexistente  |    400     | User not found  |
    | post         | login senha invalida       |    400     | Invalid password|
