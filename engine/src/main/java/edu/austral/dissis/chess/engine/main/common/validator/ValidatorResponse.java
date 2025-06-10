package edu.austral.dissis.chess.engine.main.common.validator;

// razon de fallo, es para ver que nos va mal
public interface ValidatorResponse {

  // representacion de casos validos
  record ValidatorResultValid(String message) implements ValidatorResponse {}

  // representacion de casos invalidos
  record ValidatorResultInvalid(String message) implements ValidatorResponse {}
}
