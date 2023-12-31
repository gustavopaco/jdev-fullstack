package com.pacoprojects.util;

public class Constantes {

    // Jwt
    public static final String USERNAME = "username";
    public static final String BASICTOKEN = "basicToken";
    public static final String AUTHORIZATION_SERVICE_EXCEPTION_MESSAGE = "Usuário sem permissão, por favor tente logar novamente.";
    public static final String EXPIRED_JWT_TOKEN = "ExpiredJwtException";
    public static final String EXPIRED_JWT_EXCEPTION_MESSAGE = "Sessão expirada, por favor faça o login novamente.";
    public static final String MALFORMED_JWT_EXCEPTION_MESSAGE = "Sessão foi invalidada, por favor faça o login novamente.";
    public static final String USERNAME_NOTFOUND_EXCEPTION = "Usuário não existente.";
    public static final String BAD_CREDENTIALS_EXCEPTION_MESSAGE = "Usuário ou senha estão incorretos.";
    public static final String INTERNAL_SERVER_ERROR_MESSAGE = "Ocorreu um erro inesperado no servidor";
    public static final String LOCKED_EXCEPTION_MESSAGE = "Sua conta está bloqueada.";
    public static final String EMAIL_TAKEN = "E-mail já está em uso.";
    public static final String ROLE_NOTFOUND_IN_DB = "Permissões não foram cadastradas no Banco de Dados";
    public static final String ERROR_WHILE_SENDING_MAIL = "Erro ao enviar e-mail";

    // Email
    public static final String SUBJECT = "Recuperação de Senha";
    public static final String BODY =  "Esse link para resetar a senha é válido por 24 horas, ";


    // Reset Password
    public static final String RESET_TOKEN_EXPIRED = "O token expirou, tente recuperar a senha novamente.";
    public static final String RESET_TOKEN_INVALID = "O token de reset está inválido, utilize um token válido ou tente recuperar a senha novamente";
    public static final String PASSWORD_ALREADY_IN_USE = "O password já está em uso";

    // Relatorio
    public static final String NOME_RELATORIO_BASICO = "relatorio-usuarios";
    public static final String NOME_RELATORIO_PARAM = "relatorio-usuarios-param";
    public static final String REPORT_SQL_EXCEPTION_MESSAGE = "Erro ao consultar dados no Banco de dados";
    public static final String JR_EXCEPTION_MESSAGE = "Erro ao gerar relatório, entre em contato com o administrador";
    public static final String IO_EXCEPTION_MESSAGE = "Erro ao buscar relatório, entre em contato com o administrador";
    public static final String DEFAULT_MIME_TYPE = ".pdf";

    // ChartDto2
    public static final String NO_DATA_CHART = "Não existem registros no banco suficientes de Nome e Salario para gerar gráfico";
}
