@Project
Feature: Cadastro de Projeto

  Background: Cadastro de projeto através da api
    Given Desejo utilizar os servicos disponíves através do endereço "http://54.207.100.192"
    Then Devo utilizar como path o "projects"

  Scenario Outline: Realizar validacao do "<validacao>"
  	When Coleto informacoes de usuario para cadastro
    And Eu realizo um  "<tipoOperacao>" para realizar cadastro
    Then Valido a resposta e mensagem quando tiver "<mensagem>"
    And  Valido o status da requisicao "<statusCode>"
    Examples:
    | tipoOperacao | validacao                                   | statusCode | mensagem            |
    | post    	   | criar projeto sem autenticacao	             | 	  400		  | No token provided   |
    | post         | criar projeto com sucesso                   |    200     | N/A                 |
    | post         | criar projeto token invalido                |    401     | Token invalid       |
    | get          | consultar projeto por id                    |    200     | N/A                 |
    | get          | consultar projeto sem autenticacao          |    401     | No token provided   |
    | get          | consultar projeto token invalido            |    401     | Token invalid       |
    | get          | consultar todos os projetos                 |    200     | N/A                 |
    | get          | consultar todos os projetos sem autenticacao|    401     | No token provided   |
    | get          | consultar todos os projetos token invalido  |    401     | Token invalid       |
    | put          | atualizar projeto                           |    200     | N/A                 |
    | put          | atualizar projeto sem autenticacao          |    401     | No token provided   |
    | put          | atualizar projeto sem id                    |    404     | N/A                 |
    | delete       | deletar projeto                             |    200     | N/A                 |
    | delete       | deletar projeto sem autenticacao            |    401     | No token provided   |
    | delete       | delete projeto sem id                       |    404     | N/A                 |
