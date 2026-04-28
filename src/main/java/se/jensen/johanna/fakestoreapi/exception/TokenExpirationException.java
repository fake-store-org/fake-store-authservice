package se.jensen.johanna.fakestoreapi.exception;

public class TokenExpirationException extends DomainException {

  public TokenExpirationException(String message) {
    super(message, ErrorType.TOKEN_EXPIRED);
  }
}
