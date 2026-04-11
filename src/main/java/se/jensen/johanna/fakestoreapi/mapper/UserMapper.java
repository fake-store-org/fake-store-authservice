package se.jensen.johanna.fakestoreapi.mapper;

import org.mapstruct.Mapper;
import se.jensen.johanna.fakestoreapi.dto.AddressRequest;
import se.jensen.johanna.fakestoreapi.dto.AddressResponse;
import se.jensen.johanna.fakestoreapi.model.Address;

@Mapper(componentModel = "spring")
public interface UserMapper {


  AddressResponse toAddressResponse(Address address);


  default Address toAddress(AddressRequest request) {
    return Address.create(
        request.firstName(),
        request.lastName(),
        request.co(),
        request.streetName(),
        request.streetName2(),
        request.postalCode(),
        request.city(),
        request.country()
    );
  }

}
