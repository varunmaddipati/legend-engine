parser grammar AuthenticationStrategyParserGrammar;

import CoreParserGrammar;

options
{
    tokenVocab = AuthenticationStrategyLexerGrammar;
}

identifier:                      VALID_STRING
;

// ----------------------------- RELATIONAL DATABASE CONNECTION AUTH STRATEGY -----------------------------

defaultH2Auth:                          H2_DEFAULT_AUTH
;
testDBAuth:                             TEST_DB_AUTH
;
delegatedKerberosAuth:                  DELEGATED_KERBEROS_AUTH delegatedKerberosAuthConfig?
;
delegatedKerberosAuthConfig:            BRACE_OPEN
                                            (
                                                serverPrincipalConfig
                                            )*
                                        BRACE_CLOSE
;
serverPrincipalConfig:                  SERVER_PRINCIPAL COLON STRING SEMI_COLON
;

snowflakePublicAuth:                    SNOWFLAKE_PUBLIC_AUTH
                                            BRACE_OPEN
                                                (
                                                    snowflakePublicAuthKeyVaultRef
                                                    | snowflakePublicAuthPassPhraseVaultRef
                                                    | snowflakePublicAuthUserName
                                                )*
                                            BRACE_CLOSE
;

redshiftPublicAuth:                     REDSHIFT_PUBLIC_AUTH
                                            BRACE_OPEN
                                                (
                                                    redshiftUserName
                                                    | redshiftPassword
                                                )*
                                            BRACE_CLOSE
;

redshiftUserName:                       REDSHIFT_USER_NAME COLON STRING SEMI_COLON
;

redshiftPassword:                       REDSHIFT_PASSWORD COLON STRING SEMI_COLON
;

snowflakePublicAuthKeyVaultRef:         SNOWFLAKE_AUTH_KEY_VAULT_REFERENCE COLON STRING SEMI_COLON
;

snowflakePublicAuthPassPhraseVaultRef:  SNOWFLAKE_AUTH_PASSPHRASE_VAULT_REFERENCE COLON STRING SEMI_COLON
;

snowflakePublicAuthUserName:  SNOWFLAKE_AUTH_PUBLIC_USERNAME COLON STRING SEMI_COLON
;