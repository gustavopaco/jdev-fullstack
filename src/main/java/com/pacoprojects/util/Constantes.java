package com.pacoprojects.util;

public class Constantes {

    public static final String USERNAME = "username";
    public static final String BASICTOKEN = "basicToken";
    public static final String AUTHORIZATION_SERVICE_EXCEPTION_MESSAGE = "Usuário sem permissão, por favor tente logar novamente.";
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
}
