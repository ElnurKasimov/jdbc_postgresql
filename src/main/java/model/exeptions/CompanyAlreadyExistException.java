package model.exeptions;

public class CompanyAlreadyExistException extends RuntimeException{

        public CompanyAlreadyExistException(String message) {
            super(message);
        }
}

