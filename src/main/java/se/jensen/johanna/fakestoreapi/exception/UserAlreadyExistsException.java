package se.jensen.johanna.fakestoreapi.exception;

public class UserAlreadyExistsException extends DomainException {

  public UserAlreadyExistsException(String message) {
    super(message, ErrorType.USER_ALREADY_EXISTS);
  }
}
