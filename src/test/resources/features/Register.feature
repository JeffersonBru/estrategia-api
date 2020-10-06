@Register
Feature: Realizar registro

  Background: Usuario autenticando através da api
    Given Desejo utilizar os servicos disponíves através do endereço "http://54.207.100.192"
    Then Devo utilizar como basePath o "auth" e como path o "register"

  Scenario Outline: Realizar validacao do "<validacao>"
  	When Coleto informacoes de usuario para cadastro
    And Eu realizo um  "<tipoOperacao>" para realizar cadastro
    Then Valido a resposta e mensagem quando tiver "<mensagem>"
    And  Valido o status da requisicao "<statusCode>"
    Examples:
    | tipoOperacao | validacao                  | statusCode | mensagem            |
    | post    	   | cadastro sem dados		    	| 	 400		 | Registration failed |
    | post         | cadastro com apenas nome   |    400     | Registration failed |
    | post         | cadastro sem senha         |    400     | Registration failed |
    | post         | cadastro sem nome          |    400     | Registration failed |
    | post         | cadastro com sucesso       |    200     | N/A                 |
